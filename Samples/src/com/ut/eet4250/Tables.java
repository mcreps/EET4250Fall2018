package com.ut.eet4250;

public class Tables {

	public static final String WATER_HEATER_TABLE = "CREATE TABLE `merl.crepsjr`.`Voltage` ( "
			  + " `RowId` INT UNSIGNED NOT NULL AUTO_INCREMENT, "
			  + " `EventDateTime` TIMESTAMP(6) NOT NULL, "
			  + " `ActivePower` DECIMAL(10,6) NOT NULL, "
			  + " `ReactivePower` DECIMAL(10,6) NOT NULL, " 
			  + " `Current` DECIMAL(10,6) NOT NULL, "
			  + " `Voltage` DECIMAL(10,6) NOT NULL, "
			  + " `TubeTemperature` DECIMAL(10,6) NULL, "
			  + " PRIMARY KEY (`RowId`), "
			  + " INDEX `EVENTDATE` (`EventDateTime` ASC))";
	
	
	
	
}
