package cc.gauto;

import cc.gauto.tcpserver.BasestationServer;
import cc.gauto.tcpserver.Controller;
import org.apache.log4j.Logger;

/**
 * Created by zzl on 16/6/13.
 */
public class MainServer {
    private static final Logger logger = Logger.getLogger(MainServer.class);

    public static void main(String []args) throws Exception {
        //基站和K528的端口一对一
        int hjBasePort = 9091;
        int hjK528Port = 9092;

        Controller controller = new Controller(hjBasePort, hjK528Port);
        controller.start();

        controller.join(3);
    }
}
