package imd.eventhub.config;

import imd.eventhub.security.JwtService;
import imd.eventhub.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import imd.eventhub.security.JwtAuthFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;


    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter(jwtService, userService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorize) ->
            authorize
                .requestMatchers("/v3/api-docs/**", "/swagger-resources/**", "/swagger-ui/**").permitAll()
                .requestMatchers("/").permitAll()

                //USER PERMISSIONS
                .requestMatchers(HttpMethod.POST ,"/api/user").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST ,"/api/user/auth").permitAll()
                .requestMatchers(HttpMethod.DELETE ,"/api/user").hasRole("ADMIN")
                .requestMatchers("/api/user").hasAnyRole("USER", "ADMIN")

                //ATTRACTION PERMISSIONS
                .requestMatchers(HttpMethod.POST ,"/api/attraction").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE ,"/api/attraction").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT ,"/api/attraction").hasAnyRole("ADMIN", "ATTRACTION")
                .requestMatchers("/api/attraction").hasAnyRole("ADMIN", "ATTRACTION", "PROMOTER")

                //PARTICIPANT PERMISSIONS
                .requestMatchers(HttpMethod.POST ,"/api/participant").permitAll()
                .requestMatchers(HttpMethod.DELETE ,"/api/participant").hasRole("ADMIN")
                .requestMatchers("/api/participant").hasAnyRole("USER", "ADMIN")

                //EVENT PERMISSIONS
                // .requestMatchers("/api/event/").hasAnyRole("ADMIN", "PROMOTER")
                // .requestMatchers("/api/event/list").permitAll()
                .requestMatchers("/api/event/list").permitAll()
                
                //CREDIT CARD PERMISSIONS
                .requestMatchers("/api/creditCard").hasAnyRole("USER", "ADMIN")

                //FEEDBACK PERMISSIONS
                .requestMatchers("/api/feedback").hasAnyRole("USER", "ADMIN")
                .anyRequest().permitAll()
            )
            .sessionManagement((session) -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
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
                UsernamePasswordAuthenticationFilter.class)

            //habilitado por padrão
            .csrf(AbstractHttpConfigurer::disable);
            //.httpBasic(Customizer.withDefaults()); //possibilita "logar" com o headers de autenticação
         //retorno da cadeia de filtros   
        return http.build();
        
    }
    
}
