
public class PushBallAction extends Action {

	public PushBallAction(ActionContext context) {
		super(context);
	}

	@Override
	public void doRealAction() {
		getMotionManager().setBaseSpeed(3);
		
		if(getSensorManager().isWallInFront()) {
		}
		if(isBallWellPositioned()) {		
			System.out.println("Ball is well positioned");
			float[] lvtImage = getSensorManager().getLvtImage();
			for (int i = 0; i < lvtImage.length; i++) {
				System.out.println(i + "=" + lvtImage[i]);
			}
			getMotionManager().goForward();
		} else {
			Sensor shortest = getSensorManager().getShortestDistanceValue();
			if(shortest.isLeft()) {
				System.out.println("shortest is left");
				getMotionManager().turnLeft();
			} else {
				System.out.println("shortest is right");
				getMotionManager().turnRight();
			}
		}

	}

	private boolean isBallWellPositioned() {
		return getSensorManager().isObjectInFrontOld();
	}

	
	@Override
	public String getName() {
		return this.getClass().getName();
	}
}
