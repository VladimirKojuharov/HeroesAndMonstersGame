import java.util.Random;

public class Monster {

    private String monsterName;
    private int monsterLVL;
    public int monsterBaseHealth;
    private int monsterHealth;
    public int monsterHealthReg;
    private int monsterDamage;
    private double monsterSpeed;
    private int monsterArmor;
    private double monsterMana;
    public double monsterManaReg;
    public int monsterMagicResistance;
    private boolean monsterStunStatus = false;
    private int monsterSpellPower;
    public int monsterStunChance;
    public int monsterCritChance;
    public int monsterEvasionChance;
    public static boolean CursingCast = false;
    public static boolean skeletonArmorIncreasment = false;
    public static int hydraLifeTracker = 250;
    public static boolean lichKingPoisonActivation = false;


    String[] monsterNames = {"Troll       ", "Ogre        ", "Cyclops     ", "Lich        ", "Vampire     ",
            "Skeleton    ", "Werewolf    ", "Witch       ", "Zombie      ", "Harpy       ", "Medusa      ",
            "Minotaur    ", "Goblin      ", "Ork         "};
    String[] championMonsterNames = {"Hell Monarch", "Infernal", "Dread Knight", "Abomination ", "Оblivion", "Terror",
            "Demon Lord", "Lich King"};

    String[] bossMonsterNames = {"Hydra", "Fenix", "Titan", "Black Dragon", "Behemoth"};

    public Monster() {
        if (GameEngine.playerGameProgress == 40) {
            this.createBossType();
        } else if (GameEngine.playerGameProgress % 5 != 0) {
            this.createMonsterType();
        } else {
            this.createChampionMonsterType();
        }

        this.setMonsterLVL();
        this.setMonsterHealth(monsterHealth);
        if (GameEngine.playerGameProgress % 40 == 0) {
            this.setBossMonsterStats(getMonsterName());
        } else if (GameEngine.playerGameProgress % 5 != 0) {
            this.setMonsterStats(getMonsterName());
        } else {
            this.setChampionMonsterStats(getMonsterName());
        }
        this.setMonsterDMG(0);
        this.monsterBaseHealth = monsterHealth;
        this.setMonsterStunStatus(monsterStunStatus);
    }

    public String showMonsterStats() {
        String monsterStats = String.format(getMonsterName() + "%nLVL:   " + getMonsterLVL() + "%nHealt: " + getMonsterHealth() + "%nArmor: " +
                getMonsterArmor() + "%nDMG:   " + getMonsterDamage() + "%nAS:   " + getMonsterAttackSpeed() + "%n");
        return monsterStats;
    }

    public int getMonsterLVL() {
        return this.monsterLVL;
    }

    public int getMonsterHealth() {
        return this.monsterHealth;
    }

    public int getMonsterHealthReg() {
        return this.monsterHealthReg;
    }

    public int getMonsterBaseHealth() {
        return this.monsterBaseHealth;
    }

    public int getMonsterArmor() {
        return this.monsterArmor;
    }

    public int getMonsterEvasionChance() {
        return this.monsterEvasionChance;
    }

    public String getMonsterName() {
        return monsterName;
    }

    public int getMonsterDamage() {
        return this.monsterDamage;
    }

    public double getMonsterAttackSpeed() {
        return this.monsterSpeed;
    }

    public int getMonsterCritChance() {
        return this.monsterCritChance;
    }

    public int getMonsterStunChance() {
        return this.monsterStunChance;
    }

    public double getMonsterMana() {
        return this.monsterMana;
    }

    public double getMonsterManaReg() {
        return this.monsterManaReg;
    }

    public int getMonsterSpellPower() {
        return this.monsterSpellPower;
    }

    public int getMonsterMagicResistance() {
        return this.monsterMagicResistance;
    }

    public boolean getMonsterStunStatus() {
        return this.monsterStunStatus;
    }

    public String createMonsterType() {
        Random rand = new Random();
        int monster = rand.nextInt(monsterNames.length);
        this.monsterName = monsterNames[monster].trim();
        return this.monsterName;
    }

    public String createChampionMonsterType() {
        Random rand = new Random();
        int monster = rand.nextInt(championMonsterNames.length);
        this.monsterName = championMonsterNames[monster];
        return this.monsterName;
    }

    public String createBossType() {
        Random rand = new Random();
        int monster = rand.nextInt(bossMonsterNames.length);
        this.monsterName = bossMonsterNames[monster];
        return this.monsterName;
    }

    public void setMonsterHealth(int monsterHealth) {
        this.monsterHealth = monsterHealth;
    }

    public void setMonsterArmor(int armor) {
        this.monsterArmor += armor;
    }

    public void setMonsterAttackSpeed(double changeAttackSpeed) {
        this.monsterSpeed = changeAttackSpeed;
    }

    public void setMonsterMana(double monsterManaReg) {
        this.monsterMana = monsterManaReg;
    }

    private int setMonsterLVL() {
        Random rand = new Random();
        int advantageChance = rand.nextInt(10) + 1;
        if (advantageChance < 8) {
            monsterLVL = 1 + (GameEngine.playerGameProgress / 5) + 0;
        } else if (8 <= advantageChance && advantageChance <= 9) {
            monsterLVL = 1 + (GameEngine.playerGameProgress / 5) + 1;
        } else if (advantageChance == 10) {
            monsterLVL = 1 + (GameEngine.playerGameProgress / 5) + 2;
        }
        return monsterLVL;
    }

    private void setMonsterDMG(int dmgChanging) {
        this.monsterDamage += dmgChanging;
    }

