# OWLAPI

## A project választásának okai:

A csapat egy olyan Java nyelvű projectet választott, ami remélhetőleg hasznos és több segédanyag áll hozzá rendelkezésre. Sajnos a házi feladat elkezdése során szembesültünk azza a problémával, hogy a project futtatása, az ekkora kódbázis kezelése és a rengeteg, már rendelkezésre álló teszt meglehetősen nehezíti a házi feladat elkészítését. Utólag rájöttünk, hogy érdemesebb lett volna egy kisebb és ezáltal átláthatóbb projectet választani. 

## A project célja:

Az OWLAPI egy rendkívül komplex és sokrétű alkalmazás. Legfőképpen ontológiák betöltésére, módosítására és manipulálására használható, mint például:

- Ontológiák betöltése fájlból
- Új ontológia létrehozása
- Ontológiák összefűzése
- Ontológiák bejárása
- Classok, entity-k létrehozása, módosítása, törlése
- Axiómák hozzáadása, törlése
- Kapcsolatok felvétele
- Beépített tag-ek használata, mint például `rdfs:label`, `rdfs:comment`
- Következtetők használata (pl: HermiT)
- és még sok másra...

## A választott feladatok a projecten:

### Technológia fókusz

- [Build keretrendszer beüzemelése, ha még nincs (Maven, Gradle...) + CI beüzemelése, ha még nincs (Actions, Travis, AppVeyor, Azure Pipelines...)](doc/CI%20beuzemelese.md)
- [Manuális kód átvizsgálás elvégzése az alkalmazás egy részére (GitHub, Gerrit...)](doc/ManualReview.md) + [Statikus analízis eszköz futtatása és jelzett hibák átnézése (SonarQube, SpotBugs, VS Code Analyzer, Codacy, Coverity Scan...)](/doc/StaticAnalysis.md).

### Termék/felhasználó fókusz

- [Nem-funkcionális jellemzők vizsgálata (teljesítmény, stresszteszt, biztonság, használhatóság...)](/doc/NemFunkcionalis.md)
- [BDD tesztek készítése (Cucumber, Specflow...)](/doc/BDD (Cucumber).md)

## Elsajátított ismeretek:

- GitHub Actions használata: buildelés, tesztek futtatása, cachelés
- Manuális kód átvizsgálás menete, nehézségei
- Különböző statikus analízis eszközök kipróbálása, használata, mint például a SonarQube és a SpotBugs
- Nem-funkcionális jellemzők vizsgálatára vonatkozó eszközök, nehézségek
- Megismerkedtünk a Cucumber teszteléssel, mellyel eddigi tanulmányaink során még nem találkoztunk
- Verziókezelés, issue-k használata, pull requestek alkalmazása
- Csapatmunka nehézségei, mások munkájának és kódjának ellenőrzése, értékelése
