# Arduino

Arduino sketches for the on-table sensor device that detects foosball activity
and sends events over its XBee radio.

- `babe/babe.ino`, `babe2/babe2.ino` — the sketches (two iterations of the
  firmware). Open in the Arduino IDE and flash to the board.

The messages emitted here are consumed by the [`../babduino/`](../babduino/)
host application.
