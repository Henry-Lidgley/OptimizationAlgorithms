package optimizationAlgorithms;

import java.util.Random;

public class GA {

	public void runGAAlgorithm()
	{	
	
	//Set parameters
		
	int problemDimension = 10; //dimensionality of problem
	int maxEvaluations = problemDimension*10000; //computational budget for algorithm (function evaluations)
	int populationSize = 30; //algorithm population size. Must be even number to enable mating pool to have pairs.
	double mutationVariance = 0.005; //mutation standard deviation for Gaussian distribution as a percentage of problem bounds
	Random random = new Random(); 
	
	// particle (the solution, i.e. "x")
	double[] best = new double[problemDimension];
	double[] finalBest = new double[problemDimension];
	double fBest = Double.NaN;
	int j = 0;
	
	//Set problem bounds
	
	double problemLowerBound = -5.12;
	double problemUpperBound = 5.12;
	
	double[][] bounds = new double[problemDimension][2];
	
	for (int i = 0; i < problemDimension; i++)
	{
			bounds[i][0] = problemLowerBound;	
			bounds[i][1]= problemUpperBound;	
	}
	
	//Generate initial random solution
	
	for (int l = 0; l < problemDimension; l++)
	{
		best[l] = Math.random() * (bounds[l][1] - bounds[l][0]) + bounds[l][0];
	}	
	fBest = MiscMethods.f(best);
	j++;	
	
	//Generate initial population					
	
			double population[][] = new double[populationSize][problemDimension]; 
			for (int i = 0; i < populationSize; i++)
			{
				population[i] = new double[problemDimension];
				
				for (int l = 0; l < problemDimension; l++)
				{
					population[i][l] = Math.random() * (bounds[l][1] - bounds[l][0]) + bounds[l][0];
				}	
					population[i] = MiscMethods.saturate(population[i], bounds);	//apply saturation correction to particles
			}
			
			//run selection, crossover, mutation and replacement
			
			while (j < maxEvaluations) //while on budget
			{
				
			//run tournament
		
			int matingPoolSize = populationSize*2;
			double matingPool[][] = new double[matingPoolSize][problemDimension];
			double tournamentMember1fitness;
			double tournamentMember2fitness;
			
						for (int i = 0; i < matingPoolSize; i++)
						{
						//1st tournament member
						int random1 = random.nextInt(populationSize);
						tournamentMember1fitness = MiscMethods.f(population[random1]);
						j++;
						//2nd tournament member
						int random2 = random.nextInt(populationSize);
						tournamentMember2fitness = MiscMethods.f(population[random2]);
						j++;
						
							if (tournamentMember1fitness <= tournamentMember2fitness)
							{
							matingPool[i] = population[random1];
							}
							else{
							matingPool[i] = population[random2];
							}
						}
						
					//run box crossover whereby 2 parents create 1 off-spring
						
					int l = 0;
					int newPopulationSize = populationSize;
					double newPopulation[][] = new double[populationSize][problemDimension];
									
					while (l < matingPoolSize/2)
					{
						for (int i = 0; i < matingPoolSize; i+=2)
						{
							for (int k = 0; k < problemDimension; k++)
							{
							newPopulation[l][k] = Math.min(matingPool[i][k], matingPool[i+1][k]) + (random.nextDouble() * Math.abs(matingPool[i][k] - matingPool[i+1][k])); 
							}
							l++;
						}
					}
									
					//run mutation of new population
				
					for (int i = 0; i < newPopulationSize; i++)
					{
						for (int k = 0; k < problemDimension; k++)
						{
						newPopulation[i][k] = newPopulation[i][k] +  ((mutationVariance * (bounds[k][1]-bounds[k][0])) * random.nextGaussian());
						}
						newPopulation[i] = MiscMethods.saturate(newPopulation[i], bounds);	//apply saturation correction to particles
					}
				
				//update population with new generation for next iteration (maintaining random order)
				for (int i = 0; i < populationSize; i++)
				{
				population[i] = newPopulation[i];
				}
				
				//save best particle if fitness has improved upon best found
				
					//sort new population in order of fitness
					double[] temp;
					for (int i = 0; i < newPopulationSize; i++) 
				        {
				            for (int k = i + 1; k < newPopulationSize; k++) 
				            {
				                if (MiscMethods.f(newPopulation[i]) > MiscMethods.f(newPopulation[k]))
				                {
				                	j++;
					            	j++;
				                	temp = newPopulation[i];
				                    newPopulation[i] = newPopulation[k];
				                    newPopulation[k] = temp;
				                }
				            }
				        }
					
				// save best
				double iterationBestFitness = MiscMethods.f(newPopulation[0]);	
					
				if (iterationBestFitness < fBest)
				{
					fBest = iterationBestFitness;
					for (int n = 0; n < problemDimension; n++)
						best = newPopulation[0];
				}
				
			// end while on budget
			}		
								
				finalBest = best; //save the final best
					
				System.out.println("GA final fitness: " + fBest);
	
	}
}
