package onetoone.Clans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
@Repository
public interface ClanRepository extends JpaRepository<Clan, Long> {
    Clan findById(int id);

    @Transactional
    void deleteById(int id);
}
