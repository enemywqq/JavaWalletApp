package com.wallet.gui;

import com.wallet.service.WalletService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent; // НОВЫЙ ИМПОРТ
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton; // НОВЫЙ ИМПОРТ
import javafx.scene.control.ToggleGroup; // НОВЫЙ ИМПОРТ
import javafx.scene.layout.VBox; // НОВЫЙ ИМПОРТ

public class MainController {

    // --- FXML ССЫЛКИ НА СТАРЫЕ ЭЛЕМЕНТЫ ---
    @FXML
    private ListView<String> walletListView;

    @FXML
    private TableView<?> operationsTableView;

    // --- FXML ССЫЛКИ ДЛЯ КАСТОМНОГО ПЕРЕКЛЮЧАТЕЛЯ (КРИТИЧНО) ---
    @FXML
    private ToggleButton walletsTabButton;
    @FXML
    private ToggleButton operationsTabButton;
    @FXML
    private VBox walletsContent;
    @FXML
    private VBox operationsContent;



    private WalletService walletService;

    public void setWalletService(WalletService walletService){
        this.walletService = walletService;
        loadIn
    }

    /**
     * Вызывается после того, как все элементы FXML загружены.
     */
    @FXML
    public void initialize() {
        System.out.println("Controller initialized successfully.");

        // --- ЛОГИКА КАСТОМНОГО ПЕРЕКЛЮЧАТЕЛЯ (Инициализация ToggleGroup) ---
        ToggleGroup group = new ToggleGroup();
        walletsTabButton.setToggleGroup(group);
        operationsTabButton.setToggleGroup(group);

        // Гарантируем, что всегда выбрана хотя бы одна кнопка (чтобы не было пустого экрана)
        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null && oldValue != null) {
                ((ToggleButton) oldValue).setSelected(true);
            }
        });

        // --- Заполнение ListView тестовыми данными ---
        ObservableList<String> walletItems = FXCollections.observableArrayList(
                "Кошелек для наличных (0 ₽)",
                "Счет в банке Сбер (12 500 ₽)",
                "Криптокошелек (5 000 ₽)"
        );
        walletListView.setItems(walletItems);
    }

    // --- Действия для кастомного переключателя (ВЫЗЫВАЕТСЯ ИЗ FXML) ---

    @FXML
    private void handleTabSwitch(ActionEvent event) {
        if (walletsTabButton.isSelected()) {
            // Показываем контент Счетов
            walletsContent.setVisible(true);
            operationsContent.setVisible(false);
            System.out.println("Switched to: Wallets");
        } else if (operationsTabButton.isSelected()) {
            // Показываем контент Операций
            walletsContent.setVisible(false);
            operationsContent.setVisible(true);
            System.out.println("Switched to: Operations");
        }
    }

    // --- Действия для кнопок (Button Actions) ---

    @FXML
    public void handleNewOperation() {
        showAlert("Новая операция", "Открыто диалоговое окно для создания операции.");
    }

    @FXML
    public void showSettings() {
        showAlert("Настройки", "Открыто окно настроек.");
    }

    @FXML
    public void addBankAccount() {
        showAlert("Добавить счет", "Открыто диалоговое окно для добавления счета.");
    }

    @FXML
    public void handleDeleteAccount() {
        showAlert("Удалить счет", "Вызов процедуры удаления выбранного счета.");
    }

    @FXML
    public void createNewWallet() {
        showAlert("Новый кошелек", "Открыто диалоговое окно для создания нового кошелька.");
    }

    // --- Вспомогательный метод ---

    /**
     * Показывает простое информационное окно Alert.
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Действие");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Удалены старые методы showWallets, showOperations, showBudgets,
    // так как они заменены handleTabSwitch.
}