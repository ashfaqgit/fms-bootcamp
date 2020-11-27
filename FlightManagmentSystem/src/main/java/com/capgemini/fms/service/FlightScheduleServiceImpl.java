package com.capgemini.fms.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.fms.dao.AirportDao;
import com.capgemini.fms.dao.FlightDao;
import com.capgemini.fms.dao.ScheduledFlightDao;
import com.capgemini.fms.dto.ScheduleDto;
import com.capgemini.fms.entity.Airport;
import com.capgemini.fms.entity.Flight;
import com.capgemini.fms.entity.ScheduledFlight;
import com.capgemini.fms.exceptions.AirportException;
import com.capgemini.fms.exceptions.FlightException;
import com.capgemini.fms.exceptions.ScheduledIdNotFoundException;
import com.capgemini.fms.util.ScheduleConstants;

@Service("myservice")
@Transactional
public class FlightScheduleServiceImpl implements FlightScheduleService {

	@Autowired
	private FlightDao flightDao;
	@Autowired
	private AirportDao airportDao;
	@Autowired
	private ScheduledFlightDao scheduledflightDao;

	@Override
	public String addFlightSchedule(ScheduleDto schedule)
			throws AirportException, FlightException {
	
			String scheduledFlightId = schedule.getFlightId() + schedule.getArrivalTime();

			ScheduledFlight scheduledflight = new ScheduledFlight();
			scheduledflight.setScheduledFlightId(scheduledFlightId);

			scheduledflight.setAvailableSeats(schedule.getAvailableSeats());
			scheduledflight.setArrivalTime(schedule.getArrivalTime());
			scheduledflight.setMinutes(schedule.getMinutes());
			scheduledflight.setDepartureTime(schedule.getArrivalTime().plusMinutes(schedule.getMinutes()));
			scheduledflight.setScheduleStatus(schedule.getScheduleStatus());
			scheduledflight.setFare(schedule.getFare());
			Airport srcairport = null;
			srcairport = airportDao.getAirport(schedule.getSrcAirport());
			if (srcairport == null)
				throw new AirportException(ScheduleConstants.AIRPORT_NOT_AVAILABLE);
			Airport dstairport = null;
			dstairport = airportDao.getAirport(schedule.getDstAirport());
			if (dstairport == null)
				throw new AirportException(ScheduleConstants.AIRPORT_NOT_AVAILABLE);
			Flight flight = null;
			flight = flightDao.getFlight(schedule.getFlightId());
			if (flight == null)
				throw new FlightException(ScheduleConstants.FLIGHT_NOT_AVAILABLE);
			scheduledflight.setSourceAirport(srcairport);
			scheduledflight.setDestinationAirport(dstairport);
			scheduledflight.setFlight(flight);
			ScheduledFlight scheduledFlight = scheduledflightDao.save(scheduledflight);

			return scheduledFlight.getScheduledFlightId();
	
	}

	@Override
	public String cancelFlightSchedule(String schFlightId) throws ScheduledIdNotFoundException {
		if (schFlightId == null)
			throw new ScheduledIdNotFoundException("Enter flight Id");
		Optional<ScheduledFlight> scheduleFlight = scheduledflightDao.findById(schFlightId);
		if (!scheduleFlight.isPresent())
			throw new ScheduledIdNotFoundException(ScheduleConstants.SCH_ID_NOT_FOUND);
		else {
		    scheduledflightDao.deleteById(schFlightId);
		}
		return "Schedule Cancelled Successfully and the Schedule Id is " + schFlightId;
	}

	
		
}