package equrylys.practice.libraryapp.entity;

import equrylys.practice.libraryapp.entity.AuthorEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Table(name = "books")
@Entity
@RequiredArgsConstructor
public class BookEntity {
    @Id
    private UUID id;
    private String title;
    private String genre;
    private String description;

    @ManyToOne
    private AuthorEntity author;
}