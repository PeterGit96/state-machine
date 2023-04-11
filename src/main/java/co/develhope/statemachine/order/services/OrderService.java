package co.develhope.statemachine.order.services;

import co.develhope.statemachine.order.dto.OrderCreateDTO;
import co.develhope.statemachine.order.dto.OrderDTO;
import co.develhope.statemachine.order.entities.Order;
import co.develhope.statemachine.order.entities.OrderStateEnum;
import co.develhope.statemachine.order.repositories.OrderRepository;
import co.develhope.statemachine.user.dto.UserDTO;
import co.develhope.statemachine.user.entities.User;
import co.develhope.statemachine.user.services.UserService;
import co.develhope.statemachine.user.utils.Roles;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    public OrderDTO createOrder(@NotNull OrderCreateDTO orderCreateDTO) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //Check for restaurant
        UserDTO restaurant = userService.getUserById(orderCreateDTO.getRestaurantId());
        if(!Roles.hasRole(userService.convertToEntity(restaurant), Roles.RESTAURANT)) {
            throw new EntityNotFoundException("Restaurant not found");
        }

        Order order = this.convertToEntity(orderCreateDTO);
        order.setId(null);
        order.setCreatedAt(LocalDateTime.now());
        order.setCreatedBy(user);
        order.setOrderState(OrderStateEnum.CREATED);

        order = orderRepository.saveAndFlush(order);

        OrderDTO orderDTO = this.convertToDTO(order);
        orderDTO.setCreatedBy(userService.convertToDTO(order.getCreatedBy()));
        orderDTO.setUpdatedBy(null);
        orderDTO.setRestaurant(restaurant);
        orderDTO.setRider(null);

        return orderDTO;
    }

    public List<OrderDTO> getAllOrders(@NotNull Principal principal) {
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();

        if(Roles.hasRole(user,Roles.ADMIN)) {
            return getOrdersDTO(orderRepository.findAll());
        }
        else if(Roles.hasRole(user,Roles.RESTAURANT)){
            return getOrdersDTO(orderRepository.findByRestaurant(user));
        }else if(Roles.hasRole(user,Roles.RIDER)){
            return getOrdersDTO(orderRepository.findByRider(user));
        }
        //ROLE_REGISTERED
        return getOrdersDTO(orderRepository.findByCreatedBy(user));
    }

    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        OrderDTO orderDTO = this.convertToDTO(order);
        orderDTO.setCreatedBy(userService.convertToDTO(order.getCreatedBy()));
        UserDTO updatedByDTO = order.getUpdatedBy() != null ? userService.convertToDTO(order.getUpdatedBy()) : null;
        orderDTO.setUpdatedBy(updatedByDTO);
        return orderDTO;
    }

    public OrderDTO updateOrderById(@NotNull Order orderEdit, Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if(orderEdit.getDescription() != null) {
            order.setDescription(orderEdit.getDescription());
        }
        if(orderEdit.getAddress() != null) {
            order.setAddress(orderEdit.getAddress());
        }
        if(orderEdit.getHouseNumber() != null) {
            order.setHouseNumber(orderEdit.getHouseNumber());
        }
        if(orderEdit.getCity() != null) {
            order.setCity(orderEdit.getCity());
        }
        if(orderEdit.getZipCode() != null) {
            order.setZipCode(orderEdit.getZipCode());
        }
        if(orderEdit.getState() != null) {
            order.setState(orderEdit.getState());
        }
        if(orderEdit.getPrice() != null) {
            order.setPrice(orderEdit.getPrice());
        }

        order.setUpdatedAt(LocalDateTime.now());
        order.setUpdatedBy(user);
        order = orderRepository.saveAndFlush(order);

        OrderDTO orderDTO = this.convertToDTO(order);
        orderDTO.setCreatedBy(userService.convertToDTO(order.getCreatedBy()));
        orderDTO.setUpdatedBy(userService.convertToDTO(order.getUpdatedBy()));
        return orderDTO;
    }

    public boolean canEditOrder(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Order> orderOpt = orderRepository.findById(id);
        if(!orderOpt.isPresent()) {
            return false;
        }
        return Objects.equals(orderOpt.get().getCreatedBy().getId(), user.getId())
                || Roles.hasRole(user, Roles.ADMIN);
    }

    public void deleteOrderById(Long id) {
        if(!orderRepository.existsById(id)) {
            throw new EntityNotFoundException("Order not found");
        }
        orderRepository.deleteById(id);
    }

    private List<OrderDTO> getOrdersDTO(List<Order> orders) {
        List<OrderDTO> ordersDTO = new ArrayList<>();
        for(Order order : orders) {
            OrderDTO orderDTO = this.convertToDTO(order);
            orderDTO.setCreatedBy(userService.convertToDTO(order.getCreatedBy()));
            UserDTO updatedByDTO = order.getUpdatedBy() != null ? userService.convertToDTO(order.getUpdatedBy()) : null;
            orderDTO.setUpdatedBy(updatedByDTO);
            ordersDTO.add(orderDTO);
        }
        return ordersDTO;
    }

    public Order convertToEntity(@NotNull OrderCreateDTO orderCreateDTO) {
        return modelMapper.map(orderCreateDTO, Order.class);
    }

    public Order convertToEntity(@NotNull OrderDTO orderDTO) {
        return modelMapper.map(orderDTO, Order.class);
    }

    public OrderDTO convertToDTO(@NotNull Order order) {
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        orderDTO.setUpdateAt(order.getUpdatedAt());
        return orderDTO;
    }

}
