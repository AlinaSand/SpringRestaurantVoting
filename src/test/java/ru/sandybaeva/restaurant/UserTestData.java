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

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user1@yandex.ru", "password1", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN, Role.ROLE_USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", new Date(), Collections.singleton(Role.ROLE_USER));
    }
}
