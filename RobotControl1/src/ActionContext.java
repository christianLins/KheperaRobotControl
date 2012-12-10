

public interface ActionContext {

	public void setAction(Action action);
	
	public void actionDone(Action action);
	
	public SensorManager getSensorManager();
	
	public MotionManager getMotionManager();
	
}
