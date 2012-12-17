
public class WallStopCondition extends StopCondition {
	

	public WallStopCondition(ActionContext context) {
		super(context);
	}

	@Override
	public boolean hasToStop() {
		System.out.println("CHECK WALL STOP CONDITION");
		if(getSensorManager().isWallInFront()) {
//			if(THRESHOLD < getSensorManager().getFrontLeft().getDistanceValue() && THRESHOLD < getSensorManager().getFrontRight().getDistanceValue()) {
				System.out.println("WALL STOP CONDTITION = TRUE");
				return true;
//			}
			
		}
		return false;
	}

}
