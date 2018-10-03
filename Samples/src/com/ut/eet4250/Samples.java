package com.ut.eet4250;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.connection.DatabaseManager;
import com.ut.eet4250.model.Instructor;

public class Samples {

	private static Logger logger = LoggerFactory.getLogger(Samples.class);

	public static void main(String[] args) {
		logger.debug("Program Starting");

		DatabaseManager databaseManager = new DatabaseManager("database.properties");
		Connection dataSource = databaseManager.establishConnection();
		if (null == dataSource) {
			System.exit(1);
		}

		/*
		 * List<Instructor> list = new ArrayList<>(); list.add(new
		 * Instructor("Anthony","Johnston","anthony.johnston@utoledo.edu"));
		 * 
		 * String sql =
		 * "INSERT INTO Course_Instructor (FirstName, LastName, Email) VALUES (?,?,?)";
		 * for(Instructor i : list) { try(PreparedStatement ps =
		 * dataSource.prepareStatement(sql)){ ps.setString(1, i.getFirstName());
		 * ps.setString(2, i.getLastName()); ps.setString(3, i.getEmail()); int
		 * rowsAffected = ps.executeUpdate(); try(ResultSet rs = ps.executeQuery()){
		 * while(rs.next()) { System.out.printf("%s\t\t%s\n", rs.getString("CourseNo"),
		 * rs.getString("CourseName")); } } } catch(Exception e) {
		 * logger.error("SQL Syntax Error: " + e.getLocalizedMessage()); } }
		 */
		int rows = 0;
		boolean first = true;
		logger.debug("Exists");
		String line = "";
		try (BufferedReader br = new BufferedReader(new FileReader("voltage.csv"))) {
			while ((line = br.readLine()) != null) {
				if (first) {
					first = false;
					continue;
				}
				String[] dataPacket = line.split(",");
				// inserts goes here
				/*
				 * dataPacket[0] = Date dataPacket[1] = ActivePower dataPacket[2] =
				 * ReactivePower dataPacket[3] = Current dataPacket[4] = Voltage dataPacket[5] =
				 * Tube Temperature
				 * 
				 */
				Timestamp timestamp = null;
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");

					Date parsedTimeStamp = dateFormat.parse(dataPacket[0]);
					timestamp = new Timestamp(parsedTimeStamp.getTime());
				} catch (ParseException e) {
					logger.debug(e.getLocalizedMessage());
				}

				/*
				 * logger.debug("Date: " + timestamp); logger.debug("Active Power: " +
				 * dataPacket[1]); logger.debug("Reactive Power: " + dataPacket[2]);
				 * logger.debug("Current : " + dataPacket[3]); logger.debug("Voltage: " +
				 * dataPacket[4]); logger.debug("Tube Temperature: " + dataPacket[5]);
				 */

				String sql = "INSERT INTO Voltage "
						+ " (EventDateTime, ActivePower, ReactivePower, Current, Voltage, TubeTemperature) "
						+ "  VALUES (?,?,?,?,?,?)";
				try (PreparedStatement ps = dataSource.prepareStatement(sql)) {
					ps.setTimestamp(1, timestamp);
					ps.setDouble(2, convertStringToDouble(dataPacket[1]));
					ps.setDouble(3, convertStringToDouble(dataPacket[2]));
					ps.setDouble(4, convertStringToDouble(dataPacket[3]));
					ps.setDouble(5, convertStringToDouble(dataPacket[4]));
					ps.setDouble(6, convertStringToDouble(dataPacket[5]));
					rows += ps.executeUpdate();
				} catch (Exception e) {
					logger.error(e.getLocalizedMessage());
				}
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getLocalizedMessage());
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage());
		}

		logger.debug("Rows added: " + rows);

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
			return 0d;
		}
	}

}
