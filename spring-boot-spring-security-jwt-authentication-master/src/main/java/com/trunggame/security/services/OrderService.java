package com.trunggame.security.services;

import com.trunggame.dto.*;
import com.trunggame.models.Order;

import javax.mail.MessagingException;
import java.util.List;

public interface OrderService {
    BaseResponseDTO<?> createOrder(OrderInfoDTO orderInfoDTO) throws MessagingException;

    Order updateOrder(Long id, OrderInfoDTO orderInfoDTO);

    OrderDTO detailById(Long id);

    void deleteOrder(Long id);

    List<Order> getAllOrders(GetOrderDTO getOrderDTO);

    List<Order> getAllOrdersByUserName(GetOrderDTO getOrderDTO);

    List<Order> getCheckOrder();

    void updateOrderStatus(GetOrderDTO getOrderDTO);

    OrderInfoDetailDTO updateOrderDetailStatus(OrderInfoDetailDTO getOrderDTO);
}
