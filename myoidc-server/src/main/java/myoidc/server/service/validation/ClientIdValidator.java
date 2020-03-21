package myoidc.server.service.validation;

import myoidc.server.service.OauthService;
import myoidc.server.service.dto.OauthClientDetailsDto;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 2020/3/21
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
public class ClientIdValidator implements ConstraintValidator<ClientIdValidation, String> {


    private static final Logger LOG = LoggerFactory.getLogger(ClientIdValidator.class);

    @Autowired
    private OauthService oauthService;

    @Override
    public boolean isValid(String clientId, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(clientId)) {
            LOG.debug("ClientId is blank");
            return false;
        }


        OauthClientDetailsDto detailsDto = oauthService.loadOauthClientDetailsDto(clientId);
        if (detailsDto != null) {
            LOG.warn("ClientId: {} existed", clientId);
            return false;
        }

        return true;
    }
}
