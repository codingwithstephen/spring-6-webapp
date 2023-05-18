package guru.springframework.spring6webapp.bootstrap;

import guru.springframework.spring6webapp.domain.Author;
import guru.springframework.spring6webapp.domain.Book;
import guru.springframework.spring6webapp.domain.Publisher;
import guru.springframework.spring6webapp.repositories.AuthorRepository;
import guru.springframework.spring6webapp.repositories.BookRepository;
import guru.springframework.spring6webapp.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class BootstrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository,
                         PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Author savedAuthor = addAuthor("Stephen", "Mooney");

        Book savedBook = addBook("Inverting the pyramid", "1234");

        savedAuthor.getBooks().add(savedBook);
        savedBook.getAuthors().add(savedAuthor);
        authorRepository.save(savedAuthor);

        Publisher publisher = addPublisher("10 Wynchurch Park", "Belfast", "Antrim", "1234");

        List<Book> books = new ArrayList<>();
        books.add(savedBook);

        publisher.setBooks(new HashSet<>(books));

        publisherRepository.save(publisher);
        savedBook.setPublisher(publisher);
        bookRepository.save(savedBook);

        System.out.println("In Bootstrap");
        System.out.println("Author count: " + authorRepository.count());
        System.out.println("Book count: " + bookRepository.count());
        System.out.println("Publisher count: " + publisherRepository.count());
        System.out.println("Book publisher count: " + publisherRepository.count());
    }

    private Publisher addPublisher(String address, String city, String state, String zip) {
        Publisher publisher = new Publisher();
        publisher.setAddress(address);
        publisher.setCity(city);
        publisher.setState(state);
        publisher.setZip(zip);

        Publisher savedPublisher = publisherRepository.save(publisher);
        return savedPublisher;
    }

    private Book addBook(String title, String isbn) {
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);

        Book savedBook = bookRepository.save(book);
        return savedBook;
    }

    private Author addAuthor(String firstName, String lastName) {
        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);

        Author savedAuthor = authorRepository.save(author);
        return savedAuthor;
    }
}
