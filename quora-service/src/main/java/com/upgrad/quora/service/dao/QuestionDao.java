package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.QuestionEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class QuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * This method helps to create new question
     *
     * @param questionEntity question entity to be persisted
     * @return persisted question entity
     */
    public QuestionEntity createQuestion(QuestionEntity questionEntity) {
        entityManager.persist(questionEntity);
        return questionEntity;
    }

    /**
     * This method helps to retrieve question by id
     *
     * @param questionId id of the question to be retrieved
     * @return question entity
     */
    public QuestionEntity getQuestionById(final String questionId) {
        try {
            return entityManager.createNamedQuery("questionEntityById", QuestionEntity.class).setParameter("uuid", questionId).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
