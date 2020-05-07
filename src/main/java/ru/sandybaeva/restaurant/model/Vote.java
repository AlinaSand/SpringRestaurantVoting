package ru.sandybaeva.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "votes", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "date"}, name = "user_unique_vote_idx"))
public class Vote extends AbstractBaseEntity {

    @NotNull
    @Column(name = "date")
    private LocalDate date;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @NotNull
    @Column(name = "restaurant_id")
    private Integer restaurantId;

    public Vote() {
    }

    public Vote(Integer id, Integer userId, Integer restaurantId, LocalDate date) {
        super(id);
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.date = date;
    }

    public Vote(Integer userId, Integer restaurantId, LocalDate date) {
        this(null, userId, restaurantId, date);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurant(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", userId=" + userId +
                ", restaurantId=" + restaurantId +
                ", date=" + date +
                '}';
    }
}
