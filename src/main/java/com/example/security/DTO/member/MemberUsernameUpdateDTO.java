package com.example.security.DTO.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberUsernameUpdateDTO {

    @NotBlank(message = "아이디는 필수 입력 사항입니다.")
    private String id;

    @NotBlank(message = "닉네임은 필수 입력 사항입니다.")
    private String username;
}
