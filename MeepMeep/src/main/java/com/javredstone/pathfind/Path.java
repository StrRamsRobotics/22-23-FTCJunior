package com.javredstone.pathfind;

public class Path {
    public int idx;
    public boolean backwards;
    public Path(int idx, boolean backwards) {
        this.idx = idx;
        this.backwards = backwards;
    }
}
