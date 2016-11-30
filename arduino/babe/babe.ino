/*
 * Babduino - Arduino project to detect usage of a table football.
 * Copyright (C) 2016 Thomas Leplus
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
 
// Serial
const long BAUDS = 57600L; // 57600 bauds
const unsigned long SERIAL_DELAY = 100L; // 100 ms

// Vcc
const unsigned long VREF_DELAY = 2L; // 2 ms

// Pins
const int LED = 13;
const int SENSOR = A0;
const int VCC = 255;

// Sampling
const unsigned int THRESHOLD = 32;
const unsigned int READINGS = 8192; // 2^13
const unsigned long MAX = 4294966272L; // 2^32 - 2^10

// Timing
unsigned long count = 0L;
const unsigned long LOOPS = 600L; // 600 =~ 20 min

void setup() {
  Serial.begin(BAUDS);
  pinMode(SENSOR, INPUT);
  pinMode(LED, OUTPUT);
  count = 0L;
}

void loop() {
  if (count++ % LOOPS == 0) {
    unsigned long vcc = getVcc();
    sendValue(VCC, vcc);
  }
  unsigned long value = getValue(SENSOR);
  if (value > 0) {
    sendValue(SENSOR, value);
  }
}

void sendValue(int sensor, unsigned long value) {
  unsigned long clock = millis();
  digitalWrite(LED, HIGH);
  Serial.print(sensor, DEC);
  Serial.print(",");
  Serial.print(value, DEC);
  Serial.print(",");
  Serial.print(clock, DEC);
  Serial.println();
  delay(SERIAL_DELAY);
  digitalWrite(LED, LOW);
}

unsigned long getVcc() {
  unsigned long result = 0L;
  // Read 1.1V reference against AVcc
  ADMUX = _BV(REFS0) | _BV(MUX3) | _BV(MUX2) | _BV(MUX1);
  delay(VREF_DELAY); // Wait for Vref to settle
  ADCSRA |= _BV(ADSC); // Convert
  while (bit_is_set(ADCSRA,ADSC));
  result = ADCL;
  result |= ADCH<<8;
  result = 1126400L / result; // Back-calculate AVcc in mV
  return result;
}

unsigned long getValue(unsigned int sensor) {
  unsigned long total = 0L;
  for (unsigned int i = 0; i < READINGS; i++) {
    int reading = analogRead(sensor);
    if (reading > THRESHOLD) {
      total += reading;
      if (total > MAX) {
        break;
      }
    }
  }
  return total;
}

