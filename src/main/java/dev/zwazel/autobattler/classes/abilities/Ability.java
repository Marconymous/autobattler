package dev.zwazel.autobattler.classes.abilities;

import dev.zwazel.autobattler.classes.RoundAffected;
import dev.zwazel.autobattler.classes.Utils.Vector;
import dev.zwazel.autobattler.classes.enums.AbilityOutputType;
import dev.zwazel.autobattler.classes.enums.AbilityType;
import dev.zwazel.autobattler.classes.enums.UsageType;
import dev.zwazel.autobattler.classes.units.Unit;

public abstract class Ability extends RoundAffected {
    private final UsageType costType;
    private final AbilityOutputType outputType;
    private final int outPutAmount;
    private final int cooldown;
    private final Unit owner;
    private int currentCooldown = 0;
    private AbilityType type;
    private int usageCostAmount;
    private String title;
    private String description;
    private int range;

    public Ability(String title, String description, Unit owner, UsageType costType, int usageCost, AbilityOutputType outputType, int outPutAmount, int cooldown, int range) {
        this.costType = costType;
        this.cooldown = cooldown;
        this.outputType = outputType;
        this.usageCostAmount = usageCost;
        this.range = range;
        this.outPutAmount = outPutAmount;
        this.owner = owner;
        this.title = title;
        this.description = description;
    }

    public abstract boolean canBeUsed(Unit target);

    public abstract boolean use(Unit target);

    public void doOutput(Unit target) {
        switch (this.getOutputType()) {
            case DAMAGE -> {
                target.takeDamage(this.outPutAmount);
            }
            case HEAL -> {

            }
            case STAMINA -> {

            }
        }
    }

    // TODO: 07.12.2021
    public boolean isInRange(Vector target) {
        Double range = this.getOwner().getGridPosition().getDistanceFrom(target);
        return range <= this.range;
    }

    public boolean isInRange(Unit target) {
        return isInRange(target.getGridPosition());
    }

    public UsageType getCostType() {
        return costType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AbilityType getType() {
        return type;
    }

    public void setType(AbilityType type) {
        this.type = type;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getUsageCostAmount() {
        return usageCostAmount;
    }

    public void setUsageCostAmount(int usageCostAmount) {
        this.usageCostAmount = usageCostAmount;
    }

    public AbilityOutputType getOutputType() {
        return outputType;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getCurrentCooldown() {
        return currentCooldown;
    }

    public void setCurrentCooldown(int currentCooldown) {
        this.currentCooldown = currentCooldown;
    }

    public int getOutPutAmount() {
        return outPutAmount;
    }

    public Unit getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return "Ability{" +
                "costType=" + costType +
                ", outputType=" + outputType +
                ", outPutAmount=" + outPutAmount +
                ", cooldown=" + cooldown +
                ", owner=" + owner +
                ", currentCooldown=" + currentCooldown +
                ", type=" + type +
                ", usageCostAmount=" + usageCostAmount +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", range=" + range +
                '}';
    }
}
