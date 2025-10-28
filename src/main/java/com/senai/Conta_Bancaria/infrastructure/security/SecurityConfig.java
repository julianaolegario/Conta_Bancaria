package com.senai.Conta_Bancaria.infrastructure.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final UsuarioDetailsService usuarioDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(
                        AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**","/swagger-ui/**","/v3/api-docs/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/cliente/**").hasAnyRole("ADMIN","GERENTE")
                        .requestMatchers(HttpMethod.GET, "/api/cliente").hasAnyRole("ADMIN","GERENTE")
                        .requestMatchers(HttpMethod.PUT, "/api/cliente/**").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.DELETE, "/api/cliente").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/gerente").hasAnyRole("ADMIN","GERENTE") // GERENTE pode acessar os dados da área de gerência
                        .requestMatchers(HttpMethod.POST, "/api/gerente/**").hasAnyRole("ADMIN") // Somente ADMIN pode criar ou modificar gerentes
                        .requestMatchers(HttpMethod.PUT, "/api/gerente/**").hasAnyRole("ADMIN") // Somente ADMIN pode atualizar dados de gerentes
                        .requestMatchers(HttpMethod.DELETE, "/api/gerente/**").hasAnyRole("ADMIN") // Somente ADMIN pode excluir gerentes


                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(usuarioDetailsService);

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
