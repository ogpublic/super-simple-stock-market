package com.acme;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.acme.domain.Stock;
import com.acme.domain.StockType;
import com.acme.domain.Trade;
import com.acme.domain.TradeType;
import com.acme.services.businesslogic.BusinesslogicException;
import com.acme.services.businesslogic.StockMarketService;
import com.acme.services.businesslogic.StocksService;
import com.acme.services.businesslogic.TradesService;

/**
 * Main application class.
 * 
 * @author Ovidiu Guse - ovidiu.guse@gmail.com, 2015
 *
 */
public class SuperSimpleStockMarket {
	private static final Logger logger = LogManager.getLogger(SuperSimpleStockMarket.class);

	private ClassPathXmlApplicationContext context = null;

	private StocksService stocksService;
	private TradesService tradesService;
	private StockMarketService stockMarketService;

	public SuperSimpleStockMarket() {
		init();
		loadSampleData();
	}

	/**
	 * Initialize the application by booting the Spring context and reading the required beans from
	 * the Spring context definition.
	 */
	private void init() {
		context = new ClassPathXmlApplicationContext("simple-stock-market-context.xml");

		stocksService = (StocksService) context.getBean("stocksService");
		tradesService = (TradesService) context.getBean("tradesService");
		stockMarketService = (StockMarketService) context.getBean("stockMarketService");
	}

	/**
	 * Loads samsple data in the datastores.
	 */
	private void loadSampleData() {
		logger.info("Loading stock information ...");
		try {
			stocksService.createStock(new Stock("TEA", StockType.COMMON, 1000, 100, 0, 0));
			stocksService.createStock(new Stock("POP", StockType.COMMON, 1000, 100, 8, 0));
			stocksService.createStock(new Stock("ALE", StockType.COMMON, 1000, 60, 23, 0));
			stocksService.createStock(new Stock("GIN", StockType.PREFERRED, 1000, 100, 8, 0.02));
			stocksService.createStock(new Stock("JOE", StockType.COMMON, 1000, 250, 13, 0));
		} catch (BusinesslogicException e) {
			logger.error("Exception occurred while loading stock information.", e);
			System.exit(0);
		}
		logger.info("Loading stock information ... Completed!");

		logger.info("Loading trades information ...");
		try {
			tradesService.createTrade(new Trade("TEA", TradeType.BUY, 100, 25, new Date()));
			tradesService.createTrade(new Trade("TEA", TradeType.SELL, 50, 15, new Date()));
		} catch (BusinesslogicException e) {
			logger.error("Exception occurred while loading trades information.", e);
			System.exit(0);
		}
		logger.info("Loading trades information ... Completed");
	}

