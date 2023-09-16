package com.financialtransactions.config.auth;

import com.financialtransactions.config.auth.JwtAuthenticationFilter;
import com.financialtransactions.helper.MessageHelper;
import com.financialtransactions.services.IJwtService;
import com.financialtransactions.services.MyUserDetailsService;
import com.financialtransactions.services.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.x509.X509AuthenticationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserService userService;
    private final MessageHelper messageHelper;
    private final IJwtService IJwtService;
    private final HandlerExceptionResolver resolver;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserService userService, MessageHelper messageHelper,
                          IJwtService IJwtService, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.messageHelper = messageHelper;
        this.IJwtService = IJwtService;
        this.resolver = resolver;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/auth/common-user").hasAnyRole("COMMON", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/auth/shopkeeper-user").hasAnyRole("SHOPKEEPER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/auth/admin-user").hasRole("ADMIN")
                        .requestMatchers("/api/v1/user**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable);

        http.authenticationManager(authManager(http));
        http.addFilterBefore(new JwtAuthenticationFilter(this.userDetailsService(), this.messageHelper, this.IJwtService), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new FilterChainExceptionHandler(this.resolver), JwtAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
        return authenticationManagerBuilder.build();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService(this.userService);
    }
}
