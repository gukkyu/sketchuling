package com.sketchuling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnumTest {

    public enum Status {
        Y(1, true),
        N(0, false);

        private int value1;
        private boolean value2;

        Status(int value1, boolean value2){
            this.value1 = value1;
            this.value2 = value2;
        }
    }

    @Test
    void statusTest(){
        Status status = Status.Y;

        int value1 = status.value1;
        boolean value2 = status.value2;

        assertEquals(1, value1);
        assertEquals(true, value2);
    }
}
