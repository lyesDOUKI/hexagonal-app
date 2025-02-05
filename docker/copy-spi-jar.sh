#!/bin/bash

if [ -f "./keycloak-spi-user-event.jar" ]; then
    echo "Suppression de l'ancien keycloak-spi-user-event.jar..."
    rm -f "./keycloak-spi-user-event.jar"
fi

JAR_PATH=$(find ../keycloak-spi/target -name "keycloak-spi*.jar" | head -n 1)

if [ -z "$JAR_PATH" ]; then
    echo "Erreur : Aucun JAR trouvé dans ../../target/"
    exit 1
fi

cp "$JAR_PATH" ./keycloak-spi-user-event.jar

if [ -f "./keycloak-spi-user-event.jar" ]; then
    echo "JAR copié avec succès dans $(pwd)/keycloak-spi-user-event.jar"
else
    echo "Erreur lors de la copie du JAR."
    exit 1
fi
