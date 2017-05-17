import java.io.Console;
import java.util.Map;


public class FXCalculatorMain {

	public static void main(String[] args) {
		String baseCurrency = "";
		String termCurrency = "";
		double amount = 1.0;
		double convertedAmount = 0.0;
		String input = "";
		
		FXCalculator fxCalculator = new FXCalculator();
		Map<String,String> currencyPairMatrix = fxCalculator.getCurrencyPairMatrixForCalculation();
		Map<String,Double> rateMap = fxCalculator.getRateForCurrencyPair();

		//Take input from java Console
		Console console = System.console();
		if (console == null) {
		    System.out.println("No console: non-interactive mode!");
		    System.exit(0);
		}
		
		//Continue loop till user wants to calculate rates. Terminate application if user enters Exit
		while(true){
			//Print proper input message to end user
			System.out.println("Please Enter Input in below format");
			System.out.println("<ccy1> <amount1> in <ccy2>");
			System.out.println("Please Enter Exit to terminate the application");
			
			input = console.readLine();
			
			//Split input to parse base currency , amount and term currency
			String [] inputSplit = input.split(" ");
			try{
				if(input.equalsIgnoreCase("Exit")){
					System.exit(0);
				}
				if(inputSplit.length != 4){
					System.out.println("Invalid input.");
				} else {
					baseCurrency = inputSplit[0].toUpperCase();
					amount = Double.parseDouble(inputSplit[1]);
					termCurrency = inputSplit[3].toUpperCase();
					
					if(fxCalculator.isCurrencyPairInvalid(baseCurrency, termCurrency, currencyPairMatrix)){
						System.out.println("Unable to find rate for " + baseCurrency+"/"+termCurrency);
						continue;
					}
					
					if(amount<0){
						throw new Exception();
					}
					
					if(!inputSplit[2].equalsIgnoreCase("in")){
						throw new Exception();
					}
					
					//Calculate amount on the basis of Currency Matrix
					convertedAmount = fxCalculator.calculateFinalRate(baseCurrency,termCurrency,amount,rateMap,currencyPairMatrix);
					
					//Format converted amount into valid decimal and print the same 
					fxCalculator.printConvertedCurrencyAmount(baseCurrency, termCurrency, amount, convertedAmount);
					
					fxCalculator.clearcrossCurrencyList();// Clear crossCurrencyList before next calculation. 
				}
			}
			catch(Exception e){
				System.out.println("Invalid Input");
				continue;
			}
	}
	}

}
