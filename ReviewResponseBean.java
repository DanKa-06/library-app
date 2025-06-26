package equrylys.practice.libraryapp.beans;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReviewResponseBean {
    @Id
    private UUID id;
    private String rating;
    private String review;
}
