#include <Servo.h>

// Begin variables Sietse (niet verplaatsen AUB, gebruik ik in eind verslag

Servo rbt;

int location[2] = { 0 , 0 };
int oldLocation[2] = { 1 , 1 };

boolean isPicked = false;
int writeAmount = 0;

int pauseMotor = 50;

unsigned long startTime;
unsigned long newTime;

int moveX;
int moveY;

int E1 = 5;
int M1 = 4;
int E2 = 6;
int M2 = 7;
boolean nood = false;

// Eind variables Sietse

int redPin = 13;
int greenPin = 12;
int yellowPin = 11;

void setup() {
  // put your setup code here, to run once:

  rbt.attach(9);
  rbt.write(90);
  
  pinMode(13, OUTPUT);
  pinMode(A0, INPUT);
  pinMode(9, OUTPUT);
  pinMode(12, OUTPUT);
  pinMode(11, OUTPUT);

  pinMode(M1, OUTPUT);
  pinMode(M2, OUTPUT);
  pinMode(E2, OUTPUT);
  pinMode(E1, OUTPUT);
  Serial.begin(9600);
}

void loop() {
  Serial.println(writeAmount);
  analogWrite(E1, pauseMotor);
  if(Serial.available() > 0) {
    if(location[0] == 0 && location[1] == 0) {
    writeAmount = 0;
    location[0] = Serial.read();
  } else if(location[1] == 0 && location[0] > 0) {
    location[1] = Serial.read();
  }
  }

  if(location[0] == 999) {
      noodStop();
      location[0] = 0;
      nood = true;
  } else if(location[0] == 555) {
      hardReset();
      nood = false;
  }


  if(!nood){
      if(location[1] > 0 && location[0] > 0) {
        moveX = location[0] - oldLocation[0];
        moveY = location[1] - oldLocation[1];

        if(moveX < 0) {
          moveX = -moveX;
          goDown(moveX);
        } else {
          goUp(moveX);
        }
        if(moveY < 0) {
          moveY = -moveY;
          goLeft(moveY);
        } else {
          goRight(moveY);
        }
        oldLocation[0] = location[0];
        oldLocation[1] = location[1];
        location[0] = 0;
        location[1] = 0;
        goPush();
      }
  }
}

void noodStop() {
  analogWrite(E1, 0);
  analogWrite(E2, 0);
  rbt.write(90);
  Serial.println(202);
}

void hardReset() {

}

void goUp(int up) {
  for(int i = 1; i <= up; i++) {
    startTime = millis();
    newTime = millis();
    while (startTime + 900 > newTime) {
      digitalWrite(M1, LOW);
      analogWrite(E1, 255);
      newTime = millis();
    }
  }
  analogWrite(E1, LOW);
  delay(80);
  analogWrite(E1, pauseMotor);
}

void goDown(int down) {
  for(int i = 1; i <= down; i++) {
    startTime = millis();
    newTime = millis();
    while (startTime + 500 > newTime) {
      digitalWrite(M1, HIGH);
      analogWrite(E1, 200);
      newTime = millis();
    }
  }
  digitalWrite(M1, LOW);
  delay(80);
  analogWrite(E1, pauseMotor);
}

void goLeft(int left) {
    for(int i = 1; i <= left; i++) {
     startTime = millis();
     newTime = millis();
     while (startTime + 1500 > newTime) {
        rbt.write(45);
       newTime = millis();
     }
  }
  rbt.write(90);
}

void goRight(int right) {
  for(int i = 1; i <= right; i++) {
    startTime = millis();
    newTime = millis();
    while (startTime + 1500 > newTime) {
      rbt.write(135);
      newTime = millis();
    }
  }
  rbt.write(90);
}

void goPush() {
  startTime = millis();
  newTime = millis();
  analogWrite(E1, pauseMotor);

  while (startTime + 750 > newTime) {
      digitalWrite(M2, HIGH);
      analogWrite(E2, 255);
      newTime = millis();
  }

  startTime = millis();

  while (startTime + 750 > newTime) {
    digitalWrite(M2, LOW);
    analogWrite(E2, 255);
    newTime = millis();
  }
  analogWrite(E2, 0);
  Serial.println(6);
}
