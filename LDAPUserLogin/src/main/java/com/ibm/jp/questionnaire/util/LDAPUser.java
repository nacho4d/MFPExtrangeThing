package com.ibm.jp.questionnaire.util;

public class LDAPUser {

    /** 担当者テーブル_姓_最大長*/
    private static final int LASTNAME_MAX_LENGTH = 40;
    /** 担当者テーブル_名_最大長 */
    private static final int FIRSTNAME_MAX_LENGTH = 40;
    /** 担当者テーブル_表示名_最大長 */
    private static final int DISPLAYNAME_MAX_LENGTH = 32;
    /** 文字コード_UTF8 */
    private static final String CHARSET_UTF8 = "UTF-8";

    private String userId;
    private String displayName;
    private String firstName;
    private String lastName;
    private Integer privilege = null;
    private boolean rememberMe;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getShorterDisplayName() {
        return truncateStringToLeft(displayName, DISPLAYNAME_MAX_LENGTH, CHARSET_UTF8);
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getShorterFirstName() {
        return truncateStringToLeft(firstName, FIRSTNAME_MAX_LENGTH, CHARSET_UTF8);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getShorterLastName() {
        return truncateStringToLeft(lastName, LASTNAME_MAX_LENGTH, CHARSET_UTF8);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isSysAdmin() {
        return privilege != null && privilege == 0;
    }

    public boolean isMediaAdmin() {
        return privilege != null && (privilege == 0 || privilege == 1);
    }

    public int getPrivilege() {
        return privilege;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    @Override
    public String toString() {
        return "LDAPUser{" +
                "userId='" + userId + '\'' +
                ", displayName='" + displayName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", privilege=" + privilege + '\'' +
                ", isSysAdmin='" + isSysAdmin() + '\'' +
                ", isMediaAdmin='" + isMediaAdmin() + '\'' +
                ", rememberMe=" + rememberMe +
                '}';
    }

    /**
     * バイト数分の文字列を左側に切り詰める
     * @param targetStr 対象文字列
     * @param maxByte バイト数
     * @param charset 文字コード
     * @return バイト数分の文字列
     */
    private String truncateStringToLeft(String targetStr, int maxByte, String charset) {
        StringBuilder sb = new StringBuilder();
        int bytelength = 0;
        try {
            for (int i = 0; i < targetStr.length(); i++) {
                String tmpStr = targetStr.substring(i, i + 1);
                byte[] bytes = tmpStr.getBytes(charset);
                if (bytelength + bytes.length > maxByte) {
                    return sb.toString();
                } else {
                    sb.append(tmpStr);
                    bytelength += bytes.length;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
