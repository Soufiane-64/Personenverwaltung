package de.szut.springboot_crud_service_demo.repository;
import de.szut.springboot_crud_service_demo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
