
public class Sensor {

	private final int id;	
	private String name;
	
	private int valueLight = 1000;
	private int valueDistance = 1000;
	private double sideFactor = 1;
	private boolean front = true;
	
	public Sensor(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public int getLightValue() {
		return valueLight;
	}

	public void setLightValue(int value) {
		this.valueLight = value;
	}

	/**
	 * if it is left side its negative
	 * if it is right side its positive
	 * the value increases, if sensor is longer away
	 * 
	 * @return value
	 */
	public double getSideFactor() {
		return sideFactor;
	}
	
	public void setSideFactor(double sideFactor) {
		this.sideFactor = sideFactor;
	}

	/**
	 * is sensor in front positioned
	 * @return true if front
	 */
	public boolean isFront() {
		return front;
	}

	/**
	 * rear or front sensor
	 * @param front
	 */
	public void setFront(boolean front) {
		this.front = front;
	}

	public int getDistanceValue() {
		return valueDistance;
	}

	public void setValueDistance(int valueDistance) {
		this.valueDistance = valueDistance;
	}

	public boolean isLeft() {
		return 0 > getSideFactor();
	}

	
	

}
