package missile;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import launcher.Destructor;
import war.War;

public class DestructedMissile extends AbstractMissile {
	private static Logger   logger;
	
	private Missile 		target;
	private Destructor destructor;

	/**
	 * Constructor 
	 * @param target
	 * @param destructAfterLaunch
	 */
	public DestructedMissile(Missile target, int destructAfterLaunch, 
			Destructor destructor, FileHandler fileHandler) {
		super (destructAfterLaunch, fileHandler);
		this.target = target;
		this.destructor = destructor;
		logger = Logger.getLogger("warLogger");
	}

	/**
	 * Run object
	 */
	public void run() {
		Object arr[] = { this, target };
		try {
			// wait untill destroy after war launch
			sleep(super.getDelayBeforeLaunch() * War.TIME_INTERVAL); 

			synchronized (destructor) {
				logger.log(Level.INFO, "Trying to destroy missile :"
						  + target.getMissileId()
						  + " from Missile Destructor", arr);
				destroyTarget();		// try to destroy missile
			}
		} catch (Exception e) {
			//no success destroy target and reason
			logger.log(Level.INFO, e.getMessage(), arr);
		}
	}
	
	/**
	 * method to destroy a selected mussile
	 * @throws Exception if fails with message of fail reason
	 */
	@Override
	public void destroyTarget() throws Exception {
		Object arr[] = { this, target };
		double rate = Math.random();		// generate random success
			
		if ((target.getStatus() == Missile.Status.Launched) &&
				(this.getDelayBeforeLaunch() < target.getFlyTime() + target.getLaunchTime())) {
			// if rate bigger than success rate it will destroy
			if (rate > War.SUCCESS_RATE) {
				synchronized (target) {
					target.interrupt();
				}
				String print_log = "Missle " + target.getMissileId()
						+ " was destroyed";
				logger.log(Level.INFO, print_log, arr);
			} else {
				throw new Exception("Destruction of missile "
						+ target.getMissileId() 
						+ " was failed");
			}
		} else {
			throw new Exception("Destruction of missile "
					+ target.getMissileId() 
					+ " was failed - Missile is not running");
		}
	}

}
