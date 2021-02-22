package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * This method helps to create a new user
     *
     * @param userEntity details of a new user
     * @return user Entity
     */
    public UserEntity createUser(UserEntity userEntity) {
        entityManager.persist(userEntity);
        return userEntity;
    }

    /**
     * This method helps to get user by name
     *
     * @param username name of the user
     * @return user Entity
     */
    public UserEntity getUserByName(final String username) {
        try {
            return entityManager.createNamedQuery("userByName", UserEntity.class).setParameter("username", username).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * This method helps to get user by uuid
     *
     * @param uuid uuid of the user
     * @return user Entity
     */
    public UserEntity getUserByUuid(final String uuid) {
        try {
            return entityManager.createNamedQuery("userByUuid", UserEntity.class).setParameter("uuid", uuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * This method helps to get user by email
     *
     * @param email email of the user
     * @return user Entity
     */
    public UserEntity getUserByEmail(final String email) {
        try {
            return entityManager.createNamedQuery("userByEmail", UserEntity.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * This method helps to add a authorized user
     *
     * @param userAuthEntity details of the authorized user
     * @return User Auth Entity
     */
    public UserAuthEntity createAuthToken(final UserAuthEntity userAuthEntity) {
        entityManager.persist(userAuthEntity);
        return userAuthEntity;
    }

    /**
     * This method helps to get authorized user by access toekn
     *
     * @param accessToken access token of the signed in user
     * @return User Auth Entity
     */
    public UserAuthEntity getUserByAccessToken(final String accessToken) {
        try {
            return entityManager.createNamedQuery("userAuthByAccessToken", UserAuthEntity.class).setParameter("accessToken", accessToken).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * This method helps to update the existing user details
     *
     * @param updatedUserEntity updated details of the existing user
     *
     */
    public void updateUser(final UserEntity updatedUserEntity) {
        entityManager.merge(updatedUserEntity);
    }

    /**
     * This method helps to update the existing authorized user details
     *
     * @param updatedUserAuthEntity updated details of the existing authorized user
     *
     */
    public void updateUserAuth(final UserAuthEntity updatedUserAuthEntity) {
        entityManager.merge(updatedUserAuthEntity);
    }

    /**
     * This method helps to delete the existing user
     *
     * @param deleteUserEntity details of the existing user
     *
     */
    public void userDelete(final UserEntity deleteUserEntity) {
        entityManager.remove(deleteUserEntity);
    }
}