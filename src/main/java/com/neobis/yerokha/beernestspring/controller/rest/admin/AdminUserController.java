package com.neobis.yerokha.beernestspring.controller.rest.admin;

import com.neobis.yerokha.beernestspring.dto.EmployeeDto;
import com.neobis.yerokha.beernestspring.dto.OrderDto;
import com.neobis.yerokha.beernestspring.dto.UserDto;
import com.neobis.yerokha.beernestspring.service.user.OrderService;
import com.neobis.yerokha.beernestspring.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.neobis.yerokha.beernestspring.service.user.UserService.PAGE_SIZE;

@Tag(
        name = "Admin Users",
        description = "Admin control panel for users. ADMIN has full access, and OBSERVER only to GET requests"
)
@ApiResponses({
        @ApiResponse(responseCode = "401", description = "User has not authorized", content = @Content),
        @ApiResponse(responseCode = "403", description = "User has role of CUSTOMER", content = @Content)
})
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/v1/admin/users")
public class AdminUserController {

    private final UserService userService;
    private final OrderService orderService;

    public AdminUserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @Operation(
            summary = "Create Employee",
            description = "Create a new ADMIN or OBSERVER user",
            tags = {"users", "post"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "New employee user created"),
            @ApiResponse(responseCode = "409", description = "The email provided is already taken", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto dto) {
        return new ResponseEntity<>(userService.createEmployee(dto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Retrieve all Customers",
            description = "Get list of Customer dto from database",
            tags = {"users", "get"}
    )
    @ApiResponse(responseCode = "200", description = "Success")
    @GetMapping("/all")
    public ResponseEntity<Page<UserDto>> getAllCustomers() {
        return ResponseEntity.ok(userService.getAllCustomerDtos(Pageable.ofSize(PAGE_SIZE)));
    }

    @Operation(
            summary = "Retrieve Customer",
            description = "Get a Customer dto from database specified by id",
            tags = {"users", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Customer with id: {customerId} not found.")

    })
    @Parameter(name = "customerId", description = "Unique customer object identifier")
    @GetMapping("/{customerId}")
    public ResponseEntity<UserDto> getOneCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(userService.getCustomerDtoById(customerId));
    }

    @Operation(
            summary = "Update Customer",
            description = "Update Customer info specified by id in RequestBody",
            tags = {"users", "put"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Customer with id: {customerId} not found.")

    })
    @Parameter(name = "dto", required = true, schema = @Schema(implementation = UserDto.class))
    @PutMapping("/")
    public ResponseEntity<UserDto> updateCustomer(@RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.updateCustomer(dto));
    }

    @Operation(
            summary = "Delete Customer",
            description = "Delete Customer object from database. (not recommended)",
            tags = {"users", "delete"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Customer with id: {customerId} not found."),
            @ApiResponse(responseCode = "403", description = "Only ADMIN has access to DELETE")
    })
    @Parameter(name = "customerId", description = "Unique Customer object identifier")
    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId) {
        userService.deleteCustomerById(customerId);

        return new ResponseEntity<>("Customer successfully deleted", HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieve Customer's Orders",
            description = "Get pageable list of Order dto of one Customer specified by customerId",
            tags = {"orders", "users", "get"}
    )
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "404", description = "Customer with id: {customerId} not found")
    @Parameter(name = "customerId", description = "Customer's unique identifier")
    @GetMapping("/{customerId}/orders")
    public ResponseEntity<Page<OrderDto>> getAllOrdersByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.getAllOrdersByCustomerId(customerId, Pageable.ofSize(OrderService.PAGE_SIZE)));
    }

}
