package com.nhlstenden.jabberpoint.files.loading;

import static org.junit.jupiter.api.Assertions.*;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.slide.BitmapItem;
import com.nhlstenden.jabberpoint.slide.Slide;
import com.nhlstenden.jabberpoint.slide.TextItem;
import org.junit.jupiter.api.Test;
import java.io.File;

public class DemoLoaderTest {

    @Test
    public void testLoadContent() {
        DemoLoader loader = new DemoLoader();
        Presentation presentation = new Presentation();
        File dummyFile = new File("dummy.xml");  // The file is not actually used in DemoLoader.

        loader.loadContent(presentation, dummyFile);

        // Check title
        assertEquals("Demo Presentation", presentation.getTitle());

        // Check number of slides loaded
        assertEquals(3, presentation.getShowListSize());

        // Check first slide title and some text items
        Slide slide1 = presentation.getSlide(0);
        assertEquals("JabberPoint", slide1.getTitle());
        assertTrue(slide1.getSlideItems().stream()
                .anyMatch(item -> item instanceof TextItem && ((TextItem) item).getText().equals("The Java prestentation tool")));

        // Check second slide title and a text item
        Slide slide2 = presentation.getSlide(1);
        assertEquals("Demonstration of levels and styles", slide2.getTitle());
        assertTrue(slide2.getSlideItems().stream()
                .anyMatch(item -> item instanceof TextItem && ((TextItem) item).getText().equals("Level 1 has style number 1")));

        // Check third slide title and that it contains a BitmapItem
        Slide slide3 = presentation.getSlide(2);
        assertEquals("The third slide", slide3.getTitle());
        assertTrue(slide3.getSlideItems().stream()
                .anyMatch(item -> item instanceof BitmapItem && ((BitmapItem) item).getName().equals("JabberPoint.jpg")));
    }
}