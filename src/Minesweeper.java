import java.util.*;
import java.lang.Math;

/**
* Minesweeper
* Original Date: 01/29/19
* Modified Date: 12/23/22
* A personalized/modified game of the classic Minesweeper
* @author Johnson Le
*/

class Minesweeper {
        public static void main(String[] args) {
                Scanner in = new Scanner(System.in);
                instructions();
                int grid[][] = { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
                                { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
                                { 0, 0, 0, 0, 0, 0, 0, 0 },
                                { 0, 0, 0, 0, 0, 0, 0, 0 } }; // Here the two arrays are declared
                String gridDisplay[][] = { { "?", "?", "?", "?", "?", "?", "?", "?" },
                                { "?", "?", "?", "?", "?", "?", "?", "?" },
                                { "?", "?", "?", "?", "?", "?", "?", "?" }, { "?", "?", "?", "?", "?", "?", "?", "?" },
                                { "?", "?", "?", "?", "?", "?", "?", "?" },
                                { "?", "?", "?", "?", "?", "?", "?", "?" }, { "?", "?", "?", "?", "?", "?", "?", "?" },
                                { "?", "?", "?", "?", "?", "?", "?", "?" } };
                grid = placeMines(grid); // Method places mines in the array
                grid = setNumbers(grid); // Method determines the number of mines near each cell
                int rowUser, columnUser; // Variables that stores rows and columns selected by user
                boolean endGame = false; // Variable determines whether user's game ended
                boolean winORnot = false; // Variable determines whether user wins or not
                while (endGame == false && winORnot == false) // Loop runs until the user wins or loses
                {
                        PrintDisplay(gridDisplay);
                        System.out.println("Insert the row; ENTER 10 TO EXIT"); // Takes the row input from user
                        rowUser = in.nextInt();
                        rowUser--;
                        System.out.println("Insert the column; ENTER 10 TO EXIT"); // Takes the column input from user
                        columnUser = in.nextInt();
                        columnUser--;
                        if (columnUser <= 7 && columnUser >= 0 && rowUser <= 7 && rowUser >= 0) {
                                if (grid[rowUser][columnUser] != 9) {
                                        gridDisplay[rowUser][columnUser] = String.valueOf(grid[rowUser][columnUser]);
                                        winORnot = WinChecker(gridDisplay, grid); // Checks whether user won or not
                                        if (winORnot == true) {
                                                System.out.println("You've won!"); // User won the game
                                        }
                                } else {
                                        System.out.println("You hit a mine");
                                        System.out.println("You've lost");
                                        System.out.println("These are where all the mines were:"); // Lists all the mines

                                        gridDisplay = finalGrid(grid);
                                        PrintDisplay(gridDisplay); // Solution is determined and outputed for the user
                                        endGame = true;
                                }
                        } else {
                                System.out.println("INCORRECT RESPONSE; TRY AGAIN");
                        }
                }
        }

        /**
         * Solution is shown to the user after the game is won or lost
         * Pre: Array passed must be of 8 by 8
         * Post: Array returned shows mines as asterisks
         */
        public static String[][] finalGrid(int f[][]) {
                String fg[][] = { { "", "", "", "", "", "", "", "" }, { "", "", "", "", "", "", "", "" },
                                { "", "", "", "", "", "", "", "" }, { "", "", "", "", "", "", "", "" },
                                { "", "", "", "", "", "", "", "" },
                                { "", "", "", "", "", "", "", "" }, { "", "", "", "", "", "", "", "" },
                                { "", "", "", "", "", "", "", "" } };
                for (int a = 0; a <= 7; a++) {
                        for (int b = 0; b <= 7; b++) // Final solution is determined
                        {
                                if (f[a][b] != 9) {
                                        fg[a][b] = String.valueOf(f[a][b]);
                                }
                                if (f[a][b] == 9) {
                                        fg[a][b] = "*"; // Asterisk is placed where mines are
                                }
                        }
                }
                return (fg);
        }

        /**
         * Randomly puts mines into the grid
         * Pre: Array passed must be a 8 by 8
         * Post: There will be 20 mines out of the 64 element grid
         */
        public static int[][] placeMines(int pM[][]) {
                for (int minesN = 0; minesN <= 19; minesN++) {
                        int rand1 = (int) (8 * Math.random()); // Random position is chosen and thus mine is placed
                        int rand2 = (int) (8 * Math.random());
                        if (pM[rand1][rand2] != 9) {
                                pM[rand1][rand2] = 9;
                        } else {
                                minesN--; // if position is the same then it will try again
                        }
                }
                return (pM);
        }

        /**
         * Determine numbers that go beside mines in the array
         * Pre: Array passed must be a 8 by 8 array
         * Post: All cells will occupy a number from 0 to 9
         */
        public static int[][] setNumbers(int sN[][]) {
                for (int i = 0; i <= 7; i++) {
                        for (int j = 0; j <= 7; j++) // All cells are assigned numbers
                        {
                                if (sN[i][j] != 9) {
                                        if (i != 7 && sN[i + 1][j] == 9) // For the corners we need to avoid the arrays
                                                                         // Out of bound exception
                                        {
                                                sN[i][j]++;
                                        }
                                        if (j != 7 && sN[i][j + 1] == 9) {
                                                sN[i][j]++;
                                        }
                                        if (i != 0 && sN[i - 1][j] == 9) {
                                                sN[i][j]++;
                                        }
                                        if (j != 0 && sN[i][j - 1] == 9) {
                                                sN[i][j]++;
                                        }
                                        if (j != 7 && i != 7 && sN[i + 1][j + 1] == 9) {
                                                sN[i][j]++;
                                        }
                                        if (i != 0 && j != 0 && sN[i - 1][j - 1] == 9) {
                                                sN[i][j]++;
                                        }
                                        if (i != 7 && j != 0 && sN[i + 1][j - 1] == 9) {
                                                sN[i][j]++;
                                        }
                                        if (j != 7 && i != 0 && sN[i - 1][j + 1] == 9) {
                                                sN[i][j]++;
                                        }
                                }
                        }
                }
                return (sN);
        }

        /**
         * Print array, but not required in the program
         * Pre: Array passed should be a 8 by 8 matrix
         * Post: Array is printed
         */
        public static void PrintDisplay(int pd[][]) // pd stands for print display
        {
                System.out.println(pd[0][0] + " " + pd[0][1] + " " + pd[0][2] + " " + pd[0][3] + " " + pd[0][4] + " "
                                + pd[0][5] + " " + pd[0][6] + " " + pd[0][7]);
                System.out.println(pd[1][0] + " " + pd[1][1] + " " + pd[1][2] + " " + pd[1][3] + " " + pd[1][4] + " "
                                + pd[1][5] + " " + pd[1][6] + " " + pd[1][7]); // This is only when test if the program
                                                                               // is working
                System.out.println(pd[2][0] + " " + pd[2][1] + " " + pd[2][2] + " " + pd[2][3] + " " + pd[2][4] + " "
                                + pd[2][5] + " " + pd[2][6] + " " + pd[2][7]);
                System.out.println(pd[3][0] + " " + pd[3][1] + " " + pd[3][2] + " " + pd[3][3] + " " + pd[3][4] + " "
                                + pd[3][5] + " " + pd[3][6] + " " + pd[3][7]);
                System.out.println(pd[4][0] + " " + pd[4][1] + " " + pd[4][2] + " " + pd[4][3] + " " + pd[4][4] + " "
                                + pd[4][5] + " " + pd[4][6] + " " + pd[4][7]);
                System.out.println(pd[5][0] + " " + pd[5][1] + " " + pd[5][2] + " " + pd[5][3] + " " + pd[5][4] + " "
                                + pd[5][5] + " " + pd[5][6] + " " + pd[5][7]);
                System.out.println(pd[6][0] + " " + pd[6][1] + " " + pd[6][2] + " " + pd[6][3] + " " + pd[6][4] + " "
                                + pd[6][5] + " " + pd[6][6] + " " + pd[6][7]);
                System.out.println(pd[7][0] + " " + pd[7][1] + " " + pd[7][2] + " " + pd[7][3] + " " + pd[7][4] + " "
                                + pd[7][5] + " " + pd[7][6] + " " + pd[7][7]);
                System.out.println(""); // Printed to simulate a matrix
        }

        /**
         * Prints the mine sweeping area
         * Pre: Array must be a 2D array: 8 by 8
         * Post: Array is outputed
         */
        public static void PrintDisplay(String pd[][])
        {
                System.out.println(pd[0][0] + " " + pd[0][1] + " " + pd[0][2] + " " + pd[0][3] + " " + pd[0][4] + " "
                                + pd[0][5] + " " + pd[0][6] + " " + pd[0][7]);
                System.out.println(pd[1][0] + " " + pd[1][1] + " " + pd[1][2] + " " + pd[1][3] + " " + pd[1][4] + " "
                                + pd[1][5] + " " + pd[1][6] + " " + pd[1][7]); // This is the array the user sees
                System.out.println(pd[2][0] + " " + pd[2][1] + " " + pd[2][2] + " " + pd[2][3] + " " + pd[2][4] + " "
                                + pd[2][5] + " " + pd[2][6] + " " + pd[2][7]);
                System.out.println(pd[3][0] + " " + pd[3][1] + " " + pd[3][2] + " " + pd[3][3] + " " + pd[3][4] + " "
                                + pd[3][5] + " " + pd[3][6] + " " + pd[3][7]);
                System.out.println(pd[4][0] + " " + pd[4][1] + " " + pd[4][2] + " " + pd[4][3] + " " + pd[4][4] + " "
                                + pd[4][5] + " " + pd[4][6] + " " + pd[4][7]);
                System.out.println(pd[5][0] + " " + pd[5][1] + " " + pd[5][2] + " " + pd[5][3] + " " + pd[5][4] + " "
                                + pd[5][5] + " " + pd[5][6] + " " + pd[5][7]);
                System.out.println(pd[6][0] + " " + pd[6][1] + " " + pd[6][2] + " " + pd[6][3] + " " + pd[6][4] + " "
                                + pd[6][5] + " " + pd[6][6] + " " + pd[6][7]);
                System.out.println(pd[7][0] + " " + pd[7][1] + " " + pd[7][2] + " " + pd[7][3] + " " + pd[7][4] + " "
                                + pd[7][5] + " " + pd[7][6] + " " + pd[7][7]);
                System.out.println("");
        }

        /**
         * Give instructions of the game to user
         * Pre: Nothing required
         * Post: Cursor is on the next line
         */
        public static void instructions() {
                System.out.println("Welcome to Minesweeper!");   
                System.out.println("In this game, there is a 8x8 grid, which contains hidden EXPLOSIVE MINES!");
                System.out.println("You are a member of a bomb squad (*) and you are tasked with clearling the field of mines");
                System.out.println("You must select one of the cells by selecting the number row and column you want to inspect");
                System.out.println("Select a cell by entering a ROW and Column number of the cell. For the selected cell, you will see a numeric number pop-up, representing a MINE");
                System.out.println("The pop-up number represents the number of bombs adjacent to your cell - this includes cells diagonally adjacent to it");
                                                                                                                                                           
                System.out.println(""); // Instructions for the game
                System.out.println("To win click on all cells except for the mines, untill there are no more cells to open");
                System.out.println("If your cell pops-up a mine (*), you lose the game");
                System.out.println("");
                System.out.println("This game was originally made by Microsoft");
                System.out.println("Enjoy!");
                System.out.println("\n");
                System.out.println("Start");
        }

        /**
         * Checks whether user has won
         * Pre: Array must be inputed
         * Post: We know whether user has won or not
         */
        public static boolean WinChecker(String gd[][], int g[][]) {
                for (int c = 0; c <= 7; c++) {
                        for (int d = 0; d <= 7; d++) // If all cells are selected that are not mines then the user wins
                        {
                                if (gd[c][d] == "?" && g[c][d] != 9) {
                                        return (false);
                                }
                        }
                }
                return (true);
        }
}
