package myoidc.server.service.validation;

import myoidc.server.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 2020/3/18
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
public class UsernameValidator implements ConstraintValidator<UsernameValidation, String> {


    private static final Logger LOG = LoggerFactory.getLogger(UsernameValidator.class);

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(username)) {
            LOG.debug("Username is blank");
            return false;
        }

        boolean existed = userService.isExistedUsername(username);
        if (existed) {
            LOG.debug("Username: {} existed", username);
            return false;
        }

        return true;
    }
}
