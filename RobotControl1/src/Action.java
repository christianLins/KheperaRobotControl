import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;


/**
 * implementations of this class should do a specific action
 * if the action is done call <code>actionDone()<code> on the action-context
 * the action-context is the controller of the robote
 * 
 * @author Chris
 *
 */
public abstract class Action {
	
	private ActionContext context;
	
	private Collection<StopCondition> stopConditions;
	
	private boolean isDeadLock = false;

	public Action(ActionContext context) {
		this.context = context;
	}
	
	public void setStopCondtions(Collection<StopCondition> stops) {
		this.stopConditions = stops;
	}
	
	public void setStopCondtion(StopCondition stop) {
		this.stopConditions = new LinkedList<StopCondition>();
		stopConditions.add(stop);
	}
	
	protected abstract void doRealAction();
	
	public void doAction() {
		if(hasToStop()) {
			actionDone();
		} else {
			doRealAction();
		}
	}
	
	private boolean hasToStop() {
//		System.out.println("Check stop conditions");
		if(stopConditions != null) {
			for(StopCondition stop : stopConditions) {
				if(stop.hasToStop()) return true;
			}
			return false;
		} else {
			return false;
		}
	
	}

	public SensorManager getSensorManager() {
		return context.getSensorManager();
	}
	
	public MotionManager getMotionManager() {
		return context.getMotionManager();
	}
	
	protected final void actionDone() {
		context.actionDone(this);
	}
	
	public boolean isDeadLock() {
		return isDeadLock;
	}
	
	protected void setDeadlock(boolean b) {
		isDeadLock = b;
	}

	public void deadLockResolved() {
		isDeadLock = false;
		
	}
	
}
