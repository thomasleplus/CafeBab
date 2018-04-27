#include <Ethernet.h>
#include <SPI.h>
#include <Twitter.h>

const unsigned int LED = 13;
const unsigned int PIR = 2;
const unsigned int FREE_THRESHOLD = 600;
const unsigned int WARM_UP = 1000;
const unsigned int COOL_DOWN = 100;
const unsigned int BASE = 16;

const byte MAC[] = { 0x90, 0xA2, 0xDA, 0x0D, 0xA2, 0xA0 };

const char BUFFER[141];

const char* BACK = "I was off but now I'm back online. Sorry about that.";
const char* BUSY = "Sorry but I'm busy right now. Please check again later.";
const char* FREE = "Yeah I'm available! It's time to play :)";

const char* TOKEN = “YOUR-TWITTER-TOKEN-HERE”;

const Twitter twitter(TOKEN);

unsigned int countFree = 0;
boolean stateFree = true;

void setup() {
  pinMode(LED, OUTPUT);
  pinMode(PIR, INPUT);
  delay(WARM_UP);
  Serial.println("DHCP probe");
  if (Ethernet.begin(MAC) == 0) {
    for (;;); // wait forever
  }
  delay(WARM_UP);
  post(BACK);
}

void loop() {
  boolean sensorFree = readSensor();
  if (sensorFree) {
    if (!stateFree) {
      countFree++;
      if (countFree == FREE_THRESHOLD) {
        stateFree = true;
        postState();
      }
    }
  } else if (stateFree) {
    countFree = 0;
    stateFree = false;
    postState();
  } else {
    countFree = 0;
  }
}

boolean readSensor() {
  delay(COOL_DOWN);
  return digitalRead(PIR) == LOW;
}

void postState() {
  digitalWrite(LED, stateFree ? LOW : HIGH);
  post(stateFree ? FREE : BUSY);
}

void post(const char* msg) {
  const char* fullMsg = addTimeStamp(msg);
  if (twitter.post(fullMsg)) {
    const int status = twitter.wait(NULL);
  }
}

const char* addTimeStamp(const char* msg) {
  sprintf(BUFFER, "%s [%08lX]", msg, millis());
  return BUFFER;
}

