package myoidc.server.service.impl;

import myoidc.server.domain.oauth.OauthClientDetails;
import myoidc.server.domain.oauth.OauthRepository;
import myoidc.server.service.OauthService;
import myoidc.server.service.business.OAuthClientDetailsSaver;
import myoidc.server.service.dto.OauthClientDetailsDto;
import myoidc.server.web.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 2018/3/24
 * <p>
 * from <a href="https://gitee.com/shengzhao/spring-oauth-server">spring-oauth-server</a>
 *
 * @author Shengzhao Li
 */
@Service("oauthService")
public class OauthServiceImpl implements OauthService {

    private static final Logger LOG = LoggerFactory.getLogger(OauthServiceImpl.class);


    @Autowired
    private OauthRepository oauthRepository;


    @Transactional(readOnly = true)
    @Override
    public OauthClientDetails loadOauthClientDetails(String clientId) {
        return oauthRepository.findOauthClientDetails(clientId);
    }


    @Transactional()
    @Override
    public void archiveOauthClientDetails(String clientId) {
        int i = oauthRepository.updateOauthClientDetailsArchive(clientId, true);
        LOG.debug("{}|Archived client: {}, {}", WebUtils.getIp(), clientId, i);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OauthClientDetailsDto> loadOauthClientDetailsDtos(String clientId) {
        //暂时不加分页
        List<OauthClientDetails> clientDetailses = oauthRepository.findAllOauthClientDetails(clientId);
        return OauthClientDetailsDto.toDtos(clientDetailses);
    }

    @Transactional()
    @Override
    public String saveOAuthClientDetails(OauthClientDetailsDto formDto) {
        OAuthClientDetailsSaver saver = new OAuthClientDetailsSaver(formDto);
        return saver.save();
    }

    @Transactional(readOnly = true)
    @Override
    public OauthClientDetailsDto loadOauthClientDetailsDto(String clientId) {
        final OauthClientDetails oauthClientDetails = oauthRepository.findOauthClientDetails(clientId);
        return oauthClientDetails != null ? new OauthClientDetailsDto(oauthClientDetails) : null;
    }
}
