package com.revolut.test;

import com.revolut.test.configuration.H2MemoryDatabaseConfiguration;
import com.revolut.test.controller.TransferController;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class RestServer {

    private static H2MemoryDatabaseConfiguration configuration = new H2MemoryDatabaseConfiguration();

    public static void main(String[] args) throws Exception {
        // нужна ли поддержка сессий в рест-сервисах?
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        // нужно передавать путь через args или читать из конфиг-файла
        context.setContextPath("/");

        // нужно передавать номер порта через args или читать из конфиг-файла
        Server jettyServer = new Server(8080);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        // загрузка класса TransferController ради получения его имени - не жирно?
        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                TransferController.class.getCanonicalName());

        configuration.createTableAndInsertData();

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }
}
