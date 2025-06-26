package equrylys.practice.libraryapp.rest;

import equrylys.practice.libraryapp.beans.*;
import equrylys.practice.libraryapp.entity.AuthorEntity;
import equrylys.practice.libraryapp.entity.BookEntity;
import equrylys.practice.libraryapp.entity.BookReviewEntity;
import equrylys.practice.libraryapp.repository.AuthorRepository;
import equrylys.practice.libraryapp.repository.BookRepository;
import equrylys.practice.libraryapp.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/books")
public class BookController {
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;
    private final AuthorRepository authorRepository;

    @GetMapping
    public List<BookResponseBean> getBooks(BookParamBean bookParam) {
        List<BookEntity> bookEntityList = new ArrayList<>();
        if (bookParam.getTitle() != null && !bookParam.getTitle().isEmpty()) {
            bookEntityList.addAll(bookRepository.findByTitle(bookParam.getTitle()));
        } else if (bookParam.getGenre() != null && !bookParam.getGenre().isEmpty()) {
            bookEntityList.addAll(bookRepository.findByGenre(bookParam.getGenre()));
        } else if (bookParam.getDescription() != null && !bookParam.getDescription().isEmpty()) {
            bookEntityList.addAll(bookRepository.findByDescription(bookParam.getDescription()));
        } bookEntityList.addAll(bookRepository.findAll());
        List<BookResponseBean> returnBooks = new ArrayList<>();
        for (BookEntity i : bookEntityList){
            returnBooks.add(toDTO(i));
        }
        return returnBooks;
    }

    private BookResponseBean toDTO(BookEntity entity) {
        BookResponseBean item = new BookResponseBean();
        item.setId(entity.getId());
        item.setTitle(entity.getTitle());
        item.setGenre(entity.getGenre());
        item.setDescription(entity.getDescription());

        List<BookReviewEntity> reviewList = reviewRepository.findByBookId(entity.getId());
        List<ReviewResponseBean> reviewResponses = new ArrayList<>();
        for (BookReviewEntity review : reviewList){
            reviewResponses.add(toDTO(review));
        } item.setReviews(reviewResponses);

        AuthorEntity author = entity.getAuthor();
        if (author != null){
            AuthorResponseBean authorResponse = new AuthorResponseBean();
            authorResponse.setId(author.getId());
            authorResponse.setAuthor(author.getAuthor());
            item.setAuthors(authorResponse);
        }
        return item;
    }

    @GetMapping("/find-book-by-id/{bookId}")
    public BookResponseBean getBookById(@PathVariable UUID bookId){
        Optional<BookEntity> book = bookRepository.findById(bookId);
        if (book != null){
            BookEntity entity = book.get();
            return toDTO(entity);
        } else return null;
    }

    @PostMapping
    public void addNewBook(@RequestBody CreateBookRequestBean requestBody){
        BookEntity book = new BookEntity();
        book.setId(UUID.randomUUID());
        book.setTitle(requestBody.getTitle());
        book.setGenre(requestBody.getGenre());
        book.setDescription(requestBody.getDescription());
        bookRepository.save(book);
        log.info("Book saved");
    }

    @DeleteMapping("/id/{id}")
    public void deleteBookById(@PathVariable UUID id){
        bookRepository.deleteById(id);
        log.info("book deleted");
    }

    @PutMapping("/id/{id}")
    public String updateBook(@PathVariable UUID id, @RequestBody CreateBookRequestBean newData){
        Optional<BookEntity> books = bookRepository.findById(id);
        if (books.isEmpty()) {
            log.info("book does not exist");
        }
        BookEntity updatedBook = books.get();
        updatedBook.setTitle(newData.getTitle());
        updatedBook.setGenre(newData.getGenre());
        updatedBook.setDescription(newData.getDescription());
        bookRepository.save(updatedBook);
        log.info("book's data updated");
        return null;
    }

    @PostMapping("/review")
    public void addReview(@RequestBody ReviewRequestBean reviewRequest){
        BookReviewEntity review = new BookReviewEntity();
        review.setId(UUID.randomUUID());
        review.setRating(reviewRequest.getRating());
        review.setReview(reviewRequest.getReview());
        reviewRepository.save(review);
    }

    @GetMapping("/review")
    public List<ReviewResponseBean> getReview(){
        List<BookReviewEntity> reviews = reviewRepository.findAll();
        List<ReviewResponseBean> resultList = new ArrayList<>();
        for(BookReviewEntity i : reviews) {
            resultList.add(toDTO(i));
        }
        return resultList;
    }

    private ReviewResponseBean toDTO(BookReviewEntity entity){
        ReviewResponseBean result = new ReviewResponseBean();
        result.setId(entity.getId());
        result.setRating(entity.getRating());
        result.setReview(entity.getReview());
        return result;
    }

    @PostMapping("/id/{bookId}/add-review/{reviewId}")
    public void addReviewToBook(@PathVariable UUID bookId, @PathVariable UUID reviewId) {
        BookEntity book = bookRepository.findById(bookId).orElseThrow();
        BookReviewEntity review = reviewRepository.findById(reviewId).orElseThrow();
        review.setBook(book);
        reviewRepository.save(review);
    }

    @PostMapping("/author")
    public void addAuthor(@RequestBody AuthorRequestBean authorRequest){
        AuthorEntity author = new AuthorEntity();
        author.setId(UUID.randomUUID());
        author.setAuthor(authorRequest.getAuthor());
        authorRepository.save(author);
    }

    @GetMapping("/author")
    public List<AuthorResponseBean> getAuthor(){
        List<AuthorEntity> authors = authorRepository.findAll();
        List<AuthorResponseBean> resultList = new ArrayList<>();
        for(AuthorEntity i : authors) {
            resultList.add(toDTO(i));
        }
        return resultList;
    }

    private AuthorResponseBean toDTO(AuthorEntity entity){
        AuthorResponseBean result = new AuthorResponseBean();
        result.setId(entity.getId());
        result.setAuthor(entity.getAuthor());
        return result;
    }

    @PostMapping("/id/{bookId}/add-author/{authorId}")
    public void addAuthorToBook(@PathVariable UUID bookId, @PathVariable UUID authorId){
        BookEntity book = bookRepository.findById(bookId).orElseThrow();
        AuthorEntity author = authorRepository.findById(authorId).orElseThrow();
        book.setAuthor(author);
        bookRepository.save(book);
    }

    @DeleteMapping("/id/{bookId}/delete-author/{authorId}")
    public void deleteAuthorFromBook(@PathVariable UUID bookId, @PathVariable UUID authorId){
        BookEntity book = bookRepository.findById(bookId).orElseThrow();
        AuthorEntity author = authorRepository.findById(authorId).orElseThrow();
        book.setAuthor(null);
        bookRepository.save(book);
    }
}