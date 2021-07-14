package com.yujin.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class jwtTest {
    public String secret = "dyAeHubOOc8KaOfYB6XEQoEj1QzRlVgtjNL8PYs1A1tymZvvqkcEU7L1imkKHeDa";

    @Test
    void jwt_token_create() {

        try {
            Algorithm algorithm = Algorithm.HMAC512(this.secret);
            String token = JWT.create()
                    .withIssuer("auth0")
                    .sign(algorithm);

            System.out.println("token = " + token);

        } catch (JWTCreationException exception) {

        }
    }

    @Test
    void jwt_token_validate() {
        try {
            // given
            Algorithm algorithm = Algorithm.HMAC512(this.secret);
            String token = JWT.create()
                    .withIssuer("auth0")
                    .sign(algorithm);

            // when  _ 토근 검증 객체를 생성한다.
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();

            DecodedJWT jwt = verifier.verify(token);
            System.out.println("jwt.getHeader() = " + jwt.getHeader());
            System.out.println("jwt.getToken() = " + jwt.getToken());
            System.out.println("jwt.getAlgorithm() = " + jwt.getAlgorithm());

        } catch (JWTCreationException exception) {
            System.out.println("exception = " + exception);
        }
    }

    @Test
    void jwt_decoded() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJhdXRoMCJ9.u_vt2xtce3X6eQ2levaV4XMhU5MvdIBcrc-bfXWzmF7zru8yO5Or_GOwV7E04wQpmxB6MAN5yUBl2aa0M7gOAQ";
        try {
            DecodedJWT jwt = JWT.decode(token);
            System.out.println("jwt.getToken() = " + jwt.getToken());
            System.out.println("jwt.getIssuer() = " + jwt.getIssuer());
            System.out.println("jwt.getPayload() = " + jwt.getPayload());

        } catch (JWTDecodeException exception) {
            System.out.println("exception = " + exception);
        }
    }

    @Test
    void jwt_ist() {
        try {
            // given
            Algorithm algorithm = Algorithm.HMAC512(this.secret);
            String token = JWT.create()
                    .withIssuer("auth0")
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(new Date().getTime() + (1000 * 60 * 60)))
                    .sign(algorithm);
            
            DecodedJWT jwt = JWT.decode(token);
            
            System.out.println("jwt.getHeader() = " + jwt.getHeader());
            System.out.println("jwt.getToken() = " + jwt.getToken());
            System.out.println("jwt.getAlgorithm() = " + jwt.getIssuedAt());
            System.out.println("jwt.getExpiresAt() = " + jwt.getExpiresAt());
            System.out.println("token = " + token);

        } catch (JWTDecodeException exception) {
            System.out.println("exception = " + exception);
        }
    }

    @Test
    void jwt_ist_validate() {
        try {
            // given
            Algorithm algorithm = Algorithm.HMAC512(this.secret);
            String token = JWT.create()
                    .withIssuer("auth0")
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(new Date().getTime() + (1000)))
                    .sign(algorithm);

            // 검증 객체 생성
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();

            // 일시정지
            Thread.sleep(3000);

            // 실제 검증
            DecodedJWT jwt = verifier.verify(token);

        } catch (JWTDecodeException | InterruptedException exception) {
            System.out.println("exception = " + exception);
        } catch (TokenExpiredException exception) {
            System.out.println("exception = " + exception);
        }
    }



}
