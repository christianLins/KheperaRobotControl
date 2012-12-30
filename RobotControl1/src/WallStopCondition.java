
public class WallStopCondition extends StopCondition {
	

	public WallStopCondition(ActionContext context) {
		super(context);
	}

	@Override
	public boolean hasToStop() {
		return getSensorManager().isWallInFront();
	}

}
