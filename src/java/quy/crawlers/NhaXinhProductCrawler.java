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
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import org.xml.sax.SAXException;
import quy.checkers.CheckWellformUrl;
import quy.utils.BaseUtils;
import quy.utils.XMLHelper;

/**
 *
 * @author steve
 */
public class NhaXinhProductCrawler implements Runnable {

    private String content = "";
    private int key;
    private String realPath;

    public NhaXinhProductCrawler(String url) {
        url = BaseUtils.NHAXINH_HOMEPAGE + url;
        this.makeContentWellFrom(url);
        System.out.println("Da vao duoc link: " + url);
//        this.key = key;
//        this.realPath = realPath;
    }

    private void makeContentWellFrom(String url) {
        try {
            this.content = CheckWellformUrl.textWellForm(url);
        } catch (IOException | ParserConfigurationException | SAXException ex) {
            Logger.getLogger(NhaXinhProductCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getProductInCurrentPage() {
        XMLStreamReader reader = null;
        InputStream is = null;
        boolean flag1 = true;
        boolean flag2 = true;
        boolean flag3 = true;

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
                            if (attrValue.equals("product_catalog_list")) {
                                //maybe an if here
                                while (reader.hasNext()) {
                                    currentCursor = reader.next();
                                    if (currentCursor == XMLStreamConstants.START_ELEMENT) {
                                        tagname = reader.getLocalName();
                                        if (tagname.equals("div")) {
                                            attrValue = reader.getAttributeValue("", "class");
                                            if (attrValue != null) {
                                                if (attrValue.equals("toggle_info")) {
                                                    while (flag2) {
                                                        currentCursor = reader.next();
                                                        if (currentCursor == XMLStreamConstants.START_ELEMENT) {
                                                            tagname = reader.getLocalName();
                                                            if (tagname.equals("b")) {
                                                                System.out.println("Key!: " + reader.getElementText());
//                                                                flag1 = false;
                                                            }
                                                        }
                                                        if (currentCursor == XMLStreamConstants.CHARACTERS) {
                                                            System.out.println("Ele!: " + reader.getText());
//                                                            flag1 = false;
                                                        }
                                                        if (currentCursor == XMLStreamConstants.END_ELEMENT) {
                                                            tagname = reader.getLocalName();
                                                            if (tagname.equals("div")) {
                                                                flag2 = false;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (tagname.equals("a")) {
                                            attrValue = reader.getAttributeValue("", "class");
                                            if (attrValue != null) {
                                                if (attrValue.equals("product_catalog_list_name")) {
                                                    attrValue = reader.getAttributeValue("", "title");
                                                    currentCursor = reader.next();
                                                    if (currentCursor == XMLStreamConstants.START_ELEMENT) {
                                                        tagname = reader.getLocalName();
                                                        if (tagname.equals("img")) {
                                                            System.out.println("Name: " + attrValue);
                                                            attrValue = reader.getAttributeValue("", "src");
                                                            System.out.println("Img: " + attrValue);
                                                            flag2 = true;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
//                                    if (currentCursor == XMLStreamConstants.END_ELEMENT) {
//                                        tagname = reader.getLocalName();
//                                        if (tagname.equals("div")) {
//                                            XMLStreamReader readerNext = reader;
//                                            int nextCursor = readerNext.next();
//                                            if (nextCursor == XMLStreamConstants.START_ELEMENT) {
//                                                String tagnameNext = readerNext.getLocalName();
//                                                if (tagnameNext.equals("div")) {
//                                                    String attrValueNext = readerNext.getAttributeValue("", "style");
//                                                    if (attrValueNext != null) {
//                                                        System.out.println("Yo we got it!");
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
                                }
                            }
                        }
                    }
                }
            }

        } catch (XMLStreamException ex) {
            Logger.getLogger(NhaXinhProductCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (XMLStreamException | IOException ex) {
                Logger.getLogger(NhaXinhProductCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void run() {
        getProductInCurrentPage();
    }
}
