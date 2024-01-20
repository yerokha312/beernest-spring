package com.neobis.yerokha.beernestspring.controller.rest.admin;

import com.neobis.yerokha.beernestspring.dto.OrderDto;
import com.neobis.yerokha.beernestspring.service.user.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.neobis.yerokha.beernestspring.service.user.OrderService.PAGE_SIZE;

@Tag(
        name = "Admin Orders",
        description = "Admin control panel for orders. ADMIN has full access, and OBSERVER only to GET requests"
)
@ApiResponses({
        @ApiResponse(responseCode = "401", description = "User has not authorized", content = @Content),
        @ApiResponse(responseCode = "403", description = "User has role of CUSTOMER", content = @Content)
})
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/v1/admin/orders")
public class AdminOrderController {

    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(
            summary = "Retrieve all Orders",
            description = "Get pageable list of Order dto from database",
            tags = {"orders", "get"}
    )
    @ApiResponse(responseCode = "200", description = "Success")
    @GetMapping("/all")
    public ResponseEntity<Page<OrderDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders(Pageable.ofSize(PAGE_SIZE)));
    }

    @Operation(
            summary = "Retrieve one Order",
            description = "Get one Order dto from database specified by order's unique identifier",
            tags = {"orders", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Order with id: {orderId} not found.", content = @Content)
    })
    @Parameter(name = "orderId", description = "Unique Order object identifier")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @Operation(
            summary = "Cancel Order",
            description = "Cancel a Customer's Order specified by order's unique identifier",
            tags = {"orders", "put"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order successfully canceled"),
            @ApiResponse(responseCode = "404", description = "Order with id: {orderId} not found.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Only ADMIN has access for PUT")
    })
    @Parameter(name = "orderId", description = "Unique Order object identifier")
    @PutMapping("/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelCustomersOrder(orderId);

        return new ResponseEntity<>("Order successfully canceled", HttpStatus.OK);
    }
}
