package myoidc.server.service.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 2020/3/21
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
@Documented
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ClientIdValidator.class)
public @interface ClientIdValidation {

    String message() default "client_id不能为空(或已存在)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
