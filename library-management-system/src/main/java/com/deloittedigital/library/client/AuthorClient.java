package com.deloittedigital.library.client;

import com.deloittedigital.library.client.dto.AuthorDetails;
import com.deloittedigital.library.client.dto.AuthorListItem;
import com.deloittedigital.library.config.AuthorClientConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AuthorClient {

    private final AuthorClientConfiguration authorClientConfiguration;

    private final RestTemplate restTemplate = new RestTemplate();

    public AuthorListItem[] getAllAuthors() {
        return restTemplate.getForObject(authorClientConfiguration.getBase(), AuthorListItem[].class);
    }

    public ResponseEntity<AuthorDetails> getAuthor(int authorId) {
        return restTemplate.getForEntity(authorClientConfiguration.getSpecific(), AuthorDetails.class, authorId);
    }

    public AuthorListItem addNewAuthor(AuthorDetails authorDetails) {
        return restTemplate.postForObject(authorClientConfiguration.getBase(), authorDetails, AuthorListItem.class);
    }

    public void updateAuthor(AuthorDetails authorDetails, Integer authorId) {
        restTemplate.put(authorClientConfiguration.getSpecific(), authorDetails, authorId);
    }

    public void deleteAuthor(Integer authorId) {
        HttpEntity httpEntity = HttpEntity.EMPTY;
        restTemplate.delete(authorClientConfiguration.getSpecific(), authorId);
    }
}
