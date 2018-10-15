package com.ut.eet4250.hwk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MysqlFunctions {

	private static Logger logger = LoggerFactory.getLogger(MysqlFunctions.class);
	
	private Connection dataSource = null;
	
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
		
		logger.debug("entering: getVoltageAverages" );
		String sql = "SELECT AVG(Voltage) AS AVERAGE FROM Voltage GROUP BY EventDateTime ORDER BY AVERAGE " + op;
		try(PreparedStatement ps = this.dataSource.prepareStatement(sql)){
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					logger.debug(op+" AVERAGE: " + rs.getString("AVERAGE"));
				}
			}
		}
		catch(Exception e) {
			logger.error("SQL Syntax Error: " + e.getLocalizedMessage());
		}
	}
	
	public void run(String sql) {
		logger.debug(sql);
	}
}
