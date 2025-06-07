package com.nhlstenden.jabberpoint.files;

import static org.junit.jupiter.api.Assertions.*;

import com.nhlstenden.jabberpoint.files.loading.LoadContentStrategy;
import com.nhlstenden.jabberpoint.files.loading.XMLLoadStrategy;
import com.nhlstenden.jabberpoint.files.saving.SaveContentStrategy;
import com.nhlstenden.jabberpoint.files.saving.XMLSaveStrategy;
import org.junit.jupiter.api.Test;

public class FileStrategyFactoryTest {

    @Test
    void createLoader_supportedExtension_returnsXMLLoadStrategy() {
        LoadContentStrategy<?> loader = FileStrategyFactory.createLoader("xml");
        assertNotNull(loader);
        assertEquals(XMLLoadStrategy.class, loader.getClass());
    }

    @Test
    void createLoader_unsupportedExtension_throwsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            FileStrategyFactory.createLoader("unsupported");
        });
        assertTrue(exception.getMessage().contains("Unsupported format"));
    }

    @Test
    void createSaver_supportedExtension_returnsXMLSaveStrategy() {
        SaveContentStrategy<?> saver = FileStrategyFactory.createSaver("xml");
        assertNotNull(saver);
        assertEquals(XMLSaveStrategy.class, saver.getClass());
    }

    @Test
    void createSaver_unsupportedExtension_throwsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            FileStrategyFactory.createSaver("unsupported");
        });
        assertTrue(exception.getMessage().contains("Unsupported format"));
    }
}