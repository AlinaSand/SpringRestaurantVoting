package ru.sandybaeva.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Vote vote = (Vote) o;
        return Objects.equals(date, vote.date) &&
                Objects.equals(userId, vote.userId) &&
                Objects.equals(restaurantId, vote.restaurantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), date, userId, restaurantId);
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
