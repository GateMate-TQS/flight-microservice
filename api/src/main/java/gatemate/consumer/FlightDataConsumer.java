package gatemate.consumer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import gatemate.entities.Aircraft;
import gatemate.entities.AirportFlight;
import gatemate.entities.Flight;
import gatemate.entities.Seats;
import gatemate.repositories.FlightRepository;

@Component
public class FlightDataConsumer {
    private static final String QUEUE_NAME = "flight-data";

    private static final Logger logger = LoggerFactory.getLogger(FlightDataConsumer.class);

    private final ObjectMapper objectMapper;
    private final FlightRepository flightRepository;

    public FlightDataConsumer(ObjectMapper objectMapper, FlightRepository flightRepository) {
        this.objectMapper = objectMapper;
        this.flightRepository = flightRepository;
    }

    @RabbitListener(queuesToDeclare = @Queue(value = QUEUE_NAME, durable = "false"))
    public void receiveMessage(String message) {
        logger.info("Received message from queue");

        Long currentTime = System.currentTimeMillis();
        Long updateThreshold = currentTime - 30 * 60 * 1000;

        deleteOldFlights(updateThreshold);

        try {
            Map<String, Object> messageMap = parseMessage(message);
            processMessageData(messageMap, currentTime);
            logger.info("Message processed");
        } catch (IOException e) {
            logger.error("Error parsing message: {}", e.getMessage());
        }
    }

    private void deleteOldFlights(Long updateThreshold) {
        List<Flight> oldFlights = flightRepository.findByUpdatedLessThan(updateThreshold);
        for (Flight flight : oldFlights) {
            flightRepository.delete(flight);
        }
        logger.info("Old flights deleted");
    }

    private Map<String, Object> parseMessage(String message) throws IOException {
        return objectMapper.readValue(message, HashMap.class);
    }

    private void processMessageData(Map<String, Object> messageMap, Long currentTime) {
        Object data = messageMap.get("data");
        if (data instanceof List) {
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) data;
            logger.info("Data received: {}", dataList);

            for (Map<String, Object> dataMap : dataList) {
                Flight flight = createFlightFromData(dataMap, currentTime);
                saveOrUpdateFlight(flight);
                logger.info("Flights created/updated");
            }
        } else {
            logger.error("Error: 'data' is not a list");
        }
    }

    private Flight createFlightFromData(Map<String, Object> dataMap, Long currentTime) {
        String flightNumber = getString(dataMap, "flight", "number");
        String flightIata = getString(dataMap, "flight", "iata");
        String airline = getString(dataMap, "airline", "name");
        String status = (String) dataMap.get("flight_status");

        Aircraft aircraft = createAircraft(dataMap);
        AirportFlight departure = createAirportFlight(dataMap, "departure");
        AirportFlight arrival = createAirportFlight(dataMap, "arrival");

        Flight flight = new Flight();
        flight.setFlightNumber(flightNumber);
        flight.setFlightIata(flightIata);
        flight.setAirline(airline);
        flight.setStatus(status);
        flight.setAircraft(aircraft);
        flight.setOrigin(departure);
        flight.setDestination(arrival);
        flight.setUpdated(currentTime);

        return flight;
    }

    private Aircraft createAircraft(Map<String, Object> dataMap) {
        Map<String, Object> aircraftDic = (Map<String, Object>) dataMap.get("aircraft");
        String aircraftType = (aircraftDic == null) ? "Boeing 737-800" : (String) aircraftDic.get("registration");

        Seats seats = new Seats();
        seats.setMaxRows(30);
        seats.setMaxCols(6);
        seats.setOccuped("");

        Aircraft aircraft = new Aircraft();
        aircraft.setAircraftType(aircraftType);
        aircraft.setSeats(seats);

        return aircraft;
    }

    private AirportFlight createAirportFlight(Map<String, Object> dataMap, String flightType) {
        Map<String, Object> flightDic = (Map<String, Object>) dataMap.get(flightType);

        AirportFlight flight = new AirportFlight();
        flight.setIata((String) flightDic.get("iata"));
        flight.setIcao((String) flightDic.get("icao"));
        flight.setName((String) flightDic.get("airport"));
        flight.setTerminal((String) flightDic.get("terminal"));
        flight.setGate((String) flightDic.get("gate"));
        flight.setDelay(getInt(flightDic, "delay"));
        flight.setScheduled((String) flightDic.get("scheduled"));
        flight.setEstimated((String) flightDic.get("estimated"));
        flight.setActual((String) flightDic.get("actual"));

        return flight;
    }

    private void saveOrUpdateFlight(Flight flight) {
        Flight existingFlight = flightRepository.findByFlightIata(flight.getFlightIata());
        if (existingFlight != null) {
            updateExistingFlight(existingFlight, flight);
        } else {
            flightRepository.save(flight);
        }
    }

    private void updateExistingFlight(Flight existingFlight, Flight newFlight) {
        existingFlight.setFlightNumber(newFlight.getFlightNumber());
        existingFlight.setAirline(newFlight.getAirline());
        existingFlight.setStatus(newFlight.getStatus());
        existingFlight.setAircraft(newFlight.getAircraft());
        existingFlight.setOrigin(newFlight.getOrigin());
        existingFlight.setDestination(newFlight.getDestination());
        existingFlight.setUpdated(newFlight.getUpdated());

        flightRepository.save(existingFlight);
    }

    private String getString(Map<String, Object> dataMap, String parentKey, String key) {
        Map<String, Object> parentMap = (Map<String, Object>) dataMap.get(parentKey);
        return parentMap == null ? null : (String) parentMap.get(key);
    }

    private int getInt(Map<String, Object> dataMap, String key) {
        Object value = dataMap.get(key);
        return value == null ? 0 : (int) value;
    }
}
