import java.io.IOException;
import java.util.StringTokenizer;

/**
 * A class adapter for the following functions of the WSU-Khepera
 * Simulator Class "RobotController" adapting to a serial port interface 
 * of the real Khepera robot:
 * - run
 * - getDistanceValue
 * - getLightValue
 * - getLeftWheelPosition
 * - getRightWheelPosition
 * 
 * You must derive your controller class (for example "MyController")
 * implementing the abstract method "doWork". Write in that class a main 
 * method similar to the following template:
 * 
 * public static void main ( String[] args )
 * {
 * 	MyController mc = new MyController("COM1",50); //port name and the cycle time
 *     try
 *     {
 *         mc.robStart();
 *     }
 *     catch ( Exception e )
 *    {
 *        e.printStackTrace();
 *    }
 *  }
 * @authors FHV students and teacher
 *
 */
public abstract class RobotController implements Runnable {

	/**
	 * Serial communication channel
	 */
	private KheperaSerialComm m_SerialComm;

	
	/**
	 * Update cycle wait time in milliseconds
	 */
	private long m_WaitTime = 50;

	private boolean finished = false;
	private boolean running = false;
	private Thread thread = null;
	private int speedLeft = 0;
	private int speedRight = 0;

	/**
	 * Controller name
	 */
	private String m_Name = "RealKheperaController";

	/**
	 * Number of distance sensors
	 * (Sensor ID = 0 ... 7)
	 */
	protected static final int DIST_SENSORS = 8;

	/**
	 * Number of light sensors
	 * (Sensor ID = 0 ... 7)
	 */
	protected static final int LIGHT_SENSORS = 8;

	/**
	 * maximum speed for robot security: motor might burn out
	 */
	private static final int MAX_SPEED = 15;
	/**
	 * Number of wheel sensors
	 */
	private static final int WHEEL_SENSORS = 2;

	/**
	 * Left wheel array position
	 */
	private static final int LEFT_WHEEL = 0;

	/**
	 * Right wheel array position
	 */
	private static final int RIGHT_WHEEL = 1;

	/**
	 * Current wheel sensor values
	 */
	private long[] m_Wheels = new long[WHEEL_SENSORS];

	// TODO arm/gripper variables

	/**
	 * Current distance sensor values
	 */
	private int[] m_Distances = new int[DIST_SENSORS];

	/**
	 * Current light sensors values
	 */
	private int[] m_Lights = new int[LIGHT_SENSORS];

	/**
	 * create a robot controller for a serial port and a cycle time
	 * - create a serial communication connecting to serialPort
	 * - create a new thread and run this Runnable within the new thead
	 * @param serialPort COM10 usually
	 * @param waitTime cycle time of controller activations, in milliseconds
	 */
	public RobotController(String serialPort, long waitTime) {
		// establish serial comm connection
		m_SerialComm = new KheperaSerialComm();
		try {
			m_SerialComm.connect(serialPort, this);
			System.out.println("connected");
		} catch (Exception e) {
			// no connection available
			System.err.println("Serial Comm Error: " + e.getMessage());
			System.exit(0);
		}

		if (waitTime > 0) {
			m_WaitTime = waitTime;
		} else {
			System.err.println("contract violation for RobotController: wait time must be positive");
			System.exit(0);
		}

		
	}

	/**
	 * To be implemented by specific controller
	 */
	public abstract void doWork();

	public abstract void close() throws Exception;

	/**
	 * after interrupting the controller, it now is required to be resumed 
	 * (by keystroke "U"). Restart new thread. 
	 */
	public synchronized void resumeController() {
		finished = false;
		speedLeft = 0;
		speedRight = 0;
		notify();
	}

