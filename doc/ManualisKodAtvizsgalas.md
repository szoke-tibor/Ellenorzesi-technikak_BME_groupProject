# Manuális kód átvizsgálás elvégzése az alkalmazás egy részére (GitHub, Gerrit...)

### A kód manuális átvizsgálása során az alábbi hibákat véltük felfedezni:

* **Bennmaradt TODO kommentek, kikommentezett régi kódok:** 
    * Fejlesztés során írt TODO kommentek, amiket senki sem végzett el az évek során. Például a `OWLAPIObo2Owl.java` fájlban 25 TODO maradt.
    * A kódban több nem használt függvény, régi kikommentezett kód maradt, amiket el kellett volna távolítani. Az így ottmaradt kódrészleteket senki sem törli, mert nem tudják miért lett otthagyva. 
* **Hosszú függvény nevek:** Más tárgyakból tanultuk, hogy a függvények elnevezése során törekedjünk legjobban leírni a funkcióját, de közben kerüljük el a már-már túl hosszú neveket. Továbbá nem érdemes sok függvényt hasonlóan elnevezni, mert ha csak egy-egy szóban térnek el, akkor nehéz lehet őket megkülönböztetni egymástól.
    * A `UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom()` és a `UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom()` hosszú névvel rendelkeznek és nehéz őket megkülönböztetni.
* **Hosszú paraméterlista:** Clean Code elveknél tanultuk, hogy nem ajánlott 3-nál több paraméter használata, mert ezek csökkentik a könnyű használhatóságát és nehéz észbentartani, hogy vajon mit kell írni a 6. paraméter helyére.
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