package net.jwarren.workers.misc;

import org.junit.jupiter.api.Test;

import static org.springframework.test.util.AssertionErrors.assertEquals;

public class StringUtilTest {

    @Test
    public void testDoubleOrNull() {
        assertEquals("String should be parsed", Double.valueOf("1.5"), StringUtil.doubleOrNull("1.5"));
        assertEquals("String should not be parsed", null, StringUtil.doubleOrNull("z1.5"));
    }

    @Test
    public void testToTitleCase() {
        assertEquals("String should be unchanged", "W", StringUtil.toTitleCase("W"));
        assertEquals("String should be capitalised", "Or", StringUtil.toTitleCase("or"));
        assertEquals("String should be null", null, StringUtil.toTitleCase(null));
    }

    @Test
    public void parseCashAmount() {
        assertEquals("Dollar amount should be removed", 1.5f, StringUtil.parseCashAmount("$1.5"));
    }
}
