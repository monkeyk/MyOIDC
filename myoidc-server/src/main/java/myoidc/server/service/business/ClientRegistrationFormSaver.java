package myoidc.server.service.business;

import myoidc.server.Constants;
import myoidc.server.domain.oauth.OauthClientDetails;
import myoidc.server.domain.oauth.OauthRepository;
import myoidc.server.domain.shared.BeanProvider;
import myoidc.server.domain.user.Privilege;
import myoidc.server.infrastructure.PasswordHandler;
import myoidc.server.infrastructure.oidc.OIDCUseScene;
import myoidc.server.infrastructure.oidc.OIDCUtils;
import myoidc.server.service.dto.ClientRegistrationFormDto;
import myoidc.server.service.dto.OauthClientDetailsDto;
import myoidc.server.web.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.jose4j.jwt.JwtClaims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 2020/4/1
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
public class ClientRegistrationFormSaver {


    private static final Logger LOG = LoggerFactory.getLogger(ClientRegistrationFormSaver.class);

    private OauthRepository oauthRepository = BeanProvider.getBean(OauthRepository.class);


    private ClientRegistrationFormDto formDto;

    public ClientRegistrationFormSaver(ClientRegistrationFormDto formDto) {
        this.formDto = formDto;
    }


    public OauthClientDetailsDto save() {

        OauthClientDetails clientDetails = createDomain();
        String clientSecret = OIDCUtils.generateClientSecret();
        // encrypted client secret
        clientDetails.clientSecret(PasswordHandler.encode(clientSecret));

        oauthRepository.saveOauthClientDetails(clientDetails);
        LOG.debug("{}|Register OauthClientDetails: {}", WebUtils.getIp(), clientDetails);

        OauthClientDetails oauthClientDetails = oauthRepository.findOauthClientDetails(clientDetails.clientId());
        OauthClientDetailsDto detailsDto = new OauthClientDetailsDto(oauthClientDetails);
        detailsDto.setClientSecret(clientSecret);
        return detailsDto;
    }


    OauthClientDetails createDomain() {
        OIDCUseScene useScene = formDto.getUseScene();
        OauthClientDetails clientDetails = new OauthClientDetails()
                .clientId(OIDCUtils.generateClientId())
                .resourceIds(Constants.RESOURCE_ID)
                .authorizedGrantTypes(StringUtils.join(useScene.grantTypes(), ","));


        //判断scope
        if (useScene.isServer()) {
            clientDetails.scope(OIDCUtils.SCOPE_READ);
        } else {
            clientDetails.scope(OIDCUtils.SCOPE_OPENID);
        }
        clientDetails.webServerRedirectUri(formDto.getWebServerRedirectUri());

        //权限默认 CLIENT
        clientDetails.authorities(Privilege.CLIENT.name());

        //固定值
        clientDetails.accessTokenValidity(OIDCUtils.ACCESS_TOKEN_VALIDITY)
                .refreshTokenValidity(OIDCUtils.REFRESH_TOKEN_VALIDITY)
                .trusted(false);

        JwtClaims claims = new JwtClaims();
        claims.setStringClaim("appName", formDto.getAppName());
        clientDetails.additionalInformation(claims.toJson());

        return clientDetails;
    }
}
