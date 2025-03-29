package com.nhlstenden.jabberpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SlideViewerFrameTest {

    private SlideViewerFrame frame;
    private Presentation presentation;
    private SlideViewerComponent mockViewerComponent;

    @BeforeEach
    void setUp() {
        presentation = new Presentation();
        mockViewerComponent = mock(SlideViewerComponent.class);

        // Use reflection to set the slideViewComponent in presentation
        try {
            Field field = Presentation.class.getDeclaredField("slideViewComponent");
            field.setAccessible(true);
            field.set(presentation, mockViewerComponent);
        } catch (Exception e) {
            fail("Failed to set slideViewComponent via reflection");
        }

        frame = new SlideViewerFrame("Test Title", presentation);
    }

    @Test
    void setupWindow_setsCorrectWindowSize() {
        // Verify the size is set correctly in the constructor
        Dimension expectedSize = new Dimension(SlideViewerFrame.WIDTH, SlideViewerFrame.HEIGHT);
        assertEquals(expectedSize, frame.getSize());
    }

    @Test
    void setupWindow_addsKeyListener() {
        // Verify a KeyListener is added
        assertTrue(frame.getKeyListeners().length > 0);
    }

    @Test
    void createMenuBar_returnsMenuBarWithFileMenu() {
        MenuBar menuBar = frame.createMenuBar(presentation);
        assertNotNull(menuBar);

        // Check if File menu exists
        boolean hasFileMenu = false;
        for (int i = 0; i < menuBar.getMenuCount(); i++) {
            Menu menu = menuBar.getMenu(i);
            if ("File".equals(menu.getLabel())) {
                hasFileMenu = true;
                break;
            }
        }
        assertTrue(hasFileMenu, "MenuBar should contain a File menu");
    }
}