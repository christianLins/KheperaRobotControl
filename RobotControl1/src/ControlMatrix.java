
public class ControlMatrix {
	
	
	
	public ControlMatrix() {
		// TODO Auto-generated constructor stub
	}

	
	public static float[] calculateVector(float[] sensors, float[][]controlMatrix) {
		float[] out = new float[sensors.length];
		
		for(int o = 0; o < out.length; o++) {
			for(int i = 0; i < sensors.length; i++) {
				out[o] += sensors[i] * controlMatrix[o][i];
			}
		}
		return out;
	}
	
	
}
