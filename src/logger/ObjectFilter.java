package logger;
import java.util.Vector;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class ObjectFilter implements Filter {

	private Vector<Object> objects = new Vector<Object>();

	public ObjectFilter(Object toFilter) {
		this.objects.add(toFilter);
	}
	
	public void addFilter(Object toFilter) {
		this.objects.add(toFilter);
	}
	
	public boolean isLoggable(LogRecord rec) {
		if(rec.getParameters() != null) {
			int size = objects.size();
			for(int j = 0; j < size; j++) {
				for(int i = 0; i < rec.getParameters().length; i++) {
					Object temp = rec.getParameters()[i];
					if(objects.get(j) == temp)
						return true;
				}
			}
		}
		return false;	
	}

}
