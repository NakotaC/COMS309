package onetoone.Inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Inventory findById(int id);

    @Transactional
    void deleteById(int id);
}
