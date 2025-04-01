package com.nhlstenden.jabberpoint;

import com.nhlstenden.jabberpoint.slide.BitmapItem;
import com.nhlstenden.jabberpoint.slide.CompositeSlideItem;
import com.nhlstenden.jabberpoint.slide.TextItem;
import com.nhlstenden.jabberpoint.style.LevelStyle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.awt.Rectangle;
import java.nio.file.Paths;


public class BitmapItemTest {
    private BitmapItem bitmapItem;
    private BitmapItem bitmapItem1;
    private CompositeSlideItem compositeSlideItem;
    private LevelStyle mockStyle;

    @BeforeEach
    void setUp() {
        String imagePath = Paths.get("test/resources/JabberPoint.jpg").toString();
        bitmapItem = new BitmapItem(1, imagePath);
        bitmapItem1 = new BitmapItem(1, null);
        compositeSlideItem = new CompositeSlideItem(1);
        mockStyle= new LevelStyle("Arial", 10, Color.BLACK, 12, 14);
        bitmapItem.setStyle(mockStyle); // Ensure BitmapItem has a style
    }

    @Test
    public void getBoundingBox_withNullImage_returnsZeroSizeRectangle()
    {
        // Arrange
        BitmapItem item = new BitmapItem(1, null);
        Graphics g = mock(Graphics.class);
        ImageObserver observer = mock(ImageObserver.class);

        // Act
        Rectangle box = item.getBoundingBox(g, observer, 1.0f);

        // Assert
        assertEquals(new Rectangle(0, 0, 0, 0), box);
    }

    @Test
    void GetBoundingBox_withMissingImage_returnsEmptyRectangle() {
        Graphics graphics = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB).getGraphics();
        Rectangle bounds = bitmapItem1.getBoundingBox(graphics, null, 1.0f);
        assertEquals(0, bounds.width);
        assertEquals(0, bounds.height);
    }

    @Test
    void getBoundingBox_noImage_returnsZeroSizeRectangle() {
        BitmapItem emptyBitmapItem = new BitmapItem(1, null);
        Rectangle expected = new Rectangle(0, 0, 0, 0);
        assertEquals(expected, emptyBitmapItem.getBoundingBox(null, null, 1.0f));
    }

    @Test
    public void testDraw_WithNullImage_ExpectDoesNotDraw()
    {
        // Arrange
        BitmapItem item = new BitmapItem(1, null);
        Graphics g = mock(Graphics.class);
        Rectangle area = new Rectangle(0, 0, 100, 100);
        ImageObserver observer = mock(ImageObserver.class);

        // Act
        item.draw(g, area, observer);

        // Assert
        verify(g, never()).drawImage(any(), anyInt(), anyInt(), anyInt(), anyInt(), any());
    }

    @Test
    void getChildren_asLeafItem_returnsEmptyList() {
        assertTrue(bitmapItem.getChildren().isEmpty());
    }

    @Test
    void addChild_asLeafItem_throwsUnsupportedOperationException() {;
        assertThrows(UnsupportedOperationException.class, () -> {
            bitmapItem.addChild(new TextItem(2, "Child"));
        });
    }

    @Test
    void removeChild_asLeafItem_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> bitmapItem.removeChild(new TextItem(2, "Child")));
    }
}