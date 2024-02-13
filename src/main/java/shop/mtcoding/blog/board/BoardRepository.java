package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Repository

public class BoardRepository {

    private final EntityManager em;

    public List<Board> findAll(){
        Query query = em.createNativeQuery("select * from board_tb order by id desc",Board.class);
        return query.getResultList();
    }

    public List<Board> findId(BoardRequest.WriteUserDTO writeUserDTO){
        Query query = em.createNativeQuery("SELECT * FROM board_tb WHERE author = ? AND user_id != ? ",Board.class);
        query.setParameter(1,writeUserDTO.getAuthor());
        query.setParameter(2,writeUserDTO.getUserId());

        return query.getResultList();

    }

    public List<Board> findUsersById(BoardRequest.WriteUserDTO writeUserDTO){
        Query query = em.createNativeQuery("select * from board_tb where user_id != ? and author = ?", Board.class);

        query.setParameter(1,writeUserDTO.getUserId());
        query.setParameter(2,writeUserDTO.getAuthor());

        return query.getResultList();
    }




    @Transactional
    public void save(BoardRequest.WriteUserDTO writeUserDTO){
        Query query = em.createNativeQuery("insert into board_tb(author, title,content,user_id) values(?,?,?,?)");
        query.setParameter(1,writeUserDTO.getAuthor());
        query.setParameter(2,writeUserDTO.getTitle());
        query.setParameter(3,writeUserDTO.getContent());
        query.setParameter(4,writeUserDTO.getUserId());
        query.executeUpdate();
    }

    public Integer getNextUserId(){
        Query query = em.createNativeQuery("SELECT MAX(user_id) FROM board_tb;", Integer.class);

        return (Integer) query.getSingleResult();
    }

}
