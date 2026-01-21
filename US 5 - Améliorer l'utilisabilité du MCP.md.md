En tant que développeur,  
je souhaite améliorer la compréhension des capacités MCP par un assistant IA,  
afin qu'il les utilise de manière plus précise et pertinente.


## WHY

Nous avons noté que les agents avaient du mal à utiliser notre serveur MCP.
En effet, même avec des tools fonctionnels, un LLM peut :
- mal interpréter leur rôle
- fournir des paramètres imprécis
- ignorer certaines resources

Les descriptions des tools et resources MCP sont **critiques** pour guider correctement le LLM.

## WHAT

Améliorer l'utilisabilité MCP en modifiant uniquement :
- les descriptions des tools
- les descriptions des paramètres  
- les descriptions des resources

⚠️ **Contrainte importante** : Ne modifiez pas la logique métier ni les endpoints exposés.


## HOW
0. Assures toi que l'API de DataHub tourne sur ton poste (suit les instructions dans `datahub_api/README.md`)
1. Démarres le serveur MCP développé par l'équipe ( `FULL_MCP=TRUE mvn spring-boot:run` ou `uv run python datahub_mcp_server.py`)
2. Améliorer les descriptions une à une, à partir des problèmes identifiés
3. Testes dans COpilot avec les prompts d'exemple (avant/après) pour t'assurer de la correction


### Problèmes identifiés

#### Problème 1 : Paramètre `scope` trop vague
- **Prompt** : "Cherche dans DataHub tout ce qui parle d'APIs" → peut utiliser `scope="all"` ❌
- **Symptôme** : Le LLM utilise parfois le tool avec `scope="all"` ou `scope="everything"` qui n'existent pas.
- **Cause** : La description du paramètre `scope` ne liste pas explicitement les valeurs possibles.
- **À améliorer** : La description du paramètre `scope` dans le tool `search_datahub`

#### Problème 2 : Paramètre `limit` sans recommandation
- **Prompt** : "Liste tous les documents" → peut utiliser `limit=1000` ❌
- **Symptôme** : Le LLM utilise parfois `limit=1000` ou `limit=5` sans raison.
- **Cause** : Pas d'indication sur les valeurs recommandées.
- **À améliorer** : La description du paramètre `limit`

#### Problème 3 : Confusion tool search vs resource document
- **Prompt** : "Montre-moi le document sur Kubernetes" → peut chercher `doc_id="kubernetes"` ❌  
- **Symptôme** : Le LLM utilise le tool `search_datahub` alors qu'il connaît déjà le `doc_id`.
- **Cause** : Pas de guidance claire sur quand utiliser search vs get_document.
- **À améliorer** : Les descriptions des deux pour clarifier leur usage respectif


## VALIDATION CRITERIA

- Les descriptions des tools et resources ont été améliorées avec des informations concrètes
- Les tests montrent une meilleure précision du LLM (moins d'erreurs de paramètres)
- Les prompts ambigus sont mieux interprétés