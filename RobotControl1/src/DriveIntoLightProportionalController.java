import java.util.LinkedList;



public class DriveIntoLightProportionalController extends Controller {
	
	public DriveIntoLightProportionalController() {
		LinkedList<Action> actions = new LinkedList<>();
		actions.add(new DriveIntoLightProportionalAction(this));
		init(actions);
	}

	
}
