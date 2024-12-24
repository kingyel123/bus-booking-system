package com.java.busbookingsystem.bus.service;

import com.java.busbookingsystem.bus.model.Bus;
import com.java.busbookingsystem.bus.model.BusDTO;
import com.java.busbookingsystem.bus.utils.IGenericCrudService;
import io.micrometer.common.lang.NonNull;


public interface IBusService extends IGenericCrudService<Bus, BusDTO> {

}
