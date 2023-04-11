package co.develhope.statemachine.order.controllers;

import co.develhope.statemachine.order.dto.OrderCreateDTO;
import co.develhope.statemachine.order.dto.OrderDTO;
import co.develhope.statemachine.order.entities.Order;
import co.develhope.statemachine.order.services.OrderService;
import co.develhope.statemachine.user.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('" + Roles.ADMIN + "' , '" + Roles.REGISTERED + "')")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderCreateDTO orderCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderCreateDTO));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('" + Roles.ADMIN + "' , '" + Roles.REGISTERED + "', '" + Roles.RESTAURANT + "', '" + Roles.RIDER + "')")
    public List<OrderDTO> getAllOrders(Principal principal) {
        return orderService.getAllOrders(principal);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('" + Roles.ADMIN + "' , '" + Roles.REGISTERED + "', '" + Roles.RESTAURANT + "', '" + Roles.RIDER + "')")
    @PostAuthorize("hasAnyRole('" + Roles.ADMIN + "') OR returnObject == null OR returnObject.createdBy.id == authentication.principal.id OR returnObject.restaurant.id == authentication.principal.id OR returnObject.rider.id == authentication.principal.id")
    public OrderDTO getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasAnyRole('" + Roles.ADMIN + "' , '" + Roles.REGISTERED + "')")
    public ResponseEntity<OrderDTO> updateOrderById(@RequestBody Order orderEdit, @PathVariable Long id) {
        if(!orderService.canEditOrder(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.updateOrderById(orderEdit, id));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('" + Roles.ADMIN + "' , '" + Roles.REGISTERED + "')")
    public ResponseEntity<String> deleteOrderById(@PathVariable Long id) {
        if(!orderService.canEditOrder(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        orderService.deleteOrderById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Order with " + id + " deleted successfully");
    }


}
