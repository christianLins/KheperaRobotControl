
public class LostBallStopCondition extends StopCondition {

	private int threshold;

	public LostBallStopCondition(ActionContext context, int threshold) {
		super(context);
		this.threshold = threshold;
	}
	
	int max_occourences = 70;

	@Override
	public boolean hasToStop() {
		if(!getSensorManager().isObjectInFront(threshold)) {
			if(max_occourences-- <= 0) {
				System.out.println("Lost ball");
				return true;
			}
		}
		return false;
	}

}
