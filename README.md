#  CYJE CRM - Backend Spring Boot

Application de gestion de prospects pour CY Junior Engineering.

##  Table des matières

- [Technologies utilisées](#technologies-utilisées)
- [Prérequis](#prérequis)
- [Installation](#installation)
    - [Option 1: Créer le projet depuis zéro](#option-1-créer-le-projet-depuis-zéro)
    - [Option 2: Utiliser le projet existant](#option-2-utiliser-le-projet-existant)
- [Configuration de la base de données](#configuration-de-la-base-de-données)
    - [Démarrer PostgreSQL avec Docker](#démarrer-postgresql-avec-docker)
    - [Connexion avec pgAdmin](#connexion-avec-pgadmin)
- [Démarrage du backend](#démarrage-du-backend)
- [Test de l'API](#test-de-lapi)
- [Documentation de l'API](#documentation-de-lapi)
- [Structure du projet](#structure-du-projet)
- [Dépannage](#dépannage)

---

##  Technologies utilisées

| Technologie | Version | Description |
|-------------|---------|-------------|
| **Java** | 17+ | Langage de programmation |
| **Spring Boot** | 3.2.0 | Framework backend |
| **Spring Security** | 6.x | Sécurité et authentification |
| **Spring Data JPA** | 3.x | Accès aux données |
| **PostgreSQL** | 15 | Base de données |
| **Docker** | Latest | Conteneurisation PostgreSQL |
| **JWT** | 0.12.3 | Authentification par token |
| **Lombok** | Latest | Réduction du code boilerplate |
| **Maven** | 3.6+ | Gestionnaire de dépendances |

---

## 📦 Prérequis

Avant de commencer, assurez-vous d'avoir installé:

### **1. Java JDK 17 ou supérieur**

**Vérifier l'installation:**
```bash
java -version
```

**Si non installé:**
- Téléchargez: https://www.oracle.com/java/technologies/downloads/
- Ou utilisez OpenJDK: https://adoptium.net/

### **2. Maven**

**Vérifier l'installation:**
```bash
mvn -version
```

**Si non installé:**
- Maven est inclus dans le projet (Maven Wrapper: `mvnw`)

### **3. Docker Desktop**

**Vérifier l'installation:**
```bash
docker --version
docker-compose --version
```

**Si non installé:**
- Téléchargez: https://www.docker.com/products/docker-desktop/
- Installez et démarrez Docker Desktop

### **4. IDE (Recommandé)**

- **IntelliJ IDEA** (Community ou Ultimate): https://www.jetbrains.com/idea/download/
- Ou **VS Code** avec extension Java: https://code.visualstudio.com/

### **5. Postman (Optionnel - pour tester l'API)**

- Téléchargez: https://www.postman.com/downloads/

### **6. pgAdmin (Optionnel - pour gérer PostgreSQL)**

- Téléchargez: https://www.pgadmin.org/download/

---

##  Installation

### **Option 1: Créer le projet depuis zéro**

#### **Étape 1.1: Créer le projet Spring Boot**

**Méthode A - Via Spring Initializr (Web):**

1. Allez sur https://start.spring.io/
2. Configurez le projet:
    - **Project:** Maven
    - **Language:** Java
    - **Spring Boot:** 3.2.0
    - **Group:** com.cyje
    - **Artifact:** backend
    - **Name:** backend
    - **Package name:** com.cyje.backend
    - **Packaging:** Jar
    - **Java:** 17

3. Ajoutez les dépendances:
    - Spring Web
    - Spring Data JPA
    - PostgreSQL Driver
    - Spring Security
    - Validation
    - Lombok
    - Spring Boot DevTools
    - Spring Boot Actuator

4. Cliquez sur **Generate**
5. Décompressez le fichier ZIP téléchargé

**Méthode B - Via IntelliJ IDEA:**

1. **File** → **New** → **Project**
2. Sélectionnez **Spring Initializr**
3. Configurez comme ci-dessus
4. Cliquez sur **Create**

#### **Étape 1.2: Ajouter les dépendances JWT**

Ouvrez `pom.xml` et ajoutez dans la section `<dependencies>`:

```xml
<!-- JWT -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.3</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>
```

#### **Étape 1.3: Créer la structure des packages**

Dans `src/main/java/com/cyje/backend/`, créez les packages:

```
backend/src/main/java/com/cyje/backend/
├── entity/
├── repository/
├── dto/
│   ├── request/
│   └── response/
├── service/
├── controller/
├── security/
├── config/
└── exception/
```

#### **Étape 1.4: Copier les fichiers Java**

Copiez tous les fichiers Java fournis dans les packages correspondants (36 fichiers au total).

---

### **Option 2: Utiliser le projet existant**

#### **Étape 2.1: Cloner ou décompresser le projet**

Si vous avez le ZIP complet:

```bash
# Décompresser le ZIP dans votre dossier de travail
# Par exemple: C:\projets\cyje-crm
```

#### **Étape 2.2: Ouvrir le projet dans IntelliJ**

1. **File** → **Open**
2. Sélectionnez le dossier `backend`
3. Attendez que Maven télécharge toutes les dépendances (2-3 minutes)

---

##  Configuration de la base de données

### **Démarrer PostgreSQL avec Docker**

#### **Étape 1: Créer le fichier docker-compose.yml**

Dans le dossier racine du projet (parent de `backend/`), créez `docker-compose.yml`:

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:15-alpine
    container_name: cyje-crm-postgres
    environment:
      POSTGRES_DB: cyje_crm_db
      POSTGRES_USER: cyje_user
      POSTGRES_PASSWORD: cyje_password_2025
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - cyje-network
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U cyje_user -d cyje_crm_db"]
      interval: 10s
      timeout: 5s
      retries: 5
    restart: unless-stopped

volumes:
  postgres_data:
    driver: local

networks:
  cyje-network:
    driver: bridge
```

#### **Étape 2: Démarrer Docker Desktop**

1. Lancez **Docker Desktop**
2. Attendez que l'icône Docker devienne verte (Docker est prêt)

#### **Étape 3: Démarrer PostgreSQL**

```bash
# Dans le dossier où se trouve docker-compose.yml
docker-compose up -d
```

**Vérifier que le container tourne:**

```bash
docker ps
```

**Résultat attendu:**
```
CONTAINER ID   IMAGE                PORTS                    NAMES
xxxxx          postgres:15-alpine   0.0.0.0:5432->5432/tcp   cyje-crm-postgres
```

#### **Étape 4: Vérifier les logs (optionnel)**

```bash
docker logs cyje-crm-postgres
```

Vous devriez voir: `database system is ready to accept connections`

---

### **Connexion avec pgAdmin**

#### **Installer pgAdmin**

Si ce n'est pas déjà fait:
1. Téléchargez: https://www.pgadmin.org/download/
2. Installez pgAdmin 4

#### **Configurer la connexion dans pgAdmin**

1. **Lancez pgAdmin**
2. **Clic droit** sur "Servers" → **Register** → **Server**

3. **Onglet General:**
    - **Name:** CYJE CRM Database

4. **Onglet Connection:**
    - **Host name/address:** `localhost`
    - **Port:** `5432`
    - **Maintenance database:** `cyje_crm_db`
    - **Username:** `cyje_user`
    - **Password:** `cyje_password_2025`
    - ☑ **Save password**

5. Cliquez sur **Save**

#### **Explorer la base de données**

1. **Développez** Servers → CYJE CRM Database → Databases → cyje_crm_db
2. **Développez** Schemas → public → Tables

**Vous verrez les tables:**
- `users` - Utilisateurs du système
- `prospects` - Prospects commerciaux

#### **Exécuter des requêtes SQL**

**Clic droit** sur `cyje_crm_db` → **Query Tool**

**Exemples de requêtes:**

```sql
-- Voir tous les utilisateurs
SELECT * FROM users;

-- Voir tous les prospects
SELECT * FROM prospects;

-- Compter les prospects par statut
SELECT statut, COUNT(*) 
FROM prospects 
GROUP BY statut;

-- Voir les prospects avec leur créateur
SELECT 
    p.nom_entreprise,
    p.statut,
    u.prenom || ' ' || u.nom as createur
FROM prospects p
JOIN users u ON p.created_by_id = u.id;
```

---

##  Démarrage du backend

### **Méthode 1: Via IntelliJ IDEA (Recommandé)**

1. **Ouvrez** le fichier `BackendApplication.java`
2. **Clic droit** sur le fichier → **Run 'BackendApplication.main()'**

   Ou cliquez sur la flèche verte ▶️ à côté de:
   ```java
   public class BackendApplication {
   ```

3. **Attendez** 30-60 secondes que l'application démarre

**Résultat attendu dans la console:**

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2025-12-11 01:00:00.000  INFO 12345 --- [main] com.cyje.backend.BackendApplication      : Starting BackendApplication
2025-12-11 01:00:05.000  INFO 12345 --- [main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8080 (http)
2025-12-11 01:00:10.000  INFO 12345 --- [main] c.c.b.config.DataLoader                  : ========================================
2025-12-11 01:00:10.000  INFO 12345 --- [main] c.c.b.config.DataLoader                  : Utilisateur admin créé avec succès !
2025-12-11 01:00:10.000  INFO 12345 --- [main] c.c.b.config.DataLoader                  : Email: admin@cyje.fr
2025-12-11 01:00:10.000  INFO 12345 --- [main] c.c.b.config.DataLoader                  : Mot de passe: admin123
2025-12-11 01:00:10.000  INFO 12345 --- [main] c.c.b.config.DataLoader                  : ========================================
2025-12-11 01:00:15.000  INFO 12345 --- [main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http)
2025-12-11 01:00:15.234  INFO 12345 --- [main] com.cyje.backend.BackendApplication      : Started BackendApplication in 15.234 seconds
```

---

### **Méthode 2: Via ligne de commande**

**Windows:**
```bash
cd backend
mvnw.cmd spring-boot:run
```

**Linux/Mac:**
```bash
cd backend
./mvnw spring-boot:run
```

---

### **Méthode 3: Script de démarrage automatique**

Créez un fichier `start-all.bat` (Windows):

```batch
@echo off
echo ========================================
echo   DEMARRAGE CYJE CRM
echo ========================================
echo.

echo [1/2] Demarrage PostgreSQL...
docker-compose up -d
timeout /t 10 /nobreak

echo.
echo [2/2] Demarrage Backend...
cd backend
start cmd /k "mvnw spring-boot:run"

echo.
echo ========================================
echo   TOUS LES SERVICES SONT DEMARRES
echo ========================================
echo.
echo Backend: http://localhost:8080
echo Health: http://localhost:8080/actuator/health
echo.
pause
```

**Double-cliquez** sur `start-all.bat` pour tout démarrer automatiquement.

---

##  Test de l'API

### **Test 1: Health Check**

**Navigateur ou cURL:**
```bash
http://localhost:8080/actuator/health
```

**Résultat attendu:**
```json
{
  "status": "UP"
}
```

---

### **Test 2: Login avec le compte admin**

**cURL:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@cyje.fr",
    "password": "admin123"
  }'
```

**Résultat attendu:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer",
  "id": 1,
  "email": "admin@cyje.fr",
  "prenom": "Admin",
  "nom": "CYJE",
  "role": "ADMIN"
}
```

** Copiez le token pour les tests suivants !**

---

### **Test 3: Créer un prospect**

```bash
curl -X POST http://localhost:8080/api/prospects \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer VOTRE_TOKEN" \
  -d '{
    "nomEntreprise": "TechCorp",
    "nomContact": "Marie Martin",
    "email": "marie@techcorp.fr",
    "telephone": "0601020304",
    "statut": "NOUVEAU",
    "montantPotentiel": 15000,
    "secteurActivite": "IT",
    "notes": "Premier contact intéressant"
  }'
```

---

### **Test 4: Obtenir les statistiques**

```bash
curl -X GET http://localhost:8080/api/stats \
  -H "Authorization: Bearer VOTRE_TOKEN"
```

---

##  Documentation de l'API

### **Endpoints disponibles**

| Méthode | Endpoint | Description | Auth |
|---------|----------|-------------|------|
| **POST** | `/api/auth/register` | Inscription | ❌ |
| **POST** | `/api/auth/login` | Connexion | ❌ |
| **GET** | `/api/auth/health` | Health check | ❌ |
| **GET** | `/api/users` | Liste utilisateurs | ✅ ADMIN |
| **GET** | `/api/users/me` | Utilisateur connecté | ✅ |
| **GET** | `/api/users/{id}` | Utilisateur par ID | ✅ ADMIN |
| **PUT** | `/api/users/{id}` | Modifier utilisateur | ✅ ADMIN |
| **PATCH** | `/api/users/{id}/toggle-active` | Activer/Désactiver | ✅ ADMIN |
| **DELETE** | `/api/users/{id}` | Supprimer utilisateur | ✅ ADMIN |
| **PATCH** | `/api/users/update-password` | Changer mot de passe | ✅ |
| **GET** | `/api/prospects` | Liste prospects | ✅ |
| **GET** | `/api/prospects/{id}` | Prospect par ID | ✅ |
| **GET** | `/api/prospects/statut/{statut}` | Prospects par statut | ✅ |
| **GET** | `/api/prospects/search` | Recherche avancée | ✅ |
| **POST** | `/api/prospects` | Créer prospect | ✅ |
| **PUT** | `/api/prospects/{id}` | Modifier prospect | ✅ |
| **DELETE** | `/api/prospects/{id}` | Supprimer prospect | ✅ |
| **GET** | `/api/stats` | Statistiques globales | ✅ |

---

### **Authentification**

Tous les endpoints protégés nécessitent un token JWT dans le header:

```
Authorization: Bearer VOTRE_TOKEN_JWT
```

**Obtenir un token:**
1. Appelez `POST /api/auth/login` avec email et password
2. Récupérez le `token` dans la réponse
3. Utilisez ce token dans le header `Authorization`

**Le token expire après 24 heures.**

---

### **Statuts des prospects**

Les valeurs possibles pour le champ `statut`:
- `NOUVEAU` - Prospect nouvellement ajouté
- `CONTACTE` - Premier contact effectué
- `RELANCE` - En cours de relance
- `SIGNE` - Contrat signé
- `PERDU` - Opportunité perdue

---

### **Rôles utilisateurs**

- `ADMIN` - Accès complet (gestion utilisateurs + prospects)
- `USER` - Accès limité (gestion prospects uniquement)

---

## 📂 Structure du projet

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/cyje/backend/
│   │   │   ├── BackendApplication.java          # Point d'entrée
│   │   │   ├── entity/                          # Entités JPA
│   │   │   │   ├── User.java                    # Entité Utilisateur
│   │   │   │   └── Prospect.java                # Entité Prospect
│   │   │   ├── repository/                      # Repositories JPA
│   │   │   │   ├── UserRepository.java
│   │   │   │   └── ProspectRepository.java
│   │   │   ├── dto/                             # Data Transfer Objects
│   │   │   │   ├── request/                     # DTOs de requête
│   │   │   │   │   ├── LoginRequest.java
│   │   │   │   │   ├── RegisterRequest.java
│   │   │   │   │   ├── ProspectRequest.java
│   │   │   │   │   └── UpdatePasswordRequest.java
│   │   │   │   └── response/                    # DTOs de réponse
│   │   │   │       ├── AuthResponse.java
│   │   │   │       ├── UserResponse.java
│   │   │   │       ├── ProspectResponse.java
│   │   │   │       └── StatsResponse.java
│   │   │   ├── service/                         # Logique métier
│   │   │   │   ├── AuthService.java
│   │   │   │   ├── UserService.java
│   │   │   │   ├── ProspectService.java
│   │   │   │   └── StatsService.java
│   │   │   ├── controller/                      # Endpoints REST
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── UserController.java
│   │   │   │   ├── ProspectController.java
│   │   │   │   └── StatsController.java
│   │   │   ├── security/                        # Sécurité JWT
│   │   │   │   ├── JwtService.java
│   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   └── UserDetailsServiceImpl.java
│   │   │   ├── config/                          # Configuration
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   ├── CorsConfig.java
│   │   │   │   └── DataLoader.java
│   │   │   └── exception/                       # Gestion erreurs
│   │   │       ├── ResourceNotFoundException.java
│   │   │       ├── BadRequestException.java
│   │   │       ├── ErrorResponse.java
│   │   │       └── GlobalExceptionHandler.java
│   │   └── resources/
│   │       └── application.properties           # Configuration app
│   └── test/
│       └── java/                                # Tests unitaires
├── target/                                      # Fichiers compilés
├── .gitignore
├── mvnw                                         # Maven Wrapper (Linux/Mac)
├── mvnw.cmd                                     # Maven Wrapper (Windows)
├── pom.xml                                      # Configuration Maven
└── README.md                                    # Ce fichier
```

---

##  Dépannage

### **Problème 1: Port 8080 déjà utilisé**

**Erreur:**
```
Port 8080 is already in use
```

**Solution:**

**Option A - Tuer le processus:**
```powershell
# Windows PowerShell
Get-Process -Name java | Stop-Process -Force
```

**Option B - Changer le port:**

Dans `application.properties`:
```properties
server.port=8081
```

---

### **Problème 2: Failed to configure a DataSource**

**Erreur:**
```
Failed to configure a DataSource: 'url' attribute is not specified
```

**Cause:** PostgreSQL n'est pas démarré

**Solution:**
```bash
# Vérifier si Docker tourne
docker ps

# Si vide, démarrer PostgreSQL
docker-compose up -d

# Attendre 10 secondes
# Puis redémarrer Spring Boot
```

---

### **Problème 3: Cannot connect to Docker daemon**

**Erreur:**
```
Cannot connect to the Docker daemon
```

**Solution:**
1. Démarrez **Docker Desktop**
2. Attendez que l'icône devienne verte
3. Réessayez `docker-compose up -d`

---

### **Problème 4: Lombok not working**

**Erreur:**
```
Cannot find symbol: method builder()
```

**Solution IntelliJ:**
1. **File** → **Settings**
2. **Plugins** → Recherchez "Lombok"
3. **Installez** le plugin Lombok
4. **Redémarrez** IntelliJ
5. **File** → **Settings** → **Build** → **Compiler** → **Annotation Processors**
6. ☑ **Enable annotation processing**

---

### **Problème 5: JWT Token Invalid**

**Erreur:**
```
401 Unauthorized
```

**Solution:**
1. Vérifiez que le token est dans le header `Authorization`
2. Format: `Bearer VOTRE_TOKEN` (avec l'espace après "Bearer")
3. Le token expire après 24h, reconnectez-vous
4. Vérifiez que le secret JWT dans `application.properties` fait au moins 256 bits

---

### **Problème 6: CORS Error**

**Erreur dans le navigateur:**
```
Access to XMLHttpRequest has been blocked by CORS policy
```

**Solution:**

Dans `application.properties`, vérifiez:
```properties
cors.allowed.origins=http://localhost:4200
```

Pour plusieurs origines:
```properties
cors.allowed.origins=http://localhost:4200,http://localhost:3000
```

---

### **Problème 7: Maven dependencies not downloading**

**Solution:**
```bash
# Nettoyer et réinstaller
mvnw clean install -U

# Ou dans IntelliJ:
# Clic droit sur pom.xml → Maven → Reload Project
```

---

### **Problème 8: PostgreSQL connection refused**

**Erreur:**
```
Connection refused: localhost:5432
```

**Solution:**
```bash
# Vérifier que PostgreSQL tourne
docker ps

# Voir les logs
docker logs cyje-crm-postgres

# Redémarrer PostgreSQL
docker-compose restart postgres

# Ou recréer le container
docker-compose down
docker-compose up -d
```

---

##  Variables d'environnement (optionnel)

Pour un déploiement en production, utilisez des variables d'environnement:

**Linux/Mac:**
```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/cyje_crm_db
export SPRING_DATASOURCE_USERNAME=cyje_user
export SPRING_DATASOURCE_PASSWORD=cyje_password_2025
export JWT_SECRET=VotreSecretTresLongEtSecurise
export JWT_EXPIRATION=86400000
```

**Windows PowerShell:**
```powershell
$env:SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/cyje_crm_db"
$env:SPRING_DATASOURCE_USERNAME="cyje_user"
$env:SPRING_DATASOURCE_PASSWORD="cyje_password_2025"
$env:JWT_SECRET="VotreSecretTresLongEtSecurise"
$env:JWT_EXPIRATION="86400000"
```

---



##  Compte par défaut

Au premier démarrage, un compte admin est créé automatiquement:

| Champ | Valeur |
|-------|--------|
| **Email** | admin@cyje.fr |
| **Mot de passe** | admin123 |
| **Rôle** | ADMIN |

** IMPORTANT:** Changez ce mot de passe en production !

---

##  Compilation et packaging

### **Compiler le projet:**
```bash
mvnw clean compile
```

### **Exécuter les tests:**
```bash
mvnw test
```

### **Créer un JAR exécutable:**
```bash
mvnw clean package
```















#  README COMPLET - Création Manuelle du Frontend Angular CYJE CRM

##  Ce que vous allez créer

Une application CRM complète avec Angular 18, Material Design, authentification JWT, gestion de prospects et utilisateurs.

---

##  Temps estimé

- **Configuration initiale:** 10 minutes
- **Copie des codes:** 40-60 minutes
- **Total:** ~1h pour avoir l'app complète

---

##  PRÉREQUIS

```powershell
# Vérifier Node.js
node -v
# Requis: v18.x ou v20.x

# Installer Angular CLI globalement
npm install -g @angular/cli

# Vérifier
ng version
```

---

##  PARTIE 1: CRÉATION DU PROJET DE BASE

### 1.1 Créer le projet

```powershell
cd "H:\Desktop\COURS ING 2 GSI\CYJE\MP\dsi\cyje-crm-complete"

ng new frontend --routing --style=scss --skip-git --ssr=false

cd frontend
```

**Durée:** 2-3 minutes

---

### 1.2 Installer les dépendances

```powershell
# Angular Material
ng add @angular/material
# Choisir: Indigo/Pink theme, Typography: Yes, Animations: Yes

# JWT decode
npm install jwt-decode

# Zone.js (normalement déjà installé)
npm install zone.js
```

---

##  PARTIE 2: CONFIGURATION

### 2.1 Créer proxy.conf.json

**Fichier:** `proxy.conf.json` (racine)

```json
{
  "/api": {
    "target": "http://localhost:8080",
    "secure": false,
    "changeOrigin": true,
    "logLevel": "debug"
  }
}
```

---

### 2.2 Modifier package.json

**Fichier:** `package.json`

**Trouver et modifier:**

```json
"scripts": {
  "start": "ng serve --proxy-config proxy.conf.json",
  ...
}
```

---

### 2.3 Modifier main.ts

**Fichier:** `src/main.ts`

**Remplacer TOUT par:**

```typescript
import 'zone.js';

import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';

bootstrapApplication(AppComponent, appConfig)
  .catch((err: Error) => console.error(err));
```

---

##  PARTIE 3: STRUCTURE

### 3.1 Créer tous les dossiers

```powershell
# Models, Services, Guards, Interceptors
mkdir src\app\models
mkdir src\app\services
mkdir src\app\guards
mkdir src\app\interceptors

# Composants Auth
mkdir src\app\components\auth\login

# Dashboard
mkdir src\app\components\dashboard

# Prospects
mkdir src\app\components\prospects\prospect-list
mkdir src\app\components\prospects\prospect-form
mkdir src\app\components\prospects\prospect-detail

# Users
mkdir src\app\components\users\user-list
mkdir src\app\components\users\user-profile

# Shared
mkdir src\app\components\shared\navbar
mkdir src\app\components\shared\sidebar
mkdir src\app\components\shared\not-found
```

---

## 📦 PARTIE 4: FICHIERS À CRÉER

Vous devez maintenant créer **~50 fichiers**. Voici la liste complète:

### **Models (2 fichiers)**

| Fichier | Chemin |
|---------|--------|
| user.model.ts | `src/app/models/` |
| prospect.model.ts | `src/app/models/` |



---

### **Services (4 fichiers)**

| Fichier | Chemin |
|---------|--------|
| auth.service.ts | `src/app/services/` |
| stats.service.ts | `src/app/services/` |
| prospect.service.ts | `src/app/services/` |
| user.service.ts | `src/app/services/` |



---

### **Guards (2 fichiers)**

| Fichier | Chemin |
|---------|--------|
| auth.guard.ts | `src/app/guards/` |
| admin.guard.ts | `src/app/guards/` |

---

### **Interceptors (1 fichier)**

| Fichier | Chemin |
|---------|--------|
| auth.interceptor.ts | `src/app/interceptors/` |

---

### **Composants (42 fichiers = 14 composants × 3 fichiers)**

Chaque composant a 3 fichiers: `.ts`, `.html`, `.scss`

| Composant | Dossier | Fichiers |
|-----------|---------|----------|
| **Login** | `components/auth/login/` | login.component.ts<br>login.component.html<br>login.component.scss |
| **Dashboard** | `components/dashboard/` | dashboard.component.ts<br>dashboard.component.html<br>dashboard.component.scss |
| **Prospect List** | `components/prospects/prospect-list/` | prospect-list.component.ts<br>prospect-list.component.html<br>prospect-list.component.scss |
| **Prospect Form** | `components/prospects/prospect-form/` | prospect-form.component.ts<br>prospect-form.component.html<br>prospect-form.component.scss |
| **Prospect Detail** | `components/prospects/prospect-detail/` | prospect-detail.component.ts<br>prospect-detail.component.html<br>prospect-detail.component.scss |
| **User List** | `components/users/user-list/` | user-list.component.ts<br>user-list.component.html<br>user-list.component.scss |
| **User Profile** | `components/users/user-profile/` | user-profile.component.ts<br>user-profile.component.html<br>user-profile.component.scss |
| **Navbar** | `components/shared/navbar/` | navbar.component.ts<br>navbar.component.html<br>navbar.component.scss |
| **Sidebar** | `components/shared/sidebar/` | sidebar.component.ts<br>sidebar.component.html<br>sidebar.component.scss |
| **Not Found** | `components/shared/not-found/` | not-found.component.ts<br>not-found.component.html<br>not-found.component.scss |



##  PARTIE 5: DÉMARRAGE

### 5.1 Vérifier l'installation

```powershell
npm install
```

### 5.2 Démarrer le backend

**Terminal 1:**

```powershell
cd ..\backend
mvnw spring-boot:run
```

**Attendre:** `Started BackendApplication`

### 5.3 Démarrer le frontend

**Terminal 2:**

```powershell
cd frontend
npm start
```

**Attendre:** `✔ Compiled successfully`

### 5.4 Tester

**Ouvrir:** http://localhost:4200

**Login:** admin@cyje.fr / admin123

---

##  CHECKLIST FINALE

Après création, vérifiez que vous avez:

### **Fichiers de configuration (7):**
- [ ] proxy.conf.json
- [ ] package.json (modifié)
- [ ] angular.json
- [ ] tsconfig.json
- [ ] src/main.ts (modifié)
- [ ] src/index.html
- [ ] src/styles.scss (modifié)

### **Models (2):**
- [ ] src/app/models/user.model.ts
- [ ] src/app/models/prospect.model.ts

### **Services (4):**
- [ ] src/app/services/auth.service.ts
- [ ] src/app/services/stats.service.ts
- [ ] src/app/services/prospect.service.ts
- [ ] src/app/services/user.service.ts

### **Guards & Interceptors (3):**
- [ ] src/app/guards/auth.guard.ts
- [ ] src/app/guards/admin.guard.ts
- [ ] src/app/interceptors/auth.interceptor.ts

### **App Core (5):**
- [ ] src/app/app.component.ts
- [ ] src/app/app.component.html
- [ ] src/app/app.component.scss
- [ ] src/app/app.config.ts
- [ ] src/app/app.routes.ts

### **Composants Auth (3):**
- [ ] components/auth/login/ (3 fichiers)

### **Composant Dashboard (3):**
- [ ] components/dashboard/ (3 fichiers)

### **Composants Prospects (9):**
- [ ] components/prospects/prospect-list/ (3 fichiers)
- [ ] components/prospects/prospect-form/ (3 fichiers)
- [ ] components/prospects/prospect-detail/ (3 fichiers)

### **Composants Users (6):**
- [ ] components/users/user-list/ (3 fichiers)
- [ ] components/users/user-profile/ (3 fichiers)

### **Composants Navigation (9):**
- [ ] components/shared/navbar/ (3 fichiers)
- [ ] components/shared/sidebar/ (3 fichiers)
- [ ] components/shared/not-found/ (3 fichiers)

**TOTAL: ~57 fichiers**

---

##  DÉPANNAGE

### Erreur: "Cannot find module"

```powershell
npm install
```

### Erreur: "Zone.js required"

Vérifier que `src/main.ts` commence par `import 'zone.js';`

### Erreur: Backend non accessible

1. Backend tourne sur port 8080 ?
2. `proxy.conf.json` existe ?
3. `npm start` (pas `ng serve`) ?

### Erreur de compilation

```powershell
rm -r -fo node_modules .angular
npm install
npm start
```

---

##  RÉSUMÉ DES TECHNOLOGIES

- **Framework:** Angular 18
- **UI:** Angular Material
- **Authentification:** JWT (jwt-decode)
- **HTTP:** HttpClient avec interceptors
- **Routing:** Lazy loading avec guards
- **Styles:** SCSS
- **State:** RxJS BehaviorSubject



##  RESSOURCES COMPLÉMENTAIRES

- **Documentation Angular:** https://angular.dev/
- **Angular Material:** https://material.angular.io/
- **RxJS:** https://rxjs.dev/
- **JWT:** https://jwt.io/

