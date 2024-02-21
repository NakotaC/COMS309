package onetoone.Clans;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface ClanRepository extends JpaRepository<Clan, Long> {
    Clan findById(int id);

    @Transactional
    void deleteById(int id);
}
