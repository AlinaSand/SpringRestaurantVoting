package ru.sandybaeva.restaurant;

import ru.sandybaeva.restaurant.model.Dish;

import java.time.LocalDate;

import static ru.sandybaeva.restaurant.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {

    private static final int DISH_ID_1 = START_SEQ + 7;
    private static final int DISH_ID_2 = START_SEQ + 8;
    private static final int DISH_ID_3 = START_SEQ + 9;
    private static final int DISH_ID_4 = START_SEQ + 10;
    private static final int DISH_ID_5 = START_SEQ + 11;
    private static final int DISH_ID_6 = START_SEQ + 12;
    private static final int DISH_ID_7 = START_SEQ + 13;
    private static final int DISH_ID_8 = START_SEQ + 14;
    private static final int DISH_ID_9 = START_SEQ + 15;

    public static final Dish DISH_1 = new Dish(DISH_ID_1, "Borsh", LocalDate.of(2020,04,15), 250);
    public static final Dish DISH_2 = new Dish(DISH_ID_2, "Rice", LocalDate.of(2020,04,15), 300);
    public static final Dish DISH_3 = new Dish(DISH_ID_3, "Coffee", LocalDate.of(2020,04,15), 100);
    public static final Dish DISH_4 = new Dish(DISH_ID_4, "Soup day", LocalDate.of(2020,04,15), 200);
    public static final Dish DISH_5 = new Dish(DISH_ID_5, "Grecha", LocalDate.of(2020,04,15), 200);
    public static final Dish DISH_6 = new Dish(DISH_ID_6, "Coffee", LocalDate.of(2020,04,15), 200);
    public static final Dish DISH_7 = new Dish(DISH_ID_7, "Harcho", LocalDate.now(), 180);
    public static final Dish DISH_8 = new Dish(DISH_ID_8, "Belyash", LocalDate.now(), 100);
    public static final Dish DISH_9 = new Dish(DISH_ID_9, "Bear", LocalDate.now(), 200);

}
