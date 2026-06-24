package com.example.bfhl.controller;

import com.example.bfhl.dto.BfhlRequest;
import com.example.bfhl.dto.BfhlResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BfhlControllerTest {

    @Autowired
    private BfhlController bfhlController;

    @Test
    public void testGetEndpoint() {
        ResponseEntity<Map<String, Object>> response = bfhlController.handleGet();
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().get("operation_code"));
    }

    @Test
    public void testRootEndpoint() {
        org.springframework.http.ResponseEntity<org.springframework.core.io.Resource> response = bfhlController.handleRoot();
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    public void testHealthEndpoint() {
        ResponseEntity<Map<String, Object>> response = bfhlController.handleHealth();
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("UP", response.getBody().get("status"));
    }

    @Test
    public void testPostEndpointExampleA() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("a", "1", "334", "4", "R", "$"));
        ResponseEntity<BfhlResponse> responseEntity = bfhlController.handlePost(request);
        
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCode().value());
        
        BfhlResponse response = responseEntity.getBody();
        assertNotNull(response);
        assertTrue(response.getIs_success());
        assertEquals("1", response.getOdd_numbers().get(0));
        assertEquals("334", response.getEven_numbers().get(0));
        assertEquals("4", response.getEven_numbers().get(1));
        assertEquals("A", response.getAlphabets().get(0));
        assertEquals("R", response.getAlphabets().get(1));
        assertEquals("$", response.getSpecial_characters().get(0));
        assertEquals("339", response.getSum());
        assertEquals("Ra", response.getConcat_string());
    }

    @Test
    public void testPostEndpointExampleB() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("2", "a", "y", "4", "&", "-", "*", "5", "92", "b"));
        ResponseEntity<BfhlResponse> responseEntity = bfhlController.handlePost(request);
        
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCode().value());
        
        BfhlResponse response = responseEntity.getBody();
        assertNotNull(response);
        assertTrue(response.getIs_success());
        assertEquals("5", response.getOdd_numbers().get(0));
        assertEquals("2", response.getEven_numbers().get(0));
        assertEquals("4", response.getEven_numbers().get(1));
        assertEquals("92", response.getEven_numbers().get(2));
        assertEquals("A", response.getAlphabets().get(0));
        assertEquals("Y", response.getAlphabets().get(1));
        assertEquals("B", response.getAlphabets().get(2));
        assertEquals("&", response.getSpecial_characters().get(0));
        assertEquals("-", response.getSpecial_characters().get(1));
        assertEquals("*", response.getSpecial_characters().get(2));
        assertEquals("103", response.getSum());
        assertEquals("ByA", response.getConcat_string());
    }

    @Test
    public void testPostEndpointExampleC() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("A", "ABCD", "DOE"));
        ResponseEntity<BfhlResponse> responseEntity = bfhlController.handlePost(request);
        
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCode().value());
        
        BfhlResponse response = responseEntity.getBody();
        assertNotNull(response);
        assertTrue(response.getIs_success());
        assertTrue(response.getOdd_numbers().isEmpty());
        assertTrue(response.getEven_numbers().isEmpty());
        assertEquals("A", response.getAlphabets().get(0));
        assertEquals("ABCD", response.getAlphabets().get(1));
        assertEquals("DOE", response.getAlphabets().get(2));
        assertTrue(response.getSpecial_characters().isEmpty());
        assertEquals("0", response.getSum());
        assertEquals("EoDdCbAa", response.getConcat_string());
    }
}
