/*
    This sketch demonstrates how to set up a simple HTTP-like server.
    The server will set a GPIO pin depending on the request
      http://server_ip/gpio/0 will set the GPIO2 low,
      http://server_ip/gpio/1 will set the GPIO2 high
    server_ip is the IP address of the ESP8266 module, will be
    printed to Serial when the module is connected.
*/
#include <Ticker.h>
#include <WiFiUdp.h>
#include <ESP8266WiFi.h>
#ifndef UNIT_TEST
#include <Arduino.h>
#endif
#include <IRremoteESP8266.h>
#include <IRsend.h>
#define IR_LED 5 // ESP8266 GPIO pin to use. Recommended: 4 (D2).
#include <SoftwareSerial.h> // SoftwareSerial library (사용 안함) 
#include <dht11.h>
#define DHT11PIN 16// D1 PIN NUMBER : 2
Ticker flipper;
dht11 DHT11; // DHT 11을 사용
IRsend irsend(IR_LED);  // Set the GPIO to be used to sending the message.
unsigned char  pms[32]; // 32바이트 값을 넣기 위해 사용 , pms7003은 32바이트 값을 받아온다.
//밑부터 WIFI 통신을 위한 접속 단계
const char* ssid  = "AndroidHotspot2750";  // 공유기 ID
const char* password = "01095192750"; // PASSWORD
const char* host = "api.thingspeak.com"; // Thingspeak address
const char* writeAPIKey = "2L90HSLFYCQVFDNC"; // Private key
int cds = A0; // 조도센서 A0 연결
int PM10 = 37;
int cdsValue = 120;
int temperature = 0;
int humidity = 0;
int thehour;
WiFiServer server(80);
WiFiClient client; // wificlient  변수 명시
uint16_t onsignalfull[59] = {3164, 9854,  474, 1602,  504, 530,  556, 478,  502, 540,  502, 1570,  504, 532,  502, 536,  528, 508,  514, 520,  528, 504,  502, 530,  502, 530,  504, 538,  502, 538,  502, 544,  504, 542,  528, 1558,  504, 532,  502, 1578,  504, 538,  504, 538,  526, 1560,  502, 546,  502, 536,  504, 1582,  502, 1586,  504, 1578,  504, 532,  502};  // UNKNOWN 9987F8D9
uint16_t offsignalfull[59] = {3138, 9882,  474, 1610,  504, 540,  500, 538,  504, 540,  500, 1566,  502, 536,  506, 530,  502, 540,  502, 1594,  504, 1564,  506, 536,  502, 532,  502, 536,  504, 540,  502, 540,  502, 542,  504, 540,  502, 544,  502, 534,  502, 536,  504, 540,  502, 1584,  504, 542,  502, 1578,  508, 528,  502, 530,  502, 538,  504, 1570,  502};  // UNKNOWN 1035C9DA
uint16_t onsignalaver[59] = {3110, 9902,  500, 1596,  506, 536,  530, 504,  502, 540,  504, 1576,  504, 530,  504, 530,  502, 530,  528, 512,  522, 514,  502, 530,  504, 530,  502, 530,  502, 540,  500, 544,  504, 542,  530, 1558,  504, 544,  504, 1582,  502, 544,  504, 542,  502, 536,  506, 1566,  500, 536,  504, 1590,  502, 1570,  504, 540,  500, 532,  502};  // UNKNOWN 4A58A251
uint16_t offsignalaver[59] = {3138, 9866,  474, 1610,  530, 512,  506, 544,  504, 536,  504, 1586,  506, 530,  502, 530,  504, 534,  506, 1572,  528, 1540,  504, 530,  504, 534,  504, 544,  530, 512,  504, 538,  504, 534,  508, 526,  504, 532,  504, 534,  504, 532,  504, 534,  504, 1566,  528, 504,  504, 1578,  502, 536,  506, 536,  504, 536,  504, 1572,  528};  // UNKNOWN 1035C9DA
uint16_t onaircleaner[59] = {3114, 9940,  502, 1602,  536, 500,  504, 528,  530, 512,  528, 1548,  536, 502,  528, 506,  528, 508,  536, 1544,  530, 1536,  526, 508,  530, 504,  506, 536,  504, 532,  534, 502,  502, 532,  506, 530,  508, 538,  528, 508,  534, 504,  506, 528,  528, 512,  528, 506,  530, 508,  510, 1562,  536, 1536,  504, 536,  516, 518,  528};  // UNKNOWN FB766165
uint16_t offaircleaner[59] = {3194, 9820,  500, 1578,  536, 508,  528, 506,  528, 504,  504, 1584,  510, 530,  536, 502,  528, 508,  536, 1542,  530, 1548,  504, 532,  536, 508,  530, 512,  504, 542,  536, 508,  528, 510,  534, 510,  504, 532,  536, 506,  506, 532,  534, 1536,  528, 510,  536, 508,  504, 534,  534, 504,  502, 1584,  536, 506,  534, 510,  502};  // UNKNOWN A7CEC72B
uint16_t dehumidification[59] = {3186, 9844,  500, 1578,  538, 496,  532, 502,  530, 510,  528, 1554,  506, 534,  504, 534,  536, 514,  512, 522,  554, 478,  502, 530,  526, 506,  504, 1564,  504, 518,  554, 500,  554, 1522,  526, 1552,  536, 500,  504, 542,  536, 1558,  528, 506,  528, 1538,  526, 506,  504, 542,  534, 508,  532, 1560,  530, 1550,  534, 502,  504};  // UNKNOWN 5C44A393
unsigned int localPort = 2390;      // local port to listen for UDP packets
IPAddress timeServerIP; // time.nist.gov NTP server address
const char* ntpServerName = "time.nist.gov";

