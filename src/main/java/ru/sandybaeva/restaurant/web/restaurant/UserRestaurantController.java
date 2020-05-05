package ru.sandybaeva.restaurant.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sandybaeva.restaurant.model.Restaurant;
import ru.sandybaeva.restaurant.service.RestaurantService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestaurantController {

    static final String REST_URL = "/rest/user/restaurants";
    private static final Logger log = LoggerFactory.getLogger(UserRestaurantController.class);

    private final RestaurantService restaurantService;

    public UserRestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<Restaurant> getAllCurrent() {
        log.info("get all restaurants");
        return restaurantService.getAllCurrent(LocalDate.now());
    }

    @GetMapping(value = "/{id}")
    public Restaurant getByIdCurrent(@PathVariable("id") int id) {
        log.info("get all for date {} from restaurant {}", LocalDate.now(), id);
        return restaurantService.getByIdCurrent(LocalDate.now(), id);
    }
}
