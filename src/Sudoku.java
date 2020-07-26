import java.util.concurrent.TimeUnit;

public class Sudoku {

    //sudokutaulukko
    public static int[][] sudoku = {{0, 0, 1, 0, 0, 2, 0, 0, 0},
            {2, 0, 0, 0, 3, 0, 0, 0, 4},
            {0, 0, 0, 5, 0, 0, 0, 6, 0},
            {0, 0, 0, 7, 0, 6, 0, 2, 0},
            {0, 6, 0, 0, 8, 0, 0, 1, 0},
            {0, 8, 0, 1, 0, 9, 0, 0, 0},
            {0, 9, 0, 0, 0, 8, 0, 0, 0},
            {3, 0, 0, 0, 4, 0, 0, 0, 5},
            {0, 0, 0, 6, 0, 0, 7, 0, 0}};


    //Ratkaisija joka kayttaa backtracking algoritmia. Jotta ratkaisijan loop toimii, taytyy ratkaisijan palauttaa totuusarvo.
    //ratkaisijan GUI versio
    public static boolean solverGUI() {

        Cordinate empty = findEmpty(sudoku);
        if (empty == null) {
            return true;
        }

        int row = empty.getY();
        int column = empty.getX();

        for (int i = 1; i <= 9; i++) {

            int paneIndex = row * 9 + column;

            sudoku[row][column] = i;

            if (acceptable(row, column)) {

                wait(50);
                if (solverGUI()) {
                    return true;
                }

                sudoku[row][column] = -1;
                wait(50);
            } else {
                sudoku[row][column] = -1;
            }
        }
        return false;
    }

    // Katsoo onko taulukkoon laitettava arvo hyvaksyttava
    public static boolean acceptable(int r, int c) {

        // varmistaa onko rivilla samaa numeroa
        for (int i = 0; i < 9; i++) {
            if (sudoku[i][c] == sudoku[r][c] && i != r) {
                return false;
            }
        }

        // varmistaa onko sarakkeessa samaa numeroa
        for (int i = 0; i < 9; i++) {
            if (sudoku[r][i] == sudoku[r][c] && i != c) {
                return false;
            }
        }

        // varmistaa onko sudokun 3x3 laatikossa samaa lukua
        for (int i = 0; i < 3; i++) {
            int ybox = (r / 3) * 3 + i;
            for (int a = 0; a < 3; a++) {
                int xbox = (c / 3) * 3 + a;
                if (sudoku[ybox][xbox] == sudoku[r][c] && r != ybox && c != xbox) {
                    return false;
                }
            }
        }
        return true;
    }

    // Etsii tyhjän ruudun (ruudun jossa on 0 || -1)
    public static Cordinate findEmpty(int[][] board) {
        for (int i = 0; i < 9; i++) {

            for (int j = 0; j < 9; j++) {

                if (board[i][j] == 0 || board[i][j] == -1) {
                    return new Cordinate(i, j);

                }
            }
        }
        return null;
    }

    // tulostaa sudokun
    public static void sudokuPrint() {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                System.out.print(sudoku[row][column] + "\t");
            }
            System.out.println();
        }
    }

    public static void wait(int ms) {
        long expectedtime = System.currentTimeMillis() + ms;

        while (System.currentTimeMillis() < expectedtime) {
            //Empty Loop
        }
    }


}