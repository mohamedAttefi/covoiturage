# Plateforme de covoiturage multi-étapes

API REST Spring Boot pour publier des trajets composés de points d’arrêt, rechercher un segment dans le bon sens et réserver des places.

## Démarrage

Prérequis : Java 17+, Maven et Docker Compose.

```bash
docker compose up -d
./mvnw spring-boot:run
```

Si le dépôt ne contient pas Maven Wrapper, utilisez `mvn spring-boot:run`. PostgreSQL écoute sur `localhost:5435`, base `covoiturage_db`, identifiants `postgres/postgres`. Liquibase crée le schéma au démarrage. L’API écoute sur le port 8080.

- Swagger UI : http://localhost:8080/swagger-ui/index.html
- OpenAPI : http://localhost:8080/v3/api-docs
- Actuator health : http://localhost:8080/actuator/health

## API

Les corps de requête utilisent les noms de propriétés des records Java (camelCase). Les opérations qui exigent un propriétaire reçoivent son identifiant et le comparent au propriétaire enregistré. Aucune authentification n’est configurée dans ce prototype.

| Méthode | Route | Fonction |
|---|---|---|
| POST/GET | `/api/conducteurs` | Créer et lister les conducteurs |
| GET/PUT/DELETE | `/api/conducteurs/{id}` | Consulter, modifier, supprimer |
| GET | `/api/conducteurs/search?keyword=...` | Recherche nom, prénom ou email |
| POST/GET | `/api/passagers` | Créer et lister les passagers |
| GET/PUT/DELETE | `/api/passagers/{id}` | Consulter, modifier, supprimer |
| GET | `/api/passagers/search?keyword=...` | Recherche passager |
| POST/GET | `/api/vehicules` | Ajouter et lister les véhicules |
| GET/PUT/DELETE | `/api/vehicules/{id}` | Consulter, modifier, supprimer |
| GET | `/api/vehicules/conducteur/{conducteurId}` | Véhicules d’un conducteur |
| POST/GET | `/api/trajets` | Publier et lister les trajets |
| GET | `/api/trajets/{id}` | Détail du trajet et des arrêts |
| GET | `/api/trajets/search?depart=Rabat&arrivee=Settat&date=2030-06-01&nombrePlaces=1&page=0&size=10&prixMax=100` | Recherche paginée par segment et prix du segment |
| PATCH | `/api/trajets/{id}/cancel` | Annuler le trajet et toutes ses réservations confirmées |
| PATCH | `/api/trajets/{id}/finish` | Terminer après l’heure de départ |
| GET | `/api/trajets/{id}/reservations?conducteurId=...` | Réservations confirmées du conducteur propriétaire |
| POST | `/api/reservations` | Réserver un segment |
| GET | `/api/reservations/passager/{passagerId}` | Historique du passager |
| PATCH | `/api/reservations/{id}/annuler?passagerId=...` | Annuler sa réservation, au moins 24 h avant départ |

Une réservation occupe le nombre de places demandé sur tout le trajet, conformément à la simplification du brief. Le montant est `(prix descente - prix montée) × places`. Les arrêts doivent être ordonnés, avec des heures et prix strictement croissants, et des villes uniques.

### Parcours de démonstration

1. Créer un conducteur (`POST /api/conducteurs`), un passager (`POST /api/passagers`) puis un véhicule (`POST /api/vehicules`).
2. Publier un trajet en fournissant au moins deux points d’arrêt; le premier doit avoir `ordre: 0` et un prix de `0.00`.
3. Chercher un segment avec `/api/trajets/search` et réserver via `POST /api/reservations`.
4. Lire l’historique du passager et annuler la réservation suffisamment tôt avant le départ.

Voir [la collection Postman](postman/covoiturage.postman_collection.json) et [le diagramme UML](docs/class-diagram.svg).
Pour la démonstration avec un jeu de données, exécuter `docs/demo-data.sql` une seule fois sur une base vide après le démarrage de l’application (Liquibase doit d’abord créer le schéma). Ce script ajoute cinq conducteurs, passagers, véhicules, trajets et réservations, ainsi que leurs points d’arrêt.

## Architecture

Le code suit les couches Controller → Service → Repository, avec DTO records, MapStruct, JPA, validation Bean Validation et réponses ProblemDetail centralisées. Les opérations de réservation verrouillent le trajet pendant la vérification des places pour éviter les doubles réservations concurrentes. Les points d’arrêt sont possédés par le trajet (`cascade` et `orphanRemoval`).

## Vérification

Exécuter les tests unitaires avec `mvn test` et construire le livrable avec `mvn package`.
