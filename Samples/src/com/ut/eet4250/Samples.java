package com.ut.eet4250;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
		
		List<Instructor> list = new ArrayList<>();
		list.add(new Instructor("Anthony","Johnston","anthony.johnston@utoledo.edu"));
		
		String sql = "INSERT INTO Course_Instructor (FirstName, LastName, Email) VALUES (?,?,?)";
		for(Instructor i : list) {
			try(PreparedStatement ps = dataSource.prepareStatement(sql)){		
				ps.setString(1, i.getFirstName());
				ps.setString(2, i.getLastName());
				ps.setString(3, i.getEmail());
				int rowsAffected = ps.executeUpdate();
	/*			try(ResultSet rs = ps.executeQuery()){
					while(rs.next()) {
						System.out.printf("%s\t\t%s\n", rs.getString("CourseNo"), rs.getString("CourseName"));
					}
				}*/
			}
			catch(Exception e) {
				logger.error("SQL Syntax Error: " + e.getLocalizedMessage());
			}
		}
		
		/* Close the database connection */
		databaseManager.closeConnection(dataSource);
		logger.debug("Program completed");
	}

}
