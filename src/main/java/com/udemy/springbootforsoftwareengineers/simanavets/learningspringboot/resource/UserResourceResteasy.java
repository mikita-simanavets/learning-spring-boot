package com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.resource;

import com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.model.User;
import com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.ws.rs.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Component
@Validated
@Path("api/v1/users")
public class UserResourceResteasy {

    private UserService userService;

    @Autowired
    public UserResourceResteasy(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Produces(APPLICATION_JSON)
    public List<User> fetchUsers(@QueryParam("gender") String gender) {
        return userService.getAllUsers(Optional.ofNullable(gender));
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("{userUid}")
    public User fetchUser(@PathParam("userUid") UUID userUid) {
        return userService
                .getUser(userUid)
                .orElseThrow(() -> new NotFoundException("User " + userUid + " not found"));
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public void insertNewUser(@Valid User user) {
        userService.insertUser(user);
    }

    @PUT
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public void updateUser(User user) {
        userService.updateUser(user);
    }

    @DELETE
    @Produces(APPLICATION_JSON)
    @Path("{userUid}")
    public void deleteUser(@PathParam("userUid") UUID userUid) {
        userService.removeUser(userUid);
    }
}
