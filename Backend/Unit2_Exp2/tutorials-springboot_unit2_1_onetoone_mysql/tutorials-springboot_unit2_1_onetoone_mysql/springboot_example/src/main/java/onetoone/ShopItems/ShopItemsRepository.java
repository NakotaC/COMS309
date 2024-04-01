package onetoone.ShopItems;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
@Repository
public interface ShopItemsRepository extends JpaRepository<ShopItem, Long> {
    ShopItem findById(int id);

    @Transactional
    void deleteById(int id);
}
