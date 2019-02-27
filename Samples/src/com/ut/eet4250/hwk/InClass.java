package com.ut.eet4250.hwk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InClass {

	private static Logger logger = LoggerFactory.getLogger(InClass.class);
	
	private Connection connection;
	
	/**
	 * 
	 * @param connection
	 */
	public InClass(Connection connection) {
		this.connection = connection;
	}
	
	/* Default Construct */
	public InClass() {
		
	}
	
	/**
	 * Prints the rowid's of the first 100 records from the voltage table.
	 */
	public void printRowIdFirst100() {
		
		logger.debug("entering....printRowIdFirst100");
		String sql = "SELECT * FROM Voltage  "+
		"  JOIN Object Using(ObjectRowId) LIMIT 100";
		try(PreparedStatement ps = this.connection.prepareStatement(sql)){
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					int rowId = rs.getInt("RowId");
					int objectRowId = rs.getInt("ObjectRowId");
					logger.debug("rowId: {}, objectRowId: {}" , new Object[] {rowId, objectRowId});
				}
				
			}
		}
		catch(SQLException e) {
			logger.error("SQL Syntax Error: " + e.toString());
		}
	}
	
}
