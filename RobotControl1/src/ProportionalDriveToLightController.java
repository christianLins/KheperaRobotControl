import java.util.LinkedList;



public class ProportionalDriveToLightController extends Controller {
	
	public ProportionalDriveToLightController() {
		LinkedList<Action> actions = new LinkedList<>();
		actions.add(new DriveToLightProportional(this));
		init(actions);
	}

	
}
