package org.tcibinan.activemq.heartbeat.message;

import java.time.LocalDateTime;

public record Heartbeat(String service,
                        LocalDateTime dateTime) {
}
