# Projet Gestion de la Base de Données

## Description
Ce projet est un système de gestion de la base de données pour la manipulation et la gestion des entités de l'application. Il inclut des configurations pour la connexion à la base de données, des objets d'accès aux données (DAO) génériques, et des tests unitaires pour assurer la robustesse et le bon fonctionnement du système.

## Architecture
Le projet suit une approche modulaire où chaque composant est responsable d'une partie spécifique de la fonctionnalité. L'accent a été mis sur la séparation des préoccupations et la réutilisabilité du code.

## Fonctionnalités principales
- **Connexion à la base de données**: Gestion des connexions et des configurations de la base de données.
- **DAO génériques**: Fournit un ensemble de méthodes génériques pour effectuer des opérations CRUD (Créer, Lire, Mettre à jour, Supprimer).
- **Tests unitaires**: Comprend des cas de tests à l'aide de JUnit pour valider les fonctionnalités et les comportements attendus des modules.

## Technologies utilisées
- **Java**: Langage principal du projet.
- **JUnit**: Framework utilisé pour les tests unitaires.
- **Mockito**: Pour le mock de composants lors des tests.

## Structure du projet
- `src/database/core`: Contient les classes principales pour la gestion de la base de données.
- `src/test`: Contient les classes de tests unitaires et les entités de test.

## Installation et exécution
1. Cloner le repository localement.
2. Configurer la connexion à la base de données dans la classe `DBConnection.java`.
3. Exécuter les tests unitaires pour s'assurer que tout fonctionne comme prévu.

## Auteur
Ce projet a été développé par Rehareha Ranaivo. Il est conçu pour être simple à intégrer et à tester tout en offrant toutes les fonctionnalités nécessaires pour gérer efficacement une base de données dans une application Java.