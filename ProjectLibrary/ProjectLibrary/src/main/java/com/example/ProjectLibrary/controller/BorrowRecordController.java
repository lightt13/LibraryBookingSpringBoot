package com.example.ProjectLibrary.controller;

import com.example.ProjectLibrary.model.Book;
import com.example.ProjectLibrary.model.BorrowRecord;
import com.example.ProjectLibrary.model.User;
import com.example.ProjectLibrary.repo.BookRepo;
import com.example.ProjectLibrary.repo.BorrowRecordRepository;
import com.example.ProjectLibrary.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/borrow")
public class BorrowRecordController {

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private UserRepo userRepository;


    @PostMapping("/borrowBook/{userId}/{bookId}")
    public String borrowBook(@PathVariable Long userId, @PathVariable Long bookId) {
        Optional<Book> bookOpt = bookRepo.findById(bookId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (bookOpt.isEmpty() || userOpt.isEmpty()) {
            return "User or Book not found.";
        }

        Book book = bookOpt.get();

        if (book.getQuantity() <= 0) {
            return "Book is currently not available.";
        }


        book.setQuantity(book.getQuantity() - 1);
        bookRepo.save(book);

        BorrowRecord record = new BorrowRecord();
        record.setBook(book);
        record.setUser(userOpt.get());
        record.setBorrowDate(LocalDate.now());
        record.setReturned(false);

        borrowRecordRepository.save(record);
        return "Book borrowed successfully.";
    }


    @PostMapping("/returnBook/{borrowId}")
    public String returnBook(@PathVariable Long borrowId) {
        Optional<BorrowRecord> recordOpt = borrowRecordRepository.findById(borrowId);

        if (recordOpt.isEmpty()) {
            return "Borrow record not found.";
        }

        BorrowRecord record = recordOpt.get();
        if (record.isReturned()) {
            return "Book already returned.";
        }

        record.setReturned(true);
        record.setReturnDate(LocalDate.now());
        borrowRecordRepository.save(record);


        Book book = record.getBook();
        book.setQuantity(book.getQuantity() + 1);
        bookRepo.save(book);

        return "Book returned successfully.";
    }


    @GetMapping
    public List<BorrowRecord> getAllBorrowRecords() {
        return borrowRecordRepository.findAll();
    }


    @GetMapping("/user/{userId}")
    public List<BorrowRecord> getBorrowRecordsByUser(@PathVariable Long userId) {
        return borrowRecordRepository.findByUserId(userId);
    }


    @GetMapping("/book/{bookId}")
    public List<BorrowRecord> getBorrowRecordsByBook(@PathVariable Long bookId) {
        return borrowRecordRepository.findByBookId(bookId);
    }
}
