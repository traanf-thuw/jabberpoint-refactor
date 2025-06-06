package com.nhlstenden.jabberpoint.files.loading;

import static org.junit.jupiter.api.Assertions.*;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.files.saving.XMLSaveVisitor;
import com.nhlstenden.jabberpoint.slide.Slide;
import com.nhlstenden.jabberpoint.slide.TextItem;
import com.nhlstenden.jabberpoint.style.Style;
import org.junit.jupiter.api.*;
import java.io.File;
import java.nio.file.Files;

class XMLSaveVisitorTest {

    private File tempFile;

    @BeforeEach
    void setup() throws Exception {
        tempFile = File.createTempFile("test-presentation", ".xml");
        tempFile.deleteOnExit();
    }

    @Test
    void testVisitPresentation_SavesValidXML() throws Exception {
        // Arrange
        XMLSaveVisitor visitor = new XMLSaveVisitor(tempFile);

        // Setup mock content
        TextItem textItem = new TextItem(1, "Hello World");
        Slide slide = new Slide(new Style());
        slide.addSlideItem(textItem);

        Presentation presentation = new Presentation();
        presentation.setTitle("My Presentation");
        presentation.append(slide);

        // Act
        visitor.visitPresentation(presentation);

        // Assert file exists and contains expected XML structure
        String xmlContent = Files.readString(tempFile.toPath());
        assertTrue(xmlContent.contains("<showtitle>My Presentation</showtitle>"));
        assertTrue(xmlContent.contains("<slide>"));
        assertTrue(xmlContent.contains("Hello World"));
        assertTrue(xmlContent.contains("level=\"1\""));
        assertTrue(xmlContent.contains("kind=\"text\""));
    }

    @Test
    void testVisitPresentation_ShowsDialogOnTransformerError() throws Exception {
        // Arrange
        XMLSaveVisitor visitor = new XMLSaveVisitor(tempFile) {
        };

        Presentation presentation = new Presentation();
        presentation.setTitle("Broken");

        // Act + Assert
        Assertions.assertDoesNotThrow(() -> visitor.visitPresentation(presentation));
    }
}
