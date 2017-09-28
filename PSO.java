package optimizationAlgorithms;

import java.util.Random;

public class PSO {

	public void runPSOAlgorithm()
	{	
	
	//Set parameters
		
	int problemDimension = 10;
	int maxEvaluations = problemDimension*10000;
	
	int swarmSize = 30;
	double inertiaWeight = 1;
	double inertiaWeightMax = 1;
	double inertiaWeightMin = 0.00000001;
	
	double personalWeight = 1;
	double globalWeight = 1;
	
	Random random = new Random(); 
	
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
	double[] globalBestParticle = new double[problemDimension];
	double globalParticleFitness = Double.NaN;
	int j = 0;
			
	// generate initial random solution
			
			for (int l = 0; l < problemDimension; l++)
			{
				globalBestParticle[l] = Math.random() * (bounds[l][1] - bounds[l][0]) + bounds[l][0];
			}	
			globalParticleFitness = MiscMethods.f(globalBestParticle);
			j++;
	
			//Generate initial population					
			
			double particle[][] = new double[swarmSize][problemDimension]; 
			double velocity[][] = new double[swarmSize][problemDimension]; 
			double particleFitness[] = new double[swarmSize];
			double[] finalBest = new double[problemDimension];
		
			double personalBestParticle[][] = new double[swarmSize][problemDimension];//for storing particles' personal bests 
			double personalBestFitness[] = new double[swarmSize];//for storing particles' personal best fitness 
			
			for (int i = 0; i < swarmSize; i++)
			{
				particle[i] = new double[problemDimension];
				velocity[i] = new double[problemDimension];
				
				for (int l = 0; l < problemDimension; l++)
				{
					particle[i][l] = random.nextDouble() * (bounds[l][1] - bounds[l][0]) + bounds[l][0];//create particle
					velocity[i][l] = random.nextDouble() * (bounds[l][1] - bounds[l][0]) + bounds[l][0];//create velocity
				}	
				particle[i] = MiscMethods.saturate(particle[i], bounds);//apply saturation to particles
				velocity[i] = MiscMethods.saturate(velocity[i], bounds);//apply saturation correction to velocities
					
				//store initial particles' personal bests
				for (int l = 0; l < problemDimension; l++)
				{
				personalBestParticle[i][l] = particle[i][l];
				}
				//record initial personal best fitness for comparison
				personalBestFitness[i] = MiscMethods.f(personalBestParticle[i]);
				j++;
				
				//store initial global best			
				if (personalBestFitness[i] <= globalParticleFitness)
				{
					for (int l = 0; l < problemDimension; l++)
					{
					globalBestParticle[l] = particle[i][l];
					}
					globalParticleFitness = personalBestFitness[i];
				}			
			}

			while (j < maxEvaluations) //while on budget
			{		
				//update inertia weight
				inertiaWeight = inertiaWeightMax - ((inertiaWeightMax - inertiaWeightMin)/maxEvaluations) * j;
				
				for (int i = 0; i < swarmSize; i++)	
				{
					for (int k = 0; k < problemDimension; k++)
					{
					//update velocities
					velocity[i][k] = (inertiaWeight * velocity[i][k]) + (personalWeight * random.nextDouble() * (personalBestParticle[i][k] - particle[i][k])) + (globalWeight * random.nextDouble() * (globalBestParticle[k] - particle[i][k]));
					}
					
					velocity[i] = MiscMethods.saturate(velocity[i], bounds);//apply saturation to velocities
					
					for (int k = 0; k < problemDimension; k++)
					{
					// perturb particles
					particle[i][k] = particle[i][k] + velocity[i][k];	
					}
					
					particle[i] = MiscMethods.saturate(particle[i], bounds);//apply saturation to particles
			
					particleFitness[i] = MiscMethods.f(particle[i]);//calculate particle's fitness
					j++;
					
					//update local best particle
					if (particleFitness[i] <= personalBestFitness[i])
					{
						for (int k = 0; k < problemDimension; k++)
						{
						personalBestParticle[i][k] = particle[i][k];		
						}
						personalBestFitness[i] = particleFitness[i];
						
						//update global best particle
						if (particleFitness[i] <= globalParticleFitness)
						{
							for (int k = 0; k < problemDimension; k++)
							{
							globalBestParticle[k] = particle[i][k];
							}						
							globalParticleFitness = particleFitness[i];//record global best fitness
						}
					}		
				}
			}	
			// end while on budget		
							
				finalBest = globalBestParticle; //save the final best
				
				System.out.println("PSO final fitness: " + globalParticleFitness);
			
	}
}
