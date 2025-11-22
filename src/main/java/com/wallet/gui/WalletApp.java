package com.wallet.gui;

import com.wallet.gui.MainController;
import com.wallet.service.WalletService;
import com.wallet.service.WalletServiceImpl;
import com.wallet.storage.InMemoryStorageImpl;
import com.wallet.storage.Storage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WalletApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Storage storage = new InMemoryStorageImpl();
        WalletService walletService = new WalletServiceImpl(storage);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main_scene.fxml"));


        loader.setControllerFactory(param -> new MainController(walletService));

        Scene scene = new Scene(loader.load());
        stage.setTitle("Wallet App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}