#include <Servo.h>

Servo rbt;

unsigned long startTime;
unsigned long newTime;

int E1 = 5;
int M1 = 4;
int E2 = 6;
int M2 = 7;
int movableAmount = 0;
int amountMoved = 0;

void setup() {
  // put your setup code here, to run once:

  rbt.attach(9);
  rbt.write(90);
  
  pinMode(13, OUTPUT);
  pinMode(A0, INPUT);
  pinMode(9, OUTPUT);

  pinMode(M1, OUTPUT);
  pinMode(M2, OUTPUT);
  pinMode(E2, OUTPUT);
  pinMode(E1, OUTPUT);
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  
  movableAmount = Serial.read();
  int delayAmount = map(movableAmount, 0, 100, 0, 5000);
  if(movableAmount == 101) {
    if(amountMoved > 100) {
      amountMoved = 100;
    }
    int downDelay = map(amountMoved, 0, 100, 0, 5000);
    digitalWrite(M2, LOW);
    analogWrite(E2, 255);
    delay(downDelay);
    analogWrite(E2, 0);
    amountMoved = 0;
  } else if(movableAmount == 102) {
      digitalWrite(M2, HIGH);
      analogWrite(E2, 255);
  } else if(movableAmount == 103) {
      digitalWrite(M2, LOW);
      analogWrite(E2, 255); 
  } else if(movableAmount == 104) {
      analogWrite(E2, 0);
  } else if(movableAmount == 105) {
    digitalWrite(M1, HIGH);
    analogWrite(E1, 155);
  } else if(movableAmount == 106) {
    digitalWrite(M1, LOW);
    analogWrite(E1, 255);
  } else if(movableAmount == 107) {
    digitalWrite(M1, LOW);
    delay(500);
    digitalWrite(E1, 0);
    rbt.write(90);
  } else if(movableAmount == 111) {
    startTime = millis();
    newTime = millis();
    while (startTime + 1500 > newTime) {
      rbt.write(45);
      newTime = millis();
    }
    rbt.write(90);
    
    
  } else if(movableAmount == 222) {
    startTime = millis();
    newTime = millis();
    while (startTime + 1500 > newTime) {
      rbt.write(135);
      newTime = millis();
    }
    rbt.write(90);
  }
}