    private void setMonsterStats(String monsterName) {
        Random rand = new Random();
        double attackSpeedCorrector = (rand.nextInt(1 + monsterLVL)) * 0.1;

        if (monsterName.trim().equals("Troll")) {

            this.monsterHealth = 12 + this.monsterLVL * 21 + (rand.nextInt(this.monsterLVL + 1));
            this.monsterHealthReg = 0;
            this.monsterArmor = this.monsterLVL * 2;
            this.monsterEvasionChance = this.monsterLVL;
            this.monsterDamage = 10 + this.monsterLVL * 5;
            this.monsterSpeed = 2.6 - (this.monsterLVL * 0.1) - attackSpeedCorrector;
            this.monsterCritChance = (int) (0.5 * this.monsterLVL);
            this.monsterStunChance = this.monsterLVL * 2;
            this.monsterSpellPower = this.monsterLVL * 2;
            this.monsterMagicResistance = this.monsterLVL;
            this.monsterMana = 0;
            this.monsterManaReg = 4.01 + 0.1 * this.monsterLVL + rand.nextInt(this.monsterLVL) * 0.1;

        } else if (monsterName.trim().equals("Ogre")) {
            this.monsterHealth = 9 + monsterLVL * 18 + (rand.nextInt(this.monsterLVL + 1));
            this.monsterHealthReg = 0;
            this.monsterArmor = this.monsterLVL * 2 - rand.nextInt(2);
            this.monsterEvasionChance = this.monsterLVL;
            this.monsterDamage = 7 + this.monsterLVL * 4;
            this.monsterSpeed = 2.05 - (this.monsterLVL * 0.05) - attackSpeedCorrector;
            this.monsterCritChance = (int) (0.5 * this.monsterLVL);
            this.monsterStunChance = this.monsterLVL * 2;
            this.monsterSpellPower = 1 + this.monsterLVL * 2;
            this.monsterMagicResistance = this.monsterLVL;
            this.monsterMana = 0;
            this.monsterManaReg = 2.51 + (0.25 * this.monsterLVL);

        } else if (monsterName.trim().equals("Cyclops")) {
            this.monsterHealth = 12 + this.monsterLVL * 12 + (rand.nextInt(this.monsterLVL + 1));
            this.monsterHealthReg = 0;
            this.monsterArmor = (this.monsterLVL - 1) * 2;
            this.monsterEvasionChance = (int) (this.monsterLVL * 1.5);
            this.monsterDamage = 4 + this.monsterLVL * 4 + rand.nextInt(this.monsterLVL);
            this.monsterSpeed = 1.5 - attackSpeedCorrector;
            this.monsterCritChance = (int) (1.5 * this.monsterLVL);
            this.monsterStunChance = this.monsterLVL;
            this.monsterSpellPower = this.monsterLVL - 1;
            this.monsterMagicResistance = this.monsterLVL * 2;
            this.monsterMana = 0;
            this.monsterManaReg = 1.5 + (0.25 * this.monsterLVL);

        } else if (monsterName.trim().equals("Lich")) {
            this.monsterHealth = 13 + this.monsterLVL * 15 + (rand.nextInt(this.monsterLVL + 1));
            this.monsterHealthReg = 0;
            this.monsterArmor = this.monsterLVL + 1;
            this.monsterEvasionChance = this.monsterLVL;
            this.monsterDamage = 2 + (this.monsterLVL * 5);
            this.monsterSpeed = 1.7 - attackSpeedCorrector;
            this.monsterCritChance = this.monsterLVL;
            this.monsterStunChance = (int) (this.monsterLVL * 0.34);
            this.monsterSpellPower = this.monsterLVL * 3;
            this.monsterMagicResistance = this.monsterLVL * 3;
            this.monsterMana = 0;
            this.monsterManaReg = 1.2 + 0.3 * this.monsterLVL;

        } else if (monsterName.trim().equals("Vampire")) {
            this.monsterHealth = 13 + this.monsterLVL * 15 + (rand.nextInt(this.monsterLVL + 1));
            this.monsterDamage = 3 + this.monsterLVL * 6;
            this.monsterHealthReg = (int) (this.monsterDamage * 0.5);
            this.monsterArmor = this.monsterLVL - 1;
            this.monsterEvasionChance = this.monsterLVL * 2;
            this.monsterSpeed = 1.7 - attackSpeedCorrector;
            this.monsterCritChance = 3 * this.monsterLVL;
            this.monsterStunChance = this.monsterLVL;
            this.monsterSpellPower = 0;
            this.monsterMagicResistance = (int) (this.monsterLVL * 1.5);
            this.monsterMana = 0;
            this.monsterManaReg = 0;

        } else if (monsterName.trim().equals("Skeleton")) {
            this.monsterHealth = 6 + this.monsterLVL * 15 + (rand.nextInt(this.monsterLVL * 2 + 1));
            this.monsterDamage = 5 + this.monsterLVL * 3;
            this.monsterHealthReg = 0;
            this.monsterArmor = this.monsterLVL;
            this.monsterEvasionChance = this.monsterLVL * 2;
            this.monsterSpeed = 1.8 - attackSpeedCorrector;
            this.monsterCritChance = this.monsterLVL * 3;
            this.monsterStunChance = this.monsterLVL;
            this.monsterSpellPower = 0;
            this.monsterMagicResistance = this.monsterLVL * 3;
            this.monsterMana = 0;
            this.monsterManaReg = 0;

        } else if (monsterName.trim().equals("Werewolf")) {
            this.monsterHealth = 9 + this.monsterLVL * 17 + (rand.nextInt(this.monsterLVL + 1));
            this.monsterDamage = 3 + this.monsterLVL * 5;
            this.monsterHealthReg = this.monsterLVL * 2;
            this.monsterArmor = this.monsterLVL;
            this.monsterEvasionChance = this.monsterLVL * 2;
            this.monsterSpeed = 2.223 - attackSpeedCorrector;
            this.monsterCritChance = 2 * this.monsterLVL;
            this.monsterStunChance = this.monsterLVL;
            this.monsterSpellPower = 0;
            this.monsterMagicResistance = this.monsterLVL * 2;
            this.monsterMana = 0;
            this.monsterManaReg = 0;

        } else if (monsterName.trim().equals("Witch")) {
            this.monsterHealth = 19 + this.monsterLVL * 15 + (rand.nextInt(this.monsterLVL + 1));
            this.monsterDamage = 3 + this.monsterLVL * 4;
            this.monsterHealthReg = 0;
            this.monsterArmor = this.monsterLVL;
            this.monsterEvasionChance = 3 + this.monsterLVL;
            this.monsterSpeed = 1.6 - attackSpeedCorrector;
            this.monsterCritChance = this.monsterLVL;
            this.monsterStunChance = (int) (this.monsterLVL * 0.5);
            this.monsterSpellPower = 3 + (this.monsterLVL * 2);
            this.monsterMagicResistance = this.monsterLVL * 2;
            this.monsterMana = 0;
            this.monsterManaReg = 1 + this.monsterLVL;

        } else if (monsterName.trim().equals("Zombie")) {
            this.monsterHealth = 11 + this.monsterLVL * 17 + (rand.nextInt(this.monsterLVL * 2));
            this.monsterDamage = 4 + this.monsterLVL * 4;
            this.monsterHealthReg = 0;
            this.monsterArmor = 2 + this.monsterLVL;
            this.monsterEvasionChance = this.monsterLVL * 2;
            this.monsterSpeed = 2 - attackSpeedCorrector;
            this.monsterCritChance = this.monsterLVL;
            this.monsterStunChance = (int) (this.monsterLVL * 1.5);
            this.monsterSpellPower = 1 + this.monsterLVL + rand.nextInt(this.monsterLVL);
            this.monsterMagicResistance = this.monsterLVL + rand.nextInt(this.monsterLVL);
            this.monsterMana = 0;
            this.monsterManaReg = 1.1 * this.monsterLVL;

        } else if (monsterName.trim().equals("Harpy")) {
            this.monsterHealth = 10 + this.monsterLVL * 12 + (rand.nextInt(this.monsterLVL * 2));
            this.monsterDamage = 3 + this.monsterLVL * 5;
            this.monsterHealthReg = 0;
            this.monsterArmor = 2 + this.monsterLVL;
            this.monsterEvasionChance = 2 + this.monsterLVL * 2;
            this.monsterSpeed = 1.5 - attackSpeedCorrector;
            this.monsterCritChance = this.monsterLVL * 4;
            this.monsterStunChance = (int) (this.monsterLVL * 0.5);
            this.monsterSpellPower = this.monsterLVL * 2;
            this.monsterMagicResistance = this.monsterLVL * 3;
            this.monsterMana = 0;
            this.monsterManaReg = 1 * this.monsterLVL;

        } else if (monsterName.trim().equals("Medusa")) {
            this.monsterHealth = 8 + this.monsterLVL * 17 + (rand.nextInt(this.monsterLVL * 2));
            this.monsterDamage = 5 + this.monsterLVL * 3;
            this.monsterHealthReg = 0;
            this.monsterArmor = 1 + this.monsterLVL;
            this.monsterEvasionChance = 1 + this.monsterLVL * 2;
            this.monsterSpeed = 1.4 - attackSpeedCorrector / 2;
            this.monsterCritChance = this.monsterLVL * 2;
            this.monsterStunChance = this.monsterLVL * 2 + rand.nextInt(this.monsterLVL);
            this.monsterSpellPower = 1 + this.monsterLVL * 2;
            this.monsterMagicResistance = this.monsterLVL * 3;
            this.monsterMana = 0;
            this.monsterManaReg = 1.1 * this.monsterLVL;

        } else if (monsterName.trim().equals("Minotaur")) {
            this.monsterHealth = 10 + this.monsterLVL * 18 + rand.nextInt(this.monsterLVL);
            this.monsterDamage = 4 + this.monsterLVL * 5;
            this.monsterHealthReg = 0;
            this.monsterArmor = this.monsterLVL * 2;
            this.monsterEvasionChance = 3 + this.monsterLVL;
            this.monsterSpeed = 1.6 - attackSpeedCorrector;
            this.monsterCritChance = this.monsterLVL * 2;
            this.monsterStunChance = this.monsterLVL + rand.nextInt(this.monsterLVL);
            this.monsterSpellPower = this.monsterLVL * 2;
            this.monsterMagicResistance = 1 + this.monsterLVL;
            this.monsterMana = 0;
            this.monsterManaReg = 1 * this.monsterLVL;

        } else if (monsterName.trim().equals("Goblin")) {
            this.monsterHealth = 5 + this.monsterLVL * 16 + (rand.nextInt(this.monsterLVL * 2));
            this.monsterDamage = 4 + this.monsterLVL * 4;
            this.monsterHealthReg = 0;
            this.monsterArmor = 2 + this.monsterLVL;
            this.monsterEvasionChance = 2 + this.monsterLVL * 2;
            this.monsterSpeed = 1.5 - attackSpeedCorrector / 1.6;
            this.monsterCritChance = this.monsterLVL * 2 + rand.nextInt(this.monsterLVL);
            this.monsterStunChance = this.monsterLVL * 2 + rand.nextInt(this.monsterLVL);
            this.monsterSpellPower = this.monsterLVL + rand.nextInt(this.monsterLVL);
            this.monsterMagicResistance = this.monsterLVL * 3;
            this.monsterMana = 0;
            this.monsterManaReg = this.monsterLVL;

        } else if (monsterName.trim().equals("Ork")) {
            this.monsterHealth = 13 + this.monsterLVL * 16 + (rand.nextInt(this.monsterLVL * 2));
            this.monsterDamage = 2 + this.monsterLVL * 6;
            this.monsterHealthReg = this.monsterLVL;
            this.monsterArmor = (int) (this.monsterLVL * 1.5);
            this.monsterEvasionChance = 1 + this.monsterLVL;
            this.monsterSpeed = 1.5 - attackSpeedCorrector / 2;
            this.monsterCritChance = this.monsterLVL * 2;
            this.monsterStunChance = this.monsterLVL * 2;
            this.monsterSpellPower = 1 + this.monsterLVL;
            this.monsterMagicResistance = this.monsterLVL * 2;
            this.monsterMana = 1;
            this.monsterManaReg = 0.7 * this.monsterLVL;
        }
    }

