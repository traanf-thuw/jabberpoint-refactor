package com.nhlstenden.jabberpoint;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Presentations keeps track of the slides in a presentation.</p>
 * <p>Only one instance of this class is available.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman, Rick Vinke
 * @version 1.7 2023/01/14 Rick Vinke
 */
public class Presentation
{
    private String showTitle; //The title of the presentation
    private List<Slide> showList; //An ArrayList with slides
    private int currentSlideNumber; //The number of the current slide
    private SlideViewerComponent slideViewComponent; //The view component of the slides

    public Presentation()
    {
        showTitle = "";
        slideViewComponent = null;
        clear();
    }

    public Presentation(SlideViewerComponent slideViewerComponent)
    {
        this.slideViewComponent = slideViewerComponent;
        clear();
    }

    public int getSize()
    {
        return showList.size();
    }

    public String getTitle()
    {
        return showTitle;
    }

    public void setTitle(String nt)
    {
        showTitle = nt;
    }

    public void setShowView(SlideViewerComponent slideViewerComponent)
    {
        this.slideViewComponent = slideViewerComponent;
    }

    //Returns the number of the current slide
    public int getSlideNumber()
    {
        return currentSlideNumber;
    }

    //Change the current slide number and report it to the window
    public void setSlideNumber(int number)
    {
        currentSlideNumber = number;
        if (slideViewComponent != null)
        {
            slideViewComponent.update(this, getCurrentSlide());
        }
    }

    public List<Slide> getShowList()
    {
        return this.showList;
    }

    //Navigate to the previous slide unless we are at the first slide
    public void prevSlide()
    {
        if (currentSlideNumber > 0)
        {
            setSlideNumber(currentSlideNumber - 1);
        }
    }

    //Navigate to the next slide unless we are at the last slide
    public void nextSlide()
    {
        if (currentSlideNumber < (showList.size() - 1))
        {
            setSlideNumber(currentSlideNumber + 1);
        }
    }

    //Remove the presentation
    public void clear()
    {
        showList = new ArrayList<>();
        setSlideNumber(-1);
    }

    //Add a slide to the presentation
    public void append(Slide slide)
    {
        showList.add(slide);
    }

    //Return a slide with a specific number
    public Slide getSlide(int number)
    {
        if (number < 0 || number >= getSize())
        {
            return null;
        }
        return showList.get(number);
    }

    //Return the current slide
    public Slide getCurrentSlide()
    {
        return getSlide(currentSlideNumber);
    }
}
