package io.github.api.controller;

import io.github.api.domain.Order;
import io.github.api.domain.dto.OrderRequestDTO;
import io.github.api.domain.dto.OrderResponseDTO;
import io.github.api.domain.mapper.OrderMapper;
import io.github.api.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController implements GenericController{

    private final OrderService service;
    private final OrderMapper orderMapper;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> save(@RequestBody @Valid OrderRequestDTO dto) {
        Order order = orderMapper.toEntityt(dto);
        URI uri = headerLocation(order.getId());
        service.saveOrder(order);
        return ResponseEntity.created(uri).body(orderMapper.toResponseDTO(order));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getOrders() {

        var resultList = service.listOrder();

        List<OrderResponseDTO> orderList = resultList.stream().map(orderMapper::toResponseDTO).toList();

        return ResponseEntity.ok(orderList);
    }
}
