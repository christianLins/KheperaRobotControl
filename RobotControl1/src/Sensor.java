
/**
 * abstraction of a robot sensor
 * @author Chris
 *
 */
public class Sensor implements Cloneable {

	private final int id;	
	private String name;
	
	private int valueLight = SensorManager.getLIGHT_MAX();
	private int valueDistance = SensorManager.getDISTANCE_MAX();
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
	
	public boolean isRight() {
		return 0 <= getSideFactor();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sensor other = (Sensor) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	public Sensor clone() {
		Sensor s = new Sensor(getId(), getName());
		s.setFront(front);
		s.setLightValue(getLightValue());
		s.setSideFactor(getSideFactor());
		s.setValueDistance(valueDistance);
		return s;
	}

	
	

}
