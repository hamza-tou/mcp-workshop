# US2 — Exposer la recherche DataHub comme tool MCP

En tant que développeur,  
je souhaite exposer la recherche DataHub comme tool MCP,  
afin qu’un assistant IA puisse rechercher des contenus internes sans connaître l’API.

## WHY

La route `GET /search` est puissante mais difficile à utiliser sans être développeur.  
L’exposer comme tool MCP permet un usage guidé en "language humain".

## WHAT

Créer un tool MCP nommé **`search_datahub`** qui permet de rechercher tout datahub en encapsulant :
- l'endpoint `GET /search` de l'API DataHub
- les paramètres `query`, `scope`, `limit`

## HOW

0. Assures toi que l'API de DataHub tourne sur ton poste (suit les instructions dans `datahub_api/README.md`)
1. Inspire toi de l'exemple Hello World pour créer un nouveau fichier (`search_tool.py` ou `SearchTool.java`) 
2. Définit un nouveau tool `search-datahub`:
    - Utilises le client de l'API (`datahub_client.py` ou `DataHubClient.java`) pour effectuer une recherche sur datahub
    - Formatte le résultat sous forme de texte pour qu'il soit compréhensible par un LLM
4. (Re)Lance le serveur MCP et vérifue ton tool avec Copilot :
    - #search_datahub
    - "Cherche des documents sur GraphQL"
    - "Trouve des snippets Redis"


## VALIDATION CRITERIA

- Le serveur MCP redémarre sans erreur sur `http://localhost:8001`
- Le tool `search-datahub` est accessible et retourne le message attendu
- Les résultats sont retournés dans un format exploitable