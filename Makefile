build:
	sudo docker-compose build
up:
	sudo docker-compose up -d

deploye: build up

down:
	sudo docker-compose down