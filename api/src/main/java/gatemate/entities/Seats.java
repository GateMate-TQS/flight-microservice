package gatemate.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seats")
public class Seats {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String maxRows;
  private String maxCols;
  private String occuped;

  public Seats(String maxRows, String maxCols, String occuped) {
    this.maxRows = maxRows;
    this.maxCols = maxCols;
    this.occuped = occuped;
  }
}
