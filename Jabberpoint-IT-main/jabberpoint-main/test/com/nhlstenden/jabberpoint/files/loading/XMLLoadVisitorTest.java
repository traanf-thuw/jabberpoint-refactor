package com.nhlstenden.jabberpoint.files.loading;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.nhlstenden.jabberpoint.Presentation;
import org.junit.jupiter.api.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.nio.file.*;

class XMLLoadVisitorTest {

    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        String xml = """
            <presentation>
                <showtitle>Test Show Title</showtitle>
                <slide>
                    <title>Slide 1</title>
                    <item level="1" kind="text">Slide text content</item>
                    <item level="2" kind="image">image.jpg</item>
                </slide>
                <slide>
                    <title>Slide 2</title>
                    <item level="notanumber" kind="text">Fallback level text</item>
                </slide>
            </presentation>
            """;

        tempFile = Files.createTempFile("test-presentation", ".xml").toFile();
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(xml);
        }
    }

    @AfterEach
    void tearDown() {
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    void testVisitPresentation_loadsCorrectly() throws Exception {
        XMLLoadVisitor visitor = new XMLLoadVisitor(tempFile);

        Presentation mockPresentation = mock(Presentation.class);

        visitor.visitPresentation(mockPresentation);

        verify(mockPresentation).setTitle("Test Show Title");
        // Two slides appended
        verify(mockPresentation, times(2)).append(any());

        // You can add more detailed verifications here by capturing Slide objects if needed
    }

    @Test
    void testCreateComponent_throwsOnUnknownType() throws Exception {
        // Create a minimal XML with unknown kind item
        String xml = """
            <presentation>
                <showtitle>Title</showtitle>
                <slide>
                    <title>Slide</title>
                    <item level="1" kind="unknown">Bad content</item>
                </slide>
            </presentation>
            """;

        File file = Files.createTempFile("bad-presentation", ".xml").toFile();
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(xml);
        }

        XMLLoadVisitor visitor = new XMLLoadVisitor(file);

        Presentation presentation = mock(Presentation.class);

        // The exception is thrown when visitPresentation tries to create slide items
        Exception ex = assertThrows(IllegalArgumentException.class, () -> visitor.visitPresentation(presentation));
        assertTrue(ex.getMessage().contains("Unknown slide item type"));

        file.delete();
    }
}
