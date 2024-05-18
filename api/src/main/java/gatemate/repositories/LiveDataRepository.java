package gatemate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gatemate.entities.LiveData;

@Repository
public interface LiveDataRepository extends JpaRepository<LiveData, Long> {
}