    private void setChampionMonsterStats(String championMonsterStats) {
        Random rand = new Random();
        this.monsterLVL = GameEngine.playerGameProgress / 5;

        if (championMonsterStats.trim().equals("Hell Monarch")) {
            this.monsterHealth = 25 + this.monsterLVL * 35 + (rand.nextInt(this.monsterLVL + 1));
            this.monsterHealthReg = 0;
            this.monsterArmor = this.monsterLVL * 2;
            this.monsterEvasionChance = 3 + (int) (this.monsterLVL * 1.5);
            this.monsterDamage = 10 + this.monsterLVL * 5;
            this.monsterSpeed = 2.1 - 0.1 * this.monsterLVL;
            this.monsterCritChance = 4 + this.monsterLVL;
            this.monsterStunChance = (int) (this.monsterLVL * 1.6);
            this.monsterMana = 0;
            this.monsterManaReg = 2.5 + 0.1 * this.monsterLVL + rand.nextInt(this.monsterLVL) * 0.1;
            this.monsterSpellPower = 1 + (this.monsterLVL * 3);
            this.monsterMagicResistance = 1 + (this.monsterLVL * 3);

        } else if (championMonsterStats.trim().equals("Infernal")) {
            this.monsterHealth = 25 + this.monsterLVL * 30 + (rand.nextInt(monsterLVL + 1));
            this.monsterHealthReg = 0;
            this.monsterArmor = this.monsterLVL * 3;
            this.monsterEvasionChance = 2 + (int) (this.monsterLVL);
            this.monsterDamage = 15 + this.monsterLVL * 5;
            this.monsterSpeed = 2.5 - (0.1 * this.monsterLVL);
            this.monsterCritChance = 1 + this.monsterLVL;
            this.monsterStunChance = this.monsterLVL * 2;
            this.monsterMana = 0;
            this.monsterManaReg = 0;
            this.monsterSpellPower = 0;
            this.monsterMagicResistance = 1 + (this.monsterLVL * 3);

        } else if (championMonsterStats.trim().equals("Dread Knight")) {
            this.monsterHealth = 20 + this.monsterLVL * 30 + (rand.nextInt(monsterLVL + 1));
            this.monsterHealthReg = this.monsterLVL * 2;
            this.monsterArmor = 1 + this.monsterLVL * 3;
            this.monsterEvasionChance = 3 + this.monsterLVL * 2;
            this.monsterDamage = 5 + this.monsterLVL * 8;
            this.monsterSpeed = 1.6 - 0.05 * monsterLVL;
            this.monsterCritChance = 3 + (2 * this.monsterLVL);
            this.monsterStunChance = 1 + this.monsterLVL;
            this.monsterMana = 0;
            this.monsterManaReg = 1.3 + 0.3 * this.monsterLVL;
            this.monsterSpellPower = (int) (this.monsterLVL * 0.5);
            this.monsterMagicResistance = this.monsterLVL * 2;

        } else if (championMonsterStats.trim().equals("Abomination")) {
            this.monsterHealth = 15 + this.monsterLVL * 35 + (rand.nextInt(monsterLVL + 1));
            this.monsterHealthReg = 0;
            this.monsterArmor = this.monsterLVL * 2;
            this.monsterEvasionChance = 3 + this.monsterLVL;
            this.monsterDamage = 6 + this.monsterLVL * 10;
            this.monsterSpeed = 2.7 - 0.15 * monsterLVL;
            this.monsterCritChance = 2 + this.monsterLVL;
            this.monsterStunChance = 3 + this.monsterLVL;
            this.monsterMana = 0;
            this.monsterManaReg = 2 + 0.3 * this.monsterLVL;
            this.monsterSpellPower = this.monsterLVL;
            this.monsterMagicResistance = this.monsterLVL;

        } else if (championMonsterStats.trim().equals("Оblivion")) {
            this.monsterHealth = 12 + this.monsterLVL * 33 + (2 * (rand.nextInt(monsterLVL + 1)));
            this.monsterHealthReg = 0;
            this.monsterArmor = this.monsterLVL * 3;
            this.monsterEvasionChance = 6 + this.monsterLVL * 2;
            this.monsterDamage = 10 + this.monsterLVL * 5;
            this.monsterSpeed = 2 - 0.1 * this.monsterLVL;
            this.monsterCritChance = 1 + (int) (this.monsterLVL * 1.5);
            this.monsterStunChance = (int) (this.monsterLVL * 0.5);
            this.monsterMana = 3;
            this.monsterManaReg = 2 + 0.3 * this.monsterLVL;
            this.monsterSpellPower = this.monsterLVL * 3;
            this.monsterMagicResistance = this.monsterLVL * 3;

        } else if (championMonsterStats.trim().equals("Terror")) {
            this.monsterHealth = 20 + this.monsterLVL * 35 + (2 * (rand.nextInt(monsterLVL + 1)));
            this.monsterHealthReg = 0;
            this.monsterArmor = this.monsterLVL * 2;
            this.monsterEvasionChance = 1 + this.monsterLVL * 2;
            this.monsterDamage = 10 + this.monsterLVL * 7;
            this.monsterSpeed = 2.8 - 0.1 * this.monsterLVL;
            this.monsterCritChance = 1 + (int) (this.monsterLVL * 1.5);
            this.monsterStunChance = this.monsterLVL;
            this.monsterMana = 0;
            this.monsterManaReg = 3;
            this.monsterSpellPower = this.monsterLVL * 3;
            this.monsterMagicResistance = this.monsterLVL * 2;

        } else if (championMonsterStats.trim().equals("Demon Lord")) {
            this.monsterHealth = 15 + this.monsterLVL * 25 + (2 * (rand.nextInt(monsterLVL + 1)));
            this.monsterHealthReg = 4 + this.monsterLVL;
            this.monsterArmor = this.monsterLVL;
            this.monsterEvasionChance = 1 + (int) (this.monsterLVL * 2.5);
            this.monsterDamage = 10 + this.monsterLVL * 5;
            this.monsterSpeed = 1.9 - 0.05 * this.monsterLVL;
            this.monsterCritChance = 1 + (int) (this.monsterLVL * 2);
            this.monsterStunChance = this.monsterLVL;
            this.monsterMana = 0;
            this.monsterManaReg = 2.501;
            this.monsterSpellPower = (this.monsterLVL * 3) + rand.nextInt(monsterLVL + 1);
            this.monsterMagicResistance = this.monsterLVL * 3;

        } else if (championMonsterStats.trim().equals("Lich King")) {
            this.monsterHealth = 20 + this.monsterLVL * 20 + (2 * (rand.nextInt(monsterLVL + 1)));
            this.monsterHealthReg = 0;
            this.monsterArmor = this.monsterLVL * 4;
            this.monsterEvasionChance = this.monsterLVL;
            this.monsterDamage = 9 + this.monsterLVL * 4;
            this.monsterSpeed = 2.05 - 0.05 * monsterLVL;
            this.monsterCritChance = 1 + (int) (this.monsterLVL * 2);
            this.monsterStunChance = this.monsterLVL;
            this.monsterMana = 0;
            this.monsterManaReg = 3;
            this.monsterSpellPower = this.monsterLVL * 3;
            this.monsterMagicResistance = this.monsterLVL * 4;
        }
    }

