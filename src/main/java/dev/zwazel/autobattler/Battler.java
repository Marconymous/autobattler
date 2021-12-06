package dev.zwazel.autobattler;

import dev.zwazel.autobattler.classes.Utils.Vector;
import dev.zwazel.autobattler.classes.units.MyFirstUnit;
import dev.zwazel.autobattler.classes.units.Unit;

import java.util.ArrayList;

public class Battler {
    private final Vector gridSize = new Vector(9, 4);
    private ArrayList<Unit> units;

    public Battler() {
        int unitCounter = 0;
        units = new ArrayList<>();
        units.add(new MyFirstUnit(unitCounter++, 1, "unit1", new Vector(1, 1), gridSize));
        units.add(new MyFirstUnit(unitCounter++, 1, "unit2", new Vector(2, 2), gridSize));
        units.add(new MyFirstUnit(unitCounter++, 1, "unit3", new Vector(5, 2), gridSize));
        units.add(new MyFirstUnit(unitCounter++, 1, "unit4", new Vector(9, 1), gridSize));

        boolean fightFinished = false;
        while (!fightFinished) {
            fightFinished = true;
            drawBoard();
//            for (Unit unit : units) {
//                unit.moveRandom();
//            }
//            drawBoard();
        }
        System.out.println(units.get(3).getGridPosition());
        units.get(3).move(Vector.DIRECTION.RIGHT.getDirection());
        System.out.println(units.get(3).getGridPosition());

        System.out.println("komplett nach links...");

        units.get(3).setBaseSpeed(10);
        units.get(3).move(Vector.DIRECTION.LEFT.getDirection());
        System.out.println(units.get(3).getGridPosition());

        drawBoard();
    }

    public static void main(String[] args) {
        new Battler();
    }

    private void drawBoard() {
        ArrayList<Unit> placedUnits = new ArrayList<>();
        StringBuilder vertical = new StringBuilder();
        vertical.append("-".repeat((gridSize.getX() + 1) * 4 + 1));

        int counter = 0;
        Vector gridPositionNow = new Vector(0, 0);

        for (int row = 0; row <= gridSize.getY(); row++) {
            System.out.println();
            System.out.println(vertical);

            for (int column = 0; column <= gridSize.getX(); column++) {
                String character = " ";
                gridPositionNow.setX(column);
                gridPositionNow.setY(row);
                for (Unit unit : units) {
                    if (!placedUnits.contains(unit) && unit.getGridPosition().equals(gridPositionNow)) {
                        placedUnits.add(unit);
                        character = "" + unit.getID();
                    }
                }

                System.out.print("|" + " " + character + " ");
            }
            System.out.print("|");
        }
        System.out.println();
        System.out.println(vertical);
    }
}