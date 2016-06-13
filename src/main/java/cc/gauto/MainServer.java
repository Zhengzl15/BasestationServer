package cc.gauto;

import cc.gauto.tcpserver.BasestationServer;
import org.apache.log4j.Logger;

/**
 * Created by zzl on 16/6/13.
 */
public class MainServer {
    private static final Logger logger = Logger.getLogger(MainServer.class);

    public static void main(String []args) throws Exception {
        int tcpPort = 8081;

        BasestationServer server = new BasestationServer(tcpPort);
        server.start();
        server.join();
    }
}
