package com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.clientproxy;

import com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.model.User;

import javax.ws.rs.*;
import java.util.List;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

public interface UserResourceV1 {

    @GET
    @Produces(APPLICATION_JSON)
    List<User> fetchUsers(@QueryParam("gender") String gender);

    @GET
    @Produces(APPLICATION_JSON)
    @Path("{userUid}")
    User fetchUser(@PathParam("userUid") UUID userUid);

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    void insertNewUser(User user);

    @PUT
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    void updateUser(User user);

    @DELETE
    @Produces(APPLICATION_JSON)
    @Path("{userUid}")
    void deleteUser(@PathParam("userUid") UUID userUid);
}
