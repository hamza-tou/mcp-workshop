# Workshop — Exposer une API existante à un LLM via MCP (Model Context Protocol)

Ce repository est un support de workshop pour apprendre à **mettre en place un serveur MCP** et à exposer des capacités d’une **API HTTP existante** à un assistant IA, dans un contexte réaliste de développement.

---

## 🎯 Objectif

L’objectif est de comprendre comment MCP agit comme une **façade sémantique** au-dessus d’une API existante, sans refonte de celle-ci.

- **Tools MCP** (actions métier appelables)
- **Resources MCP** (données internes consultables)

L’objectif est de comprendre comment MCP agit comme une **façade sémantique** au-dessus d’une API existante, sans refonte de celle-ci.

---

## ✅ Prérequis

- Environnement de développement **Python ou Java**
- Savoir utiliser un terminal (lancer un serveur, lire des logs)
- Notions de base : JSON, appels HTTP, fonctions / méthodes
- **GitHub Copilot activé dans VS Code**

---

## 📖 Contexte — DataHub

Tu es développeur au sein de **DataHub**, une équipe qui maintient un service interne centralisant de la **documentation technique**, des **guides internes** et des **snippets utiles** pour les équipes de développement.

DataHub expose une **API HTTP existante**, utilisée par :
- les développeurs backend
- les équipes support
- les équipes data

L’API fonctionne, mais elle n’est pas conçue pour être utilisée par un LLM :
- trop de routes à connaître
- paramètres peu explicites
- aucune description exploitable automatiquement

L’équipe décide donc d’ajouter un **serveur MCP local**, indépendant de l’API, afin d’exposer uniquement les capacités utiles à un assistant IA.

---

## 🔌 API Endpoints — DataHub

L’API DataHub existante fournit les endpoints suivants :

### Santé
- `GET /health`  
  Vérifier que l’API est opérationnelle.

---

### 📄 Documents internes

- `GET /documents`  
  Lister les documents internes  
  **Filtres disponibles** :
  - `tag` (ex: `security`, `architecture`, `onboarding`)
  - `owner` (ex: `platform`, `payments`)
  - `updated_after` (date ISO)

- `GET /documents/{doc_id}`  
  Récupérer un document  
  *(titre, contenu, tags, propriétaire, date de mise à jour)*

---

### 🔎 Recherche

- `GET /search`  
  Recherche texte dans les contenus internes  
  **Paramètres** :
  - `q` : texte libre
  - `scope` : `docs` | `snippets`
  - `limit` : nombre maximum de résultats (défaut : 5)

> ⚠️ Cette route retourne des **résultats partiels** (id, titre, extrait), pas le contenu complet.

---

### 🧩 Snippets

- `GET /snippets`  
  Lister les snippets disponibles  
  **Filtres disponibles** :
  - `type` : `command` | `config` | `template`
  - `service` : nom du service concerné

- `GET /snippets/{snippet_id}`  
  Récupérer un snippet précis  
  *(contenu, type, service associé)*

---

### 📚 Métadonnées internes

- `GET /tags`  
  Lister l’ensemble des tags utilisés dans DataHub

- `GET /owners`  
  Lister les équipes propriétaires des documents


---


## 📁 Structure du projet

```
mcp-workshop/
├── README.md                # Ce fichier
├── US 1 à US 6.md          # User Stories
└── python/                  # Implémentation Python
    ├── README.md            # Guide détaillé
    ├── pyproject.toml       # Configuration uv
    ├── datahub_api/         # API DataHub (FastAPI)
    │   ├── main.py          # 8 endpoints REST
    │   ├── models.py        # Modèles Pydantic
    │   └── data/            # Données de test (JSON)
    └── datahub_mcp/         # Serveurs MCP
        ├── server.py        # Serveur à compléter (exercices)
        └── reference_server/   # Serveur complet (solution)
```

---

## 📚 Ressources

- [Guide Python](python/README.md)
- [Documentation API](python/datahub_api/README.md)
- [FastMCP](https://github.com/jlowin/fastmcp)
- [MCP](https://modelcontextprotocol.io/)
