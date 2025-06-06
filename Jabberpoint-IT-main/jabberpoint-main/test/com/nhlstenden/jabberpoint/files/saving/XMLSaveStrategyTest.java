package com.nhlstenden.jabberpoint.files.saving;

import com.nhlstenden.jabberpoint.Content;
import com.nhlstenden.jabberpoint.ContentVisitor;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class XMLSaveStrategyTest {

    @Test
    void testSaveContent_WithXmlExtension() throws Exception {
        // Arrange
        XMLSaveStrategy<Content> strategy = new XMLSaveStrategy<>();
        Content mockContent = mock(Content.class);
        File file = new File("test.xml");

        // Act
        strategy.saveContent(mockContent, file);

        // Assert
        ArgumentCaptor<ContentVisitor> captor = ArgumentCaptor.forClass(ContentVisitor.class);
        verify(mockContent).accept(captor.capture());

        ContentVisitor passedVisitor = captor.getValue();
        assertNotNull(passedVisitor);
        assertTrue(passedVisitor instanceof XMLSaveVisitor);
    }

    @Test
    void testSaveContent_WithoutXmlExtension_AppendsXml() throws Exception {
        // Arrange
        XMLSaveStrategy<Content> strategy = new XMLSaveStrategy<>();
        Content mockContent = mock(Content.class);
        File fileWithoutExtension = new File("test");

        // Act
        strategy.saveContent(mockContent, fileWithoutExtension);

        // Assert
        ArgumentCaptor<ContentVisitor> captor = ArgumentCaptor.forClass(ContentVisitor.class);
        verify(mockContent).accept(captor.capture());

        ContentVisitor passedVisitor = captor.getValue();
        assertNotNull(passedVisitor);
        assertInstanceOf(XMLSaveVisitor.class, passedVisitor);
    }
}
