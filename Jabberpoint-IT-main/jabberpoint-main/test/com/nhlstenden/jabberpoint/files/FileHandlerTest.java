package com.nhlstenden.jabberpoint.files;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.JOptionPane;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.files.loading.LoadStrategy;
import com.nhlstenden.jabberpoint.files.saving.SaveStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FileHandlerTest
{
    @TempDir
    File tempDir;
    @Mock
    private Presentation mockPresentation;
    @Mock
    private LoadStrategy mockLoadStrategy;
    @Mock
    private SaveStrategy mockSaveStrategy;
    @InjectMocks
    private FileHandler fileHandler;

    @BeforeEach
    void setUp()
    {
        // Reset mocks between tests
        reset(mockPresentation, mockLoadStrategy, mockSaveStrategy);
    }

    @Test
    void loadFile_ValidFile_LoadsSuccessfully() throws Exception
    {
        try (MockedStatic<JOptionPane> mockedPane = mockStatic(JOptionPane.class);
             MockedStatic<FileStrategyFactory> mockedFactory = mockStatic(FileStrategyFactory.class))
        {

            // Arrange
            Path testFile = tempDir.toPath().resolve("test.xml");
            Files.createFile(testFile);

            // Mock static factory method
            mockedFactory.when(() -> FileStrategyFactory.createLoader("xml"))
                    .thenReturn(mockLoadStrategy);

            // Act
            fileHandler.loadFile(mockPresentation, testFile.toString());

            // Assert
            verify(mockLoadStrategy).loadPresentation(mockPresentation, testFile.toFile());
            mockedPane.verifyNoInteractions();
        }
    }

    @Test
    void loadFile_FileNotFound_ShowsError()
    {
        try (MockedStatic<JOptionPane> mockedPane = mockStatic(JOptionPane.class))
        {
            // Act
            fileHandler.loadFile(mockPresentation, "nonexistent.txt");

            // Assert
            mockedPane.verify(() ->
                    JOptionPane.showMessageDialog(
                            null,
                            "The file nonexistent.txt does not exist.",
                            "File Not Found",
                            JOptionPane.ERROR_MESSAGE
                    ));
        }
    }

    @Test
    void loadFile_UnsupportedFormat_ShowsError() throws Exception
    {
        try (MockedStatic<JOptionPane> mockedPane = mockStatic(JOptionPane.class);
             MockedStatic<FileStrategyFactory> mockedFactory = mockStatic(FileStrategyFactory.class))
        {

            // Arrange
            Path testFile = tempDir.toPath().resolve("test.unsupported");
            Files.createFile(testFile);

            mockedFactory.when(() -> FileStrategyFactory.createLoader("unsupported"))
                    .thenThrow(new IllegalArgumentException("Unsupported format"));

            // Act
            fileHandler.loadFile(mockPresentation, testFile.toString());

            // Assert
            mockedPane.verify(() ->
                    JOptionPane.showMessageDialog(
                            null,
                            "File format not supported: Unsupported format",
                            "Unsupported Format",
                            JOptionPane.ERROR_MESSAGE
                    ));
        }
    }

    @Test
    void saveFile_NewFile_SavesSuccessfully() throws Exception
    {
        try (MockedStatic<FileStrategyFactory> mockedFactory = mockStatic(FileStrategyFactory.class))
        {
            // Arrange
            Path testFile = tempDir.toPath().resolve("newfile.xml");

            mockedFactory.when(() -> FileStrategyFactory.createSaver("xml"))
                    .thenReturn(mockSaveStrategy);

            // Act
            fileHandler.saveFile(mockPresentation, testFile.toString());

            // Assert
            verify(mockSaveStrategy).savePresentation(mockPresentation, testFile.toFile());
        }
    }

    @Test
    void saveFile_ExistingFile_OverwritesSuccessfully() throws Exception
    {
        try (MockedStatic<FileStrategyFactory> mockedFactory = mockStatic(FileStrategyFactory.class))
        {
            // Arrange
            Path existingFile = tempDir.toPath().resolve("existing.xml");
            Files.createFile(existingFile);

            mockedFactory.when(() -> FileStrategyFactory.createSaver("xml"))
                    .thenReturn(mockSaveStrategy);

            // Act
            fileHandler.saveFile(mockPresentation, existingFile.toString());

            // Assert
            verify(mockSaveStrategy).savePresentation(mockPresentation, existingFile.toFile());
        }
    }

    @Test
    void extractExtension_ValidFilename_ReturnsExtension()
    {
        assertEquals("xml", fileHandler.extractExtension("presentation.xml"));
        assertEquals("jpg", fileHandler.extractExtension("image.1.JPG"));
    }

    @Test
    void extractExtension_NoExtension_ThrowsException()
    {
        assertThrows(IllegalArgumentException.class,
                () -> fileHandler.extractExtension("README"));
    }

    @Test
    void extractExtension_MultipleDots_ReturnsLastExtension()
    {
        assertEquals("gz", fileHandler.extractExtension("archive.tar.gz"));
    }
}