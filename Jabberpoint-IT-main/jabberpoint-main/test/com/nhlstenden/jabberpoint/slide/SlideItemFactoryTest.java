package com.nhlstenden.jabberpoint.slide;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class SlideItemFactoryTest {

    @Test
    void testCreateSlideItem_Text() {
        SlideItemFactory factory = SlideItemFactory.getInstance();
        SlideComponent item = factory.createSlideItem(SlideItemType.TEXT, 1, "Sample text");

        assertNotNull(item);
        assertTrue(item instanceof TextItem);
        assertEquals(1, item.getLevel());
        assertEquals("Sample text", item.getContent());
    }

    @Test
    void testCreateSlideItem_Bitmap() {
        SlideItemFactory factory = SlideItemFactory.getInstance();
        SlideComponent item = factory.createSlideItem(SlideItemType.BITMAP, 2, "image.png");

        assertNotNull(item);
        assertTrue(item instanceof BitmapItem);
        assertEquals(2, item.getLevel());
        assertEquals("image.png", item.getContent());
    }

    @Test
    void testSingletonInstance() {
        SlideItemFactory instance1 = SlideItemFactory.getInstance();
        SlideItemFactory instance2 = SlideItemFactory.getInstance();
        assertSame(instance1, instance2, "Should return the same singleton instance");
    }
}
