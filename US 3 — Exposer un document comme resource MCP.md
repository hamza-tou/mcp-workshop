# US3 — Exposer un document comme resource MCP

En tant que développeur,  
je souhaite exposer un document interne comme resource MCP,  
afin qu’un assistant IA puisse le consulter directement.

## WHY

Certains contenus sont purement informatifs et ne nécessitent pas d’appel d’action.  Les exposer comme resource MCP est plus simple et plus adapté qu’un tool. 

**Use-case** : Un développeur demande à l'agent IA :  
_"Donne-moi les bonnes pratiques pour concevoir une API REST"_

L'agent doit pouvoir ainsi **accéder au contenu complet du document trouvé via une resource MCP** et ensuite synthétiser les bonnes pratiques pour répondre à la question


## WHAT

**Ce que tu dois créer** :

Une **resource MCP** nommée `datahub://documents/{doc_id}` qui récupère le contenu d'un document depuis l'API DataHub (`GET /documents/{doc_id}`) et **formate le résultat en texte lisible** pour l'agent IA (titre, métadonnées, contenu).
- Permet à l'agent d'accéder directement au document en tapant `#` dans Copilot Chat puis en sélectionnant la resource


## HOW

0. Assures toi que l'API de DataHub tourne sur ton poste (suit les instructions dans `datahub_api/README.md`)
1. Reprend le fichier du MCP de recherche précédent ou créé un nouveau fichier
2. Définit une nouvelle ressource `datahub://documents/{doc_id}`:
    - Utilises le client de l'API (`datahub_client.py` ou `DataHubClient.java`) pour récupérer le document sur datahub
    - Formatte le résultat sous forme de texte pour qu'il soit compréhensible par un LLM
3. (Re)Lance le serveur MCP et essaies d'accéder à la ressource `rest-api-design` avec Copilot :
    1. Dans la Command Palette de VSCode, ouvre **MCP: Browse Resources**
    2. Sélectionne la ressource `get_document` et cherche par id `rest-api-design`
    3. Demande à Copilot de résumer cette ressource en mode **Ask**


## RESOURCES

**Tool vs. Resource** :
- Un **tool** = action que l'agent peut **exécuter** (rechercher, créer, modifier)
- Une **resource** = contenu que l'agent peut **lire** et **référencer** (document, fichier, page)


- [FastMCP @resource](https://gofastmcp.com/servers/resources#the-@resource-decorator)
- [SpringAI @McpResource](https://docs.spring.io/spring-ai/reference/api/mcp/mcp-annotations-server.html#_mcpresource)


## VALIDATION CRITERIA

- La resource est visible côté serveur MCP
- Le contenu du document est lisible par le client MCP