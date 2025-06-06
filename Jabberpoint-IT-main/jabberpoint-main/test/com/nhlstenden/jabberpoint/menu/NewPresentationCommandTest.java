package com.nhlstenden.jabberpoint.menu;

import static org.mockito.Mockito.*;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.slide.Slide;
import com.nhlstenden.jabberpoint.slide.SlideViewerFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class NewPresentationCommandTest
{
    Presentation presentation;
    SlideViewerFrame frame;
    CommandContext context;
    NewPresentationCommand command;

    @BeforeEach
    void setup()
    {
        presentation = mock(Presentation.class);
        frame = mock(SlideViewerFrame.class);
        context = new CommandContext(presentation, frame, null);
        command = new NewPresentationCommand();
    }

    @Test
    void testExecute_createsNewSlideAndAddsDefaultItems()
    {
        // We want to capture the Slide appended to Presentation
        doNothing().when(presentation).clear();
        doNothing().when(presentation).append(any(Slide.class));
        doNothing().when(presentation).setSlideNumber(anyInt());

        command.execute(context);

        // Verify clear called once
        verify(presentation, times(1)).clear();

        // Verify append called once with a Slide instance
        verify(presentation, times(1)).append(any(Slide.class));

        // Verify slide number set to 0
        verify(presentation, times(1)).setSlideNumber(0);
    }
}