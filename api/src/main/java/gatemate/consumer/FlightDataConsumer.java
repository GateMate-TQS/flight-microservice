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
import gatemate.entities.AirportFlight;
import gatemate.entities.Flight;
import gatemate.entities.Seats;
import gatemate.repositories.FlightRepository;

@Component
public class FlightDataConsumer {
    private static final String QUEUE_NAME = "flight-data";

    private final ObjectMapper objectMapper;
    private final FlightRepository flightRepository;

    public FlightDataConsumer(ObjectMapper objectMapper, FlightRepository flightRepository) {
        this.objectMapper = objectMapper;
        this.flightRepository = flightRepository;

    }

    @RabbitListener(queuesToDeclare = @Queue(value = QUEUE_NAME, durable = "false"))
    public void receiveMessage(String message) {
        System.out.println("Received message from queue: ");

        Long currentTime = System.currentTimeMillis();
        Long updateThreshold = currentTime - 30 * 60 * 1000;

        // apagar voos antigos
        List<Flight> oldFlights = flightRepository.findByUpdatedLessThan(updateThreshold);
        for (Flight flight : oldFlights) {
            flightRepository.delete(flight);
        }
        System.out.println("Old flights deleted");

        try {
            // Convert the JSON string message to a Map
            Map<String, Object> messageMap = objectMapper.readValue(message, HashMap.class);

            Object data = messageMap.get("data");
            if (data instanceof List) {
                List<Map<String, Object>> dataList = (List<Map<String, Object>>) data;

                System.out.println("Data received: " + dataList);

                // Iterate through each dictionary in the list

                for (Map<String, Object> dataMap : dataList) {
                    Map<String, Object> flight_dic = (Map<String, Object>) dataMap.get("flight");
                    String flightNumber = (String) flight_dic.get("number");
                    String flightIata = (String) flight_dic.get("iata");

                    Map<String, Object> airline_dic = (Map<String, Object>) dataMap.get("airline");
                    String airline = (String) airline_dic.get("name");

                    String status = (String) dataMap.get("flight_status");

                    Map<String, Object> aircraft_dic = (Map<String, Object>) dataMap.get("aircraft");

                    String aircraftType;

                    if (aircraft_dic == null) {
                        aircraftType = "Boeing 737-800";
                    } else {
                        aircraftType = (String) aircraft_dic.get("registration");
                    }

                    Map<String, Object> departure_dic = (Map<String, Object>) dataMap.get("departure");
                    String departureIata = (String) departure_dic.get("iata");
                    String departureIcao = (String) departure_dic.get("icao");
                    String departureAiportName = (String) departure_dic.get("airport");
                    String departureTerminal = (String) departure_dic.get("terminal");
                    String departureGate = (String) departure_dic.get("gate");
                    Object departureDelayObj = departure_dic.get("delay");
                    int departureDelay = departureDelayObj == null ? 0 : (int) departureDelayObj;
                    // int departureDelay = departure_dic.get("delay") == "null" ? 0 : (int)
                    // departure_dic.get("delay");
                    String departureScheduled = (String) departure_dic.get("scheduled");
                    String departureEstimated = (String) departure_dic.get("estimated");
                    String departureActual = (String) departure_dic.get("actual");

                    Map<String, Object> arrival_dic = (Map<String, Object>) dataMap.get("arrival");
                    String arrivalIata = (String) arrival_dic.get("iata");
                    String arrivalIcao = (String) arrival_dic.get("icao");
                    String arrivalAiportName = (String) arrival_dic.get("airport");
                    String arrivalTerminal = (String) arrival_dic.get("terminal");
                    String arrivalGate = (String) arrival_dic.get("gate");
                    Object arrivalDelayObj = arrival_dic.get("delay");
                    int arrivalDelay = arrivalDelayObj == null ? 0 : (int) arrivalDelayObj;
                    String arrivalScheduled = (String) arrival_dic.get("scheduled");
                    String arrivalEstimated = (String) arrival_dic.get("estimated");
                    String arrivalActual = (String) arrival_dic.get("actual");

                    Seats seats = new Seats();
                    seats.setMaxRows(30);
                    seats.setMaxCols(6);
                    seats.setOccuped("");

                    Aircraft aircraft = new Aircraft();
                    aircraft.setAircraftType(aircraftType);
                    aircraft.setSeats(seats);

                    AirportFlight departure = new AirportFlight();
                    departure.setIata(departureIata);
                    departure.setIcao(departureIcao);
                    departure.setName(departureAiportName);
                    departure.setTerminal(departureTerminal);
                    departure.setGate(departureGate);
                    departure.setDelay(departureDelay);
                    departure.setScheduled(departureScheduled);
                    departure.setEstimated(departureEstimated);
                    departure.setActual(departureActual);

                    AirportFlight arrival = new AirportFlight();
                    arrival.setIata(arrivalIata);
                    arrival.setIcao(arrivalIcao);
                    arrival.setName(arrivalAiportName);
                    arrival.setTerminal(arrivalTerminal);
                    arrival.setGate(arrivalGate);
                    arrival.setDelay(arrivalDelay);
                    arrival.setScheduled(arrivalScheduled);
                    arrival.setEstimated(arrivalEstimated);
                    arrival.setActual(arrivalActual);

                    // se o voo ja existir
                    Flight existingFlight = flightRepository.findByFlightIata(flightIata);

                    if (existingFlight != null) {
                        existingFlight.setFlightNumber(flightNumber);
                        existingFlight.setAirline(airline);
                        existingFlight.setStatus(status);
                        existingFlight.setAircraft(aircraft);
                        existingFlight.setOrigin(departure);
                        existingFlight.setDestination(arrival);
                        existingFlight.setUpdated(currentTime);

                        flightRepository.save(existingFlight);
                        System.out.println("Flight updated");
                    } else {
                        Flight flight = new Flight();
                        flight.setFlightNumber(flightNumber);
                        flight.setFlightIata(flightIata);
                        flight.setAirline(airline);
                        flight.setStatus(status);
                        flight.setAircraft(aircraft);
                        flight.setOrigin(departure);
                        flight.setDestination(arrival);
                        flight.setUpdated(currentTime);

                        flightRepository.save(flight);
                        System.out.println("Flight saved");
                    }
                }
            } else {
                System.err.println("Error: 'data' is not a list");
            }

            System.out.println("Message processed");

        } catch (IOException e) {
            System.err.println("Error parsing message: " + e.getMessage());
        }
    }
}
