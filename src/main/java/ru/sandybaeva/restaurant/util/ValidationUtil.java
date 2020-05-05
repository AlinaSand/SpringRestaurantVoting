package ru.sandybaeva.restaurant.util;

import ru.sandybaeva.restaurant.HasId;
import ru.sandybaeva.restaurant.util.exception.IllegalRequestDataException;
import ru.sandybaeva.restaurant.util.exception.NotFoundException;

import java.time.LocalTime;

public class ValidationUtil {

    private static LocalTime DEADLINE = LocalTime.of(11, 00);

    private ValidationUtil() {
    }

    public static <T> T checkNotFoundWithId(T object, int id, Class<T> clazz) throws NotFoundException {
        return checkNotFound(object, "type: " + clazz.getSimpleName() + ", id=" + id);
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String arg) {
        if (!found) {
            throw new NotFoundException(arg);
        }
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
//      conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }
}
