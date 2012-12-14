import java.util.LinkedList;


public class PushBallProportionalController extends Controller {
	
	public PushBallProportionalController() {
		LinkedList<Action> actions = new LinkedList<>();
		actions.add(new PushBallProportionalAction(this));
		init(actions);
	
	}

}
