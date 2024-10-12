package bebra.rzhork_ua_rest.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Schema(description = "Data Transfer Object for filtering vacancies")
public class VacancyFilterDTO {
    @Schema(description = "Position or location of the vacancy", example = "Software Engineer")
    private String search;

    @Schema(description = "Minimum salary for the position", example = "50000")
    private Double minSalary;

    @Schema(description = "Maximum salary for the position", example = "120000")
    private Double maxSalary;

    @Schema(description = "Start date for the job posting", example = "2023-01-01")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @Schema(description = "End date for the job posting", example = "2024-12-31")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @Schema(description = "Page number for pagination", example = "0", defaultValue = "0")
    private int page = 0;
}
