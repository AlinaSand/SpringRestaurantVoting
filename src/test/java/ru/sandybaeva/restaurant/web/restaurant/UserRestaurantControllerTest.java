package ru.sandybaeva.restaurant.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.sandybaeva.restaurant.service.RestaurantService;
import ru.sandybaeva.restaurant.web.AbstractControllerTest;

import java.time.LocalDate;
import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.sandybaeva.restaurant.TestUtil.userHttpBasic;
import static ru.sandybaeva.restaurant.UserTestData.*;
import static ru.sandybaeva.restaurant.RestaurantTestData.*;

class UserRestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = UserRestaurantController.REST_URL;

    @Autowired
    private RestaurantService restaurantService;

    @Test
    void getAllCurrent() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER_1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        RESTAURANT_MATCHER.assertMatch(restaurantService.getAllCurrent(LocalDate.now()), Arrays.asList(RESTAURANT_3));
    }

    @Test
    void getByIdCurrent() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + RESTAURANT_ID_3)
                .with(userHttpBasic(USER_2)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        RESTAURANT_MATCHER.assertMatch(restaurantService.getByIdCurrent(LocalDate.now(), RESTAURANT_ID_3), RESTAURANT_3);
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/1")
                .with(userHttpBasic(USER_1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getNotFoundRestaurantWithMenuToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + RESTAURANT_ID_1)
                .with(userHttpBasic(USER_1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}