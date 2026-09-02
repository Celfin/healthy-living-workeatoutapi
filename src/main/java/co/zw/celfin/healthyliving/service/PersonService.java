package co.zw.celfin.healthyliving.service;

import co.zw.celfin.healthyliving.dto.PersonDto;
import co.zw.celfin.healthyliving.dto.PersonRequest;
import java.util.List;

public interface PersonService {

    List<PersonDto> findAll();

    PersonDto findById(Long id);

    PersonDto create(PersonRequest request);

    PersonDto update(Long id, PersonRequest request);

    void delete(Long id);
}
