package com.example.security.Controller;

import com.example.security.DTO.board.BoardResponseDTO;
import com.example.security.Service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public String boards(Model model) {
        List<BoardResponseDTO> boards=boardService.findMembers();
        model.addAttribute("list",boards);

        return "board/list";
    }
}
