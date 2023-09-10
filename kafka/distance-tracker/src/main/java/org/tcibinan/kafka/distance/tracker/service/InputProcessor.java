package org.tcibinan.kafka.distance.tracker.service;

import org.tcibinan.kafka.vehicle.message.VehicleInput;
import org.tcibinan.kafka.vehicle.message.VehicleOutput;

public interface InputProcessor {

    VehicleOutput process(VehicleInput input);
}
