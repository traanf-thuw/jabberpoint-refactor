package com.nhlstenden.jabberpoint.files.loading;

import com.nhlstenden.jabberpoint.BitmapItem;
import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.Slide;
import com.nhlstenden.jabberpoint.TextItem;
import com.nhlstenden.jabberpoint.style.DefaultStyle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/** <p>The loader for .XML files.<p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman, Rick Vinke
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 * @version 1.7 2023/01/14 Rick Vinke
 */
public class XMLLoader extends FileLoader{

    @Override
    public void loadPresentation(Presentation presentation, File file) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(file); //Create a JDOM document
            Element doc = document.getDocumentElement();
            presentation.setTitle(getTitle(doc, "showtitle"));

            NodeList slides = doc.getElementsByTagName("slide");
            int max = slides.getLength();
            for (int slideNumber = 0; slideNumber < max; slideNumber++) {
                Element xmlSlide = (Element) slides.item(slideNumber);
                Slide slide = new Slide(new DefaultStyle());
                slide.setTitle(getTitle(xmlSlide, "title"));
                presentation.append(slide);

                NodeList slideItems = xmlSlide.getElementsByTagName("item");
                int maxItems = slideItems.getLength();
                for (int itemNumber = 0; itemNumber < maxItems; itemNumber++) {
                    Element item = (Element) slideItems.item(itemNumber);
                    loadSlideItem(slide, item);
                }
            }
        }
        catch (IOException iox) {
            iox.printStackTrace();
        }
        catch (SAXException sax) {
            System.err.println(sax.getMessage());
        }
        catch (ParserConfigurationException pcx) {
            System.err.println("Parser Configuration Exception");
        }
    }

    private String getTitle(Element element, String tagName) {
        NodeList titles = element.getElementsByTagName(tagName);
        return titles.item(0).getTextContent();
    }

    private void loadSlideItem(Slide slide, Element item) {
        int level = 1; // default
        NamedNodeMap attributes = item.getAttributes();
        String leveltext = attributes.getNamedItem("level").getTextContent();
        if (leveltext != null) {
            try {
                level = Integer.parseInt(leveltext);
            }
            catch(NumberFormatException x) {
                System.err.println("Number Format Exception");
            }
        }
        String type = attributes.getNamedItem("kind").getTextContent();
        if (type.equals("text")) {
            slide.addSlideItem(new TextItem(level, item.getTextContent()));
        }
        else {
            if (type.equals("image")) {
                slide.addSlideItem(new BitmapItem(level, item.getTextContent()));
            }
            else {
                System.err.println("Unknown Element type");
            }
        }
    }

    @Override
    public String getExtension() {
        return "xml";
    }

    @Override
    public char getShortcut() {
        return 'o';
    }
}
