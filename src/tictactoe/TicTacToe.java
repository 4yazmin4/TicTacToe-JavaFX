package tictactoe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TicTacToe extends Application {
    private final String O = "O";
    private final String X = "X";
    private boolean isPlayer1Turn = true; // Track the current player

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Ask for Player Names
        String player1Name = getPlayerName("Player 1");
        if (player1Name == null) Platform.exit();

        String player2Name = getPlayerName("Player 2");
        if (player2Name == null) Platform.exit();

        // Primary Stage Title
        primaryStage.setTitle("Tic Tac Toe ^_^");

        // Create a Label to Display the Turn
        Label turnLabel = new Label(player1Name + "'s turn (" + O + ")");
        turnLabel.getStyleClass().add("turn-label");

        // Create a GridPane
        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setPadding(new Insets(20));
        gridPane.setAlignment(Pos.CENTER);

        // Button array for the Tic Tac Toe grid
        Button[][] buttons = new Button[3][3];

        // Initialize the buttons and add them to the grid
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new Button("-");
                buttons[i][j].getStyleClass().add("button");
                buttons[i][j].setPrefSize(80, 80); // Adjust button size for better visibility
                gridPane.add(buttons[i][j], j, i);

                // Button action
                int row = i;
                int col = j;
                buttons[i][j].setOnAction(e -> handleButtonClick(row, col, buttons, turnLabel, player1Name, player2Name));
            }
        }

        // Create the Restart Button
        Button restartButton = new Button("Restart");
        restartButton.getStyleClass().add("restart-button");
        restartButton.setOnAction(e -> restartGame(buttons, turnLabel, player1Name));

        // Arrange everything in a VBox
        VBox layout = new VBox(10);
        layout.getChildren().addAll(turnLabel, gridPane, restartButton);
        layout.setAlignment(Pos.CENTER);

        // Create the Scene
        Scene scene = new Scene(layout, 400, 500);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String getPlayerName(String defaultName) {
        TextInputDialog dialog = new TextInputDialog(defaultName);
        dialog.setTitle("Tic Tac Toe ^_^");
        dialog.setHeaderText("Enter " + defaultName + "'s name:");
        dialog.setContentText("Name:");
        return dialog.showAndWait().orElse(null);
    }

    private void handleButtonClick(int row, int col, Button[][] buttons, Label turnLabel, String player1Name, String player2Name) {
        String currentSymbol = buttons[row][col].getText();
        if (!currentSymbol.equals("-")) {
            return; // Do nothing if the button is already occupied.
        }

        String currentPlayerSymbol = isPlayer1Turn ? O : X;
        buttons[row][col].setText(currentPlayerSymbol);
        buttons[row][col].getStyleClass().add(isPlayer1Turn ? "player1" : "player2");

        if (checkWin(convertButtonGridToString(buttons), currentPlayerSymbol)) {
            String winner = isPlayer1Turn ? player1Name : player2Name;
            turnLabel.setText(winner + " won!");
            disableButtons(buttons);
        } else if (isGridFull(convertButtonGridToString(buttons))) {
            turnLabel.setText("It's a draw!");
            disableButtons(buttons);
        } else {
            isPlayer1Turn = !isPlayer1Turn; // Switch turns
            turnLabel.setText((isPlayer1Turn ? player1Name : player2Name) + "'s turn (" + (isPlayer1Turn ? O : X) + ")");
        }
    }

    private String[][] convertButtonGridToString(Button[][] buttons) {
        String[][] grid = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = buttons[i][j].getText();
            }
        }
        return grid;
    }

    private boolean checkWin(String[][] grid, String symbol) {
        for (int i = 0; i < 3; i++) {
            // Check rows and columns
            if ((grid[i][0].equals(symbol) && grid[i][1].equals(symbol) && grid[i][2].equals(symbol)) ||
                (grid[0][i].equals(symbol) && grid[1][i].equals(symbol) && grid[2][i].equals(symbol))) {
                return true;
            }
        }
        // Check diagonals
        return (grid[0][0].equals(symbol) && grid[1][1].equals(symbol) && grid[2][2].equals(symbol)) ||
               (grid[0][2].equals(symbol) && grid[1][1].equals(symbol) && grid[2][0].equals(symbol));
    }

    private boolean isGridFull(String[][] grid) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j].equals("-")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void disableButtons(Button[][] buttons) {
        for (Button[] row : buttons) {
            for (Button button : row) {
                button.setDisable(true);
            }
        }
    }

    private void restartGame(Button[][] buttons, Label turnLabel, String player1Name) {
        // Reset the grid
        for (Button[] row : buttons) {
            for (Button button : row) {
                button.setText("-");
                button.setDisable(false);
                button.getStyleClass().removeAll("player1", "player2");
            }
        }

        // Reset the turn
        isPlayer1Turn = true;
        turnLabel.setText(player1Name + "'s turn (" + O + ")");
    }
}


