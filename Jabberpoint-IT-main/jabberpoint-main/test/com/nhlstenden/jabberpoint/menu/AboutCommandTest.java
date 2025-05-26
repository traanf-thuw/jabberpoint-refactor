//package com.nhlstenden.jabberpoint.menu;
//
//import static org.mockito.Mockito.*;
//
//import com.nhlstenden.jabberpoint.slide.SlideViewerFrame;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.MockedStatic;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//@ExtendWith(MockitoExtension.class)
//class AboutCommandTest
//{
//
//    @Mock
//    private SlideViewerFrame mockFrame;
//
//    @Test
//    void execute_ShowsAboutBoxWithCorrectParent()
//    {
//        try (MockedStatic<AboutBox> mockedAboutBox = mockStatic(AboutBox.class))
//        {
//            AboutCommand command = new AboutCommand(mockFrame);
//
//            command.execute();
//
//            mockedAboutBox.verify(() ->
//                    AboutBox.show(eq(mockFrame))
//            );
//        }
//    }
//
//    @Test
//    void execute_VerifiesNoFrameModifications()
//    {
//        try (MockedStatic<AboutBox> mockedAboutBox = mockStatic(AboutBox.class))
//        {
//            AboutCommand command = new AboutCommand(mockFrame);
//
//            command.execute();
//
//            verifyNoInteractions(mockFrame); // Ensure command doesn't modify the frame
//        }
//    }
//}