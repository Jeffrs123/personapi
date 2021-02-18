package one.digitalinnovation.personapi.service;

import lombok.AllArgsConstructor;
import one.digitalinnovation.personapi.dto.request.PetDTO;
import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personapi.entity.Pet;
import one.digitalinnovation.personapi.exception.PetNotFoundException;
import one.digitalinnovation.personapi.mapper.PetMapper;
import one.digitalinnovation.personapi.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PetService {

    private PetRepository petRepository;

    private final PetMapper petMapper = PetMapper.INSTANCE;

    public MessageResponseDTO createPerson(PetDTO personDTO) {
        Pet personToSave = petMapper.toModel(personDTO);

        Pet savedPerson = petRepository.save(personToSave);
        return createMessageResponse(savedPerson.getId(), "Created person with ID ");
    }

    public List<PetDTO> listAll() {
        List<Pet> allPeople = petRepository.findAll();
        return allPeople.stream()
                .map(petMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PetDTO findById(Long id) throws PetNotFoundException {
        Pet person = verifyIfExists(id);

        return petMapper.toDTO(person);
    }

    public void delete(Long id) throws PetNotFoundException {
        verifyIfExists(id);
        petRepository.deleteById(id);
    }

    public MessageResponseDTO updateById(Long id, PetDTO personDTO) throws PetNotFoundException {
        verifyIfExists(id);

        Pet personToUpdate = petMapper.toModel(personDTO);

        Pet updatedPerson = petRepository.save(personToUpdate);
        return createMessageResponse(updatedPerson.getId(), "Updated person with ID ");
    }

    private Pet verifyIfExists(Long id) throws PetNotFoundException {
        return petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException(id));
    }

    private MessageResponseDTO createMessageResponse(Long id, String message) {
        return MessageResponseDTO
                .builder()
                .message(message + id)
                .build();
    }
}