	/**
	 * controller is required to be interrupted 
	 * (by keystroke "S"). Interrupt current thread by finishing cycle in the "run" method.  
	 */
	public synchronized void stopController() {
		finished = true;
		speedLeft = 0;
		speedRight = 0;
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * main control cycle: read in periodically the current sensor values, and
	 * then execute control by calling the abstract method doWork.
	 */

	public boolean isStopped() {
		return finished;
	}

	
	public void run() {

		while (true) {
			try {

				while (!finished) {
					getSensorValues();
					doWork();
					Thread.sleep(m_WaitTime);
					
				}

				synchronized (this) {
					notify();
					wait();
				}
				
				
			}

			catch (InterruptedException e) { //interrupted while sleeping: that might happen, no problem, go on			
			}

			catch (Exception e) {
				System.out
						.println("\nThere was a problem with the controller.");
				System.out.println("Here is the controller thread's stack at");
				System.out.println("the time of the error:\n\n");

				System.out.println("=== Stack Trace ======================\n");
				e.printStackTrace();
				System.out.println("\n=== End of the stack trace ===========");

				System.out.println("\nSession ended\n");
				
			}
			try {
				close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	public void sleep(long timeout) {
		try {
			Thread.sleep(timeout);
		} catch (Exception e) {
		}
	}

	/**
	 * Get controller name
	 * @return
	 */
	public String getName() {
		return m_Name;
	}

	public int getMaxDistSensorId() {
		return DIST_SENSORS;
	}

	public int getMaxLightSensorId() {
		return LIGHT_SENSORS;
	}

	public int getDistanceValue(int sensorID) {
		if (sensorID >= DIST_SENSORS || sensorID < 0) {
			System.err
					.println("sensor id out of range in RobotController.getDistanceValue");
			System.exit(0);
		}

		return m_Distances[sensorID];
	}

	public int getLightValue(int sensorID) {
		if (sensorID >= LIGHT_SENSORS || sensorID < 0) {
			System.err
					.println("sensor id out of range in RobotController.getLightValue");
			System.exit(0);
		}

		return m_Lights[sensorID];
	}

	public long getLeftWheelPosition() {
		return m_Wheels[LEFT_WHEEL];
	}

	public long getRightWheelPosition() {
		return m_Wheels[RIGHT_WHEEL];
	}

	protected void robStart() {
		thread = new Thread(this, m_Name);
		thread.start();
	}

	/**
	 * Set update wait time
	 * @param waitTime in milliseconds
	 */
	protected void setWaitTime(long waitTime) {
		m_WaitTime = waitTime;
	}

	protected void setFinished(boolean finished) {
		this.finished = finished;
	}

	protected boolean isRunning() {
		return running;
	}

	/**
	 * Communicate with khepera robot via serial connection
	 * 
	 * @param message robot command
	 * @return robot message as result of command
	 */
	private String sendKheperaCommand(String message) {
		try {
			return m_SerialComm.sendMessage(message);
		} catch (IOException e) {
			System.out
					.println("Err: Send Error sendKheperaCommand (RobotController)");
			return "";
		}
	}

	/**
	 * Get current sensor values via serial command interface from
	 * khepera robot:
	 * - distances by IR
	 * - light intensities
	 * - wheel positions
	 */
	public void getSensorValues() {

		// get distance sensor values via robot command
		String answer = sendKheperaCommand("N\n");
		StringTokenizer st = new StringTokenizer(answer, ",");
		try {

			if (answer != null || answer != "") {
				st.nextToken(); //skip first token

				for (int i = 0; i < 8; i++) {
					m_Distances[i] = Integer.parseInt(st.nextToken());
				}
			} else
				System.err.println("communication error with Khepera: got no answer string");

			// get light sensor values via robot command
			answer = sendKheperaCommand("O\n");
			st = new StringTokenizer(answer, ",");
			if (answer != null || answer != "") {
				st.nextToken(); //skip first token

				for (int i = 0; i < 8; i++) {
					m_Lights[i] = Integer.parseInt(st.nextToken());
				}
			} else
				System.err
						.println("communication error with Khepera: got no answer string");

			// get motor sensor values via robot command
			answer = sendKheperaCommand("H\n"); // 
			st = new StringTokenizer(answer, ",");
			if (answer != null || answer != "") {
				st.nextToken(); //skip first token

				for (int i = 0; i < 2; i++) {
					m_Wheels[i] = Integer.parseInt(st.nextToken());
				}
			} else
				System.err
						.println("communication error with Khepera: got no answer string");
		} catch (Exception e) {
			System.err.println("communication error with Khepera! answer: "
					+ answer);
		}

		// get gripper / arm sensor values via robot command: not supported
		// answer = sendKheperaCommand("\n"); // TODO get command

		// parse answer via robot command
		// TODO
	}

	/**
	 * set current motor values via serial command interface from
	 * khepera robot.
	 * @param left speed of left motor (within -MAXSPEED..+MAXSPEED)
	 * @param right speed of right motor (within -MAXSPEED..+MAXSPEED)
	 */
	public void setMotorSpeeds(int left, int right) {
		if (left == speedLeft && right == speedRight)
			return;

		if (Math.max(Math.abs(left), Math.abs(right)) > MAX_SPEED)
			System.err.println("max motor speed exceeded");

		sendKheperaCommand("D," + left + "," + right + "\n");
		speedLeft = left;
		speedRight = right;
	}
}