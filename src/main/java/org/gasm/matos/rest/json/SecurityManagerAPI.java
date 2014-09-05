package org.gasm.matos.rest.json;

import org.gasm.security.SecurityManager;
import org.gasm.security.SecurityManagerHashMapImpl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Created with IntelliJ IDEA.
 * User: Samuel
 * Date: 02/09/14
 * Time: 12:12
 * To change this template use File | Settings | File Templates.
 */
@Path("/securityManager")
public class SecurityManagerAPI implements SecurityManager {

    private SecurityManager securityManager;

    public SecurityManagerAPI() {
        securityManager = SecurityManagerHashMapImpl.getInstance();
    }

    @Path("getSecurityKey")
    @Override
    @GET
    public String getSecurityKey(@QueryParam("login") String login, @QueryParam("password") String password) {
        return securityManager.getSecurityKey(login,password);
    }

    @Path("isValid")
    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public boolean isValid(@QueryParam("securityKey") String securityKey) {
        return securityManager.isValid(securityKey);
    }
}
