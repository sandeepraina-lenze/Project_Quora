package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthenticationFailedException;
import com.upgrad.quora.service.exception.SignOutRestrictedException;
import com.upgrad.quora.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class UserBusinessService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordCryptographyProvider CryptographyProvider;

    /**
     * This method helps to signin a user
     *
     * @param username username of a user
     * @param password password of a user
     * @return authorized user entity
     * @throws AuthenticationFailedException if username does not exist or password provided does not match
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public UserAuthEntity signIn(final String username, final String password) throws AuthenticationFailedException {
        UserEntity userEntity = userDao.getUserByName(username);
        if (userEntity == null) {
            throw new AuthenticationFailedException("ATH-001", "This username does not exist");
        }

        final String encryptedPassword = CryptographyProvider.encrypt(password, userEntity.getSalt());
        if (encryptedPassword.equals(userEntity.getPassword())) {
            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedPassword);
            UserAuthEntity userAuthTokenEntity = new UserAuthEntity();
            userAuthTokenEntity.setUser(userEntity);
            final ZonedDateTime now = ZonedDateTime.now();
            final ZonedDateTime expiresAt = now.plusHours(8);

            userAuthTokenEntity.setAccessToken(jwtTokenProvider.generateToken(userEntity.getUuid(), now, expiresAt));
            userAuthTokenEntity.setUuid(userEntity.getUuid());
            userAuthTokenEntity.setLogin_at(now);
            userAuthTokenEntity.setExpires_at(expiresAt);

            userDao.createAuthToken(userAuthTokenEntity);

            userDao.updateUser(userEntity);
            return userAuthTokenEntity;
        } else {
            throw new AuthenticationFailedException("ATH-002", "Password failed");
        }
    }

    /**
     * This method helps to signout a user
     *
     * @param accesstoken access token of the signed in user
     * @return updated authorized user entity
     * @throws SignOutRestrictedException if user is not signed in
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public UserAuthEntity signOut(final String accessToken) throws SignOutRestrictedException {
        UserAuthEntity userAuthEntity = userDao.getUserByAccessToken(accessToken);

        if (userAuthEntity == null) {
            throw new SignOutRestrictedException("SGR-001", "User is not Signed in");
        }

        final ZonedDateTime now = ZonedDateTime.now();

        userAuthEntity.setLogout_at(now);
        userDao.updateUserAuth(userAuthEntity);
        return userAuthEntity;
    }

    /**
     * This method helps to signup a new user
     *
     * @param userEntity details of the user
     * @return created user entity
     * @throws SignUpRestrictedException if username or email id already exists
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity signUp(UserEntity userEntity) throws SignUpRestrictedException {
        UserEntity isUserNameAvailable = userDao.getUserByName(userEntity.getUsername());
        UserEntity isUserEmailAvailable = userDao.getUserByEmail(userEntity.getEmail());

        if (isUserNameAvailable != null) {
            throw new SignUpRestrictedException("SGR-001", "Try any other Username, this Username has already been taken");
        }

        if (isUserEmailAvailable != null) {
            throw new SignUpRestrictedException("SGR-002", "This user has already been registered, try with any other emailId");
        }

        String[] encryptedText = CryptographyProvider.encrypt(userEntity.getPassword());
        userEntity.setSalt(encryptedText[0]);
        userEntity.setPassword(encryptedText[1]);

        return userDao.createUser(userEntity);
    }
}