    public void setBossMonsterStats(String bossType) {
        if (bossType.trim().equals("Hydra")) {
            this.monsterHealth = 500;
            this.monsterHealthReg = 15;
            this.monsterArmor = 30;
            this.monsterEvasionChance = 5;
            this.monsterDamage = 55;
            this.monsterSpeed = 1.8;
            this.monsterCritChance = 20;
            this.monsterStunChance = 10;
            this.monsterMana = 0;
            this.monsterManaReg = 0;
            this.monsterSpellPower = 15;
            this.monsterMagicResistance = 30;

        } else if (bossType.trim().equals("Fenix")) {
            this.monsterHealth = 450;
            this.monsterHealthReg = 25;
            this.monsterArmor = 25;
            this.monsterEvasionChance = 35;
            this.monsterDamage = 50;
            this.monsterSpeed = 1.4;
            this.monsterCritChance = 40;
            this.monsterStunChance = 5;
            this.monsterMana = 0;
            this.monsterManaReg = 13;
            this.monsterSpellPower = 50;
            this.monsterMagicResistance = 40;

        } else if (bossType.trim().equals("Titan")) {
            this.monsterHealth = 600;
            this.monsterHealthReg = 0;
            this.monsterArmor = 20;
            this.monsterEvasionChance = 20;
            this.monsterDamage = 65;
            this.monsterSpeed = 1.7;
            this.monsterCritChance = 10;
            this.monsterStunChance = 20;
            this.monsterMana = 5;
            this.monsterManaReg = 8;
            this.monsterSpellPower = 45;
            this.monsterMagicResistance = 30;

        } else if (bossType.trim().equals("Black Dragon")) {
            this.monsterHealth = 550;
            this.monsterHealthReg = 15;
            this.monsterArmor = 20;
            this.monsterEvasionChance = 20;
            this.monsterDamage = 70;
            this.monsterSpeed = 2;
            this.monsterCritChance = 15;
            this.monsterStunChance = 15;
            this.monsterMana = 5;
            this.monsterManaReg = 8;
            this.monsterSpellPower = 50;
            this.monsterMagicResistance = 35;

        } else if (bossType.trim().equals("Behemoth")) {
            this.monsterHealth = 500;
            this.monsterHealthReg = 35;
            this.monsterArmor = 20;
            this.monsterEvasionChance = 20;
            this.monsterDamage = 50;
            this.monsterSpeed = 1.6;
            this.monsterCritChance = 25;
            this.monsterStunChance = 20;
            this.monsterMana = 0;
            this.monsterManaReg = 7;
            this.monsterSpellPower = 0;
            this.monsterMagicResistance = 20;
        }
    }

