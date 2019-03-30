package com.revolut.test.integration;

import com.jayway.restassured.response.Response;
import com.revolut.test.util.InitializeDb;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IntegrationTest {

    private static Server server;

    @BeforeClass
    public static void startJetty() throws Exception {
        InitializeDb initializeDb = new InitializeDb();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        server = new Server(8080);
        server.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                "com.revolut.test.configuration.interceptors.ResponseServerWriteInterceptor," +
                        "com.revolut.test.controller.TransferController");

        initializeDb.createTableAndInsertData();

        server.start();
    }

    @AfterClass
    public static void stopJetty() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JSONObject prepareTestDataTransfer() {
        return new JSONObject()
                .put("paymentPurpose", "test")
                .put("accountNumberFrom", 321)
                .put("recipientAccount", 123)
                .put("transferAmount", 1);
    }

    private JSONObject prepareTestNoMoneyTransfer() {
        return new JSONObject()
                .put("paymentPurpose", "test")
                .put("accountNumberFrom", 321)
                .put("recipientAccount", 123)
                .put("transferAmount", 10000);
    }

    private JSONObject prepareTestNotFoundTransfer() {
        return new JSONObject()
                .put("paymentPurpose", "test")
                .put("accountNumberFrom", 1015)
                .put("recipientAccount", 123)
                .put("transferAmount", 10000);
    }

    private JSONObject prepareTestBadDataTransfer() {
        return new JSONObject()
                .put("paymentPurpose", "test")
                .put("accountNumberFrom", 123)
                .put("recipientAccount", 123)
                .put("transferAmount", 10000);
    }

    @Test
    public void testNormalTransfer() {
        JSONObject jsonObj = prepareTestDataTransfer();

        Response response = given()
                .port(8080)
                .contentType("application/json")
                .body(jsonObj.toString())
                .when()
                .post("/api/transfer");

        assertEquals(200, response.getStatusCode());
        JSONObject responseBodyAsJson = new JSONObject(response.getBody().asString());
        assertEquals(123, responseBodyAsJson.get("recipientAccount"));
        assertEquals(321, responseBodyAsJson.get("accountNumberFrom"));
        assertEquals(1, responseBodyAsJson.get("transferAmount"));
        assertEquals(3499, responseBodyAsJson.get("accountAmountFrom"));
        assertEquals(1201, responseBodyAsJson.get("accountAmountTo"));
        assertEquals("test", responseBodyAsJson.get("paymentPurpose"));

    }

    @Test
    public void testNoMoneyTransfer() {
        JSONObject jsonObj = prepareTestNoMoneyTransfer();

        Response response = given()
                .port(8080)
                .contentType("application/json")
                .body(jsonObj.toString())
                .when()
                .post("/api/transfer");

        assertEquals(500, response.getStatusCode());
        JSONObject responseBodyAsJson = new JSONObject(response.getBody().asString());
        assertEquals(123, responseBodyAsJson.get("recipientAccount"));
        assertEquals(321, responseBodyAsJson.get("accountNumberFrom"));
        assertEquals(10000, responseBodyAsJson.get("transferAmount"));
        assertTrue(((String) responseBodyAsJson.get("rejectionReason")).contains("Error when transferring money amount"));
    }

    @Test
    public void testNotFoundTransfer() {
        JSONObject jsonObj = prepareTestNotFoundTransfer();

        Response response = given()
                .port(8080)
                .contentType("application/json")
                .body(jsonObj.toString())
                .when()
                .post("/api/transfer");

        assertEquals(404, response.getStatusCode());
        JSONObject responseBodyAsJson = new JSONObject(response.getBody().asString());
        assertEquals(123, responseBodyAsJson.get("recipientAccount"));
        assertEquals(1015, responseBodyAsJson.get("accountNumberFrom"));
        assertEquals(10000, responseBodyAsJson.get("transferAmount"));
        assertTrue(((String) responseBodyAsJson.get("rejectionReason")).contains("not found"));
    }

    @Test
    public void testBodyBadDataTransfer() {
        JSONObject jsonObj = prepareTestBadDataTransfer();

        Response response = given()
                .port(8080)
                .contentType("application/json")
                .body(jsonObj.toString())
                .when()
                .post("/api/transfer");

        assertEquals(400, response.getStatusCode());
        JSONObject responseBodyAsJson = new JSONObject(response.getBody().asString());
        assertEquals(123, responseBodyAsJson.get("recipientAccount"));
        assertEquals(123, responseBodyAsJson.get("accountNumberFrom"));
        assertEquals(10000, responseBodyAsJson.get("transferAmount"));
        assertTrue(((String) responseBodyAsJson.get("rejectionReason")).contains("money from and to your account"));
    }

}