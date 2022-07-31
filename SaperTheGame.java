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

            System.out.println("�������� ������� ����: ");
            System.out.println("1 - �������");
            System.out.println("2 - ��������");
            System.out.println("3 - ������������");
            System.out.println("4 - ������");

            Scanner scanner = new Scanner(System.in);
            String level = scanner.nextLine();

            gameLevel(level);
            gameField();
            gameFieldForPlayer();
            playingGame();
    }
    public static void gameLevel(String level) {

        if (level.equals("1") || level.equalsIgnoreCase("�������")) {
            rows = 9;
            cols = 9;
            mines = 10;

        } else if (level.equals("2") || level.equalsIgnoreCase("��������")) {
            rows = 16;
            cols = 16;
            mines = 40;

        } else if (level.equals("3") || level.equalsIgnoreCase("������������")) {
            rows = 16;
            cols = 30;
            mines = 99;

        } else if (level.equals("4") || level.equalsIgnoreCase("������")) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("������� ������ ���� (���������� �����):");
            rows = scanner.nextInt();
            System.out.println("������� ������ ���� (���������� ��������):");
            cols = scanner.nextInt();
            System.out.println("������ ���������� ���:");
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
        System.out.println("�������� �������� �� ������� ����");
        stepTillWin = rows * cols - mines;
        Scanner scanner = new Scanner(System.in);
        while (stepTillWin > 0) {
            System.out.println("�������� ���� �����");
            System.out.println("������� ����� ����");
            row = scanner.nextInt() - 1;
            System.out.println("������� ����� �������");
            col = scanner.nextInt() - 1;
            if (gameField[row][col] == -1) {
                System.out.println("BOOM!!!!");
                System.out.println("�� ��������... � ����� ��������� ������ ���...");
                showGameField();
                break;
            } else {
                System.out.println("�� ����� �� ��� ����� � ������!");
                gameFieldForPlayer[row][col] = String.valueOf(gameField[row][col]);
                showGameFieldForPlayer();
            }
            stepTillWin--;
        }
        if (stepTillWin == 0) {
            System.out.println();
            System.out.println("���!!! �� ��������!!!");
        }
        scanner.nextLine();
        System.out.println();
        System.out.println("������ ������ ����� ���� (1 - �� / 2 - ���?)");
        String checkNewGame = scanner.nextLine();
        if (checkNewGame.equals("2") || checkNewGame.equalsIgnoreCase("���")) {
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
