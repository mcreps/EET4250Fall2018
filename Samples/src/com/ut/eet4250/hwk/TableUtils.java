package com.ut.eet4250.hwk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.slf4j.Logger;

public class TableUtils {

	/**
	 * 
	 * @param dataSource
	 * @param tableName
	 * @return
	 */
	public static boolean tableExists(Connection dataSource, String tableName) {

		String sql = "SELECT * FROM " + tableName + " LIMIT 1";

		try (PreparedStatement ps = dataSource.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 
	 * @param dataSource
	 * @param tables
	 * @param logger
	 * @return
	 */
	public static boolean tablesExists(Connection dataSource, List<String> tables, Logger logger) {

		logger.debug("Validating tables.  Tables: {} " + tables.toString());
		for (String table: tables) {
			boolean found = TableUtils.tableExists(dataSource, table);
			if (!found) {
				logger.debug(table + " Not found!");
				return false;
			}
		}
		return true;
	}
}
