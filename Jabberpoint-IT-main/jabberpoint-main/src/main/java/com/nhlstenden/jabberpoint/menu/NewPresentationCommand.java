package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.slide.*;
import com.nhlstenden.jabberpoint.style.DefaultStyle;

import java.io.File;

/**
 * <p>Create new presentation<p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public class NewPresentationCommand implements MenuCommand
{
    @Override
    public void execute(CommandContext context)
    {
        Presentation presentation = context.getPresentation();
        SlideViewerFrame frame = context.getFrame();

        presentation.clear();

        Slide defaultSlide = new Slide(new DefaultStyle());
        TextItem defaultTextItem = (TextItem) SlideItemFactory.getInstance()
                .createSlideItem(SlideItemType.TEXT, 0, "New Slide");
        defaultSlide.addSlideItem(defaultTextItem);

        // Button Items
        ButtonItem addTextButton = new ButtonItem(1, "Add Text");
        addTextButton.setActionListener(e ->
        {
            TextInputDialog dialog = new TextInputDialog(frame);
            dialog.setVisible(true);
            String input = dialog.getInputText();
            if (input != null && !input.trim().isEmpty())
            {
                SlideComponent item = SlideItemFactory.getInstance()
                        .createSlideItem(SlideItemType.TEXT, 3, input.trim());
                defaultSlide.addSlideItem(item);
                presentation.refreshView();
            }
        });

        ButtonItem addImageButton = new ButtonItem(1, "Add Image");
        addImageButton.setActionListener(e ->
        {
            ImageInputDialog dialog = new ImageInputDialog(frame);
            dialog.setVisible(true);
            File file = dialog.getSelectedFile();
            if (file != null)
            {
                SlideComponent item = SlideItemFactory.getInstance()
                        .createSlideItem(SlideItemType.BITMAP, 4, file.getAbsolutePath());
                defaultSlide.addSlideItem(item);
                presentation.refreshView();
            }
        });

        defaultSlide.addSlideItem(addTextButton);
        defaultSlide.addSlideItem(addImageButton);

        presentation.append(defaultSlide);
        presentation.setSlideNumber(0);
    }
}
