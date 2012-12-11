import java.util.LinkedList;



public class ProportionalDriveIntoLightController extends Controller {
	
	public ProportionalDriveIntoLightController() {
		LinkedList<Action> actions = new LinkedList<>();
		actions.add(new DriveIntoLightProportional(this));
		init(actions);
	}

	
}
