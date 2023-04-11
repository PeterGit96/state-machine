package co.develhope.statemachine.order.entities;

import co.develhope.statemachine.user.entities.User;
import co.develhope.statemachine.user.utils.BaseEntity;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity(name = "orders")
@Table(name = "orders")
@JsonPropertyOrder({"id", "description", "address", "houseNumber", "city", "zipCode", "state", "price", "orderState",
        "restaurant", "rider", "createdAt", "createdBy", "updatedAt", "updatedBy"})
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;
    @Column(nullable = false, name = "description")
    private String description;
    @Column(nullable = false, name = "address")
    private String address;
    @Column(nullable = false, name = "house_number")
    private String houseNumber;
    @Column(nullable = false, name = "city")
    private String city;
    @Column(nullable = false, name = "zip_code")
    private String zipCode;
    @Column(nullable = false, name = "state")
    private String state;
    @Column(nullable = false, name = "price")
    private Double price;
    @Column(nullable = false, name = "order_state")
    @Enumerated(EnumType.STRING)
    private OrderStateEnum orderState;
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User restaurant;
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User rider;

    public Order() { }

    public Order(Long id, String description, String address, String houseNumber, String city, String zipCode, String state,
                 Double price, OrderStateEnum orderState, User restaurant, User rider) {
        this.id = id;
        this.description = description;
        this.address = address;
        this.houseNumber = houseNumber;
        this.city = city;
        this.zipCode = zipCode;
        this.state = state;
        this.price = price;
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

    public OrderStateEnum getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderStateEnum orderState) {
        this.orderState = orderState;
    }

    public User getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(User restaurant) {
        this.restaurant = restaurant;
    }

    public User getRider() {
        return rider;
    }

    public void setRider(User rider) {
        this.rider = rider;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", state='" + state + '\'' +
                ", price=" + price +
                ", orderState=" + orderState +
                ", restaurant=" + restaurant +
                ", rider=" + rider +
                '}';
    }
}
