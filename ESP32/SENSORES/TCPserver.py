import socket
import cv2
import urllib

ID_KIT = ESP32
server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
host = "192.168.2.150"
port = 80
server.bind((host, port))
server.listen(1)
q,addr = s.accept()
q.send(data)  
