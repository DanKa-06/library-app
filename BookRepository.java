package equrylys.practice.libraryapp.repository;

import equrylys.practice.libraryapp.entity.AuthorEntity;
import equrylys.practice.libraryapp.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface BookRepository extends JpaRepository<BookEntity, UUID> {
    List<BookEntity> findByTitle(String title);
    List<BookEntity> findByGenre(String genre);
    List<BookEntity> findByAuthor(AuthorEntity author);
    List<BookEntity> findByDescription(String description);
}