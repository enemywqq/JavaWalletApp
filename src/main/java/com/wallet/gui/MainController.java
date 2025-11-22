package com.wallet.gui;

import com.wallet.exceptions.AccountNotSelectedException;
import com.wallet.exceptions.ValidationException;
import com.wallet.model.*;
import com.wallet.service.WalletService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainController {

    @FXML private Label totalExpenseBalanceLabel;
    @FXML private Label totalIncomeBalanceLabel;
    @FXML private Label totalBalanceLabel;

    @FXML private ListView<Account> walletListView;
    @FXML private ListView<Object> operationsListView;

    @FXML private VBox walletsContent;
    @FXML private VBox operationsContent;

    @FXML private ToggleButton walletsTabButton;
    @FXML private ToggleButton operationsTabButton;

    @FXML private TextField searchField;
    @FXML private ToggleButton noTransfersToggle;

    private final WalletService walletService;
    private final DecimalFormat currencyFormat = new DecimalFormat("#,##0.00 ₽");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static class DateHeader {
        LocalDate date;
        public DateHeader(LocalDate date) { this.date = date; }
    }

    public MainController(WalletService walletService) {
        this.walletService = walletService;
    }

    @FXML
    public void initialize() {
        setupWalletList();
        setupOperationsList();
        setupFilters();
        updateUI();
    }

    private void setupWalletList() {
        walletListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Account item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox root = new HBox(10);
                    root.setAlignment(Pos.CENTER_LEFT);
                    Label name = new Label(item.getName());
                    name.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                    Label balance = new Label(currencyFormat.format(item.getBalance()));
                    balance.setStyle("-fx-text-fill: #757575;");
                    Region spacer = new Region();
                    HBox.setHgrow(spacer, Priority.ALWAYS);
                    root.getChildren().addAll(name, spacer, balance);
                    setGraphic(root);
                }
            }
        });
    }

    private void setupOperationsList() {
        operationsListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle("-fx-background-color: transparent;");
                    return;
                }

                if (item instanceof DateHeader) {
                    DateHeader header = (DateHeader) item;
                    Label dateLabel = new Label(header.date.format(dateFormatter));
                    dateLabel.getStyleClass().add("date-header-label");
                    HBox root = new HBox(dateLabel);
                    root.setAlignment(Pos.CENTER);
                    setGraphic(root);
                    setMouseTransparent(true);
                }
                else if (item instanceof Operation) {
                    renderOperation((Operation) item);
                    setMouseTransparent(false);
                }
            }

            private void renderOperation(Operation op) {
                String amountText;
                String iconStyle;
                String amountStyleClass;
                VBox centerBox = new VBox(2);
                SVGPath iconSvg = new SVGPath();
                iconSvg.getStyleClass().add("category-icon-svg");

                if (op instanceof Transfer) {
                    Transfer tr = (Transfer) op;
                    iconStyle = "category-blue";
                    amountStyleClass = "operation-amount-transfer";
                    amountText = "-" + currencyFormat.format(op.getAmount());
                    // Стрелки
                    iconSvg.setContent("M10.4113 0V4.65225L0.0367489 4.65225L0.00185585 6.99L10.4113 6.99L10.4113 11.6306L16.2266 5.81531L10.4113 0Z");

                    Label nameLabel = new Label("Перевод");
                    nameLabel.getStyleClass().add("operation-name");
                    String from = tr.getSourceAccount().getName();
                    String to = tr.getDestinationAccount().getName();
                    Label subLabel = new Label(from + " ➔ " + to);
                    subLabel.setStyle("-fx-text-fill: #888; -fx-font-size: 12px;");
                    centerBox.getChildren().addAll(nameLabel, subLabel);
                } else if (op instanceof Income) {
                    iconStyle = "category-green";
                    amountStyleClass = "operation-amount-income";
                    amountText = "+" + currencyFormat.format(op.getAmount());
                    // Стрелка вниз
                    iconSvg.setContent("M11.6289 10.4093L6.97666 10.4093L6.97666 0.0347954L4.63891 -9.78859e-05L4.63891 10.4093L-0.00170657 10.4093L5.8136 16.2246L11.6289 10.4093Z");

                    Label nameLabel = new Label(op.getName());
                    nameLabel.getStyleClass().add("operation-name");
                    Label catLabel = new Label(op.getCategory());
                    catLabel.getStyleClass().add("operation-category");
                    centerBox.getChildren().addAll(nameLabel, catLabel);
                } else {
                    iconStyle = "category-red";
                    amountStyleClass = "operation-amount-expense";
                    amountText = "-" + currencyFormat.format(op.getAmount());
                    // Стрелка вверх
                    iconSvg.setContent("M0 5.81531H4.65225L4.65225 16.1898L6.99 16.2247L6.99 5.81531L11.6306 5.81531L5.81531 0L0 5.81531Z");

                    Label nameLabel = new Label(op.getName());
                    nameLabel.getStyleClass().add("operation-name");
                    Label catLabel = new Label(op.getCategory());
                    catLabel.getStyleClass().add("operation-category");
                    centerBox.getChildren().addAll(nameLabel, catLabel);
                }

                VBox iconBox = new VBox(iconSvg);
                iconBox.getStyleClass().addAll("operation-category-icon", iconStyle);
                Label amountLabel = new Label(amountText);
                amountLabel.getStyleClass().addAll("operation-amount", amountStyleClass);
                HBox root = new HBox(15, iconBox, centerBox, amountLabel);
                root.getStyleClass().add("operation-item-container");
                HBox.setHgrow(centerBox, Priority.ALWAYS);
                setGraphic(root);
            }
        });
    }

    private void setupFilters() {
        if (searchField != null) {
            searchField.textProperty().addListener((o, oldVal, newVal) -> refreshOperationsList());
        }
        if (noTransfersToggle != null) {
            noTransfersToggle.selectedProperty().addListener((o, oldVal, newVal) -> refreshOperationsList());
        }
    }

    private void refreshOperationsList() {
        List<Operation> allOps = walletService.showListOperations();
        if (noTransfersToggle != null && noTransfersToggle.isSelected()) {
            allOps = walletService.filterOutTransfers(allOps);
        }
        if (searchField != null && !searchField.getText().trim().isEmpty()) {
            allOps = walletService.filterBySearch(allOps, searchField.getText().trim());
        }
        Map<LocalDate, List<Operation>> grouped = new TreeMap<>((d1, d2) -> d2.compareTo(d1));
        for (Operation op : allOps) {
            LocalDate date = op.getTimestamp().toLocalDate();
            grouped.computeIfAbsent(date, k -> new ArrayList<>()).add(op);
        }
        List<Object> flatList = new ArrayList<>();
        for (Map.Entry<LocalDate, List<Operation>> entry : grouped.entrySet()) {
            flatList.add(new DateHeader(entry.getKey()));
            flatList.addAll(entry.getValue());
        }
        operationsListView.setItems(FXCollections.observableArrayList(flatList));
    }

    private void updateUI() {
        walletListView.getItems().setAll(walletService.showListAccounts());
        refreshOperationsList();
        totalBalanceLabel.setText(currencyFormat.format(walletService.geTotalBalance()));
        totalIncomeBalanceLabel.setText(currencyFormat.format(walletService.getTotalIncomeBalance()));
        totalExpenseBalanceLabel.setText(currencyFormat.format(walletService.getTotalExpenseBalance()));
    }

    @FXML
    private void addOperation() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Новая операция");
        dialog.setHeaderText("Заполните детали");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        applyDialogStyles(dialog); // БЕЗОПАСНЫЙ ВЫЗОВ

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));

        ChoiceBox<String> typeChoice = new ChoiceBox<>(FXCollections.observableArrayList("Расход", "Доход", "Перевод"));
        typeChoice.getSelectionModel().selectFirst();
        typeChoice.setMaxWidth(Double.MAX_VALUE);

        TextField descField = new TextField(); descField.setPromptText("Например: Продукты");
        TextField amountField = new TextField(); amountField.setPromptText("0.00");
        TextField catField = new TextField(); catField.setPromptText("Категория");

        ChoiceBox<Account> sourceChoice = new ChoiceBox<>(FXCollections.observableArrayList(walletService.showListAccounts()));
        sourceChoice.setMaxWidth(Double.MAX_VALUE);
        ChoiceBox<Account> targetChoice = new ChoiceBox<>(FXCollections.observableArrayList(walletService.showListAccounts()));
        targetChoice.setMaxWidth(Double.MAX_VALUE);

        Label targetLabel = new Label("На счет:");
        targetChoice.visibleProperty().bind(typeChoice.valueProperty().isEqualTo("Перевод"));
        targetChoice.managedProperty().bind(targetChoice.visibleProperty());
        targetLabel.visibleProperty().bind(typeChoice.valueProperty().isEqualTo("Перевод"));
        targetLabel.managedProperty().bind(targetLabel.visibleProperty());

        grid.addRow(0, new Label("Тип:"), typeChoice);
        grid.addRow(1, new Label("Со счета:"), sourceChoice);
        grid.addRow(2, targetLabel, targetChoice);
        grid.addRow(3, new Label("Сумма:"), amountField);
        grid.addRow(4, new Label("Категория:"), catField);
        grid.addRow(5, new Label("Описание:"), descField);

        ColumnConstraints c = new ColumnConstraints(); c.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(new ColumnConstraints(), c);
        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait().ifPresent(type -> {
            if (type == ButtonType.OK) {
                try {
                    String amountStr = amountField.getText().replace(",", ".");
                    if (amountStr.isEmpty()) throw new ValidationException("Введите сумму");
                    BigDecimal amount = new BigDecimal(amountStr);
                    Account sourceAcc = sourceChoice.getValue();
                    if (sourceAcc == null) throw new ValidationException("Выберите счет");

                    String opType = typeChoice.getValue();
                    if ("Доход".equals(opType)) walletService.makeIncome(sourceAcc.getId(), amount, catField.getText(), descField.getText());
                    else if ("Расход".equals(opType)) walletService.makeExpense(sourceAcc.getId(), amount, catField.getText(), descField.getText());
                    else if ("Перевод".equals(opType)) {
                        Account target = targetChoice.getValue();
                        if (target == null) throw new ValidationException("Выберите получателя");
                        if (sourceAcc.getId().equals(target.getId())) throw new ValidationException("Счета должны быть разными");
                        walletService.makeTransfer(sourceAcc.getId(), target.getId(), amount, catField.getText(), descField.getText());
                    }
                    updateUI();
                } catch (Exception e) {
                    showAlert("Ошибка", e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });
    }

    @FXML
    private void addBankAccount() {
        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Новый счет");
        nameDialog.setHeaderText("Название счета");
        applyDialogStyles(nameDialog); // БЕЗОПАСНЫЙ ВЫЗОВ

        nameDialog.showAndWait().ifPresent(name -> {
            if (name.trim().isEmpty()) return;
            TextInputDialog balDialog = new TextInputDialog("0");
            balDialog.setTitle("Баланс");
            balDialog.setHeaderText("Начальный баланс");
            applyDialogStyles(balDialog); // БЕЗОПАСНЫЙ ВЫЗОВ

            balDialog.showAndWait().ifPresent(balStr -> {
                try {
                    walletService.createAccount(name, new BigDecimal(balStr.replace(",", ".")));
                    updateUI();
                } catch (Exception e) {
                    showAlert("Ошибка", e.getMessage(), Alert.AlertType.ERROR);
                }
            });
        });
    }

    @FXML
    private void handleDeleteAccount() {
        Account selected = walletListView.getSelectionModel().getSelectedItem();
        try {
            if (selected == null) throw new AccountNotSelectedException("Выберите счет");
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Удалить счет?", ButtonType.YES, ButtonType.NO);
            applyDialogStyles(confirm); // БЕЗОПАСНЫЙ ВЫЗОВ

            confirm.showAndWait().ifPresent(resp -> {
                if (resp == ButtonType.YES) {
                    walletService.deleteAccount(selected.getId());
                    updateUI();
                }
            });
        } catch (Exception e) {
            showAlert("Внимание", e.getMessage(), Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void handleTabSwitch() {
        walletsContent.setVisible(walletsTabButton.isSelected());
        operationsContent.setVisible(!walletsTabButton.isSelected());
    }

    @FXML
    private void showSettings() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Настройки");
        alert.setHeaderText("Сброс данных");
        alert.setContentText("Удалить ВСЕ данные? Это действие необратимо.");
        applyDialogStyles(alert); // БЕЗОПАСНЫЙ ВЫЗОВ

        ButtonType resetButton = new ButtonType("Сбросить всё", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(resetButton, ButtonType.CANCEL);
        alert.getDialogPane().lookupButton(resetButton).getStyleClass().add("btn-danger");

        alert.showAndWait().ifPresent(type -> {
            if (type == resetButton) {
                // ТЕПЕРЬ РАБОТАЕТ, ТАК КАК МЕТОД ЕСТЬ В SERVICE
                walletService.clearAllData();
                updateUI();
                showAlert("Готово", "Данные очищены.", Alert.AlertType.INFORMATION);
            }
        });
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        applyDialogStyles(alert); // БЕЗОПАСНЫЙ ВЫЗОВ
        alert.showAndWait();
    }

    // ИСПРАВЛЕННЫЙ МЕТОД: Не падает, если CSS не найден
    private void applyDialogStyles(Dialog<?> dialog) {
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStyleClass().add("dialog-pane");

        URL css = getClass().getResource("styles.css");
        if (css == null) css = getClass().getResource("/styles.css"); // Ищем в корне

        if (css != null) {
            dialogPane.getStylesheets().add(css.toExternalForm());
        } else {
            System.err.println("CSS not found, dialog will use default styles.");
        }
    }
}