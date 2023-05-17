import paho.mqtt.client as mqtt
from struct import pack
from struct import unpack
from time import sleep
 
TOPIC = "alerta/#" #Especifica o tópico

#Função chamada quando a conexão for realizada
def on_connect(client, data, rc, properties=None):
    client.subscribe([(TOPIC,0)]) #Realiza a subscrição 

#Função chamada quando uma nova mensagem for gerada no tópico
def on_message(client, userdata, msg):
    v = unpack(">H",msg.payload)[0]
    
    if v == 1:
        print("TemperaturaAlta")

    if v == 2:
        print("AumentoRepentino")

client = mqtt.Client(client_id = 'Alarms', protocol = mqtt.MQTTv31) #Cria o cliente Alarms

client.on_connect = on_connect
client.on_message = on_message

client.connect("127.0.0.1", 1883) #Conecta ao broker

print("-----------\nALARMS\n-----------")
 
client.loop_forever() #Permanece conectado