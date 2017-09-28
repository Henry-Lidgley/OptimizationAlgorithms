package optimizationAlgorithms;

import java.util.Random;

public class DE {

	public void runDEAlgorithm()
	{	
	
	//Set parameters
		
	int problemDimension = 10;
	int maxEvaluations = problemDimension*10000;
	int populationSize = 100;
	double F = 0.6;					
	double CR = 0.9;
	
	//Set problem to be evaluated
	
	double problemLowerBound = -5.12;
	double problemUpperBound = 5.12;
	
	double[][] bounds = new double[problemDimension][2];
	
	for (int i = 0; i < problemDimension; i++)
	{
			bounds[i][0] = problemLowerBound;	
			bounds[i][1]= problemUpperBound;	
	}

	Random random = new Random(); 
	
	// particle (the solution, i.e. "x")
		
		double[] best = new double[problemDimension];
		double fBest = Double.NaN;
		double[] finalBest = new double[problemDimension];
		int j = 0;
			
	// generate initial random solution
			
			for (int l = 0; l < problemDimension; l++)
			{
				best[l] = Math.random() * (bounds[l][1] - bounds[l][0]) + bounds[l][0];
			}	
			fBest = MiscMethods.f(best);
			j++;
	
			//Generate initial population					
			
			double population[][] = new double[populationSize][problemDimension]; 
			double offspring[][] = new double[populationSize][problemDimension]; 
			
			double populationFitness[] = new double[populationSize]; 
			double offspringFitness[] = new double[populationSize]; 
			
			for (int i = 0; i < populationSize; i++)
			{		
				for (int l = 0; l < problemDimension; l++)
				{
					population[i][l] = Math.random() * (bounds[l][1] - bounds[l][0]) + bounds[l][0];//generate particle i
				}	
					population[i] = MiscMethods.saturate(population[i], bounds);//apply saturation to particle
					
					populationFitness[i] = MiscMethods.f(population[i]);// calculate fitness
					j++;//record function call
					
					//update best
					if (populationFitness[i] < fBest)
					{
						for (int l = 0; l < problemDimension; l++)	
						{
							best[l] = population[i][l];				
						}
							fBest = populationFitness[i];					
			}
			}
													
			//run selection, mutation and crossover
			
			while (j < maxEvaluations) //while on budget
			{						
				for (int i = 0; i < populationSize; i++)	
				{
					
				int a,b,c;

						//pick three different random points from population
						do{
							a = random.nextInt(populationSize);
						}while(a==i);
						do{
							b = random.nextInt(populationSize);
						}while(b==i| b==a);
						do{
							c = random.nextInt(populationSize);
						}while(c==i | c==a | c==b);
				
				//create potential offspring		
				for (int l = 0; l < problemDimension; l++)	
				{	
				offspring[i][l] = population[a][l] + F * (population[b][l] - population[c][l]);//potential mutated offspring					
				}
				
				offspring[i] = MiscMethods.saturate(offspring[i], bounds);//apply saturation to particle
						
				//apply exponential crossover
				int index = random.nextInt(problemDimension - 1);//select index
				int k = index + 1;//set k
				
				while (random.nextDouble() <= CR)
				{
					offspring[i][k] = population[i][k];
					k++;
					
					if (k == problemDimension)
					{
						k = 0;
					}
					
					if (k == index)
					{
						break;
					}
				}		
				}
							
				for (int i = 0; i < populationSize; i++)
				{	
					offspringFitness[i] = MiscMethods.f(offspring[i]);//calculate fitness of offspring
					j++;
				
					//if offspring is fitter than its parent it survives in place of parent
					if (offspringFitness[i] < populationFitness[i])
					{
						for (int l = 0; l < problemDimension; l++)	
						{
							population[i][l] = offspring[i][l];
						}
						
						populationFitness[i] = offspringFitness[i];
						
						//update best
						if (offspringFitness[i] < fBest)
						{
							for (int l = 0; l < problemDimension; l++)
							{
								best[l] = population[i][l];							
							}
								fBest = offspringFitness[i];	
						}
				}
				
				}	
			// end while on budget
			}		
						
			finalBest = best; //save the final best
			
			System.out.println("DE final fitness: " + fBest);
		
	}
}
