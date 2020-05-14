package ru.sandybaeva.restaurant.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "dishes", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "date", "restaurant_id"}, name = "dishes_unique_name_date_restaurant_idx"))
public class Dish extends AbstractNamedEntity {

    @Column(name = "date")
    private LocalDate date;

    @NotNull
    @Column(name = "price")
    @Range(min = 1, max = 10000)
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(String name, LocalDate date, int price) {
        this(null, name, date, price);
    }

    public Dish(Integer id, String name, LocalDate date, int price) {
        super(id, name);
        this.date = date;
        this.price = price;
    }

    public Dish(Integer id, String name, LocalDate date, int price, Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.date = date;
        this.restaurant = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", date=" + date +
                ", price=" + price +
                ", name='" + name +
                '}';
    }

}
