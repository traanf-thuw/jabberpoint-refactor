package com.nhlstenden.jabberpoint.files.loading;

import com.nhlstenden.jabberpoint.slide.BitmapItem;
import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.slide.Slide;
import com.nhlstenden.jabberpoint.slide.TextItem;
import com.nhlstenden.jabberpoint.style.DefaultStyle;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * <p>The loader for .XML files.<p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman, Rick Vinke
 * @version 1.7 2025/04/02 Thu Tran
 */
public class XMLLoadStrategy implements LoadStrategy
{
    @Override
    public void loadPresentation(Presentation presentation, File file) throws IOException, ParserConfigurationException, SAXException
    {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(file);
        Element doc = document.getDocumentElement();

        presentation.setShowTitle(getTitle(doc, "showtitle"));
        loadSlides(doc, presentation);
    }

    private void loadSlides(Element doc, Presentation presentation)
    {
        NodeList slides = doc.getElementsByTagName("slide");
        for (int i = 0; i < slides.getLength(); i++)
        {
            Element xmlSlide = (Element) slides.item(i);
            Slide slide = createSlide(xmlSlide);
            presentation.append(slide);
        }
    }

    private Slide createSlide(Element xmlSlide)
    {
        Slide slide = new Slide(new DefaultStyle());
        slide.setTitle(getTitle(xmlSlide, "title"));

        NodeList slideItems = xmlSlide.getElementsByTagName("item");
        for (int i = 0; i < slideItems.getLength(); i++)
        {
            Element item = (Element) slideItems.item(i);
            loadSlideItem(slide, item);
        }
        return slide;
    }

    private String getTitle(Element element, String tagName)
    {
        return element.getElementsByTagName(tagName).item(0).getTextContent();
    }

    private void loadSlideItem(Slide slide, Element item)
    {
        int level = parseLevel(item.getAttributes().getNamedItem("level"));
        String type = item.getAttributes().getNamedItem("kind").getTextContent();

        if ("text".equals(type))
        {
            slide.addSlideItem(new TextItem(level, item.getTextContent()));
        }
        else if ("image".equals(type))
        {
            slide.addSlideItem(new BitmapItem(level, item.getTextContent()));
        }
        else
        {
            System.err.println("Unknown element type: " + type);
        }
    }

    private int parseLevel(Node levelNode)
    {
        try
        {
            return Integer.parseInt(levelNode.getTextContent());
        } catch (NumberFormatException | NullPointerException e)
        {
            return 1; // Default level
        }
    }
}
