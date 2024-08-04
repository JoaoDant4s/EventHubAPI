package com.imd.web2.auth.security;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.imd.web2.auth.services.AuthService;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthService authService;

    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter(jwtService, authService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll() // Permite todas as requisições sem restrições
            )
            /*
             * No Java Spring Security, o método addFilterBefore() é usado para adicionar um filtro 
             * antes de um filtro específico na cadeia de filtros de segurança. 
             * Isso significa que o filtro JWT será executado antes do filtro de autenticação 
             * de nome de usuário e senha. 
             * O filtro JWT é usado para validar tokens JWT em solicitações HTTP para 
             * autenticação e autorização, 
             * Adicionando o filtro JWT antes do UsernamePasswordAuthenticationFilter, 
             * você está configurando o sistema para primeiro verificar se há um token JWT válido antes de 
             * tentar autenticar com nome de usuário e senha. Isso é comum em aplicativos que usam autenticação baseada em tokens JWT.
             */
            .addFilterBefore(
                jwtFilter(), 
                UsernamePasswordAuthenticationFilter.class);
            //.httpBasic(Customizer.withDefaults()); //possibilita "logar" com o headers de autenticação
         //retorno da cadeia de filtros   
        return http.build();
        
    }
    
}
