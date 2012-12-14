import java.util.LinkedList;


public class PushBallBangBangController extends Controller {
	
	public PushBallBangBangController() {
		LinkedList<Action> actions = new LinkedList<>();
		actions.add(new PushBallAction(this));
		init(actions);
	
	}

}
