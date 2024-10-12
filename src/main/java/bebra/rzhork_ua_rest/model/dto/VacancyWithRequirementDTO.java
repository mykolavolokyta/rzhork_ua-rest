package bebra.rzhork_ua_rest.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "DTO containing information for creating or updating a vacancy and its requirements")
public class VacancyWithRequirementDTO {

    @NotBlank
    @Schema(description = "Title of the vacancy", example = "Software Engineer")
    private String title;

    @NotBlank
    @Schema(description = "Location where the job is based", example = "Kyiv, Ukraine")
    private String location;

    @NotBlank
    @Schema(description = "Salary offered for the position", example = "50000.00")
    private double salary;

    @NotBlank
    @Schema(description = "Detailed description of the vacancy", example = "We are looking for a software engineer with experience in Java and Spring Boot.")
    private String description;

    @NotBlank
    @Schema(description = "Required level of education", example = "Bachelor's degree in Computer Science")
    private String education;

    @Schema(description = "Experience required for the position", example = "At least 3 years of experience in software development")
    private String experience;

    @Schema(description = "Key skills required for the job", example = "Java, Spring Boot, Microservices")
    private String skills;

    @Schema(description = "Language requirements for the job", example = "Fluent in English and Ukrainian")
    private String languageRequirements;

    @Schema(description = "Expected work schedule", example = "Full-time, 40 hours per week")
    private String workSchedule;

    @Schema(description = "Any additional requirements for the job", example = "Knowledge of Docker and Kubernetes is a plus")
    private String additionalRequirements;
}

