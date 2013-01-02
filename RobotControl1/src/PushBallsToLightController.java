import java.util.Collection;
import java.util.LinkedList;


public class PushBallsToLightController extends Controller {
	
	public PushBallsToLightController() {
		// action list
		LinkedList<Action> actions = new LinkedList<>();
		
		// find ball action
		actions.add(new FindBallAction(this));
		
		// push ball action
		Action pushBallAction = new DriveToLightAction(this);		
		Collection<StopCondition> pushBallStops = new LinkedList<>();
		pushBallStops.add(new WallStopCondition(this));
		pushBallStops.add(new LostBallStopCondition(this, 200));
		pushBallAction.setStopCondtions(pushBallStops);
		actions.add(pushBallAction);
		
		init(actions);
		setRepeate(true);
	}

	

}
