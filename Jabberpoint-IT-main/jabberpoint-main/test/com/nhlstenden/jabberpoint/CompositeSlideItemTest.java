package com.nhlstenden.jabberpoint;

import com.nhlstenden.jabberpoint.slide.BitmapItem;
import com.nhlstenden.jabberpoint.slide.CompositeSlideItem;
import com.nhlstenden.jabberpoint.slide.TextItem;
import com.nhlstenden.jabberpoint.style.LevelStyle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.nio.file.Paths;

import static org.mockito.Mockito.*;

public class CompositeSlideItemTest {
    private CompositeSlideItem composite;
    private TextItem textItem;
    private BitmapItem bitmapItem;
    private BitmapItem bitmapItem1;
    private LevelStyle levelStyle;
    private Graphics graphics;
    private ImageObserver observer;

    @BeforeEach
    void setUp() {
        levelStyle = new LevelStyle("Arial", 10, Color.BLACK, 12, 14);
        composite = new CompositeSlideItem(1);
        textItem = new TextItem(2, null);
        bitmapItem = new BitmapItem(2, null);
        bitmapItem1 = new BitmapItem(2, null);
        graphics = mock(Graphics.class);
        observer = mock(ImageObserver.class);

        // Set styles if required by your implementation
        composite.setStyle(levelStyle);
        textItem.setStyle(levelStyle);
        bitmapItem.setStyle(levelStyle);
    }

    @Test
    void addChild_withValidItem_increasesChildrenCount() {
        assertNotNull(composite, "Composite should not be null");
        int initialSize = composite.getChildren().size();
        composite.addChild(textItem);
        assertEquals(initialSize + 1, composite.getChildren().size(),
                "Children count should increase by 1 after adding");
    }

    @Test
    void removeChild_withExistingItem_decreasesChildrenCount() {
        assertNotNull(composite, "Composite should not be null");
        composite.addChild(textItem);
        int sizeAfterAdd = composite.getChildren().size();
        composite.removeChild(textItem);
        assertEquals(sizeAfterAdd - 1, composite.getChildren().size(),
                "Children count should decrease by 1 after removal");
    }

    @Test
    void draw_emptyComposite_noChildren_noExceptionsOrInteractions() {
        // Arrange
        CompositeSlideItem composite = new CompositeSlideItem(1);
        Graphics graphics = mock(Graphics.class);
        Rectangle area = new Rectangle(0, 0, 100, 100);
        ImageObserver observer = mock(ImageObserver.class);

        // Act & Assert
        assertDoesNotThrow(() -> composite.draw(graphics, area, observer));

        // Verify no interactions with graphics
        verifyNoInteractions(graphics);
        verifyNoInteractions(observer);
    }

    @Test
    public void testGetBoundingBox_EmptyComposite_ExpectEqual()
    {
        // Arrange
        CompositeSlideItem composite = new CompositeSlideItem(1);
        Graphics g = mock(Graphics.class);
        ImageObserver observer = mock(ImageObserver.class);

        // Act
        Rectangle box = composite.getBoundingBox(g, observer, 1.0f);

        // Assert
        assertEquals(new Rectangle(0, 0, 0, 0), box);
    }

    @Test
    void testGetBoundingBox_withMissingImage_returnsEmptyRectangle() {
        Graphics graphics = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB).getGraphics();
        Rectangle bounds = bitmapItem1.getBoundingBox(graphics, null, 1.0f);
        assertEquals(0, bounds.width);
        assertEquals(0, bounds.height);
    }
}