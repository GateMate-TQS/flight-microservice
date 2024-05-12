package gatemate.controllers;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gatemate.entities.Flight;
import gatemate.services.FlightService;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class FlightController {
  private final FlightService flightService;
}
