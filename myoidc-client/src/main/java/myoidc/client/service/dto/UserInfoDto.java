package myoidc.client.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     {"openid":"Ajlt9ZwVyGUvxrJCdKlFA4AataAVKVgH6gxYeCxD6J","username":"mobile","phone":null,"email":"mobile@qc8.me","create_time":0,"privileges":["USER","ADMIN","UNITY","MOBILE"]}
 * </pre>
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
public class UserInfoDto extends AbstractOauthDto {


    private static final long serialVersionUID = -4028887815415395655L;

    @JsonProperty("create_time")
    private long createTime;
    private String email;
    private String openid;

    private String phone;
    private String username;

    private List<String> privileges = new ArrayList<>();


    public UserInfoDto() {
    }

    public UserInfoDto(String error, String errorDescription) {
        this.error = error;
        this.errorDescription = errorDescription;
    }


    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<String> privileges) {
        this.privileges = privileges;
    }
}