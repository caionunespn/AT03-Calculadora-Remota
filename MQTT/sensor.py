import paho.mqtt.client as mqtt
from struct import pack
from random import randint
from time import sleep
 
SENSOR_ID = 1
 
tt = "sensor/temperatura" % () #Tópico para identificação
 
client = mqtt.Client(client_id = 'Sensor', protocol = mqtt.MQTTv31) #Cria um identificador
client.connect("127.0.0.1", 1883) #Conecta ao broker

print("-----------\nSENSOR\n-----------")
 
while True:
    
    t = randint(100,300) #Gera um valor de temperatura aleatório
    payload = pack(">H",t)

    client.publish(tt,payload,qos=0) #Publica essa temperatura 
    print(tt + "/" + str(t))
    
    sleep(10) #Repete a cada 60 segundos