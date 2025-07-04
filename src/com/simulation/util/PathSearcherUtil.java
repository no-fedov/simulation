package com.simulation.util;

import com.simulation.entity.Entity;
import com.simulation.field.Field;
import com.simulation.field.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PathSearcherUtil {

    private static final int[][] MATRIX_FOR_NEIGHBOURS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    private static final int ROW_INDEX = 0;
    private static final int COLUMN_INDEX = 1;

    private PathSearcherUtil() {
    }

    public static List<Position> searchPath(Position startPosition,
                                            Field field,
                                            Class<? extends Entity> target) {
        Queue<Position> positions = new LinkedList<>(List.of(startPosition));
        Set<Position> checkedPosition = new HashSet<>();
        Map<Position, Position> pathMap = new HashMap<>();
        pathMap.put(startPosition, null);

        while (!positions.isEmpty()) {
            Position positionForCheck = positions.poll();
            if (checkedPosition.contains(positionForCheck)) {
                continue;
            }
            Set<Position> neighbourPositions = neighboursPosition(positionForCheck, field);
            Set<Position> positionsWithEntity = positionsByCondition(neighbourPositions,
                    position -> field.getEntity(position).isPresent());
            for (Position position : positionsWithEntity) {
                Entity entityForCheck = field.getEntity(position).orElseThrow();
                if (entityForCheck.getClass().equals(target)) {
                    pathMap.put(position, positionForCheck);
                    return getPath(pathMap, position);
                }
            }
            Set<Position> positionsWithoutEntity = positionsByCondition(neighbourPositions,
                    position -> field.getEntity(position).isEmpty());
            fillPathMap(pathMap, positionsWithoutEntity, positionForCheck);
            positions.addAll(positionsWithoutEntity);
            checkedPosition.add(positionForCheck);
        }
        return List.of(startPosition);
    }

    private static Set<Position> positionsByCondition(Set<Position> positions,
                                                      Predicate<Position> condition) {
        return positions.stream()
                .filter(condition)
                .collect(Collectors.toSet());
    }

    private static Set<Position> neighboursPosition(Position position, Field field) {
        Set<Position> positions = new HashSet<>();
        for (int[] positionShift : MATRIX_FOR_NEIGHBOURS) {
            Position neighbour = new Position(
                    position.row() + positionShift[ROW_INDEX],
                    position.column() + positionShift[COLUMN_INDEX]
            );
            if (FieldUtil.isValid(neighbour, field)) {
                positions.add(neighbour);
            }
        }
        return positions;
    }

    private static Map<Position, Position> fillPathMap(Map<Position, Position> pathMap,
                                                       Set<Position> childVertex,
                                                       Position parentVertex) {
        for (Position child : childVertex) {
            if (!pathMap.containsKey(child)) {
                pathMap.put(child, parentVertex);
            }
        }
        return pathMap;
    }

    private static List<Position> getPath(Map<Position, Position> pathMap,
                                          Position endPoint) {
        List<Position> path = new ArrayList<>();
        Position previosPosition = endPoint;
        while (previosPosition != null) {
            path.add(previosPosition);
            previosPosition = pathMap.get(previosPosition);
        }
        Collections.reverse(path);
        return path;
    }
}
