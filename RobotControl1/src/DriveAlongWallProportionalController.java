import java.util.LinkedList;


public class DriveAlongWallProportionalController extends Controller {
	
	public DriveAlongWallProportionalController() {
		LinkedList<Action> actions = new LinkedList<>();
		actions.add(new DriveAlongWallProportionalAction(this));
		init(actions);
	}

}
