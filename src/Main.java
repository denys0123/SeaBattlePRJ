import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static class SeaBattle {

        public static class Ships {

            public static int numRows = 10;
            public static int numCols = 10;
            public static int playerShips;
            public static int computerShips;
            public static String[][] grid = new String[numRows][numCols];
            public static int[][] missedGuesses = new int[numRows][numCols];

            public static void main(String[] args) {

                String continueGame;

                Scanner sc = new Scanner(System.in);
                System.out.print("\nHi, wanna play Battle Ships? Press (Y or N): ");
                continueGame = sc.nextLine();

                if (continueGame.equals("Y") || continueGame.equals("y")) {

                    System.out.println("\nWelcome to the ships battle game.");
                    System.out.println("Right now, sea is empty.\n");
                    createOceanMap();
                    deployPlayerShips();
                    deployComputerShips();
                    do {
                        Battle();
                    } while (Ships.playerShips != 0 && Ships.computerShips != 0);
                    gameOver();
                } else if (continueGame.equals("N") || continueGame.equals("n")) {
                    System.out.print("\nIt's your choice.");
                    System.out.println();
                    System.exit(0);
                }
            }

            public static void createOceanMap() {
                System.out.print("  ");
                for (int i = 0; i < numCols; i++)
                    System.out.print(i);
                System.out.println();

                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid[i].length; j++) {
                        grid[i][j] = " ";
                        if (j == 0)
                            System.out.print(i + " " + grid[i][j]);
                        else if (j == grid[i].length - 1)
                            System.out.print(grid[i][j] + " " + i);
                        else
                            System.out.print(grid[i][j]);
                    }
                    System.out.println();
                }
                System.out.print("  ");
                for (int i = 0; i < numCols; i++)
                    System.out.print(i);
                System.out.println();
            }

            public static void deployPlayerShips() {

                Scanner input = new Scanner(System.in);

                System.out.println("\nDeploy your ships.");
                Ships.playerShips = 5;
                for (int i = 1; i <= Ships.playerShips; ) {
                    System.out.print("Enter X coordinate for your " + i + " ship: ");
                    int x = input.nextInt();
                    System.out.print("Enter Y coordinate for your " + i + " ship: ");
                    int y = input.nextInt();

                    if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (Objects.equals(grid[x][y], " "))) {
                        grid[x][y] = "•";
                        i++;
                    } else if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && Objects.equals(grid[x][y], "@"))
                        System.out.println("You can not place two or more ships on the same place!");
                    else if ((x < 0 || x >= numRows) || (y < 0 || y >= numCols))
                        System.out.println("You can't place ships outside the " + numRows + " by " + numCols + " grid");
                }
                printOceanMap();
            }

            public static void deployComputerShips() {
                System.out.println("\nThe computer is deploying ships");
                Ships.computerShips = 5;
                for (int i = 1; i <= Ships.computerShips; ) {
                    int x = (int) (Math.random() * 10);
                    int y = (int) (Math.random() * 10);

                    if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (Objects.equals(grid[x][y], " "))) {
                        grid[x][y] = "@";
                        System.out.println("The " + i + "th ship has been deployed.");
                        i++;
                    }
                }
                System.out.println("Computer's ships have been deployed");
                printOceanMap();
            }

            public static void Battle() {
                playerTurn();
                computerTurn();
                printOceanMap();

                System.out.println();
                System.out.println("Your ships: " + Ships.playerShips + " | Computer ships: " + Ships.computerShips);
                System.out.println();
            }

            public static void playerTurn() {
                System.out.println("\nYour turn.");
                int x, y;
                do {
                    Scanner sc = new Scanner(System.in);
                    System.out.print("Enter X coordinate: ");
                    x = sc.nextInt();
                    System.out.print("Enter Y coordinate: ");
                    y = sc.nextInt();

                    if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols)) {
                        if (Objects.equals(grid[x][y], "@")) {
                            System.out.println("You sank the ship!");
                            grid[x][y] = "x";
                            --Ships.computerShips;
                        } else if (Objects.equals(grid[x][y], "•")) {
                            System.out.println("You sunk your own ship.");
                            grid[x][y] = "y";
                            --Ships.playerShips;
                            ++Ships.computerShips;
                        } else if (grid[x][y].equals(" ")) {
                            System.out.println("You missed.");
                            grid[x][y] = "-";
                        }
                    } else
                        System.out.println("You can't place ships outside the " + numRows + " by " + numCols + " grid");
                } while ((x < 0 || x >= numRows) || (y < 0 || y >= numCols));
            }

            public static void computerTurn() {
                System.out.println("\nComputer's turn.");
                int x, y;
                do {
                    x = (int) (Math.random() * 10);
                    y = (int) (Math.random() * 10);

                    if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols)) {
                        if (Objects.equals(grid[x][y], "@")) {
                            System.out.println("The computer sank one of your ships!");
                            grid[x][y] = "X";
                            --Ships.playerShips;
                            ++Ships.computerShips;
                        } else if (Objects.equals(grid[x][y], "x")) {
                            System.out.println("The computer sank one of its own ships.");
                            grid[x][y] = "C";
                        } else if (Objects.equals(grid[x][y], " ")) {
                            System.out.println("Computer missed.");
                            if (missedGuesses[x][y] != 1)
                                missedGuesses[x][y] = 1;
                        }
                    }
                } while ((x < 0 || x >= numRows) || (y < 0 || y >= numCols));
            }

            public static void gameOver() {
                System.out.println("Your ships: " + Ships.playerShips + " | Computer ships: " + Ships.computerShips);
                if (Ships.playerShips > 0 && Ships.computerShips <= 0)
                    System.out.println("You won the game!)");
                else
                    System.out.println("You lost the game.");
                System.out.println();
            }

            public static void printOceanMap() {
                System.out.println();
                System.out.print("  ");
                for (int i = 0; i < numCols; i++)
                    System.out.print(i);
                System.out.println();

                for (int x = 0; x < grid.length; x++) {
                    System.out.print(x + " ");

                    for (int y = 0; y < grid[x].length; y++) {
                        System.out.print(grid[x][y]);
                    }
                    System.out.println(" " + x);
                }
                System.out.print("  ");
                for (int i = 0; i < numCols; i++)
                    System.out.print(i);
                System.out.println();
            }
        }
    }
}
