package cc.gauto;

import cc.gauto.tcpserver.BasestationServer;
import org.apache.log4j.Logger;

/**
 * Created by zzl on 16/6/13.
 */
public class MainServer {
    private static final Logger logger = Logger.getLogger(MainServer.class);

    public static void main(String []args) throws Exception {
        //基站和K528的端口一对一
        int hjBasePort = 8081;
        int hjK528Port = 8082;

    }
}
