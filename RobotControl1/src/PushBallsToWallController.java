import java.util.LinkedList;


public class PushBallsToWallController extends Controller {
	
	public PushBallsToWallController() {
		LinkedList<Action> actions = new LinkedList<>();
		actions.add(new FindBallAction(this));
		PushBallAction pushBallAction = new PushBallAction(this);		
		pushBallAction.setStopCondtion(new WallStopCondition(this));
		actions.add(pushBallAction);
		init(actions);
		setRepeate(true);
	}

	

}
