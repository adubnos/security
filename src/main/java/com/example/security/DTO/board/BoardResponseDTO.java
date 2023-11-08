package com.example.security.DTO.board;

import com.example.security.Entity.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardResponseDTO {

    private Long no;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifyDate;
    private String username;
    private String id;

    @Builder
    public BoardResponseDTO(Board board) {
        this.no=board.getNo();
        this.title=board.getTitle();
        this.content=board.getContent();
        this.username=board.getMember().getUsername();
        this.id=board.getMember().getId();
        this.createdDate=board.getCreatedDate();
        this.modifyDate=board.getModifyDate();
    }
}
