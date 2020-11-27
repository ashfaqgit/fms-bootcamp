package com.capgemini.fms.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.fms.dto.FlightMessage;
import com.capgemini.fms.dto.ScheduleDto;
import com.capgemini.fms.exceptions.AirportException;
import com.capgemini.fms.exceptions.FlightException;
import com.capgemini.fms.exceptions.ScheduledIdNotFoundException;
import com.capgemini.fms.service.FlightScheduleServiceImpl;
import com.capgemini.fms.util.ScheduleConstants;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class FlightScheduleWebService {
	Logger logger = LoggerFactory.getLogger(FlightScheduleWebService.class);
	@Autowired
	private FlightScheduleServiceImpl flightService;
	
	@PostMapping(ScheduleConstants.SEARCH_URL)
	public FlightMessage addFlightSchedule(@RequestBody ScheduleDto schedule) throws 
	          AirportException, FlightException {
		logger.info(schedule.getFlightId());
		String scheduledFlightId = flightService.addFlightSchedule(schedule);
		return new FlightMessage("Schedule is created and the scheduled Flight Id is " + scheduledFlightId);
		
	}
	@DeleteMapping(ScheduleConstants.SEARCH_URL1)
	public FlightMessage cancelFlightSchedule(@PathVariable("schFlightId") String schFlightId) throws ScheduledIdNotFoundException{
		return new FlightMessage(flightService.cancelFlightSchedule(schFlightId));
	}
	
}
