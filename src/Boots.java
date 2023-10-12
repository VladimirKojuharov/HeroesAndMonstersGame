import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Boots extends Item {

    private String itemName;
    private String itemSet;
    private String itemModel;
    private double itemHPReg;
    private int itemEvasionChance;
    private double itemManaReg;
    private double itemAttackSpeed;

    private String[] itemSets = {"Death's", "King's", "Rush's", "Great", "Devil's", "Dragon's", "Hell's", "Crusher's",
            "Bone's", "Ghost"};
    private String[] itemBonuses = {"AS", "Evasion", "HP.Reg", "M.Reg"};

    public Boots() {
        super();
        this.setItemName();
        this.itemModel = "BOOTS";
        this.setItemStats();
        this.itemBonusesGenerator();
        this.itemStatsForPrinting();
    }

    public String getItemModel() {
        return this.itemModel;
    }

    public String getItemName() {
        return this.itemName;
    }

    public String getItemSet() {
        return this.itemSet;
    }

    public double getItemHPReg() {
        return this.itemHPReg;
    }

    public int getItemEvasionChance() {
        return this.itemEvasionChance;
    }

    public double getItemManaReg() {
        return this.itemManaReg;
    }

    public double getItemAttackSpeed() {
        return this.itemAttackSpeed;
    }

    private String setItemName() {
        Random rand = new Random();
        int randItemSet = rand.nextInt(itemSets.length);
        String BootsType = "Boots";
        this.itemSet = itemSets[randItemSet];
        this.itemName = itemSet + " Boots";
        return this.itemName;
    }

    @Override
    void setItemStats() {
        this.itemLVL = getItemLVL();
        this.itemHPReg = getItemLVL() * 0.3;
        this.itemEvasionChance = 0;
        this.itemManaReg = 0;
        this.itemAttackSpeed = getItemLVL() * 0.01;
    }

    @Override
    public void itemBonusesGenerator() {
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
        ArrayList<String> itemBonus = new ArrayList<>(Arrays.asList("HP.Reg", "M.Reg", "Evasion, AS", "HP.Reg", "M.Reg",
                "Evasion, AS"));
        Collections.shuffle(itemBonus);

        for (int i = 1; i <= numberOfItemBonuses; i++) {
            int abilityForBoost = rand.nextInt(itemBonuses.length);
            String chosenBonus = itemBonuses[abilityForBoost];

            if (itemBonus.get(0).equals("HP.Reg")) {
                this.itemHPReg += ((getItemLVL() * 0.2) + (monsterLVLBonuses * getItemLVL() * 0.1));
            } else if (itemBonus.get(0).equals("M.Reg")) {
                this.itemEvasionChance += (int) ((getItemLVL() + monsterLVLBonuses) * 0.25);
            } else if (itemBonus.get(0).equals("Evasion")) {
                this.itemManaReg += (getItemLVL() + monsterLVLBonuses) * 0.5;
            } else if (itemBonus.get(0).equals("AS")) {
                this.itemAttackSpeed += ((getItemLVL() + monsterLVLBonuses) * 0.01);
            }
        }
        itemBonus.remove(0);
    }

    @Override
    void itemStatsForPrinting() {
        String whiteSpaces = "                              ";
        this.itemStat1 = "HP.Reg:" + whiteSpaces.substring(0, 5 - Double.toString(getItemHPReg()).length()) +
                String.valueOf(getItemHPReg());
        this.itemStat2 = "Evsn:" + whiteSpaces.substring(0, 7 - Integer.toString(getItemEvasionChance()).length()) +
                String.valueOf(getItemEvasionChance());
        this.itemStat3 = "M.Reg:" + whiteSpaces.substring(0, 6 - Double.toString(getItemManaReg()).length()) +
                String.valueOf(getItemManaReg());
        this.itemStat4 = String.format("AS:" + whiteSpaces.substring(0, 5) + "%.2f", getItemAttackSpeed());
        this.itemStat5 = whiteSpaces.substring(0, 12);
        this.itemStat6 = whiteSpaces.substring(0, 12);
        this.itemStat7 = whiteSpaces.substring(0, 12);
        this.itemStat8 = whiteSpaces.substring(0, 12);
        this.itemStat9 = whiteSpaces.substring(0, 12);
    }

}