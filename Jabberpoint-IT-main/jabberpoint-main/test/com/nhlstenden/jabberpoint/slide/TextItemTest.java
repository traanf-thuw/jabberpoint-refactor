package com.nhlstenden.jabberpoint.slide;

import com.nhlstenden.jabberpoint.slide.TextItem;
import com.nhlstenden.jabberpoint.style.LevelStyle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import static org.mockito.Mockito.*;

public class TextItemTest
{

    private TextItem textItem;
    private LevelStyle levelStyle;
    private Graphics2D mockGraphics;
    private ImageObserver observer;
    private FontMetrics mockFontMetrics;

    @BeforeEach
    void setUp()
    {
        textItem = new TextItem(1, "Test Text");
        levelStyle = new LevelStyle("Arial", 10, Color.BLACK, 12, 14);
        textItem.setStyle(levelStyle);

        // Mock Graphics and FontMetrics
        mockGraphics = mock(Graphics2D.class);
        mockFontMetrics = mock(FontMetrics.class);

        // Ensure getFontMetrics returns a non-null FontMetrics
        when(mockGraphics.getFont()).thenReturn(new Font("Arial", Font.PLAIN, 12));
        when(mockGraphics.getFontMetrics(any(Font.class))).thenReturn(mockFontMetrics);
        when(mockFontMetrics.stringWidth(anyString())).thenReturn(50);
        when(mockFontMetrics.getHeight()).thenReturn(14);

        observer = mock(ImageObserver.class);
    }

    @Test
    public void testConstructorAndGetters()
    {
        // Arrange & Act
        TextItem item = new TextItem(1, "Test Text");

        // Assert
        assertEquals(1, item.getLevel());
        assertEquals("Test Text", item.getText());

        // Act
        item.setText("New Text");

        // Assert
        assertEquals("New Text", item.getText());
    }

    @Test
    void draw_withNullGraphics_throwsNullPointerException()
    {
        // Arrange
        Rectangle area = new Rectangle(10, 20, 80, 60);

        // Act & Assert
        assertThrows(NullPointerException.class, () ->
                textItem.draw(null, area, observer));
    }

    @Test
    void draw_withNullArea_throwsNullPointerException()
    {
        // Act & Assert
        assertThrows(NullPointerException.class, () ->
                textItem.draw(mockGraphics, null, observer));
    }

    @Test
    public void testDraw_ExpectPass()
    {
        // Arrange
        TextItem item = new TextItem(1, "Test");

        // Mock LevelStyle
        LevelStyle mockStyle = mock(LevelStyle.class);
        when(mockStyle.getFont()).thenReturn(new Font("Arial", Font.PLAIN, 12));
        when(mockStyle.getColor()).thenReturn(Color.BLACK);
        when(mockStyle.getIndent()).thenReturn(10);
        when(mockStyle.getLeading()).thenReturn(5);
        item.setStyle(mockStyle);

        // Use a real Graphics2D
        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D realGraphics = img.createGraphics();
        realGraphics.setFont(mockStyle.getFont());

        // Spy on Graphics (instead of full mock)
        Graphics g = spy(realGraphics);

        // Other necessary mocks
        Rectangle area = new Rectangle(0, 0, 100, 100);
        ImageObserver observer = mock(ImageObserver.class);

        // Act
        item.draw(g, area, observer);

        // Assert
        verify(g).setFont(mockStyle.getFont());
        verify(g).setColor(mockStyle.getColor());
        verify(g).drawString(eq("Test"), anyInt(), anyInt());

        // Cleanup
        realGraphics.dispose();
    }

    @Test
    void getText_afterConstruction_returnsCorrectText()
    {
        assertEquals("Test Text", textItem.getText());
    }

    @Test
    void setText_withNewText_updatesText()
    {
        textItem.setText("New Text");
        assertEquals("New Text", textItem.getText());
    }

    @Test
    void getBoundingBox_withValidGraphics_returnsNonEmptyRectangle()
    {
        Graphics graphics = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB).getGraphics();
        Rectangle bounds = textItem.getBoundingBox(graphics, null, 1.0f);
        assertNotNull(bounds);
        assertTrue(bounds.width > 0);
    }
}