En tant que développeur,  
je souhaite améliorer la compréhension des capacités MCP par un assistant IA,  
afin qu'il les utilise de manière plus précise et pertinente.

---

## WHY

Même avec des tools fonctionnels, un LLM peut :
- mal interpréter leur rôle
- fournir des paramètres imprécis
- ignorer certaines resources

Les descriptions des tools et resources MCP sont **critiques** pour guider correctement le LLM.

---

## WHAT

Améliorer l'utilisabilité MCP en modifiant uniquement :
- les descriptions des tools
- les descriptions des paramètres  
- les descriptions des resources

⚠️ **Contrainte importante** : Ne modifiez pas la logique métier ni les endpoints exposés.

---

## HOW

### Prérequis

1. **API DataHub lancée** :
   ```bash
   cd python/
   uv run fastapi dev datahub_api/main.py --port 8000
   ```

2. **Serveur MCP lancé** :
   ```bash
   uv run python python/datahub_mcp/server.py
   ```

3. **GitHub Copilot configuré** pour utiliser ce serveur (`http://localhost:8001`)

### Identification des problèmes

Pour cet exercice, nous allons améliorer les descriptions des tools et resources dans `python/datahub_mcp/server.py`. Vous pouvez consulter `python/datahub_mcp/reference_server/server.py` pour voir des exemples de bonnes descriptions.

#### Problème 1 : Paramètre `scope` trop vague
**Symptôme** : Le LLM utilise parfois le tool avec `scope="all"` ou `scope="everything"` qui n'existent pas.

**Cause** : La description du paramètre `scope` ne liste pas explicitement les valeurs possibles.

**À améliorer** : La description du paramètre `scope` dans le tool `search_datahub`

#### Problème 2 : Paramètre `limit` sans recommandation
**Symptôme** : Le LLM utilise parfois `limit=1000` ou `limit=5` sans raison.

**Cause** : Pas d'indication sur les valeurs recommandées.

**À améliorer** : La description du paramètre `limit`

#### Problème 3 : Resource document sans contexte d'usage
**Symptôme** : Le LLM essaye d'accéder à `datahub://documents/kubernetes` au lieu de `kubernetes-deployment`.

**Cause** : La description de la resource ne précise pas le format des `doc_id`.

**À améliorer** : La description de la resource `get_document`

#### Problème 4 : Confusion tool search vs resource document
**Symptôme** : Le LLM utilise le tool `search_datahub` alors qu'il connaît déjà le `doc_id`.

**Cause** : Pas de guidance claire sur quand utiliser search vs get_document.

**À améliorer** : Les descriptions des deux pour clarifier leur usage respectif

### Processus d'amélioration

1. **Ouvrir** `python/datahub_mcp/server.py`
2. **Améliorer** les descriptions en :
   - Listant les valeurs possibles pour les enums (ex: `scope` doit être "docs" ou "snippets")
   - Donnant des exemples concrets de `doc_id` (ex: "kubernetes-deployment", "rest-api-design")
   - Précisant les recommandations (ex: limit entre 5 et 20)
   - Clarifiant les cas d'usage (search pour découvrir vs resource pour lire un document connu)
3. **Relancer** le serveur MCP (arrêter puis redémarrer)
4. **Tester** avec les mêmes prompts qu'avant dans Copilot Chat

### Exemples de prompts à tester

**Avant amélioration** :
- "Cherche dans DataHub tout ce qui parle d'APIs" → peut utiliser `scope="all"` ❌
- "Montre-moi le document sur Kubernetes" → peut chercher `doc_id="kubernetes"` ❌  
- "Liste tous les documents" → peut utiliser `limit=1000` ❌

**Après amélioration** :
- Mêmes prompts → comportements plus précis ✅

### Mesure du succès

Comparez avant/après :
- Le LLM choisit-il les bonnes valeurs pour `scope` ?
- Le LLM utilise-t-il des `doc_id` valides ?
- Le LLM utilise-t-il des `limit` raisonnables ?
- Le LLM distingue-t-il search (découverte) de get_document (lecture) ?

---

## RESSOURCES

- [Guide MCP](python/datahub_mcp/README.md) - Comment tester avec Copilot
- [Serveur de travail](python/datahub_mcp/server.py) - Code avec descriptions à améliorer
- [Serveur de référence](python/datahub_mcp/reference_server/server.py) - Exemple de bonnes descriptions
- [Documentation FastMCP](https://github.com/jlowin/fastmcp)

---

## VALIDATION CRITERIA

- Les descriptions des tools et resources ont été améliorées avec des informations concrètes
- Les tests montrent une meilleure précision du LLM (moins d'erreurs de paramètres)
- Les prompts ambigus sont mieux interprétés
- La documentation MCP est plus exploitable par le LLM