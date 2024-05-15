package gatemate.consumer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import gatemate.entities.Aircraft;
import gatemate.entities.Flight;
import gatemate.services.AircraftService;
import gatemate.services.FlightService;


@Component
public class FlightDataConsumer {
    private static final String QUEUE_NAME = "flight-data";

    private final ObjectMapper objectMapper;
    private final FlightService flightService;
    private final AircraftService aircraftService;

    public FlightDataConsumer(ObjectMapper objectMapper, FlightService flightService, AircraftService aircraftService) {
        this.objectMapper = objectMapper;
        this.flightService = flightService;
        this.aircraftService = aircraftService;
    }
    

    @RabbitListener(queuesToDeclare = @Queue(value = QUEUE_NAME, durable = "false"))
    public void receiveMessage(String message) {
        System.out.println("Received message from queue: ");

        try {
            // Convert the JSON string message to a Map
            Map<String, Object> messageMap = objectMapper.readValue(message, HashMap.class);

            Object data = messageMap.get("data");
            if (data instanceof List) {
                List<Map<String, Object>> dataList = (List<Map<String, Object>>) data;

                // Iterate through each dictionary in the list

                for (Map<String, Object> dataMap : dataList) {
                    System.out.println("Flight dictionary: " + dataMap);


                    Map<String, Object> flight_dic= (Map<String, Object>) dataMap.get("flight");
                    String flightNumber = (String) flight_dic.get("number");
                    String flightIata = (String) flight_dic.get("iata");

                    Map<String, Object> airline_dic= (Map<String, Object>) dataMap.get("airline");
                    String airline = (String) airline_dic.get("name");

                    Map<String, Object> departure_dic= (Map<String, Object>) dataMap.get("departure");
                    String departure = (String) departure_dic.get("airport");
                    String departure_time = (String) departure_dic.get("scheduled");

                    Map<String, Object> arrival_dic= (Map<String, Object>) dataMap.get("arrival");
                    String arrival = (String) arrival_dic.get("airport");
                    String arrival_time = (String) arrival_dic.get("scheduled");

                    String flightStatus = (String) dataMap.get("flight_status");

                    Map<String, Object> aircraft = (Map<String, Object>) dataMap.get("aircraft");

                    String aircrafttype = null;
                    Flight flight = new Flight();

                    Aircraft air = new Aircraft(); 
                    
                    flight.setFlightNumber(flightNumber);
                    flight.setFlightIata(flightIata);
                    flight.setAirline(airline);
                    flight.setOrigin(departure);
                    flight.setDestination(arrival);
                    flight.setDepartureTime(departure_time);
                    flight.setArrivalTime(arrival_time);
                    flight.setStatus(flightStatus);

                    if (aircraft != null) {
                        aircrafttype = (String) aircraft.get("registration");
                        air = aircraftService.getAircraftInfo(aircrafttype);
                        if (air == null) {
                            air = new Aircraft();
                            air.setAircraftType(aircrafttype);
                            air = aircraftService.save(air);
                            flight.setAircraft(air);

                        }

                    }

                   
            
                    flightService.save(flight);
                    System.out.println("Flight saved ");

                }
            } else {
                System.err.println("Error: 'data' is not a list");
            }
            
        } catch (IOException e) {
            System.err.println("Error parsing message: " + e.getMessage());
        }
    }
}
