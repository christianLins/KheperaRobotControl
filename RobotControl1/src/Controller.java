import java.util.LinkedList;
import java.util.List;

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
	private Action temporaryAction;
	private List<Action> temporaryActions = new LinkedList<>();
	
	private boolean repeate = false;
	
	// deadLock
	private int noMotionCounter = 0;
	private int maxNoMotionChange = 200;
	private DeadLockAction deadLockAction = new DeadLockAction(this);
	private int noInputChangeCounter = 0;
	private int maxNoInputChange = 150;
	
	
	
	
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
			if(temporaryAction instanceof DeadLockAction) {
				temporaryAction.doAction();
				return;
			}
			if(isDeadLock()) {
				if(deadLockAction == null) {
					deadLockAction = new DeadLockAction(this);
				}
				addTemporarySubAction(deadLockAction);
			} else {
				
			}
			if(temporaryAction != null) {
				if(!(temporaryAction instanceof DeadLockAction)) {
					temporaryAction.deadLockResolved();
				}					
				System.out.println(temporaryAction.getName());
				temporaryAction.doAction();
				return;
			} else if(currentAction != null) {
				currentAction.deadLockResolved();
				System.out.println(currentAction.getName());
				currentAction.doAction();
			}
			
		}
	}




	protected boolean isDeadLock() {
		if(temporaryAction != null) {
			if(temporaryAction.isDeadLock()) {
				return true;
			}
		}
		if(currentAction.isDeadLock()) {
			return true;
		}
		if(getMotionManager().noMotionChange()) {
			if(noMotionCounter++ > maxNoMotionChange) {
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
		} 
		if(action == temporaryAction) {			
			temporaryActions.remove(action);
			if(temporaryActions.size() > 0) {
				// more actions - take last (lifo)
				temporaryAction = temporaryActions.get(temporaryActions.size() - 1);
				return;
			} else {
				System.out.println("No more temporary action");
				temporaryAction = null;
				return;
			}
			
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
	
	public void addTemporarySubAction(Action action) {
		if(temporaryActions != null && action != null) {
			// add to buffer
			temporaryActions.add(action);
			temporaryAction = action;
			System.out.println("latest temporary action is " + temporaryAction.getName());
		}
	}
	
	public void setRepeate(boolean b) {
		this.repeate = b;
	}
	
	public boolean isRepeate() {
		return repeate;
	}

	

	

}
