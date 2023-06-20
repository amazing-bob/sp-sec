package com.sp.fc.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasicAuthenticationApplicationTest {

    @LocalServerPort
    int port;

    RestTemplate client = new RestTemplate();

    private String greetingUrl () {
        return "http://localhost:"+ port +"/greeting";
    }

    @DisplayName("1. 인증 실패")
    @Test
    void test_1() {
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            client.getForObject(greetingUrl(), String.class);
        });
        assertEquals(401, exception.getRawStatusCode());
    }

    @DisplayName("2. 인증 성공")
    @Test
    void test_2(){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Basic "+ Base64.getEncoder().encodeToString(
                "user1:1111".getBytes()
        ));
        HttpEntity endtity = new HttpEntity(null, headers);
        ResponseEntity response =  client.exchange(greetingUrl(), HttpMethod.GET, endtity, String.class);
        assertEquals("hello", response.getBody());
    }

    @DisplayName("3. 인증 성공3")
    @Test
    void test_3(){
        TestRestTemplate testClient = new TestRestTemplate("user1", "1111");
        String response = testClient.getForObject(greetingUrl(), String.class);
        assertEquals("hello", response);
    }

    @DisplayName("4. POST 인증")
    @Test
    void test_4(){
        TestRestTemplate testClient = new TestRestTemplate("user1", "1111");
        ResponseEntity<String> response =  testClient.postForEntity(greetingUrl(), "edgar", String.class);
        assertEquals("hello edgar", response.getBody());
    }






}
