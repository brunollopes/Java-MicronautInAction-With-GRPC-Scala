package com.bole.controller;

import com.bole.domain.AccountInfoMock;
import com.bole.domain.UserInfoMock;
import com.bole.grpc.server.LoginServiceImpl;
import com.twitter.finagle.ListeningServer;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for {@link UserAccount}.
 */

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private Server grpServer;

    private ListeningServer clientUserServer;

    private ListeningServer clientAccountServer;

    @Before
    public void launchRpcServer() throws Exception {
        grpServer = ServerBuilder
                .forPort(8081)
                .addService(new LoginServiceImpl()).build();

        grpServer.start();

        clientUserServer = UserInfoMock.start();
        clientAccountServer = AccountInfoMock.start();
    }

    @After
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
        ResultActions result = this.mockMvc.perform(post("/springboot/useraccount/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"username\":\"bla\",\"password\":\"foo\"}")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

    }

    @Test
    public void givenUserAccountEndpoint_withCredentials_thenGetPayloadOK() throws Exception {

        String expectedPayload =
                "{\"accountInfo\":" +
                        "{\"accountNumber\":\"12345-3346-3335-4456\"}," +
                        "\"userInfo\":" +
                        "{\"name\":\"John\",\"surname\":\"Doe\",\"sex\":\"male\",\"age\":\"32\"}}";

        ResultActions result = this.mockMvc.perform(post("/springboot/useraccount/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"username\":\"bla\",\"password\":\"foo\"}")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(expectedPayload));

    }

}