package equrylys.practice.libraryapp.entity;

import equrylys.practice.libraryapp.entity.BookEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "review")
public class BookReviewEntity {
    @Id
    private UUID id;
    private String rating;
    private String review;

    @ManyToOne
    private BookEntity book;
}
