package com.ut.eet4250.hwk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MysqlFunctions {

	private static Logger logger = LoggerFactory.getLogger(MysqlFunctions.class);

	private Connection dataSource = null;

	private double apMin;
	private double aPMax;
	private double rPMax;
	private double rPMin;
	private double minC;
	private double maxC;
	private double maxV;
	private double minV;
	private double maxT;
	private double minT;

	public MysqlFunctions() {

	}

	public MysqlFunctions(Connection dataSource) {
		this.dataSource = dataSource;
	}

	public Connection getDataSource() {
		return dataSource;
	}

	public void setDataSource(Connection dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MysqlFunctions [dataSource=");
		builder.append(dataSource);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * 
	 */
	public void getVoltageAverages(String op) {

		logger.debug("entering: getVoltageAverages");
		String sql = "SELECT AVG(Voltage) AS AVERAGE FROM Voltage GROUP BY EventDateTime ORDER BY AVERAGE " + op;
		try (PreparedStatement ps = this.dataSource.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					logger.debug(op + " AVERAGE: " + rs.getString("AVERAGE"));
				}
			}
		} catch (Exception e) {
			logger.error("SQL Syntax Error: " + e.getLocalizedMessage());
		}
	}

	public void run(String sql) {
		logger.debug(sql);
	}

	public double generateRandomNumber(double min, double max) {
		double x = (Math.random() * ((max - min) + 1)) + min;
		String number = String.format("%.6f", x);
		return Double.parseDouble(number);
	}

	/**
	 * 
	 */
	public void getAverages() {

		String sql = "SELECT AVG(ActivePower) AS A, Avg(ReactivePower) AS B, AVG(Voltage) AS C, "
				+ "  AVG(TubeTemperature) AS D,  Avg(Current) AS E " + "  FROM Voltage";
		try (PreparedStatement ps = this.dataSource.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					System.out.printf("ActivePower: %s\t\n", rs.getString("A"));
					System.out.printf("ReactivePower: %s\t\n", rs.getString("B"));
					System.out.printf("Voltage: %s\t\n", rs.getString("C"));
					System.out.printf("TubeTemperature: %s\t\n", rs.getString("D"));
					System.out.printf("Current: %s\t\n", rs.getString("E"));
				}
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}

	}
	
	public void getMinMax() {
		String sql = "SELECT Min(ActivePower) MinAp, Max(ActivePower) AS MaxAp, "
				+ " Min(ReactivePower) MinRp, Max(ReactivePower) AS MaxRp, "
				+ " Min(Current) MinC, Max(Current) AS MaxC, "
				+ " Min(Voltage) MinV, Max(Voltage) AS MaxV, "
				+ " Min(TubeTemperature) MinT, Max(TubeTemperature) AS MaxT "				
				+ " FROM Voltage";
		try (PreparedStatement ps = this.dataSource.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					this.apMin = rs.getDouble("MinAp");
					this.aPMax = rs.getDouble("MaxAp");
					this.rPMin = rs.getDouble("MinAp");
					this.rPMax = rs.getDouble("MaxAp");
					this.minC = rs.getDouble("MinC");
					this.maxC = rs.getDouble("MaxC");
					this.maxV = rs.getDouble("MaxV");
					this.minV = rs.getDouble("MinV");
					this.maxT = rs.getDouble("MaxT");
					this.minT = rs.getDouble("MinT");
				}
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
	}
	
	public void addJune() {
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2017);
		cal.set(Calendar.MONTH, 05);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		for (int i = 11; i <= 30; i++) {
			cal.set(Calendar.DAY_OF_MONTH, i);
			for (int j = 0; j < 24; j++) {
				cal.set(Calendar.HOUR_OF_DAY, j);
				Date today = cal.getTime();
				Timestamp now = new Timestamp(today.getTime());

				double activePower = generateRandomNumber(this.apMin, this.aPMax);
				double reactivePower = generateRandomNumber(this.rPMin, this.rPMax);
				double current = generateRandomNumber(this.minC, this.maxC);
				double voltage = generateRandomNumber(this.minV, this.maxV);
				double tubeTemp = generateRandomNumber(this.minT, this.maxT);

				// insert logic for june
				String sql = "INSERT INTO Voltage (EventDateTime, ActivePower, ReactivePower, Current, Voltage, TubeTemperature) "
						+ "  VALUES  (?, ?,?,?,?,?)";
				
				try(PreparedStatement ps = this.dataSource.prepareStatement(sql)){
					ps.setTimestamp(1, now);
					ps.setDouble(2, activePower);
					ps.setDouble(3, reactivePower);
					ps.setDouble(4, current);
					ps.setDouble(5, voltage);
					ps.setDouble(6, tubeTemp);
					ps.executeUpdate();
				}
				catch(Exception e) {
					logger.error(e.getLocalizedMessage());
				}
			}
		}
	}
	
	
	public void addJuly() {
	
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2017);
		cal.set(Calendar.MONTH, 05);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		cal.set(Calendar.MONTH, 06);
		for (int i = 1; i <= 30; i++) {
			cal.set(Calendar.DAY_OF_MONTH, i);
			for (int j = 0; j < 24; j++) {
				cal.set(Calendar.HOUR_OF_DAY, j);
				Date today = cal.getTime();
				Timestamp now = new Timestamp(today.getTime());
				// insert logic for july

				double activePower = generateRandomNumber(this.apMin, this.aPMax);
				double reactivePower = generateRandomNumber(this.rPMin, this.rPMax);
				double current = generateRandomNumber(this.minC, this.maxC);
				double voltage = generateRandomNumber(this.minV, this.maxV);
				double tubeTemp = generateRandomNumber(this.minT, this.maxT);

				String sql = "INSERT INTO Voltage (EventDateTime, ActivePower, ReactivePower, Current, Voltage, TubeTemperature) "
						+ "  VALUES  (?, ?,?,?,?,?)";
				
				try(PreparedStatement ps = this.dataSource.prepareStatement(sql)){
					ps.setTimestamp(1, now);
					ps.setDouble(2, activePower);
					ps.setDouble(3, reactivePower);
					ps.setDouble(4, current);
					ps.setDouble(5, voltage);
					ps.setDouble(6, tubeTemp);
					ps.executeUpdate();
				}
				catch(Exception e) {
					logger.error(e.getLocalizedMessage());
				}

			}
		}
	}
	
	public void addAugust() {
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2017);
		cal.set(Calendar.MONTH, 05);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		cal.set(Calendar.MONTH, 07);
		for (int i = 1; i <= 31; i++) {
			cal.set(Calendar.DAY_OF_MONTH, i);
			for (int j = 0; j < 24; j++) {
				cal.set(Calendar.HOUR_OF_DAY, j);
				Date today = cal.getTime();
				Timestamp now = new Timestamp(today.getTime());
				// insert logic for july

				double activePower = generateRandomNumber(this.apMin, this.aPMax);
				double reactivePower = generateRandomNumber(this.rPMin, this.rPMax);
				double current = generateRandomNumber(this.minC, this.maxC);
				double voltage = generateRandomNumber(this.minV, this.maxV);
				double tubeTemp = generateRandomNumber(this.minT, this.maxT);

				String sql = "INSERT INTO Voltage (EventDateTime, ActivePower, ReactivePower, Current, Voltage, TubeTemperature) "
						+ "  VALUES  (?, ?,?,?,?,?)";
				
				try(PreparedStatement ps = this.dataSource.prepareStatement(sql)){
					ps.setTimestamp(1, now);
					ps.setDouble(2, activePower);
					ps.setDouble(3, reactivePower);
					ps.setDouble(4, current);
					ps.setDouble(5, voltage);
					ps.setDouble(6, tubeTemp);
					ps.executeUpdate();
				}
				catch(Exception e) {
					logger.error(e.getLocalizedMessage());
				}

			}
		}
	}
}
