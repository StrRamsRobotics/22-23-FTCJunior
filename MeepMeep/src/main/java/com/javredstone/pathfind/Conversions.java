package com.javredstone.pathfind;

public class Conversions {
    public static double getAngle(int x1, int x2, int y1, int y2) {
        return Math.atan2(x2 - x1, y2 - y1);
    }
    public static double getRealPos(int a) {
        return (a - 5 * Constants.SUBDIVISIONS) * 12 / Constants.SUBDIVISIONS;
    }
    public static void subdivide(double[][] mode) {
        int[][] subdividedField = new int[Constants.FIELD.length * Constants.SUBDIVISIONS][Constants.FIELD[0].length * Constants.SUBDIVISIONS];
        for (int i = 0; i < Constants.FIELD.length; i++) {
            for (int j = 0; j < Constants.FIELD[0].length; j++) {
                for (int k = 0; k < Constants.SUBDIVISIONS; k++) {
//                    System.out.println(subdividedField.length + " " + subdividedField[0].length);
                    subdividedField[i][k] = Constants.FIELD[i][j];
                    subdividedField[i + k][k] = Constants.FIELD[i][j];
                }
            }
        }
        Constants.SUBDIVIDED_FIELD = subdividedField;

        int[][] subdividedMode = new int[mode.length][mode[0].length];
        for (int i = 0; i < subdividedMode.length; i++) {
            for (int j = 0; j < subdividedMode[0].length; j++) {
                subdividedMode[i][j] = (int)(mode[i][j] * Constants.SUBDIVISIONS);
            }
        }
        Constants.SUBDIVIDED_MODE = subdividedMode;
    }
}
