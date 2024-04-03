package onetoone.MatchHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    History findById(int id);

    @Transactional
    void deleteById(int id);
}
