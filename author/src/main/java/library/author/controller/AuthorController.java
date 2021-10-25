package library.author.controller;

import library.author.dto.AuthorDetails;
import library.author.dto.AuthorListItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/author")
public class AuthorController {
    private List<AuthorListItem> authorListItems = List.of(
            AuthorListItem.builder()
                    .id(1)
                    .name("Liviu Rebreanu")
                    .build(),
            AuthorListItem.builder()
                    .id(2)
                    .name("Mihai Eminescu")
                    .build(),
            AuthorListItem.builder()
                    .id(3)
                    .name("Ion Creanga")
                    .build());

    private List<AuthorDetails> authorDetails = List.of(
            AuthorDetails.builder()
                    .id(1)
                    .name("Liviu Rebreanu")
                    .yearOfBirth(1885)
                    .yearOfDeath(1944)
                    .build(),
            AuthorDetails.builder()
                    .id(2)
                    .yearOfBirth(1850)
                    .yearOfDeath(1889)
                    .name("Mihai Eminescu")
                    .build(),
            AuthorDetails.builder()
                    .id(3)
                    .yearOfBirth(1837)
                    .yearOfDeath(1889)
                    .name("Ion Creanga")
                    .build());

    @GetMapping
    public ResponseEntity<List<AuthorListItem>> getAuthors() {
        return ResponseEntity.ok(authorListItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDetails> getAuthor(@PathVariable Integer id) {
        Optional<AuthorDetails> authorDetailsOptional = authorDetails.stream()
                .filter(detail -> detail.getId().equals(id))
                .findFirst();
        return authorDetailsOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
