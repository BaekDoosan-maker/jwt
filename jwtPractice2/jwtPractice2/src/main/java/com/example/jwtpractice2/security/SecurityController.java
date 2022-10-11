package com.example.jwtpractice2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/security")
public class SecurityController {
    @Autowired
    private SecurityService securityService;

    @GetMapping("/create/token")
    public Map<String, Object> createToken(@RequestParam(value = "subject") String subject) {  // ? 뒤에 파라미터 던지는거 -> value=subject 던지는거
        String token = securityService.createToken(subject, ( 2*1000*60)); //토큰을 만들어 내는것  뒤에는 만료시간을 넣주면 되는데, 2분정도로 설정해둠
        Map<String, Object> map = new LinkedHashMap<>(); // 리턴할 맵을 하난 더 만듦 , 링크드해쉬맵을 쓰고
        map.put("result" , token); // map에 put 해서 result값, token값
        return map;
    }

    @GetMapping("/get/subject")
    public Map<String, Object> getSubject(@RequestParam(value = "token") String token)  {
        String subject =  securityService.getSubject(token);
        Map<String , Object> map = new LinkedHashMap<>();
        map.put("result", subject);
        return map;
    }
}
