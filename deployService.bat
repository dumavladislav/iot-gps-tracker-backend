
call mvn clean
call mvn install

REM Copy files to server
ssh dumavla@192.168.1.53 "rm -f -r iot-gps-tracker-backend && mkdir iot-gps-tracker-backend"
scp -r * dumavla@192.168.1.53:~/iot-gps-tracker-backend


REM docker build
REM ssh -t dumavla@192.168.1.53 "sudo docker build -t dumskyhome/iot-gps-tracker-backend:rev2 ~/iot-gps-tracker-backend/."
REM ssh -t dumavla@192.168.1.53 "sudo docker run --name iot-gps-tracker-backend-rev2  --restart unless-stopped -it -d dumskyhome/iot-gps-tracker-backend:rev2

ssh -t dumavla@192.168.1.53 "sudo docker stop iot-gps-tracker-backend-rev2 || true && sudo docker rm iot-gps-tracker-backend-rev2 || true && sudo docker build -t dumskyhome/iot-gps-tracker-backend:rev2 ~/iot-gps-tracker-backend/. && sudo docker run --name iot-gps-tracker-backend-rev2  --restart unless-stopped -it -d dumskyhome/iot-gps-tracker-backend:rev2"