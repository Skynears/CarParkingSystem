import socket
import cv2
import urllib

ID_KIT = ESP32
server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
host = "192.168.2.250"
port = 80
s.connect((host,port))
msg=s.recv(1024)
