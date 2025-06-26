package equrylys.practice.libraryapp.repository;

import equrylys.practice.libraryapp.entity.BookReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<BookReviewEntity, UUID> {
    List<BookReviewEntity> findByBookId(UUID bookId);
}