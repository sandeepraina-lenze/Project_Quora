package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.AnswerRequest;
import com.upgrad.quora.api.model.AnswerResponse;
import com.upgrad.quora.service.business.AnswerBusinessService;
import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/")
public class AnswerController {
    @Autowired
    private AnswerBusinessService answerBusinessService;

    @Autowired
    private QuestionDao questionDao;

    @RequestMapping(method = RequestMethod.POST, path = "/question/{questionId}/answer/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerResponse> createAnswer(final AnswerRequest answerRequest, @RequestHeader("authorization") final String authorization, @PathVariable("questionId") final long questionId) throws InvalidQuestionException, AuthorizationFailedException {
        final AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setUuid(UUID.randomUUID().toString());
        answerEntity.setAns(answerRequest.getAnswer());

        final AnswerEntity createdAnswerEntity = answerBusinessService.createAnswer(answerEntity, authorization, questionId);
        AnswerResponse answerResponse =  new AnswerResponse().id(createdAnswerEntity.getUuid()).status("ANSWER CREATED");

        return new ResponseEntity<AnswerResponse>(answerResponse, HttpStatus.OK);
    }
}
