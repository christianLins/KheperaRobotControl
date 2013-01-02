import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.wsu.KheperaSimulator.RobotController;

/**
 * handles inputs
 * additional functions
 * - interpolation of sensor values
 * - pattern recognition (ball, wall,...)
 * @author Chris
 *
 */
public class SensorManager {

	

	private static final int LVT_MAX = 255; // do not know exact value????

	/**
	 * controller to access sensor values etc.
	 */
	private Controller ctrl;
		
	private Map<String,Sensor> sensors;
	private Map<String,Sensor> sensorsOldValues;
	private List<Sensor> sensorList;
	
	private float[] lvtImageOld;

	private float[] lvtImage;
	
	private static int DISTANCE_MAX = 1023;
	private static int LIGHT_MAX = 510;
	

	public SensorManager(Controller controller) {
		this.ctrl = controller;
		init();
	}

	/**
	 * init roboter sensors
	 */
	private void init() {
		if(ctrl == null) return;
		
		sensors = new HashMap<String, Sensor>();
		sensorList = new LinkedList<Sensor>();
		sensorsOldValues = new HashMap<String,Sensor>();
		
		
		Sensor left = new Sensor(0, "left");
		left.setSideFactor(-0.5);
		addSensor(left);
		
		
		Sensor leftFront = new Sensor(1, "leftFront");
		leftFront.setSideFactor(-0.3);
		addSensor(leftFront);
		
		Sensor frontLeft = new Sensor(2, "frontLeft");
		frontLeft.setSideFactor(-0.1);
		addSensor(frontLeft);
		
		Sensor frontRight = new Sensor(3, "frontRight");
		frontRight.setSideFactor(0.1);
		addSensor(frontRight);
		
		Sensor rightFront = new Sensor(4, "rightFront");
		rightFront.setSideFactor(0.3);
		addSensor(rightFront);
		
		Sensor right = new Sensor(5, "right");
		left.setSideFactor(0.5);
		addSensor(right);
		
		Sensor rearRight = new Sensor(6, "rearRight");
		rearRight.setSideFactor(-0.2);
		rearRight.setFront(false);
		addSensor(rearRight);
		
		Sensor rearLeft = new Sensor(7, "rearLeft");
		rearLeft.setSideFactor(0.2);
		rearLeft.setFront(false);
		addSensor(rearLeft);
	}
	
	private void addSensor(Sensor sensor) {
		sensors.put(sensor.getName(), sensor);
		sensorList.add(sensor);
	}

	public float[] getSensorDistanceVector() {
		float[] matrix = new float[sensorList.size()];
		int i = 0;
		for(Sensor s : sensorList) {
			matrix[i++] = s.getDistanceValue();
		}		
		return matrix;
	}
	
	public float[] getSensorLightVector() {
		float[] matrix = new float[sensorList.size()];
		int i = 0;
		for(Sensor s : sensorList) {
//			System.out.println("light matrix " + i + " = " + s.getName());
			matrix[i++] = s.getLightValue();
		}		
		return matrix;
	}
	
	public float[] getSensorLightVectorFront() {
		float[] matrix = new float[sensorList.size()-2];
		int i = 0;
		for(Sensor s : sensorList) {
			if(i < matrix.length) {
				matrix[i++] = s.getLightValue();
			}
		}		
		return matrix;
	}
	
	public Sensor getSensor(String location) {
		return sensors.get(location);
	}
	
	
	
	public Sensor getLeft() {
		return sensors.get("left");
	}
	
	public Sensor getLeftFront() {
		return sensors.get("leftFront");
	}
	
	public Sensor getFrontLeft() {
		return sensors.get("frontLeft");
	}
	
	public Sensor getFrontRight() {
		return sensors.get("frontRight");
	}

	public Sensor getRightFront() {
		return sensors.get("rightFront");
	}

	public Sensor getRight() {
		return sensors.get("right");
	}

	
	public Sensor getRearRight() {
		return sensors.get("rearRight");
	}

	
	public Sensor getRearLeft() {
		return sensors.get("rearLeft");
	}
	
	/**
	 * the highest value is the darkest value in reality!
	 * @return highest sensor value
	 */
	public Sensor getDarkestLightValue() {
		Sensor max = null;
		for(Sensor s : sensors.values()) {
			if(max == null) max = s;
			if(s.getLightValue() > max.getLightValue()) max = s;
		}
		return max;
	}
	
	/**
	 * update all sensor values
	 */
	public synchronized void update() {
		lvtImageOld  = lvtImage;
		lvtImage = ctrl.readLvtImage();
		
		for(Sensor s : sensors.values()) {
			sensorsOldValues.put(s.getName(), s.clone());
			int lightValue = 0;
			try {
				lightValue = ctrl.getLightValue(s.getId());
				s.setLightValue(lightValue);
				int distanceValue = ctrl.getDistanceValue(s.getId());
				s.setValueDistance(distanceValue);
			} catch(Exception e) {
				System.out.println("Problems during sensor updating " + s.getId());
			}
		}
	}

