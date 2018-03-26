package myoidc.server.service.dto;

import java.io.Serializable;

/**
 * 2018/3/26
 *
 * @author Shengzhao Li
 */
public class OAuthResourceDto implements Serializable {
    private static final long serialVersionUID = -7266690020307910479L;


    private String resourceId;

    private String description;


    public OAuthResourceDto() {
    }

    public OAuthResourceDto(String resourceId, String description) {
        this.resourceId = resourceId;
        this.description = description;
    }


    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
