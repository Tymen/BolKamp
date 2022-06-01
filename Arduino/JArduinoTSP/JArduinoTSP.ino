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

// Eind variables Sietse

int redPin = 12;
int greenPin = 13;
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

  if(location[0] == 7) {
    goHome(oldLocation[0], oldLocation[1]);
    location[0] = 0;
  }

  if(location[1] > 0 && location[0] > 0) {
    moveX = location[0] - oldLocation[0];
    moveY = location[1] - oldLocation[1];

    if(moveX == 0 && moveY > 0) {
      goRight(moveY);
    } else if(moveY == 0 && moveX > 0) {
      goUp(moveX);
    } else if(moveX == 0 && moveY < 0) {
      moveY = -moveY;
      goLeft(moveY);
    } else if(moveY == 0 && moveX < 0) {
      moveX = -moveX;
      goDown(moveX);
    }
    if(moveX < 0 && moveY < 0) {
      moveX = -moveX;
      moveY = -moveY;
      goDownLeft(moveX, moveY);
    } else if(moveX < 0 && moveY > 0){
      moveX = -moveX;
      goDownRight(moveX, moveY);
    } else if(moveX > 0 && moveY < 0){
      moveY = -moveY;
      if(moveY * 1500 > moveX * 900) {
        goLeftUp(moveX, moveY);
      } else {
        goUpLeft(moveX, moveY);
      }
    } else {
      goUpRight(moveX, moveY);
    }
//    if(moveY < 0) {
//      moveY = -moveY;
//      goLeft(moveY);
//    } else {
//      goRight(moveY);
//    }
    oldLocation[0] = location[0];
    oldLocation[1] = location[1];
    location[0] = 0;
    location[1] = 0;
    goPush();
  }
}

void goUpRight(int up, int right) {
  unsigned long startTime2;
  unsigned long newTime2;
  int delayLR = 1500 / right;
  for(int i = 1; i <= up; i++) {
    startTime = millis();
    newTime = millis();
    while (startTime + 900 > newTime) {
      digitalWrite(M1, LOW);
      analogWrite(E1, 255);
      newTime = millis();
      for(int i = 1; i <= right; i++) {
        startTime2 = millis();
        newTime2 = millis();
        while (startTime2 + delayLR > newTime2) {
          rbt.write(135);
          newTime2 = millis();
        }
      }
    }
  }
  analogWrite(E1, LOW);
  delay(80);
  analogWrite(E1, pauseMotor);
  rbt.write(90);
}

void goLeftUp(int up, int right) {
  int delayUD = 900 / up;
  
  startTime = millis();
  newTime = millis();
  while(startTime + (1500 * right) > newTime) {
    rbt.write(45);
    
    while(startTime + (900 * up) > newTime) {
      digitalWrite(M1, LOW);
      analogWrite(E1, 255);
      newTime = millis();
    }
    analogWrite(E1, pauseMotor);
    newTime = millis();
  }
  analogWrite(E1, LOW);
  delay(80);
  analogWrite(E1, pauseMotor);
  rbt.write(90);
}

void goUpLeft(int up, int left) {
  unsigned long startTime2;
  unsigned long newTime2;
  int delayLR = 1500 / left;
  startTime = millis();
  newTime = millis();
    
  for(int i = 1; i <= up; i++) {
    while (startTime + 900 > newTime) {
      digitalWrite(M1, LOW);
      analogWrite(E1, 255);
      for(int j = 1; j <= left; j++) {
        startTime2 = millis();
        newTime2 = millis();
        while (startTime2 + delayLR > newTime2) {
          rbt.write(45);
          newTime = millis();
        }
      }

      newTime = millis();
    }
  }
  analogWrite(E1, LOW);
  delay(80);
  analogWrite(E1, pauseMotor);
  rbt.write(90);
}

void goDownLeft(int down, int left) {
  int delayLR = 1500 / down;
  for(int i = 1; i <= down; i++) {
    startTime = millis();
    newTime = millis();
    while (startTime + 900 - delayLR > newTime) {
      digitalWrite(M1, HIGH);
      analogWrite(E1, 255);
      newTime = millis();
    for(int i = 1; i <= left; i++) {
      startTime = millis();
      newTime = millis();
      while (startTime + delayLR > newTime) {
          rbt.write(45);
          newTime = millis();
        }
      }
    }
  }
  analogWrite(E1, LOW);
  delay(80);
  analogWrite(E1, pauseMotor);
  rbt.write(90);
}

void goDownRight(int down, int right) {
  int delayLR = 1500 / down;
  for(int i = 1; i <= down; i++) {
    startTime = millis();
    newTime = millis();
    while (startTime + 900 - delayLR > newTime) {
      digitalWrite(M1, HIGH);
      analogWrite(E1, 255);
      newTime = millis();
    for(int i = 1; i <= right; i++) {
      startTime = millis();
      newTime = millis();
      while (startTime + delayLR > newTime) {
        rbt.write(135);
        newTime = millis();
    }
  }
    }
  }
  analogWrite(E1, LOW);
  delay(80);
  analogWrite(E1, pauseMotor);
  rbt.write(90);
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

  while (startTime + 850 > newTime) {
      digitalWrite(M2, HIGH);
      analogWrite(E2, 255);
      newTime = millis();
  }

  startTime = millis();

  while (startTime + 850 > newTime) {
    digitalWrite(M2, LOW);
    analogWrite(E2, 255);
    newTime = millis();
  }
  analogWrite(E2, 0);
  Serial.println(6);
}

void goHome(int locX, int locY) {
  goLeft(locY - 1);
  goDown(locX - 1);
}
