package myoidc.server.domain.user;


import myoidc.server.domain.AbstractDomain;
import myoidc.server.domain.shared.BeanProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author Shengzhao Li
 */
@Entity
@Table(name = "user_")
public class User extends AbstractDomain {


    private static final long serialVersionUID = -7873396364481790308L;

    private static final Logger LOG = LoggerFactory.getLogger(User.class);

    private transient UserRepository userRepository = BeanProvider.getBean(UserRepository.class);

    @Column(name = "username", unique = true)
    private String username;


    /**
     * Encrypted
     */
    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    //Default user is initial when create database, do not delete
    @Column(name = "default_user")
    private boolean defaultUser = false;

    @Column(name = "last_login_time")
    private Date lastLoginTime;

    /*
    * Register is null; otherwise is current logged user
    * */
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;


    public User() {
    }


    public boolean registerUser() {
        return !defaultUser && creator == null;
    }

    public User creator() {
        return creator;
    }

    public User creator(User creator) {
        this.creator = creator;
        return this;
    }

    public List<Privilege> privileges() {
        return userRepository.findUserPrivileges(this);
    }

    public boolean defaultUser() {
        return defaultUser;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public String phone() {
        return phone;
    }

    public String email() {
        return email;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{username='").append(username).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", uuid='").append(uuid).append('\'');
        sb.append(", defaultUser='").append(defaultUser).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public User email(String email) {
        this.email = email;
        return this;
    }

    public User phone(String phone) {
        this.phone = phone;
        return this;
    }


    public User username(String username) {
        this.username = username;
        return this;
    }

//    public String resetPassword() {
//        String newOriginalPass = PasswordHandler.randomPassword();
//        this.password = PasswordHandler.encryptPassword(newOriginalPass);
//        LOG.debug("<{}> reset User [username={},guid={}] password", SecurityUtils.currentUsername(), username, guid);
//        return newOriginalPass;
//    }


    public Date lastLoginTime() {
        return lastLoginTime;
    }

    public void lastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

//    public User deleteMe() {
//        this.archived(true);
//        LOG.debug("<{}> delete User: {} [Logic delete]", SecurityUtils.currentUsername(), this);
//        return this;
//    }
//
//    public User updatePassword(String newPassword) {
//        this.password = PasswordHandler.encryptPassword(newPassword);
//        LOG.debug("<{}> update user[{}] password", SecurityUtils.currentUsername(), this);
//        return this;
//    }
}