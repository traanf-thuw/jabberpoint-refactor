package com.nhlstenden.jabberpoint.menu;

import static org.mockito.Mockito.*;

import com.nhlstenden.jabberpoint.Content;
import com.nhlstenden.jabberpoint.slide.SlideViewerFrame;
import org.junit.jupiter.api.Test;


public class NewCommandTest
{
    @Test
    void testExecute() {
        // Arrange
        @SuppressWarnings("unchecked")
        Content content = mock(Content.class);

        SlideViewerFrame frame = mock(SlideViewerFrame.class);
        CommandContext<Content> context = mock(CommandContext.class);

        when(context.getContent()).thenReturn(content);
        when(context.getFrame()).thenReturn(frame);

        NewContentCommand<Content> command = new NewContentCommand<>();

        // Act
        command.execute(context);

        // Assert
        verify(content).clear();
        verify(content).accept(any(NewContentVisitor.class));
        verify(frame).repaint();
    }
}