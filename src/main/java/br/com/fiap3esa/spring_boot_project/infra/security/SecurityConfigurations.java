package br.com.fiap3esa.spring_boot_project.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos - devem vir PRIMEIRO
                        .requestMatchers("/login", "/login/**", "/health-check", "/health-check/**").permitAll()
                        .requestMatchers("/v3/api-docs", "/v3/api-docs/**", "/v3/api-docs.yaml").permitAll()
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/swagger-ui**").permitAll()
                        .requestMatchers("/swagger-resources/**", "/webjars/**").permitAll()
                        
                        // Endpoints de usuários - apenas ADMIN
                        .requestMatchers(HttpMethod.POST, "/usuarios").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/usuarios", "/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/usuarios/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/usuarios/{id}").hasRole("ADMIN")
                        
                        // Endpoint para alterar senha - qualquer usuário autenticado
                        .requestMatchers(HttpMethod.PUT, "/usuarios/alterar-senha").authenticated()
                        
                        // Demais endpoints - qualquer usuário autenticado
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}