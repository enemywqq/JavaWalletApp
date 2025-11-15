package com.wallet.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class WalletApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        // 1. Загрузка FXML-разметки
        // FXML Loader ищет main_scene.fxml относительно класса WalletApp
        FXMLLoader fxmlLoader = new FXMLLoader(WalletApp.class.getResource("main_scene.fxml"));

        // Устанавливаем начальный размер окна
        Scene scene = new Scene(fxmlLoader.load(), 850, 650);

        // 2. ✅ Надежная загрузка CSS с использованием абсолютного пути
        try {
            // Ищем ресурс по полному пути: /пакет/имя_файла
            String cssPath = Objects.requireNonNull(getClass().getResource("/com/wallet/gui/styles.css")).toExternalForm();
            scene.getStylesheets().add(cssPath);
            System.out.println("CSS успешно загружен из: " + cssPath);
        } catch (Exception e) {
            System.err.println("Ошибка при загрузке CSS. Проверьте путь и наличие файла styles.css.");
            // e.printStackTrace(); // Можно раскомментировать для детального вывода
        }

        // 3. Настройка и отображение окна
        stage.setTitle("MoneyFlow Wallet Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}