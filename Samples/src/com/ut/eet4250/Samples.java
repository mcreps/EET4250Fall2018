package com.ut.eet4250;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.connection.DatabaseManager;
import com.ut.eet4250.hwk.MysqlFunctions;

public class Samples {

	private static Logger logger = LoggerFactory.getLogger(Samples.class);

	public static void main(String[] args) {
		logger.debug("Program Starting");

		DatabaseManager databaseManager = new DatabaseManager("database.properties");
		Connection dataSource = databaseManager.establishConnection();
		if (null == dataSource) {
			System.exit(1);
		}
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter assignment name: " );
		String choice = scanner.nextLine();
		
		switch(choice) {
		case "functions" :
			MysqlFunctions functions = new MysqlFunctions(dataSource);
			//functions.getVoltageAverages("ASC");
			//logger.debug("DESC");
			//functions.getVoltageAverages("DESC");
			
			functions.run("SELECT COUNT(*) AS VALUE FROM Voltage");
			functions.run("SELECT MAX(VLOTAGE) AS VALUE FROM Voltage");
			functions.run("SELECT MIN(VOLTAGE) AS VALUE FROM Voltage");
			break;
		case "averages" :
			MysqlFunctions averages = new MysqlFunctions(dataSource);
			averages.getAverages();
			averages.getMinMax();
			averages.addJune();
			averages.addJuly();
			averages.addAugust();
			averages.getAverages();
			break;
			
		default : 
			System.out.println("Stupid choice..  Choice: "+ choice);
			break;
		}

		/* Close the database connection */
		databaseManager.closeConnection(dataSource);
		logger.debug("Program completed");
	}

	static boolean tableExists(Connection dataSource, String tableName) {

		String sql = "SELECT * FROM " + tableName;

		try (PreparedStatement ps = dataSource.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
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