    public static void activateMonsterSpecialSkill(String monsterType) throws InterruptedException {
        if (monsterType.equals("Troll")) {
            if (GameEngine.enemy.getMonsterAttackSpeed() > 2 && GameEngine.enemy.getMonsterMana() >= 2 + GameEngine.enemy.getMonsterLVL()) {
                GameEngine.enemy.setMonsterMana(GameEngine.enemy.getMonsterMana() - (2 + GameEngine.enemy.getMonsterLVL()));
                System.out.println("🕮 Troll cast BERSERK increasing his attack speed by 20%.");
                Thread.sleep(1000L);
                GameEngine.enemy.setMonsterAttackSpeed(GameEngine.enemy.getMonsterAttackSpeed() * 0.8);
            }
            if (GameEngine.enemy.getMonsterMana() >= 4 + GameEngine.enemy.getMonsterMana()) {
                System.out.printf("🕮 Troll cast FIREBLAST dealing %d damage to %s.%n",
                        (GameEngine.enemy.getMonsterSpellPower() * 3) - GameEngine.player1.getHeroMagicResistance(),
                        GameEngine.playerName);
                Thread.sleep(1000L);
                GameEngine.enemy.setMonsterMana(GameEngine.enemy.getMonsterMana() - (4 + GameEngine.enemy.getMonsterLVL()));
            }

        } else if (monsterType.equals("Ogre")) {
            if (GameEngine.enemy.getMonsterMana() >= 2 && GameEngine.player1.getHeroAttackSpeed() < 3) {
                GameEngine.enemy.setMonsterMana(GameEngine.enemy.getMonsterMana() - 2);
                GameEngine.player1.setHeroAttackSpeed(GameEngine.player1.getHeroAttackSpeed() * 1.2);
                if (GameEngine.player1.getHeroAttackSpeed() > 3) {
                    GameEngine.player1.setHeroAttackSpeed(3);
                }
                System.out.printf("Ogre cast SLOW decrease %s attack speed to %.2f.%n", GameEngine.playerName,
                        GameEngine.player1.getHeroAttackSpeed() * 0.2);
                Thread.sleep(1000L);
            }

        } else if (monsterType.equals("Cyclops")) {
            if (GameEngine.enemy.getMonsterMana() >= 2 + GameEngine.enemy.monsterLVL) {
                GameEngine.enemy.setMonsterMana(GameEngine.enemy.getMonsterMana() - (2 + GameEngine.enemy.monsterLVL));
                GameEngine.player1.setHeroStunStatus(true);
                int cyclopsRockTrowDMG = 3 + GameEngine.enemy.getMonsterLVL() + GameEngine.enemy.getMonsterSpellPower() -
                        GameEngine.player1.getHeroMagicResistance();
                if (cyclopsRockTrowDMG < 1) {
                    cyclopsRockTrowDMG = 1;
                }
                System.out.printf("Cyclops trow giant rock at the hero dealing %d DMG and stunning him.%n", cyclopsRockTrowDMG);
                Thread.sleep(1000L);
                GameEngine.player1.setHeroCurrentHealth(GameEngine.player1.getHeroCurrentHealth() - (3 + GameEngine.enemy.getMonsterLVL()));
                GameEngine.player1.setHeroStunStatus(true);
            }

        } else if (monsterType.equals("Lich")) {
            if (GameEngine.enemy.getMonsterMana() >= 3) {
                GameEngine.enemy.setMonsterMana(GameEngine.enemy.getMonsterMana() - 3);
                int lichSorrowRippleDMG = (2 * GameEngine.enemy.getMonsterSpellPower()) - GameEngine.player1.getHeroMagicResistance();
                if (lichSorrowRippleDMG < 1) {
                    lichSorrowRippleDMG = 1;
                }
                System.out.printf("🕮 Lich cast SORROW RIPPLE dealing %d DMG to %s.%n", lichSorrowRippleDMG, GameEngine.playerName);
                Thread.sleep(1000L);
                GameEngine.player1.setHeroCurrentHealth(GameEngine.player1.getHeroCurrentHealth() - lichSorrowRippleDMG);
            }
        } else if (monsterType.equals("Skeleton")) {
            if (CursingCast == false) {
                CursingCast = true;
                System.out.printf("Skeleton CURS the %s to deliver minimum damage.%n", GameEngine.playerName);
                Thread.sleep(1000L);
                GameEngine.player1.setHeroMaxDMG(GameEngine.player1.getHeroMinDMG());
            }
            if (skeletonArmorIncreasment == false) {
                if (GameEngine.enemy.getMonsterHealth() < (int) (5 + (GameEngine.enemy.getMonsterLVL() * 8))) {
                    System.out.printf("Skeleton gain %d armor. %n", GameEngine.enemy.getMonsterLVL() * 2);
                    Thread.sleep(1000L);
                    GameEngine.enemy.setMonsterArmor(GameEngine.enemy.getMonsterLVL() * 2);
                    skeletonArmorIncreasment = true;
                }
            }

        } else if (monsterType.equals("Werewolf")) {
            if (GameEngine.enemy.getMonsterAttackSpeed() >= 1.70) {
                System.out.println("Werewolf become furious increasing his attack speed by 15% and armor by 1.");
                Thread.sleep(1000L);
                GameEngine.enemy.setMonsterAttackSpeed(GameEngine.enemy.getMonsterAttackSpeed() * 0.85);
                GameEngine.enemy.setMonsterArmor(1);
            }

        } else if (monsterType.equals("Witch")) {
            int witchSpiritExtraxtDMG = GameEngine.enemy.getMonsterLVL() + GameEngine.enemy.getMonsterSpellPower() -
                    GameEngine.player1.getHeroMagicResistance();
            Thread.sleep(1000L);
            if (witchSpiritExtraxtDMG < 1) {
                witchSpiritExtraxtDMG = 1;
            }
            if (GameEngine.enemy.getMonsterMana() >= 3 + (int) (GameEngine.enemy.getMonsterLVL() * 0.5)) {
                System.out.printf("🕮 Witch cast SPIRIT EXTRACT to %s dealing %d damage.%n", GameEngine.playerName, witchSpiritExtraxtDMG);
                Thread.sleep(1000L);
                GameEngine.player1.setHeroCurrentHealth(GameEngine.player1.getHeroCurrentHealth() - witchSpiritExtraxtDMG);
                GameEngine.enemy.setMonsterMana(GameEngine.enemy.getMonsterMana() - (4 + GameEngine.enemy.getMonsterLVL()));
            }
            if (GameEngine.enemy.getMonsterMana() >= 2 + (int) (GameEngine.enemy.getMonsterLVL() * 0.5)) {
                System.out.printf("🕮 Witch cast HEALING and gain %d health.%n", 2 + (GameEngine.enemy.getMonsterLVL() * 2));
                Thread.sleep(1000L);
                GameEngine.enemy.setMonsterHealth(GameEngine.enemy.getMonsterHealth() + (2 + GameEngine.enemy.getMonsterLVL() * 2));
                GameEngine.enemy.setMonsterMana(GameEngine.enemy.getMonsterMana() - (3 + GameEngine.enemy.getMonsterLVL()));
            }

        } else if (monsterType.equals("Zombie")) {
            if (GameEngine.enemy.getMonsterMana() >= 1 + GameEngine.enemy.getMonsterLVL()) {
                GameEngine.enemy.setMonsterMana(GameEngine.enemy.getMonsterMana() - (1 + GameEngine.enemy.monsterLVL));

                int zombiePlague = GameEngine.enemy.getMonsterSpellPower() * 2 - (int) (GameEngine.player1.getHeroMagicResistance() * 0.5);
                if (zombiePlague < 1) {
                    zombiePlague = 1;
                }
                System.out.printf("🕮 Zombie cast Plague deal %d damage to %s.%n", zombiePlague, GameEngine.playerName);
                Thread.sleep(1000L);
                GameEngine.player1.setHeroCurrentHealth(GameEngine.player1.getHeroCurrentHealth() - zombiePlague);
            }

        } else if (monsterType.equals("Harpy")) {
            if (GameEngine.enemy.getMonsterMana() >= 1 + GameEngine.enemy.getMonsterLVL()) {
                GameEngine.enemy.setMonsterMana(GameEngine.enemy.getMonsterMana() - (1 + GameEngine.enemy.getMonsterLVL()));
                int nailsStrike = (int) (1 + GameEngine.enemy.getMonsterSpellPower() * 2 * GameEngine.enemy.getMonsterCritChance() * 0.1) -
                        (int) (GameEngine.player1.getHeroMagicResistance() * 0.5);
                if (nailsStrike < 1) {
                    nailsStrike = 1;
                }
                System.out.printf("🕮 Harpy attack %s with Nails Strike, deal him %d damage.%n", GameEngine.playerName, nailsStrike);
                Thread.sleep(1000L);
                GameEngine.player1.setHeroCurrentHealth(GameEngine.player1.getHeroCurrentHealth() - nailsStrike);
            }

        } else if (monsterType.equals("Medusa")) {
            if (GameEngine.enemy.getMonsterMana() >= 1 + GameEngine.enemy.getMonsterLVL()) {
                GameEngine.enemy.setMonsterMana(GameEngine.enemy.getMonsterMana() - (1 + GameEngine.enemy.getMonsterLVL()));
                int snakesStrike = 2 + GameEngine.enemy.getMonsterSpellPower() - (int) (GameEngine.player1.getHeroMagicResistance() * 0.5);
                if (snakesStrike < 1) {
                    snakesStrike = 1;
                }
                System.out.printf("🕮 Medusa make Snakes Strike attack and deal %d damage to %s and gain %d Health.%n",
                        snakesStrike, GameEngine.playerName, snakesStrike);
                Thread.sleep(1000L);
                GameEngine.enemy.setMonsterHealth(GameEngine.enemy.getMonsterHealth() + snakesStrike);
                GameEngine.player1.setHeroCurrentHealth(GameEngine.player1.getHeroCurrentHealth() - snakesStrike);
            }

        } else if (monsterType.equals("Minotaur")) {
            if (GameEngine.enemy.getMonsterMana() >= 1 + GameEngine.enemy.getMonsterLVL()) {
                GameEngine.enemy.setMonsterMana(GameEngine.enemy.getMonsterMana() - (1 + GameEngine.enemy.getMonsterLVL()));
                int slashAttack = GameEngine.enemy.getMonsterDamage() / 2 + GameEngine.enemy.getMonsterSpellPower() -
                        GameEngine.player1.getHeroMagicResistance();
                if (slashAttack < 1) {
                    slashAttack = 1;
                }
                System.out.printf("🕮 Minotaur make Slash Attack and deal %d damage to %s and gain %d Health.%n",
                        slashAttack, GameEngine.playerName, slashAttack);
                Thread.sleep(1000L);
                GameEngine.player1.setHeroCurrentHealth(GameEngine.player1.getHeroCurrentHealth() - slashAttack);
            }

        } else if (monsterType.equals("Goblin")) {
            if (GameEngine.enemy.getMonsterMana() >= 1 + GameEngine.enemy.getMonsterLVL()) {
                GameEngine.enemy.setMonsterMana(GameEngine.enemy.getMonsterMana() - (1 + GameEngine.enemy.getMonsterLVL()));
                int trowingSpear = GameEngine.enemy.getMonsterSpellPower() * 2 - (GameEngine.player1.getHeroMagicResistance());
                if (trowingSpear < 1) {
                    trowingSpear = 1;
                }
                System.out.printf("🕮 Goblin trow a Spire dealing %d damage to %s.%n",
                        trowingSpear, GameEngine.playerName);
                Thread.sleep(1000L);
                GameEngine.player1.setHeroCurrentHealth(GameEngine.player1.getHeroCurrentHealth() - trowingSpear);
            }

        } else if (monsterType.equals("Ork")) {
            if (GameEngine.enemy.getMonsterMana() >= 1 + GameEngine.enemy.getMonsterLVL()) {
                GameEngine.enemy.setMonsterMana(GameEngine.enemy.getMonsterMana() - (1 + GameEngine.enemy.getMonsterLVL()));
                int orkVoodoo = GameEngine.enemy.getMonsterSpellPower() * 2 - (int) (GameEngine.player1.getHeroMagicResistance() * 0.5);
                if (orkVoodoo < 1) {
                    orkVoodoo = 1;
                }
                int orkLifeRestore = GameEngine.enemy.getMonsterLVL() * 2;
                System.out.printf("🕮 Ork cast Voodoo and restore %d Health, gain 2 Armor and deal %d damage to %s.%n",
                        orkLifeRestore, orkVoodoo, GameEngine.playerName);
                GameEngine.enemy.setMonsterHealth(GameEngine.enemy.getMonsterHealth() + GameEngine.enemy.getMonsterLVL());
                GameEngine.enemy.setMonsterArmor(2);
                Thread.sleep(1000L);
                GameEngine.player1.setHeroCurrentHealth(GameEngine.player1.getHeroCurrentHealth() - orkVoodoo);
            }

        } else if (monsterType.equals("Оblivion")) {
            int oblivionManaBurnDMG = 2 + GameEngine.enemy.getMonsterSpellPower() - GameEngine.player1.getHeroMagicResistance();
            if (oblivionManaBurnDMG < 1) {
                oblivionManaBurnDMG = 1;
            }
            int oblivionManaBurning = 2 + GameEngine.enemy.getMonsterLVL();
            if (GameEngine.enemy.getMonsterMana() >= 2 + GameEngine.enemy.getMonsterLVL()) {
                System.out.printf("🕮 Oblivion cast MANA BURN deal %d damage and burning %d mana of %s.%n",
                        oblivionManaBurnDMG, oblivionManaBurning, GameEngine.playerName);
                Thread.sleep(1000L);
                GameEngine.enemy.setMonsterMana(GameEngine.enemy.getMonsterMana() - (3 + GameEngine.enemy.getMonsterLVL()));
                GameEngine.player1.setHeroCurrentMana(GameEngine.player1.getHeroCurrentMana() - oblivionManaBurning);
                if (GameEngine.player1.getHeroCurrentMana() < 1) {
                    GameEngine.player1.setHeroCurrentMana(0);
                }
                GameEngine.player1.setHeroCurrentHealth(GameEngine.player1.getHeroCurrentHealth() - oblivionManaBurnDMG);
            }
        } else if (monsterType.equals("Infernal")) {
            int infernalBurningDMG = 2 + GameEngine.enemy.getMonsterLVL() * 3 - (int) (GameEngine.player1.getHeroMagicResistance() * 0.5);
            if (infernalBurningDMG < 1) {
                infernalBurningDMG = 1;
            }
            System.out.printf("Infernal burn %s deal him %d damage.%n", GameEngine.playerName, infernalBurningDMG);
            Thread.sleep(1000L);
            GameEngine.player1.setHeroCurrentHealth(GameEngine.player1.getHeroCurrentHealth() - infernalBurningDMG);

        } else if (monsterType.equals("Abomination")) {
            int abominationLifeDrainDMG = 3 + GameEngine.enemy.getMonsterLVL() + GameEngine.enemy.getMonsterSpellPower() -
                    GameEngine.player1.getHeroMagicResistance();
            if (abominationLifeDrainDMG < 1) {
                abominationLifeDrainDMG = 1;
            }
            if (GameEngine.enemy.getMonsterMana() >= 2 + GameEngine.enemy.getMonsterLVL()) {
                int abominationHealthInTheBegining = GameEngine.enemy.getMonsterHealth();
                System.out.printf("🕮 Abomination cast LIFE DRAIN dealing %d Health Point from %s.%n",
                        abominationLifeDrainDMG, GameEngine.playerName);
                Thread.sleep(1000L);
                GameEngine.enemy.setMonsterMana(GameEngine.enemy.getMonsterMana() - (2 + GameEngine.enemy.getMonsterLVL()));
                GameEngine.player1.setHeroCurrentHealth(GameEngine.player1.getHeroCurrentHealth() - abominationLifeDrainDMG);
                GameEngine.enemy.setMonsterHealth(GameEngine.enemy.getMonsterHealth() + abominationLifeDrainDMG);
                if (GameEngine.enemy.getMonsterHealth() > abominationHealthInTheBegining) {
                    GameEngine.enemy.setMonsterHealth(abominationHealthInTheBegining);
                }
            }
        } else if (monsterType.equals("Dread Knight")) {
            if (GameEngine.enemy.getMonsterMana() >= 5 + GameEngine.enemy.getMonsterHealth() +
                    (int) (GameEngine.player1.getHeroMagicResistance() * 0.2)) {
                System.out.printf("🕮 Dread Knight cast BLIND to %s.%n", GameEngine.playerName);
                Thread.sleep(1000L);
                GameEngine.player1.setHeroStunStatus(true);
                GameEngine.enemy.setMonsterMana(GameEngine.enemy.getMonsterMana() - (5 + GameEngine.enemy.getMonsterHealth() +
                        (int) (GameEngine.player1.getHeroMagicResistance() * 0.2)));
            }
        } else if (monsterType.equals("Hell Monarch")) {
            int blackDragonBurningFlamesDMG = GameEngine.enemy.getMonsterSpellPower() - GameEngine.player1.getHeroMagicResistance();
            if (blackDragonBurningFlamesDMG < 1) {
                blackDragonBurningFlamesDMG = 1;
            }
            if (GameEngine.enemy.getMonsterMana() >= 5) {
                System.out.printf("🕮 Hell Monarch cast BURNING FLAMES dealing %d damage to %s.%n",
                        blackDragonBurningFlamesDMG, GameEngine.playerName);
                Thread.sleep(1000L);
                GameEngine.enemy.setMonsterMana(GameEngine.enemy.getMonsterMana() - (5 + GameEngine.enemy.getMonsterLVL()));
                GameEngine.player1.setHeroCurrentHealth(GameEngine.player1.getHeroCurrentHealth() - blackDragonBurningFlamesDMG);
            }
        } else if (monsterType.equals("Terror")) {
            if (GameEngine.enemy.getMonsterMana() >= 3) {
                int terrorFreezingNovaDMG = GameEngine.enemy.getMonsterSpellPower() - GameEngine.player1.getHeroMagicResistance();
                if (terrorFreezingNovaDMG < 1) {
                    terrorFreezingNovaDMG = 1;
                }
                System.out.printf("🕮 Terror cast FREEZING NOVA dealing %d damage and reducing %s Attack Speed by 0.1%n",
                        terrorFreezingNovaDMG, GameEngine.playerName);
                Thread.sleep(1000L);
                GameEngine.enemy.setMonsterMana(GameEngine.enemy.getMonsterMana() - 3);
                GameEngine.player1.setHeroCurrentHealth(GameEngine.player1.getHeroCurrentHealth() - terrorFreezingNovaDMG);
                GameEngine.player1.setHeroAttackSpeed(GameEngine.player1.getHeroAttackSpeed() + 0.1);
            }
        } else if (monsterType.equals("Demon Lord")) {
            int demonLordHellhoundDMG = 7 + GameEngine.enemy.getMonsterSpellPower() - GameEngine.player1.getHeroMagicResistance();
            if (demonLordHellhoundDMG < 1) {
                demonLordHellhoundDMG = 1;
            }
            if (GameEngine.enemy.getMonsterMana() >= 5) {
                System.out.printf("🕮 Demon Lord Summon HELLHOUND that dealing %d to %s.%n", demonLordHellhoundDMG, GameEngine.playerName);
                Thread.sleep(1000L);
                GameEngine.enemy.setMonsterMana(GameEngine.enemy.getMonsterMana() - 5);
                GameEngine.player1.setHeroCurrentHealth(GameEngine.player1.getHeroCurrentHealth() - demonLordHellhoundDMG);
            }

        } else if (monsterType.equals("Lich King")) {
            int lichKingPoisonDMG = 0;
            int lichKingTotalPoisonDMG = 0;
            if (GameEngine.enemy.getMonsterMana() >= 6) {
                GameEngine.enemy.setMonsterMana(GameEngine.enemy.getMonsterMana() - 6);
                lichKingPoisonDMG = GameEngine.enemy.getMonsterSpellPower() - GameEngine.player1.getHeroMagicResistance();
                if (lichKingPoisonDMG < 1) {
                    lichKingPoisonDMG = 1;
                }
                lichKingTotalPoisonDMG += lichKingPoisonDMG;
                System.out.printf("🕮 Lich King cast %d POISON to %s (total Poison %d).%n", lichKingPoisonDMG,
                        GameEngine.playerName, lichKingTotalPoisonDMG);
                lichKingPoisonActivation = true;
            }
            if (lichKingPoisonActivation == true) {
                GameEngine.player1.setHeroCurrentHealth(GameEngine.player1.getHeroCurrentHealth() - lichKingTotalPoisonDMG);
                System.out.printf("Poison take %d damage.%n", lichKingTotalPoisonDMG);
                Thread.sleep(1000L);
            }

        } else if (monsterType.equals("Hydra")) {
            if (GameEngine.enemy.getMonsterHealth() <= hydraLifeTracker) {
                System.out.println("Another head grows on the HYDRA. Hers damage increase by 15.");
                GameEngine.enemy.setMonsterDMG(15);
                hydraLifeTracker = 125;
                Thread.sleep(1000L);
            } else if (GameEngine.enemy.getMonsterHealth() <= hydraLifeTracker) {
                System.out.println("Another head grows on the HYDRA. Hers damage increase by 15.");
                GameEngine.enemy.setMonsterDMG(15);
                hydraLifeTracker = 0;
                Thread.sleep(1000L);
            }
        } else if (monsterType.equals("Fenix")) {
            if (GameEngine.enemy.getMonsterMana() >= 10) {
                GameEngine.enemy.setMonsterMana(GameEngine.enemy.getMonsterMana() - 10);
                int fenixBreath = (GameEngine.enemy.getMonsterSpellPower() * 2) - GameEngine.player1.getHeroMagicResistance();
                if (fenixBreath < 1) {
                    fenixBreath = 1;
                }
                System.out.printf("🕮 Fenix cast FENIX BREATH dealing %d DMG to %s%n", fenixBreath, GameEngine.playerName);
                GameEngine.player1.setHeroCurrentHealth(GameEngine.player1.getHeroCurrentHealth() - fenixBreath);
                Thread.sleep(1000L);
            }
        } else if (monsterType.equals("Titan")) {
            if (GameEngine.enemy.getMonsterMana() >= 10) {
                GameEngine.enemy.setMonsterMana(GameEngine.enemy.getMonsterMana() - 10);
                int lightningBoltDMG = (GameEngine.enemy.getMonsterSpellPower() - GameEngine.player1.getHeroMagicResistance());
                if (lightningBoltDMG < 1) {
                    lightningBoltDMG = 1;
                }
                System.out.printf("🕮 The greatest Titan cast LIGHTNING BOLT dealing %d DMG to %s%n", lightningBoltDMG, GameEngine.playerName);
                GameEngine.player1.setHeroCurrentHealth(GameEngine.player1.getHeroCurrentHealth() - lightningBoltDMG);
                Thread.sleep(1000L);
            }
        } else if (monsterType.equals("Black Dragon")) {
            boolean blooded = false;
            if (GameEngine.enemy.getMonsterMana() >= 10) {
                GameEngine.enemy.setMonsterMana(GameEngine.enemy.getMonsterMana() - 10);
                System.out.printf("🕮 The Black Dragon cast Dragon Fury increasing his attack speed by 0.1); ");
                Thread.sleep(1000L);
                GameEngine.enemy.setMonsterAttackSpeed(GameEngine.enemy.getMonsterAttackSpeed() - 0.1);
                if (GameEngine.enemy.getMonsterAttackSpeed() < 0.8) {
                    GameEngine.enemy.setMonsterAttackSpeed(0.8);
                }
            }
            if (GameEngine.enemy.getMonsterHealth() < 100 && blooded == false) {
                blooded = true;
                double jetOfFire = GameEngine.enemy.getMonsterSpellPower() * 2 - GameEngine.player1.getHeroMagicResistance();
                if (jetOfFire < 1) {
                    jetOfFire = 1;
                }
                System.out.printf("🕮 The Black Dragon cast Jet of Fire dealing %d DMG to %s%n", jetOfFire, GameEngine.playerName);
                GameEngine.player1.setHeroCurrentHealth(GameEngine.player1.getHeroCurrentHealth() - jetOfFire);
                Thread.sleep(1000L);
            }
        } else if (monsterType.equals("Behemoth")) {
            int behemothRagesQuantity = 0;
            if (GameEngine.enemy.getMonsterMana() >= 10 && behemothRagesQuantity < 4) {
                behemothRagesQuantity++;
                GameEngine.enemy.setMonsterMana(GameEngine.enemy.getMonsterMana() - 10);
                System.out.println("🕮 Behemoth gain Rage increasing his Armor and DMG by 5");
                Thread.sleep(1000L);
                GameEngine.enemy.setMonsterDMG(5);
                GameEngine.enemy.setMonsterArmor(5);
            }
        }

    }

    public boolean setMonsterStunStatus(boolean isStunned) {
        this.monsterStunStatus = isStunned;
        return monsterStunStatus;
    }

}