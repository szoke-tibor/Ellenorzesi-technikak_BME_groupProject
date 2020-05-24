Feature: Ontology operations

  Scenario: Load ontology
    Given Create a fresh ontology manager to handle ontologies
    When Load the http://home.mit.bme.hu/~fandrew/integralt/szepmuveszeti-dbpedia.2017-03-10.owl ontology
    Then Ontology has 1696 axioms
    And Ontology has 762 logical axioms
    And Ontology has Turtle format

  Scenario: Add and remove axiom
    Given Create a fresh ontology manager to handle ontologies
    When Load the http://home.mit.bme.hu/~fandrew/integralt/szepmuveszeti-dbpedia.2017-03-10.owl ontology
    And Add Agent subclass to the Person class
    Then Ontology has 1697 axioms
    When Remove Agent subclass from the Person class
    Then Ontology has 1696 axioms

  Scenario: Merge two ontology
    Given Create a fresh ontology manager to handle ontologies
    When Load the http://home.mit.bme.hu/~fandrew/integralt/szepmuveszeti-dbpedia.2017-03-10.owl ontology
    And Merge ontology with http://home.mit.bme.hu/~fandrew/iir/pc_shop.owl ontology
    Then Merged ontology has 1804 axioms