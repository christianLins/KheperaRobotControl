
public class DriveToLightAction extends Action {

	private int threshold;
	
	public DriveToLightAction(ActionContext context) {
		super(context);
		this.threshold = 92;
	}
	
	public DriveToLightAction(ActionContext context, int threshold) {
		super(context);
		this.threshold = threshold;
	}

	@Override
	public void doAction() {
		Sensor brightestLightValue = getSensorManager().getBrightestLightValue();
		
		if(!foundLight()){
			//back
			if(!brightestLightValue.isFront()){
				getMotionManager().turnLeft();
			}
			
			else if(brightestLightValue.isLeft()){
				getMotionManager().goToLeft();
			}
			else if(brightestLightValue.isRight()){
				getMotionManager().goToRight();
			}
			else{
				getMotionManager().goForward();
			}	
		}
		else{
			System.out.println("Found light - action done");
			getMotionManager().stop();
			actionDone();
		}
	}
	
	private boolean foundLight() {
		Sensor brightest = getSensorManager().getBrightestLightValue();
		if(brightest.getLightValue() < threshold) {
			return true;
		}
		return false;
	}

}
