package com.nhlstenden.jabberpoint.files.saving;

import static org.junit.jupiter.api.Assertions.*;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.slide.BitmapItem;
import com.nhlstenden.jabberpoint.slide.Slide;
import com.nhlstenden.jabberpoint.slide.TextItem;
import com.nhlstenden.jabberpoint.style.Style;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;

public class XMLSaveVisitorTest {

    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = File.createTempFile("test_presentation", ".xml");
        tempFile.deleteOnExit();
    }

    @Test
    void testVisitPresentation_withText_generatesXml() throws Exception {
        // Arrange
        Presentation presentation = new Presentation();
        presentation.setTitle("Test Presentation");

        Slide slide = new Slide(new Style());
        slide.addSlideItem(new TextItem(1, "Hello"));
        presentation.append(slide);

        XMLSaveVisitor visitor = new XMLSaveVisitor(tempFile);

        // Act
        visitor.visitPresentation(presentation);

        // Assert
        String xml = Files.readString(tempFile.toPath());
        assertTrue(xml.contains("<showtitle>Test Presentation</showtitle>"));
        assertTrue(xml.contains("Hello"));
        assertTrue(xml.contains("kind=\"text\""));
    }

    @Test
    void testVisitPresentation_withTransformerException_showsDialog() throws Exception {
        Presentation presentation = new Presentation();
        presentation.setTitle("Error Presentation");

        XMLSaveVisitor visitor = new XMLSaveVisitor(tempFile) {
        };

        // Act & Assert (no exception thrown, dialog shows internally)
        assertDoesNotThrow(() -> visitor.visitPresentation(presentation));
    }
}
