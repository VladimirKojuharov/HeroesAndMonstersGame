import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Weapon extends Item {

    public int itemLVL;
    private String itemModel;
    private String itemSet;
    private String itemName;
    private String wepType;
    private int itemMinDMG;
    private int itemMaxDMG;
    private double itemAttackSpeed;
    private int itemCriticalChance;
    private int itemStunChance;
    private int itemSpellPower;

    private String[] itemSets = {"Death's", "King's", "Rush's", "Great", "Devil's", "Dragon's", "Hell's", "Crusher's", "Bone's", "Ghost"};
    private String[] weaponType = {"Axe", "Hammer", "Sword", "Bow", "Staff", "Crossbow", "Scepter"};

    public Weapon(String club) {
        super();
        this.itemModel = "WEAPON";
        this.itemSet = "Wood";
        wepType = "Club";
        itemName = "Wood Club";
        itemMinDMG = 4;
        itemMaxDMG = 6;
        itemAttackSpeed = 1;
    }

    public Weapon() {
        super();
        this.itemModel = "WEAPON";
        this.setWeaponType();
        this.setWeaponName();
        this.setItemStats();
        this.itemBonusesGenerator();
        itemStatsForPrinting();
    }

    public String getItemName() {
        return (this.itemName);
    }

    public String showItemStats() {
        String heroWeapon = String.format("%n   " + getItemName() + "%n   LVL:   " + getItemLVL() + "%n   DMG: " +
                getItemMinDMG() + "-" + getItemMaxDMG() + "%n   AS:  %.2f" + "%n   Crit%%: " + getItemCriticalChance() +
                "%n   Stun%%: " + getItemStunChance() + "%n   S.Pwr: " + getItemSpellPower(), getItemAttackSpeed());
        return heroWeapon;
    }

    public String getItemSet() {
        return itemSet;
    }

    @Override
    String getItemModel() {
        return this.itemModel;
    }

    public String getWeaponType() {
        return this.wepType;
    }

    public int getItemMinDMG() {
        return this.itemMinDMG;
    }

    public int getItemMaxDMG() {
        return this.itemMaxDMG;
    }

    public double getItemAttackSpeed() {
        return this.itemAttackSpeed;
    }

    public int getItemCriticalChance() {
        return this.itemCriticalChance;
    }

    public int getItemStunChance() {
        return this.itemStunChance;
    }

    public int getItemSpellPower() {
        return this.itemSpellPower;
    }

    private String setWeaponType() {
        Random rand = new Random();
        int type = rand.nextInt(weaponType.length);
        this.wepType = weaponType[type];
        return this.wepType;
    }

    private String setWeaponName() {
        Random rand = new Random();
        int randItemSet = rand.nextInt(itemSets.length);
        this.itemSet = itemSets[randItemSet];
        this.itemName = itemSet + " " + getWeaponType();
        return this.itemName;
    }

    @Override
    public void setItemStats() {
        Random rand = new Random();
        this.itemLVL = getItemLVL();

        if (this.getWeaponType().equals("Axe")) {
            this.itemMinDMG = 2 + (getItemLVL() * 4);
            this.itemMaxDMG = this.itemMinDMG + (getItemLVL() * 2);
            this.itemAttackSpeed = 0.9 - (rand.nextInt(GameEngine.player1.getHeroLVL() + 1) * 0.01);
            this.itemCriticalChance = itemLVL;
            this.itemStunChance = (int) (getItemLVL() * 0.334);
            this.itemSpellPower = 0;
        } else if (this.getWeaponType().equals("Hammer")) {
            this.itemMinDMG = 2 + (getItemLVL() * 5);
            this.itemMaxDMG = this.itemMinDMG + 1 + (getItemLVL() * 2);
            this.itemAttackSpeed = 1.0 - (rand.nextInt(GameEngine.player1.getHeroLVL() + 1) * 0.015);
            this.itemCriticalChance = 0;
            this.itemStunChance = (int) (getItemLVL() * 1.25);
            this.itemSpellPower = 0;
        } else if (this.getWeaponType().equals("Sword")) {
            this.itemMinDMG = 3 + (getItemLVL() * 2);
            this.itemMaxDMG = this.itemMinDMG + 1 + getItemLVL();
            this.itemAttackSpeed = 0.75 - (rand.nextInt(GameEngine.player1.getHeroLVL() + 1) * 0.01);
            this.itemCriticalChance = 1 + itemLVL;
            this.itemStunChance = (int) (getItemLVL() * 0.25);
            this.itemSpellPower = (int) (getItemLVL() * 0.334);
        } else if (this.getWeaponType().equals("Bow")) {
            this.itemMinDMG = 2 + (getItemLVL() * 2);
            this.itemMaxDMG = this.itemMinDMG + (int) (getItemLVL() * 1.5);
            this.itemAttackSpeed = 0.66 - (this.itemLVL * 0.01);
            this.itemCriticalChance = 1 + (int) (this.itemLVL * 1.334);
            this.itemStunChance = 0;
            this.itemSpellPower = (int) (this.itemLVL * 0.5);
        } else if (this.getWeaponType().equals("Crossbow")) {
            this.itemMinDMG = 2 + (int) (getItemLVL() * 2.5);
            this.itemMaxDMG = this.itemMinDMG + 2 + getItemLVL();
            this.itemAttackSpeed = 0.80 - (rand.nextInt(GameEngine.player1.getHeroLVL() + 1) * 0.01);
            this.itemCriticalChance = 1 + (int) (0.34 * itemLVL);
            this.itemStunChance = (int) (getItemLVL() * 0.5);
            this.itemSpellPower = 0;
        } else if (this.getWeaponType().equals("Staff")) {
            this.itemMinDMG = 2 + (int) (getItemLVL() * 2.5);
            this.itemMaxDMG = this.itemMinDMG + 1 + getItemLVL();
            this.itemAttackSpeed = 0.85 - (rand.nextInt(GameEngine.player1.getHeroLVL() + 1) * 0.015);
            this.itemCriticalChance = (int) (0.5 * itemLVL);
            this.itemStunChance = (int) (getItemLVL() * 0.334);
            this.itemSpellPower = (int) (getItemLVL() * 0.75);
        } else if (this.getWeaponType().equals("Scepter")) {
            this.itemMinDMG = 1 + (int) (getItemLVL() * 2.5);
            this.itemMaxDMG = this.itemMinDMG + 2 + getItemLVL();
            this.itemAttackSpeed = 0.80 - (rand.nextInt(GameEngine.player1.getHeroLVL() + 1) * 0.01);
            this.itemCriticalChance = 1 + (int) (0.334 * itemLVL);
            this.itemStunChance = (int) (getItemLVL() * 0.75);
            this.itemSpellPower = 0;
        }
    }

    @Override
    void itemBonusesGenerator() {
        Random rand = new Random();
        int numberOfItemBonuses = 2 + this.itemLVL / 3;
        double monsterLVLBonuses;

        if (GameEngine.playerGameProgress == 0) {
            monsterLVLBonuses = 0;
        } else {
            if (GameEngine.playerGameProgress % 5 == 0) {
                monsterLVLBonuses = 2;
            } else {
                monsterLVLBonuses = (GameEngine.enemy.getMonsterLVL() - (1 - GameEngine.playerGameProgress / 5));
            }
        }
        ArrayList<String> itemBonus = new ArrayList<>(Arrays.asList("Max DMG", "AS", "Crit%", "Stun%", "S.Pwr",
                "Max DMG", "AS", "Crit%", "Stun%", "S.Pwr"));
        Collections.shuffle(itemBonus);
        for (int i = 0; i < numberOfItemBonuses; i++) {
            itemBonus.get(0);
            if (itemBonus.get(0).equals("Max DMG")) {
                this.itemMaxDMG += getItemLVL() + (monsterLVLBonuses * 2);
            } else if (itemBonus.get(0).equals("AS")) {
                this.itemAttackSpeed = this.itemAttackSpeed - ((getItemLVL() * 0.0125) + (monsterLVLBonuses * 0.02));
            } else if (itemBonus.get(0).equals("Crit%")) {
                this.itemCriticalChance += (int) (getItemLVL() * 0.5) + monsterLVLBonuses;
            } else if (itemBonus.get(0).equals("Stun%")) {
                this.itemStunChance += getItemLVL() + (int) (monsterLVLBonuses * 0.5);
            } else if (itemBonus.get(0).equals("S.Pwr")) {
                this.itemSpellPower += getItemLVL() + monsterLVLBonuses;
            }
            itemBonus.remove(0);
        }
        itemBonus.clear();
    }

    @Override
    void itemStatsForPrinting() {
        String whiteSpaces = "                              ";
        String fullDMG = getItemMinDMG() + "-" + getItemMaxDMG();
        this.itemStat1 = "DMG:" + whiteSpaces.substring(0, 8 - fullDMG.length()) + fullDMG;
        this.itemStat2 = String.format("AS:" + whiteSpaces.substring(0, 5) + "%.2f", getItemAttackSpeed());
        this.itemStat3 = "Crit%%:" + whiteSpaces.substring(0, 6 - Integer.toString(getItemCriticalChance()).length()) +
                String.valueOf(getItemCriticalChance());
        this.itemStat4 = "Stun%%:" + whiteSpaces.substring(0, 6 - Integer.toString(getItemStunChance()).length()) +
                String.valueOf(getItemStunChance());
        this.itemStat5 = "S.Pwr:" + whiteSpaces.substring(0, 6 - Integer.toString(getItemSpellPower()).length()) +
                String.valueOf(getItemSpellPower());
        this.itemStat6 = whiteSpaces.substring(0, 12);
        this.itemStat7 = whiteSpaces.substring(0, 12);
        this.itemStat8 = whiteSpaces.substring(0, 12);
        this.itemStat9 = whiteSpaces.substring(0, 12);
    }
}