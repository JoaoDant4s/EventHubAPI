package com.imd.web2.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import com.imd.web2.auth.model.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    @Value("${security.jwt.expiracao}")
    private String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    public String gerarToken( User user ){

        Date data = converter(expiracao);
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(chaveAssinatura));

        return Jwts 
                .builder()
                .subject(user.getEmail()) 
                .expiration(data)
                .signWith(key)
                .compact();
    }

    

     private Claims obterClaims( String token ) throws ExpiredJwtException {

        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(chaveAssinatura));

        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
     
    public boolean tokenValido( String token ){
        try{
            Claims claims = (Claims) obterClaims(token);
            Date dataExpiracao = claims.getExpiration();
            LocalDateTime data =
                    dataExpiracao.toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(data);
        }catch (Exception e){ 
            return false;
        }
    }
    

    public String obterLoginUsuario(String token) throws ExpiredJwtException{
        return (String) (obterClaims(token)).getSubject();
    }

    public Date converter(String expiration){

        long expString = Long.valueOf(expiration);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant); 
        return data;
    }


    /* public static void main(String[] args){

        
        ConfigurableApplicationContext contexto = SpringApplication.run(SpringRestApiAppApplication.class);
        JwtService service = contexto.getBean(JwtService.class);
        Usuario usuario = Usuario.builder().login("fulano").build();
        System.out.println("Gerando token");
        String token = service.gerarToken(usuario);
        System.out.println(token);

        System.out.println("Verifica se o token é válido");
        boolean isTokenValido = service.tokenValido(token);
        System.out.println("O token é válido? " + isTokenValido);

        System.out.println("Obtenha o login do usuário");
        String login = service.obterLoginUsuario(token);
        System.out.println(login);



    } */
}
