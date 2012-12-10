
public class PushBallAction extends Action {

	public PushBallAction(ActionContext context) {
		super(context);
	}

	@Override
	public void doAction() {
		
		if(isBallWellPositioned()) {			
			getMotionManager().goForward();
		} else {
			Sensor shortest = getSensorManager().getShortestDistanceValue();
			if(shortest.isLeft()) {
				getMotionManager().turnLeft();
			} else {
				getMotionManager().turnRight();
			}
		}

	}

	private boolean isBallWellPositioned() {
		return getSensorManager().isBallInFront();
	}

}
