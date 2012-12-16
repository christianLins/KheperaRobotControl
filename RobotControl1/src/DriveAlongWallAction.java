
public class DriveAlongWallAction extends Action {
	
	private int threshold;
	private Direction wall = Direction.straight;
	private int thresholdToClose;

	public DriveAlongWallAction(ActionContext context) {
		super(context);
		this.threshold = 200;
		this.thresholdToClose = 800;
	}

	@Override
	public void doAction() {
		if(isToFarFromWall()) {
			if(Direction.right.equals(wall)) {
				getMotionManager().goToRight();
			} else if(Direction.left.equals(wall)){
				getMotionManager().goToLeft();
			} else {
				getMotionManager().goForward();
			}
		} else {
			if(isToCloseToWall()) {
				getMotionManager().turnRight();
			} else if(getSensorManager().isWallOnLeftSide()) {
				wall = Direction.left;
				getMotionManager().goForward();
			} else if(getSensorManager().isWallOnRightSide()) {
				wall = Direction.right;
				getMotionManager().goForward();
			} else {
				wall = Direction.straight;
			}
		}
		
	}
	
	private boolean isToCloseToWall() {
		Sensor shortest = getSensorManager().getShortestDistanceValue();
		if(shortest.getDistanceValue() > thresholdToClose) {
			System.out.println("is to close to wall");
			return true;
		}
		return false;
	}

	
	private boolean isToFarFromWall() {
		Sensor shortest = getSensorManager().getShortestDistanceValue();
		if(shortest.getDistanceValue() < threshold) {
			System.out.println("is to far from wall");
			return true;
		}
		return false;
	}
	
	
	private enum Direction { left, right, straight }
}
