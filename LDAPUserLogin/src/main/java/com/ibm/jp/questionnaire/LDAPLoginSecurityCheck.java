package com.ibm.jp.questionnaire;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import com.ibm.jp.questionnaire.util.*;
import com.ibm.mfp.security.checks.base.UserAuthenticationSecurityCheck;
import com.ibm.mfp.server.registration.external.model.AuthenticatedUser;
import com.ibm.mfp.server.security.external.checks.SecurityCheckConfiguration;

public class LDAPLoginSecurityCheck extends UserAuthenticationSecurityCheck {

    private static final long serialVersionUID = 42L;

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String REMEMBER_ME = "rememberMe";
    private static final String CLIENT_NAME = "client";

    private static final String CLIENT_WEB = "adminToolApp";
    private static final String CLIENT_IOS = "iosApp";

    static Logger logger = LogUtil.getLogger(LDAPSecurityCheckConfig.class.getName());

    // When I use LDAPUser object there are problems when using requestWithDelegate method in iOS
    // Also, randomly userId becomes null after a while.
    //private LDAPUser user;

    String userId;
    String userDisplayName;
    Integer userPrivilege = null;
    boolean userRememberMe;

    private ResponseCode responseCode;

    ByteArrayOutputStream logBuffer = null;

    @Override
    protected AuthenticatedUser createUser() {
        logger.info("createUser " + logUser());
        HashMap<String, Object> m = new HashMap<>();
        m.put("isSysAdmin", userPrivilege != null && userPrivilege == 0);
        m.put("isMediaAdmin", userPrivilege != null && (userPrivilege == 0 || userPrivilege == 1));
        AuthenticatedUser authenticatedUser = new AuthenticatedUser(
                userId,
                userDisplayName,
                this.getName(), m);
        return authenticatedUser;
    }

    @Override
    public SecurityCheckConfiguration createConfiguration(Properties properties) {
        logger.info("createConfiguration " + logUser());
        return new LDAPSecurityCheckConfig(properties);
    }

    @Override
    protected LDAPSecurityCheckConfig getConfiguration() {
        logger.info("getConfiguration " + logUser());
        return (LDAPSecurityCheckConfig) super.getConfiguration();
    }

    /**
     * This method is called by the base class UserAuthenticationSecurityCheck when an authorization
     * request is made that requests authorization for this security check or a scope which contains this security check
     *
     * @param credentials credentials given by challenge request
     * @return true if the credentials are valid, false otherwise
     */
    @Override
    protected boolean validateCredentials(Map<String, Object> credentials) {
        logger.info("=============== Attempt validateCredentials with hardcoded user: IBMJ001 =================");
        logger.info("validateCredentials " + logUser());

        String username = credentials.get(USERNAME).toString();
        String password = credentials.get(PASSWORD).toString();
        boolean rememberMe = Boolean.valueOf(credentials.getOrDefault(REMEMBER_ME, "false").toString());

        logger.info("searchLDAP1 -> IBMJ001? No ldaps at all");
        if (username.equals("IBMJ001")) {
            userId = "IBMJ001";
            userDisplayName = "IBM Admin";
            userPrivilege = 0;
            userRememberMe = rememberMe;

            logger.info("searchLDAP1 -> IBMJ001 will return GRANTED!!");
            responseCode = ResponseCode.GRANTED;
            return true;
        }
        responseCode = ResponseCode.CONNECTION_ERROR;
        return false;
    }

    /**
     * This method is describes the challenge JSON that gets sent to the client during the authorization process
     * This is called by the base class UserAuthenticationSecurityCheck when validateCredentials returns false and
     * the number of remaining attempts is > 0
     *
     * @return the challenge object
     */
    @Override
    protected Map<String, Object> createChallenge() {
        logger.info("createChallenge " + logUser());
        Map<String, Object> challenge = new HashMap<>();
        challenge.put("code", responseCode == null ? null : responseCode.getCode());
        challenge.put("errorMsg", responseCode == null ? null : responseCode.getErrorMsg());
        challenge.put("remainingAttempts", getRemainingAttempts());
        return challenge;
    }

    @Override
    protected boolean rememberCreatedUser() {
        logger.info("rememberCreatedUser " + logUser());
        return userRememberMe;
    }

    private String logUser() {
        return user != null ? user.toString() : "null";
    }
}
