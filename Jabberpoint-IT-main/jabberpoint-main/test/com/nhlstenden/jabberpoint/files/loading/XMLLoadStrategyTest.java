package com.nhlstenden.jabberpoint.files.loading;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.slide.BitmapItem;
import com.nhlstenden.jabberpoint.slide.Slide;
import com.nhlstenden.jabberpoint.slide.TextItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class XMLLoadStrategyTest
{
    private XMLLoadStrategy loader;
    private Presentation presentation;
    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp()
    {
        loader = new XMLLoadStrategy();
        presentation = new Presentation();
    }

    @Test
    void loadPresentation_withValidXml_shouldLoadCorrectly() throws Exception
    {
        // Arrange
        String xmlContent = """
                <?xml version="1.0"?>
                <presentation>
                    <showtitle>Test Presentation</showtitle>
                    <slide>
                        <title>Slide 1</title>
                        <item kind="text" level="1">Text content</item>
                        <item kind="image" level="2">image.jpg</item>
                    </slide>
                    <slide>
                        <title>Slide 2</title>
                        <item kind="text" level="2">More text</item>
                    </slide>
                </presentation>
                """;
        File xmlFile = createTempFile(xmlContent);

        // Act
        loader.loadContent(presentation, xmlFile);

        // Assert
        assertEquals("Test Presentation", presentation.getTitle());
        assertEquals(2, presentation.getSize());

        Slide firstSlide = presentation.getSlide(0);
        assertEquals("Slide 1", firstSlide.getTitle());
        assertEquals(2, firstSlide.getSlideItems().size());
        assertTrue(firstSlide.getSlideItems().get(0) instanceof TextItem);
        assertTrue(firstSlide.getSlideItems().get(1) instanceof BitmapItem);

        Slide secondSlide = presentation.getSlide(1);
        assertEquals(1, secondSlide.getSlideItems().size());
    }

    @Test
    void loadPresentation_withMissingLevel_shouldUseDefaultLevel() throws Exception
    {
        String xmlContent = """
                <?xml version="1.0"?>
                <presentation>
                    <showtitle>Test</showtitle>
                    <slide>
                        <title>Slide</title>
                        <item kind="text">No level</item>
                    </slide>
                </presentation>
                """;
        File xmlFile = createTempFile(xmlContent);

        loader.loadContent(presentation, xmlFile);

        Slide slide = presentation.getSlide(0);
        TextItem item = (TextItem) slide.getSlideItems().get(0);
        assertEquals(1, item.getLevel());
    }

    @Test
    void loadPresentation_withInvalidLevel_shouldUseDefault() throws Exception
    {
        String xmlContent = """
                <?xml version="1.0"?>
                <presentation>
                    <showtitle>Test</showtitle>
                    <slide>
                        <title>Slide</title>
                        <item kind="text" level="invalid">Text</item>
                    </slide>
                </presentation>
                """;
        File xmlFile = createTempFile(xmlContent);

        loader.loadContent(presentation, xmlFile);

        TextItem item = (TextItem) presentation.getSlide(0).getSlideItems().get(0);
        assertEquals(1, item.getLevel());
    }

    @Test
    void loadPresentation_withUnknownItemType_shouldSkipItem() throws Exception
    {
        String xmlContent = """
                <?xml version="1.0"?>
                <presentation>
                    <showtitle>Test</showtitle>
                    <slide>
                        <title>Slide</title>
                        <item kind="video" level="1">movie.mp4</item>
                    </slide>
                </presentation>
                """;
        File xmlFile = createTempFile(xmlContent);

        assertThrows(IllegalArgumentException.class, ()->  loader.loadContent(presentation, xmlFile));
    }

    @Test
    void loadPresentation_withMalformedXml_shouldThrowException()
    {
        String badXml = "This is not XML";
        File xmlFile = createTempFile(badXml);

        assertThrows(SAXException.class, () ->
                loader.loadContent(presentation, xmlFile)
        );
    }

    @Test
    void loadPresentation_withMissingShowTitle_shouldThrowException()
    {
        String xmlContent = """
                <?xml version="1.0"?>
                <presentation>
                    <slide>
                        <title>Slide</title>
                    </slide>
                </presentation>
                """;
        File xmlFile = createTempFile(xmlContent);

        assertThrows(NullPointerException.class, () ->
                loader.loadContent(presentation, xmlFile)
        );
    }

    private File createTempFile(String content)
    {
        try
        {
            Path path = tempDir.resolve("test.xml");
            Files.writeString(path, content);
            return path.toFile();
        } catch (IOException e)
        {
            throw new RuntimeException("Failed to create temp file", e);
        }
    }
}