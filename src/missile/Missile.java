package missile;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import launcher.Launcher;
import war.War;

public class Missile extends AbstractMissile {

	public enum Status {Waiting, Launched, Destroyed, Hit};
	
	private static Logger 	logger;

	private String 			missileId;
	private String 			destination;
	private int 			flyTime;
	private int 			damage;
	private Launcher 		launcher;
	private Status			status;

	/**
	 * Constructor 
	 * @param id
	 * @param destination
	 * @param launchTime
	 * @param flyTime
	 * @param damage
	 * @param fileHandler
	 * @param launcher
	 */
	public Missile(String id, String destination, int launchTime, int flyTime,
			int damage, FileHandler fileHandler, Launcher launcher) {
		super(launchTime, fileHandler);
		this.missileId = id;
		this.destination = destination;
		this.flyTime = flyTime;
		this.damage = damage;
		this.launcher = launcher;
		this.setStatus(Status.Waiting);
		
		logger = Logger.getLogger("warLogger");
	}
	
	/**
	 * @return id of missile 
	 */
	public String getMissileId() {
		return missileId;
	}
	
	/**
	 * @return launch time
	 */
	public int getLaunchTime() {
		return super.getDelayBeforeLaunch();
	}

	/**
	 * @return fly time
	 */
	public int getFlyTime() {
		return flyTime;
	}

	/**
	 * @return damage
	 */
	public int getDamage() {
		return damage;
	}
	
	/**
	 * @return missile status 
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * set status
	 * @param status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/** 
	 * Run missile 
	 */
	public void run() {
		boolean reveal_status = false;
		try {
			sleep(getLaunchTime() * War.TIME_INTERVAL);
			
			synchronized (launcher) {
				if (launcher.isRunning()) {
					this.setStatus(Status.Launched);
					// make launcher not hidden for X amount of time
					if (launcher.isHidden()) {
						launcher.revealYourSelf(); 
						reveal_status = true;
					}
					String print_log = "Missle "+ this.missileId 
									 + " was launched from launcher: "
									 + this.launcher.getLauncherId();
					logger.log(Level.INFO, print_log, this);
					sleep(flyTime * War.TIME_INTERVAL);	
					destroyTarget();
					if (reveal_status == true) {
						launcher.hideYourSelf(); // make launcher hide again
					}
				}
			}	
		} catch (InterruptedException e) {
			//missile is destroyed
			this.setStatus(Status.Destroyed);
			if (reveal_status == true) {
				launcher.hideYourSelf(); // make launcher hide again
			}
		} catch (SecurityException e) {
			
		} catch (Exception e) {
			logger.log(Level.INFO, e.getMessage(), this);
		}
	}
	
	@Override
	public void destroyTarget() {
		// print to log that missile successfully hit targer
		String print_log = "Missle "+ this.missileId + " hit " 
				  + this.destination + " with " 
				  + this.damage + " damage";
		logger.log(Level.INFO, print_log, this);
		this.setStatus(Status.Hit);
	}
	
}
