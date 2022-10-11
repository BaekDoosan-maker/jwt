package com.example.jwtpractice2.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
@Service
public class SecurityService {
    private static final String SECRET_KEY = "aasdjaskasldfkjalsdkfjalksdfalskdjf";
    public String createToken(String subject , long expTime) {
        if(expTime<=0){ // 만약 완료 시간이 0보다 작을경우
            throw new RuntimeException("만료시간이 0보다 커야함~"); // 런타임 익셉션을 던저준다.
        }
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; // 알고리즘은 HS256을 쓴다.
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY); // DatatypeConverter를 이용해서 string 형태의 키를 byte로 바꾸어 주는것임
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());  // java.security.key 이용해서 키를 만들어준다.
        return  Jwts.builder() // 자바 빌더 패턴이 좋아. 8버전부터 쓰는건데, 코드도 줄여주고, 간소화해주고 좋음
                .setSubject(subject)
                .signWith(signingKey, signatureAlgorithm)
                .setExpiration(new Date(System.currentTimeMillis() + expTime))
                .compact();
    }
    // 토큰 검증하는 메서드를 boolean~~
    public String getSubject(String token) { // claim -> payload에 담기는 정보임
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY)) // 키를 셋팅
                .build() // 빌드 해주고
                .parseClaimsJws(token)// 토큰을 넣어서 풀어주고
                .getBody();  // 풀어줬으니까 값이 나오겠지. getBody 하면 claimn이 만들어지는데
        return claims.getSubject(); // 우리가 꺼내올꺼는 subject만 꺼내올거야
    }
}
