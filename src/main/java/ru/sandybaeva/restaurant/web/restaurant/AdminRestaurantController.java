package ru.sandybaeva.restaurant.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.sandybaeva.restaurant.model.Restaurant;
import ru.sandybaeva.restaurant.service.RestaurantService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.sandybaeva.restaurant.util.ValidationUtil.assureIdConsistent;
import static ru.sandybaeva.restaurant.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {

    static final String REST_URL = "/rest/admin/restaurants";
    private static final Logger log = LoggerFactory.getLogger(AdminRestaurantController.class);

    private final RestaurantService restaurantService;

    public AdminRestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("get all restaurant");
        return restaurantService.getAll();
    }

    @GetMapping(value = "/{id}")
    public Restaurant getById(@PathVariable int id) {
        log.info("get restaurant by id={}", id);
        return restaurantService.getById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody Restaurant restaurant) {
        checkNew(restaurant);
        log.info("create restaurant");
        Restaurant created = restaurantService.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Restaurant update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        assureIdConsistent(restaurant, id);
        log.info("update restaurant with id={}", id);
        return restaurantService.update(restaurant);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant id={}", id);
        restaurantService.delete(id);
    }
}
