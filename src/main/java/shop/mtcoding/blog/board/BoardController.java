package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List ;
import java.util.Objects;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final HttpSession session;
    private final BoardRepository boardRepository;

    @GetMapping({"/","/board"})
    public String index() {
        // 세션에 userId가 있는지 확인
        // userId가 없으면 만들어서 삽입

        Object userId = session.getAttribute("userId");
        if(userId == null){
            // 데이터베이스에 있는 userId의 다음 숫자 넣는다.
            session.setAttribute("userId", boardRepository.getNextUserId() + 1);
        }

        List<Board> boardList = boardRepository.findAll();
        List<BoardRequest.ViewDto> viewDtos = new ArrayList<>();
        for (int i = 0; i < boardList.size(); i++) {
            Board board = boardList.get(i);
            BoardRequest.ViewDto viewDto = BoardRequest.ViewDto.builder()
                                .title(board.getTitle())
                                .author(board.getAuthor())
                                .id(board.getId())
                                .userId(board.getUserId())
                                .content(board.getContent())
                                .build();

            if(viewDto.getUserId() == (int) session.getAttribute("userId")){
                viewDto.setMe(true);
            }

            viewDtos.add(viewDto);
        }
        session.setAttribute("boardlist", viewDtos);

        return "index";
    }

    @GetMapping("/session")
    public String session() {
        System.out.println(session.getAttribute("userId"));

        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {

        return "board/saveForm";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id) {

        return "board/updateForm";
    }

    @PostMapping("/board/save")
    public String save(BoardRequest.WriteUserDTO writeUserDTO, HttpServletRequest request){
        //조건로직
        if (writeUserDTO.getAuthor() == ""){
            return "error/400";
        }
        writeUserDTO.setUserId((int) session.getAttribute("userId"));

        // writeUserDTO.getAuthor() == database.author and writeUserDTO.getUserId() != database.useid
        List<Board> board = boardRepository.findId(writeUserDTO);
        if (board.size() == 0){
            //필수 로직
            boardRepository.save(writeUserDTO);
        }else{
            return "error/400";
        }

        return "redirect:/";
    }

    @PostMapping("/board/{id}/update")
    public String update(@PathVariable int id){

        return "redirect:/";
    }
    //
    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable int id,HttpServletRequest request){
        // Board sessionUser = session.getAttribute("bo")
//        Board board = boardRepository.findId(id);
        //if (board.getUserId() != session)
        return "redirect:/";
    }
}
