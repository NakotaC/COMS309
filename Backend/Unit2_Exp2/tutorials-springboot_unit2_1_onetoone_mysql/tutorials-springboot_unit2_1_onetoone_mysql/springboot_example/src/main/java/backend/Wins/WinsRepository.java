package backend.ShopItems;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface ShopItemsRepository extends JpaRepository<ShopItems, Long> {
    ShopItems findById(int id);

    @Transactional
    void deleteById(int id);
}
