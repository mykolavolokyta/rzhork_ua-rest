package bebra.rzhork_ua_rest.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO containing information for editing company")
public class EditCompanyDTO {
    @Schema(description = "The title of the company.", example = "Rzhork Inc.")
    private String title;

    @Schema(description = "The location of the company.", example = "Kyiv, Ukraine")
    private String location;

    @Schema(description = "A brief description of the company.", example = "A leading company in tech innovations.")
    private String description;

    @Schema(description = "The year the company joined.", example = "2023")
    private int joinYear;
}
