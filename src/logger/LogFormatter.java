package logger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;


public class LogFormatter extends Formatter {

	@Override
	public String format(LogRecord record) {
		StringBuffer buffer = new StringBuffer(1000);
		LocalDateTime ldate = LocalDateTime.now();
		DateTimeFormatter date = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		buffer.append(ldate.format(date));
		buffer.append(" ");
		buffer.append(formatMessage(record));
		buffer.append("\n");
		return buffer.toString();
	}
	
}
