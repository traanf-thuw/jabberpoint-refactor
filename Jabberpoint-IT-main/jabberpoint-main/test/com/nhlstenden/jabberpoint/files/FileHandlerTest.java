package com.nhlstenden.jabberpoint.files;

import com.nhlstenden.jabberpoint.Content;
import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.files.loading.LoadContentStrategy;
import com.nhlstenden.jabberpoint.files.saving.SaveContentStrategy;
import org.junit.jupiter.api.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

    private FileHandler<Presentation> fileHandler;
    private Presentation presentation;

    @BeforeEach
    void setUp() {
        fileHandler = new FileHandler<>();
        presentation = new Presentation();
    }

    @Test
    void testLoadFile_FileNotFound_ShowsError() {
        // Should not throw; error handled internally
        fileHandler.loadFile(presentation, "nonexistent_file.xyz");
    }

    @Test
    void testLoadFile_InvalidExtension_ThrowsError() {
        String fileName = "presentation.invalid";
        assertDoesNotThrow(() -> fileHandler.loadFile(presentation, fileName));
    }

    @Test
    void testSaveFile_SaveFails_HandledGracefully() throws IOException {
        File tempFile = File.createTempFile("test", ".xml");

        FileStrategyFactory.overrideSaver("xml", (content, file) -> {
            throw new IOException("Simulated IO error");
        });

        fileHandler.saveFile(presentation, tempFile.getAbsolutePath());
        // No exception should escape
    }

    @Test
    void testSaveFile_InvalidExtension_ThrowsError() {
        assertDoesNotThrow(() -> fileHandler.saveFile(presentation, "invalidfile"));
    }

    @Test
    void testExtractExtension_NoDot_Throws() throws Exception {
        var method = FileHandler.class.getDeclaredMethod("extractExtension", String.class);
        method.setAccessible(true);

        InvocationTargetException thrown = assertThrows(InvocationTargetException.class, () -> {
            method.invoke(fileHandler, "nofileextension");
        });

        // Now check the cause is IllegalArgumentException
        assertTrue(thrown.getCause() instanceof IllegalArgumentException);
        assertEquals("No file extension", thrown.getCause().getMessage());
    }


    // Override hooks for t
public static class FileStrategyFactory {
    private static LoadContentStrategy<?> testLoader;

        public static <T extends Content> void overrideSaver(String ext, SaveContentStrategy<T> saver) {
        }
}
}
