package com.nhlstenden.jabberpoint;

import com.nhlstenden.jabberpoint.slide.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PresentationTest {

    private Presentation presentation;
    private SlideViewerComponent mockViewer;
    private Slide mockSlide;
    private SlideItemFactory mockFactory;

    @BeforeEach
    void setup() {
        mockViewer = mock(SlideViewerComponent.class);
        presentation = new Presentation(mockViewer);

        mockSlide = mock(Slide.class);
        mockFactory = mock(SlideItemFactory.class);
        presentation.setFactory(mockFactory);
    }

    @Test
    void testConstructorWithoutArgs() {
        Presentation p = new Presentation();
        assertNotNull(p.getShowList());
        assertEquals("", p.getTitle());
    }

    @Test
    void testSetAndGetTitle() {
        presentation.setTitle("Test");
        assertEquals("Test", presentation.getTitle());
    }

    @Test
    void testSetAndGetShowList() {
        List<Slide> slides = new ArrayList<>();
        presentation.setShowList(slides);
        assertEquals(slides, presentation.getShowList());
    }

    @Test
    void testSetAndGetCurrentSlideNumber() {
        presentation.setCurrentSlideNumber(2);
        assertEquals(2, presentation.getCurrentSlideNumber());
    }

    @Test
    void testSetAndGetSlideViewerComponent() {
        assertEquals(mockViewer, presentation.getSlideViewComponent());
    }

    @Test
    void testSetAndGetFactory() {
        assertEquals(mockFactory, presentation.getFactory());
    }

    @Test
    void testAddSlideComponent_ValidIndex() {
        List<Slide> slides = new ArrayList<>();
        slides.add(mockSlide);
        presentation.setShowList(slides);

        SlideComponent mockComponent = mock(SlideComponent.class);
        presentation.addSlideComponent(0, mockComponent);

        verify(mockSlide).addSlideItem(mockComponent);
    }

    @Test
    void testAddSlideComponent_InvalidIndex() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                presentation.addSlideComponent(-1, mock(SlideComponent.class))
        );
        assertEquals("Invalid slide index", exception.getMessage());
    }

    @Test
    void testAddSlideItem() {
        List<Slide> slides = new ArrayList<>();
        slides.add(mockSlide);
        presentation.setShowList(slides);

        SlideComponent mockComponent = mock(SlideComponent.class);
        when(mockFactory.createSlideItem(SlideItemType.TEXT, 1, "hello")).thenReturn(mockComponent);

        presentation.addSlideItem(0, SlideItemType.TEXT, 1, "hello");

        verify(mockFactory).createSlideItem(SlideItemType.TEXT, 1, "hello");
        verify(mockSlide).addSlideItem(mockComponent);
    }

    @Test
    void testAppendAndGetSize() {
        presentation.append(mockSlide);
        assertEquals(1, presentation.getSize());
        assertEquals(mockSlide, presentation.getSlide(0));
    }

    @Test
    void testGetSlide_InvalidIndex() {
        assertNull(presentation.getSlide(-1));
        assertNull(presentation.getSlide(999));
    }

    @Test
    void testGetCurrentSlide() {
        presentation.append(mockSlide);
        presentation.setCurrentSlideNumber(0);
        assertEquals(mockSlide, presentation.getCurrentSlide());
    }

    @Test
    void testClear() {
        presentation.append(mockSlide);
        presentation.clear();
        assertEquals(0, presentation.getShowListSize());
        assertEquals(0, presentation.getCurrentSlideNumber()); // Reset to 0 internally
    }

    @Test
    void testAccept() {
        ContentVisitor mockVisitor = mock(ContentVisitor.class);
        presentation.accept(mockVisitor);
        verify(mockVisitor).visitPresentation(presentation);
    }

    @Test
    void testSetShowListNumber_Negative() {
        presentation.append(mockSlide);
        presentation.setShowListNumber(-1);
        assertEquals(0, presentation.getCurrentSlideNumber());
        verify(mockViewer).update(presentation);
    }

    @Test
    void testSetShowListNumber_TooHigh() {
        presentation.append(mockSlide);
        presentation.setShowListNumber(99);
        assertEquals(0, presentation.getCurrentSlideNumber());
        verify(mockViewer, atLeastOnce()).update(presentation);
    }

    @Test
    void testSetShowListNumber_Valid() {
        presentation.append(mockSlide);
        presentation.setShowListNumber(0);
        assertEquals(0, presentation.getCurrentSlideNumber());
    }

    @Test
    void testPrevious_WhenValid() {
        presentation.append(mockSlide);
        presentation.append(mock(Slide.class));
        presentation.setShowListNumber(1);
        presentation.previous();
        assertEquals(0, presentation.getCurrentSlideNumber());
    }

    @Test
    void testPrevious_WhenAtStart() {
        presentation.append(mockSlide);
        presentation.setShowListNumber(0);
        presentation.previous();
        assertEquals(0, presentation.getCurrentSlideNumber());
    }

    @Test
    void testNext_WhenValid() {
        presentation.append(mockSlide);
        presentation.append(mock(Slide.class));
        presentation.setShowListNumber(0);
        presentation.next();
        assertEquals(1, presentation.getCurrentSlideNumber());
    }

    @Test
    void testNext_WhenAtEnd() {
        presentation.append(mockSlide);
        presentation.setShowListNumber(0);
        presentation.next(); // Should stay at 0 since only one slide
        assertEquals(0, presentation.getCurrentSlideNumber());
    }

    @Test
    void testRefreshView() {
        presentation.append(mockSlide);
        presentation.setShowListNumber(0);
        presentation.refreshView();
        // Just re-triggers update, already verified in setShowListNumber
        verify(mockViewer, atLeast(2)).update(presentation);
    }
}
