package onetoone.ShopItems;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface ShopItemsRepository extends JpaRepository<ShopItem, Long> {
    ShopItem findById(int id);

    @Transactional
    void deleteById(int id);
}
