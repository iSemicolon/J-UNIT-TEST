package org.kol.junit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/book")
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @GetMapping
    public List<Book> getAllBookRecords(){
        return  bookRepository.findAll();
    }

    @GetMapping(value = "{bookId}")
    public Book getBookById(@PathVariable(value = "bookId") Long bookId){
        return  bookRepository.findById(bookId).get();
    }

    @PostMapping
    public Book createBookRecord(@Valid @RequestBody  Book bookRecord){
        return  bookRepository.save(bookRecord);
    }

    @PutMapping
    public Book updateBookRecord(@Valid @RequestBody  Book bookRecord) throws  Exception{

        if(bookRecord==null || bookRecord.getBookId()==null)
        {
            throw  new NotFoundException("Book Record or Id can'nt be null");
        }

        Optional<Book> optionalBook=bookRepository.findById(bookRecord.getBookId());

        if(optionalBook.isPresent()){
            throw new NotFoundException(" book with bookId"+ bookRecord.getBookId()+ "doesn't exists");
        }

        Book existingBookRecord= optionalBook.get();
        existingBookRecord.setName(bookRecord.getName());
        existingBookRecord.setSummary(bookRecord.getSummary());
        existingBookRecord.setRating(bookRecord.getRating());
        return  bookRepository.save(existingBookRecord);
    }

    @DeleteMapping(value = "{bookId}")
    public void deleteBookById(@PathVariable(value = "bookId") Long bookId) throws  Exception{

        if(!bookRepository.findById(bookId).isPresent())
        {
            throw  new NotFoundException("Book Id "+ bookId+ "Not present");
        }

         bookRepository.deleteById(bookId);
    }
}































