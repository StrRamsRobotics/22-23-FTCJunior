package com.javredstone.pathfind;

public class Constants {
    public static final boolean DEBUG = false;
    public static final int SUBDIVISIONS = 1;
    public static final int TIMES_CONES = 1;
    public static final int[][] FIELD = {
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
    };

    public static final double[][] LEFT_BLUE = {
            // Vision paths
            { 0, 4, 0, 2 },
            { 0, 4, 2, 2 },
            { 0, 4, 4, 2 },
            // Cones
            { 2, 0, 0, 4 },
            { 0, 4, 2, 4 }
    };
    public static final double[][] RIGHT_BLUE = {
            // Vision paths
            { 10, 4, 6, 2 },
            { 10, 4, 8, 2 },
            { 10, 4, 10, 2 },
            // Cones
            { 8, 0, 10, 4 },
            { 10, 4, 8, 4 }
    };
    public static final double[][] LEFT_RED = {
            // Vision paths
            { 10, 6, 10, 8 },
            { 10, 6, 8, 8 },
            { 10, 6, 6, 8 },
            // Cones
            { 8, 10, 10, 6 },
            { 10, 6, 8, 6 }
    };
    public static final double[][] RIGHT_RED = {
            // Vision paths
            { 0, 6, 4, 8 },
            { 0, 6, 2, 8 },
            { 0, 6, 0, 8 },
            // Cones
            { 2, 10, 0, 6 },
            { 0, 6, 2, 6 }
    };
    public static int[][] SUBDIVIDED_FIELD = null;
    public static int[][] SUBDIVIDED_MODE = null;
}
