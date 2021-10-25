package com.deloittedigital.library.client;

import com.deloittedigital.library.client.dto.AuthorDetails;
import com.deloittedigital.library.client.dto.AuthorListItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthorWebClientTest {

    @Autowired
    AuthorWebClient authorWebClient;

    @Test
    void getAuthors() {
        authorWebClient.getAuthorList()
                .subscribe(authorListItem -> assertNotNull(authorListItem.getId()));

    }

    @Test
    void getAuthor() {
        Integer authorId = 1;
        String authorName = "Liviu Rebreanu";
        AuthorDetails expectedAuthorDetails = AuthorDetails.builder()
                .id(authorId)
                .name(authorName)
                .build();
        authorWebClient.getAuthor(authorId).subscribe(authorDetails -> assertEquals(expectedAuthorDetails.getName(), authorDetails.getName()));

    }

}