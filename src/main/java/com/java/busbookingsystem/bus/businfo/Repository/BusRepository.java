package com.java.busbookingsystem.bus.businfo.Repository;


import com.java.busbookingsystem.bus.businfo.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRepository extends JpaRepository<Bus,Long> {
}
