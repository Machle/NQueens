import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    public static char[][] gen_matrix(int n) {

        char[][] matrix = new char[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = '_';
            }
        }

        for (int i = 0; i < n; i++) {
            int rand = randomWithRange(0, n-1);
            matrix[rand][i] = '*';
        }

        return matrix;

    }

    private static ArrayList<Integer> minConflictRow(int column, char[][] matrix, int n) {
        //TODO

        int min_conflicts = check_row(0, column, n, matrix) + check_diagonal(0, column, n, matrix);
        int temp = 0;
        for (int i = 0; i < n; i++) {
            int conflicts = check_row(i, column, n, matrix) + check_diagonal(i, column, n, matrix);
            if (conflicts <= min_conflicts) {
                min_conflicts = conflicts;
                temp = i;
            }

        }

        ArrayList<Integer> rows = new ArrayList<Integer>();
        rows.add(temp);
        for(int i = 0; i<n; i++){
            int conflicts = check_row(i, column, n, matrix) + check_diagonal(i, column, n, matrix);
            if(conflicts == min_conflicts){
                rows.add(i);

            }

        }

        return rows;
    }

    private static void switch_fields(int min_conflict_row, int j, char[][] column, int n) {

        for (int i = 0; i < n; i++) {
            if (column[i][j] == '*' && min_conflict_row != i) {
                column[min_conflict_row][j] = '*';
                column[i][j] = '_';
            }
        }

    }

    private static boolean check_solution(char[][] matrix, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '*') {
                    int check_conflicts = check_diagonal(i, j, n, matrix) + check_row(i, j, n, matrix);
                    if (check_conflicts != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static void Nqueens(int n, int max_iter) {
        boolean solution = false;
        char[][] final_board = new char[n][n];

        while (solution != true) {

            //We generate NxN matrix with N queens
            char[][] board = gen_matrix(n);
            int i = 0;
            while (i++ < max_iter) {
                //For each column
                for (int j = 0; j < n; j++) {
                    //We find the rows with minimum conflics
                    ArrayList<Integer> min_conflict_row = minConflictRow(j, board, n);
                    //Get random row from the minimum conflicts row
                    int new_random_index= randomWithRange(0,min_conflict_row.size()-1);
                    int new_random_row = min_conflict_row.get(new_random_index);

                    //Move our queen
                    switch_fields(new_random_row, j, board, n);


                }
                //If we have a solution
                //The solution matrix will be printed

                if (check_solution(board, n)) {
                    solution = true;
                    for (int k = 0; k < n; k++) {
                        for (int s = 0; s < n; s++) {
                            System.out.print(board[k][s] + " ");
                        }
                        System.out.println();
                    }
                    break;
                }
            }

        }

    }

    //Calculates row conflicts
    private static int check_row(int row, int column, int n, char[][] matrix) {

        int count = 0;

        int i = column;

        while (i-- > 0) {

            if (matrix[row][i] == '*')
                count++;

        }

        i = column;

        while (i++ < n - 1) {

            if (matrix[row][i] == '*')
                count++;

        }

        return count;
    }

    //Calculates diogonal conflicts
    private static int check_diagonal(int row, int column, int n, char[][] matrix) {

        int count = 0;
        int i = row;
        int j = column;

        while (i-- > 0 && j-- > 0) {
            if (matrix[i][j] == '*')
                count++;

        }

        i = row;
        j = column;

        while (i++ < n - 1 && j++ < n - 1) {
            if (matrix[i][j] == '*')
                count++;

        }

        i = row;
        j = column;

        while (i++ < n - 1 && j-- > 0) {
            if (matrix[i][j] == '*')
                count++;
        }

        i = row;
        j = column;

        while (i-- > 0 && j++ < n - 1) {
            if (matrix[i][j] == '*')
                count++;

        }

        return count;
    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.print("Enter the number of Queens: ");
        int n = in.nextInt();


        Nqueens(n, 1000);


    }
}

