#!/bin/bash

if [ -f "./app-user-service.jar" ]; then
    echo "Suppression de l'ancien app-user-service.jar..."
    rm -f "./app-user-service.jar"
fi


JAR_PATH=$(find ../application/target -name "application*.jar" | head -n 1)

if [ -z "$JAR_PATH" ]; then
    echo "Erreur : Aucun JAR trouvé dans ../../target/"
    exit 1
fi

cp "$JAR_PATH" ./app-user-service.jar

if [ -f "./app-user-service.jar" ]; then
    echo "JAR copié avec succès dans $(pwd)/app-user-service.jar"
else
    echo "Erreur lors de la copie du JAR."
    exit 1
fi
