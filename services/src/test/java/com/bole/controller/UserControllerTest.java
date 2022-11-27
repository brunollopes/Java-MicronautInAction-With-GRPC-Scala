package com.bole.controller;

import com.bole.domain.AccountInfoMock;
import com.bole.domain.UserInfoMock;
import com.bole.grpc.server.LoginServiceImpl;
import com.twitter.finagle.ListeningServer;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.micronaut.http.*;
import io.micronaut.http.client.HttpClient;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

    @Inject
    HttpClient httpClient;

    private static Server grpServer;

    private static ListeningServer clientUserServer;

    private static ListeningServer clientAccountServer;

    @BeforeAll
    public void lunchRpcServer() throws Exception {
        grpServer = ServerBuilder
                .forPort(8082)
                .addService(new LoginServiceImpl()).build();

        grpServer.start();

        clientUserServer = UserInfoMock.start();
        clientAccountServer = AccountInfoMock.start();
    }

    @AfterAll
    public void shutdownRpcServer() throws Exception {
        clientUserServer.close();
        clientAccountServer.close();

        while(!clientUserServer.isClosed());
        while(!clientAccountServer.isClosed());

        grpServer.shutdown();
        grpServer.awaitTermination();
    }

    @Test
    public void givenUserAccountEndpoint_withCredentialsOK_thenGet200() throws Exception {
        HttpRequest request = HttpRequest.create(HttpMethod.POST,"http://localhost:8081/micronaut/useraccount/")
                .accept(MediaType.APPLICATION_JSON)
                .body("{\"username\":\"bla\",\"password\":\"foo\"}");

        HttpResponse response = httpClient.toBlocking().exchange(request);

        Assertions.assertEquals(HttpStatus.OK.getCode(),response.status().getCode());


    }

    @Test
    public void givenUserAccountEndpoint_withCredentialsOK_thenBodyOK() throws Exception {
        HttpRequest request2 = HttpRequest.POST("http://localhost:8081/micronaut/useraccount/","{\"username\":\"bla\",\"password\":\"foo\"}")
                .accept(MediaType.APPLICATION_JSON);

        HttpResponse<String> response = httpClient.toBlocking().exchange(request2, String.class);


        Assertions.assertEquals("{\"accountInfo\":{\"accountNumber\":\"12345-3346-3335-4456\"},\"userInfo\":{\"name\":\"John\",\"surname\":\"Doe\",\"sex\":\"male\",\"age\":\"32\"}}",
                response.body());


    }
}
