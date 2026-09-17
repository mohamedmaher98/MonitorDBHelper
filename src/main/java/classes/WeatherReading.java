package classes;

import java.time.LocalDateTime;

public class WeatherReading {
	private double latu;
	private double logu;
	private double temp;
	private LocalDateTime readingTime;
	private LocalDateTime recordedAt;

	public double getLatu() {
		return latu;
	}

	public void setLatu(double latu) {
		this.latu = latu;
	}

	public double getLogu() {
		return logu;
	}

	public void setLogu(double logu) {
		this.logu = logu;
	}

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public LocalDateTime getReadingTime() {
		return readingTime;
	}

	public void setReadingTime(LocalDateTime readingTime) {
		this.readingTime = readingTime;
	}

	public LocalDateTime getRecordedAt() {
		return recordedAt;
	}

	public void setRecordedAt(LocalDateTime recordedAt) {
		this.recordedAt = recordedAt;
	}

}
