//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sample;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.beans.property.BooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBase;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

class ButtonAnimationHelper {
    static final String SHRINK_ANIMATE_ON_PRESS_PROPERTY_NAME = "-shrink-animate-on-press";
    private static final Duration SCALE_TRANSITION_DURATION = Duration.millis(400.0D);
    private static final double SCALE_ON_PRESS = 0.97D;
    private EventHandler<MouseEvent> buttonPressed = this::onButtonPressed;
    private EventHandler<MouseEvent> buttonReleased = this::onButtonReleased;
    private EventHandler<KeyEvent> keyPressed = this::onKeyPressed;
    private EventHandler<KeyEvent> keyReleased = this::onKeyReleased;
    private boolean isKeyPressed = false;
    private BooleanProperty buttonShrinkAnimateOnPressProperty;
    private ButtonBase button;

    private ButtonAnimationHelper(ButtonBase button, BooleanProperty buttonShrinkAnimateOnPressProperty) {
        this.buttonShrinkAnimateOnPressProperty = buttonShrinkAnimateOnPressProperty;
        this.button = button;
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, this.buttonPressed);
        button.addEventHandler(MouseEvent.MOUSE_RELEASED, this.buttonReleased);
        button.addEventHandler(KeyEvent.KEY_PRESSED, this.keyPressed);
        button.addEventHandler(KeyEvent.KEY_RELEASED, this.keyReleased);
    }

    static ButtonAnimationHelper setupButton(ButtonBase button, BooleanProperty buttonShrinkAnimateOnPressProperty) {
        return new ButtonAnimationHelper(button, buttonShrinkAnimateOnPressProperty);
    }

    private void onButtonPressed(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            this.performShrink();
        }
    }

    private void onButtonReleased(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            this.performUnshrink();
        }
    }

    private void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE) {
            this.performShrink();
            this.isKeyPressed = true;
        }

    }

    private void onKeyReleased(KeyEvent event) {
        if (this.isKeyPressed) {
            this.performUnshrink();
            this.isKeyPressed = false;
        }

    }

    private void performShrink() {
        if (this.buttonShrinkAnimateOnPressProperty.get()) {
            this.button.setScaleX(0.97D);
            this.button.setScaleY(0.97D);
        }

    }

    private void performUnshrink() {
        if (this.buttonShrinkAnimateOnPressProperty.get()) {
            ScaleTransition scaleTransition = new ScaleTransition(SCALE_TRANSITION_DURATION, this.button);
            scaleTransition.setInterpolator(Interpolator.EASE_OUT);
            scaleTransition.setToX(1.0D);
            scaleTransition.setToY(1.0D);
            scaleTransition.play();
        }

    }

    void dispose() {
        this.button.removeEventHandler(MouseEvent.MOUSE_PRESSED, this.buttonPressed);
        this.button.removeEventHandler(MouseEvent.MOUSE_RELEASED, this.buttonReleased);
        this.button.removeEventHandler(KeyEvent.KEY_PRESSED, this.keyPressed);
        this.button.removeEventHandler(KeyEvent.KEY_RELEASED, this.keyReleased);
    }
}
