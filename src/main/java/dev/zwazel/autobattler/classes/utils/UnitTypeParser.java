package dev.zwazel.autobattler.classes.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.zwazel.autobattler.BattlerGen2;
import dev.zwazel.autobattler.classes.enums.Side;
import dev.zwazel.autobattler.classes.enums.UnitTypes;
import dev.zwazel.autobattler.classes.exceptions.UnknownUnitType;
import dev.zwazel.autobattler.classes.units.MyFirstUnit;
import dev.zwazel.autobattler.classes.units.Sniper;
import dev.zwazel.autobattler.classes.units.Unit;

public class UnitTypeParser {
    public static Unit getUnit(JsonObject unitJson, BattlerGen2 battler, Side side) throws UnknownUnitType {
        JsonElement unitType = unitJson.get("type");
        UnitTypes type = UnitTypes.findUnitType(unitType.getAsString());
        if (type != null) {
            switch (type) {
                case MY_FIRST_UNIT -> {
                    return new MyFirstUnit(unitJson.get("id").getAsLong(), unitJson.get("priority").getAsInt(),
                            unitJson.get("level").getAsInt(), unitJson.get("name").getAsString(),
                            new Vector(unitJson.get("position").getAsJsonObject()), battler, side);
                }
                case SNIPER -> {
                    return new Sniper(unitJson.get("id").getAsLong(), unitJson.get("priority").getAsInt(),
                            unitJson.get("level").getAsInt(), unitJson.get("name").getAsString(),
                            new Vector(unitJson.get("position").getAsJsonObject()), battler, side);
                }
                default -> {
                    throw new UnknownUnitType();
                }
            }
        } else {
            throw new UnknownUnitType();
        }
    }
}
