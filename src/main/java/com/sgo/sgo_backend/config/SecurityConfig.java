package com.sgo.sgo_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration // Marca esta classe como uma fonte de configurações para o Spring
@EnableWebSecurity // Habilita a configuração de segurança web do Spring
public class SecurityConfig {

    @Bean // Define um "Bean", um objeto gerenciado pelo Spring
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Passo 1: Desabilitar a proteção CSRF
                .csrf(csrf -> csrf.disable())

                // Passo 2: Configurar as regras de autorização
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated() // Exige que TODAS as requisições sejam autenticadas
                )

                // Passo 3: Habilitar a autenticação via "Basic Auth" (usuário e senha)
                .httpBasic(withDefaults());

        return http.build();
    }
}