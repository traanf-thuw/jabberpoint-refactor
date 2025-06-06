package com.nhlstenden.jabberpoint.style;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

public class DefaultStyleTest {

    @Test
    void testDefaultStyleInitialization() {
        DefaultStyle defaultStyle = new DefaultStyle();

        // Check keys
        for (int level = 0; level <= 4; level++) {
            assertTrue(defaultStyle.styles.containsKey(level), "Level " + level + " should be in styles map");
        }

        // Verify contents for each level
        LevelStyle style0 = defaultStyle.styles.get(0);
        assertEquals("Helvetica", style0.getFontName());
        assertEquals(1, style0.getFontStyle());
        assertEquals(Color.red, style0.getColor());
        assertEquals(5, style0.getIndent());

        LevelStyle style1 = defaultStyle.styles.get(1);
        assertEquals("Helvetica", style1.getFontName());
        assertEquals(1, style1.getFontStyle());
        assertEquals(Color.blue, style1.getColor());
        assertEquals(10, style1.getIndent());

        LevelStyle style2 = defaultStyle.styles.get(2);
        assertEquals("Helvetica", style2.getFontName());
        assertEquals(1, style2.getFontStyle());
        assertEquals(Color.black, style2.getColor());
        assertEquals(20, style2.getIndent());

        LevelStyle style3 = defaultStyle.styles.get(3);
        assertEquals("Helvetica", style3.getFontName());
        assertEquals(1, style3.getFontStyle());
        assertEquals(Color.black, style3.getColor());
        assertEquals(50, style3.getIndent());

        LevelStyle style4 = defaultStyle.styles.get(4);
        assertEquals("Helvetica", style4.getFontName());
        assertEquals(1, style4.getFontStyle());
        assertEquals(Color.black, style4.getColor());
        assertEquals(70, style4.getIndent());
    }
}
