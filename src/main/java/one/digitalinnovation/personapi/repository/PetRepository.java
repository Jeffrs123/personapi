package one.digitalinnovation.personapi.repository;

import one.digitalinnovation.personapi.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
