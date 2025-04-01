package com.nhlstenden.jabberpoint.files;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.files.loading.FileLoader;
import com.nhlstenden.jabberpoint.files.loading.XMLLoader;
import com.nhlstenden.jabberpoint.files.saving.FileSaver;
import com.nhlstenden.jabberpoint.files.saving.XMLSaver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

    private FileHandler fileHandler;
    private Presentation presentation;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        fileHandler = new FileHandler();
        presentation = new Presentation();
    }

    @Test
    void loadFile_withValidXmlFile_shouldLoadPresentation() throws IOException
    {
        // Arrange
        Path xmlFile = tempDir.resolve("test.xml");
        Files.writeString(xmlFile, """
        <?xml version="1.0" encoding="UTF-8"?>
        <!DOCTYPE presentation [
            <!ELEMENT presentation (showtitle, slide*)>
            <!ELEMENT showtitle (#PCDATA)>
            <!ELEMENT slide (title, item*)>
            <!ELEMENT title (#PCDATA)>
            <!ELEMENT item (#PCDATA)>
            <!ATTLIST item kind CDATA #REQUIRED>
            <!ATTLIST item level CDATA #REQUIRED>
        ]>
        <presentation>
            <showtitle>Test Presentation</showtitle>
            <slide>
                <title>Test Slide</title>
                <item kind="text" level="1">Test Text</item>
            </slide>
        </presentation>
        """);

        // Act
        fileHandler.loadFile(presentation, xmlFile.toString());

        // Assert
        assertEquals("Test Presentation", presentation.getShowTitle());
        assertEquals(1, presentation.getSize());
        assertNotNull(presentation.getSlide(0));
    }

    @Test
    void loadFile_withNonExistentFile_shouldShowErrorMessage()
    {
        // Arrange
        String nonExistentFile = "nonexistent.xml";

        try (MockedStatic<JOptionPane> mockedOptionPane = Mockito.mockStatic(JOptionPane.class)) {
            // Act
            fileHandler.loadFile(presentation, nonExistentFile);

            // Assert
            mockedOptionPane.verify(() -> JOptionPane.showMessageDialog(
                    null,
                    "The file " + nonExistentFile + " does not exist.",
                    "IO Error",
                    JOptionPane.ERROR_MESSAGE
            ));
        }
    }

    @Test
    void loadFile_withUnsupportedFileType_shouldNotModifyPresentation() throws Exception
    {
        // Get initial state
        int initialSlideCount = presentation.getSize();

        // Try loading invalid file
        Path invalidFile = tempDir.resolve("invalid.unsupported");
        Files.writeString(invalidFile, "invalid content");

        fileHandler.loadFile(presentation, invalidFile.toString());

        // Verify no slides were added
        assertEquals(initialSlideCount, presentation.getSize());
    }

    @Test
    void getFileLoaders_shouldReturnRegisteredLoaders()
    {
        // Act
        Set<FileLoader> loaders = fileHandler.getFileLoaders();

        // Assert
        assertEquals(1, loaders.size());
        assertTrue(loaders.stream().anyMatch(loader -> loader instanceof XMLLoader));
    }

    @Test
    void getFileSavers_shouldReturnRegisteredSavers()
    {
        // Act
        Set<FileSaver> savers = fileHandler.getFileSavers();

        // Assert
        assertEquals(1, savers.size());
        assertTrue(savers.stream().anyMatch(saver -> saver instanceof XMLSaver));
    }
}