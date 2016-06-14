package cc.gauto.tcpserver;

/**
 * @Author Zhilong Zheng
 * @Email zhengzl0715@163.com
 * @Date 2016-06-13 20:47
 */
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.log4j.Logger;
import org.apache.log4j.net.SyslogAppender;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class BasestationHandler extends SimpleChannelInboundHandler {
    private static final Logger logger = Logger.getLogger(BasestationHandler.class.getName());
    private LinkedBlockingDeque dataQueue;

    public BasestationHandler() {
        super();
    }

    public BasestationHandler(LinkedBlockingDeque queue) {
        this.dataQueue = queue;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String)msg;
        logger.info("recv " + body.length() + " on " + ctx.channel().localAddress());
        this.dataQueue.offer(body, 2, TimeUnit.SECONDS);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
    }

    /*
         * 新的client链接
         */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        logger.info("A new client connected: " + ctx.channel().remoteAddress());
        super.channelRegistered(ctx);
    }

    /*
     * client断开链接
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        logger.info("A client disconnected: " + ctx.channel().localAddress());
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn("Unexpected exception from channel. " + cause.getMessage());
        ctx.close();
    }
}