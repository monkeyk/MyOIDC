package myoidc.server.service.impl;

import myoidc.server.domain.oauth.OauthClientDetails;
import myoidc.server.domain.oauth.OauthRepository;
import myoidc.server.service.OauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 2018/3/24
 * <p>
 * from <a href="https://gitee.com/shengzhao/spring-oauth-server">spring-oauth-server</a>
 *
 * @author Shengzhao Li
 */
@Service("oauthService")
public class OauthServiceImpl implements OauthService {


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
        oauthRepository.updateOauthClientDetailsArchive(clientId, true);
    }
}
