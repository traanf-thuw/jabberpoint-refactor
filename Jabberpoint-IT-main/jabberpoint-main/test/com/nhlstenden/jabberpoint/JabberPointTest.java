package com.nhlstenden.jabberpoint;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.nhlstenden.jabberpoint.files.FileHandler;
import com.nhlstenden.jabberpoint.files.loading.DemoLoader;
import com.nhlstenden.jabberpoint.slide.SlideViewerFrame;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.io.File;
import java.util.List;

class JabberPointTest
{
    @Test
    void main_withNoArgs_usesDemoLoaderAndCreatesFrame()
    {
        try (MockedConstruction<SlideViewerFrame> mockedFrame = mockConstruction(SlideViewerFrame.class);
             MockedConstruction<DemoLoader> mockedLoader = mockConstruction(DemoLoader.class))
        {

            JabberPoint.main(new String[]{});

            // Verify frame creation
            List<SlideViewerFrame> frames = mockedFrame.constructed();
            assertEquals(1, frames.size());

            // Verify demo loader usage
            List<DemoLoader> loaders = mockedLoader.constructed();
            assertEquals(1, loaders.size());
        }
    }

    @Test
    void main_withFileArg_usesFileHandlerAndCreatesFrame()
    {
        try (MockedConstruction<SlideViewerFrame> mockedFrame = mockConstruction(SlideViewerFrame.class);
             MockedConstruction<FileHandler> mockedHandler = mockConstruction(FileHandler.class))
        {

            String testFile = "test.xml";
            JabberPoint.main(new String[]{testFile});

            // Verify frame creation
            List<SlideViewerFrame> frames = mockedFrame.constructed();
            assertEquals(1, frames.size());

            // Verify file handler usage
            List<FileHandler> handlers = mockedHandler.constructed();
            assertEquals(1, handlers.size());
        }
    }
}