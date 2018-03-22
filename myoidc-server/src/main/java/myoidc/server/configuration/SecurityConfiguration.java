package myoidc.server.configuration;


import myoidc.server.domain.security.SecurityUtils;
import myoidc.server.service.SecurityService;
import myoidc.server.web.context.SpringSecurityHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 2018/2/1
 * <p>
 * Security
 * <p>
 *
 * @author Shengzhao Li
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    private SecurityService securityService;


    @Override
    public void configure(WebSecurity web) throws Exception {
        //Ignore, public
        web.ignoring().antMatchers("/public/**", "/static/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
        http.authorizeRequests()
                .antMatchers("/public/**").permitAll()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/login*").permitAll()

                .antMatchers("/user/**").hasAnyRole("USER")

                .antMatchers(HttpMethod.GET, "/login*").anonymous()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/signin")
                .failureUrl("/login?error=1")
//                .successHandler(authenticationSuccessHandler)
                .usernameParameter("oidc_user")
                .passwordParameter("oidcPwd")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/signout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .exceptionHandling();

        http.authenticationProvider(authenticationProvider());
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(securityService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }


    /**
     * BCrypt  加密
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * SecurityUtils
     */
    @Bean
    public SecurityUtils securityUtils() {
        SecurityUtils securityUtils = new SecurityUtils();
        securityUtils.setSecurityHolder(new SpringSecurityHolder());
        return securityUtils;
    }


}
