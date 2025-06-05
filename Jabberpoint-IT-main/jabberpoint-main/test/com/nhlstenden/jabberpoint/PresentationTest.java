package com.nhlstenden.jabberpoint;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.nhlstenden.jabberpoint.slide.*;
import com.nhlstenden.jabberpoint.style.Style;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PresentationTest
{

    private Presentation presentation;

    @Mock
    private SlideViewerComponent mockViewer;

    @Mock
    private SlideItemFactory mockFactory;

    @Mock
    private SlideComponent mockComponent;

    @Mock
    private Slide mockSlide;

    @BeforeEach
    void setUp()
    {
        presentation = new Presentation();
        presentation.setSlideViewComponent(mockViewer);
        presentation.setFactory(mockFactory);
    }

    @Test
    void constructor_initializesEmptyPresentation()
    {
        Presentation newPres = new Presentation();
        assertEquals(0, newPres.getSize());
        assertEquals(0, newPres.getCurrentSlideNumber());
    }

    @Test
    void addSlideComponent_validIndex_addsComponent()
    {
        // Create a real Slide with a mock style
        Slide slide = new Slide(mock(Style.class));
        presentation.append(slide);

        presentation.addSlideComponent(0, mockComponent);

        assertEquals(1, presentation.getSlide(0).getSlideItems().size());
    }

    @Test
    void addSlideComponent_invalidIndex_throwsException()
    {
        assertThrows(IllegalArgumentException.class,
                () -> presentation.addSlideComponent(0, mockComponent));
    }

    @Test
    void append_slide_increasesSize()
    {
        int initialSize = presentation.getSize();
        presentation.append(mockSlide);
        assertEquals(initialSize + 1, presentation.getSize());
    }

    @Test
    void getSlide_invalidIndex_returnsNull()
    {
        assertNull(presentation.getSlide(-1));
        assertNull(presentation.getSlide(0));
    }

    @Test
    void prevSlide_atFirstSlide_doesNothing()
    {
        presentation.append(mockSlide);
        presentation.setShowListNumber(0);
        presentation.previous();
        assertEquals(0, presentation.getCurrentSlideNumber());
    }

    @Test
    void nextSlide_atLastSlide_doesNothing()
    {
        presentation.append(mockSlide);
        presentation.setShowListNumber(0);
        presentation.next();
        assertEquals(0, presentation.getCurrentSlideNumber());
    }

    @Test
    void setSlideNumber_outOfBounds_clampsCorrectly()
    {
        presentation.append(mockSlide);
        presentation.append(mockSlide);

        presentation.setShowListNumber(-5);
        assertEquals(0, presentation.getCurrentSlideNumber());

        presentation.setShowListNumber(100);
        assertEquals(1, presentation.getCurrentSlideNumber());
    }

    @Test
    void refreshView_triggersViewerUpdate()
    {
        presentation.append(mockSlide);
        presentation.refreshView();
        verify(mockViewer).update(presentation, mockSlide);
    }

    @Test
    void clear_resetsSlidesAndSlideNumber()
    {
        presentation.append(mockSlide);
        presentation.clear();
        assertEquals(0, presentation.getSize());
        assertEquals(0, presentation.getCurrentSlideNumber());
    }

    @Test
    void setShowTitle_updatesTitle()
    {
        presentation.setTitle("New Title");
        assertEquals("New Title", presentation.getTitle());
    }

    @Test
    void setSlideViewComponent_updatesViewer()
    {
        SlideViewerComponent newViewer = mock(SlideViewerComponent.class);
        presentation.setSlideViewComponent(newViewer);
        presentation.append(mockSlide);
        presentation.setShowListNumber(0);

        verify(newViewer).update(presentation, mockSlide);
    }

    @Test
    void getSize_returnsCorrectValue()
    {
        assertEquals(0, presentation.getSize());
        presentation.append(mockSlide);
        assertEquals(1, presentation.getSize());
    }

    @Test
    void getCurrentSlide_withSlides_returnsSlide()
    {
        presentation.append(mockSlide);
        assertEquals(mockSlide, presentation.getCurrentSlide());
    }

    @Test
    void setFactory_updatesFactoryInstance()
    {
        SlideItemFactory newFactory = mock(SlideItemFactory.class);
        presentation.setFactory(newFactory);
        assertSame(newFactory, presentation.getFactory());
    }
}