package cc.gauto.tcpserver;

/**
 * @Author Zhilong Zheng
 * @Email zhengzl0715@163.com
 * @Date 2016-06-13 20:37
 */
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.log4j.Logger;
import sun.awt.image.ImageWatched;

import java.util.concurrent.LinkedBlockingDeque;

public class BasestationServer extends Thread {
    private static final Logger logger = Logger.getLogger(BasestationServer.class.getName());
    private int port;
    private LinkedBlockingDeque dataQueue;

    public BasestationServer(int port, LinkedBlockingDeque queue) {
        this.port = port;
        this.dataQueue = queue;
    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1 /* number of threads */ );
        EventLoopGroup workerGroup = new NioEventLoopGroup(2 /* number of threads */);

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024);
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(
                            //new LineBasedFrameDecoder(1024),
                            new StringDecoder(),
                            new BasestationHandler(dataQueue));
                }
            });
            //b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
            // Bind and start to accept incoming connections.
            Channel ch = b.bind(port).sync().channel();
            logger.info("Basestation Server started on port [" + port + "]");

            ch.closeFuture().sync();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
