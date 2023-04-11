package co.develhope.statemachine.order.services;

import co.develhope.statemachine.order.dto.OrderDTO;
import co.develhope.statemachine.order.entities.Order;
import co.develhope.statemachine.order.entities.OrderStateEnum;
import co.develhope.statemachine.order.repositories.OrderRepository;
import co.develhope.statemachine.user.entities.User;
import co.develhope.statemachine.user.services.RiderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class OrderStateService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RiderService riderService;

    public OrderDTO setAccept(Long id) {
        Order order = this.getOrderById(id);
        if(order.getOrderState() != OrderStateEnum.CREATED) {
            throw new RuntimeException("Cannot edit order");
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!Objects.equals(order.getRestaurant().getId(), user.getId())) {
            throw new RuntimeException("This is not your order");
        }
        order.setOrderState(OrderStateEnum.ACCEPTED);
        order.setUpdatedAt(LocalDateTime.now());
        order.setUpdatedBy(user);
        return orderService.convertToDTO(orderRepository.saveAndFlush(order));
    }

    public OrderDTO setInPreparation(Long id) {
        Order order = this.getOrderById(id);
        if(order.getOrderState() != OrderStateEnum.ACCEPTED) {
            throw new RuntimeException("Cannot edit order");
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!Objects.equals(order.getRestaurant().getId(), user.getId())) {
            throw new RuntimeException("This is not your order");
        }
        order.setOrderState(OrderStateEnum.IN_PREPARATION);
        order.setUpdatedAt(LocalDateTime.now());
        order.setUpdatedBy(user);
        return orderService.convertToDTO(orderRepository.saveAndFlush(order));
    }

    public OrderDTO setReady(Long id) {
        Order order = this.getOrderById(id);
        if(order.getOrderState() != OrderStateEnum.IN_PREPARATION) {
            throw new RuntimeException("Cannot edit order");
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!Objects.equals(order.getRestaurant().getId(), user.getId())) {
            throw new RuntimeException("This is not your order");
        }

        //Rider Selection
        User rider = riderService.pickRider();
        if(rider == null || Objects.equals(rider.getId(), order.getCreatedBy().getId())) {
            throw new RuntimeException("Cannot edit order. No riders available at the moment");
        }
        order.setRider(rider);

        order.setOrderState(OrderStateEnum.READY);
        order.setUpdatedAt(LocalDateTime.now());
        order.setUpdatedBy(user);
        return orderService.convertToDTO(orderRepository.saveAndFlush(order));
    }

    public OrderDTO setDelivering(Long id) {
        Order order = this.getOrderById(id);
        if(order.getOrderState() != OrderStateEnum.READY) {
            throw new RuntimeException("Cannot edit order");
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!Objects.equals(order.getRestaurant().getId(), user.getId()) && !Objects.equals(order.getRider().getId(), user.getId())) {
            throw new RuntimeException("This is not your order");
        }
        order.setOrderState(OrderStateEnum.DELIVERING);
        order.setUpdatedAt(LocalDateTime.now());
        order.setUpdatedBy(user);
        return orderService.convertToDTO(orderRepository.saveAndFlush(order));
    }

    public OrderDTO setCompleted(Long id) {
        Order order = this.getOrderById(id);
        if(order.getOrderState() != OrderStateEnum.DELIVERING) {
            throw new RuntimeException("Cannot edit order");
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!Objects.equals(order.getRestaurant().getId(), user.getId()) && !Objects.equals(order.getRider().getId(), user.getId())) {
            throw new RuntimeException("This is not your order");
        }
        order.setOrderState(OrderStateEnum.COMPLETED);
        order.setUpdatedAt(LocalDateTime.now());
        order.setUpdatedBy(user);
        return orderService.convertToDTO(orderRepository.saveAndFlush(order));
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

}
