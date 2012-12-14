
/**
 * stop in front of light
 * 
 * @author Chris
 *
 */
public class DriveIntoLightProportionalAction extends Action {
	
	private static final int LIGHT_MAX = 600;
	private float[][] controlMatrix = new float[][] { 
			new float[] { 0.0f, 0.3f, 0.7f , 0f, 0f, 0f },
			new float[] { 0, 0, 0, 0.7f, 0.3f, 0.0f } 
			}; 
	private float PROP_BASE_SPEED = 4;

	public DriveIntoLightProportionalAction(ActionContext context) {
		super(context);
	}

	@Override
	public void doAction() {
		float left = 0;
		float right = 0;
		
		float[] sensors = getSensorManager().getSensorLightVectorFront();

		for (int i = 0; i < sensors.length; i++) {
			left += controlMatrix[0][i] * sensors[i];
			right += controlMatrix[1][i] * sensors[i];
		}

		left = left / LIGHT_MAX;
		right = right / LIGHT_MAX;
		
		getMotionManager().setMotorSpeeds((int)(PROP_BASE_SPEED * 1 + (left) * PROP_BASE_SPEED), (int)(PROP_BASE_SPEED * 1 + (right)* PROP_BASE_SPEED));
		
	}

}
