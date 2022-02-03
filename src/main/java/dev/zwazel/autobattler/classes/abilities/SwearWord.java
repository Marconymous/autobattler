package dev.zwazel.autobattler.classes.abilities;

import dev.zwazel.autobattler.classes.enums.AbilityOutputType;
import dev.zwazel.autobattler.classes.enums.UsageType;
import dev.zwazel.autobattler.classes.units.Unit;

/**
 * Ability that inflicts an Energy Reduction to the Target.
 *
 * @author Marconymous
 */
public class SwearWord extends Ability {

    /**
     * Default constructor.
     *
     * @param owner the owner of the ability.
     */
    public SwearWord(Unit owner) {
        super("Swear Word", "Reduces energy of the target Unit", owner, UsageType.NOTHING, 0, AbilityOutputType.ENERGY, 35, 2, 1, owner.getSide().getOpposite());
    }

    /**
     * Not used in this ability.
     */
    @Override
    public void doRound() {
    }

    /**
     * Returns a list of strings that describe the ability's use.
     *
     * @return the list of strings.
     */
    @Override
    public String[] getUseMessages() {
        return new String[]{generateUseMassage("swears at $targetName"), generateUseMassage("screams random curse words at $targetName"), generateUseMassage("curses $targetName")};
    }

    /**
     * @return a string array with one string because this ability cannot kill.
     */
    @Override
    public String[] getKillMessages() {
        return new String[]{"SwearWord cannot be used to kill."};
    }

    /**
     * Scales the energy output based on the owners level.
     *
     * @param level      the level of the owner.
     * @param baseDamage the base damage of the ability.
     * @return the scaled damage.
     */
    @Override
    public int scaleOutputAmount(int level, int baseDamage) {
        return baseDamage * (1 + (level / 5));
    }

    /**
     * Checks if the ability can be used on the target.
     *
     * @param target the target of the ability.
     * @return true if the ability can be used.
     */
    @Override
    public boolean canBeUsed(Unit target) {
        return true;
    }

    /**
     * Does logic if the ability is used.
     *
     * @param target the target of the ability.
     * @return if the ability was used.
     */
    @Override
    public boolean actuallyUse(Unit target) {
        if (canBeUsed(target)) {
            this.setCurrentCooldown(this.getCooldown());
            doOutput(target);
            return true;
        }

        return false;
    }


    /**
     * Combines the owners name and a description of the ability.
     *
     * @param afterName the text after the owner's name.
     * @return the combined string.
     */
    private String generateUseMassage(String afterName) {
        return this.getOwner().getName() + " " + afterName;
    }
}
