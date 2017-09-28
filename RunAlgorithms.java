package optimizationAlgorithms;

public class RunAlgorithms {

	public static void main(String[] args) {
		
		S newS = new S();
		newS.runSAlgorithm();
		
		SA newSA = new SA();
		newSA.runSAAlgorithm();
		
		GA newGA = new GA();
		newGA.runGAAlgorithm();
		
		ES1plus1 newES1plus1 = new ES1plus1();
		newES1plus1.runES1plus1Algorithm();
		
		PSO newPSO = new PSO();
		newPSO.runPSOAlgorithm();
		
		DE newDE = new DE();
		newDE.runDEAlgorithm();

	}

}
