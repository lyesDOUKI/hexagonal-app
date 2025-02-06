# Architecture Hexagonale

## Principe de l'architecture hexagonale

L'architecture hexagonale repose sur une séparation claire entre le cœur métier et les différentes interactions extérieures. Elle permet de structurer notre application de manière modulaire et interchangeable.

### Le cœur de l'hexagone : la valeur ajoutée
Le cœur de l'hexagone représente le cœur de la logique métier, c'est-à-dire ce que veut faire notre métier. C'est la valeur ajoutée de notre application. Il ne dépend d'aucune technologie spécifique et reste totalement isolé des préoccupations techniques.

### Les petits ports en orange : les Use Cases
Les petits ports en orange représentent les Use Cases. Ce sont les fonctionnalités que le domaine est en mesure d'accomplir. Ils définissent les actions et les règles de gestion spécifiques au métier.

### Les petits ports en violet : les ports extérieurs
Les petits ports en violet sont les ports extérieurs. Ce sont les services et besoins extérieurs que le domaine va utiliser pour accomplir son travail. Ce sont des contrats qu'il propose sous forme d'interfaces.

## L'intérêt de cette architecture
L'un des principaux avantages de l'architecture hexagonale est que toutes les briques que nous voyons sont interchangeables.

- Si demain, on veut passer sur du Quarkus ou une autre technologie, notre domaine, notre cœur de métier ne sera pas impacté.
- Si on ne veut plus s'authentifier via Keycloak, aucun problème : notre domaine reste pur et ne dépend pas de cette technologie.

### Inversion de dépendance
Grâce à cette architecture, on met en place l'inversion de dépendance. Cela signifie que ce n'est plus le métier, notre valeur, qui dépend de la technologie, mais bien les technologies et frameworks qui suivent la ligne directrice fixée par le domaine.
