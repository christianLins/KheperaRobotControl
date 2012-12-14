import java.util.LinkedList;



public class DriveToLightProportionalController extends Controller {
	
	public DriveToLightProportionalController() {
		LinkedList<Action> actions = new LinkedList<>();
		actions.add(new DriveToLightProportionalAction(this));
		init(actions);
	}

	
}
