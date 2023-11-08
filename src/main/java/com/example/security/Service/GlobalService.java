package com.example.security.Service;

import com.example.security.DTO.member.MemberSaveRequestDTO;
import com.example.security.Entity.Member;
import com.example.security.Entity.Role;
import com.example.security.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GlobalService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(MemberSaveRequestDTO memberSaveRequestDTO) {
        memberSaveRequestDTO.setPassword(passwordEncoder.encode(memberSaveRequestDTO.getPassword()));

        Member member = Member.builder()
                .id(memberSaveRequestDTO.getId())
                .username(memberSaveRequestDTO.getUsername())
                .password(memberSaveRequestDTO.getPassword())
                .role(Role.valueOf(memberSaveRequestDTO.getRole()))
                .build();

        return memberRepository.save(member).getNo();
    }

}
