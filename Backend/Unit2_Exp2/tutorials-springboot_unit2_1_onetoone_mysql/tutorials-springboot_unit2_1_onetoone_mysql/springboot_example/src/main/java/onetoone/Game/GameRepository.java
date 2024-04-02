package onetoone.Game;

import onetoone.Inventory.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Game findById(int id);

    @Transactional
    void deleteById(int id);
}
