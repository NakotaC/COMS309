package onetoone.EquippedItems;

import onetoone.Inventory.Inventory;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
@Repository
public interface EquippedItemsRepository extends JpaRepository<EquippedItems, Long>{
    Inventory findById(int id);

    @Transactional
    void deleteById(int id);
}
