package io.github.api.controller;

import io.github.api.domain.Order;
import io.github.api.domain.dto.OrderRequestDTO;
import io.github.api.domain.dto.OrderResponseDTO;
import io.github.api.domain.dto.OrderUpdatePaymentDTO;
import io.github.api.domain.dto.OrderUpdateStatusDTO;
import io.github.api.domain.mapper.OrderMapper;
import io.github.api.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController implements GenericController{

    private final OrderService service;
    private final OrderMapper orderMapper;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<OrderResponseDTO> save(@RequestBody @Valid OrderRequestDTO dto) {
        Order order = orderMapper.toEntity(dto);
        URI uri = headerLocation(order.getId());
        service.saveOrder(order);
        return ResponseEntity.created(uri).body(orderMapper.toResponseDTO(order));
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getOrders() {

        var resultList = service.listOrder();

        List<OrderResponseDTO> orderList = resultList.stream()
                .map(orderMapper::toResponseDTO).collect(Collectors.toList());

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
