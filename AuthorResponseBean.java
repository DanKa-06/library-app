package equrylys.practice.libraryapp.beans;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AuthorResponseBean {
    @Id
    private UUID id;
    private String author;
}
