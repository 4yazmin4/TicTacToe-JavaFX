package tictactoe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TicTacToe extends Application {
    private String[][] gameGrid = {{"-", "-", "-"}, {"-", "-", "-"}, {"-", "-", "-"}};
    private final String O = "O";
    private final String X = "X";
    private boolean isPlayer1Turn = true; // Track the current player

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tic Tac Toe ^_^!");

        // Create a GridPane
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10); // Vertical space between buttons
        gridPane.setHgap(10); // Horizontal space between buttons
        gridPane.setPadding(new Insets(20)); // Space around the grid
        gridPane.setAlignment(Pos.CENTER); // Center the grid within the parent
        gridPane.setStyle("-fx-background-color: #ADD8E6;");

        Button[][] buttons = new Button[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new Button("-");
                buttons[i][j].getStyleClass().add("button");
                gridPane.add(buttons[i][j], j, i);

                int row = i;
                int col = j;
                buttons[i][j].setOnAction(e -> {
                    handleButtonClick(row, col, buttons);
                    buttons[row][col].getStyleClass().add(isPlayer1Turn ? "player1" : "player2");
                });
            }
        }


        // Use StackPane to hold the GridPane
        StackPane layout = new StackPane();
        layout.getChildren().add(gridPane);
        layout.setAlignment(Pos.CENTER); // Center the grid within the scene
        

        Scene scene = new Scene(layout, 400, 450); // Adjusted scene size
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(scene);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleButtonClick(int row, int col, Button[][] buttons) {
        String currentSymbol = buttons[row][col].getText();
        if (!currentSymbol.equals("-")) {
            return; // Do nothing if the button is already occupied.
        }

        String currentPlayerSymbol = isPlayer1Turn ? O : X;
        buttons[row][col].setText(currentPlayerSymbol);

        if (checkWin(convertButtonGridToString(buttons), currentPlayerSymbol)) {
            String winner = isPlayer1Turn ? "Player 1" : "Player 2";
            System.out.println(winner + " won!");
            Platform.exit(); // Exit the game when someone wins.
        } else if (isGridFull(convertButtonGridToString(buttons))) {
            System.out.println("It's a draw!");
            Platform.exit(); // Exit the game if it's a draw.
        } else {
            isPlayer1Turn = !isPlayer1Turn; // Switch turns.
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
}
