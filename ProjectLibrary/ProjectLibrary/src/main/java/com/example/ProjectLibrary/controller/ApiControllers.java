package com.example.ProjectLibrary.controller;

import com.example.ProjectLibrary.model.Book;
import com.example.ProjectLibrary.repo.BookRepo;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ApiControllers {
    @Autowired
    private BookRepo bookRepo;

    @GetMapping(value="/")
    public String getPage(){
        return "Welcome";
    }

    @GetMapping(value="/getBooks")
    public List<Book> getBooks(){
        return bookRepo.findAll();
    }

    @PostMapping(value="/createBook")
    public String createBook(@RequestBody Book book){


        if(bookRepo.findByTitle(book.getTitle()).isEmpty()){
            try {
                bookRepo.save(book);  // Save the book
                return "save successful";
            } catch (Exception e) {
                return "Error saving book: " + e.getMessage();  // Return error message in case of failure
            }
        }




        /*

            Book book1 = new Book(title, author, publisher, yearPublished, price, quantity);

            bookRepo.save(book1);

            return "save successful";

         */



        else {



            return "duplicate title";
        }



    }

    @PutMapping(value="/update/{id}")
    public String deleteBook(@PathVariable long id, @RequestBody Book book){


        if(bookRepo.findById(id).isPresent()){
            Book book1 = bookRepo.findById(id).get();
            book1.setAuthor(book.getAuthor());
            book1.setPrice(book.getPrice());
            book1.setPublisher(book.getPublisher());
            book1.setQuantity(book.getQuantity());
            book1.setTitle(book.getTitle());
            book1.setYearPublished(book.getYearPublished());

            bookRepo.save(book1);
            return "update successful";
        }
        else{
            return "Book not found";
        }
    }

    @DeleteMapping(value="/delete/{id}")
    public String deleteBook(@PathVariable long id){


        if(bookRepo.findById(id).isPresent()){
            Book book1 = bookRepo.findById(id).get();
            bookRepo.delete(book1);
            return "Deleted successfully";
        }
        else{
            return "Book not found";
        }
    }
}
