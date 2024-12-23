package com.java.busbookingsystem.bus.service;

import com.java.busbookingsystem.bus.model.Bus;
import com.java.busbookingsystem.bus.utils.IGenericCrudService;
import io.micrometer.common.lang.NonNull;


public interface IBusService extends IGenericCrudService<Long,   Bus> {
    Bus save(@NonNull Bus bus);

    String update(long id, Long entity);

    Bus findById(long id);

    String update(Bus entity);

    Bus updateBus(Long id, Bus bus);
}
