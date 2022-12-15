//Adicionar as bibliotecas necessárias
#include <Firebase_ESP_Client.h>
#include "time.h"
#include <WiFi.h>
#include <WiFiUdp.h>
#include "DHT.h"
#include <Arduino.h>

#define DHTPIN 15 // PINO PARA O SENSOR DHT11
#define DHTPIN 18 // PINO PARA O SENSOR InfraRed 1
#define DHTPIN 25 // PINO PARA O SENSOR InfraRed 2
#define DHTPIN 27 // PINO PARA O SENSOR InfraRed 3

// LCD DISPLAY

#define DHTPIN 32
#define DHTPIN 33
#define DHTPIN 34
#define DHTPIN 35


#define DHTTYPE DHT11
DHT dht(DHTPIN, DHTTYPE);
#define LED_BUILTIN 2   // Set the GPIO pin where you connected your test LED or comment this line out if your dev board has a built-in LED


//Adicionar a generação de tokens para processar informação.
#include "addons/TokenHelper.h"
#include "addons/RTDBHelper.h"

//Definir o SSID e Password da rede
#define WIFI_SSID "IPVC-ESTG"
#define WIFI_PASSWORD "12345678"

//Indicar os dados para aceder a Firebase
#define API_KEY "AIzaSyCoOLWLBkD-jCueuvQ-E9Nldpw_NivUtUY"
#define DATABASE_URL "https://car-parking-system-264e1-default-rtdb.europe-west1.firebasedatabase.app/"
#define USER_EMAIL "carsystemparking@ipvc.pt"
#define USER_PASSWORD "ProgramacaoMovel"
#define DATABASE_SECRET "cpiEnYUT1dcrcYI25HaAdOO6p0VuwHJu4ngpoDgH"

//Definir Firebase objects
FirebaseData fbdo;
FirebaseAuth auth;
FirebaseConfig config;

//Definição de variaveis
bool taskCompleted = false;
float temperature;
float humidity;
String uid;							// Variavel do USER UID
String databasePath;				// Database caminho principal
//Database nós
String tempPath = "/Temperatura";
String humPath = "/Humidade";
String statusPath = "/Status";
String timePath = "/timestamp";
String parentPath;

int timestamp;
FirebaseJson json;

const char* ntpServer = "pool.ntp.org";

//Timer variables (send new readings every three minutes)
unsigned long sendDataPrevMillis = 0;
unsigned long timerDelay = 180000;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
void initWiFi(){
  
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to ");
  Serial.println(WIFI_SSID);
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print('.');
    delay(1000);
  }
  Serial.println("");
  Serial.println("ESP32 connected to WiFi with IP address: ");
  Serial.println(WiFi.localIP());
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//Function that gets current epoch time
unsigned long getTime() {
  time_t now;
  struct tm timeinfo;
  if (!getLocalTime(&timeinfo)) {
    //Serial.println("Failed to obtain time");
    return(0);
  }
  time(&now);
  return now;
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


void setup(){
	
  Serial.begin(115200);

  
  pinMode(15,INPUT);    //*********************MUDAR DE ACORDO COM O PIN DO MOCKUP***************

  //Iniciar DHT
  dht.begin();
  
  // Iniciar WIFI
  initWiFi();
  
  configTime(0, 0, ntpServer);

// Assign the api key (required)
  config.api_key = API_KEY;

  // Assign the user sign in credentials
  auth.user.email = USER_EMAIL;
  auth.user.password = USER_PASSWORD;

  // Assign the RTDB URL (required)
  config.database_url = DATABASE_URL;
  


  Firebase.reconnectWiFi(true);
  fbdo.setResponseSize(4096);

  // Assign the callback function for the long running token generation task
  config.token_status_callback = tokenStatusCallback; //see addons/TokenHelper.h
  config.max_token_generation_retry = 5;

  // Inicio a library com Firebase authen and config
  Firebase.begin(&config, &auth);

  // Recolha do user UID 
  Serial.println("Getting User UID");
  while ((auth.token.uid) == "") {
    Serial.print('.');
    delay(1000);
  }
  // Print user UID
  uid = auth.token.uid.c_str();
  Serial.print("User UID: ");
  Serial.print(uid);

// Update database path
  databasePath = "/UsersData/" + uid + "/readings";

}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


void loop(){

  // Send new readings to database
  if (Firebase.ready() && (millis() - sendDataPrevMillis > timerDelay || sendDataPrevMillis == 0)){
    sendDataPrevMillis = millis();

    //Get current timestamp
    timestamp = getTime();
    Serial.print ("time: ");
    Serial.println (timestamp);

    parentPath= databasePath + "/" + String(timestamp);

    json.set(tempPath.c_str(), String(dht.readTemperature()));
    json.set(humPath.c_str(), String(dht.readHumidity()));
    if (digitalRead(33)== LOW)
  {
    json.set(statusPath.c_str(), String("Ocupado"));
  }
  else 
  {   
    json.set(statusPath.c_str(), String("Livre"));   
  }
    json.set(timePath, String(timestamp));
    Serial.printf("Set json... %s\n", Firebase.RTDB.setJSON(&fbdo, parentPath.c_str(), &json) ? "ok" : fbdo.errorReason().c_str());
  }

  delay(60000);
}