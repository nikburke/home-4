import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final int SIZE_X = 5;
    private static final int SIZE_Y = 5;
    private static final char KRESTIK = 'X';
    private static final char NOLIK = 'O';
    private static final char PUSTO = '-';
    private static final int TOWIN = 5;

    private static char[][] field = new char[SIZE_Y][SIZE_X];

    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void initFields() {
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                field[i][j] = PUSTO;
            }
        }
    }

    public static void printField() {
        System.out.print("   ");
        for (int i = 0; i < SIZE_Y; i++) {
            System.out.print((i+1)+" ");
        }
        System.out.println("   ");
        for (int i = 0; i < SIZE_Y; i++) {
            System.out.print((i+1)+ " |");
            for (int j = 0; j < SIZE_X; j++) {
                System.out.print(field[i][j] + "|");
            }
            System.out.println();
        }
    }

    public static void setSym(int y, int x, char sym) {
        field[y][x] = sym;
    }

    public static void playerStep() {
        int x;
        int y;
        do {
            System.out.println("Введите координаты: X (от 1 до " + SIZE_X + ")");
            x = scanner.nextInt() - 1;
            System.out.println("Введите координаты: Y  (от 1 до " + SIZE_Y + ")");
            y = scanner.nextInt() - 1;
        } while (!isCellValid(y, x));
        setSym(y, x, KRESTIK);
    }


    static void aiStep() {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(field.length);
            y = random.nextInt(field.length);
        } while (!isCellValid(x, y));
        field[x][y] = 'O';
    }



    public static boolean isCellValid(int y, int x) {
        if (x < 0 || y < 0 || x > SIZE_X - 1 || y > SIZE_Y - 1) {
            return false;
        }
        return field[y][x] == PUSTO;
    }

    public static boolean isFuelFull() {
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                if (field[i][j] == PUSTO) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkWin(char sym) {
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                if (checkLine(i, j, 0, 1,  sym)) return true;
                if (checkLine(i, j, 1, 1,  sym)) return true;
                if (checkLine(i, j, 1, 0,  sym)) return true;
                if (checkLine(i, j, -1, 1, sym)) return true;
            }
        }
        return false;
    }
    private static boolean checkLine(int y, int x, int vy, int vx, char sym) {
        int wayX = x + (TOWIN - 1) * vx;
        int wayY = y + (TOWIN - 1) * vy;
        if (wayX < 0 || wayY < 0 || wayX > SIZE_X - 1 || wayY > SIZE_Y - 1) return false;
        for (int i = 0; i < TOWIN; i++) {
            int itemY = y + i * vy;
            int itemX = x + i * vx;
            if (field[itemY][itemX] != sym) return false;
        }
        return true;
    }


    public static void main(String[] args) {
        initFields();
        printField();
        while (true) {
            playerStep();
            if (checkWin(KRESTIK)) {
                printField();
                System.out.println("ВЫ выйграли!");
                break;
            }
            if (isFuelFull()) {
                printField();
                System.out.println("Ничья!");
                break;
            }
            aiStep();
            printField();
            if (checkWin(NOLIK)) {
                printField();
                System.out.println("Вы проиграли!");
                break;
            }
            if (isFuelFull()) {
                 printField();
                System.out.println("Ничья!");
                break;
            }
        }

    }

}