//package com.nhlstenden.jabberpoint.menu;
//
//import com.nhlstenden.jabberpoint.Presentation;
//import com.nhlstenden.jabberpoint.slide.Slide;
//import com.nhlstenden.jabberpoint.style.DefaultStyle;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class NewPresentationCommandTest
//{
//
//    private Presentation mockPresentation;
//    private NewPresentationCommand command;
//
//    @BeforeEach
//    void setUp()
//    {
//        mockPresentation = Mockito.mock(Presentation.class);
//        command = new NewPresentationCommand(mockPresentation);
//    }
//
//    @Test
//    void execute_normalExecution_clearsPresentationAndAddsDefaultSlide()
//    {
//        // Arrange
//        when(mockPresentation.getShowList()).thenReturn(List.of(new Slide(new DefaultStyle())));
//
//        // Act
//        command.execute();
//
//        // Assert
//        verify(mockPresentation).clear();
//        verify(mockPresentation).append(any(Slide.class));
//        assertEquals(1, mockPresentation.getShowList().size());
//    }
//
//    @Test
//    void execute_emptyPresentation_createsNewSlide()
//    {
//        // Arrange
//        when(mockPresentation.getShowList()).thenReturn(List.of());
//
//        // Act
//        command.execute();
//
//        // Assert
//        verify(mockPresentation).clear();
//        verify(mockPresentation).append(any(Slide.class));
//    }
//
//    @Test
//    void execute_presentationWithMultipleSlides_clearsAndCreatesSingleSlide()
//    {
//        // Arrange
//        when(mockPresentation.getShowList()).thenReturn(List.of(
//                new Slide(new DefaultStyle()),
//                new Slide(new DefaultStyle()),
//                new Slide(new DefaultStyle())
//        ));
//
//        // Act
//        command.execute();
//
//        // Assert
//        verify(mockPresentation).clear();
//        verify(mockPresentation).append(any(Slide.class));
//    }
//
//    @Test
//    void execute_multipleCalls_alwaysCreatesNewPresentation()
//    {
//        // Arrange
//        when(mockPresentation.getShowList()).thenReturn(List.of());
//
//        // Act
//        command.execute();
//        command.execute();
//        command.execute();
//
//        // Assert
//        verify(mockPresentation, times(3)).clear();
//        verify(mockPresentation, times(3)).append(any(Slide.class));
//    }
//
//    @Test
//    void execute_verifySlideHasDefaultStyle()
//    {
//        // Arrange
//        Slide[] capturedSlide = new Slide[1];
//        doAnswer(invocation ->
//        {
//            capturedSlide[0] = invocation.getArgument(0);
//            return null;
//        }).when(mockPresentation).append(any(Slide.class));
//
//        // Act
//        command.execute();
//
//        // Assert
//        assertNotNull(capturedSlide[0]);
//        assertTrue(capturedSlide[0].getSlideItems().isEmpty());
//    }
//}