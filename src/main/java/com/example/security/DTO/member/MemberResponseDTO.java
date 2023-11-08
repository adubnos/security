package com.example.security.DTO.member;

import com.example.security.Entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberResponseDTO {

    private String id;
    private String username;
    private String role;
    private LocalDateTime createdDate;

    @Builder
    public MemberResponseDTO(Member member) {
        this.id=member.getId();
        this.username=member.getUsername();
        this.role=member.getRole().toString();
        this.createdDate=member.getCreatedDate();
    }
}
