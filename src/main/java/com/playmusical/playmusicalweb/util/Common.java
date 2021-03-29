package com.playmusical.playmusicalweb.util;

public class Common {

    private Common() {
        throw new IllegalStateException("Hashing Class");
    }

    public static final Long PRETIME_FIRSTRESEVATION = 30L;
    public static final Long PRETIME_SECONDRESERVATION = (10 * 60L);

    public enum POSSIBLE_CANCLE {
        NO, YES
    }
}
