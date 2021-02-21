package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.UserDeleteResponse;
import com.upgrad.quora.service.business.AdminBusinessService;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class AdminController {
    @Autowired
    AdminBusinessService adminBusinessService;

    /**
     * This api endpoint is used to delete a user
     *
     * @RequestHeader accessToken - access token of the signed in user in authorization request header
     * @PathVariable userId - uuid of the corresponding user
     *
     * @return JSON response with user uuid and message
     *
     * @throws AuthorizationFailedException if user is not signed-in or user is signed out or user role is non-admin
     * @throws UserNotFoundException if user does not exist
     * */
    @RequestMapping(method = RequestMethod.DELETE, path = "/admin/user/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDeleteResponse> userDelete(@PathVariable("userId") final String userId, @RequestHeader("authorization") final String accessToken) throws AuthorizationFailedException, UserNotFoundException {
        UserEntity userEntity = adminBusinessService.UserDelete(userId, accessToken);
        UserDeleteResponse userDeleteResponse = new UserDeleteResponse().id(userEntity.getUuid()).status("USER SUCCESSFULLY DELETED");

        return new ResponseEntity<UserDeleteResponse>(userDeleteResponse, HttpStatus.OK);
    }
}
