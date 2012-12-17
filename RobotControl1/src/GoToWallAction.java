
public class GoToWallAction extends Action {

	private int threshold;

	public GoToWallAction(ActionContext context) {
		super(context);
		this.threshold = 200;
	}

	@Override
	public void doRealAction() {
		System.out.println("go to wall");
		
		if(!foundWall()) {
			getMotionManager().goForward();
		} else {
			System.out.println("Found wall - action done");
			actionDone();
		}

	}
	
	private boolean foundWall() {
		Sensor shortest = getSensorManager().getShortestDistanceValue();
		if(shortest.getDistanceValue() > threshold) {
			return true;
		}
		return false;
	}

}
