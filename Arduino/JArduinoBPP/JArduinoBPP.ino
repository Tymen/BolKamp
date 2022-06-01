
#define A 2
#define B 3
#define C 4
#define D 5

int currentPos = 1;

void setup(){
  pinMode(A,OUTPUT);
  pinMode(B,OUTPUT);
  pinMode(C,OUTPUT);
  pinMode(D,OUTPUT);
  Serial.begin(9600);
}

void loop(){
  Serial.println(0);
  if(Serial.available() > 0){
    int doos = Serial.read();
    if(doos > 0 && doos <= 4) {
      box(doos);
    } else if(doos == 5) {
      Serial.println(currentPos);
    } else if(doos == 7) {
      box(1);
    }
  }
 }

 void box(int doos) {
    if(currentPos - doos == -1) {
      turnLeft();
    } else {
      if(currentPos - doos == -2 || currentPos - doos == 2) {
        turnRight(2);
      } else if(currentPos == 4 && doos == 1) {
        turnLeft();
      } else if(currentPos != doos) {
        turnRight(1);
      }
    }
    currentPos = doos;
    Serial.println(6);
 }
 
void turnRight(int quarters){
  int del = 1;
  for(int i = 1; i <= quarters * 128; i++) {
    write(0, 0, 0, 1);
    delay(del);
    write(0, 0, 1, 1);
    delay(del);
    write(0, 0, 1, 0);
    delay(del);
    write(0, 1, 1, 0);
    delay(del);
    write(0, 1, 0, 0);
    delay(del);
    write(1, 1, 0, 0);
    delay(del);
    write(1, 0, 0, 0);
    delay(del);
    write(1, 0, 0, 1);
    delay(del);
  }
}

void turnLeft() {
  int del = 1;
  for(int i = 1; i <= 128; i++) {
    write(1,0,0,0);
    delay(del);
    write(1,1,0,0);
    delay(del);
    write(0,1,0,0);
    delay(del);
    write(0,1,1,0);
    delay(del);
    write(0,0,1,0);
    delay(del);
    write(0,0,1,1);
    delay(del);
    write(0,0,0,1);
    delay(del);
    write(1,0,0,1);
    delay(del);
  }
}

void write(int a,int b,int c,int d) {
  digitalWrite(A,a);
  digitalWrite(B,b);
  digitalWrite(C,c);
  digitalWrite(D,d);
}
