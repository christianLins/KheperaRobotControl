import java.util.LinkedList;


public class DriveAlongWallBangBangController extends Controller {
	
	public DriveAlongWallBangBangController() {
		LinkedList<Action> actions = new LinkedList<>();
//		actions.add(new GoToWallAction(this));
		actions.add(new DriveAlongWallAction(this));
		init(actions);
	}

}


