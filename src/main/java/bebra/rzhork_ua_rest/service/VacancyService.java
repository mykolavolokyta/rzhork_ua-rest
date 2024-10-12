package bebra.rzhork_ua_rest.service;

import bebra.rzhork_ua_rest.model.dto.VacancyFilterDTO;
import bebra.rzhork_ua_rest.model.dto.VacancyWithRequirementDTO;
import bebra.rzhork_ua_rest.model.entity.Company;
import bebra.rzhork_ua_rest.model.entity.Requirement;
import bebra.rzhork_ua_rest.model.entity.Vacancy;
import bebra.rzhork_ua_rest.repository.RequirementRepository;
import bebra.rzhork_ua_rest.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VacancyService {
    private final VacancyRepository vacancyRepository;
    private final RequirementRepository requirementRepository;

    public Page<Vacancy> getFilteredVacancies(VacancyFilterDTO filterDTO) {
        Pageable pageable = PageRequest.of(filterDTO.getPage(), 4);

        String search = (filterDTO.getSearch() == null ? "" : filterDTO.getSearch());
        Double minSalary = (filterDTO.getMinSalary() == null) ? 0.0 : filterDTO.getMinSalary();
        Double maxSalary = (filterDTO.getMaxSalary() == null) ? Double.MAX_VALUE : filterDTO.getMaxSalary();
        LocalDateTime startDate = (filterDTO.getStartDate() == null) ? LocalDateTime.of(1900, 1, 1, 0, 0) : filterDTO.getStartDate().atStartOfDay();
        LocalDateTime endDate = (filterDTO.getEndDate() == null) ? LocalDateTime.now() : filterDTO.getEndDate().atStartOfDay();

        return vacancyRepository.findFilteredVacancies(
                search,
                minSalary,
                maxSalary,
                startDate,
                endDate,
                pageable
        );
    }

    public Vacancy getVacancy(UUID id) {
        Vacancy vacancy = vacancyRepository.findById(id).orElse(null);
        if (vacancy == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vacancy not found");
        }
        return vacancy;
    }

    public Vacancy saveVacancyRequirement(VacancyWithRequirementDTO dto, Company company) {
        Vacancy vacancy = new Vacancy();
        vacancy.setCompany(company);
        vacancy.setDescription(dto.getDescription());
        vacancy.setTitle(dto.getTitle());
        vacancy.setLocation(dto.getLocation());
        vacancy.setSalary(dto.getSalary());

        Requirement requirement = new Requirement();
        requirement.setEducation(dto.getEducation());
        requirement.setExperience(dto.getExperience());
        requirement.setSkills(dto.getSkills());
        requirement.setLanguageRequirements(dto.getLanguageRequirements());
        requirement.setWorkSchedule(dto.getWorkSchedule());
        requirement.setAdditionalRequirements(dto.getAdditionalRequirements());
        requirement.setVacancy(vacancy);
        requirementRepository.save(requirement);

        vacancy.setRequirement(requirement);
        vacancyRepository.save(vacancy);
        return vacancy;
    }

    public Vacancy updateVacancy(UUID id, VacancyWithRequirementDTO dto) {
        Vacancy vacancy = getVacancy(id);
        Requirement requirement = vacancy.getRequirement();

        vacancy.setTitle(dto.getTitle());
        vacancy.setLocation(dto.getLocation());
        vacancy.setSalary(dto.getSalary());
        vacancy.setDescription(dto.getDescription());

        requirement.setEducation(dto.getEducation());
        requirement.setExperience(dto.getExperience());
        requirement.setSkills(dto.getSkills());
        requirement.setLanguageRequirements(dto.getLanguageRequirements());
        requirement.setWorkSchedule(dto.getWorkSchedule());
        requirement.setAdditionalRequirements(dto.getAdditionalRequirements());

        requirementRepository.save(requirement);
        vacancyRepository.save(vacancy);
        return vacancy;
    }

    public void deleteVacancy(UUID id) {
        Vacancy vacancy = vacancyRepository.findById(id).orElse(null);
        if (vacancy == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vacancy not found");
        }
        requirementRepository.delete(vacancy.getRequirement());
        vacancyRepository.deleteById(id);
    }

}
