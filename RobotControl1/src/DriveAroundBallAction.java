
public class DriveAroundBallAction extends Action {

	public DriveAroundBallAction(ActionContext context) {
		super(context);
		getMotionManager().setBaseSpeed(4);
	}
	
	private final static int METHOD_COUNT = 5;
	
	private int method = 0;
	private int loopCounter = 0;

	@Override
	protected void doRealAction() {
		
		if(method == 0) {
			getMotionManager().driveBack();
			getMotionManager().stop();
			nextMethod();
			return;
		} else if (method == 1) {
			getMotionManager().turnLeft();
			if(loopCounter++ >= 8) {
				nextMethod();
				loopCounter = 0;
				return;
			} 
					
		} else if (method == 2) {
			getMotionManager().goForward();
			if(loopCounter++ >= 20) {
				nextMethod();
				loopCounter = 0;
				return;
			} 		
		} else if (method == 3) {
			getMotionManager().goToRight();
			if(loopCounter++ >= 75) {
				nextMethod();
				loopCounter = 0;
				return;
			} 		
		}  else if (method == 4) {
			getMotionManager().turnRight();
			if(loopCounter++ >= 12) {
				nextMethod();
				loopCounter = 0;
				return;
			} 		
		}

	}
	
	private void nextMethod() {
		method = (method + 1) % METHOD_COUNT;	
		if(0 == method) {
			actionDone();
		}
	}

}
