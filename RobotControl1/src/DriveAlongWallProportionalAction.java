
public class DriveAlongWallProportionalAction extends Action {

	
	private float[][] controlMatrix = new float[][] { 
			new float[] { 0.1f, 0.1f, 0.2f, 0.2f, -0.4f, -0.4f,	0f, 0f	}, //left
			new float[] { 0.2f, 0.2f, -0.4f, -0.4f, 0.1f, 0.1f, 0f, 0f 	} //right
			}; 
	private float PROP_BASE_SPEED = 9;
	private final int MAX_DISTANCE_VALUE = 1023;
	
	public DriveAlongWallProportionalAction(ActionContext context) {
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

		left = left / MAX_DISTANCE_VALUE;
		right = right / MAX_DISTANCE_VALUE;
		
		getMotionManager().setMotorSpeeds((int)(PROP_BASE_SPEED * 0 + (left) * PROP_BASE_SPEED), (int)(PROP_BASE_SPEED * 0 + (right)* PROP_BASE_SPEED));
		

	}



}
