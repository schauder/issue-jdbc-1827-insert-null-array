package be.testcontainertest;

import org.springframework.data.repository.CrudRepository;

public interface SomeTableRepository extends CrudRepository<SomeTable, Long> {
}
