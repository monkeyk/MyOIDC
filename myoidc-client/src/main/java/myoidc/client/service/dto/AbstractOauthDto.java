package myoidc.client.service.dto;



import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author Shengzhao Li
 */
public abstract class AbstractOauthDto implements Serializable {


    private static final long serialVersionUID = -5902798061281438305L;
    //Error if have from oauth server
    protected String errorDescription;
    protected String error;


    //original data
    protected String originalText;


    protected AbstractOauthDto() {
    }


    public boolean error() {
        return StringUtils.isNotEmpty(error) || StringUtils.isNotEmpty(errorDescription);
    }

    public String getOriginalText() {
        return originalText;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}