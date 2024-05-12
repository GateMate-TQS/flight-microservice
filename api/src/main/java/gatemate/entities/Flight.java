package gatemate.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "flights")
public class Flight {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String flightNumber;
  private String airline;
  private String origin;
  private String destination;
  private String departureTime;
  private String arrivalTime;
  private String status;
  private Aircraft aircraft;

  public Flight() {
  }

  public Flight(String flightNumber, String airline, String origin, String destination, String departureTime,
      String arrivalTime,
      String status,
      Aircraft aircraft) {
    this.flightNumber = flightNumber;
    this.airline = airline;
    this.origin = origin;
    this.destination = destination;
    this.departureTime = departureTime;
    this.arrivalTime = arrivalTime;
    this.status = status;
    this.aircraft = aircraft;
  }

  public Long getId() {
    return id;
  }

  public String getFlightNumber() {
    return flightNumber;
  }

  public String getAirline() {
    return airline;
  }

  public String getOrigin() {
    return origin;
  }

  public String getDestination() {
    return destination;
  }

  public String getDepartureTime() {
    return departureTime;
  }

  public String getArrivalTime() {
    return arrivalTime;
  }

  public String getStatus() {
    return status;
  }

  public Aircraft getAircraft() {
    return aircraft;
  }

  public void setFlightNumber(String flightNumber) {
    this.flightNumber = flightNumber;
  }

  public void setAirline(String airline) {
    this.airline = airline;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public void setDepartureTime(String departureTime) {
    this.departureTime = departureTime;
  }

  public void setArrivalTime(String arrivalTime) {
    this.arrivalTime = arrivalTime;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setAircraft(Aircraft aircraft) {
    this.aircraft = aircraft;
  }
}
