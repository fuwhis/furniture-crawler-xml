/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quy.crawlers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.xml.sax.SAXException;
import quy.checkers.CheckWellformUrl;
import quy.utils.BaseUtils;
import quy.utils.XMLHelper;

/**
 *
 * @author steve
 */
public class NoiThat5CProductCrawler implements Runnable {

    private String content = "";
//    private int key;
//    private String realPath;

    public NoiThat5CProductCrawler(String url) {
        this.makeContentWellFrom(url);
        System.out.println("Da vao duoc link: " + url);
    }

    private void makeContentWellFrom(String url) {
        try {
            this.content = CheckWellformUrl.textWellForm(url);
        } catch (IOException | ParserConfigurationException | SAXException ex) {
            Logger.getLogger(NoiThat5CProductCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getProductInCurrentPage() {
        XMLStreamReader reader = null;
        InputStream is = null;

        Boolean continueConn = true;

        try {
            is = new ByteArrayInputStream(content.getBytes(Charset.forName("UTF-8")));
            reader = XMLHelper.parseFileUsingStAXCursor(is);
            while (reader.hasNext()) {
                int currentCursor = reader.next();
                if (currentCursor == XMLStreamConstants.START_ELEMENT) {
                    String tagname = reader.getLocalName();
                    if (tagname.equals("div")) {
                        String attrValue = reader.getAttributeValue("", "id");
                        if (attrValue != null) {
                            if (attrValue.equals("wrap_product_list")) {
                                while (reader.hasNext()) {
                                    currentCursor = reader.next();
                                    if (currentCursor == XMLStreamConstants.START_ELEMENT) {
                                        tagname = reader.getLocalName();
                                        if (tagname.equals("div")) {
                                            attrValue = reader.getAttributeValue("", "class");
                                            if (attrValue != null) {
                                                if (attrValue.equals("product-layout col-lg-3 col-md-4 col-sm-6 col-xs-6 ")) {
                                                    while (reader.hasNext()) {
                                                        currentCursor = reader.next();
                                                        if (currentCursor == XMLStreamConstants.START_ELEMENT) {
                                                            tagname = reader.getLocalName();
                                                            if (tagname.equals("a")){
                                                                attrValue = reader.getAttributeValue("", "class");
                                                                if (attrValue != null){
                                                                    if (attrValue.equals("link-block")){
                                                                        attrValue = reader.getAttributeValue("", "href");
                                                                        System.out.println("Link: "+attrValue);
                                                                        attrValue = reader.getAttributeValue("", "title");
                                                                        System.out.println("Title: "+attrValue);
                                                                    }
                                                                }
                                                            }
//                                                            if (tagname.equals("div")) {
//                                                                attrValue = reader.getAttributeValue("", "class");
//                                                                if (attrValue != null) {
//                                                                    if (attrValue.equals("product-item-container")) {
//                                                                        while (continueConn) {
//                                                                            currentCursor = reader.next();
//                                                                            if (currentCursor == XMLStreamConstants.START_ELEMENT) {
//                                                                                tagname = reader.getLocalName();
//                                                                                if (tagname.equals("div")) {
//                                                                                    attrValue = reader.getAttributeValue("", "class");
//                                                                                    if (attrValue != null) {
//                                                                                        if (attrValue.equals("left-block")) {
//                                                                                            while (continueConn) {
//                                                                                                currentCursor = reader.next();
//                                                                                                if (currentCursor == XMLStreamConstants.START_ELEMENT) {
//                                                                                                    tagname = reader.getLocalName();
//                                                                                                    if (tagname.equals("div")) {
//                                                                                                        attrValue = reader.getAttributeValue("", "class");
//                                                                                                        if (attrValue != null) {
//                                                                                                            if (attrValue.equals("product-image-container so-quickview")) {
//                                                                                                                while (continueConn) {
//                                                                                                                    currentCursor = reader.next();
//                                                                                                                    if (currentCursor == XMLStreamConstants.START_ELEMENT) {
//                                                                                                                        tagname = reader.getLocalName();
//                                                                                                                        if (tagname.equals("a")) {
//                                                                                                                            attrValue = reader.getAttributeValue("", "href");
//                                                                                                                            // met qua
//                                                                                                                        }
//                                                                                                                    }
//                                                                                                                }
//                                                                                                            }
//                                                                                                        }
//                                                                                                    }
//                                                                                                }
//                                                                                            }
//                                                                                        }
//                                                                                    }
//                                                                                }
//                                                                            }
//                                                                        }
//                                                                    }
//                                                                }
//                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (XMLStreamException ex) {
            Logger.getLogger(NoiThat5CProductCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (XMLStreamException | IOException ex) {
                Logger.getLogger(NoiThat5CProductCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void run() {
        getProductInCurrentPage();

    }

}
