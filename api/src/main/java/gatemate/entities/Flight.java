package gatemate.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "flights")
public class Flight {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String flightNumber;
  private String flightIata;
  private String airline;
  private String origin;
  private String destination;
  private String departureTime;
  private String arrivalTime;
  private String status;
  @OneToOne
  private Aircraft aircraft;

  public Flight(String flightNumber, String flightIata, String airline, String origin, String destination,
      String departureTime,
      String arrivalTime,
      String status,
      Aircraft aircraft) {
    this.flightNumber = flightNumber;
    this.flightIata = flightIata;
    this.airline = airline;
    this.origin = origin;
    this.destination = destination;
    this.departureTime = departureTime;
    this.arrivalTime = arrivalTime;
    this.status = status;
    this.aircraft = aircraft;
  }
}