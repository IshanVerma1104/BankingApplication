package com.example.demo.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CGCExceptionHandler {

	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleAccountNotFoundException(AccountNotFoundException ex){
		ErrorResponse error = new ErrorResponse(ex.getMessage(), LocalDate.now());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<ErrorResponse> handleInsufficientBalanceException(InsufficientBalanceException ex){
		ErrorResponse error = new ErrorResponse(ex.getMessage(), LocalDate.now());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(CustomerNotFoundException ex){
		ErrorResponse error = new ErrorResponse(ex.getMessage(), LocalDate.now());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(FDAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleFDAlreadyExistsException(FDAlreadyExistsException ex){
		ErrorResponse error = new ErrorResponse(ex.getMessage(), LocalDate.now());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(FDNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleFDNotFoundException(FDNotFoundException ex){
		ErrorResponse error = new ErrorResponse(ex.getMessage(), LocalDate.now());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(NoDataFoundException.class)
	public ResponseEntity<ErrorResponse> handleNoDataNotFoundException(NoDataFoundException ex){
		ErrorResponse error = new ErrorResponse(ex.getMessage(), LocalDate.now());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(InvalidAmountException.class)
	public ResponseEntity<ErrorResponse> handleInvalidAmountException(InvalidAmountException ex){
		ErrorResponse error = new ErrorResponse(ex.getMessage(), LocalDate.now());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(CustomerNotActiveException.class)
	public ResponseEntity<ErrorResponse> handleCustomerNotActiveException(CustomerNotActiveException ex){
		ErrorResponse error = new ErrorResponse(ex.getMessage(), LocalDate.now());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(InputNotCorrectException.class)
	public ResponseEntity<ErrorResponse> handleInputNotCorrectException(InputNotCorrectException ex){
		ErrorResponse error = new ErrorResponse(ex.getMessage(), LocalDate.now());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);
	}
}
