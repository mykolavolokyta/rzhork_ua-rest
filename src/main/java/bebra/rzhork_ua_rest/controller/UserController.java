package bebra.rzhork_ua_rest.controller;

import bebra.rzhork_ua_rest.model.dto.EditCompanyDTO;
import bebra.rzhork_ua_rest.model.entity.Company;
import bebra.rzhork_ua_rest.model.entity.User;
import bebra.rzhork_ua_rest.service.UserService;
import bebra.rzhork_ua_rest.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "Get current user's profile", description = "Returns the profile information of the currently authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/my/profile")
    public User myProfile() {
        String username = SecurityUtils.getCurrentUsername();
        if (username == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return userService.findByUsername(username);
    }

    @Operation(summary = "Get current user's company profile", description = "Returns the company profile of the currently authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company profile successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Company not found")
    })
    @GetMapping("/company/profile")
    public Company companyProfile() {
        String username = SecurityUtils.getCurrentUsername();
        return userService.getCompanyByUsername(username);
    }

    @Operation(summary = "Update user profile", description = "Updates the profile information of the currently authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile successfully updated"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/my/profile/edit")
    public User updateProfile(@Parameter(description = "New name of the user", required = true, example = "Mykola")
            @RequestParam String name) {
        String username = SecurityUtils.getCurrentUsername();
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        user.setName(name);
        return userService.save(user);
    }

    @Operation(summary = "Update company profile", description = "Updates the profile information of the currently authenticated user's company.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company profile successfully updated"),
            @ApiResponse(responseCode = "404", description = "Company not found")
    })
    @PutMapping("/company/profile/edit")
    public Company updateCompanyProfile(@RequestBody EditCompanyDTO dto) {
        String username = SecurityUtils.getCurrentUsername();
        User user = userService.findByUsername(username);
        Company company = userService.getCompanyByUsername(username);

        company.setTitle(dto.getTitle());
        company.setDescription(dto.getDescription());
        company.setLocation(dto.getLocation());
        company.setJoinYear(dto.getJoinYear());
        user.setCompany(company);
        userService.save(user);
        return company;
    }
}
