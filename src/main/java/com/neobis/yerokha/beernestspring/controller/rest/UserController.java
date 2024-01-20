package com.neobis.yerokha.beernestspring.controller.rest;

import com.neobis.yerokha.beernestspring.dto.Credentials;
import com.neobis.yerokha.beernestspring.dto.UserDto;
import com.neobis.yerokha.beernestspring.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.neobis.yerokha.beernestspring.service.user.TokenService.getUserIdFromAuthToken;

@Tag(name = "Users", description = "Controller for customers")
@ApiResponses({
        @ApiResponse(responseCode = "401", description = "User is not authorized", content = @Content),
        @ApiResponse(responseCode = "403", description = "User is not CUSTOMER or invalid credentials", content = @Content)
})
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Profile page",
            description = "Show own profile page",
            tags = {"users", "get"}
    )
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "404", description = "Customer not found.", content = @Content)
    @GetMapping("/account")
    public ResponseEntity<UserDto> showProfilePage(Authentication authentication) {
        return ResponseEntity.ok().body(userService.getCustomerDto(getUserIdFromAuthToken(authentication)));
    }

    @Operation(
            summary = "Update profile",
            description = "Modify own profile basic info",
            tags = {"users", "put"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated profile info"),
            @ApiResponse(responseCode = "404", description = "Customer not found.", content = @Content)
    })
    @PutMapping("/update")
    public ResponseEntity<UserDto> updateProfile(Authentication authentication, @RequestBody UserDto userDto) {
        return ResponseEntity.ok().body(userService
                .updateProfileInformation(getUserIdFromAuthToken(authentication), userDto));
    }

    @Operation(
            summary = "Delete account",
            description = "Deletes own account",
            tags = {"users", "delete"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Your account successfully deleted"),
            @ApiResponse(responseCode = "403", description = "Invalid credentials", content = @Content)
    })
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAccount(Authentication authentication, @RequestBody Credentials credentials) {
        userService.setActiveFalse(getUserIdFromAuthToken(authentication), credentials);
        return new ResponseEntity<>("Your account successfully deleted", HttpStatus.OK);
    }

    @Operation(
            summary = "Restore account",
            description = "Endpoint for recovering an account",
            tags = {"users", "put"}
    )
    @ApiResponse(responseCode = "200", description = "Your account successfully restored")
    @PostMapping("/recovery")
    public ResponseEntity<String> restoreAccount(@RequestBody Credentials credentials) {
        userService.setActiveTrue(credentials);
        return new ResponseEntity<>("Your account successfully restored", HttpStatus.OK);
    }
}
