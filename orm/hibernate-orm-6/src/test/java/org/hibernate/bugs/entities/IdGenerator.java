package org.hibernate.bugs.entities;

import java.util.UUID;

public class IdGenerator {

    public static  int    UUID_LENGTH_WITHOUT_SEPARATOR = 32;
    public static  String SEPARATOR                     = "-";

    private IdGenerator() {
    }

    public static String generate(IdPrefix idPrefix) {
        return idPrefix.getPrefix() + "-" + UUID.randomUUID().toString().replace(SEPARATOR, "");
    }


    public static boolean isValidId(IdPrefix idPrefix, String value) {
        return isLengthCorrect(idPrefix, value) && isStartingWithPrefix(idPrefix, value);
    }

    private static boolean isLengthCorrect(IdPrefix idPrefix, String value) {
        return value.length() == lengthOfPrefixWithUuid(idPrefix);
    }

    private static boolean isStartingWithPrefix(IdPrefix idPrefix, String value) {
        return value.startsWith(idPrefix.getPrefix());
    }

    private static int lengthOfPrefixWithUuid(IdPrefix idPrefix) {
        return idPrefix.getPrefix().length() + SEPARATOR.length() + UUID_LENGTH_WITHOUT_SEPARATOR;
    }

}
