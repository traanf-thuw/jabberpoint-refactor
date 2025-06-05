package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Content;
import com.nhlstenden.jabberpoint.ContentVisitor;
import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.slide.*;
import com.nhlstenden.jabberpoint.style.DefaultStyle;

import java.io.File;

public class NewContentVisitor<T extends Content> implements ContentVisitor
{
    private final CommandContext<T> context;

    public NewContentVisitor(CommandContext<T> context)
    {
        this.context = context;
    }

    @Override
    public void visitPresentation(Presentation presentation)
    {
        SlideViewerFrame frame = context.getFrame();

        Slide defaultSlide = new Slide(new DefaultStyle());
        TextItem defaultTextItem = (TextItem) SlideItemFactory.getInstance()
                .createSlideItem(SlideItemType.TEXT, 0, "New Slide");

        defaultSlide.addSlideItem(defaultTextItem);
        addInteractiveButtons(defaultSlide, presentation, frame);

        presentation.append(defaultSlide);
        presentation.setShowListNumber(0);
    }

    private void addInteractiveButtons(Slide slide, Presentation presentation, SlideViewerFrame frame)
    {
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
                slide.addSlideItem(item);
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
                slide.addSlideItem(item);
                presentation.refreshView();
            }
        });

        slide.addSlideItem(addTextButton);
        slide.addSlideItem(addImageButton);
    }
}


