package com.neobis.yerokha.beernestspring.controller.rest;

import com.neobis.yerokha.beernestspring.dto.CreateOrderDto;
import com.neobis.yerokha.beernestspring.dto.OrderDto;
import com.neobis.yerokha.beernestspring.service.user.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.neobis.yerokha.beernestspring.service.user.TokenService.getUserIdFromAuthToken;

@Tag(name = "Orders", description = "Controller for order objects")
@ApiResponses({
        @ApiResponse(responseCode = "401", description = "User is not authorized", content = @Content),
        @ApiResponse(responseCode = "403", description = "User is not CUSTOMER or accessing not own data", content = @Content)
})
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(
            summary = "Create Order",
            description = "Create a complex Order object with contact in it, to order beer",
            tags = {"orders", "post"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order successfully created"),
            @ApiResponse(responseCode = "406", description = "Ordered quantity exceeds available stock",
                    content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<OrderDto> createOrder(Authentication authentication,
                                                @Valid @RequestBody CreateOrderDto dto) {
        return new ResponseEntity<>(orderService.createOrder(getUserIdFromAuthToken(authentication), dto),
                HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get own orders",
            description = "Customer gets all orders made, whether cancelled or received",
            tags = {"orders", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<Page<OrderDto>> getCustomersOrders(Authentication authentication, Pageable pageable) {
        Page<OrderDto> orders = orderService.getAllOrdersByCustomer(getUserIdFromAuthToken(authentication), pageable);
        return ResponseEntity.ok(orders);
    }

    @Operation(
            summary = "Get one order",
            description = "Customer gets an order specified by id number",
            tags = {"orders", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Order with id: {orderId} not found.", content = @Content)
    })
    @Parameter(name = "orderId", description = "Unique identifier of Order object")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(Authentication authentication, @PathVariable Long orderId) {
        OrderDto order = orderService.getOneOrder(getUserIdFromAuthToken(authentication), orderId);
        return ResponseEntity.ok(order);
    }

    @Operation(
            summary = "Cancel order",
            description = "Customer cancels an order specified by id number. Delete mapping used",
            tags = {"orders", "delete"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order successfully canceled"),
            @ApiResponse(responseCode = "404", description = "Order with id: {orderId} not found.", content = @Content)
    })
    @Parameter(name = "orderId", description = "Unique identifier of Order object")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> cancelOrder(Authentication authentication, @PathVariable Long orderId) {
        orderService.cancelOrder(getUserIdFromAuthToken(authentication), orderId);
        return ResponseEntity.ok("Order successfully canceled");
    }
}
