package com.yujin.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class MyController {


    @GetMapping("/login")
    public TokenDTO test1(TestDTO data) {
        System.out.println(data.getName());
        System.out.println(data.getAge());

        TokenDTO createToken = new TokenDTO();
        System.out.println("createToken.getToken() = " + createToken.getToken());

        return createToken;
    }

    @GetMapping("/innerPage")
    public TestDTO test2(TestDTO data) {
        System.out.println(data.getName());
        System.out.println(data.getAge());
        return data;
    }

    @Data // 수정이 필요할 수도 있는데, 그건 해보면서 확인
    static class TestDTO {

        private String name;
        private int age;
        private String token;
    }

    @Data
    static class TokenDTO {
        public String secret = "dyAeHubOOc8KaOfYB6XEQoEj1QzRlVgtjNL8PYs1A1tymZvvqkcEU7L1imkKHeDa";

        Algorithm algorithm = Algorithm.HMAC512(this.secret);
        String token = JWT.create()
                .withIssuer("auth0")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + (1000)))
                .sign(algorithm);
    }
}
