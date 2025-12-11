# 🎨 CYJE CRM - Frontend Angular COMPLET

## ✅ CE ZIP CONTIENT TOUT !

Ce projet est **100% complet** et prêt à l'emploi !

Pas besoin de copier ou fusionner quoi que ce soit.

---

## 🚀 INSTALLATION EN 3 ÉTAPES

### **Étape 1: Extraire le ZIP**

Extraire ce ZIP dans:
```
H:\Desktop\COURS ING 2 GSI\CYJE\MP\dsi\cyje-crm-complete\
```

Renommer le dossier en **"frontend"** si nécessaire.

---

### **Étape 2: Installer les dépendances**

```powershell
cd frontend
npm install
```

**Durée:** ~2-3 minutes

---

### **Étape 3: Démarrer**

**Terminal 1 - Backend:**
```powershell
cd ..\backend
mvnw spring-boot:run
```

**Terminal 2 - Frontend:**
```powershell
cd frontend
npm start
```

**Ouvrir:** http://localhost:4200

**Connexion:** admin@cyje.fr / admin123

---

## ✅ FONCTIONNALITÉS INCLUSES

### **Authentification:**
- ✅ Login avec JWT
- ✅ Guards de protection
- ✅ Interceptors automatiques

### **Dashboard:**
- ✅ 6 cartes statistiques colorées
- ✅ Chiffres en temps réel depuis le backend

### **Gestion Prospects:**
- ✅ Liste en grille avec cartes colorées
- ✅ Créer un nouveau prospect
- ✅ Modifier un prospect
- ✅ Voir le détail complet
- ✅ Supprimer avec confirmation
- ✅ Chips de statut (NOUVEAU, CONTACTE, SIGNE, etc.)

### **Gestion Users:**
- ✅ Liste de tous les utilisateurs (admin)
- ✅ Activer/Désactiver un compte
- ✅ Voir son profil personnel

### **Navigation:**
- ✅ Navbar fixe en haut
- ✅ Sidebar avec menu à gauche
- ✅ Liens actifs colorés
- ✅ Menu avatar avec déconnexion
- ✅ Responsive mobile

### **Autre:**
- ✅ Page 404 stylée
- ✅ Guard admin pour protéger /users
- ✅ Design Material moderne

---

## 📁 STRUCTURE DU PROJET

```
frontend/
├── src/
│   ├── app/
│   │   ├── components/
│   │   │   ├── auth/login/
│   │   │   ├── dashboard/
│   │   │   ├── prospects/
│   │   │   │   ├── prospect-list/
│   │   │   │   ├── prospect-form/
│   │   │   │   └── prospect-detail/
│   │   │   ├── users/
│   │   │   │   ├── user-list/
│   │   │   │   └── user-profile/
│   │   │   └── shared/
│   │   │       ├── navbar/
│   │   │       ├── sidebar/
│   │   │       └── not-found/
│   │   ├── models/
│   │   ├── services/
│   │   ├── guards/
│   │   └── interceptors/
│   ├── index.html
│   ├── main.ts
│   └── styles.scss
├── package.json
├── angular.json
└── proxy.conf.json
```

---

## 🐛 DÉPANNAGE

### Erreur: "Zone.js required"
```powershell
npm install zone.js
```

### Erreur: Backend non accessible
1. Vérifiez que le backend tourne sur port 8080
2. Testez: http://localhost:8080/actuator/health

### Erreur de compilation
```powershell
rm -r -fo node_modules .angular
npm install
npm start
```

---

## 📝 ROUTES DISPONIBLES

- `/login` - Connexion
- `/dashboard` - Tableau de bord
- `/prospects` - Liste prospects
- `/prospects/new` - Créer prospect
- `/prospects/:id` - Détail prospect
- `/prospects/:id/edit` - Modifier prospect
- `/profile` - Mon profil
- `/users` - Liste users (admin)
- `/**` - Page 404

---

## 🎉 PROJET 100% COMPLET !

Tout fonctionne out-of-the-box !

**Bon développement ! 🚀**
