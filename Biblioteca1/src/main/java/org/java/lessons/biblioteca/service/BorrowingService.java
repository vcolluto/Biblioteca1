package org.java.lessons.biblioteca.service;

import java.time.LocalDate;
import java.util.List;

import org.java.lessons.biblioteca.model.Book;
import org.java.lessons.biblioteca.model.Borrowing;
import org.java.lessons.biblioteca.repository.BookRepository;
import org.java.lessons.biblioteca.repository.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

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
	
	public boolean validate(Borrowing borrowing,BindingResult bindingResult) {
		boolean res=true;
		
		if (borrowing.getReturnDate()!=null && borrowing.getBorrowingDate()!=null &&
			borrowing.getReturnDate().isBefore(borrowing.getBorrowingDate())) {
			bindingResult.addError(new FieldError("borrowing", "returnDate", "return date must be after borrowing date"));
			res=false;
		}
		//eventuali altre validazioni
		return res;		
			
	}

}
