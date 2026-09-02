package co.zw.celfin.healthyliving.controller;

import co.zw.celfin.healthyliving.dto.ApiResponse;
import co.zw.celfin.healthyliving.dto.PersonDto;
import co.zw.celfin.healthyliving.dto.PersonRequest;
import co.zw.celfin.healthyliving.service.PersonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/people")
@RequiredArgsConstructor
@Tag(name = "People", description = "The two profiles this app plans for: an adult and a baby")
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public ApiResponse<List<PersonDto>> findAll() {
        return ApiResponse.ok(personService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<PersonDto> findById(@PathVariable Long id) {
        return ApiResponse.ok(personService.findById(id));
    }

    @PostMapping
    public ApiResponse<PersonDto> create(@Valid @RequestBody PersonRequest request) {
        return ApiResponse.created(personService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<PersonDto> update(@PathVariable Long id, @Valid @RequestBody PersonRequest request) {
        return ApiResponse.ok(personService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        personService.delete(id);
        return ApiResponse.ok(null);
    }
}
