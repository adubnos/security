package com.example.security.Service;

import com.example.security.DTO.board.BoardResponseDTO;
import com.example.security.DTO.board.BoardSaveRequestDTO;
import com.example.security.Entity.Board;
import com.example.security.Entity.Member;
import com.example.security.Repository.BoardRepository;
import com.example.security.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public List<BoardResponseDTO> findMembers() {
        List<Board> all = boardRepository.findAll();
        List<BoardResponseDTO> boards=new ArrayList<>();

        for(Board board:all) {
            BoardResponseDTO build = BoardResponseDTO.builder()
                    .board(board)
                    .build();
            boards.add(build);
        }

        return boards;
    }

    @Transactional
    public Long saveBoard(BoardSaveRequestDTO boardSaveRequestDTO,
                          String id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다."));

        Board result=Board.builder()
                .title(boardSaveRequestDTO.getTitle())
                .content(boardSaveRequestDTO.getContent())
                .member(member)
                .build();

        boardRepository.save(result);

        return result.getNo();
    }

    public BoardResponseDTO boardDetail(Long no) {
        Board board = boardRepository.findById(no).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

        return BoardResponseDTO.builder()
                .board(board)
                .build();
    }

    public Page<BoardResponseDTO> searchingBoardList(String keyword, Pageable pageable) {
        Page<Board> boards = boardRepository.findByTitle(keyword, pageable);
        return getBoardResponseDTOS(pageable, boards);
    }

    private Page<BoardResponseDTO> getBoardResponseDTOS(Pageable pageable, Page<Board> boards) {
        List<BoardResponseDTO> boardDTOs=new ArrayList<>();

        for(Board board:boards) {
            BoardResponseDTO result = BoardResponseDTO.builder()
                    .board(board)
                    .build();
            boardDTOs.add(result);
        }

        return new PageImpl<>(boardDTOs, pageable, boards.getTotalElements());
    }

    public Long boardUpdate(Long no, BoardSaveRequestDTO boardSaveRequestDTO) {
        Board board=boardRepository.findById(no).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));
        board.update(boardSaveRequestDTO.getTitle(),boardSaveRequestDTO.getContent());
        boardRepository.save(board);

        return board.getNo();
    }

    public void boardRemove(Long no) {
        Board board=boardRepository.findById(no).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));
        boardRepository.delete(board);
    }
}
