package io.github.api.controller;

import io.github.api.domain.Order;
import io.github.api.domain.dto.OrderRequestDTO;
import io.github.api.domain.dto.OrderResponseDTO;
import io.github.api.domain.dto.OrderUpdatePaymentDTO;
import io.github.api.domain.dto.OrderUpdateStatusDTO;
import io.github.api.domain.enums.OrderStatus;
import io.github.api.domain.mapper.OrderMapper;
import io.github.api.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Tag(name = "Order Controller")
public class OrderController implements GenericController{

    private final OrderService service;
    private final OrderMapper orderMapper;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    @Operation(summary = "Make a order")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description =  "Order created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid data")
    })
    public ResponseEntity<OrderResponseDTO> save(@RequestBody @Valid OrderRequestDTO dto) {
        Order order = orderMapper.toEntity(dto);
        URI uri = headerLocation(order.getId());
        service.saveOrder(order);
        return ResponseEntity.created(uri).body(orderMapper.toResponseDTO(order));
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    @GetMapping
    public ResponseEntity<Page<OrderResponseDTO>> getOrders(
            @RequestParam(value = "days", required = false)
            Integer days,
            @RequestParam(value = "status", required = false)
            OrderStatus status,
            @RequestParam(value = "total", required = false)
            BigDecimal total,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ) {

        var resultList = service.listOrder(days, status, total, pageNo, pageSize);

        Page<OrderResponseDTO> orderList = resultList.map(
                orderMapper::toResponseDTO
        );

        return ResponseEntity.ok(orderList);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserPaymentOrder(@PathVariable String id,
                                                    @RequestBody @Valid OrderUpdatePaymentDTO request) {
        return service.getOrderById(id)
                .map(order -> {
                    OrderRequestDTO newOrder = orderMapper.toUpdatePayment(request);
                    order.setPayment(newOrder.payment());

                    service.updateOrder(order);

                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable String id,
                                               @RequestBody OrderUpdateStatusDTO request){
        return service.getOrderById(id)
                .map(order -> {
                    Order newOrder = orderMapper.toUpdateStatus(request);
                    order.setStatus(newOrder.getStatus());

                    service.updateOrder(order);

                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }


}
