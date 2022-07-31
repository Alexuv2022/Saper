package Saper;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class SaperTheGame {
    static int rows;
    static int cols;
    static int mines;

    private static int stepTillWin;

    static int row;

    static int col;
    static int[][] gameField;

    static String[][] gameFieldForPlayer;
    public static void game() {

            System.out.println("Выберите уровень игры: ");
            System.out.println("1 - Новичёк");
            System.out.println("2 - Любитель");
            System.out.println("3 - Профессионал");
            System.out.println("4 - Особый");

            Scanner scanner = new Scanner(System.in);
            String level = scanner.nextLine();

            gameLevel(level);
            gameField();
            gameFieldForPlayer();
            playingGame();
    }
    public static void gameLevel(String level) {

        if (level.equals("1") || level.equalsIgnoreCase("новичёк")) {
            rows = 9;
            cols = 9;
            mines = 10;

        } else if (level.equals("2") || level.equalsIgnoreCase("любитель")) {
            rows = 16;
            cols = 16;
            mines = 40;

        } else if (level.equals("3") || level.equalsIgnoreCase("профессионал")) {
            rows = 16;
            cols = 30;
            mines = 99;

        } else if (level.equals("4") || level.equalsIgnoreCase("особый")) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите высоту поля (количество рядов):");
            rows = scanner.nextInt();
            System.out.println("Введите ширину поля (количество столбцов):");
            cols = scanner.nextInt();
            System.out.println("ведите количество мин:");
            mines = scanner.nextInt();
        }
    }

    public static void gameField() {
        gameField = new int[rows][cols];
        setMines();
        setHints();
    }

    public static void setMines() {
        for (int mine = 0; mine < mines; mine++) {
            getRandomFieldAndSetMine(gameField);
        }
    }

    public static void getRandomFieldAndSetMine(int[][] gameField) {
        int randomRowIndex = ThreadLocalRandom.current().nextInt(gameField.length);
        int randomColIndex = ThreadLocalRandom.current().nextInt(gameField[0].length);
        if (gameField[randomRowIndex][randomColIndex] == -1) {
            getRandomFieldAndSetMine(gameField);
        }
        gameField[randomRowIndex][randomColIndex] = -1;

    }

    public static void showGameField() {
        for (int b = 0; b < gameField.length; b++) {
            for (int z = 0; z < gameField[b].length; z++) {

                System.out.printf("%4d", gameField[b][z]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void setHints() {
        int count;
        for (int b = 0; b < gameField.length; b++) {
            for (int z = 0; z < gameField[b].length; z++) {
                if (gameField[b][z] != -1) {
                    count = 0;
                    for (int bn = b - 1; bn <= b + 1; bn++) {
                        for (int zn = z - 1; zn <= z + 1; zn++) {
                            if (bn < 0 || bn > gameField.length - 1 || zn < 0 || zn > gameField[bn].length - 1) {
                                continue;
                            } else if (gameField[bn][zn] == -1) {
                                count += 1;
                            }
                        }
                    }

                } else {
                    continue;
                }

                gameField[b][z] = count;
            }
        }
    }



    public static void gameFieldForPlayer() {
        gameFieldForPlayer = new String[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                gameFieldForPlayer[i][j] = "*";
                System.out.print(" " + gameFieldForPlayer[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void playingGame() {
        System.out.println("Начинаем прогулки по минному полю");
        stepTillWin = rows * cols - mines;
        Scanner scanner = new Scanner(System.in);
        while (stepTillWin > 0) {
            System.out.println("Выбираем куда шагнём");
            System.out.println("Введите номер ряда");
            row = scanner.nextInt() - 1;
            System.out.println("Введите номер столбца");
            col = scanner.nextInt() - 1;
            if (gameField[row][col] == -1) {
                System.out.println("BOOM!!!!");
                System.out.println("Вы ошиблись... А сапер ошибается только раз...");
                showGameField();
                break;
            } else {
                System.out.println("Вы стали на шаг ближе к победе!");
                gameFieldForPlayer[row][col] = String.valueOf(gameField[row][col]);
                showGameFieldForPlayer();
            }
            stepTillWin--;
        }
        if (stepTillWin == 0) {
            System.out.println();
            System.out.println("УРА!!! ВЫ ПОБЕДИЛИ!!!");
        }
        scanner.nextLine();
        System.out.println();
        System.out.println("Хотите начать новую игру (1 - да / 2 - нет?)");
        String checkNewGame = scanner.nextLine();
        if (checkNewGame.equals("2") || checkNewGame.equalsIgnoreCase("нет")) {
            return;
        } else {
            game();
        }
    }

    public static void showGameFieldForPlayer() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(" " + gameFieldForPlayer[i][j] + " ");
            }
            System.out.println();
        }
    }
}
