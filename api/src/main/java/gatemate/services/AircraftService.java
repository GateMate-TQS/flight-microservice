package gatemate.services;

import gatemate.entities.Aircraft;

public interface AircraftService {
    public Aircraft getAircraftInfo(String aircraftType);
    public Aircraft save (Aircraft aircraft);
}
