package team.jlpt.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import team.jlpt.auth.PrincipalOauth2UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalOauth2UserService principalOauth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //post요청 forbiddon에러 방지

        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()    //  /user~로 시작하는 경로면 인증이 필요
                .antMatchers("/manager/**").access("hasRole('MANAGER') or hasRole('ADMIN')")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()  // antMathers에 적힌 경로를 제외한 모든 경로는 인증 필요X
            .and()
                .formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/loginForm")
            .and()
                .logout()
            .and()
                .oauth2Login()
                .loginPage("/loginForm")
                .defaultSuccessUrl("/")
                .failureUrl("/loginForm")
                .userInfoEndpoint()
                .userService(principalOauth2UserService)
        ;
    }
}
