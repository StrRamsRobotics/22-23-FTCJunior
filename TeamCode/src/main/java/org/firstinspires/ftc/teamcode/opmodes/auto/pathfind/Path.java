package org.firstinspires.ftc.teamcode.opmodes.auto.pathfind;

public class Path {
    public int idx;
    public boolean backwards;
    public boolean cone;
    public Path(int idx, boolean backwards, boolean cone) {
        this.idx = idx;
        this.backwards = backwards;
        this.cone = cone;
    }
}
