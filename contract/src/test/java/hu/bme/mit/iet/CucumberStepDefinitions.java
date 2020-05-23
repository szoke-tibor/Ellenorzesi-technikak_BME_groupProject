package hu.bme.mit.iet;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;

import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;


public class CucumberStepDefinitions {

    OWLOntologyManager manager;
    OWLOntology ontology;
    IRI IOR;

    @Given("^Create a fresh ontology manager to handle ontologies$")
    public void createOntologyManager() {
        manager = OWLManager.createOWLOntologyManager();
    }

    @When("^Load the (.*) ontology$")
    public void loadOntologyFromUrl(String url) throws Throwable {
        ontology = manager.loadOntology(IRI.create(url));
        IOR = IRI.create("http://home.mit.bme.hu/~fandrew/integralt/szepmuveszeti-dbpedia.2017-03-10.owl");
    }

    @When("^Add (.*) subclass to the (.*) class$")
    public void addSubclassAxiom(String subclass, String superclass) {
        OWLDataFactory df = ontology.getOWLOntologyManager().getOWLDataFactory();
        OWLClass classAgent = df.getOWLClass(IOR + "#", subclass);
        OWLClass classPerson = df.getOWLClass(IOR + "#", superclass);
        OWLAxiom axiom = df.getOWLSubClassOfAxiom(classAgent, classPerson);
        AddAxiom addAxiom = new AddAxiom(ontology, axiom);
        manager.applyChange(addAxiom);
    }

    @When("^Remove (.*) subclass from the (.*) class$")
    public void removeSubclassAxiom(String subclass, String superclass) {
        OWLDataFactory df = ontology.getOWLOntologyManager().getOWLDataFactory();
        OWLClass classAgent = df.getOWLClass(IOR + "#", subclass);
        OWLClass classPerson = df.getOWLClass(IOR + "#", superclass);
        OWLAxiom axiom = df.getOWLSubClassOfAxiom(classAgent, classPerson);
        RemoveAxiom removeAxiom = new RemoveAxiom(ontology, axiom);
        manager.applyChange(removeAxiom);
    }

    @Then("^Ontology has (\\d+) axioms$")
    public void hasAxioms(int axiomsCount) {
        assertThat(ontology.getAxiomCount(), equalTo(axiomsCount));
    }

    @Then("^Ontology has (\\d+) logical axioms$")
    public void hasLogicalAxioms(int logicalAxiomsCount) {
        assertThat(ontology.getLogicalAxiomCount(), equalTo(logicalAxiomsCount));
    }

    @Then("^Ontology has (.*) format$")
    public void checkFormat(String format) {
        assertThat(Objects.requireNonNull(manager.getOntologyFormat(ontology)).getKey(), equalTo(format));
    }

}