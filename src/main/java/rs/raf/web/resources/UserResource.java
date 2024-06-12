package rs.raf.web.resources;

import rs.raf.web.entities.Destination;
import rs.raf.web.entities.User;
import rs.raf.web.requests.EditInfoUser;
import rs.raf.web.requests.LoginRequest;
import rs.raf.web.requests.UserEdit;
import rs.raf.web.requests.UserNew;
import rs.raf.web.services.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/users")
public class UserResource {
    @Inject
    private UserService userService;

    @POST
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(@Valid LoginRequest loginRequest)
    {
        Map<String, String> response = new HashMap<>();

        String jwt = this.userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (jwt == null || !userService.isAuthorized(jwt)) {
            response.put("message", "These credentials do not match our records");
            return Response
                    .status(422, "Unprocessable Entity")
                    .entity(response)
                    .build();
        }

        response.put("jwt", jwt);

        return Response
                .ok(response)
                .build();
    }
    @GET
    @Path("/usersPP/{page}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPerPage(@PathParam("page") int page){

        List<User> users = this.userService.getPerPage(page);
        return Response
                .ok(users)
                .build();
    }
    @POST
    @Path("/addNew")
    @Produces({MediaType.APPLICATION_JSON})
    public Response addNew(@Valid UserNew user){

        boolean okej = this.userService.addNew(user);
        return Response
                .ok(Map.of("ok",okej))
                .build();
    }
    @PUT
    @Path("/editStatus")
    @Produces({MediaType.APPLICATION_JSON})
    public Response editStatus(@Valid UserEdit userEdit){

        this.userService.editUser(userEdit);
        return Response
                .ok()
                .build();
    }
    @PUT
    @Path("/editInfo")
    @Produces({MediaType.APPLICATION_JSON})
    public Response editInfoUser(@Valid EditInfoUser editInfoUser){

        this.userService.editInfoUser(editInfoUser);
        return Response
                .ok()
                .build();
    }
}
