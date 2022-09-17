package com.overloaded.overloaded;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestMagicCauldrons {

    @Test
    public void testPartOneBasic() {
        Assertions.assertEquals(
                "20.00",
                MagicCauldrons.calculatePartOne(20, 1, 0, 0)
        );
    }

    @Test
    public void testPartOneBasic2() {
        Assertions.assertEquals(
                "23.00",
                MagicCauldrons.calculatePartOne(23, 1, 0, 0)
        );
    }

    @Test
    public void testPartTwoBasic() {
        Assertions.assertEquals(
                1,
                MagicCauldrons.calculatePartTwo(10, 10.00d, 0, 0)
        );
    }

    @Test
    public void testPartTwoBasic2() {
        Assertions.assertEquals(
                2,
                MagicCauldrons.calculatePartTwo(17, 34.00d, 0, 0)
        );
    }
}
