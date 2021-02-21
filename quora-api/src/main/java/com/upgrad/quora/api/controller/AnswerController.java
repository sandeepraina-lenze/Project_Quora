package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.*;
import com.upgrad.quora.service.business.AnswerBusinessService;
import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.exception.AnswerNotFoundException;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class AnswerController {
    @Autowired
    private AnswerBusinessService answerBusinessService;
    @Autowired
    private QuestionDao questionDao;

    @RequestMapping(method = RequestMethod.POST, path = "/question/{questionId}/answer/create", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerResponse> createAnswer(final AnswerRequest answerRequest, @RequestHeader("authorization") final String authorization, @PathVariable("questionId") final String questionId) throws InvalidQuestionException, AuthorizationFailedException {

        final AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setUuid(UUID.randomUUID().toString());
        answerEntity.setAns(answerRequest.getAnswer());
        final AnswerEntity createdAnswerEntity = answerBusinessService.createAnswer(answerEntity, authorization, questionId);
        AnswerResponse answerResponse = new AnswerResponse().id(createdAnswerEntity.getUuid()).status("ANSWER CREATED");

        return new ResponseEntity<AnswerResponse>(answerResponse, HttpStatus.OK);
    }

    /**
     * getAllAnswersToQuestion - Get all answer to the question with given question id.
     *
     * @param questionId    - questionId for which answers should be fetched from database.
     * @param authorization - authorization access  token of user
     * @return response entity of list of answer details response
     * @throws AuthorizationFailedException throws when authorization failed,
     *                                      ATHR-001 - if User has not signed in. ATHR-002 if the User is signed out.
     * @throws InvalidQuestionException     throws when question is not available
     */

    @RequestMapping(method = RequestMethod.GET, path = "/answer/all/{questionId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<AnswerDetailsResponse>> getAllAnswersToQuestion(@PathVariable("questionId") String questionId, @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, InvalidQuestionException {
        List<AnswerEntity> answers = answerBusinessService.getAllAnswersToQuestion(questionId, authorization);
        List<AnswerDetailsResponse> answerDetailsResponses = new ArrayList<>();

        QuestionEntity questionEntity = questionDao.getQuestionById(questionId);
        for (AnswerEntity answerEntity : answers) {
            if (answerEntity.getQuestion().equals(questionDao.getQuestionById(questionId))) {
                AnswerDetailsResponse answerDetailsResponse = new AnswerDetailsResponse();
                answerDetailsResponse.setId(answerEntity.getUuid());
                answerDetailsResponse.setQuestionContent(answerEntity.getQuestion().getContent());
                answerDetailsResponse.setAnswerContent(answerEntity.getAns());
                answerDetailsResponses.add(answerDetailsResponse);
            }
        }
        return new ResponseEntity<List<AnswerDetailsResponse>>(answerDetailsResponses, HttpStatus.OK);
    }
    //delete a answer using answerId


    @RequestMapping(method = RequestMethod.DELETE, path = "/answer/delete/{answerId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerDeleteResponse> deleteAnswer(@PathVariable("answerId") String answerId, @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, AnswerNotFoundException {
        AnswerEntity answerEntity = answerBusinessService.deleteAnswer(answerId, authorization);
        AnswerDeleteResponse answerDeleteResponse = new AnswerDeleteResponse().id(answerEntity.getUuid()).status("ANSWER DELETED");
        return new ResponseEntity<AnswerDeleteResponse>(answerDeleteResponse, HttpStatus.OK);
    }

    /**
     * edit a answer using answerId
     *
     * @throws AuthorizationFailedException In case the access token is invalid.
     * @throws AnswerNotFoundException      if answer with answerId doesn't exist.
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/answer/edit/{answerId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerEditResponse> editAnswerContent(final AnswerEditRequest answerEditRequest, @RequestHeader("authorization") final String authorization, @PathVariable("answerId") final String answerId) throws AuthorizationFailedException, AnswerNotFoundException {
        AnswerEntity answerEntity = answerBusinessService.editAnswer(authorization, answerId, answerEditRequest.getContent());
        AnswerEditResponse answerEditResponse = new AnswerEditResponse().id(answerEntity.getUuid()).status("ANSWER EDITED");

        return new ResponseEntity<AnswerEditResponse>(answerEditResponse, HttpStatus.OK);
    }
}
