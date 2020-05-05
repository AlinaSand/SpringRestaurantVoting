package ru.sandybaeva.restaurant.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.sandybaeva.restaurant.model.Restaurant;
import ru.sandybaeva.restaurant.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.sandybaeva.restaurant.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAllCurrent(LocalDate date) {
        return restaurantRepository.getAllCurrent(date);
    }

    public Restaurant getByIdCurrent(LocalDate date, int id) {
        return checkNotFoundWithId(restaurantRepository.getByIdCurrent(date, id), id);
    }

    public Restaurant getById(int id) {
        return checkNotFoundWithId(restaurantRepository.findById(id).orElse(null), id);
    }

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return checkNotFoundWithId(restaurantRepository.save(restaurant), restaurant.getId());
    }

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(restaurantRepository.delete(id), id);
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }
}
