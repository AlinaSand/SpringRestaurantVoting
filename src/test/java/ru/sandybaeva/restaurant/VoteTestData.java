package ru.sandybaeva.restaurant;

import ru.sandybaeva.restaurant.model.Vote;
import java.time.LocalDate;

import static ru.sandybaeva.restaurant.RestaurantTestData.*;
import static ru.sandybaeva.restaurant.UserTestData.USER_ID_2;
import static ru.sandybaeva.restaurant.model.AbstractBaseEntity.START_SEQ;
import static ru.sandybaeva.restaurant.UserTestData.USER_ID_1;

public class VoteTestData {

    public static TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingEquals(Vote.class);

    private static final int VOTE_ID_1 = START_SEQ + 16;
    private static final int VOTE_ID_2 = START_SEQ + 17;
    private static final int VOTE_ID_3 = START_SEQ + 18;

    public static final Vote VOTE_1 = new Vote(VOTE_ID_1, USER_ID_1, RESTAURANT_ID_1, LocalDate.of(2020, 04, 15));
    public static final Vote VOTE_2 = new Vote(VOTE_ID_2, USER_ID_2, RESTAURANT_ID_2, LocalDate.of(2020, 04, 15));
    public static final Vote VOTE_3 = new Vote(VOTE_ID_3, USER_ID_1, RESTAURANT_ID_3, LocalDate.now());

    public static Vote getCreated() {
        return new Vote(START_SEQ + 19, USER_ID_2, RESTAURANT_ID_3, LocalDate.now());
    }
}
