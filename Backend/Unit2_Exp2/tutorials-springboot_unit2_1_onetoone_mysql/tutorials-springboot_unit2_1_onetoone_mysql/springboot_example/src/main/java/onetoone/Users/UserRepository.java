package onetoone.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Vivek Bengre
 * 
 */ 
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(int id);

    @Transactional
    void deleteById(int id);
}
