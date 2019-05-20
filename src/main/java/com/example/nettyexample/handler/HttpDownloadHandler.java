package com.example.nettyexample.handler;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import com.example.nettyexample.util.GeneralResponse;
import com.example.nettyexample.util.ResponseUtil;
//import io.netty.channel.*;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelProgressiveFuture;
import io.netty.channel.ChannelProgressiveFutureListener;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.LastHttpContent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * Http download handler which define the download path is "/download"
 *
 */
@Slf4j
public class HttpDownloadHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Logger log = LoggerFactory.getLogger(HttpDownloadHandler.class);

    public HttpDownloadHandler() {
        super(false);
    }

    private String filePath = "body.txt";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {
        String uri = request.uri();
        if (uri.startsWith("/download") && request.method().equals(HttpMethod.GET)) {
            GeneralResponse generalResponse = null;
            File[] file = new File[1];
            try {
                file[0] = new ClassPathResource(filePath).getFile();
                final RandomAccessFile raf = new RandomAccessFile(file[0], "r");
                long fileLength = raf.length();
                HttpResponse response = new DefaultHttpResponse(HTTP_1_1, HttpResponseStatus.OK);
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, fileLength);
                response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/octet-stream");
//                response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/force-download");
                response.headers().add(HttpHeaderNames.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", file[0].getName()));
                ctx.write(response);
                ChannelFuture sendFileFuture = ctx.write(new DefaultFileRegion(raf.getChannel(), 0, fileLength), ctx.newProgressivePromise());
                sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
                    @Override
                    public void operationComplete(ChannelProgressiveFuture future)
                            throws Exception {
                        log.info("file {} transfer complete.", file[0].getName());
                        raf.close();
                    }

                    @Override
                    public void operationProgressed(ChannelProgressiveFuture future,
                                                    long progress, long total) throws Exception {
                        if (total < 0) {
                            log.warn("file {} transfer progress: {}", file[0].getName(), progress);
                        } else {
                            log.debug("file {} transfer progress: {}/{}", file[0].getName(), progress, total);
                        }
                    }
                });
                ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            } catch (FileNotFoundException e) {
                log.warn("file {} not found", filePath);
                generalResponse = new GeneralResponse(HttpResponseStatus.NOT_FOUND, String.format("file %s not found", filePath), null);
                ResponseUtil.response(ctx, request, generalResponse);
            } catch (IOException e) {
                log.warn("file {} has a IOException: {}", file[0].getName(), e.getMessage());
                generalResponse = new GeneralResponse(HttpResponseStatus.INTERNAL_SERVER_ERROR, String.format("读取 file %s 发生异常", filePath), null);
                ResponseUtil.response(ctx, request, generalResponse);
            }
        } else {
            ctx.fireChannelRead(request);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
        log.warn("{}", e);
        ctx.close();

    }
}
