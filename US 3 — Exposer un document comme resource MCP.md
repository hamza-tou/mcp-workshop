# US3 — Exposer un document comme resource MCP

En tant que développeur,  
je souhaite exposer un document interne comme resource MCP,  
afin qu’un assistant IA puisse le consulter directement.

---

## WHY

Certains contenus sont purement informatifs et ne nécessitent pas d’appel d’action.  
Les exposer comme resource MCP est plus simple et plus adapté qu’un tool.

---

## WHAT

Créer une resource MCP exposant :
- un document interne accessible via `GET /docs/{doc_id}`

---

## HOW

- Travaille dans `mcp/exercises/exo3/`
- Utilise le **serveur MCP de référence**
- Expose une resource basée sur un `doc_id` fixe (fourni dans le README API)
- Définis clairement le format retourné

---

## RESSOURCES

- Endpoint `GET /docs/{doc_id}`
- Documentation MCP

---

## VALIDATION CRITERIA

- La resource est visible côté serveur MCP
- Le contenu du document est lisible par le client MCP