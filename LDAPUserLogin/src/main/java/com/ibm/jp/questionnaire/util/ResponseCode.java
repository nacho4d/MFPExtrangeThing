package com.ibm.jp.questionnaire.util;

public enum ResponseCode {

    /**
     * 認証成功
     */
    GRANTED(0, null),
    /**
     * 認証情報不正 (正常エラー)
     */
    CREDENTIALS_INCORRECT(1, "Username or password is incorrect"),
    /**
     * 認証情報が空文字 (正常エラー)
     */
    CREDENTIALS_EMPTY(2, "Credentials are empty"),
    /**
     * 認証情報が見つかりませんでした (正常エラー)
     */
    USER_NOT_FOUND(3, "UserID not found"),
    /**
     * 認証情報のユーザーは権限不足 (正常エラー)
     */
    ACCESS_RESTRICTED(4, "User has insufficient access rights"),
    /**
     * 認証情報がユニークでない (異常エラー)
     */
    CREDENTIALS_NON_UNIQUE(94, "UserID is not unique"),
    /**
     * 認証処理は実行されていない (異常エラー)
     */
    AUTHENTICATION_NOT_PERFORMED(95, "Authentication was not performed"),
    /**
     * 認証情報がnull (異常エラー)
     */
    CREDENTIALS_NOT_SET(96, "Credentials not set"),
    /**
     * 認証成功後 DB更新処理中のエラー (異常エラー)
     */
    UNEXPECTED_ERROR_WHILE_UPDATING_DB(95, "Connection error occurred while updating user information"),
    /**
     * DBサーバー接続エラー (異常エラー)
     */
    DB_CONNECTION_ERROR(96, "DB Connection error occurred while validating credentials"),
    /**
     * 認証サーバー接続エラー (異常エラー)
     */
    LDAP_CONNECTION_ERROR(97, "LDAP Connection error occurred while validating credentials"),
    /**
     * サーバー接続エラー (一般 異常エラー)
     */
    CONNECTION_ERROR(98, "Connection error occurred while validating credentials"),
    /**
     * 予期ぜぬエラー (一般 異常エラー)
     */
    UNEXPECTED_ERROR(99, "Unexpected error occurred while validating credentials");

    private int code;
    private String errorMsg;

    public int getCode() {
        return code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    ResponseCode(int code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }
}
