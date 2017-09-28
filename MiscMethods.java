package optimizationAlgorithms;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class MiscMethods {

	// Rastrigin problem: https://en.wikipedia.org/wiki/Rastrigin_function	
	
	public static double f(double[] x)	
	{		
		final int n = x.length;
		double y = 0;
			
	
			for (int i = 0; i < n; i++)
			{
				y += (Math.pow(x[i],2)-10*Math.cos(2*Math.PI*x[i]));
			}
			
			y = 10*n + y;
	
	return y;
	}
		
	 // Saturation of solutions that fall outside of search space to nearest bound.

	public static double[] saturate(double[] x, double[][] bounds)
	{
		int n = x.length;
		double[] x_sat = new double[n];
		for (int i = 0; i < n; i++){
			x_sat[i] = min(max(x[i], bounds[i][0]), bounds[i][1]);}
		return x_sat;
	}
	
	
}
