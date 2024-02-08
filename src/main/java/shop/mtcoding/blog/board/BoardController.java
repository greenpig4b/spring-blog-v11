package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final HttpSession session;
    private final BoardRepository boardRepository;

    @GetMapping({"/","/board"})
    public String index() {
        List<Board> boardList = boardRepository.findAll();
        session.setAttribute("boardlist",boardList);

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
    public String save(BoardRequest.WriteUserDTO writeUserDTO,HttpServletRequest request){
        //조건로직

        if (writeUserDTO.getAuthor() == ""){
            return "error/400";
        }

        //필수 로직
        boardRepository.save(writeUserDTO);
        //request.setAttribute("sessionUser",);

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
        Board board = boardRepository.findId(id);
        //if (board.getUserId() != session)
        return "redirect:/";
    }
}
