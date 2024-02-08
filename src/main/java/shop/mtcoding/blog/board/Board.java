package shop.mtcoding.blog.board;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "board_tb")
@Entity
@Data
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int  id;
    private String author;
    private String title;
    private String content;
    private int userId; // 테이블에 만들어 질때 user_id
}
