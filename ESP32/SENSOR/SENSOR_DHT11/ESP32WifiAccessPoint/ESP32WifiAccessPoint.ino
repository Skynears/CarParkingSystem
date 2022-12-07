// ---------------------------------------------------------------------------------------
//
// Simple code to setup the ESP32 as access point (device used for tests: ESP32-WROOM-32D).
//
// Written by mo thunderz (last update: 05.12.2021)
//
// ---------------------------------------------------------------------------------------

#include <WiFi.h>                                     // needed to connect to setup an accesspoint

// SSID and password that are going to be used for the Access Point you will create -> DONT use the SSID/Password of your router:
const char* ssid = "ESP32-WROOVER";
const char* password = "123456";

// Configure IP addresses of the local access point
IPAddress local_IP(192,168,2,200);
IPAddress gateway(192,168,2,1);
IPAddress subnet(255,255,255,0);

void setup() {
  Serial.begin(115200);                               // init serial port for debugging
 
  Serial.print("Setting up Access Point ... ");
  Serial.println(WiFi.softAPConfig(local_IP, gateway, subnet) ? "Ready" : "Failed!");

  Serial.print("Starting Access Point ... ");
  Serial.println(WiFi.softAP(ssid, password) ? "Ready" : "Failed!");

  Serial.print("IP address = ");
  Serial.println(WiFi.softAPIP());
  
}

void loop() {
  
}
