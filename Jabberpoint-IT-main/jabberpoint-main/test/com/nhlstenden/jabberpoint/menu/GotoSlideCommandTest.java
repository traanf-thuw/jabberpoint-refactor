//package com.nhlstenden.jabberpoint.menu;
//
//import static org.mockito.Mockito.*;
//
//import com.nhlstenden.jabberpoint.Presentation;
//import com.nhlstenden.jabberpoint.slide.Slide;
//import com.nhlstenden.jabberpoint.slide.SlideViewerFrame;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.MockedStatic;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import javax.swing.JOptionPane;
//import java.util.List;
//
//@ExtendWith(MockitoExtension.class)
//class GotoSlideCommandTest
//{
//
//    @Mock
//    private Presentation mockPresentation;
//
//    @Mock
//    private SlideViewerFrame mockFrame;
//
//    @Test
//    void execute_ValidInput_ChangesSlide()
//    {
//        try (MockedStatic<JOptionPane> mockedPane = mockStatic(JOptionPane.class))
//        {
//            // Arrange
//            when(mockPresentation.getShowList()).thenReturn(List.of(mock(Slide.class), mock(Slide.class), mock(Slide.class)));
//            mockedPane.when(() -> JOptionPane.showInputDialog(any(), anyString()))
//                    .thenReturn("2"); // User enters 2 (slide index 1)
//
//            GotoSlideCommand command = new GotoSlideCommand(mockPresentation, mockFrame);
//
//            // Act
//            command.execute();
//
//            // Assert
//            verify(mockPresentation).setSlideNumber(1);
//            verify(mockFrame).repaint();
//        }
//    }
//
//    @Test
//    void execute_InputOutOfRange_ShowsError()
//    {
//        try (MockedStatic<JOptionPane> mockedPane = mockStatic(JOptionPane.class))
//        {
//            // Arrange
//            when(mockPresentation.getShowList()).thenReturn(List.of(mock(Slide.class)));
//            mockedPane.when(() -> JOptionPane.showInputDialog(any(), anyString()))
//                    .thenReturn("5"); // Only 1 slide exists
//
//            GotoSlideCommand command = new GotoSlideCommand(mockPresentation, mockFrame);
//
//            // Act
//            command.execute();
//
//            // Assert
//            mockedPane.verify(() ->
//                    JOptionPane.showMessageDialog(
//                            eq(mockFrame),
//                            eq("Slide number out of range."),
//                            eq("Error"),
//                            eq(JOptionPane.ERROR_MESSAGE)
//                    ));
//            verify(mockPresentation, never()).setSlideNumber(anyInt());
//            verify(mockFrame, never()).repaint();
//        }
//    }
//
//    @Test
//    void execute_InvalidNumberFormat_ShowsError()
//    {
//        try (MockedStatic<JOptionPane> mockedPane = mockStatic(JOptionPane.class))
//        {
//            // Arrange
//            mockedPane.when(() -> JOptionPane.showInputDialog(any(), anyString()))
//                    .thenReturn("abc");
//
//            GotoSlideCommand command = new GotoSlideCommand(mockPresentation, mockFrame);
//
//            // Act
//            command.execute();
//
//            // Assert
//            mockedPane.verify(() ->
//                    JOptionPane.showMessageDialog(
//                            eq(mockFrame),
//                            eq("Invalid input. Please enter a valid number."),
//                            eq("Error"),
//                            eq(JOptionPane.ERROR_MESSAGE)
//                    ));
//            verify(mockPresentation, never()).setSlideNumber(anyInt());
//        }
//    }
//
//    @Test
//    void execute_NullInput_DoesNothing()
//    {
//        try (MockedStatic<JOptionPane> mockedPane = mockStatic(JOptionPane.class))
//        {
//            // Arrange
//            mockedPane.when(() -> JOptionPane.showInputDialog(any(), anyString()))
//                    .thenReturn(null);
//
//            GotoSlideCommand command = new GotoSlideCommand(mockPresentation, mockFrame);
//
//            // Act
//            command.execute();
//
//            // Assert
//            verifyNoInteractions(mockPresentation);
//            verifyNoInteractions(mockFrame);
//        }
//    }
//
//    @Test
//    void execute_EmptyInput_ShowsError()
//    {
//        try (MockedStatic<JOptionPane> mockedPane = mockStatic(JOptionPane.class))
//        {
//            // Arrange
//            mockedPane.when(() -> JOptionPane.showInputDialog(any(), anyString()))
//                    .thenReturn("   ");
//
//            GotoSlideCommand command = new GotoSlideCommand(mockPresentation, mockFrame);
//
//            // Act
//            command.execute();
//
//            // Assert
//            mockedPane.verify(() ->
//                    JOptionPane.showMessageDialog(
//                            eq(mockFrame),
//                            eq("Invalid input. Please enter a valid number."),
//                            eq("Error"),
//                            eq(JOptionPane.ERROR_MESSAGE)
//                    ));
//        }
//    }
//
//    @Test
//    void execute_BoundaryMinimumValue_Succeeds()
//    {
//        try (MockedStatic<JOptionPane> mockedPane = mockStatic(JOptionPane.class))
//        {
//            // Arrange
//            when(mockPresentation.getShowList()).thenReturn(List.of(mock(Slide.class), mock(Slide.class)));
//            mockedPane.when(() -> JOptionPane.showInputDialog(any(), anyString()))
//                    .thenReturn("1"); // Minimum valid input
//
//            GotoSlideCommand command = new GotoSlideCommand(mockPresentation, mockFrame);
//
//            // Act
//            command.execute();
//
//            // Assert
//            verify(mockPresentation).setSlideNumber(0);
//            verify(mockFrame).repaint();
//        }
//    }
//
//    @Test
//    void execute_BoundaryMaximumValue_Succeeds()
//    {
//        try (MockedStatic<JOptionPane> mockedPane = mockStatic(JOptionPane.class))
//        {
//            // Arrange
//            List<Slide> slides = List.of(mock(Slide.class), mock(Slide.class), mock(Slide.class));
//            when(mockPresentation.getShowList()).thenReturn(slides);
//            mockedPane.when(() -> JOptionPane.showInputDialog(any(), anyString()))
//                    .thenReturn(String.valueOf(slides.size()));
//
//            GotoSlideCommand command = new GotoSlideCommand(mockPresentation, mockFrame);
//
//            // Act
//            command.execute();
//
//            // Assert
//            verify(mockPresentation).setSlideNumber(slides.size() - 1);
//            verify(mockFrame).repaint();
//        }
//    }
//}