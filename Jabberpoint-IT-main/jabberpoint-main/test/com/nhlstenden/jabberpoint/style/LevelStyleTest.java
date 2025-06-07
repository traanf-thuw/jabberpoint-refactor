package com.nhlstenden.jabberpoint.style;

import org.junit.jupiter.api.Test;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class LevelStyleTest {

    @Test
    void getFont_withValidFontProperties_returnsFontWithCorrectAttributes() {
        // Arrange
        String fontName = "Times New Roman";
        int fontSize = 18;
        LevelStyle style = new LevelStyle(fontName, 0, Color.BLACK, fontSize, 0);

        // Act
        Font font = style.getFont();

        // Assert
        assertEquals(fontName, font.getName());
        assertEquals(Font.BOLD, font.getStyle());
        assertEquals(fontSize, font.getSize());
    }

    @Test
    void getFontStyle_whenCalled_returnsBoldStyle() {
        // Arrange
        LevelStyle style = new LevelStyle("Helvetica", 10, Color.BLUE, 12, 2);

        // Act & Assert
        assertEquals(Font.BOLD, style.getFontStyle());
    }


    @Test
    void getColor_withCustomColor_returnsSameColor() {
        // Arrange
        Color expectedColor = new Color(128, 128, 128); // Gray
        LevelStyle style = new LevelStyle("Arial", 10, expectedColor, 12, 2);

        // Act & Assert
        assertEquals(expectedColor, style.getColor());
    }

    @Test
    void getFont_withAnyInput_returnsBoldFont() {
        // Arrange
        LevelStyle style = new LevelStyle("Arial", 0, Color.BLACK, 12, 0);

        // Act & Assert
        assertEquals(Font.BOLD, style.getFont().getStyle());
    }

    @Test
    void getIndent_withNegativeValue_returnsSameValue() {
        // Arrange
        LevelStyle style = new LevelStyle("Arial", -10, Color.BLACK, 12, 0);

        // Act & Assert
        assertEquals(-10, style.getIndent());
    }

    @Test
    void getLeading_withZeroValue_returnsZero() {
        // Arrange
        LevelStyle style = new LevelStyle("Arial", 0, Color.BLACK, 12, 0);

        // Act & Assert
        assertEquals(0, style.getLeading());
    }
}