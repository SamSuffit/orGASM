package org.gasm.matos.rest.json;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import org.apache.commons.lang3.StringUtils;
import org.gasm.security.SecurityManagerHashMapImpl;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;


/**
 * Created with IntelliJ IDEA.
 * User: Samuel
 * Date: 02/09/14
 * Time: 14:27
 * To change this template use File | Settings | File Templates.
 */
@Provider
public class AuthorizationRequestFilter implements ContainerRequestFilter , ContainerResponseFilter {

    private final Logger log = Logger.getLogger(getClass().getName());

    SecurityManagerHashMapImpl securityManager = SecurityManagerHashMapImpl.getInstance();

    @Override
    public ContainerRequest filter(ContainerRequest requestContext) {
        if(!StringUtils.startsWith(requestContext.getPath(),"securityManager")) {

            if(!securityManager.isValid(requestContext.getQueryParameters().getFirst("securityKey"))) {
                Response.ResponseBuilder builder = null;
                String response = "UNAUTHORIZED Access";
                builder = Response.status(Response.Status.UNAUTHORIZED).entity(response);
                throw new WebApplicationException(builder.build());
             }
        }
        return requestContext;
    }

    @Override
    public ContainerResponse filter(ContainerRequest containerRequest, ContainerResponse containerResponse) {
        log.fine("Origin: " + containerRequest.getHeaderValue("Origin") + " - " + containerRequest.getRequestUri());
        if(StringUtils.equals("http://localhost:8000", containerRequest.getHeaderValue("Origin"))) {
            containerResponse.getHttpHeaders().add("Access-Control-Allow-Origin", containerRequest.getHeaderValue("Origin"));
        }
        return containerResponse;
    }
}