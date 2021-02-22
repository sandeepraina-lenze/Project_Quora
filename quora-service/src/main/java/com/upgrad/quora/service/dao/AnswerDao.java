package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.AnswerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class AnswerDao {
    @PersistenceContext
    private EntityManager entityManager;

    // create Answer for a question
    public AnswerEntity createAnswer(AnswerEntity answerEntity) {
        answerEntity.setDate(ZonedDateTime.now());
        entityManager.persist(answerEntity);
        return answerEntity;
    }

    //fetch all the answers to the question using questionId of question
    public List<AnswerEntity> getAllAnswersToQuestion(final String questionId) {
        return entityManager.createNamedQuery("getAllAnswersToQuestion", AnswerEntity.class).getResultList();
    }
    // Fetches an answer from DB based on the answerId


    public AnswerEntity getAnswerById(final String answerId) {
        try {
            return entityManager.createNamedQuery("getAnswerById", AnswerEntity.class).setParameter("uuid", answerId).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }


    /**
     * Delete a answer by given answerId from the DB.
     *
     * @return Answer details which is to be deleted if exist in the DB else null.
     */

    public AnswerEntity deleteAnswer(final String answerId) {
        AnswerEntity deleteAnswer = getAnswerById(answerId);
        if (deleteAnswer != null) {
            entityManager.remove(deleteAnswer);
        }
        return deleteAnswer;
    }

    /**
     * updates the row of information in answer table of DB.
     *
     * @param answerEntity answer to be updated.
     */
    public void updateAnswer(AnswerEntity answerEntity) {
        entityManager.merge(answerEntity);
    }
}
