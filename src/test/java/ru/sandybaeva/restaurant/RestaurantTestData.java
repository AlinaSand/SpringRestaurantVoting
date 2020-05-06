package ru.sandybaeva.restaurant;

import ru.sandybaeva.restaurant.model.Restaurant;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static ru.sandybaeva.restaurant.model.AbstractBaseEntity.START_SEQ;
import static ru.sandybaeva.restaurant.DishTestData.*;

public class RestaurantTestData {

    public static TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingEquals(Restaurant.class);

    public static final int RESTAURANT_ID_1 = START_SEQ + 3;
    public static final int RESTAURANT_ID_2 = START_SEQ + 4;
    public static final int RESTAURANT_ID_3 = START_SEQ + 5;
    public static final int RESTAURANT_ID_4 = START_SEQ + 6;

    public static final Restaurant RESTAURANT_1 = new Restaurant(RESTAURANT_ID_1, "Stolovaya");
    public static final Restaurant RESTAURANT_2 = new Restaurant(RESTAURANT_ID_2, "Zabegalovka na Ordynke");
    public static final Restaurant RESTAURANT_3 = new Restaurant(RESTAURANT_ID_3, "Good Cafe");
    public static final Restaurant RESTAURANT_4 = new Restaurant(RESTAURANT_ID_4, "Dishes");

    static {
        RESTAURANT_1.setDishes(Arrays.asList(DISH_1, DISH_2, DISH_3));
        RESTAURANT_2.setDishes(Arrays.asList(DISH_4, DISH_5, DISH_6));
        RESTAURANT_3.setDishes(Arrays.asList(DISH_7, DISH_8, DISH_9));
    }

    public static Restaurant getCreated() {
        return new Restaurant("New restaurant");
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(RESTAURANT_ID_1, "Restaurant updated");
        updated.setDishes(Arrays.asList(DISH_1, DISH_2, DISH_3));
        return updated;
    }
}
