package gatemate.entities;

import jakarta.persistence.CascadeType;
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
  private String status;
  @OneToOne(cascade = CascadeType.ALL)
  private Aircraft aircraft;
  @OneToOne(cascade = CascadeType.ALL)
  private AirportFlight origin;
  @OneToOne(cascade = CascadeType.ALL)
  private AirportFlight destination;
  @OneToOne(cascade = CascadeType.ALL)
  private LiveData liveData;
  private int price;
  private long updated;
}