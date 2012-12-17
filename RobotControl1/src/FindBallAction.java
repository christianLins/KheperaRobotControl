
public class FindBallAction extends Action {
	
	private static float BALL_COLOUR = 200f;

	public FindBallAction(ActionContext context) {
		super(context);
	}

	@Override
	public void doRealAction() {
		
		float[] lvtImage = getSensorManager().getLvtImage();
		
		if(!isStuck()) {
			if(!getSensorManager().isBallInFront()) {
				getMotionManager().goToRight();
			} else {
				for (int i = 0; i < lvtImage.length; i++) {
					if(BALL_COLOUR == lvtImage[i]) System.out.println(i + "=" + lvtImage[i]);
				}
				actionDone();
			}
		} else {
			getMotionManager().turnRight();
		}
		
		
		
		
		

	}

	private boolean isStuck() {
		return getSensorManager().isWallInFront();
	}

}
