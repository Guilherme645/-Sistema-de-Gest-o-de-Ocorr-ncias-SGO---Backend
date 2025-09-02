package com.sgo.sgo_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration // Marca esta classe como uma fonte de configurações para o Spring
@EnableWebSecurity // Habilita a configuração de segurança web do Spring
public class SecurityConfig {

    @Bean // Define um "Bean", um objeto gerenciado pelo Spring
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Passo 1: Desabilitar a proteção CSRF, que é opcional para APIs REST
                .csrf(csrf -> csrf.disable())

                // Passo 2: Configurar as regras de autorização com base nos papéis
                .authorizeHttpRequests(authorize -> authorize
                        // Permite apenas ADMIN para requisições PUT, para alterar ocorrências
                        .requestMatchers(HttpMethod.PUT, "/api/ocorrencias/{id}").hasRole("ADMIN")
                        // Permite apenas ADMIN para requisições DELETE, para deletar ocorrências
                        .requestMatchers(HttpMethod.DELETE, "/api/ocorrencias/{id}").hasRole("ADMIN")
                        // Permite USER e ADMIN para criar novas ocorrências (POST)
                        .requestMatchers(HttpMethod.POST, "/api/ocorrencias").hasAnyRole("USER", "ADMIN")
                        // Permite USER e ADMIN para listar e visualizar ocorrências (GET)
                        .requestMatchers(HttpMethod.GET, "/api/ocorrencias/**").hasAnyRole("USER", "ADMIN")
                        // Qualquer outra requisição deve ser autenticada
                        .anyRequest().authenticated()
                )

                // Passo 3: Habilitar a autenticação via "Basic Auth" (usuário e senha)
                .httpBasic(withDefaults());

        return http.build();
    }

    // Define usuários e seus respectivos papéis em memória.
    // Isso é ideal para desenvolvimento. Em produção, você usaria um banco de dados.
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.builder()
                .username("usuario")
                .password(passwordEncoder.encode("senha123"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("adminpass"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    // Define o codificador de senhas (BCrypt). Essencial para segurança.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
