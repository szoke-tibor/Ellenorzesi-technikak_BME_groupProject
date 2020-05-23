# Manuális kód átvizsgálás elvégzése az alkalmazás egy részére (GitHub, Gerrit...)

### A kód manuális átvizsgálása során az alábbi hibákat véltük felfedezni:

* **Bennmaradt TODO kommentek, kikommentezett régi kódok:** Fejlesztés során írt TODO kommentek, amiket senki sem végzett el az évek során.

    * Például a `OWLAPIObo2Owl.java` fájlban 25 TODO maradt.
    * A kódban több nem használt függvény, régi kikommentezett kód maradt, amiket el kellett volna távolítani. Az így ottmaradt kódrészleteket senki sem törli, mert nem tudják miért lett otthagyva.
    * Példa egy olyan esetre, ahol a TODO-t félig teljesítették csak.
        
        ```java
        else {
            // TODO: Throw Exceptions
            OBOFormatException e =
            new OBOFormatException ("Cannot translate clause «" + clause + '»');
            LOG.error("Cannot translate: {}", clause, e);
        }
        ```

        * Az exceptiont ugyan létrehozták, de a throw kulcsóval nem kerül eldobásra sosem.
        
    * Arra is látható példa, hogy ugyanazt a TODO kommentet több helyre is bemásolták.

        ```java
        if (id.startsWith("http:")) {
            // TODO - roundtrip from other schemes
            return IRI.create(id);
        } else if (id.startsWith("https:")) {
            // TODO - roundtrip from other schemes
            return IRI.create(id);
        } else if (id.startsWith("ftp:")) {
            // TODO - roundtrip from other schemes
            return IRI.create(id);
        } else if (id.startsWith("urn:")) {
            // TODO - roundtrip from other schemes
            return IRI.create(id);
        }
        ```

        * Ez feleslegesen növeli a kódsorok számát, túlságosan redundáns információ és rontja az olvashatóságot.
        * Elegendő lenne egyszer jelezni az elvégzendő feladatot a kódrészlet elején.

* **Hosszú függvénynevek:** Más tárgyakból tanultuk, hogy a függvények elnevezése során törekedjünk legjobban leírni a funkcióját, de közben kerüljük el a már-már túl hosszú neveket. Továbbá nem érdemes sok függvényt hasonlóan elnevezni, mert ha csak egy-egy szóban térnek el, akkor nehéz lehet őket megkülönböztetni egymástól.

    * A `UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom()` és a `UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom()` hosszú névvel rendelkeznek és nehéz őket megkülönböztetni.
    
* **Hosszú paraméterlista:** Clean Code elveknél tanultuk, hogy nem ajánlott 3-nál több paraméter használata, mert ezek csökkentik a könnyű használhatóságát és nehéz észben tartani, hogy vajon mit kell írni a 6. paraméter helyére.

    *  Például: `translateNegativeDataPropertyAssertion(IRI s, IRI p, IRI o, IRI source, IRI property, OWLLiteral target, Set<OWLAnnotation> annos)`

* **Beszédesebb változónevek használata:** Célszerű lenne kerülni az egy-egy betűből álló változónevek használatát.

    ```java
    OWLAnnotationProperty p = AnnotationProperty(iri("p"));
    OWLAnnotationSubject i = iri("i");
    OWLLiteral v = Literal("value");
    ```

    * A lentebb látható példában szintén egy-egy betűket adunk át, amiből nagyon nehéz megmondani, hogy valójában mit is jelenthet az N, R, P vagy az L. Valószínűleg az N a Node, az R a Resource, az L pedig a Literalt akarja jelölni.

        ```java
        public abstract class AbstractTranslator<N extends Serializable, R extends N, P extends N, L extends N>
        ```

* **Zavaros javadoc:** A javadoc azt lenne hivatott szolgálni, hogy aki olvassa a kódot könnyedén megértse például egy függvény működését azáltal, hogy elolvassa a javadoc-ban szereplő információkat (függvény viselkedésének, paraméterek és a visszatérési érték magyarázata).

    ```java
    /**
     * Gets the qV int.
     *
     * @param q the q
     * @param quals the quals
     * @return the qV int
     */
    ```
    
    * Látható, hogy a függvény viselkedésének lényege egy integer változó kinyerése, azt azonban nem tudjuk meg, hogy milyen érték ez.
    * A hosszabb és gyakran ismétlődő szavak rövidítése ugyan hasznos lehet például a paraméternevek megválasztása során, azonban erősen ajánlott elkerülni azt a dokumentáció írása során.
        * Jelen esetben például a javadoc-ból nem tudjuk meg, hogy a qV minek a rövidítése.
    * A q nevű paraméterről például nem tudjuk meg, hogy milyen célt szolgál, ugyanis a "q the q" magyarázat lényegében semmit sem ad hozzá az olvasó által olvasott kód megértéséhez.
    * Ugyanez elmondható a "quals the quals" sorról is.

    ```java
    /**
     * Tr term frame clauses.
     *
     * @param cls the cls
     * @param clauses the clauses
     * @param t the t
     * @return the sets the
     */
    ```

    * Itt is látható néhány fent leírt súlyos hiba.
    * Ráadásul itt még a visszatérési érték leírása is teljesen értelmetlen. Látszik, hogy a dokumentálást rendkívüli módon félvállról vette a fejlesztő, ez által a későbbi fejlesztő munkáját és a kód karbantarthatóságát szignifikánsan megnehezítve.

* **Struktúrálatlan kód:** Kódírás során rendkívül fontos, hogy ne csak a funkcionalitást és a hatékonyságot tartsuk szem előtt, hanem a kód olvashatóságát is a kód későbbi karbantarthatósága miatt.

    * Például: a `trTypedefClause` nevű függvény törzsében található egy 16 ágú if-else szerkezet, mely rendkívül átláthatatlanná teszi a kódot. Switch szerkezettel sokkal tisztább képet alkotna a kód.
    * Szintén a `trTypedefClause` nevű függvényben az említett nagy méretű if-else szerkezeten belül található az alábbi kód:

        ```java
            else if (tagConstant == OboFormatTag.TAG_HOLDS_OVER_CHAIN
                || tagConstant == OboFormatTag.TAG_EQUIVALENT_TO_CHAIN) {
                if (tagConstant == OboFormatTag.TAG_EQUIVALENT_TO_CHAIN) {
                    OWLAnnotation ann = fac.getOWLAnnotation(
                        trAnnotationProp(IRI_PROP_ISREVERSIBLEPROPERTYCHAIN), trLiteral(TRUE));
                    annotations.add(ann);
                }
                List<OWLObjectPropertyExpression> chain = new ArrayList<>();
                chain.add(trObjectProp(v));
                chain.add(trObjectProp(clause.getValue2()));
                ax = fac.getOWLSubPropertyChainOfAxiom(chain, p, annotations);
        ```
    
        * Egy else if szerkezeten belül ami 2 feltételt foglal magába felbontja a két esetet egy újabb if szerkezettel, ahelyett, hogy a 2 eset egy-egy else if-ként lenne reprezentálva.
        * Ez szintén a kód olvashatatlanságához vezet.
