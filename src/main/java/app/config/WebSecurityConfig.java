package app.config;

import app.util.Util;
import java.util.Locale;
import java.io.IOException;
import java.net.URL;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@PropertySource("classpath:application.properties")
public class WebSecurityConfig {
    
    @Autowired
    private DataSource dataSource;

    @Autowired
    private Environment env;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        Locale.setDefault(Locale.ROOT);
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter,CsrfFilter.class)
        .authorizeHttpRequests()
        .requestMatchers("/resources/**", "/uploads/**", "/WEB-INF/views/**", "/login", "/resetPassword", "/changePassword/**", "/stack").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .usernameParameter("name")
        .successHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
                if (savedRequest != null) {
                    URL url = new URL(savedRequest.getRedirectUrl());
                    if (url.getPath().replaceAll("/", "").equals(request.getContextPath().replaceAll("/", ""))) {
                        response.sendRedirect(request.getContextPath() + "/home");
                    }
                    else {
                        response.sendRedirect(savedRequest.getRedirectUrl());
                    }
                }
                else {
                    response.sendRedirect(request.getContextPath() + "/home");
                }
            }
        })
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/login")
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID");
        Util.setProperties(env);
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService()
    {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager();
        userDetailsManager.setDataSource(dataSource);
        userDetailsManager.setUsersByUsernameQuery("SELECT name as username, password, active as enabled FROM UserAccount where name = ?");
        userDetailsManager.setAuthoritiesByUsernameQuery("SELECT UserAccount.name as username, Role.name as authority FROM UserAccount join UserRole on UserAccount.id = UserRole.user_id join Role on UserRole.role_id = Role.id where UserAccount.name = ?");
        return userDetailsManager;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}