# OptimizationAlgorithms
Some examples of optimization algorithms in Java.

Six different optimisation algorithms are tested on the Rastrigin problem at 10 dimensions: https://en.wikipedia.org/wiki/Rastrigin_function	

When a new position is created, saturation is applied in case the position is outside the bounds of the problem.

This is an overview of the algorithms tested:

1. The Short Distance Exploration algorithm, or S algorithm for short, is a deterministic greedy-descent algorithm with a
perturbation logic that sees a single particle locally explore an n-dimension domain using orthogonal steps along the
axes. If steps in each dimension do not result in an improvement a half step is taken in the opposite direction. This
methodology continues until the computational budget allocated is reached.
2. The Simulated Annealing algorithm is a single particle stochastic search algorithm that uses a mechanism to jump out
of local minima.
3. The genetic algorithm is an optimization method based on natural selection, the process behind biological evolution.
An initial population of particles is randomly created, from which individuals are selected as parents. The parents are
used to create offspring particles that usual completely replace the previous population. All surviving particles are
then subject to a form of mutation before the parent selection and subsequent algorithm steps reoccur.
4. The (1+1) evolutionary strategy (ES) algorithm is a simple, single-particle, mutation-based algorithm for continuous
optimisation problems. This variant uses a methodology whereby if the offspring is fitter than the parent the step size is increased by 1.5; if it is worse, the step size is decreased by 1.5^(-1/4). Both perturbations of the step size in essence implement the one-fifth success rule.
5. The Particle Swarm Optimiser (PSO) is a technique modelled on the swarm behaviour of certain animals that search
in a collaborative manner. Each member of the swarm changes its search pattern, or velocity, based on its own
experience and the experience of the swarm as a whole.
6. Differential Evolution (DE) is a type of evolutionary algorithm that is simple to execute and understand and yet
demonstrates good convergence. One of its standard methodologies (and the one used in these experiments) is to
create potential offspring from each particle in turn using three other particles chosen at random and a variable F,
which controls the amplification of the difference between two of them. The difference vector is added to the third
randomly chosen particle. Crossover is then applied. In binomial crossover each dimensionality of the potential offspring is accepted
unless a uniformly random number (within the bounds 0, 1) is equal to or less than a variable CR, else the element is
reverted to that of its parent. In exponential crossover as soon as a uniformly random number is equal to or less than
CR all the remaining elements of the particle are reverted to that of its parent. In each method one randomly chosen
index always remains that of the offspring to ensure that some change in the population occurs. Finally, if the
offspring is fitter than its parent, it survives, if not, the parent survives instead.
