package bebra.rzhork_ua_rest.controller;

import bebra.rzhork_ua_rest.model.entity.Company;
import bebra.rzhork_ua_rest.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @Operation(summary = "Get a list of companies", description = "Returns a list of companies based on optional search criteria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of companies"),
            @ApiResponse(responseCode = "400", description = "Invalid filter parameters"),
    })
    @GetMapping
    public List<Company> getCompanies(@Parameter(description = "Page number for pagination", example = "0")
                                      @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                      @Parameter(description = "Name or location of the company", example = "rzhad")
                                      @RequestParam(value = "search", required = false) String search) {
        return companyService.getFilteredCompanies(search, page).getContent();
    }

    @Operation(summary = "Get company details by ID", description = "Returns details of a company by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved company details"),
            @ApiResponse(responseCode = "404", description = "Company not found")
    })
    @GetMapping("/{id}")
    public Company getCompanyDetails(@Parameter(description = "ID of the company", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
                                     @PathVariable UUID id) {
        return companyService.getCompany(id);
    }
}
