package equrylys.practice.libraryapp.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookRequestBean {
    private String title;
    private String genre;
    private String description;
}