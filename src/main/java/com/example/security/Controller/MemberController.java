package com.example.security.Controller;

import com.example.security.DTO.member.MemberPasswordUpdateDTO;
import com.example.security.DTO.member.MemberResponseDTO;
import com.example.security.DTO.member.MemberUsernameUpdateDTO;
import com.example.security.Service.GlobalService;
import com.example.security.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Conditional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final GlobalService globalService;
    private final MemberService memberService;

    @GetMapping("/list")
    public String members(Model model) {
        List<MemberResponseDTO> members=memberService.findMembers();
        model.addAttribute("list",members);

        return "member/memberList";
    }

    @GetMapping("/info")
    public String memberInfo(Model model, Authentication authentication) {
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        MemberResponseDTO member=memberService.findMember(userDetails.getUsername());

        model.addAttribute("member",member);

        return "member/info";
    }

    @GetMapping("/update/username")
    public String updateUsernameForm(Model model, Authentication authentication) {
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        MemberResponseDTO member=memberService.findMember(userDetails.getUsername());
        model.addAttribute("memberUsernameUpdateDTO",member);

        return "member/updateUsername";
    }

    @PostMapping("/update/username")
    public String updateUsername(@Valid MemberUsernameUpdateDTO memberUsernameUpdateDTO, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("memberUsernameUpdateDTO",memberUsernameUpdateDTO);
            return "member/updateUsername";
        }
        memberService.updateMemberUsername(memberUsernameUpdateDTO);

        return "redirect:/member/info";
    }

    @GetMapping("/update/password")
    public String updatePasswordForm(Model model) {
        model.addAttribute("memberPasswordUpdateDTO",new MemberPasswordUpdateDTO());
        return "member/updatePassword";
    }

    @PostMapping("/update/password")
    public String updatePassword(@Valid MemberPasswordUpdateDTO memberPasswordUpdateDTO, Model model, Authentication authentication) {
        if(!Objects.equals(memberPasswordUpdateDTO.getNewPassword(), memberPasswordUpdateDTO.getConfirmPassword())) {
            model.addAttribute("memberPasswordUpdateDTO", memberPasswordUpdateDTO);
            model.addAttribute("differentPassword","새로운 비밀번호와 비밀번호 확인을 똑같이 입력하여 주십시오.");
            return "member/updatePassword";
        }

        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        Long result = memberService.updateMemberPassword(memberPasswordUpdateDTO, userDetails.getUsername());

        if(result==null) {
            model.addAttribute("dto",memberPasswordUpdateDTO);
            model.addAttribute("wrongPassword","기존 비밀번호가 맞지 않습니다.");
            return "member/updatePassword";
        }

        return "redirect:/member/info";
    }

    @GetMapping("/withdrawal")
    public String memberWithdrawalForm() {
        return "member/withdrawal";
    }

    @PostMapping("/withdrawal")
    public String memberWithdrawal(@RequestParam String password, Model model, Authentication authentication) {
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        boolean result = memberService.withdrawal(userDetails.getUsername(), password);

        if(result) {
            return "redirect:/logout";
        } else {
            model.addAttribute("wrongPassword","비밀번호가 맞지 않습니다.");
            return "member/withdrawal";
        }
    }
}
