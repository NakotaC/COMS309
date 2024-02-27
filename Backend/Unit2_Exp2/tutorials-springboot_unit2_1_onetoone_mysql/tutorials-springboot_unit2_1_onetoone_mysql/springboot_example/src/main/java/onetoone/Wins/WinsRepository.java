package onetoone.Wins;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface WinsRepository extends JpaRepository<Wins, Long> {
    Wins findById(int id);

    @Transactional
    void deleteById(int id);
}
