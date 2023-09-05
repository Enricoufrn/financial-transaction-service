package com.financialtransactions.config;

import com.financialtransactions.helper.MessageHelper;
import com.financialtransactions.services.IJwtService;
import com.financialtransactions.services.MyUserDetailsService;
import com.financialtransactions.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserService userService;
    private final MessageHelper messageHelper;
    private final IJwtService IJwtService;

    public SecurityConfig(UserService userService, MessageHelper messageHelper, IJwtService IJwtService) {
        this.userService = userService;
        this.messageHelper = messageHelper;
        this.IJwtService = IJwtService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        // login endpoint
                        request
                                .requestMatchers("/api/auth/login").permitAll()
                                // others endpoints
                                .requestMatchers("/api/auth/admin-user").hasRole("ADMIN")
                                .requestMatchers("/api***").authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable);

        http.authenticationManager(authManager(http));
        http.addFilterBefore(new JwtAuthenticationFilter(this.userDetailsService(), this.messageHelper, this.IJwtService), UsernamePasswordAuthenticationFilter.class);
        // todo: implement logout
//        http.logout(logout -> {
//            logout.logoutUrl("/api/auth/logout");
//            // todo: implement logout
//        });

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
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService(this.userService);
    }

    /**
     * Cryptography password type.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
