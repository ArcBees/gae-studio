package com.arcbees.gaestudio.cucumber.stepdefs;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.companion.domain.Car;
import com.arcbees.gaestudio.cucumber.page.BasePage;
import com.arcbees.gaestudio.cucumber.page.VisualizerPage;
import com.arcbees.gaestudio.factories.CarFactory;
import com.arcbees.gaestudio.server.rest.RestIT;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertEquals;

public class DeleteEntityStepDefs {
    private RestIT restIT;
    private final BasePage basePage;
    private final VisualizerPage visualizerPage;
    private CarFactory carFactory;
    private Long carId;

    @Inject
    DeleteEntityStepDefs(RestIT restIT,
                         BasePage basePage,
                         VisualizerPage visualizerPage,
                         CarFactory carFactory) {
        this.restIT = restIT;
        this.basePage = basePage;
        this.visualizerPage = visualizerPage;
        this.carFactory = carFactory;
    }

    @Given("^I create an entity for deletion$")
    public void I_create_an_entity() throws Throwable {
        Car car = carFactory.createFakeCar();
        carId = restIT.createRemoteCar(car);
    }

    @Then("^the entity should be deleted$")
    public void the_entity_should_be_deleted() throws Throwable {
        assertEntityIsDeleted(carId);
    }

    private void assertEntityIsDeleted(long carId) {
        assertEquals(404, restIT.getRemoteCarResponseCode(carId));
    }

    @And("^I delete the entity")
    public void I_delete_the_entity() throws Throwable {
        deleteEntity();
    }

    private void deleteEntity() {
        basePage.navigate(NameTokens.visualizer);

        visualizerPage.clickOnKind(Car.class.getSimpleName());
        visualizerPage.clickOnEntityinTable();
        visualizerPage.clickDeleteButton();
        visualizerPage.clickDeleteConfirmButton();
        basePage.waitForSaveConfirmation("Entity deleted");
    }


}
