package onetoone.Cosmetics;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface CosmeticRepository extends JpaRepository<Cosmetic, Long> {
    Cosmetic findById(int id);

    @Transactional
    void deleteById(int id);
}
