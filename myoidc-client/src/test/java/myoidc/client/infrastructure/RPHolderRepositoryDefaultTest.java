package myoidc.client.infrastructure;

import myoidc.client.domain.RPHolder;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 2020/4/7
 *
 * @author Shengzhao Li
 */
public class RPHolderRepositoryDefaultTest {


    private RPHolderRepositoryDefault repositoryDefault = new RPHolderRepositoryDefault();


    @Test
    @Ignore
    public void test() {


        RPHolder rpHolder = repositoryDefault.findRPHolder();
        assertNotNull(rpHolder);
//        assertFalse(rpHolder.isConfigRP());


        rpHolder = new RPHolder();
        rpHolder.setClientId("clientId");
        rpHolder.setClientSecret("clientSecret");
        rpHolder.setDiscoveryEndpoint("https://...");

        boolean ok = repositoryDefault.saveRPHolder(rpHolder);
        assertTrue(ok);

    }
}