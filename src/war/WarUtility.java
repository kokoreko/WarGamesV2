package war;

import java.util.Vector;

import launcher.*;
import missile.Missile;

public class WarUtility {

	/**
	 * get id and search for missile with that id in missileLaunchers vector
	 * @param id
	 * @return wanted Missile from war
	 */
	public static Missile getMissileById(String id, War war) {
		Vector<Launcher> missileLaunchers = war.getMissileLaunchers();
		int size_launcher = missileLaunchers.size();
		for (int i = 0; i < size_launcher; i++) {
			Launcher l = missileLaunchers.elementAt(i);
			int size_missile = l.getMissiles().size();
			for (int j=0; j < size_missile; j++){
				Missile m = l.getMissiles().elementAt(j);
				if (id.equals(m.getMissileId())) {
					return m;
				}
			}
		}
		return null;
	}

	/**
	 * get id and search for Launcher with that id in missileLaunchers vector
	 * @param id
	 * @return wanted Launcher from war
	 */
	public static Launcher getLauncherById(String id, War war) {
		Vector<Launcher> missileLaunchers = war.getMissileLaunchers();
		int size_launcher = missileLaunchers.size();
		for (int i = 0; i < size_launcher; i++) {
			Launcher l = missileLaunchers.elementAt(i);
			if (id.equals(l.getLauncherId())) {
				return l;
			}
		}
		return null;
	}

	/**
	 * The method receives id of destructe launcher
	 * @param id
	 * @param war
	 * @return destructe launcher by id
	 */
	public static Destructor getDestructorById(String id, War war, String type) {
		Vector<Destructor> destructors = null;
		int size_launcher = 0;
		if (type.equals(main.Program.LAUNCHER)) {
			destructors = war.getMissileLauncherDestructors();
		}
		else {
			destructors = war.getMissileDestructors();
		}
		size_launcher = destructors.size();
		for (int i = 0; i < size_launcher; i++) {
			Destructor d = destructors.elementAt(i);
			if (id.equals(d.getDestructorId())) {
				return d;
			}
		}
		return null;
	}
}
