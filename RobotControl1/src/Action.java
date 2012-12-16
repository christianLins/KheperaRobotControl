
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

	public Action(ActionContext context) {
		this.context = context;
	}
	
	public abstract void doAction();
	
	protected SensorManager getSensorManager() {
		return context.getSensorManager();
	}
	
	protected MotionManager getMotionManager() {
		return context.getMotionManager();
	}
	
	protected final void actionDone() {
		context.actionDone(this);
	}
}
