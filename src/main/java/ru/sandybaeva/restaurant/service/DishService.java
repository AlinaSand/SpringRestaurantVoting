package ru.sandybaeva.restaurant.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.sandybaeva.restaurant.model.Dish;
import ru.sandybaeva.restaurant.model.Restaurant;
import ru.sandybaeva.restaurant.repository.DishRepository;
import ru.sandybaeva.restaurant.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.sandybaeva.restaurant.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    public DishService(DishRepository dishRepository, RestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Dish get(int id) {
        return checkNotFoundWithId(dishRepository.findById(id), id);
    }

    public List<Dish> getAll(int restaurantId) {
        return checkNotFoundWithId(dishRepository.getAll(restaurantId), restaurantId);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public Dish create(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        checkNotFoundWithId(restaurant, restaurantId, Restaurant.class);
        dish.setRestaurant(restaurant);
        if(dish.getDate() == null) {
            dish.setDate(LocalDate.now());
        }
        return dishRepository.save(dish);
    }

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public Dish update(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        dish.setRestaurant(restaurantRepository.getOne(restaurantId));
        checkNotFoundWithId(dishRepository.save(dish), dish.getId());
        return dish;
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(int id) {
        dishRepository.deleteById(id);
    }
}
