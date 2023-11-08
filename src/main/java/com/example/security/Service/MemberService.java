package com.example.security.Service;

import com.example.security.DTO.member.MemberPasswordUpdateDTO;
import com.example.security.DTO.member.MemberResponseDTO;
import com.example.security.DTO.member.MemberUsernameUpdateDTO;
import com.example.security.Entity.Member;
import com.example.security.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    public List<MemberResponseDTO> findMembers() {
        List<Member> all = memberRepository.findAll();
        List<MemberResponseDTO> members = new ArrayList<>();

        for(Member member: all) {
            MemberResponseDTO build =MemberResponseDTO.builder()
                    .member(member)
                    .build();
            members.add(build);
        }

        return members;
    }

    public MemberResponseDTO findMember(String id) {
        Member member=memberRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다."));

        return MemberResponseDTO.builder()
                .member(member)
                .build();
    }

    public void updateMemberUsername(MemberUsernameUpdateDTO memberUsernameUpdateDTO) {
        Member member = memberRepository.findById(memberUsernameUpdateDTO.getId()).orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다."));

        member.updateUsername(memberUsernameUpdateDTO.getUsername());
        memberRepository.save(member);

    }

    public Long updateMemberPassword(MemberPasswordUpdateDTO memberPasswordUpdateDTO, String id) {
        Member member=memberRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다."));

        if(!passwordEncoder.matches(memberPasswordUpdateDTO.getCurrentPassword(), member.getPassword())) {
            return null;
        } else {
            memberPasswordUpdateDTO.setNewPassword(passwordEncoder.encode(memberPasswordUpdateDTO.getNewPassword()));
            member.updatePassword(memberPasswordUpdateDTO.getNewPassword());
            memberRepository.save(member);
        }

        return member.getNo();
    }

    public boolean withdrawal(String id, String password) {
        Member member=memberRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다."));

        if(passwordEncoder.matches(password, member.getPassword())) {
            memberRepository.delete(member);
            return true;
        }else {
            return false;
        }
    }
}
