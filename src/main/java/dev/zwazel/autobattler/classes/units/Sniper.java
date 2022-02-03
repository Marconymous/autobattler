package dev.zwazel.autobattler.classes.units;

import dev.zwazel.autobattler.BattlerGen2;
import dev.zwazel.autobattler.classes.abilities.Ability;
import dev.zwazel.autobattler.classes.abilities.SniperShot;
import dev.zwazel.autobattler.classes.enums.Action;
import dev.zwazel.autobattler.classes.enums.Side;
import dev.zwazel.autobattler.classes.enums.UnitTypes;
import dev.zwazel.autobattler.classes.utils.Vector;
import dev.zwazel.autobattler.classes.utils.json.ActionHistory;

import java.util.Random;

public class Sniper extends Unit {
    public Sniper(long id, int priority, int level, String name, Vector position, BattlerGen2 battler, Side side) {
        super(id, level, name, "First Unit", 10, 100, 'u', position, battler.getGrid().getGridSize(), 1, battler, side, priority, UnitTypes.MY_FIRST_UNIT);
        this.setAbilities(new Ability[]{new SniperShot(this), new SniperShot(this)});
    }


    @Override
    public ActionHistory run() {
        // run doRound on all abilities
        for (Ability ability : getAbilities()) {
            ability.doRound();
        }

        var suitableAbility = findSuitableAbility();

        var action = (suitableAbility == null) ? Action.RETREAT : Action.USE_ABILITY;

        Unit target = null;
        switch (action) {
            case USE_ABILITY -> {
                target = findTargetUnit(getSide().getOpposite());
                suitableAbility.use(target);
            }

            case RETREAT -> moveRandom();

            default -> System.out.println("Sniper.run() - Action not found");
        }

        return new ActionHistory(action, this, new Unit[]{target}, suitableAbility, new Vector[]{this.getGridPosition()});
    }

    @Override
    protected int getLevelHealth(int health, int level) {
        return (int) (health + health * level * 0.1);
    }

    @Override
    protected int getLevelEnergy(int energy, int level) {
        return (int) (energy + energy * level * 0.1);
    }

    @Override
    protected Ability findSuitableAbility() {
        for (Ability ability : getAbilities()) {
            Unit target = findTargetUnit(ability.getTargetSide());
            if (ability.canBeUsed(target))
                return ability;
        }

        return null;
    }

    @Override
    protected Vector[] moveTowards(Unit target) {
        return null;
    }

    @Override
    protected boolean move(Vector direction, boolean checkIfOccupied) {
        if (!checkIfOccupied) {
//            System.out.println(this.getName() + "(" + this.getID() + ")" + " moves from " + this.getGridPosition() + " to " + direction);
            this.setGridPosition(direction);
            return true;
        } else {
            if (!this.getBattler().placeOccupied(direction)) {
                this.setGridPosition(direction);
                return true;
            }
        }
        return false;
    }

    @Override
    protected void moveRandom() {
        Random rand = new Random();
        int n = rand.nextInt(Vector.DIRECTION.values().length);
        Vector direction = Vector.DIRECTION.values()[n].getDirection();
        move(direction, true);
    }

    @Override
    protected Unit findTargetUnit(Side side) {
        return getBattler().findClosestOther(this, side, false, false);
    }

    @Override
    public void takeDamage(Ability ability) {
        this.setHealth((int) (this.getHealth() - ability.getOutPutAmount() * 1.25));
        if (this.getHealth() <= 0) {
            die(ability);
        }
    }

    @Override
    public void energyReduction(Ability ability) {
        this.setEnergy(this.getEnergy() - ability.getOutPutAmount() / 2);
    }
}
