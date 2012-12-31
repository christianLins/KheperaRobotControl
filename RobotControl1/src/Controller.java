import java.util.LinkedList;

import edu.wsu.KheperaSimulator.RobotController;

/**
 * cyclic schedule:
 * - update world map (recognized objects etc.)
 * - check for collision
 * - check if stuck (deadlock)
 * - plan next action (find ball etc.)
 * 
 * @author Chris
 *
 */
public abstract class Controller extends RobotController implements ActionContext {
	
//	public Controller(String serialPort, long waitTime) {
//		super(serialPort, waitTime);
//	}
	
//	public Controller() {
//		this("COM3", 50);
//	}

	private SensorManager sensorMgr;
	private MotionManager motionMgr;
	
	private int baseSpeed = 9;
	
	private Action currentAction;
	private LinkedList<Action> actions;
	
	private boolean repeate = false;
	
	// deadLock
	private int noMotionCounter = 0;
	private int maxNoMotion = 50;
	private DeadLockAction deadLockAction;
	private int noInputChangeCounter = 0;
	private int maxNoInputChange = 50;
	
	
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
	public void doWork() {
		sensorMgr.update();
		updateWorldMap();
		if(checkStopConditions()) {
//			setWaitTime(2000);
//			motionMgr.stop();
			actionDone(currentAction);
		} else {
			if(isDeadLock()) {
				if(deadLockAction == null) {
					deadLockAction = new DeadLockAction(this);
				}
				deadLockAction.doAction();
			} else {	
				if(currentAction != null) {
					currentAction.deadLockResolved();
					currentAction.doAction();
				}
			}
			
		}
	}

	protected boolean isDeadLock() {
		if(currentAction.isDeadLock()) {
			return true;
		}
		if(getMotionManager().noMotionChange()) {
			if(noMotionCounter++ > maxNoMotion) {
				System.out.println("no motion");
				return true;
			}
		} else if(!getSensorManager().isAnyChange(0.4f)) {
			// no input value change
			if(noInputChangeCounter++ > maxNoInputChange) {
				System.out.println("no input value changed");
				return true;
			}
		} else {
			noMotionCounter = 0; // reset
			deadLockAction = null;
		}
		return false;
		
		// CURRENTLY NOT NEEDED
		// overwrite this in the implementation or change code to pass deadlock-conditions,
		// how we do it with the actions --> deadLockList
		// or give to the actions deadLockConditions, because could be deadlock specific
		
	}
	
	

	protected void updateWorldMap() {
		// CURRENTLY NOT NEEDED
	}

	private boolean checkStopConditions() {
		// CURRENTLY NOT NEEDED
		return false;
	}


	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
	}

	public double getBaseSpeed() {
		return baseSpeed;
	}

	
	/**
	 * set next (current) action
	 */
	@Override
	public void setAction(Action action) {
		this.currentAction = action;		
	}

	/**
	 * returns the sensor manager
	 */
	@Override
	public SensorManager getSensorManager() {
		return sensorMgr;
	}

	/**
	 * returns the motion manager
	 */
	@Override
	public MotionManager getMotionManager() {
		return motionMgr;
	}

	/**
	 * calls next action
	 * for future:
	 * - add actions by a specific actions etc.
	 */
	@Override
	public void actionDone(Action action) {
		if(action instanceof DeadLockAction) {
			currentAction.deadLockResolved();
			return;
		}
		
		int indexOf = actions.indexOf(action);
		if(indexOf+1  < actions.size()) {
			System.out.println("Switch to next action");
			currentAction = actions.get(indexOf+1);
		} else {
			if(isRepeate()) {
				indexOf = 0;
				currentAction = actions.get(indexOf);
			} else {
				System.out.println("No more actions available");
				currentAction = null;
			}
		}
	}
	
	public void setRepeate(boolean b) {
		this.repeate = b;
	}
	
	public boolean isRepeate() {
		return repeate;
	}

	

	

}
