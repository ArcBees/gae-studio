package com.arcbees.gaestudio.cucumber.stepdefs;

import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.companion.domain.Car;
import com.arcbees.gaestudio.cucumber.page.BasePage;
import com.arcbees.gaestudio.cucumber.page.VisualizerPage;
import com.arcbees.gaestudio.factories.CarFactory;
import com.arcbees.gaestudio.server.rest.RestIT;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import javax.inject.Inject;

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

    @Given("^I create a Car for deletion$")
    public void I_create_a_Car() throws Throwable {
        Car car = carFactory.createFakeCar();
        carId = restIT.createRemoteCar(car);
    }

    @Then("^the Car should be deleted$")
    public void the_Car_should_be_deleted() throws Throwable {
        assertCarIsDeleted(carId);
    }

    private void assertCarIsDeleted(long carId) {
        assertEquals(404, restIT.getRemoteCarResponseCode(carId));
    }

    @And("^I delete the Car$")
    public void I_delete_the_Car() throws Throwable {
        deleteCar();
    }

    private void deleteCar() {
        basePage.navigate(NameTokens.visualizer);

        visualizerPage.clickOnKind(Car.class.getSimpleName());
        visualizerPage.clickOnEntityinTable();
        visualizerPage.clickDeleteButton();
        visualizerPage.clickDeleteConfirmButton();
        basePage.waitForSaveConfirmation("Entity deleted");
    }


}
