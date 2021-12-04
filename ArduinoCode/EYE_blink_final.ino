  #define SENSE A0

  void setup()
  {
    Serial.begin(9600);
    pinMode(LED_BUILTIN,OUTPUT);
  }

  void loop()
  {
    if(digitalRead(SENSE))
    {
      digitalWrite(LED_BUILTIN,HIGH);
        Serial.println("1");
        delay(3000);
      }
    
        else
        {
        digitalWrite(LED_BUILTIN,LOW);
        Serial.println("0");
        delay(1000);
        }
    
    }
