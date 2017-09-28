package optimizationAlgorithms;

import java.util.Arrays;
import java.util.Random;

public class ES1plus1 {

	public void runES1plus1Algorithm()
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
			
		Random random = new Random(); //for generating changes to mutation
		double stepSize = (0.2 * (bounds[0][1]-bounds[0][0])); //stepSize standard deviation initialised at 20% of domain
			
		double[] xTrial = Arrays.copyOf (particle, particle.length); //offspring
		double[] xTrialOld = Arrays.copyOf (particle, particle.length); //to reset offspring for next generation
		double fxTrial; //function value of offspring for comparison
		
		//while within budget
				while (i < maxEvaluations){

					//update xTrialOld for resets
					for (int j = 0; j < problemDimension; j++){
						xTrialOld[j] = particle[j];
						}
					
					//create offspring by mutating parent with Gaussian distribution	
					for (int j = 0; j < problemDimension; j++){
					xTrial[j] = xTrial[j] + (stepSize * random.nextGaussian()); 
					}
				
					//perform saturation of offspring to ensure it falls within bounds.
					xTrial = MiscMethods.saturate(xTrial, bounds); 
					
					fxTrial = MiscMethods.f(xTrial);//calculate function value
					i++; //record call of fitness function
					
					//if offspring fitter than parent, offspring becomes parent for next iteration
					if (fxTrial < fParticle){
						for (int j = 0; j < problemDimension; j++){
							particle[j] = xTrial[j];		
						}
						fParticle = fxTrial;
						stepSize = stepSize * 1.5;//increase step size
					}
					
					//if offspring not fitter than parent, keep parent for next iteration
					else if (fxTrial == fParticle){
						for (int j = 0; j < problemDimension; j++){
							xTrial[j] = particle[j];//reset offspring
					}
						fxTrial = fParticle;
					}
					
					//if offspring not fitter than parent, keep parent for next iteration
					else {
						for (int j = 0; j < problemDimension; j++){
							xTrial[j] = particle[j];//reset offspring
					}
						fxTrial = fParticle;
						stepSize = Math.pow(1.5, -0.25) * stepSize;//reduce step size
					}
				}
				finalBest = particle; //save the final best
				
				System.out.println("ES1plus1 final fitness: " + fParticle);
	}
}
