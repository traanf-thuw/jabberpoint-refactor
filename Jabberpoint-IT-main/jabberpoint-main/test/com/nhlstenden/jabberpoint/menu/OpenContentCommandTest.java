package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Content;
import com.nhlstenden.jabberpoint.files.FileHandler;
import com.nhlstenden.jabberpoint.slide.SlideViewerFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.*;
import java.io.File;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OpenContentCommandTest {

    @Mock
    private SlideViewerFrame frame;

    @Mock
    private Content content;

    @Mock
    private FileHandler fileHandler;

    @Mock
    private CommandContext<Content> context;

    @BeforeEach
    void setUp() {
        when(context.getContent()).thenReturn(content);
        when(context.getFrame()).thenReturn(frame);
        when(content.getTitle()).thenReturn("Test Title");
        when(context.getFileHandler()).thenReturn(fileHandler);
    }

    @Test
    void testExecute_successfulLoad() throws Exception {
        File mockFile = mock(File.class);
        when(mockFile.getAbsolutePath()).thenReturn("example.xml");

        try (
                MockedConstruction<JFileChooser> chooserMock = mockConstruction(JFileChooser.class, (mock, ctx) -> {
                    when(mock.showOpenDialog(any())).thenReturn(JFileChooser.APPROVE_OPTION);
                    when(mock.getSelectedFile()).thenReturn(mockFile);
                })
        ) {
            // Execute
            OpenContentCommand<Content> command = new OpenContentCommand<>();
            command.execute(context);

            // Verify expected behavior
            verify(content).clear();
            verify(fileHandler).loadFile(eq(content), eq("example.xml"));
            verify(content).setShowListNumber(0);
            verify(frame).setTitle(anyString());
            verify(frame).repaint();
        }
    }
}
