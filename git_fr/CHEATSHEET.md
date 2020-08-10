# Rappels Git

## Configuration recommandée
* `git config --global user.name "John Doe"` renseigne le nom de l'utilisateur utilisé dans les commits
* `git config --global user.email johndoe@example.fr` renseigne l'email de l'utilisateur utilisé dans les commits

* `git config --global push.default current` utilise par défaut le même nom de branche pour le remote

## Les titres de commits
Ces titres sont la plupart du temps lus par d'autres personnes que l'auteur et doivent aider à comprendre l'**intention** derrière la modification.  
L'**intention** reflète le **pourquoi** et non le comment.  
Le comment n'a pas d'intérêt comme titre car il est directement disponible dans les fichiers modifiés.

On estime qu'un bon titre de commit commence par une majuscule et un verbe et ne dépasse pas les 50 caractères.

Il peut être nécessaire d'apporter des élèments de contexte ou des explications.
Dans ce cas une description peut être ajouté au titre après avoir sauté une ligne

```
Limit numbers of API calls

To avoid using too much CPU on the only node the
app is deployed on, we choose to limit API calls
to a maximum of **100 per minute**.

Further infrastructure improvements can lead to
an increase of this value.
```

Plus d'informations peuvent être trouvées ici : https://chris.beams.io/posts/git-commit/

## Commandes
* `git clone <url>` clone un dépôt distant
* `git status` affiche l'état du dossier de travail
* `git log --oneline` affiche l'historique de la branche courante (du plus récent au plus vieux)
  * `git log --oneline -n 10` affiche les 10 derniers commits de la branche courante
* `git fetch --all --prune` récupère la base de donnée Git du remote (ne change pas le répertoire de travail)

* `git add <file>` indexe un fichier
  * `git add <folder>` indexe toutes les modifications dans le répertoire indiqué
  * `git add .` indexe toutes les modifications
* `git reset <file>` désindexe un fichier

* `git commit -m "<titre>"` crée un commit avec les modifications indexées et le titre indiqué
  * `git commit` crée un commit avec les modifications indexées et le message renseigné dans l'outil d'édition qui est alors ouvert

* `git checkout <branch_name>` bascule sur la branche indiquée
  * `git checkout -b <branch_name>` crée une nouvelle branche et bascule dessus
* `git branch -D <branch_name>` supprime la branche indiquée

* `git push` publie la branche courante sur le remote
  * `git push --force-with-lease` écrase la branche distante avec l'historique local

* `git rebase origin/<branch_name>` rebase la branche courante par rapport à sa version distante, c'est-à-dire déplace les commits locaux après les derniers commits distants
  * cette commande est préférable a un merge, car elle ne crée pas de commit supplémentaire
  * cette commande permet de retrouver le statut **fast-forward** d'une branche, c'est à dire de n'avoir que des commits **en plus** par rapport à la branche distante
  * en cas de conflit, le rebase s'arrête sur chaque commit conflictuel
    * une fois les conflits résolus (marqués comme tels avec `git add <conflicting_file>`), le rebase peut être continué avec la commande `git rebase --continue`
    * si la résolution de conflit mène a une absence de changement, le commit peut être sauté avec `git rebase --skip`
    * pendant un rebase, il est toujours possible de revenir dans l'état d'avant le début du rebase avec `git rebase --abort`

* `git commit --amend` modifie le dernier commit en y ajoutant les fichiers indexés, cette commande ne crée donc pas un nouveau commit
  * `git commit --amend --no-edit` modifie le dernier commit en y ajoutant les fichiers indexés sans changer le message de commit
  * :warning: en modifiant un commit qui a été publié sur le remote, la branche locale diverge, il faudra utiliser `git push --force-with-lease` pour écraser la version distante

* `git rebase -i <sha>` retravaille l'historique sur les commits après celui dont le **sha** est indiqué
  * le **sha** des commits peut être visualisé avec la commande `git log --oneline`
  * ce type de rebase, dit rebase *interactif*, permet pour chaque commit sélectionné de
    * changer les modifications
    * renommer le messages
    * changer l'ordre
    * le fusionner avec un autre commit
    * le supprimer
