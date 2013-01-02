
/**
 * interface for the context object for all actions
 * an implementation of this interface is for example the robot-controller
 * 
 * @author Chris
 *
 */
public interface ActionContext {

	public void setAction(Action action);
	
	public void actionDone(Action action);
	
	public SensorManager getSensorManager();
	
	public MotionManager getMotionManager();
	
	public void addTemporarySubAction(Action action);
	
}
