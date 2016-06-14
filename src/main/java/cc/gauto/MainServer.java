package cc.gauto;

import cc.gauto.tcpserver.BasestationServer;
import cc.gauto.tcpserver.Controller;
import org.apache.log4j.Logger;

import javax.print.Doc;
import javax.swing.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by zzl on 16/6/13.
 */
public class MainServer {
    private static final Logger logger = Logger.getLogger(MainServer.class);

    public static void main(String []args) throws Exception {
        //基站和K528的端口一对一
        int hjBasePort = 9091;
        int hjK528Port = 9092;

        ArrayList<Controller> controllers = new ArrayList<>();

        URL url = MainServer.class.getResource("/config.xml");
        File file = new File(url.toURI());
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);
        NodeList nodeList = doc.getElementsByTagName("region");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            int basePort = Integer.parseInt(doc.getElementsByTagName("baseport").item(i).getFirstChild().getNodeValue());
            int k528Port = Integer.parseInt(doc.getElementsByTagName("k528port").item(i).getFirstChild().getNodeValue());
            Controller controller = new Controller(basePort, k528Port);
            controllers.add(controller);
        }

        for (Controller c : controllers) {
            c.start();
        }
        for (Controller c : controllers) {
            c.join(3);
        }

    }
}
