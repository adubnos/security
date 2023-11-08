package com.example.security.DTO.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveRequestDTO {

    @NotEmpty(message = "아이디는 필수 입력 사항입니다.")
    private String id;

    @NotEmpty(message = "닉네임은 필수 입력 사항입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수 입력 사항입니다.")
    private String password;

    private String role;
}
