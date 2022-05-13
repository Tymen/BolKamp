boolean ledOn = false;
boolean ledOn2 = false;

void setup() {
  // put your setup code here, to run once:
  pinMode(13, OUTPUT);
  pinMode(A0, INPUT);
  pinMode(9, OUTPUT);
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
    int potValue = analogRead(A0);
    int percent = map(potValue, 0, 1023, 0, 100);
    String percString = String(percent);
    Serial.println(percent);
    delay(100);
    int buttonValue = Serial.read();
      if(buttonValue == 11) {
        if(ledOn2) {
          digitalWrite(9, LOW);
          ledOn2 = false;
        } else {
          digitalWrite(9, HIGH);
          ledOn2 = true;
        }
      } else if(buttonValue == 1) {
        if(ledOn) {
          digitalWrite(13, LOW);
          ledOn = false;
        } else {
          digitalWrite(13, HIGH);
          ledOn = true;
        }
      }
}
