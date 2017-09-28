package optimizationAlgorithms;

import java.util.Arrays;

/**
 * S Algorithm
 */
public class S {
	
	public void runSAlgorithm()
	{	
	
	//Set parameters
		
	int problemDimension = 10;
	int maxEvaluations = problemDimension*10000;
	double a = 0.4; //step parameter (40% of domain)
	
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
			
		double[] step = new double[problemDimension]; //to hold step values
		double[] NewStep = new double[problemDimension]; //to hold new step values
		double[] xTrial = Arrays.copyOf (particle, particle.length); //trial version of x
		double xTrialOld; //to reset xTrial to old values
		double fxTrial; //fitness of xTrial
		double oldfxTrial; //old fitness of xTrial for comparison
		double OldFitness = fParticle; //old fitness of particle/x for comparison
		
		//create new step size
		for (int k = 0; k < problemDimension; k++){	
			NewStep[k] = a * (bounds[k][1]-bounds[k][0]);
			}
		
		//while within budget
		while (i < maxEvaluations){
		
			//update step size if no improvement to fitness
			for (int t = 0; t < problemDimension; t++){	
				step[t] = NewStep[t];
				}	
				
			//for every dimension do this	
			for (int j = 0; j < problemDimension; j++){ 		
				
				//update old xTrial fitness with best fitness
				oldfxTrial = fParticle;
				// save the current particle for resets
				xTrialOld = particle[j];
				
				//trial version of x is current x less the step.	
				xTrial[j] = particle[j]-step[j]; 		
				//perform toroidal saturation of x to ensure it falls within bounds.
				xTrial = MiscMethods.saturate(xTrial, bounds); 
				
				//calculate fitness of xTrial
				fxTrial = MiscMethods.f(xTrial);
				i++; //record call of fitness function
			
				//if fitness has improved save position.
				if (fxTrial <= fParticle){
					particle[j] = xTrial[j];
					fParticle = fxTrial; //update fitness
				}
				
				//if fitness has not improved reset position.
				else{		

					xTrial[j] = xTrialOld;
					fParticle = oldfxTrial;//reset fitness
					
					//trial version of x is current x less half a step in the other direction.
					xTrial[j] = particle[j] + (step[j] * 0.5);
					//perform toroidal saturation of x to ensure it falls within bounds.
					xTrial = MiscMethods.saturate(xTrial, bounds);
							
					//calculate fitness of xTrial
					fxTrial = MiscMethods.f(xTrial);
					i++; //record call of fitness function
					
					//if fitness has improved save position.
					if (fxTrial <= fParticle){
						particle[j] = xTrial[j];
						fParticle = fxTrial; //update fitness
					}
					
					//if fitness has not improved reset position.
					else{
						xTrial[j] = xTrialOld;
						//reset position of particle
						particle[j] = xTrial[j];
						fParticle = oldfxTrial; //update fitness
					}
				}
			}

			//if new fitness is better than old fitness, old fitness becomes current best for comparison.
			if (fParticle < OldFitness){
				OldFitness = fParticle;
			}
			
			//if fitness has not improved then step is halved.
			else{
				for (int l = 0; l < problemDimension; l++){
				NewStep[l] = step[l] * 0.5;
				}
			}
		}
	
		finalBest = particle; //save the final best
		
		System.out.println("S final fitness: " + fParticle);
	}	
}


