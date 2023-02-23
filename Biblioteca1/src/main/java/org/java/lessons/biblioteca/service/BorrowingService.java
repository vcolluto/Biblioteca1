package org.java.lessons.biblioteca.service;

import java.time.LocalDate;
import java.util.List;

import org.java.lessons.biblioteca.model.Book;
import org.java.lessons.biblioteca.model.Borrowing;
import org.java.lessons.biblioteca.repository.BookRepository;
import org.java.lessons.biblioteca.repository.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowingService {
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	BorrowingRepository borrowingRepository;
	
	public Borrowing Create(Integer bookId) throws Exception {
		Borrowing borrowing=new Borrowing();
		Book book=bookRepository.getReferenceById(bookId);
		
		//validazioni di tipo applicativo
		if (book.getAvailableCopies()>0) {			
			borrowing.setBook(book);
		}	
		else
			throw new Exception("Not enough copies. BookId="+bookId);
		
		//valori predefiniti
		borrowing.setBorrowingDate(LocalDate.now());
		return borrowing;
		
	}

	public Borrowing save(Borrowing borrowing) {
		Book book=borrowing.getBook();
		book.setAvailableCopies(book.getAvailableCopies()-1);
		return borrowingRepository.save(borrowing);
	}
	
	public List<Borrowing> findAll() {
		return borrowingRepository.findAll();
	}

}
