//package com.nhlstenden.jabberpoint.files.loading;
//
//import com.nhlstenden.jabberpoint.Presentation;
//import com.nhlstenden.jabberpoint.slide.Slide;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.io.TempDir;
//import org.w3c.dom.Document;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class XMLLoaderTest
//{
//
//    private XMLLoadStrategy xmlLoader;
//    private Presentation presentation;
//
//    @TempDir
//    Path tempDir;
//
//    @BeforeEach
//    void setUp()
//    {
//        xmlLoader = new XMLLoadStrategy();
//        presentation = new Presentation();
//    }
//
//    @Test
//    void loadPresentation_withValidXml_shouldLoadCorrectly() throws IOException
//    {
//        // Arrange
//        Path xmlFile = tempDir.resolve("valid.xml");
//        Files.writeString(xmlFile, """
//            <?xml version="1.0"?>
//            <!DOCTYPE presentation [
//                <!ELEMENT presentation (showtitle, slide*)>
//                <!ELEMENT showtitle (#PCDATA)>
//                <!ELEMENT slide (title, item*)>
//                <!ELEMENT title (#PCDATA)>
//                <!ELEMENT item (#PCDATA)>
//                <!ATTLIST item kind CDATA #REQUIRED>
//                <!ATTLIST item level CDATA #REQUIRED>
//            ]>
//            <presentation>
//                <showtitle>Test Presentation</showtitle>
//                <slide>
//                    <title>Slide 1</title>
//                    <item kind="text" level="1">Text content</item>
//                </slide>
//            </presentation>
//            """);
//
//        // Act
//        xmlLoader.loadPresentation(presentation, xmlFile.toFile());
//
//        // Assert
//        assertEquals("Test Presentation", presentation.getShowTitle());
//        assertEquals(1, presentation.getSize());
//        Slide slide = presentation.getSlide(0);
//        assertNotNull(slide);
//        assertEquals("Slide 1", slide.getTitle());
//    }
//
//    @Test
//    void loadPresentation_withInvalidItemType_shouldHandleGracefully() throws Exception
//    {
//        // Arrange
//        Path xmlFile = tempDir.resolve("invalid_item.xml");
//        Files.writeString(xmlFile, """
//            <?xml version="1.0"?>
//            <presentation>
//                <showtitle>Test</showtitle>
//                <slide>
//                    <title>Slide</title>
//                    <item kind="invalid_type" level="1">Content</item>
//                </slide>
//            </presentation>
//            """);
//
//        // Act
//        xmlLoader.loadPresentation(presentation, xmlFile.toFile());
//
//        // Assert - Should load but skip invalid items
//        assertEquals(1, presentation.getSize());
//    }
//
//    @Test
//    void getExtension_shouldReturnXml()
//    {
//        assertEquals("xml", xmlLoader.getExtension());
//    }
//
//    @Test
//    void getShortcut_shouldReturnLowerCaseO()
//    {
//        assertEquals('o', xmlLoader.getShortcut());
//    }
//
//    @Test
//    void loadSlideItem_withInvalidLevel_shouldUseDefaultLevel() throws Exception
//    {
//        // Arrange - Mock Document components
//        DocumentBuilderFactory factory = mock(DocumentBuilderFactory.class);
//        DocumentBuilder builder = mock(DocumentBuilder.class);
//        Document document = mock(Document.class);
//
//        when(factory.newDocumentBuilder()).thenReturn(builder);
//        when(builder.parse(any(File.class))).thenReturn(document);
//
//        // Create test XML with invalid level
//        Path xmlFile = tempDir.resolve("test.xml");
//        Files.writeString(xmlFile, """
//            <?xml version="1.0"?>
//            <presentation>
//                <showtitle>Test</showtitle>
//                <slide>
//                    <title>Slide</title>
//                    <item kind="text" level="invalid">Text</item>
//                </slide>
//            </presentation>
//            """);
//
//        // Act
//        xmlLoader.loadPresentation(presentation, xmlFile.toFile());
//
//        // Assert - Should use default level when parsing fails
//        assertEquals(1, presentation.getSize());
//    }
//}