package backend.Inventory;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Inventory findById(int id);

    @Transactional
    void deleteById(int id);
}
