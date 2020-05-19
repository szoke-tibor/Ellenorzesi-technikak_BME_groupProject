# CI létrehozása Actionsben

Új CI-t hozunk létre a projekthez, ami ellenőrzi a tesztek helyes lefutását és utána deploy-olja a buildelt projektet.

* ### Keret létrehozása
  
  Létrehoztunk GitHubon egy Maven with Java keretet az actions-ben és megírtuk a hozzá tartozó jobokat.

* ### Test job

  Test job-ban a Dinamikus teszteket futtatjuk, a megfelelő konfigurációval.

* ### Artifact

  Az artifact-ot GitHub Package-be publikáljuk.
