package ru.sandybaeva.restaurant;


import ru.sandybaeva.restaurant.model.Role;
import ru.sandybaeva.restaurant.model.User;
import ru.sandybaeva.restaurant.web.json.JsonUtil;

import java.util.Collections;
import java.util.Date;

import static ru.sandybaeva.restaurant.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static TestMatcher<User> USER_MATCHER = TestMatcher.usingFieldsComparator(User.class, "registered", "password");

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }

    public static final int ADMIN_ID = START_SEQ;
    public static final int USER_ID_1 = START_SEQ + 1;
    public static final int USER_ID_2 = START_SEQ + 2;

    public static final User USER_1 = new User(USER_ID_1, "User1", "user1@mail.com", "password1", Role.ROLE_USER);
    public static final User USER_2 = new User(USER_ID_2, "User2", "user2@mail.com", "password2", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@mail.com", "admin", Role.ROLE_ADMIN, Role.ROLE_USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", new Date(), Collections.singleton(Role.ROLE_USER));
    }
}
