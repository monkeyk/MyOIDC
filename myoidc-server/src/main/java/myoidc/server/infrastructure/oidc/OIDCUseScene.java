package myoidc.server.infrastructure.oidc;

/**
 * 2020/3/31
 * <p>
 * OIDC具体的使用场景
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
public enum OIDCUseScene {

    WEB(OIDCUtils.GrantType.AUTHORIZATION_CODE, OIDCUtils.GrantType.REFRESH_TOKEN),   //浏览器
    CELL_PHONE(OIDCUtils.GrantType.PASSWORD, OIDCUtils.GrantType.REFRESH_TOKEN),   //移动设备(手机)
    DEVICE_CLIENT(OIDCUtils.GrantType.PASSWORD, OIDCUtils.GrantType.REFRESH_TOKEN),  // 其他设备
    SERVER(OIDCUtils.GrantType.CLIENT_CREDENTIALS);   //服务端


    /**
     * 每种场景对应的 grant_types
     */
    private OIDCUtils.GrantType[] grantTypes;

    OIDCUseScene(OIDCUtils.GrantType... grantTypes) {
        this.grantTypes = grantTypes;
    }


    public OIDCUtils.GrantType[] grantTypes() {
        return this.grantTypes;
    }

    public boolean isServer() {
        return SERVER.equals(this);
    }

    public boolean isDeviceClient() {
        return DEVICE_CLIENT.equals(this);
    }
}
