package com.deloittedigital.library.client;

import com.deloittedigital.library.client.dto.AuthorDetails;
import com.deloittedigital.library.client.dto.AuthorListItem;
import com.deloittedigital.library.config.AuthorClientConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AuthorWebClient {

    private final AuthorClientConfiguration authorClientConfiguration;
    private WebClient webClient;

    public AuthorWebClient(AuthorClientConfiguration authorClientConfiguration) {
        this.authorClientConfiguration = authorClientConfiguration;
        this.webClient = WebClient.create(authorClientConfiguration.getBase());
    }

    public Flux<AuthorListItem> getAuthorList() {
        return webClient.get()
                .retrieve().bodyToFlux(AuthorListItem.class);

    }

    public Mono<AuthorDetails> getAuthor(Integer id) {
        return webClient.get().uri(authorClientConfiguration.getSpecific(), id)
                .retrieve().bodyToMono(AuthorDetails.class);
    }

}
