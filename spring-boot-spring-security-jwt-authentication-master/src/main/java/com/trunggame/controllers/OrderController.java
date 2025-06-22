package com.trunggame.controllers;

import com.trunggame.dto.*;
import com.trunggame.models.Order;
import com.trunggame.repository.OrderRepository;
import com.trunggame.security.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public BaseResponseDTO<?>  createOrder(@RequestBody OrderInfoDTO orderInfoDTO) throws MessagingException {
        return orderService.createOrder(orderInfoDTO);
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Order updateOrder(@PathVariable Long id, @RequestBody OrderInfoDTO orderInfoDTO) {
        return orderService.updateOrder(id, orderInfoDTO);
    }

    @GetMapping("/detail/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public OrderDTO detail(@PathVariable Long id) {
        return orderService.detailById(id);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public GetOrderDTO getAllOrders(@ModelAttribute GetOrderDTO getOrderDTO) {
        var dto= GetOrderDTO.builder()
                .orderList(orderService.getAllOrders(getOrderDTO))
                .pageNumber(getOrderDTO.getPageNumber())
                .pageSize(getOrderDTO.getPageSize())
                .code(getOrderDTO.getCode())
                .totalData(orderRepository.count()).build();
        return dto;
    }

    @GetMapping("/filter")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public List<Order> getAllOrdersByUserName(@ModelAttribute GetOrderDTO getOrderDTO) {
        return orderService.getAllOrdersByUserName(getOrderDTO);
    }

    @GetMapping("/check-order")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Order> getCheckOrder() {
        return orderService.getCheckOrder();
    }


    @PostMapping("/update/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public void updateOrderStatus(@RequestBody GetOrderDTO getOrderDTO) {
         orderService.updateOrderStatus(getOrderDTO);
    }


//    @PostMapping("/delete/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public void deleteOrder(@PathVariable Long id) {
//         orderService.deleteOrder(id);
//    }

    @PostMapping("/update/order-detail/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public void updateOrderDetailStatus(@RequestBody OrderInfoDetailDTO orderInfoDetailDTO) {
        orderService.updateOrderDetailStatus(orderInfoDetailDTO);
    }


}