	/**
	 * the lowest value is the brightest value in reality!
	 * @return lowest sensor value
	 */
	public Sensor getBrightestLightValue() {
		Sensor min = null;
		for(Sensor s : sensors.values()) {
			if(min == null) min = s;
			if(s.getLightValue() < min.getLightValue()) min = s;
		}
		return min;
	}

	/**
	 * the min value is the max distance in reality!
	 * @return min sensor value
	 */
	public Sensor getFarestDistanceValue() {
		Sensor min = null;
		for(Sensor s : sensors.values()) {
			if(min == null) min = s;
			if(s.getDistanceValue() < min.getDistanceValue()) min = s;
		}
		return min;
		
	}

	/**
	 * the max value is the min distance in reality!
	 * @return max sensor value
	 */
	public Sensor getShortestDistanceValue() {
		Sensor max = null;
		for(Sensor s : sensors.values()) {
			if(max == null) max = s;
			if(s.getDistanceValue() > max.getDistanceValue()) max = s;
		}
		return max;
	}
	
	public boolean isWallOnLeftSide() {
		Sensor max = null;
		Sensor max2 = null;
		for(Sensor s : sensors.values()) {
			if(max == null) {
				max = s;
			} else if(max2 == null) {
				max2 = s;
			}			
			if(s.getDistanceValue() > max.getDistanceValue()) {
				max2 = max;
				max = s;
			} else if(max2 != null) {
				if(s.getDistanceValue() > max2.getDistanceValue()) max2 = s;
			}
		}
		if (((max == getLeft() && max2 == getRearLeft()) || (max2 == getLeft() && max == getRearLeft())) || 
				((max == getLeft() && max2 == getLeftFront()) || (max2 == getLeft() && max == getLeftFront()))) {
			System.out.println("wall is on left side");
			return true;
		}
		System.out.println("wall is NOT on left side - max sensors are " + max.getName() + " and " + max2.getName());
		return false;
	}
	
	public boolean isWallOnRightSide() {
		Sensor max = null;
		Sensor max2 = null;
		for(Sensor s : sensors.values()) {
			if(max == null) {
				max = s;
			} else if(max2 == null) {
				max2 = s;
			}			
			if(s.getDistanceValue() > max.getDistanceValue()) {
				max2 = max;
				max = s;
			} else if(max2 != null) {
				if(s.getDistanceValue() > max2.getDistanceValue()) max2 = s;
			}
		}
		if (((max == getRight() && max2 == getRearRight()) || (max2 == getRight() && max == getRearRight())) ||
				((max == getRight() && max2 == getRightFront()) || (max2 == getRight() && max == getRightFront()))) {
			System.out.println("Wall is on right side");
			return true;
		}
		System.out.println("wall is NOT on right side - max sensors are " + max.getName() + " and " + max2.getName());
		return false;
	}

	
	
	
	public boolean isObjectInFront(int threshold) {
		return getFrontLeft().getDistanceValue() >= threshold && getFrontRight().getDistanceValue() >= threshold;
	}
	
	@Deprecated
	public boolean isObjectInFrontOld() {
		Sensor max = null;
		Sensor max2 = null;
		for(Sensor s : sensors.values()) {
			if(max == null) {
				max = s;
			} else if(max2 == null) {
				max2 = s;
			}			
			if(s.getDistanceValue() > max.getDistanceValue()) {
				max2 = max;
				max = s;
			} else if(max2 != null) {
				if(s.getDistanceValue() > max2.getDistanceValue()) max2 = s;
			}
		}
		if (((max == getFrontLeft() && max2 == getFrontRight()) || (max2 == getFrontRight() && max == getFrontLeft())) ) {
//			System.out.println("Object is in front");
			return true;
		}
//		System.out.println("Object is NOT in front - max sensors are " + max.getName() + " and " + max2.getName());
		return false;
	}

	public float[] getLvtImage() {
		return lvtImage;
	}
	
	public float[] getLvtBallVector() {
		float[] lvtImage = getLvtImage();
		float[] ret = new float[lvtImage.length];
		
		for (int i = 0; i < lvtImage.length; i++) {
			if(lvtImage[i] == ObjectColours.Ball.getValue()) {
				ret[i] = 1;
			} else {
				ret[i] = 0;
			}
		}
		return ret;
	}
	
