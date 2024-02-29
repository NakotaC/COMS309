package onetoone.Wins;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import onetoone.Users.User;
import org.springframework.data.jpa.repository.Query;

public interface WinsRepository extends JpaRepository<Wins, Long> {
    Wins findById(int id);

    @Transactional
    void deleteById(int id);

   // Wins findByWins(int wins);



//    @Query("SELECT id, wins, user_id FROM Wins as wins order by wins DESC")
//    List<Wins> findLeaderBoard();
}
