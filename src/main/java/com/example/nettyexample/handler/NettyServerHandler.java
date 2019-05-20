package com.example.nettyexample.handler;


import com.example.nettyexample.util.GeneralResponse;
import com.example.nettyexample.util.ResponseUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 李文浩
 * @date 2018/9/5
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Logger log = LoggerFactory.getLogger(NettyServerHandler.class);

    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {
        System.out.println("====NettyServerHandler=====");
        GeneralResponse generalResponse;
        //错误处理
        generalResponse = new GeneralResponse(HttpResponseStatus.BAD_REQUEST, "请检查你的请求方法及url", null);
        ResponseUtil.response(ctx, request, generalResponse);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
        log.warn("{}", e);
        ctx.close();
    }
}
