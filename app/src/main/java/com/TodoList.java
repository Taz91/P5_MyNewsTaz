package com;

public class TodoList {
    /*
    - Glide
        preload possible : dans un RV :  https://bumptech.github.io/glide/int/recyclerview.html
        Tu as plus d'infos ici pour mettre une taille max, choisir de mettre le cache sur le disque interne ou externe etc, etc...
        https://github.com/bumptech/glide/wiki/Configuration
    - pagination - infinit scroller

    - MostPopular
            MostPopular : 3 types (emailed/viewed/shared), periode 1,7,30j
                https://api.nytimes.com/svc/mostpopular/v2/emailed/7.json?api-key=yourkey
                https://api.nytimes.com/svc/mostpopular/v2/viewed/1.json?api-key=yourkey
                    Cas shared: shared type = email, facebook, twitter, periode = 1,7,30j
                    https://api.nytimes.com/svc/mostpopular/v2/shared/1/facebook.json?api-key=yourkey

                    == mettre config défaut = viewed // 1j
                    == dans le configurateur :
                        - choix nb j 1 / 7 / 30
                        - choix type : emailed / viewed / shared
                        - si choix shared : alors ouvrir sous choix : email, facebook, twitter



    - Search
    - interface graphique de recherche
    - key dans les preferences
    - preferences (choix onglet en front )
    - couleur qd déja lu
    - webview







    */
}
