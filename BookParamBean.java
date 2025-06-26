package equrylys.practice.libraryapp.beans;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BookParamBean {
    @Id
    private UUID id;
    private String title;
    private String genre;
    private String description;
}
