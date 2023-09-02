package com.financialtransactions.controllers;

public class GenericController {
    private final String CONTROLLER = "controller";
    /**
     * Get the base path of the controller.
     *
     * @return String
     */
    protected String getPath() {
        String controllerName = this.inferControllerName();
        controllerName = controllerName.replaceAll("([a-z])([A-Z]+)", "$1-$2");
        return controllerName;
    }
    /**
     * Get the controller name
     *
     * @return String
     */
    private String inferControllerName() {
        String controllerName = this.getClass().getSimpleName().toLowerCase();
        return controllerName.replaceAll(CONTROLLER, "");
    }
}
