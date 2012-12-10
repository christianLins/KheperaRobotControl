import java.util.Iterator;
import java.util.LinkedList;

import edu.wsu.KheperaSimulator.RobotController;

/**
 * update world map (recognized objects etc.)
 * check for collision
 * check if stuck (deadlock)
 * plan next action (find ball etc.)
 * 
 * @author Chris
 *
 */
public abstract class Controller extends RobotController implements ActionContext {
	
	private SensorManager sensorMgr;
	private MotionManager motionMgr;
	
	private int baseSpeed = 5;
	
	private Action currentAction;
	private LinkedList<Action> actions;
	
	
	protected void init(LinkedList<Action> actions) {
		this.actions = actions;		
		sensorMgr = new SensorManager(this);
		motionMgr = new MotionManager(this);
		setWaitTime(50);	
		motionMgr.setBaseSpeed(baseSpeed);
		if(actions != null) {
			setAction(actions.getFirst());
		} else {
			System.out.println("No actions available");
		}
		
	}



	/**
	 * routine 
	 * - checks input values
	 * - checks stop conditions
	 * - calculate outputs
	 * - and write the outputs to robot
	 */
	@Override
	public void doWork() throws Exception {
		sensorMgr.update();
		updateWorldMap();
		if(checkStopConditions()) {
			setWaitTime(2000);
			motionMgr.stop();
		} else {
			checkDeadlock();
			if(currentAction != null) currentAction.doAction();
		}
	}

	private void checkDeadlock() {
		// TODO Auto-generated method stub
		
	}

	private void updateWorldMap() {
		// TODO Auto-generated method stub
		
	}

	private boolean checkStopConditions() {
		// robot is close to light source
//		Sensor maxDistanceValue = sensorMgr.getShortestDistanceValue();
//		if(maxDistanceValue.getDistanceValue() > 800)
//			try {
//				System.out.println("Light source reached");
//				return true;
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		return false;
	}


	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub

	}

	public double getBaseSpeed() {
		return baseSpeed;
	}

	
	@Override
	public void setAction(Action action) {
		this.currentAction = action;		
	}

	@Override
	public SensorManager getSensorManager() {
		return sensorMgr;
	}

	@Override
	public MotionManager getMotionManager() {
		return motionMgr;
	}

	@Override
	public void actionDone(Action action) {
		int indexOf = actions.indexOf(action);
		if(indexOf+1  < actions.size()) {
			System.out.println("Switch to next action");
			currentAction = actions.get(indexOf+1);
		} else {
			System.out.println("No more actions available");
			currentAction = null;
		}
		
		
		
	}

	

	

}
