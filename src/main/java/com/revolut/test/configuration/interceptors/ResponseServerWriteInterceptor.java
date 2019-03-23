package com.revolut.test.configuration.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.IOException;

import static com.revolut.test.util.ConnectionPerThreadManager.closeConnection;

@Provider
public class ResponseServerWriteInterceptor implements WriterInterceptor {

    private static final Logger log = LoggerFactory.getLogger(ResponseServerWriteInterceptor.class);

    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
        log.info("Commit operation and release connection");
        closeConnection();
        context.proceed();
    }
}
