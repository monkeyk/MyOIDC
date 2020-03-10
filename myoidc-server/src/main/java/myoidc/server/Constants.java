package myoidc.server;

/**
 * 2020/3/11
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
public interface Constants {

    /*Fixed,  resource-id */
    String RESOURCE_ID = "myoidc-resource";

    /**
     * Fixed  keyId
     *
     * @since 1.1.0
     */
    String DEFAULT_KEY_ID = "myoidc-keyid";

    /**
     * 长度 至少 1024, 建议 2048
     *
     * @since 1.1.0
     */
    int DEFAULT_KEY_SIZE = 2048;


    /**
     * keystore file name
     *
     * @since 1.1.0
     */
    String KEYSTORE_NAME = "keystore.json";


    //系统字符编码
    public static final String ENCODING = "UTF-8";

    public static final String PROJECT_HOME = "https://github.com/monkeyk/MyOIDC";

    //Current  version
    public static final String VERSION = "1.1.0";


}
