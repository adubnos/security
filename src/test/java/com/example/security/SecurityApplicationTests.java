package com.example.security;

import com.example.security.Service.GlobalService;
import com.example.security.Service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecurityApplicationTests {
    @Autowired
    GlobalService globalService;
    @Autowired
    MemberService memberService;


    @Test
    public void userJoinTest() {
        
    }


}
