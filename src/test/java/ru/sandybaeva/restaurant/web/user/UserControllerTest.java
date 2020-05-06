package ru.sandybaeva.restaurant.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.sandybaeva.restaurant.model.User;
import ru.sandybaeva.restaurant.service.UserService;
import ru.sandybaeva.restaurant.to.UserTo;
import ru.sandybaeva.restaurant.util.UserUtil;
import ru.sandybaeva.restaurant.web.AbstractControllerTest;
import ru.sandybaeva.restaurant.web.json.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.sandybaeva.restaurant.TestUtil.readFromJson;
import static ru.sandybaeva.restaurant.UserTestData.*;

class UserControllerTest extends AbstractControllerTest {

    private static final String REST_URL = UserController.REST_URL;

    @Autowired
    private UserService userService;

    @Test
    void register() throws Exception {
        UserTo newTo = new UserTo(null, "newName", "newemail@ya.ru", "newPassword");
        User newUser = UserUtil.createNewFromTo(newTo);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andDo(print())
                .andExpect(status().isCreated());

        User created = readFromJson(action, User.class);
        int newId = created.getId();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(userService.getById(newId), newUser);
    }
}