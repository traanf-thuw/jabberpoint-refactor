package com.nhlstenden.jabberpoint.files;

import static org.junit.jupiter.api.Assertions.*;

import com.nhlstenden.jabberpoint.files.loading.XMLLoadPresentationStrategy;
import com.nhlstenden.jabberpoint.files.saving.XMLSavePresentationStrategy;
import org.junit.jupiter.api.Test;
import com.nhlstenden.jabberpoint.files.loading.LoadContentStrategy;
import com.nhlstenden.jabberpoint.files.saving.SaveContentStrategy;

class FileStrategyFactoryTest
{

    @Test
    void createLoader_ValidXmlExtension_ReturnsXmlLoader()
    {
        LoadContentStrategy strategy = FileStrategyFactory.createLoader("xml");
        assertInstanceOf(XMLLoadPresentationStrategy.class, strategy);
    }

    @Test
    void createLoader_UnsupportedExtension_ThrowsException()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> FileStrategyFactory.createLoader("pdf"));

        assertEquals("Unsupported format: pdf", exception.getMessage());
    }

    @Test
    void createLoader_NullExtension_ThrowsException()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> FileStrategyFactory.createLoader(null));

        assertEquals("Unsupported format: null", exception.getMessage());
    }

    @Test
    void createSaver_ValidXmlExtension_ReturnsXmlSaver()
    {
        SaveContentStrategy strategy = FileStrategyFactory.createSaver("xml");
        assertInstanceOf(XMLSavePresentationStrategy.class, strategy);
    }

    @Test
    void createSaver_UnsupportedExtension_ThrowsException()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> FileStrategyFactory.createSaver("doc"));

        assertEquals("Unsupported format: doc", exception.getMessage());
    }

    @Test
    void createSaver_NullExtension_ThrowsException()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> FileStrategyFactory.createSaver(null));

        assertEquals("Unsupported format: null", exception.getMessage());
    }

    @Test
    void createLoader_MultipleCalls_ReturnsSingletonInstance()
    {
        LoadContentStrategy first = FileStrategyFactory.createLoader("xml");
        LoadContentStrategy second = FileStrategyFactory.createLoader("xml");
        assertSame(first, second);
    }

    @Test
    void createSaver_MultipleCalls_ReturnsSingletonInstance()
    {
        SaveContentStrategy first = FileStrategyFactory.createSaver("xml");
        SaveContentStrategy second = FileStrategyFactory.createSaver("xml");
        assertSame(first, second);
    }

    @Test
    void createLoader_UpperCaseExtension_ThrowsException()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> FileStrategyFactory.createLoader("XML"));

        assertEquals("Unsupported format: XML", exception.getMessage());
    }
}