	/**
	 * Starts the main application logic, by showing a console menu to the user and reading various
	 * use options from the keyboard.
	 */
	public void start() {
		printUsage();

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			String line = "";
			while ((line = br.readLine()) != null && !line.equalsIgnoreCase("0")) {
				try {

					int option = -1;
					try {
						option = Integer.parseInt(line);
					} catch (Exception e) {
						printUsage();
						continue;
					}

					switch (option) {
						case 1:
							System.out.print("Stock symbol: ");
							String stockSymbol = br.readLine();

							System.out.print("Price: ");
							String numberString = br.readLine();

							double number = -1;
							try {
								number = Double.parseDouble(numberString);
							} catch (Exception e) {
								System.out.println("Invalid price value.");
								printUsage();
								continue;
							}

							double dividentYield = stockMarketService.calculateDividentYield(stockSymbol, number);
							System.out.println("Divident Yield: " + dividentYield);
							System.out.print("Please choose what you want to do next: ");
							break;
						case 2:
							System.out.print("Stock symbol: ");
							stockSymbol = br.readLine();

							System.out.print("Price: ");
							numberString = br.readLine();

							number = -1;
							try {
								number = Double.parseDouble(numberString);
							} catch (Exception e) {
								System.out.println("Invalid price value.");
								printUsage();
								continue;
							}

							double peRatio = stockMarketService.calculatePERatio(stockSymbol, number);
							System.out.println("P/E Ratio: " + peRatio);
							System.out.print("Please choose what you want to do next: ");

							break;
						case 3:
							System.out.print("Stock symbol: ");
							stockSymbol = br.readLine();

							System.out.print("Type (use 'BUY' or 'SELL'): ");
							String typeString = br.readLine();

							TradeType type = null;
							try {
								type = TradeType.valueOf(typeString);
							} catch (Exception e) {
								System.out.println("Unknown trade type.");
								printUsage();
								continue;
							}

							System.out.print("Quantity: ");
							numberString = br.readLine();

							int quantity = -1;
							try {
								quantity = Integer.parseInt(numberString);
							} catch (Exception e) {
								System.out.println("Invalid quantity value.");
								printUsage();
								continue;
							}

							System.out.print("Price: ");
							numberString = br.readLine();

							number = -1;
							try {
								number = Double.parseDouble(numberString);
							} catch (Exception e) {
								System.out.println("Invalid price value.");
								printUsage();
								continue;
							}

							Trade trade = new Trade();
							trade.setStockSymbol(stockSymbol);
							trade.setType(type);
							trade.setQuantity(quantity);
							trade.setPrice(number);

							tradesService.createTrade(trade);

							System.out.println("Trade saved to persistent storage!");
							System.out.print("Please choose what you want to do next: ");

							break;
						case 4:
							System.out.print("Stock symbol: ");
							stockSymbol = br.readLine();

							System.out.print("Trades age (in minutes): ");
							String ageString = br.readLine();

							int age = -1;
							try {
								age = Integer.parseInt(ageString);
							} catch (Exception e) {
								System.out.println("Invalid age value.");
								continue;
							}

							double volumeWeightedStockPrice = stockMarketService.calculateVolumeWeightedStockPrice(stockSymbol, age);
							System.out.println("Volume Weighted Stock Price: " + volumeWeightedStockPrice);
							System.out.print("Please choose what you want to do next: ");
							break;
						case 5:
							double gbceAllSharesIndex = stockMarketService.calculateGBCEAllShareIndex();
							System.out.println("GBCE All Shares Index: " + gbceAllSharesIndex);
							System.out.print("Please choose what you want to do next: ");
							break;
						case 6:
							System.out.print("Stock symbol: ");
							stockSymbol = br.readLine();

							System.out.print("Stock type (use 'COMMON' or 'PREFERRED'): ");
							String stockTypeString = br.readLine();

							StockType stockType = null;
							try {
								stockType = StockType.valueOf(stockTypeString);
							} catch (Exception e) {
								System.out.println("Unknown stock type.");
								printUsage();
								continue;
							}

							System.out.print("Shares count: ");
							String sharesCountString = br.readLine();

							int sharesCount = -1;
							try {
								sharesCount = Integer.parseInt(sharesCountString);
							} catch (Exception e) {
								System.out.println("Invalid shares count value.");
								continue;
							}

							System.out.print("Last dividend: ");
							numberString = br.readLine();

							double lastDividend = -1;
							try {
								lastDividend = Double.parseDouble(numberString);
							} catch (Exception e) {
								System.out.println("Invalid last dividend value.");
								continue;
							}

							System.out.print("Fixed dividend: ");
							numberString = br.readLine();

							double fixedDividend = -1;
							try {
								fixedDividend = Double.parseDouble(numberString);
							} catch (Exception e) {
								System.out.println("Invalid fixed dividend value.");
								continue;
							}

							System.out.print("Par value: ");
							numberString = br.readLine();

							double parValue = -1;
							try {
								parValue = Double.parseDouble(numberString);
							} catch (Exception e) {
								System.out.println("Invalid par value.");
								continue;
							}

							Stock stock = new Stock();
							stock.setSymbol(stockSymbol);
							stock.setType(stockType);
							stock.setSharesCount(sharesCount);
							stock.setLastDividend(lastDividend);
							stock.setFixedDividend(fixedDividend);
							stock.setParValue(parValue);

							stocksService.createStock(stock);

							System.out.println("Stock saved to persistent storage!");
							System.out.print("Please choose what you want to do next: ");

							break;
						case 7:
							Collection<Stock> stocks = stocksService.listAllStocks();
							for (Stock aStock : stocks) {
								System.out.println(aStock.toString());
							}
							System.out.print("Please choose what you want to do next: ");
							break;
						case 8:
							Collection<Trade> trades = tradesService.listAllTrades();
							for (Trade aTrade : trades) {
								System.out.println(aTrade.toString());
							}
							System.out.print("Please choose what you want to do next: ");
							break;
						case 9:
							printUsage();
							break;
					}
				} catch (BusinesslogicException e) {
					printUsage();
					continue;
				}
			}
		} catch (Exception e) {
			logger.error("Exception occurred while reading user input.", e);
			System.exit(0);
		}
	}

	/**
	 * Displays application usage to the console.
	 */
	private void printUsage() {
		StringBuilder usage = new StringBuilder();
		usage.append("Welcome to Super Simple Stock Market.").append("\n");
		usage.append("1. Calculate divident yield").append("\n");
		usage.append("2. Calculate P/E ratio").append("\n");
		usage.append("3. Record a trade").append("\n");
		usage.append("4. Calculate Volume Weighted Stock Price").append("\n");
		usage.append("5. Calculate GBCE All Shares Index").append("\n");
		usage.append("----------------------------------------").append("\n");
		usage.append("6. Record a stock").append("\n");
		usage.append("7. List all stocks").append("\n");
		usage.append("8. List all trades").append("\n");
		usage.append("----------------------------------------").append("\n");
		usage.append("9. Print this menu").append("\n");
		usage.append("0. Exit").append("\n");
		usage.append("Please choose what you want to do next: ");

		System.out.print(usage.toString());
	}

	public static void main(String[] args) {
		new SuperSimpleStockMarket().start();
	}
}
