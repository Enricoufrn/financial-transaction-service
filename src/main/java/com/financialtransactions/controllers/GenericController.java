package com.financialtransactions.controllers;

import org.springframework.web.util.UriComponentsBuilder;

public class GenericController {
    protected final String CONTROLLER = "controller";
    protected final String ID = "/{id}";
    protected UriComponentsBuilder uriBuilder;
    protected String getByIdPath(){
        return this.getPath() + ID;
    }
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
