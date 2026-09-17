package classes;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class WeatherRuns {
private LocalDateTime startDate ;
private int readingDuration;
private boolean success;
private String errorMassage;



public LocalDateTime getStartDate() {
	return startDate;
}
public void setStartDate(LocalDateTime startDate) {
	this.startDate = startDate;
}
public int getReadingDuration() {
	return readingDuration;
}
public void setReadingDuration(int readingDuration) {
	this.readingDuration = readingDuration;
}
public boolean isSuccess() {
	return success;
}
public void setSuccess(boolean success) {
	this.success = success;
}
public String getErrorMassage() {
	return errorMassage;
}
public void setErrorMassage(String errorMassage) {
	this.errorMassage = errorMassage;
}



}
