
public abstract class StopCondition {
	
	private ActionContext context;
	
	public StopCondition(ActionContext context) {
		this.context = context;
	}
	
	protected SensorManager getSensorManager() {
		return context.getSensorManager();
	}
	
	public abstract boolean hasToStop();

}
