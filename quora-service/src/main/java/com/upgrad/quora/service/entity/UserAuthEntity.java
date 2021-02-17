package com.upgrad.quora.service.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
@Table(name = "USER_AUTH", schema = "quora")
@NamedQueries({
        @NamedQuery(name = "userAuthByAccessToken", query = "select ut from UserAuthEntity ut where ut.accessToken =:accessToken")
})
public class UserAuthEntity implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "UUID")
    @Size(max = 200)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    @Column(name = "ACCESS_TOKEN")
    @NotNull
    @Size(max = 500)
    private String accessToken;

    @Column(name = "EXPIRES_AT")
    @NotNull
    private OffsetDateTime expires_at;

    @Column(name = "LOGIN_AT")
    @NotNull
    private OffsetDateTime login_at;

    @Column(name = "LOGOUT_AT")
    private OffsetDateTime   logout_at;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public OffsetDateTime getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(OffsetDateTime expires_at) {
        this.expires_at = expires_at;
    }

    public OffsetDateTime getLogin_at() {
        return login_at;
    }

    public void setLogin_at(OffsetDateTime login_at) {
        this.login_at = login_at;
    }

    public OffsetDateTime getLogout_at() {
        return logout_at;
    }

    public void setLogout_at(OffsetDateTime logout_at) {
        this.logout_at = logout_at;
    }

    @Override
    public boolean equals(Object obj) {
        return new EqualsBuilder().append(this, obj).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this).hashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}