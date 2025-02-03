- Récupération des jars : 

> ./copy-app-user-jar.sh  
> ./copy-spi-jar.sh

- pour créer les volumes :  

> docker volume create db_pg_data  
> docker volume create keycloack_mysql_data
> docker volume create redis_data

- Pour créer le network :  

> docker network create --driver bridge ddd-app-network

- Pour lancer les deux docker-compose :   

> docker compose -f docker-compose-services.yml -f docker-compose-app.yml up --build -d

- Pour lancer que les services : 
> docker compose -f docker-compose-services.yml up --build -d

### Explicration 
> L'intéret de lancer les services uniquement, c'est de travailler en mode développement, débuger, intéragir avec les services.  
> Une fois bien avancé sur le développement, on lances l'ensemble de l'application sur docker pour vérifer le comportement.