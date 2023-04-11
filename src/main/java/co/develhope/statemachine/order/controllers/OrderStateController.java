package co.develhope.statemachine.order.controllers;

import co.develhope.statemachine.order.dto.OrderDTO;
import co.develhope.statemachine.order.entities.Order;
import co.develhope.statemachine.order.services.OrderStateService;
import co.develhope.statemachine.user.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders/{id}/state")
public class OrderStateController {

    @Autowired
    private OrderStateService orderStateService;

    @PreAuthorize("hasAnyRole('" + Roles.RESTAURANT + "')")
    @PutMapping("/accepted")
    public ResponseEntity<OrderDTO> accepted(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderStateService.setAccept(id));

    }

    @PreAuthorize("hasAnyRole('" + Roles.RESTAURANT + "')")
    @PutMapping("/in-preparation")
    public ResponseEntity<OrderDTO> inPreparation(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderStateService.setInPreparation(id));
    }

    @PreAuthorize("hasAnyRole('" + Roles.RESTAURANT + "')")
    @PutMapping("/ready")
    public ResponseEntity<OrderDTO> ready(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderStateService.setReady(id));
    }

    @PreAuthorize("hasAnyRole('" + Roles.RESTAURANT + "' , '" + Roles.RIDER + "')")
    @PutMapping("/delivering")
    public ResponseEntity<OrderDTO> delivering(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderStateService.setDelivering(id));
    }

    @PreAuthorize("hasAnyRole('" + Roles.RESTAURANT + "' , '" + Roles.RIDER + "')")
    @PutMapping("/completed")
    public ResponseEntity<OrderDTO> completed(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderStateService.setCompleted(id));
    }

}
