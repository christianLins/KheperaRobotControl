import java.util.LinkedList;



public class ProportionalController extends Controller {
	
	public ProportionalController() {
		LinkedList<Action> actions = new LinkedList<>();
		actions.add(new GoToLightProportional(this));
		init(actions);
	}

	
}
