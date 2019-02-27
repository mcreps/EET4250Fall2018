package com.ut.eet4250;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.connection.DatabaseManager;
import com.ut.eet4250.hwk.InClass;
import com.ut.eet4250.hwk.InClass1;
import com.ut.eet4250.hwk.Lab;
import com.ut.eet4250.hwk.TableUtils;

public class Samples {

	private static Logger logger = LoggerFactory.getLogger(Samples.class);

	public static void main(String[] args) throws SQLException {
		logger.debug("Program Starting");

		DatabaseManager databaseManager = new DatabaseManager("database.properties");
		Connection dataSource = databaseManager.establishConnection();
		if (null == dataSource) {
			System.exit(1);
		}
		
		List<String> list = new ArrayList<>();
		list.add("Voltage");
		list.add("Object");
		list.add("BankAccount");
		list.add("Course");
		list.add("Course_Grade");
		TableUtils.tablesExists(dataSource, list, logger);
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter assignment name: " );
		String choice = scanner.nextLine();
		Lab lab = new Lab(dataSource);
		
		
		if ("100".equals(choice)) {
			InClass inClass = new InClass(dataSource);
			inClass.printRowIdFirst100();
		}
		else if ("avgsummax".equals(choice)) {
			lab.avgSumMaxLab();
		}
		else if("1".equals(choice)) {
			InClass1 ic1 = new InClass1(dataSource);
			ic1.displayVoltageRowCount();
			double rows = ic1.getDisplayVoltageRowCount();
			logger.debug("From Samples: " + rows);
			
			ic1.computeVoltage();
			ic1.reverse();
		}
		else {
			throw new IllegalArgumentException("Invalid argument..  Argument: " + choice);
		}
				
		
		
		
//		switch(choice) {
//		case "functions" :
//			MysqlFunctions functions = new MysqlFunctions(dataSource);
//			//functions.getVoltageAverages("ASC");
//			//logger.debug("DESC");
//			//functions.getVoltageAverages("DESC");
//			
//			functions.run("SELECT COUNT(*) AS VALUE FROM Voltage");
//			functions.run("SELECT MAX(VLOTAGE) AS VALUE FROM Voltage");
//			functions.run("SELECT MIN(VOLTAGE) AS VALUE FROM Voltage");
//			break;
//		case "averages" :
//			MysqlFunctions averages = new MysqlFunctions(dataSource);
//			averages.getAverages();
//			averages.getMinMax();
//			averages.addJune();
//			averages.addJuly();
//			averages.addAugust();
//			averages.getAverages();
//			break;
//			
//		default : 
//			System.out.println("Stupid choice..  Choice: "+ choice);
//			break;
//		}

		/* Close the database connection */
		databaseManager.closeConnection(dataSource);
		logger.debug("Program completed");
	}


	/**
	 * Converts a String to a double
	 * 
	 * @param s
	 *            String to convert
	 * @return
	 */
	static double convertStringToDouble(String s) {
		try {
			return Double.parseDouble(s);
		} catch (Exception e) {
			return 0.0;
		}
	}

}
