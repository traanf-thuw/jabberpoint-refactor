package com.nhlstenden.jabberpoint;

import com.nhlstenden.jabberpoint.style.DefaultStyle;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

class PresentationTest {
    private Presentation presentation;
    private Slide testSlide;

    @BeforeEach
    void setUp() {
        presentation = new Presentation();
        testSlide = new Slide(new DefaultStyle());
        testSlide.setTitle("Test Slide");
    }

    @Test
    void append_addSlideToEmptyPresentation_sizeIncreasesByOne() {
        int initialSize = presentation.getSize();
        presentation.append(testSlide);
        assertEquals(initialSize + 1, presentation.getSize());
    }

    @Test
    void getSlideNumber_withNoSlides_returnsNegativeOne() {
        assertEquals(-1, presentation.getSlideNumber());
    }

    @Test
    void setSlideNumber_withValidIndex_updatesCurrentSlide() {
        presentation.append(testSlide);
        presentation.setSlideNumber(0);
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    void setSlideNumber_withInvalidIndex_doesNotChangeCurrentSlide() {
        presentation.append(testSlide);
        int initialSlideNumber = presentation.getSlideNumber();
        presentation.setSlideNumber(-1);
        assertEquals(initialSlideNumber, presentation.getSlideNumber());
    }

    @Test
    void nextSlide_withMultipleSlides_incrementsCurrentSlide() {
        presentation.append(testSlide);
        presentation.append(new Slide(new DefaultStyle()));
        presentation.setSlideNumber(0);
        presentation.nextSlide();
        assertEquals(1, presentation.getSlideNumber());
    }

    @Test
    void nextSlide_atLastSlide_doesNotChangeCurrentSlide() {
        presentation.append(testSlide);
        presentation.setSlideNumber(0);
        presentation.nextSlide();
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    void prevSlide_withMultipleSlides_decrementsCurrentSlide() {
        presentation.append(testSlide);
        presentation.append(new Slide(new DefaultStyle()));
        presentation.setSlideNumber(1);
        presentation.prevSlide();
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    void prevSlide_atFirstSlide_doesNotChangeCurrentSlide() {
        presentation.append(testSlide);
        presentation.setSlideNumber(0);
        presentation.prevSlide();
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    void clear_withPopulatedPresentation_resetsToEmptyState() {
        presentation.append(testSlide);
        presentation.clear();
        assertEquals(0, presentation.getSize());
        assertEquals(-1, presentation.getSlideNumber());
    }
}