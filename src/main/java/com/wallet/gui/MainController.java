package com.wallet.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.math.BigDecimal;

public class MainController {

    // ========== Элементы FXML для Скрытия Баланса ==========
    // Эти ID должны совпадать с main_scene.fxml
    @FXML
    private Label totalBalanceValue;
    @FXML
    private Button toggleBalanceButton;

    // ========== Элементы FXML, которые пока не используются ==========
    @FXML
    private ListView accountListView;
    @FXML
    private TextField newAccountNameField;
    @FXML
    private TextField newAccountBalanceField;
    @FXML
    private Text statusText;
    @FXML
    private ChoiceBox operationTypeChoice;
    @FXML
    private ComboBox sourceAccountCombo;
    @FXML
    private ComboBox targetAccountCombo;
    @FXML
    private TextField amountField;
    @FXML
    private TextField descriptionField;
    @FXML
    private Text operationStatusText;
    @FXML
    private ComboBox historyAccountCombo;
    @FXML
    private TableView historyTableView;
    // ==========================================================

    // Жестко заданное значение баланса для отображения
    private final BigDecimal currentTotalBalance = new BigDecimal("182500.00");
    private boolean isBalanceVisible = true;
    private final String MASKED_BALANCE = "•••••• ₽";

    // ==========================================================
    // МЕТОД ИНИЦИАЛИЗАЦИИ
    // ==========================================================

    @FXML
    public void initialize() {
        // Устанавливаем начальное значение баланса при старте
        updateTotalBalanceDisplay();

        // ВНИМАНИЕ: Здесь должна быть вся остальная (сейчас отключенная) инициализация.
    }

    // ==========================================================
    // ЛОГИКА Скрытия/Показа Баланса
    // ==========================================================

    /**
     * Метод, привязанный к кнопке (onAction="#handleToggleBalanceVisibility")
     */
    @FXML
    private void handleToggleBalanceVisibility() {
        isBalanceVisible = !isBalanceVisible;
        updateTotalBalanceDisplay();
    }

    /**
     * Обновляет отображение общего баланса
     */
    private void updateTotalBalanceDisplay() {
        if (isBalanceVisible) {
            // Форматируем реальное значение (182 500.00 ₽)
            totalBalanceValue.setText(
                    String.format("%,.2f ₽", currentTotalBalance)
            );
            toggleBalanceButton.setText("👁️");
        } else {
            // Показываем маску
            totalBalanceValue.setText(MASKED_BALANCE);
            toggleBalanceButton.setText("✖️");
        }
    }

    // ==========================================================
    // ЗАГЛУШКИ ДЛЯ ОБРАБОТЧИКОВ (ОСТАВЬТЕ ИХ ПУСТЫМИ)
    // ==========================================================

    @FXML
    private void handleCreateAccount() {
    }

    @FXML
    private void handleDeleteAccount() {
    }

    @FXML
    private void handleExecuteOperation() {
    }

    @FXML
    private void handleShowHistory() {
    }
}