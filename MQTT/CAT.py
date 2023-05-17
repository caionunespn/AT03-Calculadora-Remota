import paho.mqtt.client as mqtt
from struct import pack
from struct import unpack
 
TOPIC = "sensor/#" #Especifica o tópico
temp_values = []
average_values = []

def enviar_alerta(alerta):
    atr = "alerta/temperatura"#Tópico para identificação dos alertas

    payload = pack(">H",alerta)
    client.publish(atr,payload,qos=1) #Publica a mensagem de alerta

    return True

def calculate_average(temp):
    temp_values.append(temp)

    if len(temp_values) > 2: #Se tiver mais que 2 temperaturas no vetor, remove a mais antiga
        temp_values.pop(0) 
    
    if len(temp_values) == 2: #Calcula e média das 2 últimas temperaturas
        average_temp = sum(temp_values[-2:]) / 2
        print("Media:", average_temp)

        average_values.append(average_temp)

        if average_temp > 200: #Se a média for maior do que 200
            enviar_alerta(alerta=1) 

    if len(average_values) > 2: #Se tiver mais que 2 médias no vetor, remove a mais antiga
        average_values.pop(0) 

    if len(average_values) == 2:
        if (average_values[1] - average_values[0]) >= 5: #Se a última média for maior do que a média anterior em 5 graus ou mais
            enviar_alerta(alerta=2)
    
    return True

#Função chamada quando a conexão for realizada
def on_connect(client, data, rc, properties=None):
    client.subscribe([(TOPIC,0)]) #Realiza a subscrição 

#Função chamada quando uma nova mensagem for gerada no tópico
def on_message(client, userdata, msg):
    v = unpack(">H",msg.payload)[0]
    
    calculate_average(v) #Chama a função para calcular a média das temperaturas

client = mqtt.Client(client_id = 'CAT', protocol = mqtt.MQTTv31) #Cria o cliente CAT

client.on_connect = on_connect 
client.on_message = on_message

client.connect("127.0.0.1", 1883) #Conecta ao broker

print("-----------\nCAT\n-----------")

client.loop_forever() #Permanece conectado