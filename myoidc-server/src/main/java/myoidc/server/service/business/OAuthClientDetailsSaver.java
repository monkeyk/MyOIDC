package myoidc.server.service.business;

import myoidc.server.domain.oauth.OauthClientDetails;
import myoidc.server.domain.oauth.OauthRepository;
import myoidc.server.domain.shared.BeanProvider;
import myoidc.server.service.dto.OauthClientDetailsDto;
import myoidc.server.web.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 2020/3/21
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
public class OAuthClientDetailsSaver {


    private static final Logger LOG = LoggerFactory.getLogger(OAuthClientDetailsSaver.class);

    private OauthRepository oauthRepository = BeanProvider.getBean(OauthRepository.class);

    private OauthClientDetailsDto formDto;

    public OAuthClientDetailsSaver(OauthClientDetailsDto formDto) {
        this.formDto = formDto;
    }

    public String save() {
        OauthClientDetails clientDetails = formDto.createDomain();
        oauthRepository.saveOauthClientDetails(clientDetails);
        LOG.debug("{}|Save OauthClientDetails: {}", WebUtils.getIp(), clientDetails);
        return clientDetails.clientId();
    }
}
