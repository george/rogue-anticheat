package org.hostile.anticheat.util.entity;

import org.hostile.anticheat.location.Vector;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class TrackedEntity {

    private final Deque<TrackedPosition> trackedPositions = new LinkedList<>();

    public void addPosition(Vector position) {
        trackedPositions.add(new TrackedPosition(position));

        if (trackedPositions.size() > 20) {
            trackedPositions.removeFirst();
        }
    }

    public void handleRelMove(double x, double y, double z) {
        addPosition(trackedPositions.peekLast().getLocation().clone().add(new Vector(x, y, z)));
    }

    public List<TrackedPosition> getPositions(long ping) {
        List<TrackedPosition> positions = new ArrayList<>();

        long time = System.currentTimeMillis();
        long offset = time - ping - 200L;

        trackedPositions.stream()
                .filter(position -> position.getTimestamp() - offset > 0L)
                .forEach(positions::add);

        if (positions.isEmpty()) {
            positions.add(trackedPositions.peekLast());
        }

        return positions;
    }
}
