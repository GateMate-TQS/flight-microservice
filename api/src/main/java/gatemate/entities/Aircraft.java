package gatemate.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.HashMap;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Aircraft {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String aircraftType;
  private HashMap<String, int[][]> seats;

  public Aircraft(String aircraftType) {
    this.aircraftType = aircraftType;
    this.seats = new HashMap<String, int[][]>() {
      {
        int[][] firstClassSeats = new int[10][6];
        for (int i = 0; i < 10; i++) {
          for (int j = 0; j < 6; j++) {
            firstClassSeats[i][j] = 0;
          }
        }
        put("firstClass", firstClassSeats);

        int[][] businessClassSeats = new int[20][6];
        for (int i = 0; i < 20; i++) {
          for (int j = 0; j < 6; j++) {
            businessClassSeats[i][j] = 0;
          }
        }
        put("businessClass", businessClassSeats);

        int[][] economyClassSeats = new int[30][6];
        for (int i = 0; i < 30; i++) {
          for (int j = 0; j < 6; j++) {
            economyClassSeats[i][j] = 0;
          }
        }
        put("economyClass", economyClassSeats);
      }
    };
  }

  public String getAircraftType() {
    return aircraftType;
  }

  public HashMap<String, int[][]> getSeats() {
    return seats;
  }

  public void setAircraftType(String aircraftType) {
    this.aircraftType = aircraftType;
  }

  public void setSeats(HashMap<String, int[][]> seats) {
    this.seats = seats;
  }
}
