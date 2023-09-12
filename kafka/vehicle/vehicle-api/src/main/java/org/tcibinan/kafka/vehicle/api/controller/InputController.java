package org.tcibinan.kafka.vehicle.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tcibinan.kafka.vehicle.api.service.InputProducer;
import org.tcibinan.kafka.vehicle.message.VehicleInput;

@RestController
@RequestMapping("/inputs")
public class InputController {

    private final InputProducer publisher;

    public InputController(final InputProducer publisher) {
        this.publisher = publisher;
    }

    @PostMapping
    public void create(final VehicleInput input) {
        publisher.produce(input);
    }
}
