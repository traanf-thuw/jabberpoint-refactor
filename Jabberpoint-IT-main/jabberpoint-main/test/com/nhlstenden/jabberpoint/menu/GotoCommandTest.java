package com.nhlstenden.jabberpoint.menu;

import static org.mockito.Mockito.*;

import javax.swing.*;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.slide.Slide;
import com.nhlstenden.jabberpoint.slide.SlideViewerFrame;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.util.Arrays;
import java.util.List;

public class GotoCommandTest
{
    private GotoCommand command;
    private CommandContext context;
    private Presentation presentation;
    private SlideViewerFrame frame;

    @BeforeEach
    void setup()
    {
        command = new GotoCommand();
        presentation = mock(Presentation.class);
        frame = mock(SlideViewerFrame.class);
        context = new CommandContext(presentation, frame, null);
    }

    @Test
    void testExecute_ValidInputOutOfRange()
    {
        List<Slide> slides = Arrays.asList(mock(Slide.class), mock(Slide.class), mock(Slide.class));
        when(presentation.getShowList()).thenReturn(slides);

        try (MockedStatic<JOptionPane> mocked = mockStatic(JOptionPane.class))
        {
            mocked.when(() -> JOptionPane.showInputDialog(frame, "Page number?")).thenReturn("5");

            command.execute(context);

            verify(presentation, never()).setShowListNumber(anyInt());
            verify(frame, never()).repaint();
            mocked.verify(() -> JOptionPane.showMessageDialog(frame, "Slide number out of range.", "Error", JOptionPane.ERROR_MESSAGE));
        }
    }

    @Test
    void testExecute_InvalidNumberInput()
    {
        List<Slide> slides = Arrays.asList(mock(Slide.class), mock(Slide.class), mock(Slide.class));
        when(presentation.getShowList()).thenReturn(slides);

        try (MockedStatic<JOptionPane> mocked = mockStatic(JOptionPane.class))
        {
            mocked.when(() -> JOptionPane.showInputDialog(frame, "Page number?")).thenReturn("abc");

            command.execute(context);

            verify(presentation, never()).setShowListNumber(anyInt());
            verify(frame, never()).repaint();
            mocked.verify(() -> JOptionPane.showMessageDialog(frame, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE));
        }
    }

    @Test
    void testExecute_NullInput()
    {
        try (MockedStatic<JOptionPane> mocked = mockStatic(JOptionPane.class))
        {
            mocked.when(() -> JOptionPane.showInputDialog(frame, "Page number?")).thenReturn(null);

            command.execute(context);

            verifyNoInteractions(presentation);
            verifyNoInteractions(frame);
            mocked.verify(() -> JOptionPane.showMessageDialog(any(), anyString(), anyString(), anyInt()), never());
        }
    }
}
