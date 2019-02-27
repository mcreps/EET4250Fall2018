package com.ut.eet4250.hwk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lab {

	private static Logger logger = LoggerFactory.getLogger(Lab.class);
	
	private Connection connection;
	
	/**
	 * 
	 * @param connection
	 */
	public Lab(Connection connection) {
		if (null == connection) {
			throw new IllegalArgumentException("Database connection cannot be null!");
		}
		this.connection = connection;
	}
	
	/**
	 * 
	 */
	public Lab() {
		
	}
	
	/**
	 * 
	 * @throws SQLException
	 */
	public void avgSumMaxLab() throws SQLException {
		String sql = "SELECT ObjectRowId, AVG(Voltage) AS A, MAX(Voltage) M, SUM(Voltage) S FROM Voltage GROUP BY ObjectRowId";
		ResultSet rs = getResultSet(sql);
		while(rs.next()) {
			double objectId = rs.getInt("ObjectRowId");
			double avg = rs.getDouble("A");
			double v = rs.getDouble("S");
			double m = rs.getDouble("M");
			logger.debug("Data: {} " + objectId + ", " + avg + ", " + v + ", " + m);
		}
	}
	
	/**
	 * 
	 * @param sql
	 * @return
	 */
	private ResultSet getResultSet(String sql) {
		logger.debug("entering....getResultSet");
		logger.debug("sql: {} ", new Object[] {sql});
		ResultSet rs = null;
		try {
			PreparedStatement ps = this.connection.prepareStatement(sql);
			rs = ps.executeQuery();
		}
		catch(SQLException e) {
			logger.error("SQL Syntax Error: " + e.toString());
		}
		return rs;
	}
}
