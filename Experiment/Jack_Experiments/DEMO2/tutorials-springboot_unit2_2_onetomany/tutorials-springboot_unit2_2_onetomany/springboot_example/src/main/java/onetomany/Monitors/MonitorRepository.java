package onetomany.Monitors;

import org.springframework.data.jpa.repository.JpaRepository;
public interface MonitorRepository extends JpaRepository<Monitor, Long>  {
    Monitor findById(int id);
}
