package ru.sandybaeva.restaurant.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.sandybaeva.restaurant.model.Restaurant;
import ru.sandybaeva.restaurant.service.RestaurantService;
import ru.sandybaeva.restaurant.web.AbstractControllerTest;
import ru.sandybaeva.restaurant.web.json.JsonUtil;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.sandybaeva.restaurant.TestUtil.userHttpBasic;
import static ru.sandybaeva.restaurant.TestUtil.readFromJson;
import static ru.sandybaeva.restaurant.UserTestData.USER_1;
import static ru.sandybaeva.restaurant.UserTestData.ADMIN;
import static ru.sandybaeva.restaurant.RestaurantTestData.*;

class AdminRestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestaurantController.REST_URL;

    @Autowired
    RestaurantService restaurantService;

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        RESTAURANT_MATCHER.assertMatch(restaurantService.getAll(), Arrays.asList(RESTAURANT_1, RESTAURANT_2, RESTAURANT_3, RESTAURANT_4));
    }

    @Test
    void getById() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + RESTAURANT_ID_1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        RESTAURANT_MATCHER.assertMatch(restaurantService.getById(RESTAURANT_ID_1), RESTAURANT_1);
    }

    @Test
    void createRestaurant() throws Exception {
        Restaurant created = getCreated();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created))
                .with(userHttpBasic(ADMIN)));
        Restaurant returned = readFromJson(action, Restaurant.class);
        created.setId(returned.getId());
        RESTAURANT_MATCHER.assertMatch(restaurantService.getAll(),
                Arrays.asList(RESTAURANT_1, RESTAURANT_2, RESTAURANT_3, RESTAURANT_4, created));
    }

    @Test
    void update() throws Exception {
        Restaurant updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + RESTAURANT_ID_1)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        RESTAURANT_MATCHER.assertMatch(restaurantService.getById(RESTAURANT_ID_1), updated);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + RESTAURANT_ID_1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
        RESTAURANT_MATCHER.assertMatch(restaurantService.getAll(), Arrays.asList(RESTAURANT_2, RESTAURANT_3, RESTAURANT_4));
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER_1)))
                .andExpect(status().isForbidden());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/1")
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}