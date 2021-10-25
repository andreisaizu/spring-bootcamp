package com.deloittedigital.library.client;

import com.deloittedigital.library.client.dto.AuthorDetails;
import com.deloittedigital.library.client.dto.AuthorListItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class AuthorRestTemplateClientTest {

    @Autowired
    AuthorRestTemplateClient authorRestTemplateClient;

    @Test
    void getAuthors() {
        AuthorListItem[] teamListItems = authorRestTemplateClient.getAllAuthors();
        assertTrue(teamListItems.length > 0);
    }

    @Test
    void getAuthor() {
        ResponseEntity<AuthorDetails> responseEntity = authorRestTemplateClient.getAuthor(1);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertNotNull(responseEntity.getBody());

        AuthorDetails authorDetails = responseEntity.getBody();
        assertEquals("Liviu Rebreanu", authorDetails.getName());

    }

}