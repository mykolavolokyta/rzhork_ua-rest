package bebra.rzhork_ua_rest.controller;

import bebra.rzhork_ua_rest.model.dto.VacancyFilterDTO;
import bebra.rzhork_ua_rest.model.dto.VacancyWithRequirementDTO;
import bebra.rzhork_ua_rest.model.entity.Company;
import bebra.rzhork_ua_rest.model.entity.Vacancy;
import bebra.rzhork_ua_rest.service.UserService;
import bebra.rzhork_ua_rest.service.VacancyService;
import bebra.rzhork_ua_rest.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vacancies")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;
    private final UserService userService;

    @Operation(summary = "Get list of filtered vacancies", description = "Returns a list of vacancies based on the provided filters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of vacancies"),
            @ApiResponse(responseCode = "400", description = "Invalid filter parameters"),
    })
    @GetMapping
    public List<Vacancy> getVacancies(@Parameter(description = "Filters for the vacancies") VacancyFilterDTO filterDTO) {
        return vacancyService.getFilteredVacancies(filterDTO).getContent();
    }

    @Operation(summary = "Get vacancy by ID", description = "Returns a single vacancy by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved vacancy"),
            @ApiResponse(responseCode = "404", description = "Vacancy not found")
    })
    @GetMapping("/{id}")
    public Vacancy getVacancy(@Parameter(description = "ID of the vacancy", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
                              @PathVariable UUID id) {
        return vacancyService.getVacancy(id);
    }

    @Operation(summary = "Create a new vacancy", description = "Creates a new vacancy for the current user's company.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vacancy successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
    })
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Vacancy addVacancy(@RequestBody VacancyWithRequirementDTO vacancyWithRequirementDTO) {
        String username = SecurityUtils.getCurrentUsername();
        Company company = userService.getCompanyByUsername(username);
        return vacancyService.saveVacancyRequirement(vacancyWithRequirementDTO, company);
    }

    @Operation(summary = "Update an existing vacancy", description = "Updates the details of an existing vacancy by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vacancy successfully updated"),
            @ApiResponse(responseCode = "404", description = "Vacancy not found"),
    })
    @PutMapping("/edit/{id}")
    public Vacancy updateVacancy(@Parameter(description = "ID of the vacancy to update", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
                                 @PathVariable UUID id,
                                 @RequestBody VacancyWithRequirementDTO vacancyWithRequirementDTO) {
        return vacancyService.updateVacancy(id, vacancyWithRequirementDTO);
    }

    @Operation(summary = "Delete a vacancy", description = "Deletes an existing vacancy by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vacancy successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Vacancy not found")
    })
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVacancy(@Parameter(description = "ID of the vacancy to delete", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
                              @PathVariable UUID id) {
        vacancyService.deleteVacancy(id);
    }
}
