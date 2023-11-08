package com.example.security.Controller;

import com.example.security.DTO.member.MemberSaveRequestDTO;
import com.example.security.Service.BoardService;
import com.example.security.Service.GlobalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class GlobalController {

    private final GlobalService globalService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/denied")
    public String doDenied() {
        return "denied";
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("memberSaveRequestDTO",new MemberSaveRequestDTO());
        return "member/memberForm";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberSaveRequestDTO memberSaveRequestDTO, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("memberSaveRequestDTO",memberSaveRequestDTO);
            return "member/memberForm";
        }
        globalService.join(memberSaveRequestDTO);
        return "redirect:/";
    }
}
