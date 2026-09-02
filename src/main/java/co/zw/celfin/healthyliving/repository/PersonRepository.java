package co.zw.celfin.healthyliving.repository;

import co.zw.celfin.healthyliving.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
