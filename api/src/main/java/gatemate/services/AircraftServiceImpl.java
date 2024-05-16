package gatemate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gatemate.entities.Aircraft;
import gatemate.repositories.AircraftRepository;
import redis.clients.jedis.JedisPool;

@Service
public class AircraftServiceImpl implements AircraftService {

    @Autowired
    private final AircraftRepository aircraftRepository;
    private JedisPool pool;

    public AircraftServiceImpl(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
        this.pool = new JedisPool("redis", 6379);
    }

    @Override
    public Aircraft getAircraftInfo(String aircraftType) {

        return aircraftRepository.findByAircraftType(aircraftType);
    }
}
