package org.hibernate.bugs.entities;

public class BooleanUtil {

    private BooleanUtil() {
    }

    public static boolean not(boolean input) {
        return !input;
    }


    public static boolean isNotNull(Object o) {
        return not(o == null);
    }

}