const int NTP_PACKET_SIZE = 48; // NTP time stamp is in the first 48 bytes of the message

byte packetBuffer[ NTP_PACKET_SIZE]; //buffer to hold incoming and outgoing packets

// A UDP instance to let us send and receive packets over UDP
WiFiUDP udp;

void setup() {
  irsend.begin();
  Serial.begin(9600);
  delay(10);

  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi connected");

  // Start the server
  server.begin();
  udp.begin(localPort);
  Serial.println("Server started");
  // Print the IP address
  Serial.println(WiFi.localIP());
    int thehour;
     flipper.attach(1, flip);
}

void loop() {
  WiFi.hostByName(ntpServerName, timeServerIP);
  sendNTPpacket(timeServerIP); // send an NTP packet to a time server
  delay(1000);
  int cb = udp.parsePacket();
  if (!cb) {
    Serial.println("no packet yet");
  }
  else {
    udp.read(packetBuffer, NTP_PACKET_SIZE); // read the packet into the buffer
    unsigned long highWord = word(packetBuffer[40], packetBuffer[41]);
    unsigned long lowWord = word(packetBuffer[42], packetBuffer[43]);
    unsigned long secsSince1900 = highWord << 16 | lowWord;
    const unsigned long seventyYears = 2208988800UL;
    unsigned long epoch = secsSince1900 - seventyYears;
    if ( ((epoch % 3600) / 60) < 10 ) {
      thehour = 0;
    }
    thehour = (epoch  % 3600) / 60; // print the minute (3600 equals secs per minute)
  }
  Serial.print(thehour);
  // wait ten seconds before asking for the time again
  delay(5000);

  if (thehour % 2 == 1) {
    if (Serial.available() >= 32) {    // 들어온 값이 32 btye 값이 들어오면 사용합니다.

      for (int j = 0; j < 32 ; j++) {    // pms 에 값들을 입력한다.
        pms[j] = Serial.read();
      }
      // 저희가 사용하는 PM_10 값은 15 16 번째 ! 기본 PMS7003 책자 확인하시면 32바이트에 무엇이 들어가는지 다나와있으실겁니다.
      if (pms[0] == 0x42) {   // 오류 검출, 첫 값이 0x42 라면
        PM10 = (pms[14] << 8)  | pms[15]; // Shift 연산으로 15번째 값이 왼쪽으로 8칸이동
        // PM_10 값은 15번째와 16번째로 들어옵니다. 15 16 형태를 만들기 위해서 8번 쉬프트연산 해준겁니다.
        delay(5000);
      }
      else {                  // 오류 검출 , 처음 들어오는 값이 0x42가 아니면, error
        Serial.println("error");
      }
    }
    int chk = DHT11.read(DHT11PIN);  // ckh 변수 선언입니다.
    //int cdsValue = (analogRead(cds)) * 500;
    temperature = DHT11.temperature;
    humidity = DHT11.humidity;
    Serial.print("CDS =  ");
    Serial.println(cdsValue);
    Serial.println("-----------");
    Serial.println("PM10 =");  // PM 10 값출력
    Serial.println(PM10);
    Serial.println("-----------");
    Serial.print("Temperature (°C): ");
    Serial.println((float)DHT11.temperature, 2);
    Serial.print("Humidity (%): ");
    Serial.println((float)DHT11.humidity, 2);
    Serial.println("----line-------");
    while (Serial.available())  //D1의 스택에러 방지용으로 만들었습니다.
    {
      Serial.read();
    }
    // make TCP connections
    WiFiClient client; // wificlient  변수 명시
    const int httpPort = 80;  // 포트 80  http 일겁니다.
    if (!client.connect(host, httpPort)) {
      return;
    }
    String url = "/update?key=";  // 일반적으로 저희가 사용하는 Thingspeak는 필드가 나뉘어져 있습니다.
    url += writeAPIKey;
    url += "&field1=";
    url += String(cdsValue);  // 학교 내의 조도 값
    url += "&field2=";
    url += String(PM10); // 학교 내의 미세먼지 값
    url += "&field3=";
    url += String(DHT11.temperature);  // 학교 내의 온도 값
    url += "&field4=";
    url += String(DHT11.humidity); // 학교 내의 습도 값
    url += "&field5=";
    url += String(cdsValue);  // 학교 내의 조도 값
    url += "&field6=";
    url += String(PM10); // 학교 내의 미세먼지 값
    url += "&field7=";
    url += String(DHT11.temperature);  // 학교 내의 온도 값
    url += "&field8=";
    url += String(DHT11.humidity); // 학교 내의 습도 값
    url += "\r\n";
    // Request to the server
    client.print(String("GET ") + url + " HTTP/1.1\r\n" +  // Client 연결 방식입니다.
                 "Host: " + host + "\r\n" +
                 "Connection: close\r\n\r\n");
    delay(50000);
    client.stop();
  }
  WiFiClient client = server.available();
  if (!client) {
    return;
  }

  // Wait until the client sends some data
  Serial.println("new client");
  while (!client.available()) {
    delay(1);
  }

  // Read the first line of the request
  String req = client.readStringUntil('\r');
  Serial.println(req);
  client.flush();

  // Match the request

  if (req.indexOf("/airhigh") != -1) { //탐구관 지하 B101 20명 이상
    for (int i = 0; i < 9; i++) {
      Serial.println("Temperature (°C): 25 Power : Good !");
      irsend.sendRaw(onsignalfull, 59, 38);  // Send a raw data capture at 38kHz.
      delay(40);
    }
  }
  else if (req.indexOf("/airmid") != -1) { //탐구관 지하 B101 20명 이하
    for (int i = 0; i < 9; i++) {
      Serial.println("Temperature (°C): 25 Power : Soso !");
      irsend.sendRaw(onsignalaver, 59, 38);  // Send a raw data capture at 38kHz.
      delay(40);
    }
  }

  else if (req.indexOf("/duston") != -1) { //탐구관 지하 B101 20명 이하
    for (int i = 0; i < 9; i++) {
      Serial.println("Aircleaner On");
      irsend.sendRaw(onaircleaner, 59, 38);  // Send a raw data capture at 38kHz.
      delay(40);
    }
  }
  else if (req.indexOf("/lighton") != -1) { //탐구관 지하 B101 20명 이하
    for (int i = 0; i < 9; i++) {
      Serial.println("Light On");
      delay(40);
    }
  }
  else if (req.indexOf("/cleanon") != -1) { //탐구관 지하 B101 20명 이하
    for (int i = 0; i < 9; i++) {
      
      Serial.print("Humidity (%) Controler On ");
      irsend.sendRaw(dehumidification, 59, 38);  // Send a raw data capture at 38kHz.
      delay(40);
    }
  }
  else if (req.indexOf("/turnoff") != -1) { //탐구관 지하 B101 20명 이하
    for (int i = 0; i < 9; i++) {
      irsend.sendRaw(offsignalaver, 59, 38);  // Send a raw data capture at 38kHz.
      Serial.print("All OFF ");
      delay(40);
    }
  }
  else {
    Serial.println("invalid request");
    client.stop();
    return;
  }
  client.flush();

  // Prepare the response
  String s = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n<!DOCTYPE HTML>\r\n<html>\r\n FIGHT!! ";
  s += "</html>\n";

  // Send the response to the client
  client.print(s);
  delay(1);
  Serial.println("Client disonnected");
  while (Serial.available())  //D1의 스택에러 방지용으로 만들었습니다.
  {
    Serial.read();
  }

  // The client will actually be disconnected
  // when the function returns and 'client' object is detroyed
}

void sendNTPpacket(IPAddress& address)
{
  memset(packetBuffer, 0, NTP_PACKET_SIZE);
  // Initialize values needed to form NTP request
  // (see URL above for details on the packets)
  packetBuffer[0] = 0b11100011;   // LI, Version, Mode
  packetBuffer[1] = 0;     // Stratum, or type of clock
  packetBuffer[2] = 6;     // Polling Interval
  packetBuffer[3] = 0xEC;  // Peer Clock Precision
  // 8 bytes of zero for Root Delay & Root Dispersion
  packetBuffer[12]  = 49;
  packetBuffer[13]  = 0x4E;
  packetBuffer[14]  = 49;
  packetBuffer[15]  = 52;

  // all NTP fields have been given values, now
  // you can send a packet requesting a timestamp:
  udp.beginPacket(address, 123); //NTP requests are to port 123
  udp.write(packetBuffer, NTP_PACKET_SIZE);
  udp.endPacket();
}

void flip(){
      Serial.println("*"+String(temperature)+"//"+String(humidity)+"//"+String(cdsValue)+"//"+String(PM10)+"*");
}
