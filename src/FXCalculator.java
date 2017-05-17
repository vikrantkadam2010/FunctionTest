import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FXCalculator {
	
	List crossCurrencyList = new ArrayList();
	
	//Load all direct cross - currency pair rates into hashmap
	public Map<String,Double> getRateForCurrencyPair(){
		
		Map<String,Double> rateMap = new HashMap<String, Double>();
		rateMap.put("AUDUSD", 0.8371);
		
		rateMap.put("CADUSD", 0.8711);

		rateMap.put("USDCNY", 6.1715);

		rateMap.put("EURUSD", 1.2315);

		rateMap.put("GBPUSD", 1.5683);	
		
		rateMap.put("NZDUSD", 0.7750);
		
		rateMap.put("USDJPY", 119.95);
		
		rateMap.put("EURCZK", 27.6028);

		rateMap.put("EURDKK", 7.4405);
		
		rateMap.put("EURNOK", 8.6651);
		
		return rateMap;
	}
	
	//Load cross - currency pair matrix into hashmap
	public Map<String,String> getCurrencyPairMatrixForCalculation(){
			
			Map<String,String> currencyPairMatrix = new HashMap<String, String>();
			
			currencyPairMatrix.put("AUDCAD","USD");
			currencyPairMatrix.put("AUDCNY","USD");
			currencyPairMatrix.put("AUDCZK","USD");
			currencyPairMatrix.put("AUDDKK","USD");
			currencyPairMatrix.put("AUDEUR","USD");
			currencyPairMatrix.put("AUDGBP","USD");
			currencyPairMatrix.put("AUDJPY","USD");
			currencyPairMatrix.put("AUDNOK","USD");
			currencyPairMatrix.put("AUDNZD","USD");
			currencyPairMatrix.put("AUDUSD","D");
			
			currencyPairMatrix.put("CADCNY","USD");
			currencyPairMatrix.put("CADCZK","USD");
			currencyPairMatrix.put("CADDKK","USD");
			currencyPairMatrix.put("CADEUR","USD");
			currencyPairMatrix.put("CADGBP","USD");
			currencyPairMatrix.put("CADJPY","USD");
			currencyPairMatrix.put("CADNOK","USD");
			currencyPairMatrix.put("CADNZD","USD");
			currencyPairMatrix.put("CADUSD","D");
			
			currencyPairMatrix.put("CNYCZK","USD");
			currencyPairMatrix.put("CNYDKK","USD");
			currencyPairMatrix.put("CNYEUR","USD");
			currencyPairMatrix.put("CNYGBP","USD");
			currencyPairMatrix.put("CNYJPY","USD");
			currencyPairMatrix.put("CNYNOK","USD");
			currencyPairMatrix.put("CNYNZD","USD");
			currencyPairMatrix.put("CNYUSD","D");			
			
			currencyPairMatrix.put("CZKDKK","EUR");
			currencyPairMatrix.put("CZKEUR","INV");
			currencyPairMatrix.put("EURCZK","D");
			currencyPairMatrix.put("CZKGBP","USD");
			currencyPairMatrix.put("CZKJPY","USD");
			currencyPairMatrix.put("CZKNOK","EUR");
			currencyPairMatrix.put("CZKNZD","USD");
			currencyPairMatrix.put("CZKUSD","EUR");
			
			currencyPairMatrix.put("DKKEUR","INV");
			currencyPairMatrix.put("EURDKK","D");
			currencyPairMatrix.put("DKKGBP","USD");
			currencyPairMatrix.put("DKKJPY","USD");
			currencyPairMatrix.put("DKKNOK","EUR");
			currencyPairMatrix.put("DKKNZD","USD");
			currencyPairMatrix.put("DKKUSD","EUR");
			
			currencyPairMatrix.put("EURGBP","USD");
			currencyPairMatrix.put("EURJPY","USD");
			currencyPairMatrix.put("EURNOK","D");
			currencyPairMatrix.put("NOKEUR","INV");
			currencyPairMatrix.put("EURNZD","USD");
			currencyPairMatrix.put("EURUSD","D");
			
			currencyPairMatrix.put("GBPJPY","USD");
			currencyPairMatrix.put("GBPNOK","USD");	
			currencyPairMatrix.put("GBPNZD","USD");
			currencyPairMatrix.put("GBPUSD","D");
			
			currencyPairMatrix.put("JPYNOK","USD");
			currencyPairMatrix.put("JPYNZD","USD");
			currencyPairMatrix.put("JPYUSD","INV");
			
			currencyPairMatrix.put("NOKNZD","USD");
			currencyPairMatrix.put("NOKUSD","EUR");
			
			currencyPairMatrix.put("NZDUSD","D");
			
			currencyPairMatrix.put("USDNZD","INV");
			currencyPairMatrix.put("USDJPY","D");
			currencyPairMatrix.put("USDGBP","INV");
			currencyPairMatrix.put("USDEUR","INV");
			currencyPairMatrix.put("USDCNY","INV");
			currencyPairMatrix.put("USDCAD","INV");
			currencyPairMatrix.put("USDAUD","INV");			
			
			
			return currencyPairMatrix;
	}
	
	//Calculate rate for Direct Currency Pair
	public double calculateDirectValue(String baseCurrency, String termCurrency, Map<String,Double> rateMap){
		double rate = 0.0;
		if(rateMap.containsKey(baseCurrency+termCurrency)){
			rate = rateMap.get(baseCurrency+termCurrency);
		}
		if(rate == 0.0){
			rate = 1.0/rateMap.get(termCurrency+baseCurrency);
		}
				
		return rate;
	}
	
	//Calculate rate for Inverse Currency Pair
	public double calculateInverseValue(String baseCurrency, String termCurrency, Map<String,Double> rateMap){
		double rate = 0.0;
		if(rateMap.containsKey(baseCurrency+termCurrency)){
			rate = rateMap.get(baseCurrency+termCurrency);
		}
		/*if(rate != 0.0){
			rate = 1.0/rate;
		}*/
		if(rate == 0.0){
			rate = rateMap.get(termCurrency+baseCurrency);
			rate = 1.0/rate;
		}
			
		return rate;
	}
	
	/*Calculate rate for Cross Currency pair and Store rate into list for dependent pairs
	for example: User input is DKK 1 in CAD
	then this function will return a list as : [DKK-EUR,EUR-USD,USD-CAD]
	using these pairs we will calculate final conversion currency rates in method calculateFinalRateForCrossCurrency()
	*/
	public List calculateCrossCurrencyRates(String baseCurrency, String termCurrency, Map<String,Double> rateMap, Map<String,String> currencyPairMatrix){
		//List crossCurrencyList = new ArrayList();
		String currencyMatrixValue = currencyPairMatrix.get(baseCurrency+termCurrency);
		if(currencyMatrixValue == null){
			currencyMatrixValue = currencyPairMatrix.get(termCurrency+baseCurrency);
		}
		
		if(currencyMatrixValue.equalsIgnoreCase(ApplicationConstants.CURRENCY_DIRECT) || (currencyMatrixValue.equalsIgnoreCase(ApplicationConstants.CURRENCY_INVERSE)))
		{
			crossCurrencyList.add(baseCurrency+termCurrency);
			return crossCurrencyList;
		}
		else
		{
			calculateCrossCurrencyRates(baseCurrency,currencyMatrixValue, rateMap, currencyPairMatrix);
			calculateCrossCurrencyRates(currencyMatrixValue,termCurrency, rateMap, currencyPairMatrix);
		}
		return crossCurrencyList;
		
	}
	
	//Gives the final rate based on Direct, Inversion or cross Currency Pairs
	public double calculateFinalRate(String baseCurrency,String termCurrency,double amount, Map<String,Double> rateMap, Map<String,String> currencyPairMatrix){
		double finalRate = 1.0;
		String currencyMatrixValue = currencyPairMatrix.get(baseCurrency+termCurrency);
		if(currencyMatrixValue == null){
			currencyMatrixValue = currencyPairMatrix.get(termCurrency+baseCurrency);
		}
		
		if(baseCurrency.equalsIgnoreCase(termCurrency)){
			finalRate = 1;
		}
		else if(currencyMatrixValue.equalsIgnoreCase(ApplicationConstants.CURRENCY_DIRECT)){
			finalRate = calculateDirectValue(baseCurrency, termCurrency, rateMap);
		} else if(currencyMatrixValue.equalsIgnoreCase(ApplicationConstants.CURRENCY_INVERSE)){
			finalRate = calculateInverseValue(baseCurrency, termCurrency, rateMap);
		} else {
			//recursive call until we get either Direct or Inverse feed 
			crossCurrencyList = calculateCrossCurrencyRates(baseCurrency, currencyMatrixValue, rateMap, currencyPairMatrix);
			crossCurrencyList = calculateCrossCurrencyRates(currencyMatrixValue, termCurrency, rateMap, currencyPairMatrix);
			finalRate = calculateRateForCrossCurrency(crossCurrencyList,rateMap, currencyPairMatrix);
		}
		return finalRate*amount;
	}

	public double calculateRateForCrossCurrency(List crossCurrencyList,
		Map<String, Double> rateMap, Map<String, String> currencyPairMatrix) {
		double rate = 1.0;
		Iterator it = crossCurrencyList.iterator();
		while(it.hasNext()){
			String currencyPair = (String) it.next();
			String baseCurr = currencyPair.substring(0, 3);
			String termCurr = currencyPair.substring(3, 6);
			if(currencyPairMatrix.get(currencyPair).equalsIgnoreCase(ApplicationConstants.CURRENCY_DIRECT)){
				rate = rate * calculateDirectValue(baseCurr, termCurr, rateMap);
			} else{
				rate = rate * calculateInverseValue(baseCurr, termCurr, rateMap);
			}
		}
		return rate;
	}
	
	//Prints final output on console with mentioned precision
	public void printConvertedCurrencyAmount(String baseCurrency, String termCurrency,double amount,double convertedAmount){
		NumberFormat formatter = new DecimalFormat("#0.00"); 
		
		if(termCurrency.equalsIgnoreCase("JPY") && baseCurrency.equalsIgnoreCase("JPY")){
			System.out.println(baseCurrency +" "+ (long)amount + " = " + termCurrency+ " " +(long)convertedAmount);
		} else if(baseCurrency.equalsIgnoreCase("JPY")){
			System.out.println(baseCurrency +" "+ (long)amount + " = " + termCurrency+ " " +formatter.format(convertedAmount));
		} else if(termCurrency.equalsIgnoreCase("JPY")){
			System.out.println(baseCurrency +" "+ formatter.format(amount) + " = " + termCurrency+ " " +(long)convertedAmount);
		} else{
			System.out.println(baseCurrency +" "+ formatter.format(amount) + " = " + termCurrency+ " " +formatter.format(convertedAmount));
		}
	}
	
	//Validate Currency pair 
	public boolean isCurrencyPairInvalid(String baseCurrency,
			String termCurrency, Map<String, String> currencyPairMatrix) {
		boolean invalidCurrencyPair = false;
		if(!(currencyPairMatrix.containsKey(baseCurrency+termCurrency) || 
				(currencyPairMatrix.containsKey(termCurrency+baseCurrency))|| baseCurrency.equalsIgnoreCase(termCurrency))){
			invalidCurrencyPair = true;
		} 
		return invalidCurrencyPair;
	}
	
	public void clearcrossCurrencyList(){
		crossCurrencyList.clear();
	}
}
