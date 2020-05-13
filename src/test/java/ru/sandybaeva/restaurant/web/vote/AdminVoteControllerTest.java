package ru.sandybaeva.restaurant.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.sandybaeva.restaurant.service.VoteService;
import ru.sandybaeva.restaurant.web.AbstractControllerTest;

import java.time.LocalDate;
import java.util.Arrays;

import static ru.sandybaeva.restaurant.UserTestData.*;
import static ru.sandybaeva.restaurant.VoteTestData.*;
import static ru.sandybaeva.restaurant.RestaurantTestData.RESTAURANT_ID_1;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.sandybaeva.restaurant.TestUtil.userHttpBasic;

class AdminVoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminVoteController.REST_URL;

    @Autowired
    private VoteService voteService;

    @Test
    void getByRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/restaurant/" + RESTAURANT_ID_1 + "/votes")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        VOTE_MATCHER.assertMatch(voteService.getByRestaurant(RESTAURANT_ID_1), Arrays.asList(VOTE_1));
    }

    @Test
    void getToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/votes/history")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        VOTE_MATCHER.assertMatch(voteService.getBetween(LocalDate.now(), LocalDate.now()), Arrays.asList(VOTE_3));
    }

    @Test
    void getBetween() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/votes/history")
                .param("startDate", "2020-04-01")
                .param("endDate", "2020-05-01")
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        VOTE_MATCHER.assertMatch(voteService.getBetween(LocalDate.of(2020, 4, 1),
                LocalDate.of(2020, 5, 1)), Arrays.asList(VOTE_1, VOTE_2));
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
}