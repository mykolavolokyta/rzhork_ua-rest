package bebra.rzhork_ua_rest.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO containing information for creating user with company")
public class UserWithCompanyDTO {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    @Schema(description = "The username of the user. It must be between 3 and 20 characters long.", example = "rzhork")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be at least 6 characters")
    @Schema(description = "The password for the user account. It must be at least 6 characters long.", example = "securePassword123")
    private String password;

    @NotBlank(message = "Company title is required")
    @Schema(description = "The title of the company.", example = "Rzhork Inc.")
    private String companyTitle;

    @NotBlank(message = "Company location is required")
    @Schema(description = "The location of the company.", example = "Kyiv, Ukraine")
    private String companyLocation;

    @NotBlank(message = "Company description is required")
    @Schema(description = "A brief description of the company.", example = "A leading company in tech innovations.")
    private String companyDescription;

    @Schema(description = "The year the company joined.", example = "2023")
    private int companyJoinYear;
}
