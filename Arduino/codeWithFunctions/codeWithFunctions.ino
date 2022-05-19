void setup() {
  // put your setup code here, to run once:

}

void loop() {
  if(Serial.available() > 0) {
  if(location[0] == 0 && location[1] == 0) {
    location[0] = Serial.read();
  } else if(location[1] == 0 && location[0] > 0) {
    location[1] = Serial.read();
  }
  }

  if(location[1] > 0 && location[0] > 0) {
    moveX = location[0] - oldLocation[0];
    moveY = location[1] - oldLocation[1];

    if(moveX < 0) {
      moveX = -moveX;
      goRight(moveX);
    } else {
      goLeft(moveX);
    }
    if(oldLocation[1] > location[1]) {
      moveY = -moveY;
      goDown(moveY);
    } else {
      goUp(moveY);
    }
    oldLocation[0] = location[0];
    oldLocation[1] = location[1];
    location[0] = 0;
    location[1] = 0;
  }
}

void goUp(int up) {
  for(int i = 0; i < down; i++) {
    startTime = millis();
    newTime = millis();
    while (startTime + 1000 > newTime) {
      digitalWrite(M1, LOW);
      analogWrite(E1, 50);
      newTime = millis();
    }
    analogWrite(E1, 0);
  }
}

void goDown(int down) {
  for(int i = 0; i < down; i++) {
    startTime = millis();
    newTime = millis();
    while (startTime + 1000 > newTime) {
      digitalWrite(M1, HIGH);
      analogWrite(E1, 50);
      newTime = millis();
    }
    analogWrite(E1, 0);
  }
}

void goLeft(int left) {
    for(int i = 0; i < left; i++) {
     startTime = millis();
     newTime = millis();
     while (startTime + 1000 > newTime) {
       digitalWrite(M1, HIGH);
       analogWrite(E1, 255);
       digitalWrite(greenPin, HIGH);
       newTime = millis();
     }
  }
  digitalWrite(greenPin, LOW);
  analogWrite(E1, 0);
}

void goRight(int right) {
    startTime = millis();
    newTime = millis();
    while (startTime + 1500 > newTime) {
      rbt.write(135);
      newTime = millis();
    }
    rbt.write(90);
  }
}
