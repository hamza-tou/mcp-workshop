# US5 — Améliorer l’utilisabilité MCP

En tant que développeur,  
je souhaite améliorer la compréhension des capacités MCP par un assistant IA,  
afin qu’il les utilise de manière plus précise et pertinente.

---

## WHY

Même avec des tools fonctionnels, un LLM peut :
- mal interpréter leur rôle
- fournir des paramètres imprécis
- ignorer certaines resources

---

## WHAT

Améliorer l’utilisabilité MCP en modifiant uniquement :
- les descriptions des tools
- les descriptions des paramètres
- les descriptions des resources

---

## HOW

- Travaille dans `mcp/exercises/exo5/`
- Observe les appels MCP existants
- Identifie les points de confusion
- Améliore les descriptions MCP
- Teste à nouveau le comportement

⚠️ Ne modifie ni la logique métier, ni les endpoints exposés.

---

## RESSOURCES

- Historique des appels MCP
- Code MCP existant

---

## VALIDATION CRITERIA

- L’assistant choisit plus souvent le bon tool
- Les paramètres sont mieux renseignés
- Les réponses finales sont plus pertinentes