package com.spring.exceptions;
import java.time.LocalDate;

public class ErrorResponse {

     private String problem;
     private LocalDate dateofproblem;
		
		public ErrorResponse(String problem, LocalDate dateofproblem) {
			super();
			this.problem = problem;
			this.dateofproblem = dateofproblem;
		}

		public String getProblem() {
			return problem;
		}

		public void setProblem(String problem) {
			this.problem = problem;
		}

		public LocalDate getDateofproblem() {
			return dateofproblem;
		}

		public void setDateofproblem(LocalDate dateofproblem) {
			this.dateofproblem = dateofproblem;
		}
		
		
}
