package org.gasm.matos.rest.json;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.gasm.security.SecurityManager;
import org.gasm.security.SecurityManagerHashMapImpl;

/**
 * Created with IntelliJ IDEA.
 * User: Samuel
 * Date: 02/09/14
 * Time: 12:12
 * To change this template use File | Settings | File Templates.
 */
@Path("/securityManager")
public class SecurityManagerAPI implements SecurityManager {

    private SecurityManager delegate;

    public SecurityManagerAPI() {
        delegate = SecurityManagerHashMapImpl.getInstance();
    }

    @Path("getSecurityKey")
    @Override
    @GET
    public String getSecurityKey(@QueryParam("login") String login, @QueryParam("password") String password) {
        return delegate.getSecurityKey(login,password);
    }

    @Override
    public boolean isValid(String securityKey) {
        return delegate.isValid(securityKey);
    }
}
