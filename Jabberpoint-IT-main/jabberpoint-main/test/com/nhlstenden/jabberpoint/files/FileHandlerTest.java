package com.nhlstenden.jabberpoint.files;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.files.loading.LoadStrategy;
import com.nhlstenden.jabberpoint.files.saving.SaveStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileHandlerTest {

    private FileHandler fileHandler;
    private Presentation mockPresentation;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        fileHandler = new FileHandler();
        mockPresentation = mock(Presentation.class);
    }

    @Test
    void loadFile_withExistingFile_shouldDelegateToLoader() throws Exception {
        // Arrange
        Path validFile = tempDir.resolve("test.xml");
        Files.writeString(validFile, "<content>");

        LoadStrategy mockLoader = mock(LoadStrategy.class);
        try (MockedStatic<FileStrategyFactory> factory = mockStatic(FileStrategyFactory.class)) {
            factory.when(() -> FileStrategyFactory.createLoader("xml"))
                    .thenReturn(mockLoader);

            // Act
            fileHandler.loadFile(mockPresentation, validFile.toString());

            // Assert
            verify(mockLoader).loadPresentation(mockPresentation, validFile.toFile());
        }
    }

    @Test
    void loadFile_withMissingFile_shouldShowErrorMessage() {
        try (MockedStatic<JOptionPane> optionPane = mockStatic(JOptionPane.class)) {
            // Act
            fileHandler.loadFile(mockPresentation, "missing.txt");

            // Assert
            optionPane.verify(() -> JOptionPane.showMessageDialog(
                    null,
                    "The file missing.txt does not exist.",
                    "File Not Found",
                    JOptionPane.ERROR_MESSAGE
            ));
        }
    }

    @Test
    void saveFile_withValidFile_shouldDelegateToSaver() throws Exception {
        // Arrange
        Path outputFile = tempDir.resolve("test.xml");
        SaveStrategy mockSaver = mock(SaveStrategy.class);

        try (MockedStatic<FileStrategyFactory> factory = mockStatic(FileStrategyFactory.class)) {
            factory.when(() -> FileStrategyFactory.createSaver("xml"))
                    .thenReturn(mockSaver);

            // Act
            fileHandler.saveFile(mockPresentation, outputFile.toString());

            // Assert
            verify(mockSaver).savePresentation(mockPresentation, outputFile.toFile());
        }
    }

    @Test
    void extractExtension_withValidFileName_shouldReturnExtension() {
        assertEquals("xml", fileHandler.extractExtension("presentation.xml"));
        assertEquals("txt", fileHandler.extractExtension("notes.TXT"));
        assertEquals("jpg", fileHandler.extractExtension("image.1.jpg"));
    }

    @Test
    void extractExtension_withNoExtension_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                fileHandler.extractExtension("README")
        );
    }

    @Test
    void showError_shouldDisplayCorrectDialog() {
        try (MockedStatic<JOptionPane> optionPane = mockStatic(JOptionPane.class)) {
            // Act
            fileHandler.showError("Test Title", "Test Message");

            // Assert
            optionPane.verify(() -> JOptionPane.showMessageDialog(
                    null,
                    "Test Message",
                    "Test Title",
                    JOptionPane.ERROR_MESSAGE
            ));
        }
    }
}