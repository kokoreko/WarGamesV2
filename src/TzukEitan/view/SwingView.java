package TzukEitan.view;

import java.util.LinkedList;
import java.util.List;

import TzukEitan.listeners.WarEventUIListener;


public class SwingView extends Thread{
	private List<WarEventUIListener> allListeners;
	private boolean isRunnig = true;
	
	public SwingView(){
		allListeners = new LinkedList<WarEventUIListener>();
		
	}
	
	public void run(){
		try{
			sleep(500);
		}catch(Exception e){
			System.err.println(e.getCause());
		}
	}
}
