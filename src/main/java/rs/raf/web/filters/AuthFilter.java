package rs.raf.web.filters;


import rs.raf.web.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Provider
public class AuthFilter implements ContainerRequestFilter {

    @Inject
    UserService userService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        //samo za cors
        if(requestContext.getMethod().equalsIgnoreCase("options"))
            return;

        boolean adminRequired = authCheck(requestContext);
        boolean authRequired = adminRequired || isAuthRequired(requestContext);
        if (!authRequired) return;

        try {
            String token = requestContext.getHeaderString("Authorization");
            if(token != null && token.startsWith("Bearer "))
                token = token.replace("Bearer ", "");

            if (!this.userService.isAuthorized(token)) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                return;
            }
            if(adminRequired && !userService.isAdmin(token))
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());

        } catch (Exception exception) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private boolean authCheck(ContainerRequestContext req) {
        List<String> need = new ArrayList<>();
        need.add("users/usersPP");
        need.add("users/addNew");
        need.add("users/editStatus");
        need.add("users/editInfo");
        for(String path: need)
            if (req.getUriInfo().getPath().startsWith(path))
                return true;
        return false;
    }

    private boolean isAuthRequired(ContainerRequestContext req) {

        List<String> need = new ArrayList<>();
        need.add("articles/addNew");
        need.add("articles/edit");
        need.add("articles/delete");
        need.add("destinations/addNew");
        need.add("destinations/edit");
        need.add("destinations/delete");

        for(String path: need)
            if (req.getUriInfo().getPath().startsWith(path))
                return true;
        return false;
    }
}
