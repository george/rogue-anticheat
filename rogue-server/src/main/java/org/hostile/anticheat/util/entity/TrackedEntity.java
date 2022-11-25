package org.hostile.anticheat.util.entity;

import org.hostile.anticheat.location.Vector;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class TrackedEntity {

    private final Deque<TrackedPosition> trackedPositions = new LinkedList<>();

    public void addPosition(Vector position) {
        this.trackedPositions.add(new TrackedPosition(position));

        if (this.trackedPositions.size() > 20) {
            this.trackedPositions.removeFirst();
        }
    }

    public void handleRelMove(double x, double y, double z) {
        this.addPosition(trackedPositions.peekLast().getLocation().clone().add(new Vector(x, y, z)));
    }

    public List<TrackedPosition> getPositions(long ping) {
        List<TrackedPosition> positions = new ArrayList<>();

        long time = System.currentTimeMillis();
        long offset = time - ping - 200L;

        this.trackedPositions.stream()
                .filter(position -> position.getTimestamp() - offset > 0L)
                .forEach(positions::add);

        if (positions.isEmpty()) {
            positions.add(this.trackedPositions.peekLast());
        }

        return positions;
    }
}
