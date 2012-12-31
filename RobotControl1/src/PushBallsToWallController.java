import java.util.Collection;
import java.util.LinkedList;


public class PushBallsToWallController extends Controller {
	
	public PushBallsToWallController() {
		// action list
		LinkedList<Action> actions = new LinkedList<>();
		
		// find ball action
		actions.add(new FindBallAction(this));
		
		// push ball action
		PushBallProportionalAction pushBallAction = new PushBallProportionalAction(this);		
		Collection<StopCondition> pushBallStops = new LinkedList<>();
		pushBallStops.add(new WallStopCondition(this));
		pushBallStops.add(new LostBallStopCondition(this));
		pushBallAction.setStopCondtions(pushBallStops);
		actions.add(pushBallAction);
		
		init(actions);
		setRepeate(true);
	}

	

}
