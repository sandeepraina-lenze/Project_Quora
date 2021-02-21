package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.SignoutResponse;
import com.upgrad.quora.service.business.SignoutBusinessService;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.SignOutRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SignOutController {
    @Autowired
    private SignoutBusinessService signoutBusinessService;

    @RequestMapping(method = RequestMethod.POST, path = "/user/signout", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignoutResponse> logout(@RequestHeader("authorization") final String accessToken) throws SignOutRestrictedException {

        UserAuthEntity userAuthEntity = signoutBusinessService.signout(accessToken);
        UserEntity userEntity = userAuthEntity.getUser();

        SignoutResponse signoutResponse = new SignoutResponse().id(userEntity.getUuid())
                .message("SIGNED OUT SUCCESSFULLY");
        return new ResponseEntity<SignoutResponse>(signoutResponse, HttpStatus.OK);
    }
}
