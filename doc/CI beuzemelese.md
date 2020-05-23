# CI létrehozása Actionsben

Új CI-t hozunk létre a projekthez, ami ellenőrzi a tesztek helyes lefutását és utána deploy-olja a buildelt projektet.

* ### Keret létrehozása
  
  Létrehoztunk GitHubon egy Maven with Java keretet az actions-ben és megírtuk a hozzá tartozó jobokat.

* ### Sonar Cloud

  Sonar Cloud felhő alapú tesztelőt próbáltuk beleintegrálni a CI-ba, azonban nem várt hibába ütköztünk.

* ### Test job

  Test job-ban a Dinamikus teszteket futtatjuk, a megfelelő konfigurációval.

  * ## Cache
    
    Build előtt megnézzük, hogy előző build során lecachelt dolgokból felhasználható-e valami, ha igen fel is használjuk a buildidő csökkentése érdekében.

> * ### Artifact
> 
  > Az artifact-ot GitHub Package-be publikáljuk, amennyiben a tesztek helyesen lefutottak.
