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
}
