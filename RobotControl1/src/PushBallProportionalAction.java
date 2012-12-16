

public class PushBallProportionalAction extends Action {

	private float[][] controlMatrix = new float[][] { 
            new float[] {-0.5f, -0.5f, -0.0f, 0.3f, 0.30f, 0.40f, -0.50f, 0.50f  },     //left 
            new float[] { 0.40f, 0.30f, 0.30f, -0.0f, -0.5f, -0.5f, 0.50f, -0.50f   }      //right
            }; 

	private static final int MAX_DISTANCE = 1023;
	private float PROP_BASE_SPEED = 5;
	
	public PushBallProportionalAction(ActionContext context) {
	     super(context);
	}
	
	@Override
	public void doAction() {
	     float left = 0;
	     float right = 0;
	     
	     float[] sensors = getSensorManager().getSensorDistanceVector();
	     
	     for (int i = 0; i < sensors.length; i++) {
	            left += controlMatrix[0][i] * sensors[i];
	            right += controlMatrix[1][i] * sensors[i];
	     }
	     
	     left = left / MAX_DISTANCE;
	     right = right / MAX_DISTANCE;
	     
	     getMotionManager().setMotorSpeeds((int)(PROP_BASE_SPEED * 0 + (left) * PROP_BASE_SPEED), (int)(PROP_BASE_SPEED * 0 + (right)* PROP_BASE_SPEED));
	
	
	}


}
