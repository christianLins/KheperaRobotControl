
public class DriveAroundBallProportionalAction extends Action {

	public DriveAroundBallProportionalAction(ActionContext context) {
		super(context);
	}
	
	private float[][] controlMatrix = new float[][] { 
            new float[] {0f, 0f, 0f, 0f, 0.4f, 0.6f, 0f, 0f },     //left 
            new float[] { 0f, 0f, 0.6f, 0.3f, 0f, 0f, 0f, 0f   }      //right
            }; 

	private float PROP_BASE_SPEED = 5;
	
	

	@Override
	protected void doRealAction() {
		 float left = 0;
	     float right = 0;
	     
	     float[] sensors = getSensorManager().getSensorDistanceVector();
	     
	     for (int i = 0; i < sensors.length; i++) {
	            left += controlMatrix[0][i] * sensors[i];
	            right += controlMatrix[1][i] * sensors[i];
	     }
	     
	     left = left / SensorManager.getDISTANCE_MAX();
	     right = right / SensorManager.getDISTANCE_MAX();
	     
	     if(isDone()) {
	    	 actionDone();
	    	 return;
	     }
	     
	     getMotionManager().setMotorSpeeds((int)(1 + (left) * PROP_BASE_SPEED), (int)(1 + (right)* PROP_BASE_SPEED));
	
	}



	private boolean isDone() {
		return getSensorManager().isObjectInFront(350);
	}
		

}
