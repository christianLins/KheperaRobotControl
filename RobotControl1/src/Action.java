

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
