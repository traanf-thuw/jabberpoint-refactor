package com.nhlstenden.jabberpoint.files.saving;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.slide.BitmapItem;
import com.nhlstenden.jabberpoint.slide.Slide;
import com.nhlstenden.jabberpoint.slide.SlideComponent;
import com.nhlstenden.jabberpoint.slide.TextItem;
import org.junit.jupiter.api.*;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.nio.file.Files;
import java.util.List;

class XMLSaveVisitorTest {

    private File tempFile;

    @BeforeEach
    void setup() throws IOException {
        tempFile = Files.createTempFile("test-save", ".xml").toFile();
    }

    @AfterEach
    void cleanup() {
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    void testConstructor_initializesDocument() throws Exception {
        XMLSaveVisitor visitor = new XMLSaveVisitor(tempFile);
        assertNotNull(visitor);
    }

    @Test
    void testVisitPresentation_createsCorrectXml() throws Exception {
        XMLSaveVisitor visitor = new XMLSaveVisitor(tempFile);

        Presentation presentation = mock(Presentation.class);
        when(presentation.getTitle()).thenReturn("My Presentation");

        Slide slide = mock(Slide.class);
        when(slide.getTitle()).thenReturn("Slide 1");

        // Use real TextItem and BitmapItem (or your actual implementations)
        TextItem realTextItem = new TextItem(1, "Hello World");
        BitmapItem realImageItem = new BitmapItem(2, "image.png");

        when(slide.getSlideItems()).thenReturn(List.of(realTextItem, realImageItem));
        when(presentation.getShowList()).thenReturn(List.of(slide));

        visitor.visitPresentation(presentation);

        // Now check output file contents...
        String content = Files.readString(tempFile.toPath());
        assertTrue(content.contains("<showtitle>My Presentation</showtitle>"));
        assertTrue(content.contains("<slide>"));
        assertTrue(content.contains("<title>Slide 1</title>"));
    }
}
