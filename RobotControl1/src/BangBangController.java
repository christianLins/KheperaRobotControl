import java.util.LinkedList;


public class BangBangController extends Controller {
	
	
	
	
	

	public BangBangController() {
		LinkedList<Action> actions = new LinkedList<>();
//		actions.add(new GoToWallAction(this));
//		actions.add(new DriveAlongWallAction(this));
		actions.add(new DriveTowardsLightAction(this));
		
		init(actions);
	}

}
