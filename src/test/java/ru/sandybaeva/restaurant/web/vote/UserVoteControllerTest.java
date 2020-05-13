package ru.sandybaeva.restaurant.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.sandybaeva.restaurant.model.Vote;
import ru.sandybaeva.restaurant.service.VoteService;
import ru.sandybaeva.restaurant.util.exception.IllegalRequestDataException;
import ru.sandybaeva.restaurant.web.AbstractControllerTest;
import ru.sandybaeva.restaurant.web.json.JsonUtil;

import java.time.LocalDate;
import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.sandybaeva.restaurant.UserTestData.*;
import static ru.sandybaeva.restaurant.VoteTestData.*;
import static ru.sandybaeva.restaurant.RestaurantTestData.RESTAURANT_ID_3;
import static ru.sandybaeva.restaurant.RestaurantTestData.RESTAURANT_ID_2;
import static ru.sandybaeva.restaurant.TestUtil.readFromJson;
import static ru.sandybaeva.restaurant.TestUtil.userHttpBasic;

class UserVoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = UserVoteController.REST_URL;

    @Autowired
    private VoteService voteService;

    @Test
    void create() throws Exception {
        Vote created = getCreated();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "/restaurants/" + RESTAURANT_ID_3 + "/votes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created))
                .with(userHttpBasic(USER_2)));
        Vote returned = readFromJson(action, Vote.class);
        created.setId(returned.getId());
        VOTE_MATCHER.assertMatch(voteService.getByUser(USER_ID_2), Arrays.asList(created, VOTE_2));
    }

    @Test
    void createNotFound() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + "/restaurants/66/votes")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER_2)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createByRestaurantWithoutMenuToday() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + "/restaurants/" + RESTAURANT_ID_2 + "/votes")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER_2)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getBetween() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/votes/history")
                .param("startDate", "2020-04-01")
                .param("endDate", "2020-05-01")
                .with(userHttpBasic(USER_1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        VOTE_MATCHER.assertMatch(voteService.getBetweenWithUser(USER_ID_1, LocalDate.of(2020, 4, 1),
                LocalDate.of(2020, 5, 1)), Arrays.asList(VOTE_1));
    }
}