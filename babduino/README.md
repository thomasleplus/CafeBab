# babduino

The Java host application (Maven) that receives foosball activity from the XBee
network and publishes it.

- `src/main/java/com/cafebab/app/` — the application:
  - `Main.java` — entry point / wiring.
  - `XBeeReader.java` — reads messages from the XBee serial link.
  - `QueueManager.java`, `TimeKeeper.java` — buffering and timing.
  - `RESTPublisher.java`, `TwitterPublisher.java` — publish measurements to a
    REST endpoint and to Twitter.
  - `PropertyManager.java` + `src/main/resources/*.properties` — configuration
    (`babduino.properties`, `twitter4j.properties`, `log4j.properties`).
- `src/main/java/com/cafebab/bo/Measure.java` — the measurement value object.
- `lib/` — bundled native/serial dependencies (RXTX, xbee-api) with
  `install.sh` to install them into the local Maven repository.

Build/run with the Maven wrapper (`./mvnw ...`); see `compile.sh` / `run.sh`.
