package shop.mtcoding.blog.board;

import lombok.Data;
import org.springframework.stereotype.Repository;


public class BoardRequest {

    @Data
    public static class IdAndAuthorCheckDTO{
        private int userId;
        private String author;
    }

    @Data
    public static class WriteUserDTO{
        private String author;
        private String title;
        private String content;
        private int userId;
    }
}
