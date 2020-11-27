package com.capgemini.fms.service;

import org.springframework.stereotype.Service;

import com.capgemini.fms.dto.ScheduleDto;
import com.capgemini.fms.exceptions.AirportException;
import com.capgemini.fms.exceptions.FlightException;
import com.capgemini.fms.exceptions.ScheduledIdNotFoundException;

@Service
public interface FlightScheduleService {
	
	public String addFlightSchedule(ScheduleDto schedule) throws AirportException, FlightException;
	
	public String cancelFlightSchedule(String schFlightId) throws ScheduledIdNotFoundException;
	
}
