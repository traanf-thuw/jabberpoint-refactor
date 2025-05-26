//package com.nhlstenden.jabberpoint.menu;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.stream.Stream;
//
//import com.nhlstenden.jabberpoint.Presentation;
//import com.nhlstenden.jabberpoint.slide.SlideViewerFrame;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//@ExtendWith(MockitoExtension.class)
//class MenuControllerTest
//{
//
//    @Mock
//    private SlideViewerFrame mockFrame;
//
//    @Mock
//    private Presentation mockPresentation;
//
//    private MenuController menuController;
//
//    @BeforeEach
//    void setUp()
//    {
//        menuController = new MenuController(mockFrame, mockPresentation);
//    }
//
//    @ParameterizedTest
//    @MethodSource("commandProvider")
//    void createCommand_ValidActions_ReturnsCorrectCommand(
//            String action,
//            Class<? extends MenuCommand> expectedType
//    )
//    {
//        MenuCommand command = menuController.createCommand(action);
//        assertInstanceOf(expectedType, command);
//    }
//
//    private static Stream<Arguments> commandProvider()
//    {
//        return Stream.of(
//                Arguments.of("Open", OpenPresentationCommand.class),
//                Arguments.of("New", NewPresentationCommand.class),
//                Arguments.of("Save", SavePresentationCommand.class),
//                Arguments.of("Exit", ExitApplicationCommand.class),
//                Arguments.of("Next", NextSlideCommand.class),
//                Arguments.of("Prev", PreviousSlideCommand.class),
//                Arguments.of("Go to", GotoSlideCommand.class),
//                Arguments.of("About", AboutCommand.class)
//        );
//    }
//
//    @Test
//    void createCommand_UnknownAction_ReturnsNull()
//    {
//        assertNull(menuController.createCommand("UNKNOWN_ACTION"));
//    }
//
//    @Test
//    void actionPerformed_ValidCommand_ExecutesCommand()
//    {
//        // Arrange
//        ActionEvent mockEvent = new ActionEvent(this, 0, "NEXT");
//        NextSlideCommand mockCommand = mock(NextSlideCommand.class);
//        MenuController spiedController = spy(menuController);
//
//        doReturn(mockCommand).when(spiedController).createCommand("NEXT");
//
//        // Act
//        spiedController.actionPerformed(mockEvent);
//
//        // Assert
//        verify(mockCommand).execute();
//    }
//
//    @Test
//    void actionPerformed_NullCommand_DoesNothing()
//    {
//        // Arrange
//        ActionEvent mockEvent = new ActionEvent(this, 0, "INVALID");
//        MenuController spiedController = spy(menuController);
//
//        doReturn(null).when(spiedController).createCommand("INVALID");
//
//        // Act & Assert (no exception expected)
//        assertDoesNotThrow(() -> spiedController.actionPerformed(mockEvent));
//    }
//
//    @Test
//    void constructor_InitializesCorrectly()
//    {
//        assertNotNull(menuController);
//        assertInstanceOf(ActionListener.class, menuController);
//    }
//}