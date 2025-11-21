package com.wallet; // Ваш пакет

import com.wallet.gui.WalletApp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main {
    public static void main(String[] args) {
        // Для запуска JavaFX приложения используется статический метод launch
        Application.launch(WalletApp.class, args);
    }
}


