
public class DeadLockAction extends Action {

	public DeadLockAction(ActionContext context) {
		super(context);
	}
	
	private final static int METHOD_COUNT = 4;
	
	private int deadlockMode = 0;
	private int deadLockActionCounter = 0;

	@Override
	protected void doRealAction() {
		doDeadLockAction();
	}
	
	private void doDeadLockAction() {
		if(deadLockActionCounter >= 15) {
			// method not appropriate
			forceNextMethod();
		}
		if(deadlockMode == 0) {
	   		getMotionManager().driveBack();
   			nextDeadLockMode();
	   	 } else if(deadlockMode == 1) {
	   		 getMotionManager().turnLeft();
	   		 nextDeadLockMode();
	   		 if (getSensorManager().isObjectInBack()) {
	   			 nextDeadLockMode();
	   		 }
	   	 } else if(deadlockMode == 2) {
	   		 getMotionManager().goForward();
	   		
	   	 } else if(deadlockMode == 3) {
	   		 getMotionManager().turnRight();
	   		 nextDeadLockMode();
//	   		 if (getSensorManager().isObjectInBack()) {
//	   			 nextDeadLockMode();
//	   		 }
	   	 }
		deadLockActionCounter++;
		System.out.println("Deadlockmode = " + deadlockMode);
	}

	
	private void nextDeadLockMode() {
		deadlockMode = (deadlockMode + 1) % METHOD_COUNT;	
		if(deadlockMode == 0 || deadlockMode == 2 || deadlockMode == 3) {
			actionDone();
		}
	}
	
	public void forceNextMethod() {
		deadLockActionCounter = 0;
		deadlockMode = (deadlockMode + 1) % METHOD_COUNT;	
	}
	
}
