package missile;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import launcher.Destructor;
import launcher.Launcher;
import war.War;

public class DestructedLanucher extends AbstractMissile {

	private static Logger 	logger;

	private Launcher 		target;
	private Destructor 		destructor;

	/**
	 * Constructor
	 * @param launcher
	 * @param destructTime
	 */
	public DestructedLanucher(Launcher target, int destructTime, 
			Destructor destructor, FileHandler fileHandler) {
		super (destructTime, fileHandler);
		this.target = target;
		this.destructor = destructor;

		logger = Logger.getLogger("warLogger");
	}

	/**
	 * Run object
	 */
	public void run() {
		Object arr[] = {this, target};
		try {
			// wait untill destroy after war launch
			sleep(super.getDelayBeforeLaunch() * War.TIME_INTERVAL); 

			synchronized (destructor) {
				logger.log(Level.INFO, "Trying to destroy launcher :"
						  + target.getLauncherId(), arr);
				destroyTarget();		// try to destroy launcher
			}
		} 
		catch (Exception e) { 
			// no success destroy target and the reason
			logger.log(Level.INFO, e.getMessage(), arr); 
		}
	}

	/**
	 * method to destroy a selected launcher
	 * @throws Exception if fails with message of fail reason
	 */
	@Override
	public void destroyTarget() throws Exception {
		Object arr[] = {this, target};
		double rate = Math.random();	// generate random success
		
		if (target.isRunning()) {
			if (!target.isHidden()) {
				// if rate bigger than success rate it will destroy
				if (rate > War.SUCCESS_RATE) {
					target.stopLauncher();
					String print_log = "Launcher " + target.getLauncherId()
									 + " was destroyed";
					logger.log(Level.INFO, print_log, arr);
				} 
				else {
					throw new Exception("Destruction of launcher "
									   + target.getLauncherId() 
									   + " was failed");
				}
			}
			else {
				throw new Exception("Destruction of launcher "
								   + target.getLauncherId() 
								   + " was failed - Launcher is hidden!");
			}
		} 
		else {
			throw new Exception("Destruction of launcher "
							   + target.getLauncherId() 
							   + " was failed - Launcher is not running!");
		}
	}

}
