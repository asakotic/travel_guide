package rs.raf.web.resources;

import org.modelmapper.ModelMapper;
import rs.raf.web.entities.Article;
import rs.raf.web.entities.Destination;
import rs.raf.web.entities.User;
import rs.raf.web.requests.*;
import rs.raf.web.services.DestinationService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/destinations")
public class DestinationResource {
    @Inject
    private DestinationService destinationService;

    @GET
    @Path("/allDestinations")
    @Produces({MediaType.APPLICATION_JSON})
    public Response allDestinations(){

        List<Destination> allDestinations = this.destinationService.allDestinations();
        return Response
                .ok(allDestinations)
                .build();
    }
    @GET
    @Path("/destinationsPP/{page}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPerPage(@PathParam("page") int page){

        List<Destination> destinations = this.destinationService.getPerPage(page);
        return Response
                .ok(destinations)
                .build();
    }
    @POST
    @Path("/addNew")
    @Produces({MediaType.APPLICATION_JSON})
    public Response addNew(@Valid DestinationAdd destinationAdd){

        ModelMapper modelMapper = new ModelMapper();
        Destination destination = modelMapper.map(destinationAdd, Destination.class);

        this.destinationService.addNew(destination);
        return Response
                .ok()
                .build();
    }
    @PUT
    @Path("/edit")
    @Produces({MediaType.APPLICATION_JSON})
    public Response edit(@Valid DestinationEdit destinationEdit){

        boolean okej = this.destinationService.edit(destinationEdit);
        return Response
                .ok(Map.of("ok",okej))
                .build();
    }
    @DELETE
    @Path("/delete/{destinationName}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("destinationName") String destinationName){

        boolean okej = this.destinationService.delete(destinationName);
        return Response
                .ok(Map.of("ok", okej))
                .build();
    }
}
