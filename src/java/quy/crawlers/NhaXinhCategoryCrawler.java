/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quy.crawlers;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.xml.sax.SAXException;
import quy.checkers.CheckWellformUrl;
import quy.utils.BaseUtils;
import static quy.utils.BaseUtils.KEY_BED_ROOM;
import static quy.utils.BaseUtils.KEY_DINNER_ROOM;
import static quy.utils.BaseUtils.KEY_HOME_OFFICE;
import static quy.utils.BaseUtils.KEY_LIVING_ROOM;
import quy.utils.XMLHelper;

/**
 *
 * @author steve
 */
public class NhaXinhCategoryCrawler {

    private String content;

    public NhaXinhCategoryCrawler() {
        this.makeContentWellFrom();
    }

    private void makeContentWellFrom() {
        try {
            this.content = CheckWellformUrl.textWellForm(BaseUtils.NHAXINH_HOMEPAGE);
        } catch (IOException ex) {
            Logger.getLogger(NhaXinhCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(NhaXinhCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(NhaXinhCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Map<Integer, String> getCatalogy() {
        Map<Integer, String> results = new HashMap<>();
        XMLStreamReader reader = null;
        InputStream is = null;
        boolean continueConn = true;
        try {
            is = new ByteArrayInputStream(content.getBytes(Charset.forName("UTF-8")));
            reader = XMLHelper.parseFileUsingStAXCursor(is);
            while (reader.hasNext()) {
                int currentCursor = reader.next();
                if (currentCursor == XMLStreamConstants.START_ELEMENT) {
                    String tagName = reader.getLocalName();
                    if (tagName.equals("div")) {
                        String attrValue = reader.getAttributeValue("", "class");
                        if (attrValue != null) {
                            if (attrValue.contains("d-flex justify-content-center custom-nav position-relative")) {
                                while (continueConn) {
                                    currentCursor = reader.next();
                                    if (currentCursor == XMLStreamConstants.START_ELEMENT) {
                                        tagName = reader.getLocalName();
                                        if (tagName.equals("ul")) {
                                            while (continueConn) {
                                                currentCursor = reader.next();
                                                if (currentCursor == XMLStreamConstants.START_ELEMENT) {
                                                    tagName = reader.getLocalName();
                                                    if (tagName.equals("a")) {
                                                        attrValue = reader.getAttributeValue("", "href");
                                                        String roomName = reader.getElementText();
//                                                        System.out.println("attribute---" + attrValue + " == " + roomName);
                                                        // put href to key constant
                                                        if (roomName.equalsIgnoreCase("Phòng khách")) {
                                                            results.put(KEY_LIVING_ROOM, attrValue);
                                                        }
                                                        if (roomName.equalsIgnoreCase("Phòng ăn")) {
                                                            results.put(KEY_DINNER_ROOM, attrValue);
                                                        }
                                                        if (roomName.equalsIgnoreCase("Phòng ngủ")) {
                                                            results.put(KEY_BED_ROOM, attrValue);
                                                        }
                                                        if (roomName.equalsIgnoreCase("Phòng làm việc")) {
                                                            results.put(KEY_HOME_OFFICE, attrValue);
                                                        }
                                                    }
                                                }
                                                if (currentCursor == XMLStreamConstants.END_ELEMENT) {
                                                    tagName = reader.getLocalName();
                                                    if (tagName.equals("ul")) {
                                                        continueConn = false;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                        }

//                            
                    }
                }
            }
        } catch (XMLStreamException e) {
            Logger.getLogger(NhaXinhCategoryCrawler.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
            }
        }
        System.out.println("results----------" + results);
        return results;
    }

//    public Map<Integer, String> getCatagories() {
//        Map<Integer, String> results = new HashMap<>();
//        InputStream is = null;
//        BufferedReader reader = null;
//        try {
//            String line = "";
//            String document = "";
//            boolean isStart = false;
//            boolean isFound = false; 
//            while ((line = reader.readLine())!=null) {                
//                
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(NhaXinhCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                if (reader != null) {
//                    reader.close();
//                }
//                if (is != null) {
//                    is.close();
//                }
//            } catch (Exception e) {
//
//            }
//        }
//        return results;
//    }
}
