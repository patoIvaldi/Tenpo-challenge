# Tenpo-challenge

## Instrucciones de instalación:

### - Opción A:
1) Ir a https://hub.docker.com/r/trixio95/tenpo_challenge .

2) Descargarse las 3 imágenes.

Nota: Instalarse docker desktop en caso de no tenerlo desde (https://docs.docker.com/desktop/install/windows-install/) .

3) Ejecute los siguientes 3 comandos para generar los contenedores correspondientes a estas 3 imágenes (asegúrese que queden ejecutándose):

```bash
docker run --hostname=8f884c69d577 --mac-address=02:42:ac:11:00:04 --env=PATH=/opt/openjdk-17/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin --env=JAVA_HOME=/opt/openjdk-17 --env=JAVA_VERSION=17-ea+14 -p 8080:8080 --link tenpo_postgres_DB:tenpo_postgres_DB --restart=no --runtime=runc -t -d spring-boot-docker

docker run --hostname=79d25c0d462f --mac-address=02:42:ac:11:00:03 --env=POSTGRES_USER=postgres --env=POSTGRES_PASSWORD=postgres --env=POSTGRES_DB=tenpodb --env=PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/lib/postgresql/15/bin --env=GOSU_VERSION=1.16 --env=LANG=en_US.utf8 --env=PG_MAJOR=15 --env=PG_VERSION=15.2-1.pgdg110+1 --env=PGDATA=/var/lib/postgresql/data --volume=/var/lib/postgresql/data -p 5432:5432 --restart=no --runtime=runc -d postgres

docker run --hostname=0ea5deae1aa6 --mac-address=02:42:ac:11:00:02 --env=PATH=/opt/rabbitmq/sbin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin --env=RABBITMQ_DATA_DIR=/var/lib/rabbitmq --env=RABBITMQ_VERSION=3.9.29 --env=RABBITMQ_PGP_KEY_ID=0x0A9AF2115F4687BD29803A206B73A36E6026DFCA --env=RABBITMQ_HOME=/opt/rabbitmq --env=HOME=/var/lib/rabbitmq --env=LANG=C.UTF-8 --env=LANGUAGE=C.UTF-8 --env=LC_ALL=C.UTF-8 --volume=/var/lib/rabbitmq -p 15672:15672 -p 5672:5672 --restart=always --label='org.opencontainers.image.ref.name=ubuntu' --label='org.opencontainers.image.version=20.04' --runtime=runc -d rabbitmq:3.9-management
```

4) Importe el siguiente postman collection:
  [Tenpo-challenge.postman_collection.zip](https://github.com/patoIvaldi/Tenpo-challenge/files/10998374/Tenpo-challenge.postman_collection.zip)

5) Ejecute los endpoints para probarlo.
  
  ![image](https://user-images.githubusercontent.com/52920765/225828021-a3284d9e-bb45-4b7f-a4eb-e267211c6d22.png)



### - Opción B (en caso de querer debuguear el código):
1) Frene la imagen del proyecto en docker (imagen:spring-boot-docker).

2) Descárguese el repositorio en su máquina local y póngalo a correr.

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

