# US2 — Exposer la recherche DataHub comme tool MCP

En tant que développeur,  
je souhaite exposer la recherche DataHub comme tool MCP,  
afin qu’un assistant IA puisse rechercher des contenus internes sans connaître l’API.

---

## WHY

La route `GET /search` est puissante mais difficile à utiliser sans documentation.  
L’exposer comme tool MCP permet un usage guidé et fiable par un LLM.

---

## WHAT

Créer un tool MCP qui encapsule :
- l’endpoint `GET /search`
- les paramètres `q`, `scope`, `limit`

---

## HOW

- Travaille dans `mcp/exercises/exo2/`
- Utilise le **serveur MCP de référence**
- Crée un tool MCP mappant précisément l’endpoint :
  - `q` → texte de recherche
  - `scope` → `docs` ou `snippets`
  - `limit` → nombre maximum de résultats
- Gère une erreur simple (ex : aucun résultat)

---

## RESSOURCES

- Code de l’API DataHub (`api/`)
- Documentation MCP
- Serveur MCP de référence

---

## VALIDATION CRITERIA

- Le tool est déclaré et visible côté serveur MCP
- L’appel du tool déclenche bien `GET /search`
- Les résultats sont retournés dans un format exploitable