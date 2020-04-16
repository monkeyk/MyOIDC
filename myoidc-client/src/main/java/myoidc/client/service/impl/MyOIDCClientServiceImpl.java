package myoidc.client.service.impl;

import myoidc.client.domain.RPHolder;
import myoidc.client.domain.RPHolderRepository;
import myoidc.client.service.MyOIDCClientService;
import myoidc.client.service.business.OIDCTokenVerifier;
import myoidc.client.service.dto.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 2020/4/7
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
@Service
public class MyOIDCClientServiceImpl implements MyOIDCClientService {

    private static final Logger LOG = LoggerFactory.getLogger(MyOIDCClientServiceImpl.class);

    @Autowired
    private RPHolderRepository rpHolderRepository;


    @Autowired
    private RestTemplate restTemplate;


    @Override
    public RPHolder loadRPHolder() {
        return rpHolderRepository.findRPHolder();
    }

    @Override
    public boolean saveRPHolder(RPHolder rpHolder) {
        boolean saveOK = rpHolderRepository.saveRPHolder(rpHolder);
        return saveOK && rpHolder.getDiscoveryEndpointInfo() != null;
    }

    @Override
    public AuthAccessTokenDto createAuthAccessTokenDto(AuthCallbackDto callbackDto) {
        RPHolder rpHolder = loadRPHolder();
        return new AuthAccessTokenDto()
                .setAccessTokenUri(rpHolder.getDiscoveryEndpointInfo().getToken_endpoint())
                .setCode(callbackDto.getCode());
    }

    @Override
    public AccessTokenDto retrieveAccessTokenDto(AuthAccessTokenDto tokenDto) {
        final String fullUri = tokenDto.getAccessTokenUri();
        LOG.debug("Get access_token URL: {}", fullUri);

        return loadAccessTokenDto(fullUri, tokenDto.getAuthCodeParams());
    }

    @Override
    public UserInfoDto loadUserInfoDto(String accessToken) {
        LOG.debug("Load OIDC User_Info by access_token = {}", accessToken);

        if (StringUtils.isBlank(accessToken)) {
            return new UserInfoDto("Illegal 'access_token'", "'access_token' is empty");
        } else {
            RPHolder rpHolder = loadRPHolder();
            try {
                String userinfoEndpoint = rpHolder.getDiscoveryEndpointInfo().getUserinfo_endpoint();
                //access_token 建议使用header传递
                return restTemplate.getForObject(userinfoEndpoint + "?access_token=" + accessToken, UserInfoDto.class);
            } catch (RestClientException e) {
                LOG.error("Rest error", e);
            }
            return null;
        }
    }

    @Override
    public Map<String, Object> verifyToken(String token) {
        RPHolder rpHolder = loadRPHolder();
        OIDCTokenVerifier verifier = new OIDCTokenVerifier(rpHolder, token);
        return verifier.verify();
    }

    @Override
    public AccessTokenDto retrieveCredentialsAccessTokenDto(AuthAccessTokenDto authAccessTokenDto) {
        final String uri = authAccessTokenDto.getAccessTokenUri();
        LOG.debug("Get [{}] access_token URL: {}", authAccessTokenDto.getGrantType(), uri);

        return loadAccessTokenDto(uri, authAccessTokenDto.getCredentialsParams());
    }

    @Override
    public AccessTokenDto retrievePasswordAccessTokenDto(AuthAccessTokenDto authAccessTokenDto) {
        final String fullUri = authAccessTokenDto.getAccessTokenUri();
        LOG.debug("Get [password] access_token URL: {}", fullUri);

        return loadAccessTokenDto(fullUri, authAccessTokenDto.getAccessTokenParams());
    }

    @Override
    public AccessTokenDto refreshAccessTokenDto(RefreshAccessTokenDto refreshAccessTokenDto) {
        final String fullUri = refreshAccessTokenDto.getRefreshAccessTokenUri();
        LOG.debug("Get refresh_access_token URL: {}", fullUri);

        return loadAccessTokenDto(fullUri, refreshAccessTokenDto.getRefreshTokenParams());
    }


    private AccessTokenDto loadAccessTokenDto(String fullUri, Map<String, String> params) {
        try {
            //此处的 post请求参数用在 url上拼接不是好方式
            StringBuilder url = new StringBuilder(fullUri + "?");
            for (String key : params.keySet()) {
                url.append(key).append("=").append(params.get(key)).append("&");
            }
            return restTemplate.postForObject(url.toString(), null, AccessTokenDto.class);
        } catch (RestClientException e) {
            LOG.error("Send request to: {} error", fullUri, e);
            return new AccessTokenDto("request_error", e.getMessage());
        }
    }
}
