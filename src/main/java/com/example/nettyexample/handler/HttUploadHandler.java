package com.example.nettyexample.handler;

import static io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;

import com.example.nettyexample.util.GeneralResponse;
import com.example.nettyexample.util.ResponseUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.LastHttpContent;
//import io.netty.handler.codec.http.multipart.*;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.util.ReferenceCountUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Http upload handler which define the download path is "/download"
 *
 */
@Slf4j
public class HttUploadHandler extends SimpleChannelInboundHandler<HttpObject> {

    private static final Logger log = LoggerFactory.getLogger(HttUploadHandler.class);

    public HttUploadHandler() {
        super(false);
    }

    private static final HttpDataFactory factory = new DefaultHttpDataFactory(true);
    private static final String FILE_UPLOAD = "/data/";
    private static final String URI = "/upload";
    private HttpPostRequestDecoder httpDecoder;
    HttpRequest request;

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, final HttpObject httpObject)
            throws Exception {
        if (httpObject instanceof HttpRequest) {
            request = (HttpRequest) httpObject;
            if (request.uri().startsWith(URI) && request.method().equals(HttpMethod.POST)) {
                httpDecoder = new HttpPostRequestDecoder(factory, request);
                httpDecoder.setDiscardThreshold(0);
            } else {
                //传递给下一个Handler
                ctx.fireChannelRead(httpObject);
            }
        }
        if (httpObject instanceof HttpContent) {
            if (httpDecoder != null) {
                final HttpContent chunk = (HttpContent) httpObject;
                httpDecoder.offer(chunk);
                if (chunk instanceof LastHttpContent) {
                    writeChunk(ctx);
                    //关闭httpDecoder
                    httpDecoder.destroy();
                    httpDecoder = null;
                }
                ReferenceCountUtil.release(httpObject);
            } else {
                ctx.fireChannelRead(httpObject);
            }
        }

    }

    private void writeChunk(ChannelHandlerContext ctx) throws IOException {
        while (httpDecoder.hasNext()) {
            InterfaceHttpData data = httpDecoder.next();
            if (data != null && HttpDataType.FileUpload.equals(data.getHttpDataType())) {
                final FileUpload fileUpload = (FileUpload) data;
                final File file = new File(FILE_UPLOAD + fileUpload.getFilename());
                log.info("upload file: {}", file);
                try (FileChannel inputChannel = new FileInputStream(fileUpload.getFile()).getChannel();
                     FileChannel outputChannel = new FileOutputStream(file).getChannel()) {
                    outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
                    ResponseUtil.response(ctx, request, new GeneralResponse(HttpResponseStatus.OK, "SUCCESS", null));
                }
            }
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.warn("{}", cause);
        ctx.channel().close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (httpDecoder != null) {
            httpDecoder.cleanFiles();
        }
    }

}