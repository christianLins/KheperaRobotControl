
public class LostBallStopCondition extends StopCondition {

	public LostBallStopCondition(ActionContext context) {
		super(context);
	}
	
	int max_occourences = 50;

	@Override
	public boolean hasToStop() {
		if(!getSensorManager().isObjectInFront()) {
			if(max_occourences-- <= 0) {
				System.out.println("Lost ball");
				return true;
			}
		}
		return false;
	}

}
