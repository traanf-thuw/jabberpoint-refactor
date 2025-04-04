package com.nhlstenden.jabberpoint.style;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class StyleTest {
    private Style style;
    private LevelStyle level1Style;
    private LevelStyle level2Style;

    @BeforeEach
    void setUp() {
        style = new Style();
        level1Style = new LevelStyle("Arial", 10, Color.RED, 24, 15);
        level2Style = new LevelStyle("Times New Roman", 20, Color.BLUE, 18, 10);
    }

    @Test
    void addStyle_withNewLevel_addsStyleToMap() {
        // Act
        style.addStyle(1, level1Style);

        // Assert
        assertEquals(level1Style, style.getStyle(1));
    }

    @Test
    void addStyle_withExistingLevel_overwritesPreviousStyle() {
        // Arrange
        style.addStyle(1, level1Style);
        LevelStyle newStyle = new LevelStyle("Verdana", 15, Color.GREEN, 20, 12);

        // Act
        style.addStyle(1, newStyle);

        // Assert
        assertEquals(newStyle, style.getStyle(1));
    }

    @Test
    void removeStyle_withExistingLevel_removesStyleFromMap() {
        // Arrange
        style.addStyle(1, level1Style);

        // Act
        style.removeStyle(1);

        // Assert
        assertNotEquals(level1Style, style.getStyle(1));
    }

    @Test
    void removeStyle_withNonExistingLevel_doesNothing() {
        // Arrange
        int initialSize = style.styles.size();

        // Act
        style.removeStyle(99);

        // Assert
        assertEquals(initialSize, style.styles.size());
    }

    @Test
    void getStyle_withExistingLevel_returnsCorrectStyle() {
        // Arrange
        style.addStyle(1, level1Style);
        style.addStyle(2, level2Style);

        // Act & Assert
        assertEquals(level1Style, style.getStyle(1));
        assertEquals(level2Style, style.getStyle(2));
    }

    @Test
    void getStyle_withNonExistingLevelButOtherStylesExist_returnsFirstAvailableStyle() {
        // Arrange
        style.addStyle(1, level1Style);
        style.addStyle(2, level2Style);

        // Act
        LevelStyle result = style.getStyle(3);

        // Assert
        assertTrue(result == level1Style || result == level2Style);
    }

    @Test
    void getStyle_withEmptyStylesMap_returnsDefaultStyle() {
        // Act
        LevelStyle result = style.getStyle(1);

        // Assert
        assertEquals("Helvetica", result.getFontName());
        assertEquals(0, result.getIndent());
        assertEquals(Color.BLACK, result.getColor());
        assertEquals(48, result.getFontSize());
        assertEquals(20, result.getLeading());
    }

    @Test
    void getStyle_withMultipleStyles_returnsCorrectStyleForEachLevel() {
        // Arrange
        style.addStyle(1, level1Style);
        style.addStyle(2, level2Style);

        // Act & Assert
        assertEquals(level1Style, style.getStyle(1));
        assertEquals(level2Style, style.getStyle(2));
    }

    @Test
    void getStyle_withNonExistingLevel_returnsFallbackStyle() {
        // Arrange
        style.addStyle(1, level1Style);

        // Act
        LevelStyle result = style.getStyle(2);

        // Assert
        assertEquals(level1Style, result);
    }
}