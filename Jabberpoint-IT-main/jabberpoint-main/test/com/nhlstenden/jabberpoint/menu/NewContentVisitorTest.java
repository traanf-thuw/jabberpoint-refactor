package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.slide.ButtonItem;
import com.nhlstenden.jabberpoint.slide.Slide;
import com.nhlstenden.jabberpoint.slide.SlideViewerFrame;
import com.nhlstenden.jabberpoint.slide.TextItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewContentVisitorTest {

    @Mock
    private CommandContext<Presentation> context;

    @Mock
    private Presentation presentation;

    @Mock
    private SlideViewerFrame frame;

    @Captor
    ArgumentCaptor<Slide> slideCaptor;

    @Test
    void testVisitPresentation_addsSlideWithTextAndButtons() {
        NewContentVisitor<Presentation> visitor = new NewContentVisitor<>(context);

        visitor.visitPresentation(presentation);

        // Verify slide appended and show list reset
        verify(presentation).append(slideCaptor.capture());
        verify(presentation).setShowListNumber(0);

        Slide slide = slideCaptor.getValue();
        assertNotNull(slide);
        assertEquals(3, slide.getSlideItems().size()); // TextItem + 2 Buttons

        // Optionally verify the items added
        assertTrue(slide.getSlideItems().stream().anyMatch(i -> i instanceof TextItem));
        Assertions.assertEquals(2, slide.getSlideItems().stream().filter(i -> i instanceof ButtonItem).count());
    }
}