import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Class for connecting a robot controller to the Khepera robot via serial port. 
 * Connecting is accomplished by the method "connect" using the using the RXTXComm.jar library.
 * Communication with the robot is handled exclusively by the method
 * "sendMessage" sending command strings to the robot which in turn
 * result in return messages in form of a string returned to the calling robot controller.
 * Furthermore, an object of the inner class "SerialWriter" running in its own thread
 * listening for user input from key-strokes:
 * "s"   -  results in stopping the robot (motor speed 0)
 * "u"   -  results in  resuming the robot controller of a robot stopped previously
 * "q"   -  results in  displaying the current sensor values at the console 
 * 
 * 
 * @authors FHV students and teacher
 *
 */
public class KheperaSerialComm {

	protected SerialPort serialPort;
	protected InputStream in;
	protected OutputStream out;
	protected BufferedReader reader;
	protected BufferedWriter writer;
	protected RobotController controller;
	protected Thread thread;
	protected SerialWriter sw;

	public InputStream getInputStream() {
		return in;
	}

	public OutputStream getOutputStream() {
		return out;
	}

	/**
	 * connects a robot controller to a serial port, using settings required for the Khepera:
	 * baud rate 9600,8 databits,2 stopbits, parity none
	 * furthermore, it creates an object of the inner class "SerialWriter" running in its own thread
	 * listening for user input from key-strokes:
	 * "s"   -  results in stopping the robot (motor speed 0)
	 * "u"   -  results in  resuming the robot controller of a robot stopped previously
	 * "q"   -  results in  displaying the current sensor values at the console 
	 * @param portName COM1 or COM2 usually
	 * @param controller back reference to the controller using this serial connection
	 */
	public void connect(String portName, RobotController controller)
			throws Exception {
		this.controller = controller;

		CommPortIdentifier portIdentifier = CommPortIdentifier
				.getPortIdentifier(portName);
		if (portIdentifier.isCurrentlyOwned()) {
			System.out.println("Error: Port is currently in use");
		} else {
			CommPort commPort = portIdentifier.open(this.getClass().getName(),
					2000);

			if (commPort instanceof SerialPort) {
				serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);

				in = serialPort.getInputStream();
				out = serialPort.getOutputStream();
				reader = new BufferedReader(new InputStreamReader(in));
				writer = new BufferedWriter(new OutputStreamWriter(out));
				sw = new SerialWriter(out);
				thread = new Thread(sw); // object of inner class for listening to user keystroke
				thread.start();

			} else {
				System.out
						.println("Error: Only serial ports are handled by this example.");
			}
		}
	}

	/**
	 * sends a message in string format to the robot, and receives the answer
	 * in string format
	 * @param message  a command to the Khepera in a correct format (see Khepera manual)
	 */
	public String sendMessage(String message) throws IOException {
		// write to output stream
		for (char c : message.toCharArray())
			out.write(c);

		// read result

		String returnMessage = "";

		try {

			returnMessage = reader.readLine();

			if (returnMessage.equalsIgnoreCase("command not found"))
				System.err.println("error in sendMessage to robot: sent: "
						+ message);

		} catch (IOException e) {
			System.out
					.println("Err: Read Error in sendMessage (KheperaSerialComm)");
		}

		return returnMessage;
	}

	/**
	 * Inner class for an object running in its own thread
	 * listening for user input from key-strokes:
	 * "s"   -  results in stopping the robot (motor speed 0)
	 * "u"   -  results in  resuming the robot controller of a robot stopped previously
	 * "q"   -  results in  displaying the current sensor values at the console 
	 * in case of such a keystroke, communicates directly with the robot controller
	 * 
	 * @authors FHV students and teacher
	 *
	 */
	public class SerialWriter implements Runnable {
		OutputStream out;

		public SerialWriter(OutputStream out) {
			this.out = out;
		}

		public void run() {
			try {

				int c = 0;
				int i = 0;
				while ((c = System.in.read()) > -1) {

					if (i == 0 && (c == 'S' || c == 's')) // interrupt
					// controller
					{
						
						controller.stopController();
						sendMessage("D,0,0\n");
						System.out
								.println("RobotController interrupted in SerialComm");

					}
					if (i == 0 && (c == 'U' || c == 'u')) // resume controller
					{
						System.out
								.println("RobotController restarted in SerialComm");
						controller.resumeController();
						System.out
								.println("RobotController restarted in SerialComm");
					}
					if (i == 0 && (c == 'Z' || c == 'z')) // exit program
					{
						System.out.println("RobotController finished");
						controller.stopController();
						sendMessage("D,0,0\n");
						System.exit(0);
					}

					if (i == 0 && (c == 'Q' || c == 'q')) //write sensor values to console
					{
						int maxDist = controller.getMaxDistSensorId();
						int maxLight = controller.getMaxLightSensorId();

						if (controller.isStopped())
							controller.getSensorValues();

						System.out.print("Distance values: ");
						for (int id = 0; id < maxDist; id++)
							System.out.print(controller.getDistanceValue(id)
									+ ",");
						System.out.println();
						System.out.print("Light values: ");
						for (int id = 0; id < maxLight; id++)
							System.out
									.print(controller.getLightValue(id) + ",");
						System.out.println();

						System.out
						.println(controller.getLeftWheelPosition());
						System.out
						.println(controller.getRightWheelPosition());
					}
					if (c == '\n')
						i = 0;
					else
						i++;
				}
				System.out.println("SerialWriter finished");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Object getThread() {
		// TODO Auto-generated method stub

		return sw;
	}

}