package com.wallet.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class WalletApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // 1. Загрузка FXML. Путь должен быть точным.
        // Мы используем /com/wallet/gui/main_scene.fxml
        URL fxmlLocation = getClass().getResource("/com/wallet/gui/main_scene.fxml");

        // ЖЕСТКАЯ ПРОВЕРКА: Если файл не найден, приложение упадет с понятной ошибкой сразу
        if (fxmlLocation == null) {
            throw new IllegalStateException("CRITICAL ERROR: FXML file not found at /com/wallet/gui/main_scene.fxml");
        }

        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();

        // 2. Загрузка CSS.
        URL cssLocation = getClass().getResource("/com/wallet/gui/styles.css");
        if (cssLocation == null) {
            System.err.println("WARNING: CSS file not found at /com/wallet/gui/styles.css");
        } else {
            root.getStylesheets().add(cssLocation.toExternalForm());
        }

        // 3. Настройка сцены
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MoneyFlow - Финансы");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}