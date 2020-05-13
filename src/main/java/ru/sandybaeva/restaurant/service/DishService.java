package ru.sandybaeva.restaurant.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.sandybaeva.restaurant.model.Dish;
import ru.sandybaeva.restaurant.model.Restaurant;
import ru.sandybaeva.restaurant.repository.DishRepository;
import ru.sandybaeva.restaurant.util.exception.DuplicateDataException;
import ru.sandybaeva.restaurant.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static ru.sandybaeva.restaurant.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {

    private final DishRepository dishRepository;
    private final RestaurantService restaurantService;

    public DishService(DishRepository dishRepository, RestaurantService restaurantService) {
        this.dishRepository = dishRepository;
        this.restaurantService = restaurantService;
    }

    public Dish get(int id) {
        return checkNotFoundWithId(dishRepository.findById(id).orElse(null), id);
    }

    public List<Dish> getAll(int restaurantId) {
        restaurantService.getById(restaurantId);
        return checkNotFoundWithId(dishRepository.getAll(restaurantId), restaurantId);
    }

    @Transactional
    @CacheEvict(value = {"restaurants", "restaurantWithDishToday"}, allEntries = true)
    public Dish create(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        Restaurant restaurant = restaurantService.getById(restaurantId);
        dish.setRestaurant(restaurant);
        dish.setDate(LocalDate.now());
        if (!dishRepository.getByNameAndRestaurantIdAndDate(dish.getName(), dish.getRestaurant().getId(), dish.getDate())
                .isEmpty()) {
            throw new DuplicateDataException("Dish already exists");
        }
        return dishRepository.save(dish);
    }

    @Transactional
    @CacheEvict(value = {"restaurants", "restaurantWithDishToday"}, allEntries = true)
    public Dish update(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        if (dishRepository.findById(dish.getId()).isEmpty()) {
            throw new NotFoundException("No dish found");
        }
        Restaurant restaurant = restaurantService.getById(restaurantId);
        dish.setRestaurant(restaurant);
        dish.setDate(LocalDate.now());
        Dish check = dishRepository.getByNameAndRestaurantIdAndDate(dish.getName(), dish.getRestaurant().getId(), dish.getDate()).orElse(null);
        if (check != null && check.getId() != dish.getId()) {
            throw new DuplicateDataException("Dish already exists");
        }
        checkNotFoundWithId(dishRepository.save(dish), dish.getId());
        return dish;
    }

    @CacheEvict(value = {"restaurants", "restaurantWithDishToday"}, allEntries = true)
    public void delete(int id) {
        dishRepository.deleteById(id);
    }
}
