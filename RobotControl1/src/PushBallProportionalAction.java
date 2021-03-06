

public class PushBallProportionalAction extends Action {

	private float[][] controlMatrix = new float[][] { 
            new float[] {-0.0f, -0.0f, -0.0f, 0.2f, 0.35f, 0.45f, -0.00f, 0.50f  },     //left 
            new float[] { 0.45f, 0.35f, 0.20f, -0.0f, -0.0f, -0.0f, 0.50f, -0.00f   }      //right
            }; 

	private float PROP_BASE_SPEED = 5;
	
	public PushBallProportionalAction(ActionContext context) {
	     super(context);
	}
	
	@Override
	public void doRealAction() {
	     float left = 0;
	     float right = 0;
	     
	     float[] sensors = getSensorManager().getSensorDistanceVector();
	     
	     for (int i = 0; i < sensors.length; i++) {
	            left += controlMatrix[0][i] * sensors[i];
	            right += controlMatrix[1][i] * sensors[i];
	     }
	     
	     left = left / SensorManager.getDISTANCE_MAX();
	     right = right / SensorManager.getDISTANCE_MAX();
	     
	     getMotionManager().setMotorSpeeds((int)(2 + (left) * PROP_BASE_SPEED), (int)(2 + (right)* PROP_BASE_SPEED));
	}
	
	@Override
	public String getName() {
		return "Push Ball Proportional Action";
	}


}
