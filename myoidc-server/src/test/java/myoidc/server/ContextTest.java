package myoidc.server;


import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.BeforeTransaction;


/**
 * @author Shengzhao Li
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public abstract class ContextTest extends AbstractTransactionalJUnit4SpringContextTests {


    @BeforeTransaction
    public void before() throws Exception {
//        BeanProvider.initialize(applicationContext);
//        SecurityUtils securityUtils = new SecurityUtils();
//        securityUtils.setSecurityHolder(new SpringSecurityHolder() {
//            @Override
//            public AndailyUserDetails userDetails() {
//                return null;
//            }
//        });
    }


}