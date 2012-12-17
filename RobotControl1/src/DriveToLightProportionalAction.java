
public class DriveToLightProportionalAction extends Action {
	
	private static final int LIGHT_MAX = 600;
	private float[][] controlMatrix = new float[][] { 
			new float[] { 0.0f, 0.1f, 0.9f , 0f, 0f, 0f, 0f, 0f },
			new float[] { 0, 0, 0, 0.9f, 0.1f, 0.0f, 0f, 0f } 
			}; 
	private float PROP_BASE_SPEED = 5;

	public DriveToLightProportionalAction(ActionContext context) {
		super(context);
	}

	@Override
	public void doRealAction() {
		float left = 0;
		float right = 0;
		
		float[] sensors = getSensorManager().getSensorLightVector();

		for (int i = 0; i < sensors.length; i++) {
			left += controlMatrix[0][i] * sensors[i];
			right += controlMatrix[1][i] * sensors[i];
		}

		left = left / LIGHT_MAX;
		right = right / LIGHT_MAX;
		
		getMotionManager().setMotorSpeeds((int)(PROP_BASE_SPEED * 0 + (left) * PROP_BASE_SPEED), (int)(PROP_BASE_SPEED * 0 + (right)* PROP_BASE_SPEED));
		
	}

}
