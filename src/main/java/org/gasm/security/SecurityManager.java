package org.gasm.security;

/**
 * Created with IntelliJ IDEA.
 * User: Samuel
 * Date: 02/09/14
 * Time: 11:54
 * To change this template use File | Settings | File Templates.
 */
public interface SecurityManager {

    /**
     * Get the securityKey for the given login/password
     * @param login
     * @param password
     * @return the securityKey for the given login/password. Null if login password didn't match
     */
    public String getSecurityKey(String login, String password);

    /**
     *
     * @param securityKey
     * @return true if security key is valid
     */
    public boolean isValid(String securityKey);
}
