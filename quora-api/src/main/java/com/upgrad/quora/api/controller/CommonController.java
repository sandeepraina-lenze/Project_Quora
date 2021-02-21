package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.UserDetailsResponse;
import com.upgrad.quora.service.business.CommonBussinessService;
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
public class CommonController {
    @Autowired
    CommonBussinessService commonBussinessService;

    @RequestMapping(method = RequestMethod.GET, path = "/userprofile/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDetailsResponse> userProfile(@PathVariable("userId") final String userId, @RequestHeader("authorization") final String accessToken) throws AuthorizationFailedException, UserNotFoundException {
         UserEntity userEntity = commonBussinessService.UserProfileEntity(userId, accessToken);

        UserDetailsResponse userDetailsResponse = new UserDetailsResponse().userName(userEntity.getUsername())
                .aboutMe(userEntity.getAboutme())
                .contactNumber(userEntity.getContactnumber())
                .country(userEntity.getCountry())
                .dob(userEntity.getDob())
                .emailAddress(userEntity.getEmail())
                .firstName(userEntity.getFirstname())
                .lastName(userEntity.getLastname());

        return new ResponseEntity<UserDetailsResponse>(userDetailsResponse, HttpStatus.OK);
    }
}
