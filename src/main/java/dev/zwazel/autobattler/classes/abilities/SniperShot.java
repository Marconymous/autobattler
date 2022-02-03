package dev.zwazel.autobattler.classes.abilities;

import dev.zwazel.autobattler.classes.enums.AbilityOutputType;
import dev.zwazel.autobattler.classes.enums.UsageType;
import dev.zwazel.autobattler.classes.units.Unit;

public class SniperShot extends Ability {

    public SniperShot(Unit owner) {
        super("Sniper Shot", "Sniper Shot which deals a lot of damage!", owner, UsageType.NOTHING, 0, AbilityOutputType.DAMAGE, 20, 4, 0xffffffff, owner.getSide().getOpposite());
    }

    @Override
    public void doRound() {
        if (this.getCurrentCooldown() > 0) {
            this.setCurrentCooldown(this.getCurrentCooldown() - 1);
        }
    }

    @Override
    public String[] getUseMessages() {
        return new String[]{this.getOwner().getName() + " used Sniper Shot on $targetName!"};
    }

    @Override
    public String[] getKillMessages() {
        return new String[]{this.getOwner().getName() + " styled on $targetName with Sniper Shot!", this.getOwner().getName() + " 360 No-scoped $targetName with Sniper Shot!"};
    }

    @Override
    public int scaleOutputAmount(int level, int baseDamage) {
        return (int) (baseDamage + (baseDamage * (level * 0.5)));
    }

    @Override
    public boolean canBeUsed(Unit target) {
        var onCooldown = this.getCurrentCooldown() > 0;

        return !onCooldown;
    }

    @Override
    public boolean actuallyUse(Unit target) {
        if (!this.canBeUsed(target)) {
            return false;
        }

        doOutput(target);
        return true;
    }
}
