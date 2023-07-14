package thevelopers.devsoftware.controller;

import thevelopers.devsoftware.observer.Observer;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public abstract class Controller implements Observer {

    /**
     * Método para puxar eventos da view
     * @param event é o evento que foi puxado do tipo ActionEvent
     */
    public abstract void handleEvent(ActionEvent event);

    /**
     * Método para puxar eventos da view
     * @param event é o evento que foi puxado do tipo MouseEvent
     */
    public abstract void handleEvent(MouseEvent event);
}