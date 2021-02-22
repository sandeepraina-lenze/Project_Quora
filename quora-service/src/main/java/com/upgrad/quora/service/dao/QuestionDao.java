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
    /**
     * This method helps to get all question by user
     *
     * @param uuid uuid of an user
     * @return list of question entities
     */
    public List<QuestionEntity> getAllQuestionsByUser(final String uuid){
        try {
            return entityManager.createNamedQuery("questionByUserId", QuestionEntity.class).setParameter("uuid", uuid).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * This method helps to get the question by uuid
     *
     * @param uuid uuid of question
     * @return question entity
     */
    public QuestionEntity getQuestionByUuid(final String uuid) {
        try {
            return entityManager.createNamedQuery("questionEntityById", QuestionEntity.class).setParameter("uuid",uuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * This method helps to delete the question by uuid
     *
     * @param uuid uuid of question to be deleted
     */
    public void userQuestionDelete(final String uuid) {
        QuestionEntity questionEntity = getQuestionByUuid(uuid);
        entityManager.remove(questionEntity);
    }

    /**
     * This method helps to edit the question entity content
     *
     * @param questionEntity modified question entity
     * @return persisted question entity
     */
    public QuestionEntity editQuestionContent(final QuestionEntity questionEntity) {
        return entityManager.merge(questionEntity);
    }

    /**
     * This method helps to get all questions
     *
     * @return list of question entities
     */
    public List<QuestionEntity> getAllQuestions() {
        try {
            return entityManager.createNamedQuery("allQuestions", QuestionEntity.class).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
