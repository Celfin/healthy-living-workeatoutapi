package co.zw.celfin.healthyliving.service.impl;

import co.zw.celfin.healthyliving.dto.PersonDto;
import co.zw.celfin.healthyliving.dto.PersonRequest;
import co.zw.celfin.healthyliving.entity.Person;
import co.zw.celfin.healthyliving.exception.ResourceNotFoundException;
import co.zw.celfin.healthyliving.mapper.PersonMapper;
import co.zw.celfin.healthyliving.repository.PersonRepository;
import co.zw.celfin.healthyliving.service.PersonService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PersonDto> findAll() {
        return personRepository.findAll().stream().map(personMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PersonDto findById(Long id) {
        return personMapper.toDto(getOrThrow(id));
    }

    @Override
    public PersonDto create(PersonRequest request) {
        Person saved = personRepository.save(personMapper.toEntity(request));
        return personMapper.toDto(saved);
    }

    @Override
    public PersonDto update(Long id, PersonRequest request) {
        Person person = getOrThrow(id);
        person.setName(request.getName());
        person.setType(request.getType());
        return personMapper.toDto(personRepository.save(person));
    }

    @Override
    public void delete(Long id) {
        if (!personRepository.existsById(id)) {
            throw new ResourceNotFoundException("Person not found: " + id);
        }
        personRepository.deleteById(id);
    }

    private Person getOrThrow(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found: " + id));
    }
}
