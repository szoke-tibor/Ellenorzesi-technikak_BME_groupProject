package hu.bme.mit.iet;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.OWLEntityRemover;
import org.semanticweb.owlapi.util.OWLOntologyMerger;

import java.util.Objects;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;


public class CucumberStepDefinitions {

    OWLOntologyManager manager;
    OWLOntology ontology;
    IRI IOR;
    OWLOntology ontology2;
    IRI IOR2;
    OWLOntology mergedOntology;

    @Given("^Create a fresh ontology manager to handle ontologies$")
    public void createOntologyManager() {
        manager = OWLManager.createOWLOntologyManager();
    }

    @When("^Load the (.*) ontology$")
    public void loadOntologyFromUrl(String url) throws Throwable {
        ontology = manager.loadOntology(IRI.create(url));
        IOR = IRI.create(url);
    }

    @When("^Merge ontology with (.*) ontology$")
    public void mergeOntologies(String url) throws Throwable {
        ontology2 = manager.loadOntology(IRI.create(url));
        IOR2 = IRI.create(url);
        OWLDataFactory df = ontology.getOWLOntologyManager().getOWLDataFactory();
        manager.addAxiom(ontology2, df.getOWLDeclarationAxiom(df.getOWLClass(IOR2)));
        OWLOntologyMerger merger = new OWLOntologyMerger(manager);
        IRI mergedOntologyIRI = IRI.create("http://home.mit.bme.hu", "newOntology");
        mergedOntology = merger.createMergedOntology(manager, mergedOntologyIRI);
    }

    @When("^Add (.*) as subclass to the (.*) class$")
    public void addSubclassAxiom(String subclass, String superclass) {
        OWLDataFactory df = ontology.getOWLOntologyManager().getOWLDataFactory();
        OWLClass classAgent = df.getOWLClass(IOR + "#", subclass);
        OWLClass classPerson = df.getOWLClass(IOR + "#", superclass);
        OWLAxiom axiom = df.getOWLSubClassOfAxiom(classAgent, classPerson);
        AddAxiom addAxiom = new AddAxiom(ontology, axiom);
        manager.applyChange(addAxiom);
    }

    @When("^Remove (.*) as subclass from the (.*) class$")
    public void removeSubclassAxiom(String subclass, String superclass) {
        OWLDataFactory df = ontology.getOWLOntologyManager().getOWLDataFactory();
        OWLClass classAgent = df.getOWLClass(IOR + "#", subclass);
        OWLClass classPerson = df.getOWLClass(IOR + "#", superclass);
        OWLAxiom axiom = df.getOWLSubClassOfAxiom(classAgent, classPerson);
        RemoveAxiom removeAxiom = new RemoveAxiom(ontology, axiom);
        manager.applyChange(removeAxiom);
    }

    @When("^Add the (.*) class as disjoint class with LCD class$")
    public void addDisjointClassesWithAxiom(String newClassName) {
        OWLDataFactory factory = ontology.getOWLOntologyManager().getOWLDataFactory();
        OWLClass classLCD = factory.getOWLClass(IRI.create(IOR + "#LCD" ));
        OWLClass newClass = factory.getOWLClass(IRI.create(IOR + "#" + newClassName ));
        OWLAxiom axiom = factory.getOWLDisjointClassesAxiom(classLCD, newClass);
        AddAxiom addAxiom = new AddAxiom(ontology, axiom);
        manager.applyChange(addAxiom);
    }

    @When("^Remove the (.*) class with its axioms as well")
    public void deleteDisjointClassesWithAxiom(String deleteClassName) {
        OWLDataFactory factory = ontology.getOWLOntologyManager().getOWLDataFactory();
        OWLClass deleteClass = factory.getOWLClass(IRI.create(IOR + "#" + deleteClassName ));
        OWLEntityRemover remover = new OWLEntityRemover(ontology);
        remover.visit(deleteClass);
        manager.applyChanges(remover.getChanges());
    }

    @Then("^Merged ontology has (\\d+) axioms$")
    public void mergedHasAxioms(int axiomsCount) {
        assertThat(mergedOntology.getAxiomCount(), equalTo(axiomsCount));
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

    @Then("^LCD class has (\\d+) disjoint class$")
    public void hasDisjointClasses(long disjointCount) {
        OWLDataFactory factory = ontology.getOWLOntologyManager().getOWLDataFactory();
        OWLClass classLCD = factory.getOWLClass(IRI.create(IOR + "#LCD" ));
        long lcdDisjoint = ontology.disjointClassesAxioms(classLCD).count();
        assertThat(lcdDisjoint, equalTo(disjointCount));
    }
}