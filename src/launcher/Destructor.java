package launcher;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import logger.LogFormatter;
import logger.ObjectFilter;
import missile.AbstractMissile;
import missile.DestructedLanucher;
import missile.DestructedMissile;

public class Destructor extends Thread{

	private static Logger logger;

	private String 					id;
	private String 					type;
	private FileHandler 			fileHandler;
	private Vector<AbstractMissile>	Destructed;
	
	/**
	 * Constructor 
	 * @param id
	 * @param type
	 * @param destructed
	 * @throws SecurityException
	 * @throws IOException
	 */
	public Destructor(String id, String type, Vector<AbstractMissile> destructed)
			throws SecurityException, IOException {
		super();
		this.id = id;
		this.type = type;
		this.Destructed = destructed;
		
		fileHandler = new FileHandler("Destructor_"+this.id+".txt", false);
		fileHandler.setFilter(new ObjectFilter(this));
		fileHandler.setFormatter(new LogFormatter());
		logger = Logger.getLogger("warLogger");
		logger.addHandler(fileHandler);
	} 

	/**
	 * @return file handler 
	 */
	public FileHandler getFileHandler() {
		return fileHandler;
	}

	/**
	 * return id of destructor 
	 */
	public String getDestructorId() {
		return id;
	}

	/**
	 * return type of destructor 
	 */
	public String getType() {
		return type;
	}

	/**
	 * Add destruct missile by type 
	 * DestructedMissile / DestructedLanucher
	 * @param destruct
	 */
	public void addDestructMissile(AbstractMissile destruct) {

		if (destruct instanceof DestructedMissile) {
			((DestructedMissile)(destruct)).addFileHandler(this.fileHandler);
		}
		else if (destruct instanceof DestructedLanucher) {
			((DestructedLanucher)(destruct)).addFileHandler(this.fileHandler);
		}
		this.Destructed.add(destruct);
		synchronized (this) {
			this.notify();
		}
	}

	/**
	 * Run destructor 
	 */
	public void run() {
		try {
			int size = this.Destructed.size();
			synchronized (this) {
				//fix for new launcher 
				if (size == 0) {
					wait();
					size = this.Destructed.size();
				}
				for(int i = 0; i < size; i++) {
					((Thread) this.Destructed.get(i)).start();		
					if (i == size-1) {
						wait();
						size = this.Destructed.size();
					}
				}
			}
		}
		catch (InterruptedException e) {
		}
	}

}
