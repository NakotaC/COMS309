package onetoone.UserMatch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
@Repository
public interface UMRepository extends JpaRepository<UM, Long> {
    UM findById(int id);

    @Transactional
    void deleteById(int id);
}
