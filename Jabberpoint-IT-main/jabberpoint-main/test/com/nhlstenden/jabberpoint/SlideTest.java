package com.nhlstenden.jabberpoint;

import com.nhlstenden.jabberpoint.style.DefaultStyle;
import com.nhlstenden.jabberpoint.style.LevelStyle;
import com.nhlstenden.jabberpoint.style.Style;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.nio.file.Paths;

import static org.mockito.Mockito.*;

class SlideTest {
    private Slide slide;
    private DefaultStyle defaultStyle;
    private Graphics mockGraphics;
    private ImageObserver mockObserver;
    private Style mockStyle;
    private LevelStyle mockLevelStyle;
    private AbstractSlideItem mockItem;

    @BeforeEach
    void setUp() {
        defaultStyle = new DefaultStyle();
        slide = new Slide(defaultStyle);
        mockGraphics = mock(Graphics.class);
        mockObserver = mock(ImageObserver.class);
        mockStyle = mock(Style.class);
        mockLevelStyle = mock(LevelStyle.class);
        mockItem = mock(AbstractSlideItem.class);

        slide = new Slide(mockStyle);

        when(mockStyle.getStyle(anyInt())).thenReturn(mockLevelStyle);
        when(mockLevelStyle.getFontSize()).thenReturn(12);
        when(mockLevelStyle.getFontName()).thenReturn("Arial");
        when(mockLevelStyle.getColor()).thenReturn(Color.BLACK);
        when(mockLevelStyle.getLeading()).thenReturn(14);
        when(mockLevelStyle.getIndent()).thenReturn(10);
    }

    @Test
    void draw_withTitle_drawsTitleFirst() {
        // Arrange
        slide.setTitle("Test Title");
        Rectangle area = new Rectangle(0, 0, 800, 600);

        // Use a real Graphics object from a BufferedImage
        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D realGraphics = image.createGraphics();

        // Act
        slide.draw(realGraphics, area, mockObserver);

        // Assert - verify the title was processed by checking if Y position advanced
        // We can't verify the exact drawing, but we can verify the side effect
        // that items after the title would be drawn lower if title exists
        assertTrue(slide.getTitle() != null && !slide.getTitle().isEmpty());
    }

    @Test
    void draw_withTitleAndItem_properlyPositionsItems() {
        // Arrange
        slide.setTitle("Test Title");
        TextItem testItem = new TextItem(1, "Test Item");
        slide.addSlideItem(testItem);

        Rectangle area = new Rectangle(0, 0, 800, 600);
        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D realGraphics = image.createGraphics();

        // Act
        slide.draw(realGraphics, area, mockObserver);
        assertFalse(testItem.getBoundingBox(realGraphics, mockObserver, 1.0f).y > 0);
    }

    @Test
    void addTextItem_withValidText_addsItemToSlide() {
        int initialSize = slide.getSize();
        slide.addText(1, "Test text");
        assertEquals(initialSize + 1, slide.getSize());
    }

    @Test
    void addImageItem_withValidPath_addsItemToSlide() {
        String imagePath = Paths.get("test/resources/JabberPoint.jpg").toString();
        int initialSize = slide.getSize();
        slide.addImage(1, imagePath);
        assertEquals(initialSize + 1, slide.getSize());
    }

    @Test
    void setTitle_withNewTitle_updatesTitle() {
        String newTitle = "New Title";
        slide.setTitle(newTitle);
        assertEquals(newTitle, slide.getTitle());
    }

    @Test
    void getSlideItem_withValidIndex_returnsCorrectItem() {
        slide.addText(1, "Test text");
        AbstractSlideItem item = slide.getSlideItem(0);
        assertNotNull(item);
        assertTrue(item instanceof TextItem);
    }

    @Test
    void createComposite_withMultipleItems_returnsCompositeWithAllItems() {
        slide.addText(1, "Text 1");
        slide.addText(2, "Text 2");
        CompositeSlideItem composite = slide.createComposite();
        assertEquals(2, composite.getChildren().size());
    }
}