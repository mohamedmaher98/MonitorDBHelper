package util;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogUtil {

	public static void log(LogLevel level, String message, Class<?> clazz) {
		LocalDateTime dateTime = LocalDateTime.now();
		String dateTiemFormat = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		String fileName = LocalDate.now().toString();
		try (FileWriter writer = new FileWriter("D:/learn-lab/logs/" + fileName + ".log", true);) {
			writer.write(dateTiemFormat + " " + level + " " + clazz.getName() + " " + message + System.lineSeparator());
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}

	}
}
