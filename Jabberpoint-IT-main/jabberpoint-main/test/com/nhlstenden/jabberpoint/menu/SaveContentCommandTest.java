package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Content;
import com.nhlstenden.jabberpoint.files.FileHandler;
import com.nhlstenden.jabberpoint.slide.SlideViewerFrame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaveContentCommandTest
{

    @Test
    void testExecute_SuccessfulSaveWithExtension() throws Exception
    {
        // Arrange
        Content content = mock(Content.class);
        SlideViewerFrame frame = mock(SlideViewerFrame.class);
        CommandContext<Content> context = mock(CommandContext.class);

        when(context.getContent()).thenReturn(content);
        when(context.getFrame()).thenReturn(frame);

        try (
                MockedConstruction<JFileChooser> mockedFileChooser =
                        Mockito.mockConstruction(JFileChooser.class, (mock, ctx) ->
                        {
                            when(mock.showSaveDialog(any())).thenReturn(JFileChooser.APPROVE_OPTION);

                            File mockFile = mock(File.class);
                            when(mockFile.getAbsolutePath()).thenReturn("testfile");  // Only stub if used
                            when(mock.getSelectedFile()).thenReturn(mockFile);
                        });

                MockedConstruction<FileHandler> mockedFileHandler =
                        Mockito.mockConstruction(FileHandler.class, (mock, ctx) ->
                        {
                            doNothing().when(mock).saveFile(eq(content), eq("testfile.xml"));
                        })
        )
        {
            // Act
            SaveContentCommand<Content> command = new SaveContentCommand<>();
            command.execute(context);

            // Assert
            FileHandler handler = mockedFileHandler.constructed().get(0);
            verify(handler).saveFile(eq(content), eq("testfile.xml"));
        }
    }

    @Test
    void testExecute_ShowErrorDialogOnException() throws Exception
    {
        // Arrange
        Content content = mock(Content.class);
        SlideViewerFrame frame = mock(SlideViewerFrame.class);
        CommandContext<Content> context = mock(CommandContext.class);

        when(context.getContent()).thenReturn(content);
        when(context.getFrame()).thenReturn(frame);

        File file = mock(File.class);
        when(file.getAbsolutePath()).thenReturn("errorfile");

        try (
                MockedConstruction<JFileChooser> mockedFileChooser =
                        Mockito.mockConstruction(JFileChooser.class, (mock, context1) ->
                        {
                            when(mock.showSaveDialog(any())).thenReturn(JFileChooser.APPROVE_OPTION);
                            when(mock.getSelectedFile()).thenReturn(file);
                        });

                MockedConstruction<FileHandler> mockedFileHandler =
                        Mockito.mockConstruction(FileHandler.class, (mock, context1) ->
                        {
                            doThrow(new RuntimeException("Disk Full")).when(mock).saveFile(any(), any());
                        });

                MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class)
        )
        {
            // Act
            SaveContentCommand<Content> command = new SaveContentCommand<>();
            command.execute(context);

            // Assert
            mockedJOptionPane.verify(() -> JOptionPane.showMessageDialog(eq(frame),
                    contains("Disk Full"), anyString(), eq(JOptionPane.ERROR_MESSAGE)));
        }
    }
}
