package org.tcibinan.kafka.distance.tracker.service;

import org.springframework.stereotype.Component;
import org.tcibinan.kafka.vehicle.message.VehicleInput;
import org.tcibinan.kafka.vehicle.message.VehicleOutput;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryInputProcessor implements InputProcessor {

    private final Map<String, VehicleState> states = Collections.synchronizedMap(new HashMap<>());

    @Override
    public VehicleOutput process(final VehicleInput input) {
        final VehicleState state = transit(states.get(input.id()), input);
        states.put(input.id(), state);
        return new VehicleOutput(input.id(), state.distance());
    }

    private VehicleState transit(final VehicleState state, final VehicleInput input) {
        if (state == null) {
            return new VehicleState(0d, input);
        }
        return new VehicleState(state.distance() + distanceBetween(state.input(), input), input);
    }

    private Double distanceBetween(final VehicleInput left, final VehicleInput right) {
        return Math.sqrt(Math.pow(left.x() - right.x(), 2) + Math.pow(left.y() - right.y(), 2));
    }

    private record VehicleState(Double distance, VehicleInput input) {
    }
}
