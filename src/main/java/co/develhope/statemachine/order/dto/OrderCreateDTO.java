package co.develhope.statemachine.order.dto;

import co.develhope.statemachine.order.entities.OrderStateEnum;

public class OrderCreateDTO {

    private Long id;
    private String description;
    private String address;
    private String houseNumber;
    private String city;
    private String zipCode;
    private String state;
    private Double price;
    private OrderStateEnum orderState;
    private Long restaurantId;

    public OrderCreateDTO() { }

    public OrderCreateDTO(Long id, String description, String address, String houseNumber, String city, String zipCode, String state,
                          Double price, OrderStateEnum orderState, Long restaurantId) {
        this.id = id;
        this.description = description;
        this.address = address;
        this.houseNumber = houseNumber;
        this.city = city;
        this.zipCode = zipCode;
        this.state = state;
        this.price = price;
        this.orderState = orderState;
        this.restaurantId = restaurantId;
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

    public OrderStateEnum getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderStateEnum orderState) {
        this.orderState = orderState;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

}