	public float[] getLvtWallVector() {
		float[] lvtImage = getLvtImage();
		float[] ret = new float[lvtImage.length];
		
		for (int i = 0; i < lvtImage.length; i++) {
			if(lvtImage[i] == ObjectColours.Wall.getValue()) {
				ret[i] = 1;
			} else {
				ret[i] = 0;
			}
		}
		return ret;
	}
	
	public boolean isBallInFront() {		
		float[] lvtImage = getLvtImage();
		
		int count = 0;

		for (int i = 0; i < lvtImage.length; i++) {
			if(lvtImage[i] != ObjectColours.Ball.getValue()) {
				count++;
				if(count >= 42) return false;
			}
		}
		return true;
	}
	
	public boolean isWallInFront() {		
		float[] lvtImage = getLvtImage();
		int THRESHOLD = 50;
		boolean isWall = true;
		
		for (int i = 0; i < lvtImage.length; i++) {
			if(lvtImage[i] != ObjectColours.Wall.getValue()) {
				isWall = false;
			}
		}
		if(isWall) {
			if(THRESHOLD < getLeftFront().getDistanceValue() && THRESHOLD < getRightFront().getDistanceValue()
					|| THRESHOLD < getLeftFront().getDistanceValue() && THRESHOLD < getFrontLeft().getDistanceValue() 
					|| THRESHOLD < getRightFront().getDistanceValue() && THRESHOLD < getFrontRight().getDistanceValue()) {
				return true;
			}
		}
		return false;
	}

	public boolean isObjectInBack() {
		int THRESHOLD = 100;
		if(THRESHOLD < getRearLeft().getDistanceValue() && THRESHOLD < getRearRight().getDistanceValue()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param percentage of min change
	 * @return
	 */
	public boolean isAnyChange(float percentage) {
		if(sensorList == null) return false;
		
		for(Sensor s : sensorList) {
			// distance
			int currentDistance = s.getDistanceValue();
			Sensor oldSensor = sensorsOldValues.get(s.getName());
			if(oldSensor != null) {
				int oldDistance = oldSensor.getDistanceValue();
				if(currentDistance >= oldDistance + getDistanceTolerance(percentage) || currentDistance <= oldDistance - getDistanceTolerance(percentage)) {
					// "new" value for distance
					return true;
				} else {
					int currentLight = s.getLightValue();
					int oldLight = oldSensor.getLightValue();
					if(currentLight >= oldLight + getLightTolerance(percentage) || currentLight <= oldLight - getLightTolerance(percentage)) {
						// "new" value for light
						return true;
					} else {
						if(lvtImage != null && lvtImageOld != null) {
							for (int i = 0; i < lvtImage.length; i++) {
								if(lvtImage[i] <= lvtImageOld[i] - getLvtTolerance(percentage) || lvtImage[i] >= lvtImageOld[i] + getLvtTolerance(percentage)) {
									return true;
								}
							}
						}
						return false;
					}
				}
			} else  {
				return true;
			}
		}
		return true;
	}

	private float getLvtTolerance(float percentage) {
		return (LVT_MAX / 100) * percentage;
	}

	public static int getDISTANCE_MAX() {
		return DISTANCE_MAX;
	}


	public static int getLIGHT_MAX() {
		return LIGHT_MAX;
	}
	
	private float getLightTolerance(float percentage) {
		return (LIGHT_MAX / 100) * percentage;
	}
	
	private float getDistanceTolerance(float percentage) {
		return (DISTANCE_MAX / 100) * percentage;
	}

	public boolean inFrontOfLight() {
		int threshold = 50;
		int counter = 0;
		float[] lvtLightVector = getLvtLightVector();
		for (int i = 0; i < lvtLightVector.length; i++) {
			if(lvtLightVector[i] == 1) {
				if(++counter >= threshold) {
					return true;
				}
			}
		}
		return false;
	}

	public float[] getLvtLightVector() {
		float[] lvtImage = getLvtImage();
		float[] ret = new float[lvtImage.length];
		
		for (int i = 0; i < lvtImage.length; i++) {
			if(lvtImage[i] == ObjectColours.Light.getValue()) {
				ret[i] = 1;
			} else {
				ret[i] = 0;
			}
		}
		return ret;
		
	}

	public boolean isLightInFront(int occourences) {
		float[] lvtImage = getLvtLightVector();
		
		int count = 0;

		for (int i = 0; i < lvtImage.length; i++) {
			if(lvtImage[i] == 1) {
				count++;
				if(count >= occourences) return true;
			}
		}
		return false;
	}

	
	
}

enum ObjectColours {
	Ball(200f),
	Wall(10f),
	Light(255);
	
	private final float colour;
	  
    ObjectColours(float colour) {
    	this.colour = colour; 
	}
    public float getValue() { 
    	return colour; 
	}
	
}
