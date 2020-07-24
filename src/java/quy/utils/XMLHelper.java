/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quy.utils;

import com.sun.codemodel.JCodeModel;
import com.sun.tools.xjc.api.S2JJAXBModel;
import com.sun.tools.xjc.api.SchemaCompiler;
import com.sun.tools.xjc.api.XJC;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.InputSource;
import quy.jaxbs.JaxBData;

/**
 *
 * @author steve
 */
public class XMLHelper implements Serializable {

    public static XMLStreamReader parseFileUsingStAXCursor(InputStream is) throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newFactory();
        XMLStreamReader reader = factory.createXMLStreamReader(is);
        return reader;
    }

    public static BufferedReader getBufferedReaderForURL(String urlString) throws MalformedURLException, UnsupportedEncodingException, IOException {
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64");
        InputStream is = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        return reader;
    }

    public static XMLEventReader parseFileUsingStAXEvent(String xmlSelection) throws UnsupportedEncodingException, XMLStreamException, IOException {
        byte[] byteArray = xmlSelection.getBytes("UTF-8");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader reader = factory.createXMLEventReader(inputStream);
        return reader;
    }

    public static String getTextOfElementStAxCursor(String elementName, XMLStreamReader reader) throws XMLStreamException {
        if (reader == null) {
            return null;
        }
        if (elementName == null) {
            return null;
        }
        if (elementName.trim().isEmpty()) {
            return null;
        }
        while (reader.hasNext()) {
            int currentCursor = reader.getEventType();

            if (currentCursor == XMLStreamConstants.START_ELEMENT) {
                String tagName = reader.getLocalName();
                if (tagName.equals(elementName)) {
                    reader.next();
                    String result = reader.getText();
                    reader.nextTag();
                    return result;
                }
            }

            reader.next();
        }
        return null;
    }

    public static String getNodeValue(String elementName, String attrName, String namespaceURI, XMLStreamReader reader) throws XMLStreamException {
        if (reader != null) {
            while (reader.hasNext()) {
                int cursor = reader.next();
                if (cursor == XMLStreamConstants.START_ELEMENT) {
                    String tagName = reader.getLocalName();
                    if (tagName.equals(elementName)) {
                        String result = reader.getAttributeValue(namespaceURI, attrName);
                        return result;
                    }
                }
            }
        }
        return null;
    }

    public static void BindingJAXB(String xsdFile) {
        try {
            String output = "src/java/";
            SchemaCompiler sc = XJC.createSchemaCompiler();
            sc.setErrorListener(new com.sun.tools.xjc.api.ErrorListener() {
                @Override
                public void error(org.xml.sax.SAXParseException saxpe) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void fatalError(org.xml.sax.SAXParseException saxpe) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void warning(org.xml.sax.SAXParseException saxpe) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void info(org.xml.sax.SAXParseException saxpe) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            sc.forcePackageName("nhan.jaxb");
            File schema = new File(output + xsdFile);
            InputSource is = new InputSource(schema.toURI().toString());
            sc.parseSchema(is);
            S2JJAXBModel model = sc.bind();
            JCodeModel code = model.generateCode(null, null);
            code.build(new File(output));
            System.out.println("Finish");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    public static void marshall(ListProduct products, String xmlPath) throws JAXBException {
//
//        JAXBContext ctx = JAXBContext.newInstance(products.getClass());
//        Marshaller mar = ctx.createMarshaller();
//        mar.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
//        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//        mar.marshal(products, new File(xmlPath));
//
//    }
//
//    public static ByteArrayOutputStream marshallProduct(ListProduct result) {
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        try {
//            JAXBContext ctx = JAXBContext.newInstance(ListProduct.class);
//            Marshaller mar = ctx.createMarshaller();
//            mar.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
//            mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//            mar.marshal(result, os);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return os;
//    }
    public static boolean validateXml(String xsdFilePath, JaxBData data) {
        try {
            JAXBContext context = JAXBContext.newInstance(data.getData().getClass());
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Marshaller marshaller = context.createMarshaller();
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdFilePath));
            XmlValidatorHandler handler = new XmlValidatorHandler();

            unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(handler);

            StringWriter writer = new StringWriter();
            marshaller.marshal(data.getData(), writer);

            marshaller.setEventHandler(handler);
            Validator validator = schema.newValidator();
            validator.validate(new JAXBSource(marshaller, data.getData()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
