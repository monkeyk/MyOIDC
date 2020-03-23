package myoidc.server.infrastructure.oidc;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 2020/3/23
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
public class OIDCUtilsTest {


    @Test
    public void isJWT() {

        assertFalse(OIDCUtils.isJWT("aaa"));
        assertFalse(OIDCUtils.isJWT("aaa.bb"));
        assertFalse(OIDCUtils.isJWT(""));
        assertFalse(OIDCUtils.isJWT(null));
        assertFalse(OIDCUtils.isJWT("aaa.bb.cc.dd"));

        assertTrue(OIDCUtils.isJWT("aaa.bb.cc"));
        assertTrue(OIDCUtils.isJWT("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Im15b2lkYy1rZXlpZCJ9.eyJzdWIiOiJtb2JpbGUiLCJhdWQiOlsibXlvaWRjLXJlc291cmNlIl0sInVzZXJfbmFtZSI6Im1vYmlsZSIsInNjb3BlIjpbIm9wZW5pZCIsInJlYWQiXSwiYXRpIjoiZjcyMmM3ZTQtZDZkMC00ZWVmLThmYWYtZThjZGU5ODA5N2Y0IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL215b2lkYy1zZXJ2ZXIvIiwiZXhwIjoxNTg3NDUwMzU2LCJpYXQiOjE1ODQ4NTgzNTYsImF1dGhvcml0aWVzIjpbIlJPTEVfTU9CSUxFIiwiUk9MRV9VU0VSIl0sImp0aSI6ImRkMzQ1NjEzLWExYWUtNDk0OS05MjVhLThiOTc4OTMwZGZjMCIsImNsaWVudF9pZCI6InVaeWlyQTNSdkFtN09UVkk2In0.gGz8j52iCk-40OUTFcLUVpH7VpxhiyenaVEZJ3_hu634BkdLjzpJwk3eGZBixPUQXLvuF3HUPgkevcYrAxn21u2QF4p3Qqr1P2ebT8ERwD1Sov2eeWrdGW_pr4KgyFFSZ64hczCCLZYILmwpXFDzV_dBiUQx0h2ttUAKm55NOQ6Ve-uE48XIBdXvjR9cJwy1EJ-BHgf6lp9xbZi0uQ2Mw2SEgV7C1q9e-1VJzBFGLRH1tD3xX1e5i4We_fqFg_gyTmd4sciQpExFWR1DaYsPnxYtYpVJYnfzPL5JLpw7eImnnw8oO7EWZiibORWem626T-WgzFRVd4lFaFaXqU33uA"));

    }
}