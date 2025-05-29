package com.nhlstenden.jabberpoint.files.loading;

import com.nhlstenden.jabberpoint.slide.Slide;
import org.junit.jupiter.api.Test;
import com.nhlstenden.jabberpoint.Presentation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InOrder;

import java.io.File;
import java.nio.file.Paths;

import static org.mockito.Mockito.*;

class DemoLoaderTest
{
    private Presentation mockPresentation;
    private DemoLoader demoLoader;

    @BeforeEach
    void setUp()
    {
        mockPresentation = mock(Presentation.class);
        demoLoader = new DemoLoader();
    }

    @Test
    void loadPresentation_withNullPresentation_shouldThrowNullPointerException()
    {
        assertThrows(NullPointerException.class,
                () -> demoLoader.loadContent(null, new File("dummy")),
                "Should throw NPE for null presentation");
    }

    @Test
    void loadPresentation_shouldCreateDemoPresentationWithCorrectStructure()
    {
        // Act
        demoLoader.loadContent(mockPresentation, null);

        // Verify call order and structure
        InOrder inOrder = inOrder(mockPresentation);
        inOrder.verify(mockPresentation).setTitle("Demo Presentation");
        inOrder.verify(mockPresentation, times(3)).append(any(Slide.class));
    }


    @Test
    void loadPresentation_createsDemoPresentationWithImage_shouldPass()
    {
        String imagePath = Paths.get("test/resources/JabberPoint.jpg").toString();
        File dummyFile = new File(imagePath);
        // Act
        demoLoader.loadContent(mockPresentation, dummyFile);

        // Verify title was set
        verify(mockPresentation).setTitle("Demo Presentation");

        // Verify slides were added
        verify(mockPresentation, times(3)).append(any(Slide.class));

        // Verify clear was NOT called
        verify(mockPresentation, never()).clear();
    }

    @Test
    void loadPresentation_shouldNotClearExistingSlides_ExpectPass()
    {
        // Act
        demoLoader.loadContent(mockPresentation, null);

        // Verify
        verify(mockPresentation, never()).clear();
    }

    @Test
    void loadPresentation_createsCorrectNumberOfSlides_ExpectPass()
    {
        // Act
        demoLoader.loadContent(mockPresentation, null);

        // Verify exactly 3 slides were added
        verify(mockPresentation, times(3)).append(any(Slide.class));
    }

    @Test
    void loadPresentation_setsCorrectTitle_ExpectPass()
    {
        // Act
        demoLoader.loadContent(mockPresentation, null);

        // Verify
        verify(mockPresentation).setTitle("Demo Presentation");
    }

    @Test
    void loadPresentation_shouldCreateExactlyThreeSlides()
    {
        // Act
        demoLoader.loadContent(mockPresentation, null);

        // Verify
        verify(mockPresentation, times(3)).append(any(Slide.class));
    }

    @Test
    void loadPresentation_shouldSetCorrectTitle()
    {
        // Act
        demoLoader.loadContent(mockPresentation, null);

        // Verify
        verify(mockPresentation).setTitle(eq("Demo Presentation"));
    }
}