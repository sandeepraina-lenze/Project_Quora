package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.AnswerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AnswerDao {
    @PersistenceContext
    private EntityManager entityManager;
// create Answer for a question
    public AnswerEntity createAnswer(AnswerEntity answerEntity) {
        entityManager.persist(answerEntity);
        return answerEntity;
    }

    //fetch all the answers to the question using questionId of question
    public List<AnswerEntity> getAllAnswersToQuestion(final String questionId) {
        return entityManager.createNamedQuery("getAllAnswersToQuestion", AnswerEntity.class).getResultList();
    }
}
