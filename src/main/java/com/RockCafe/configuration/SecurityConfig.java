package com.RockCafe.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                // Páginas públicas (qualquer um pode ver)
                .requestMatchers("/", "/api/list_items", "/images/**", "/main.css", "/newItem.css", "/style.css").permitAll()
                // Console H2 (apenas para dev)
                .requestMatchers("/h2-console/**").permitAll()
                // Qualquer outra requisição precisa de login (criar, editar, deletar)
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .permitAll() // Habilita a página de login padrão do Spring
                .defaultSuccessUrl("/api/list_items", true) // Redireciona para a lista após logar
            )
            .logout((logout) -> logout
                .logoutSuccessUrl("/api/list_items") // Redireciona para a lista após deslogar
                .permitAll()
            )
            // Configurações necessárias para o H2 Console funcionar
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
            .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Define o usuário único ADMIN em memória
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin") // Senha simples para o exemplo
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }
}