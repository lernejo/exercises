# Exercice sur Git

La correction sera automatique, prêtez-donc une attention spéciale aux messages de commit, aux noms des branches etc.

Tous les fichiers que vous indexerez doivent avoir une ligne vide à la fin (en plus du contenu donc).

Les fichiers avec l'extension .md sont au format [Markdown](https://guides.github.com/features/mastering-markdown).

Préfixé par &#x1F4D8;, des "checkpoints" pour vous aider à vérifier que vous avez tout bon.

Les commits non modifiés d'une partie à l'autre doivent avoir le même ID (hash).

Les parties sont notées sur 1 point, le total sur 4 points.

Si une partie n'est pas faite en intégralité, la note de cette partie sera 0.

Les parties se suivent et sont à faire dans l'ordre.

## Partie 0
* installer Git (cf https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
* (Sur Windows, avoir un terminal POSIX type **Git Bash**, la suite de l'exercice est à faire dans celui-ci)
* configurer Git avec votre nom et email
  * `git config --global user.name "John Doe"`
  * `git config --global user.email johndoe@example.fr`
* générer une clé SSH (si absente) et donner la partie publique à GitHub (cf https://help.github.com/articles/connecting-to-github-with-ssh/)

## Partie 1

* créer un nouveau dépôt Git sur la plateforme GitHub avec le nom **git_training** initialisé avec un fichier README.md (case à cocher dans le formulaire de création de dépôt)
* cloner ce nouveau dépôt en utilisant l'**url SSH**
* créer une nouvelle branche nommée **ex/part_1**
* ajouter un fichier nommé **Doc.md** et contenant un titre **Documentation** et un lien hypertexte nommé **Git - Documentation** pointant sur https://git-scm.com/doc
* &#x1F4D8; ce fichier doit donc faire 3 lignes, une pour le titre, une pour le lien, puis une ligne vide
* commiter ce fichier avec le message **Add Git documentation**
* ajouter un fichier nommé **Ex.md** et contenant le titre **Exercice** et un bullet point avec le texte **Part 1**
* commiter ce fichier avec le message **Setup exercise file**
* modifier le fichier Doc.md en ajoutant à la fin un lien hypertexte nommé **Markdown - Documentation** et pointant sur https://guides.github.com/features/mastering-markdown
* commiter ce changement avec le message **Add Markdown doc**
* &#x1F4D8; cette branche a donc 4 commits en tout (Initial commit + les 3 que vous venez de faire)
* pusher la branche (sur votre remote par défaut, en l'occurrence GitHub)

## Partie 2

* créer une nouvelle branche nommée **ex/part_2** à partir de la branche précédente
* pusher cette branche
* modifier le dernier message de commit pour donner **Add Markdown documentation**
* modifier le dernier commit en ajoutant à la fin du fichier **Doc.md** un nouveau lien hypertexte appelé **Markdown bestpractices** et pointant sur https://www.markdownguide.org/basic-syntax/
* &#x1F4D8; a ce stade, cette branche a 4 commits en tout et elle a divergé par rapport à la version du remote
* pusher la branche
* ajouter dans le fichier **Ex.md** un nouveau bullet point avec le texte **Part 2**
* commiter avec le message **Ex Part 2**
* pusher la branche

## Partie 3

* créer une nouvelle branche nommée **ex/part_3** à partir de la branche précédente  
* modifier le fichier **Ex.md** en ajoutant un bullet point avec le texte **Part 3**
* commiter ce changement
* regrouper les commits sur la documentation en 1 seul commit avec comme message **Add documentation**
* regrouper les commits sur le fichier **Ex.md** en 1 seul commit avec comme message **Exercise**
* &#x1F4D8; a ce stade, cette branche a 3 commits (Initial commit + les deux commits gardés sur cette partie)
* pusher la branche

## Partie 4
* créer une nouvelle branche nommée **ex/part_4** à partir de la branche précédente  
* ajouter un fichier **End.md** avec le titre **The end**
* commiter ce fichier avec le message **Back to the future**
* déplacer ce commit entre le commit sur la documentation et celui sur le fichier **Ex.md**
* &#x1F4D8; vous devriez avoir les commits suivants (ce que vous pouvez voir avec `git log --oneline`)
  * Exercise
  * Back to the future
  * Add documentation
  * Initial commit
* pusher la branche
