package com.nickdnepr.strategy.models;

import java.util.LinkedHashMap;

public class Unit {

    //global info
    private long id;
    private boolean isTransport;
    //defence and health
    private double health;
    private double maxHealthPercent;
    private double defence;
    private double explosiveDefence;
    private double armor;
    private double armorQuality;
    //attack
    private LinkedHashMap<Long, Weapon> weapons;
    private double experience;
    //action
    private double actionPoints;
    //transport characteristics
    private double fuel;
    private double maxFuel;

    public Unit(long id, boolean isTransport, double health, double maxHealthPercent, double defence, double explosiveDefence, double armor, double armorQuality, LinkedHashMap<Long, Weapon> weapons, double experience, double actionPoints, double fuel, double maxFuel) {
        this.id = id;
        this.isTransport = isTransport;
        this.health = health;
        this.maxHealthPercent = maxHealthPercent;
        this.defence = defence;
        this.explosiveDefence = explosiveDefence;
        this.armor = armor;
        this.armorQuality = armorQuality;
        this.weapons = weapons;
        this.experience = experience;
        this.actionPoints = actionPoints;
        this.fuel = fuel;
        this.maxFuel = maxFuel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isTransport() {
        return isTransport;
    }

    public void setTransport(boolean transport) {
        isTransport = transport;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getMaxHealthPercent() {
        return maxHealthPercent;
    }

    public void setMaxHealthPercent(double maxHealthPercent) {
        this.maxHealthPercent = maxHealthPercent;
    }

    public double getDefence() {
        return defence;
    }

    public void setDefence(double defence) {
        this.defence = defence;
    }

    public double getExplosiveDefence() {
        return explosiveDefence;
    }

    public void setExplosiveDefence(double explosiveDefence) {
        this.explosiveDefence = explosiveDefence;
    }

    public double getArmor() {
        return armor;
    }

    public void setArmor(double armor) {
        this.armor = armor;
    }

    public double getArmorQuality() {
        return armorQuality;
    }

    public void setArmorQuality(double armorQuality) {
        this.armorQuality = armorQuality;
    }

    public LinkedHashMap<Long, Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(LinkedHashMap<Long, Weapon> weapons) {
        this.weapons = weapons;
    }

    public double getExperience() {
        return experience;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }

    public double getActionPoints() {
        return actionPoints;
    }

    public void setActionPoints(double actionPoints) {
        this.actionPoints = actionPoints;
    }

    public double getFuel() {
        return fuel;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

    public double getMaxFuel() {
        return maxFuel;
    }

    public void setMaxFuel(double maxFuel) {
        this.maxFuel = maxFuel;
    }
}
