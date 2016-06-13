package cc.gauto;

import org.apache.log4j.Logger;

/**
 * Created by zzl on 16/6/13.
 */
public class MainServer {
    private static final Logger logger = Logger.getLogger(MainServer.class);

    public static void main(String []args) {
        System.out.println("hello");
        logger.info("info");
        logger.error("error");
    }
}
