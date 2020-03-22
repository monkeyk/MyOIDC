package myoidc.server.service.dto;


import myoidc.server.domain.user.Privilege;
import myoidc.server.domain.user.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 2016/3/12
 * * From spring-oauth-server
 *
 * @author Shengzhao Li
 */
public class UserDto implements Serializable {
    private static final long serialVersionUID = -2502329463915439215L;


    private String uuid;

    private String username;

    private String phone;
    private String email;

    private String createTime;

    private List<Privilege> privileges = new ArrayList<>();

    private boolean defaultUser = false;

    private String lastLoginTime;

    private String creatorName;

    public UserDto() {
    }


    public UserDto(User user) {
        this.uuid = user.uuid();
        this.username = user.username();
        this.phone = user.phone();
        this.email = user.email();

        this.privileges = user.privileges();
        this.createTime = user.createTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.defaultUser = user.defaultUser();

        LocalDateTime lastLoginTime = user.lastLoginTime();
        if (lastLoginTime != null) {
            this.lastLoginTime = lastLoginTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
        User creator = user.creator();
        if (creator != null) {
            this.createTime = creator.username();
        }
    }

    public boolean isDefaultUser() {
        return defaultUser;
    }

    public void setDefaultUser(boolean defaultUser) {
        this.defaultUser = defaultUser;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }

    public static List<UserDto> toDtos(List<User> users) {
        List<UserDto> dtos = new ArrayList<>(users.size());
        for (User user : users) {
            dtos.add(new UserDto(user));
        }
        return dtos;
    }
}
