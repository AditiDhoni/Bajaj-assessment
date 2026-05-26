package com.bfhl.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.bfhl.api.dto.BfhlRequest;
import com.bfhl.api.dto.BfhlResponse;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BfhlApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetBfhl() {
        ResponseEntity<Map> response = restTemplate.getForEntity("/bfhl", Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get("operation_code")).isEqualTo(1);
    }

    @Test
    void testPostBfhlExampleA() {
        BfhlRequest request = new BfhlRequest();
        request.setData(Arrays.asList("a", "1", "334", "4", "R", "$"));

        ResponseEntity<BfhlResponse> response = restTemplate.postForEntity("/bfhl", request, BfhlResponse.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        BfhlResponse body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.isSuccess()).isTrue();
        assertThat(body.getUserId()).isEqualTo("aditi_dhoni_24032005");
        assertThat(body.getEmail()).isEqualTo("aditidhoni231188@acropolis.in");
        assertThat(body.getRollNumber()).isEqualTo("0827IT231008");
        assertThat(body.getOddNumbers()).containsExactly("1");
        assertThat(body.getEvenNumbers()).containsExactly("334", "4");
        assertThat(body.getAlphabets()).containsExactly("A", "R");
        assertThat(body.getSpecialCharacters()).containsExactly("$");
        assertThat(body.getSum()).isEqualTo("339");
        assertThat(body.getConcatString()).isEqualTo("Ra");
    }

    @Test
    void testPostBfhlExampleB() {
        BfhlRequest request = new BfhlRequest();
        request.setData(Arrays.asList("2", "a", "y", "4", "&", "-", "*", "5", "92", "b"));

        ResponseEntity<BfhlResponse> response = restTemplate.postForEntity("/bfhl", request, BfhlResponse.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        BfhlResponse body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.isSuccess()).isTrue();
        assertThat(body.getUserId()).isEqualTo("aditi_dhoni_24032005");
        assertThat(body.getEmail()).isEqualTo("aditidhoni231188@acropolis.in");
        assertThat(body.getRollNumber()).isEqualTo("0827IT231008");
        assertThat(body.getOddNumbers()).containsExactly("5");
        assertThat(body.getEvenNumbers()).containsExactly("2", "4", "92");
        assertThat(body.getAlphabets()).containsExactly("A", "Y", "B");
        assertThat(body.getSpecialCharacters()).containsExactly("&", "-", "*");
        assertThat(body.getSum()).isEqualTo("103");
        assertThat(body.getConcatString()).isEqualTo("ByA");
    }

    @Test
    void testPostBfhlExampleC() {
        BfhlRequest request = new BfhlRequest();
        request.setData(Arrays.asList("A", "ABCD", "DOE"));

        ResponseEntity<BfhlResponse> response = restTemplate.postForEntity("/bfhl", request, BfhlResponse.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        BfhlResponse body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.isSuccess()).isTrue();
        assertThat(body.getUserId()).isEqualTo("aditi_dhoni_24032005");
        assertThat(body.getEmail()).isEqualTo("aditidhoni231188@acropolis.in");
        assertThat(body.getRollNumber()).isEqualTo("0827IT231008");
        assertThat(body.getOddNumbers()).isEmpty();
        assertThat(body.getEvenNumbers()).isEmpty();
        assertThat(body.getAlphabets()).containsExactly("A", "ABCD", "DOE");
        assertThat(body.getSpecialCharacters()).isEmpty();
        assertThat(body.getSum()).isEqualTo("0");
        assertThat(body.getConcatString()).isEqualTo("EoDdCbAa");
    }
}
