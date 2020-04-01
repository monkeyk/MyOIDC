package myoidc.server.service.dto;

import myoidc.server.infrastructure.oidc.OIDCUseScene;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 2020/4/1
 * <p>
 * <p>
 * 客户端 注册 DTO
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
public class ClientRegistrationFormDto implements Serializable {
    private static final long serialVersionUID = -6634612576542523523L;


    //应用名称
    @NotBlank(message = "应用名称不能为空")
    @Size(min = 1, max = 20, message = "应用名称最多20字符")
    private String appName;


    @NotBlank(message = "redirect_uri不能为空")
    @URL(message = "redirect_uri格式错误")
    private String webServerRedirectUri;


    //场景
    @NotNull(message = "useScene is required")
    private OIDCUseScene useScene;


    public ClientRegistrationFormDto() {
    }

    public ClientRegistrationFormDto(OIDCUseScene useScene) {
        this.useScene = useScene;
    }


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getWebServerRedirectUri() {
        return webServerRedirectUri;
    }

    public void setWebServerRedirectUri(String webServerRedirectUri) {
        this.webServerRedirectUri = webServerRedirectUri;
    }

    public OIDCUseScene getUseScene() {
        return useScene;
    }

    public void setUseScene(OIDCUseScene useScene) {
        this.useScene = useScene;
    }
}
