package optimizationAlgorithms;

import java.util.Random;

public class SA {

	public void runSAAlgorithm()
	{	
	
	//Set parameters
		
	int problemDimension = 10;
	int maxEvaluations = problemDimension*10000;
	
	//Set problem to be evaluated
	
	double problemLowerBound = -5.12;
	double problemUpperBound = 5.12;
	
	double[][] bounds = new double[problemDimension][2];
	
	for (int i = 0; i < problemDimension; i++)
	{
			bounds[i][0] = problemLowerBound;	
			bounds[i][1]= problemUpperBound;	
	}

	// particle (the solution, i.e. "x")
	
	double[] particle = new double[problemDimension];
	double[] finalBest = new double[problemDimension];
	double fParticle; //fitness value, i.e. "f(x)"
	int i = 0;
	
	// generate initial random solution
	
	for (int l = 0; l < problemDimension; l++)
	{
		particle[l] = Math.random() * (bounds[l][1] - bounds[l][0]) + bounds[l][0];
	}	
	fParticle = MiscMethods.f(particle);
	i++;	
	
	double temp = 1;//temperature
	double decayRate = 0.9;//cooling rate
	Random random = new Random(); //generate a random number [0,1)

	double xCurrent; //position of current x for resets
	double fCurrentx; //current fitness of x for comparison
	
	while (i < maxEvaluations && temp > 0.0) // while on budget and temperature does not equal zero	
	{
		for (int j = 0; j < problemDimension; j++) //for all dimensions of x
		{
			//update current fitness
			fCurrentx = fParticle;
			//save the current particle for resets
			xCurrent = particle[j];
			
			particle[j] = particle[j] + ((0.2 * (bounds[j][1]-bounds[j][0])) * random.nextGaussian());
			particle = MiscMethods.saturate(particle, bounds); //perform saturation of x to ensure it falls within bounds.
			fParticle = MiscMethods.f(particle); //update fitness

			i++; //record call of fitness function
				
			//if fitness has improved OR accept poor configuration THEN retain position
				
			if (fParticle <= fCurrentx || random.nextDouble() < Math.exp((fCurrentx - fParticle) / temp)){}
			
			//otherwise:
			else{
				particle[j] = xCurrent;//reset particle position
				fParticle = fCurrentx;//reset fitness
			}
			
		
			temp = temp	* decayRate; //cooling
			//temp = temp * Math.pow(0.95, i);
			//temp = temp / Math.log(i);
		}
	}
	
	fParticle = MiscMethods.f(particle);
	
	finalBest = particle; //save the final best
	
	System.out.println("SA final fitness: " + fParticle);
}
}
