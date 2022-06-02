#include <Servo.h>

// Begin variables Sietse (niet verplaatsen AUB, gebruik ik in eind verslag

Servo rbt;

int location[2] = { 0 , 0 };
int oldLocation[2] = { 1 , 1 };

boolean isPicked = false;
int writeAmount = 0;

int pauseMotor = 50;

const int btnPin = 3;
int buttonState = 0;

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

int redPin = 12;
int greenPin = 13;
int yellowPin = 11;

void setup() {
  // put your setup code here, to run once:

  rbt.attach(9);
  rbt.write(90);

  pinMode(btnPin, INPUT);
  
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
  hardReset();
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

  
    checkStop();

    if(location[1] > 0 && location[0] > 0) {
      if(nood) {
          checkStop();
          location[0] = 0;
          location[1] = 0;
          nood = true;
      } else {
      
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
    } else if(moveX < 0 && moveY < 0) {
      moveX = -moveX;
      moveY = -moveY;
      if(moveY * 1500 > moveX * 500) {
        goLeftDown(moveY, moveX);
      } else {
        goDownLeft(moveX, moveY);
      }
    } else if(moveX < 0 && moveY > 0){
      moveX = -moveX;
      moveY = -moveY;
      if(moveY * 1500 > moveX * 500) {
        goRightDown(moveY, moveX);
      } else {
        goDownRight(moveX, moveY);
      }
    } else if(moveX > 0 && moveY < 0){
      moveY = -moveY;
      if(moveY * 1500 > moveX * 900) {
        goLeftUp(moveY, moveX);
      } else {
        goUpLeft(moveX, moveY);
      }
    } else {
      if (moveY * 1500 > moveX * 900) {
        goRightUp(moveY, moveX);
      } else {
        goUpRight(moveX, moveY);
      }
    }
    oldLocation[0] = location[0];
    oldLocation[1] = location[1];
    location[0] = 0;
    location[1] = 0;
    goPush();
    }
  }
}

void resetVars() {
  location[0] = 0;
  location[1] = 0;
  oldLocation[0] = 1;
  oldLocation[1] = 1;
  
  isPicked = false;
  writeAmount = 0;
  
  buttonState = 0;
  
  moveX = 0;
  moveY = 0;
  
  nood = false;
}

void checkStop() {
  
  if(Serial.read() == 3) {
      nood = true;
      location[0] = 0;
      noodStop();
  }else if(Serial.read() == 4) {
      Serial.println(203);
      hardReset();
  }
}

void noodStop() {
  analogWrite(E1, 0);
  analogWrite(E2, 0);
  rbt.write(90);
  Serial.println(202);
  
}

void hardReset() {
    
    while (digitalRead(btnPin) == LOW) {
      rbt.write(45);
    }
    rbt.write(90);
    
    resetVars(); // dan is boolean nood leeg en kan ie naar beneden

for(int i = 1; i <= 5; i++) {
    startTime = millis();
    newTime = millis();
    while (startTime + 500 > newTime) {
        digitalWrite(M1, HIGH);
        analogWrite(E1, 200);
        newTime = millis();
    }
  }
  digitalWrite(M1, LOW);
    
//    goDown(5);
}

void goUpRight(int up, int right) {
  startTime = millis();
  newTime = millis();
  
  while (startTime + (up * 900) > newTime) {
    digitalWrite(M1, LOW);
    analogWrite(E1, 255);
    
    while (startTime + (1500 * right) > newTime) {
      rbt.write(135);
      newTime = millis();
    }
    rbt.write(90);
    newTime = millis();
  }
  
  analogWrite(M1, LOW);
  delay(80);
  analogWrite(E1, pauseMotor);
}

void goRightUp(int right, int up) {
  startTime = millis();
  newTime = millis();
  while (startTime + (1500 * right) > newTime) {
    rbt.write(135);
    
    while (startTime + (up * 900) > newTime) {
      digitalWrite(M1, LOW);
      analogWrite(E1, 255);
      newTime = millis();
    }
    
    analogWrite(M1, LOW);
    delay(80);
    analogWrite(E1, pauseMotor);
    newTime = millis();
  }
  
  rbt.write(90);
}

