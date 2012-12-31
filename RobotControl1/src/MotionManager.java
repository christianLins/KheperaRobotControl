import edu.wsu.KheperaSimulator.RobotController;

/**
 * this class provides (currently only necessary) actors of the robot
 * and makes it easier to access or set for example the motor speeds (turn,...)
 * 
 * @author Chris
 *
 */
public class MotionManager {
	
	private Controller ctrl;
	private int baseSpeed = 5;
	private static final int MAX_SPEED = 9; // 9 = robot max speed!!!
	
	float oldLeft = 0;
	float oldRight = 0;
	
	float currentLeft = 0;
    float currentRight = 0;
	
	public MotionManager(Controller controller) {
		this.ctrl = controller;
	}
	
	public void setBaseSpeed(int baseSpeed) {
		this.baseSpeed = baseSpeed;
	}
	
	public synchronized void setMotorSpeeds(int left, int right) {
		
		// set old speeds
		oldLeft = currentLeft;
		oldRight = currentRight;
		
		// check max speeds
		if(left >= MAX_SPEED) {
			System.out.println("left speed to high (" + left + ")");
			left = MAX_SPEED;
		}
		if(right >= MAX_SPEED) {
			System.out.println("right speed to high (" + right + ")");
			right = MAX_SPEED;
		}
		
		// write speeds to robot
		ctrl.setMotorSpeeds(left, right);
		
		// update speeds
		currentLeft = left;
		currentRight = right;
		
		System.out.println("Set motor speed to " + left + " | " + right);
	}
	
	public void goForward() {
		System.out.println("Go forward");
		setMotorSpeeds(baseSpeed, baseSpeed);
	}
	
	public void turnLeft() {
		System.out.println("Turn left");
		setMotorSpeeds(-baseSpeed, baseSpeed);
	}
	
	public void turnRight() {
		System.out.println("Turn right");
		setMotorSpeeds(baseSpeed, -baseSpeed);
	}
	
	public void goToLeft() {
		System.out.println("Go left");
		setMotorSpeeds(0, baseSpeed);
	}
	
	public void goToRight() {
		System.out.println("Go right");
		setMotorSpeeds(baseSpeed, 0);
	}

	public void stop() {
		setMotorSpeeds(0, 0);
		
	}

	public void scaleMotorSpeedByBaseSpeed(float left, float right) {
		int l = (int)((double)baseSpeed*left);
		int r = (int)((double)baseSpeed*right);
		System.out.println("Go in direction " + l + " | " + r + " (input = " + left + " | " + right + ")");
		setMotorSpeeds(l, r);		
	}

	public void driveBack() {
		setMotorSpeeds(-baseSpeed, -baseSpeed);		
	}

	
	public boolean noMotionChange() {
		return oldLeft == currentLeft && oldRight == currentRight;
	}
		

}
