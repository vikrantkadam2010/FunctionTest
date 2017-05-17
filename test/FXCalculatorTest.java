import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;
import junit.framework.TestCase;
import org.junit.Test;


public class FXCalculatorTest extends TestCase {
	private String baseCurrency;
	private String termCurrency;
	private double amount;
	private Map<String, String> currencyPairMatrix;
	private Map<String,Double> rateMap;
	private FXCalculator fxCalculator = new FXCalculator();
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
	protected void setUp() throws Exception {
		super.setUp();
		baseCurrency = "AUD";
		termCurrency = "USD";
		amount = 10.50;
		currencyPairMatrix = fxCalculator.getCurrencyPairMatrixForCalculation();
		rateMap = fxCalculator.getRateForCurrencyPair();
		System.setOut(new PrintStream(outContent));
	}
	protected void tearDown() throws Exception {
		super.tearDown();
		baseCurrency = "";
		termCurrency = "";
		amount = 1.0;
		currencyPairMatrix.clear();
		rateMap.clear();
		System.setOut(null);
	}
	
	@Test
	public void testIsCurrencyPairInvalid() {
		assertFalse(fxCalculator.isCurrencyPairInvalid(baseCurrency, termCurrency, currencyPairMatrix));
	}

	@Test
	public void testcalculateDirectValue() {
		assertEquals(0.8371, fxCalculator.calculateDirectValue(baseCurrency, termCurrency, rateMap)) ;// Checking Direct rate not converted value.
	}
	
	@Test
	public void testcalculateInverseValue() {
		baseCurrency = "USD";
		termCurrency = "NZD";
		assertEquals(1.2903225806451613, fxCalculator.calculateInverseValue(baseCurrency, termCurrency, rateMap)) ;// Checking Inverse rate not converted value.
	}
	
	@Test
	public void testprintConvertedCurrencyAmount() {
		 double convertedAmount = fxCalculator.calculateFinalRate(baseCurrency,termCurrency,amount,rateMap,currencyPairMatrix);
		 fxCalculator.printConvertedCurrencyAmount(baseCurrency, termCurrency, amount, convertedAmount);
		 String expectedOutput = new String("AUD 10.50 = USD 8.79");
		 assertTrue(outContent.toString().trim().equals(expectedOutput.trim()));
	}
}
