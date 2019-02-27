package com.ut.eet4250.hwk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InClass1 {
	
	private static Logger logger = LoggerFactory.getLogger(InClass1.class);
	private Connection dataSource;
	
	public InClass1() {
		
	}

	public InClass1(Connection dataSource){
		
		this.dataSource = dataSource;
	}
	
	/**
	 * 
	 */
	public void displayVoltageRowCount() {
		
		String sql = "SELECT COUNT(*) FROM Voltage";
		
		try(PreparedStatement ps = this.dataSource.prepareStatement(sql)){
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					double c = rs.getDouble("COUNT(*)");
					logger.debug("rowCount: {} " , new Object[] {c});
				}
				
			}
		}
		catch(SQLException e) {
			logger.error("displayVoltageRowCount: SQLSyntax Error: " + e.getLocalizedMessage());
		}	
	}
	
	/**
	 * 
	 * @return
	 */
	public double getDisplayVoltageRowCount() {
		
		String sql = "SELECT COUNT(*) FROM Voltage";
		double rows = 0.0;
		try(PreparedStatement ps = this.dataSource.prepareStatement(sql)){
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					rows = rs.getDouble("COUNT(*)");
				}
				
			}
		}
		catch(SQLException e) {
			logger.error("displayVoltageRowCount: SQLSyntax Error: " + e.getLocalizedMessage());
		}	
		return rows;
	}
	
	/* V = IR */
	public void computeVoltage() {
		double resistance = 0.0;
		
		String sql = "SELECT IFNULL(ROUND(SUM(Voltage)/Sum(Current),4), 0) AS I FROM Voltage WHERE Current != ? LIMIT 100";
		try(PreparedStatement ps = this.dataSource.prepareStatement(sql)){
			ps.setInt(1, 0);
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					resistance = rs.getDouble("I");
					logger.debug("Resistance: " + resistance);
				}
				
			}
		}
		catch(SQLException e) {
			logger.error("displayVoltageRowCount: SQLSyntax Error: " + e.getLocalizedMessage());
		}	
	}
	
	public void reverse() {
		
	
		String sql = "SELECT Upper(Reverse(ObjectName)) AS Name FROM Object";
		try(PreparedStatement ps = this.dataSource.prepareStatement(sql)){
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					String name = rs.getString("Name");
					logger.debug("Reversed: " + name);
				}
				
			}
		}
		catch(SQLException e) {
			logger.error("displayVoltageRowCount: SQLSyntax Error: " + e.getLocalizedMessage());
		}	
	}
}
