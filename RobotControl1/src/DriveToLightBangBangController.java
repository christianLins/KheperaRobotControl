import java.util.LinkedList;


public class DriveToLightBangBangController extends Controller {
	
	
	
	
	

	public DriveToLightBangBangController() {
		LinkedList<Action> actions = new LinkedList<>();
		actions.add(new DriveToLightAction(this));
		init(actions);
	}

}


