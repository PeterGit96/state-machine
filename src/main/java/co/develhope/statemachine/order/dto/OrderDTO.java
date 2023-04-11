package co.develhope.statemachine.order.dto;

import co.develhope.statemachine.order.entities.OrderStateEnum;
import co.develhope.statemachine.user.dto.UserDTO;

import java.time.LocalDateTime;

public class OrderDTO {

    private Long id;
    private String description;
    private String address;
    private String houseNumber;
    private String city;
    private String zipCode;
    private String state;
    private Double price;
    private LocalDateTime createdAt;
    private UserDTO createdBy;
    private LocalDateTime updateAt;
    private UserDTO updatedBy;
    private OrderStateEnum orderState;
    private UserDTO restaurant;
    private UserDTO rider;

    public OrderDTO() { }

    public OrderDTO(Long id, String description, String address, String houseNumber, String city, String zipCode, String state,
                    Double price, LocalDateTime createdAt, UserDTO createdBy, LocalDateTime updateAt, UserDTO updatedBy, OrderStateEnum orderState,
                    UserDTO restaurant, UserDTO rider) {
        this.id = id;
        this.description = description;
        this.address = address;
        this.houseNumber = houseNumber;
        this.city = city;
        this.zipCode = zipCode;
        this.state = state;
        this.price = price;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updateAt = updateAt;
        this.updatedBy = updatedBy;
        this.orderState = orderState;
        this.restaurant = restaurant;
        this.rider = rider;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserDTO createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public UserDTO getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserDTO updatedBy) {
        this.updatedBy = updatedBy;
    }

    public OrderStateEnum getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderStateEnum orderState) {
        this.orderState = orderState;
    }

    public UserDTO getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(UserDTO restaurant) {
        this.restaurant = restaurant;
    }

    public UserDTO getRider() {
        return rider;
    }

    public void setRider(UserDTO rider) {
        this.rider = rider;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", state='" + state + '\'' +
                ", price=" + price +
                ", createdAt=" + createdAt +
                ", createdBy=" + createdBy +
                ", updateAt=" + updateAt +
                ", updatedBy=" + updatedBy +
                ", orderState=" + orderState +
                ", restaurant=" + restaurant +
                ", rider=" + rider +
                '}';
    }
}
