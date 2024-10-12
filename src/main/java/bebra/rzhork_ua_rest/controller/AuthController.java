package bebra.rzhork_ua_rest.controller;

import bebra.rzhork_ua_rest.model.dto.UserWithCompanyDTO;
import bebra.rzhork_ua_rest.model.entity.User;
import bebra.rzhork_ua_rest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @Operation(summary = "Register a new job seeker", description = "Registers a new user with the role of job seeker.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully registered"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Username already exists")
    })
    @PostMapping("/jobseeker/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestParam String username, @RequestParam String password) {
        return userService.saveUser(username, password);
    }

    @Operation(summary = "Register a new company", description = "Registers a new user with the role of company.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Company successfully registered"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Username already exists")
    })
    @PostMapping("/company/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User addCompany(@RequestBody UserWithCompanyDTO userWithCompanyDTO) {
        return userService.saveUserCompany(userWithCompanyDTO);
    }
}