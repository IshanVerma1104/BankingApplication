package com.example.demo.exception;

import java.time.LocalDate;
import java.util.Objects;
import lombok.Data;

@Data
public class ErrorResponse 
{
	private String issueName;
	private LocalDate date;
	
	
	public ErrorResponse(String issueName, LocalDate date) {
		super();
		this.issueName = issueName;
		this.date = date;
	}
	public String getIssueName() {
		return issueName;
	}
	public void setIssueName(String issueName) {
		this.issueName = issueName;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public ErrorResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public int hashCode() {
		return Objects.hash(date, issueName);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ErrorResponse other = (ErrorResponse) obj;
		return Objects.equals(date, other.date) && Objects.equals(issueName, other.issueName);
	}
	@Override
	public String toString() {
		return "ErrorResponse [issueName=" + issueName + ", date=" + date + "]";
	}
}