void goLeftUp(int left, int up) {  
  startTime = millis();
  newTime = millis();
  
  while(startTime + (1500 * left) > newTime) {
    rbt.write(45);
    
    while(startTime + (900 * up) > newTime) {
      digitalWrite(M1, LOW);
      analogWrite(E1, 255);
      newTime = millis();
    }
    
    analogWrite(M1, LOW);
    delay(80);
    analogWrite(E1, pauseMotor);
    newTime = millis();
  }
  
  rbt.write(90);
}

void goUpLeft(int up, int left) {
  startTime = millis();
  newTime = millis();
    
  while (startTime + (900 * up) > newTime) {
    digitalWrite(M1, LOW);
    analogWrite(E1, 255);
    
    while (startTime + (left * 1500) > newTime) {
      rbt.write(45);
      newTime = millis();
    }
    
    rbt.write(90);
    newTime = millis();
  }

  digitalWrite(M1, LOW);
  delay(80);
  analogWrite(E1, pauseMotor);
}

void goDownLeft(int down, int left) {
  startTime = millis();
  newTime = millis();
  
  while (startTime + (500 * down) > newTime) {
    digitalWrite(M1, HIGH);
    analogWrite(E1, 200);
    
    while (startTime + (left * 1500) > newTime) {
        rbt.write(45);
        newTime = millis();
    }
    
    rbt.write(90);
    newTime = millis();
  }
  
  digitalWrite(M1, LOW);
  delay(80);
  analogWrite(E1, pauseMotor);
}

void goLeftDown(int left, int down) {
  startTime = millis();
  newTime = millis();
  
  while(startTime + (1500 * left) > newTime) {
    rbt.write(45);
    
    while(startTime + (500 * down) > newTime) {
      digitalWrite(M1, HIGH);
      analogWrite(E1, 200);
      newTime = millis();
    }
    digitalWrite(M1, LOW);
    delay(80);
    analogWrite(E1, pauseMotor);
    newTime = millis();
  }
  
  rbt.write(90);
}

void goDownRight(int down, int right) {
  startTime = millis();
  newTime = millis();
  
  while (startTime + (900 * down) > newTime) {
    digitalWrite(M1, HIGH);
    analogWrite(E1, 255);
    
    while (startTime + (1500 * right) > newTime) {
      rbt.write(135);
      newTime = millis();
    }

    rbt.write(90);
    newTime = millis();
  }
  
  digitalWrite(M1, LOW);
  delay(80);
  analogWrite(E1, pauseMotor);
}

void goRightDown(int right, int down) {
  startTime = millis();
  newTime = millis();
  
  while (startTime + (1500 * right) > newTime) {
    rbt.write(135);
    
    while (startTime + (down * 900) > newTime) {
        digitalWrite(M1, HIGH);
        analogWrite(E1, 255);
        newTime = millis();
    }
    
    digitalWrite(M1, LOW);
    delay(80);
    analogWrite(E1, pauseMotor);
    newTime = millis();
  }
  rbt.write(90);
}

void goUp(int up) {
  for(int i = 1; i <= up; i++) {
    startTime = millis();
    newTime = millis();
    while (startTime + 900 > newTime) {
      checkStop();
      if(!nood){
        digitalWrite(M1, LOW);
        analogWrite(E1, 255);
        newTime = millis();
      }
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
      checkStop();
      if(nood == false){
        digitalWrite(M1, HIGH);
        analogWrite(E1, 200);
        newTime = millis();
      }
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
        checkStop();
        if(!nood){
          rbt.write(45);
          newTime = millis();
        }
     }
  }
  rbt.write(90);
}

void goRight(int right) {
  for(int i = 1; i <= right; i++) {
    startTime = millis();
    newTime = millis();
    while (startTime + 1500 > newTime) {
      checkStop();
      if(!nood){
      rbt.write(135);
      newTime = millis();
      }
    }
  }
  rbt.write(90);
}

void goPush() {
  startTime = millis();
  newTime = millis();
  analogWrite(E1, pauseMotor);
  checkStop();

  while (startTime + 1900 > newTime) {
      checkStop();
      if(!nood){
        digitalWrite(M2, HIGH);
        analogWrite(E2, 255);
        newTime = millis();
      }
  }

  startTime = millis();

  while (startTime + 1940 > newTime) {
    checkStop();
    if(!nood){
      digitalWrite(M2, LOW);
      analogWrite(E2, 255);
      newTime = millis();
    }
  }
  analogWrite(E2, 0);
  Serial.println(6);
}

void goHome(int locX, int locY) {
  goLeft(locY - 1);
  goDown(locX - 1);
}
