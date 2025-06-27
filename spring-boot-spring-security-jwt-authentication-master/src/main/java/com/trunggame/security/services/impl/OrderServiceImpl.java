package com.trunggame.security.services.impl;

import com.trunggame.dto.*;
import com.trunggame.enums.EUserStatus;
import com.trunggame.models.Order;
import com.trunggame.models.OrderDetail;
import com.trunggame.repository.*;
import com.trunggame.repository.impl.OrderRepositoryCustom;
import com.trunggame.security.services.OrderService;
import com.trunggame.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PackageRepository packageRepository;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    ColorRepository colorRepository;

    @Autowired
    MaterialsRepository materialsRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepositoryCustom orderRepositoryCustom;

    @Override
    @Transactional
    public BaseResponseDTO<?> createOrder(OrderInfoDTO orderInfoDTO) throws MessagingException {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        var currentUser = userRepository.findByUsername(userDetails.getUsername());

        if (currentUser.isEmpty() || currentUser.get().getStatus() == EUserStatus.DELETED) {
            return new BaseResponseDTO<>("User does not exist", 401, 401, null);
        }

        String uuID = UUID.randomUUID().toString();
        var gameOrder = Order.builder()
                .customerId(currentUser.get().getId())
                .createdAt(LocalDateTime.now())
                .customerName(currentUser.get().getUsername())
                .code("TRUNGGAME - " + uuID)
                .status("1") // 1 - cho xu ly, 2 - dang xu ly, 3 - thanh cong , 4 - Huỷ
                .build();
        var gameOrderEntity = orderRepository.save(gameOrder);

        List<OrderDetail> orderDetails = new ArrayList<>();
        BigDecimal sum = new BigDecimal("0");
        for (var item : orderInfoDTO.getItems()) {
            sum = sum.add(item.getAmount());
            var orderDetail = OrderDetail.builder()
//                    .account(item.getAccount())
//                    .gameOrderId(gameOrder.getId())
//                    .characterName(item.getCharacterName())
//                    .serverName(item.getServer())
//                    .loginCode(item.getLoginCode())
//                    .loginType(item.getLoginType())
//                    .password(item.getPassword())
                    .productId(item.getGameId())
                    .amount(item.getAmount())
                    .quantity(item.getQuantity())
                    .price(item.getPrice())
//                    .packageId(item.getPackageId())
                    .createdAt(LocalDateTime.now())
                    .build();
            orderDetails.add(orderDetail);
        }
        orderDetailRepository.saveAll(orderDetails);
        gameOrderEntity.setTotalAmount(sum);
        orderRepository.save(gameOrderEntity);
        userService.sendEmailOrderSuccessful(0, currentUser.get().getFullName(), currentUser.get().getEmail(), uuID, "https://trunggames.com/my-order/" + gameOrderEntity.getId());
        userService.sendEmailOrderSuccessful(1, currentUser.get().getFullName(), "trungbet2512@gmail.com", uuID, "http://trunggames.com:8090/#/order/edit/" + gameOrderEntity.getId());
        return new BaseResponseDTO<>("Success", 200, 200, gameOrderEntity);
    }

    @Override
    @Transactional
    public Order updateOrder(Long id, OrderInfoDTO orderInfoDTO) {
        var gameOrder = orderRepository.findById(id);
        if (gameOrder.isPresent()) {

            // delete all old order detail
            orderDetailRepository.deleteAllByOrderId(gameOrder.get().getId());

            // create new game order detail
            List<OrderDetail> orderDetails = new ArrayList<>();
            for (var item : orderInfoDTO.getItems()) {
                var orderDetail = OrderDetail.builder()
                        .productId(item.getGameId())
                        .amount(item.getAmount())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .createdAt(LocalDateTime.now())
                        .build();
                orderDetails.add(orderDetail);
            }
            orderDetailRepository.saveAll(orderDetails);

        }
        return null;
    }

    @Override
    public OrderDTO detailById(Long id) {
        var orderDetails = new ArrayList<OrderInfoDetailDTO>();
        var gameOrder = orderRepository.findById(id);
        if (gameOrder.isPresent()) {
            var gameOrderDetails = orderDetailRepository.findAllByOrderId(gameOrder.get().getId());
            if (gameOrderDetails.isEmpty()) {
                return null;
            }


            var user = userRepository.findById(gameOrder.get().getCustomerId());
            String phone = "";
            String email = "";
            String name = "";
            EUserStatus userStatus = null;
            if (user.isPresent()) {
                phone = user.get().getPhoneNumber();
                email = user.get().getEmail();
                name = user.get().getFullName();
                userStatus = user.get().getStatus();
            }
            List<OrderDTO> userTradeInfo = orderRepositoryCustom.getCustomerTradeInfo(gameOrder.get().getCustomerId());

            return OrderDTO.builder()
                    .id(gameOrder.get().getId())
                    .code(gameOrder.get().getCode())
                    .orderDetailList(orderDetails)
                    .customerName(name)
                    .createdAt(gameOrder.get().getCreatedAt())
                    .updatedAt(gameOrder.get().getUpdatedAt())
                    .phoneNumber(phone)
                    .email(email)
                    .status(gameOrder.get().getStatus())
                    .tradeCount(userTradeInfo.get(0).getTradeCount())
                    .successCount(userTradeInfo.get(0).getSuccessCount())
                    .totalAmount(gameOrder.get().getTotalAmount())
                    .totalAmountTrade(userTradeInfo.get(0).getTotalAmountTrade())
                    .userStatus(userStatus)
                    .build();
        }
        return null;
    }

    @Override
    public void deleteOrder(Long id) {
        var gameOrder = orderRepository.findById(id);
        if (gameOrder.isPresent()) {
            gameOrder.get().setStatus("4");
            gameOrder.get().setUpdatedAt(LocalDateTime.now());
            orderRepository.save(gameOrder.get());
        }
    }

    @Override
    public List<Order> getCheckOrder() {

        var orderList = orderRepository.findByStatus("1");
        if (!orderList.isEmpty())
            return orderList;
        return null;
    }

    @Override
    public List<Order> getAllOrders(GetOrderDTO getOrderDTO) {
        String orderCode = getOrderDTO.getOrderCode();
        String orderBy = getOrderDTO.getOrderBy();
        String orderType = getOrderDTO.getOrderType();
        int pageSize = Integer.parseInt(getOrderDTO.getPageSize());
        int pageNumber = Integer.parseInt(getOrderDTO.getPageNumber());

        // Use the filters to retrieve all orders from the database
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        Specification<Order> spec = Specification.where(OrderSpecification.orderCodeEqual(orderCode));
        if (getOrderDTO.getCode() != null && getOrderDTO.getCode() != "") {
            spec = spec.and(OrderSpecification.codeLike(getOrderDTO.getCode()));
        }
        if (orderType.equalsIgnoreCase("desc")) {
            pageable = ((PageRequest) pageable).withSort(Sort.by(orderBy).descending());
        }
        return orderRepository.findAll(spec, pageable).getContent();
    }

    @Override
    public List<Order> getAllOrdersByUserName(GetOrderDTO getOrderDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        var user = userRepository.findByUsername(userDetails.getUsername());


        String orderBy = getOrderDTO.getOrderBy();
        String orderType = getOrderDTO.getOrderType();
        int pageSize = Integer.parseInt(getOrderDTO.getPageSize());
        int pageNumber = Integer.parseInt(getOrderDTO.getPageNumber());

        // Use the filters to retrieve all orders from the database
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));

        if (orderType.equalsIgnoreCase("desc")) {
            pageable = ((PageRequest) pageable).withSort(Sort.by(orderBy).descending());
        }
        return orderRepository.findByCustomerId(user.get().getId(), pageable).getContent();
    }

    @Override
    @Transactional
    public void updateOrderStatus(GetOrderDTO getOrderDTO) {
        var order = orderRepository.findById(getOrderDTO.getOrderId());
        if (order.isPresent()) {
            if (Objects.equals(getOrderDTO.getStatus(), "4")) {
                List<OrderDetail> listDetail = orderDetailRepository.findAllByOrderId(order.get().getId());
                if (listDetail.size() > 0) {
                    listDetail.forEach(detail -> {
                        var orderDetail = orderDetailRepository.findById(detail.getId());

                        if (orderDetail.isPresent()) {
                            orderDetail.get().setStatus("2");
                            orderDetailRepository.save(orderDetail.get());
                        }
                    });
                }
            }
            order.get().setStatus(getOrderDTO.getStatus());
            order.get().setTotalAmount(getOrderDTO.getTotalAmount());
            order.get().setUpdatedAt(LocalDateTime.now());
            orderRepository.save(order.get());
        }
    }

    @Override
    public OrderInfoDetailDTO updateOrderDetailStatus(OrderInfoDetailDTO orderInfoDetailDTO) {
        var orderDetail = orderDetailRepository.findById(orderInfoDetailDTO.getId());

        if (orderDetail.isPresent()) {
            var detail = orderDetail.get();
            detail.setStatus(orderInfoDetailDTO.getStatus());
            detail.setPrice(orderInfoDetailDTO.getPrice());
            detail.setQuantity(orderInfoDetailDTO.getQuantity());
            detail.setAmount(orderInfoDetailDTO.getAmount());
            detail.setDescription(orderInfoDetailDTO.getDescription());
            orderDetailRepository.save(detail);
            return orderInfoDetailDTO;
        }
        return null;
    }
}
