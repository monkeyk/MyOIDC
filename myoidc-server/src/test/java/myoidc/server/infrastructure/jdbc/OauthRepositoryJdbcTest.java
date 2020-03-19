package myoidc.server.infrastructure.jdbc;

import myoidc.server.domain.oauth.OauthClientDetails;
import myoidc.server.infrastructure.AbstractRepositoryTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * 2020/3/19
 *
 * @author Shengzhao Li
 */
public class OauthRepositoryJdbcTest extends AbstractRepositoryTest {


    @Autowired
    private OauthRepositoryJdbc repositoryJdbc;


    @Test
    public void findAllOauthClientDetails() {

        List<OauthClientDetails> list = repositoryJdbc.findAllOauthClientDetails("client_id");
        assertTrue(list.isEmpty());
    }


}