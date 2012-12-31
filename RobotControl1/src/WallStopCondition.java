
public class WallStopCondition extends StopCondition {
	

	public WallStopCondition(ActionContext context) {
		super(context);
	}

	@Override
	public boolean hasToStop() {
		if (getSensorManager().isWallInFront()) {
			System.out.println("pushed ball to wall");
			return true;
		}
		return false;
	}

}
