package com.nhlstenden.jabberpoint.files.saving;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.slide.Slide;
import com.nhlstenden.jabberpoint.style.DefaultStyle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InOrder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class XMLSaverTest {

    private XMLSaver xmlSaver;
    private Presentation presentation;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        xmlSaver = new XMLSaver();
        presentation = new Presentation();
        presentation.setShowTitle("Test Presentation");
    }

    @Test
    void savePresentation_shouldCreateValidXmlFile() throws IOException {
        // Arrange
        Slide slide = new Slide(new DefaultStyle());
        slide.setTitle("Test Slide");
        slide.addText(1, "Sample text");
        presentation.append(slide);

        File outputFile = tempDir.resolve("presentation.xml").toFile();

        // Act
        xmlSaver.savePresentation(presentation, outputFile);

        // Assert
        assertTrue(outputFile.exists());
        String content = Files.readString(outputFile.toPath());

        // Verify XML structure
        assertTrue(content.contains("<?xml version=\"1.0\"?>"));
        assertTrue(content.contains("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">"));
        assertTrue(content.contains("<showtitle>Test Presentation</showtitle>"));
        assertTrue(content.contains("<title>Test Slide</title>"));
        assertTrue(content.contains("<item kind=\"text\" level=\"1\">Sample text</item>"));
    }

    @Test
    void savePresentation_withEmptyPresentation_shouldCreateMinimalXml() throws IOException {
        // Arrange
        File outputFile = tempDir.resolve("empty.xml").toFile();

        // Act
        xmlSaver.savePresentation(presentation, outputFile);

        // Assert
        String content = Files.readString(outputFile.toPath());
        assertTrue(content.contains("<presentation>"));
        assertTrue(content.contains("<showtitle>Test Presentation</showtitle>"));
        assertFalse(content.contains("<slide>")); // No slides should be present
    }

    @Test
    void savePresentation_withImageItem_shouldIncludeImagePath() throws IOException {
        // Arrange
        Slide slide = new Slide(new DefaultStyle());
        slide.addImage(1, "test/resources/JabberPoint.jpg");
        presentation.append(slide);

        File outputFile = tempDir.resolve("with_image.xml").toFile();

        // Act
        xmlSaver.savePresentation(presentation, outputFile);

        // Assert
        String content = Files.readString(outputFile.toPath());
        assertTrue(content.contains("<item kind=\"image\" level=\"1\">test/resources/JabberPoint.jpg</item>"));
    }

    @Test
    void savePresentation_toReadOnlyFile_shouldThrowIOException() throws IOException {
        // Arrange
        File readOnlyFile = tempDir.resolve("readonly.xml").toFile();
        Files.write(readOnlyFile.toPath(), "dummy".getBytes(), StandardOpenOption.CREATE);
        assertTrue(readOnlyFile.setReadOnly());

        // Act & Assert
        assertThrows(IOException.class, () ->
                xmlSaver.savePresentation(presentation, readOnlyFile)
        );
    }

    @Test
    void getExtension_shouldReturnXml() {
        assertEquals("xml", xmlSaver.getExtension());
    }

    @Test
    void getShortcut_shouldReturnLowerCaseS() {
        assertEquals('s', xmlSaver.getShortcut());
    }

    @Test
    void savePresentation_shouldMaintainElementOrder() throws IOException {
        // Arrange
        Slide slide1 = new Slide(new DefaultStyle());
        slide1.setTitle("First");
        Slide slide2 = new Slide(new DefaultStyle());
        slide2.setTitle("Second");
        presentation.append(slide1);
        presentation.append(slide2);

        File outputFile = tempDir.resolve("order.xml").toFile();

        // Act
        xmlSaver.savePresentation(presentation, outputFile);

        // Assert
        String content = Files.readString(outputFile.toPath());
        int firstIndex = content.indexOf("<title>First</title>");
        int secondIndex = content.indexOf("<title>Second</title>");
        assertTrue(firstIndex < secondIndex, "Slides should maintain insertion order");
    }
}