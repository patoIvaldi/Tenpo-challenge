# Tenpo-challenge

## Instrucciones de instalación:

### - Opción A:
1) Ir a www.dockerhub...

2) descargarse las imagenes

3) instalarse docker desktop en caso de no tenerlo desde www.docler.

4) con el sigueinte comando agregar las imagenes de dcoker y pongalas a correr

5) Importe el siguiente postman collection:
  [Tenpo-challenge.postman_collection.zip](https://github.com/patoIvaldi/Tenpo-challenge/files/10998374/Tenpo-challenge.postman_collection.zip)

6) Ejecute los endpoints para probarlo.
  
  ![image](https://user-images.githubusercontent.com/52920765/225828021-a3284d9e-bb45-4b7f-a4eb-e267211c6d22.png)



### - Opción B (en caso de querer debuguear el código):
1) Frene la imágen del proyecto en docker.

2) Descarguese el repositorio en su máquina local y póngalo a correr.

  ![image](https://user-images.githubusercontent.com/52920765/225828235-2bba8117-c8a9-4ed7-9f26-aa4081313b66.png)

  ![image](https://user-images.githubusercontent.com/52920765/225827671-86eefcc0-9fa4-4602-a54b-c4b2184c58ce.png)

3) Importe el siguiente postman collection:
[Tenpo-challenge.postman_collection.zip](https://github.com/patoIvaldi/Tenpo-challenge/files/10998374/Tenpo-challenge.postman_collection.zip)


4) Ejecute los endpoints para probarlo.
  
  ![image](https://user-images.githubusercontent.com/52920765/225828021-a3284d9e-bb45-4b7f-a4eb-e267211c6d22.png)


## Información importante:
### En caso de querer modificar el valor que devuelve al servicio externo, modifique el siguiente valor por el deseado:
   
   ![image](https://user-images.githubusercontent.com/52920765/225784661-3c9e453e-ca2e-42dd-b81d-ed2c2cd5270c.png)


### Las imágenes en docker se crearon separadas a fines prácticos de poder probar el manejo de excepciones ante la caída de la BD o de la cola de mensajería (puede pausar/detener la imágen correspondiente).


### Se seteo un delay de 1 minuto para la persistencia en la BD, a fines prácticos para comprobar como encola las tareas RabbitMQ. En caso de querer modificar ese delay, ver el siguiente método en la clase Consumer.java:
  
  ![image](https://user-images.githubusercontent.com/52920765/225827392-3c3d4169-7f07-493c-a3b7-f1805b528393.png)

