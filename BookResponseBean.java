package equrylys.practice.libraryapp.beans;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class BookResponseBean {
    @Id
    private UUID id;
    private String title;
    private String genre;
    private String description;

    private List<ReviewResponseBean> reviews;
    private AuthorResponseBean authors;
}