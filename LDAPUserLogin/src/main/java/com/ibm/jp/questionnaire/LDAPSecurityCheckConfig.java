package com.ibm.jp.questionnaire;

import java.util.Properties;
import java.util.logging.Logger;

import com.ibm.mfp.security.checks.base.UserAuthenticationSecurityCheckConfig;

public class LDAPSecurityCheckConfig extends UserAuthenticationSecurityCheckConfig {

    private static Logger logger = Logger.getLogger(LDAPSecurityCheckConfig.class.getName());

    public LDAPSecurityCheckConfig(Properties properties) {
        super(properties);
    }

}
