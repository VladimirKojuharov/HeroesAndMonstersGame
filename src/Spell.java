import java.util.Random;

public class Spell {

    private String spellName;
    private int spellManaCost;
    private int spellDMG;
    public String spellCastingMessage;
    private String spellDescriptionRow1;
    private String spellDescriptionRow2;
    private String spellDescriptionRow3;
    private String spellDescriptionRow4;
    private String spellDescriptionRow5;
    private String spellDescriptionRow6;
    private String spellDescriptionRow7;
    private String spellDescriptionRow8;
    private String spellDescriptionRow9;
    private Random rand = new Random();

    public Spell(String spellName) {
        this.spellName = spellName;
        this.setSpellStats();
    }

    public String getSpellName() {
        return this.spellName;
    }

    public int getSpellManaCost() {
        return this.spellManaCost;
    }

    public int getSpellDMG() {
        return this.spellDMG;
    }

    public void getSpellCastingMessage() {
        System.out.println(this.spellCastingMessage);
    }

    public String getSpellDescriptionRow1() {
        return spellDescriptionRow1;
    }

    public String getSpellDescriptionRow2() {
        return spellDescriptionRow2;
    }

    public String getSpellDescriptionRow3() {
        return spellDescriptionRow3;
    }

    public String getSpellDescriptionRow4() {
        return spellDescriptionRow4;
    }

    public String getSpellDescriptionRow5() {
        return spellDescriptionRow5;
    }

    public String getSpellDescriptionRow6() {
        return spellDescriptionRow6;
    }

    public String getSpellDescriptionRow7() {
        return spellDescriptionRow7;
    }

    public String getSpellDescriptionRow8() {
        return spellDescriptionRow8;
    }

    public String getSpellDescriptionRow9() {
        return spellDescriptionRow9;
    }


    public void makeSpellEffect(String spellName) {

        if (spellName.equals("HEAL")) {
            GameEngine.player1.setHeroCurrentHealth(GameEngine.player1.getHeroCurrentHealth() + this.spellDMG);
            if (GameEngine.player1.getHeroCurrentHealth() > GameEngine.player1.getHeroMaxHealth()) {
                GameEngine.player1.setHeroCurrentHealth(GameEngine.player1.getHeroMaxHealth());
            }
        } else if (spellName.equals("FIREBALL")) {
            GameEngine.enemy.setMonsterHealth(GameEngine.enemy.getMonsterHealth() - this.spellDMG);
            if (GameEngine.enemy.getMonsterHealth() < 1) {
                GameEngine.enemy.setMonsterHealth(0);
            }
        } else if (spellName.equals("STONE SKIN")) {
            GameEngine.player1.currentHeroArmor += 2 + GameEngine.player1.getHeroSpellPower() / 5;
        } else if (spellName.equals("MANA BURN")) {
            GameEngine.enemy.setMonsterMana(GameEngine.enemy.getMonsterMana() - (2 + (GameEngine.player1.getHeroLVL() / 2) +
                    ((GameEngine.player1.getHeroSpellPower() - GameEngine.enemy.getMonsterMagicResistance()) / 5)));
            if (GameEngine.enemy.getMonsterMana() < 0) {
                GameEngine.enemy.setMonsterMana(0);
            }
            GameEngine.enemy.setMonsterHealth(GameEngine.enemy.getMonsterHealth() - this.spellDMG);

        } else if (spellName.equals("BLIND")) {
            int blindStunChance = rand.nextInt(100) + 1;
            if (blindStunChance > GameEngine.enemy.getMonsterMagicResistance()) {
                GameEngine.enemy.setMonsterStunStatus(true);
            }
        } else if (spellName.equals("STUN STRIKE")) {
            GameEngine.enemy.setMonsterHealth(GameEngine.enemy.getMonsterHealth() - this.spellDMG);
            if (GameEngine.enemy.getMonsterHealth() < 1) {
                GameEngine.enemy.setMonsterHealth(0);
            }
            int stunSuccess = rand.nextInt(100) + 1;
            if (stunSuccess >= 20 + GameEngine.enemy.getMonsterMagicResistance()) {
                GameEngine.enemy.setMonsterStunStatus(true);
                System.out.printf("%s stun the %s.%n", GameEngine.playerName, GameEngine.enemy.getMonsterName().trim());
            } else {
                System.out.printf("%s fail to stun the %s.%n", GameEngine.playerName, GameEngine.enemy.getMonsterName().trim());
            }

        } else if (spellName.equals("BLIZZARD")) {
            Fight.enemySpeed += GameEngine.enemy.getMonsterAttackSpeed() * 0.1;
            GameEngine.enemy.setMonsterAttackSpeed(1.1 * GameEngine.enemy.getMonsterAttackSpeed());
            if (GameEngine.enemy.getMonsterAttackSpeed() > 3) {
                GameEngine.enemy.setMonsterAttackSpeed(3);
            }
            GameEngine.enemy.setMonsterHealth(GameEngine.enemy.getMonsterHealth() - this.spellDMG);
            if (GameEngine.enemy.getMonsterHealth() < 1) {
                GameEngine.enemy.setMonsterHealth(0);
            }

        } else if (spellName.equals("SLOW")) {
            Fight.enemySpeed += GameEngine.enemy.getMonsterAttackSpeed() * 0.2;
            GameEngine.enemy.setMonsterAttackSpeed(GameEngine.enemy.getMonsterAttackSpeed() * 1.2);
            if (GameEngine.enemy.getMonsterAttackSpeed() > 3) {
                GameEngine.enemy.setMonsterAttackSpeed(3);
            }
        } else if (spellName.equals("HASTE")) {
            Fight.heroSpeed -= GameEngine.player1.getHeroAttackSpeed() * 0.2;
            GameEngine.player1.setHeroAttackSpeed(GameEngine.player1.getHeroAttackSpeed() * 0.8);
            if (GameEngine.player1.getHeroAttackSpeed() < 0.8) {
                GameEngine.player1.setHeroAttackSpeed(0.8);
            }
        } else if (spellName.equals("LIFE DRAIN")) {
            GameEngine.enemy.setMonsterHealth(GameEngine.enemy.getMonsterHealth() - this.spellDMG);
            GameEngine.player1.setHeroCurrentHealth(GameEngine.player1.getHeroCurrentHealth() + this.spellDMG);
            if (GameEngine.player1.getHeroCurrentHealth() > GameEngine.player1.getHeroMaxHealth()) {
                GameEngine.player1.setHeroCurrentHealth(GameEngine.player1.getHeroMaxHealth());
                if (GameEngine.enemy.getMonsterHealth() < 1) {
                    GameEngine.enemy.setMonsterHealth(0);
                }
            }
        }


    }

    public void setSpellManaCost() {
        if (this.spellName.equals("HEAL")) {
            this.spellManaCost = 2 + GameEngine.player1.getHeroLVL();

            if (GameEngine.playerGameProgress % 5 == 0) {
                this.spellManaCost += 1;
            }
        } else if (this.spellName.equals("FIREBALL")) {
            this.spellManaCost = 3 + GameEngine.player1.getHeroLVL();
            if (GameEngine.playerGameProgress % 5 == 0) {
                this.spellManaCost += 1;
            }
        } else if (this.spellName.equals("STONE SKIN")) {
            this.spellManaCost = 2 + (int) (GameEngine.player1.getHeroLVL() * 0.75);
            if (GameEngine.playerGameProgress % 5 == 0) {
                this.spellManaCost += 1;
            }
        } else if (this.spellName.equals("MANA BURN")) {
            this.spellManaCost = 2 + GameEngine.player1.getHeroLVL();
            if (GameEngine.playerGameProgress % 5 == 0 && GameEngine.playerGameProgress != 0) {
                this.spellManaCost += 1;
            }
        } else if (this.spellName.equals("BLIND")) {
            this.spellManaCost = GameEngine.enemy.getMonsterLVL() + 4;
            if (GameEngine.playerGameProgress % 5 == 0) {
                this.spellManaCost += 1;
            }
        } else if (this.spellName.equals("BLIZZARD")) {
            this.spellManaCost = 3 + GameEngine.player1.getHeroLVL();
            if (GameEngine.playerGameProgress % 5 == 0) {
                this.spellManaCost += 1;
            }
        } else if (this.spellName.equals("SLOW")) {
            this.spellManaCost = 3 + (int) (GameEngine.player1.getHeroLVL() * 0.5);
            if (GameEngine.playerGameProgress % 5 == 0) {
                this.spellManaCost += 1;
            }
        } else if (this.spellName.equals("STUN STRIKE")) {
            this.spellManaCost = GameEngine.player1.getHeroLVL() + 4;
            if (GameEngine.playerGameProgress % 5 == 0) {
                this.spellManaCost += 1;
            }
        } else if (this.spellName.equals("HASTE")) {
            this.spellManaCost = 3 + (int) (GameEngine.player1.getHeroLVL() * 0.5);
            if (GameEngine.playerGameProgress % 5 == 0) {
                this.spellManaCost += 1;
            }
        } else if (this.spellName.equals("LIFE DRAIN")) {
            this.spellManaCost = 3 + GameEngine.player1.getHeroLVL();
            if (GameEngine.playerGameProgress % 5 == 0) {
                this.spellManaCost += 1;
            }
        }
    }

    public void setSpellDMG() {
        if (this.spellName.equals("HEAL")) {
            this.spellDMG = 1 + (GameEngine.player1.getHeroLVL() * 2) + GameEngine.player1.getHeroSpellPower() -
                    GameEngine.enemy.getMonsterMagicResistance();
            if (this.spellDMG < 1) {
                this.spellDMG = 1;
            }
        } else if (this.spellName.equals("FIREBALL")) {
            this.spellDMG = 2 + (GameEngine.player1.getHeroLVL() * 3) + GameEngine.player1.getHeroSpellPower() -
                    GameEngine.enemy.getMonsterMagicResistance();
            if (this.spellDMG < 1) {
                this.spellDMG = 1;
            }
        } else if (this.spellName.equals("STONE SKIN")) {
            this.spellDMG = 2 + (int) (GameEngine.player1.getHeroSpellPower() * 0.15);
        } else if (this.spellName.equals("MANA BURN")) {
            this.spellDMG = ((int) (GameEngine.player1.getHeroLVL()) + GameEngine.player1.getHeroSpellPower() -
                    GameEngine.enemy.getMonsterMagicResistance());
            if (this.spellDMG < 2) {
                this.spellDMG = 2;
            }
        } else if (this.spellName.equals("BLIND")) {
            this.spellDMG = 0;
        } else if (this.spellName.equals("BLIZZARD")) {
            this.spellDMG = 2 + (GameEngine.player1.getHeroLVL()) + (GameEngine.player1.getHeroSpellPower() -
                    GameEngine.enemy.getMonsterMagicResistance());
            if (this.spellDMG < 1) {
                this.spellDMG = 1;
            }
        } else if (this.spellName.equals("SLOW")) {
            this.spellDMG = 0;
        } else if (this.spellName.equals("STUN STRIKE")) {
            this.spellDMG = GameEngine.player1.getHeroLVL() + GameEngine.player1.getHeroSpellPower() -
                    GameEngine.enemy.getMonsterMagicResistance();
            if (this.spellDMG < 1) {
                this.spellDMG = 1;
            }
        } else if (this.spellName.equals("HASTE")) {
            this.spellDMG = 0;
        } else if (spellName.equals("LIFE DRAIN")) {
            this.spellDMG = GameEngine.player1.getHeroSpellPower() -
                    GameEngine.enemy.getMonsterMagicResistance();
            if (this.spellDMG < 1) {
                this.spellDMG = 1;
            }
        }
    }

    public void setSpellCastingMessage() {
        if (this.spellName.equals("HEAL")) {
            this.spellCastingMessage = String.format("🕮 %s cast HEAL restoring %d of his Health Points.%n",
                    GameEngine.playerName, this.spellDMG);
        } else if (this.spellName.equals("FIREBALL")) {
            this.spellCastingMessage = String.format("🕮 %s cast FIREBALL dealing %d damage to %s.%n",
                    GameEngine.playerName, this.spellDMG, GameEngine.enemy.getMonsterName());
        } else if (this.spellName.equals("STONE SKIN")) {
            this.spellCastingMessage = String.format("🕮 %s cast STONE SKIN increasing his Armor by %d.%n",
                    GameEngine.playerName, this.spellDMG);
        } else if (this.spellName.equals("MANA BURN")) {
            this.spellCastingMessage = String.format("🕮 %s cast MANA BURN to %s decreasing his Mana by %d and dealing " +
                            "him %d damage.%n", GameEngine.playerName, GameEngine.enemy.getMonsterName(),
                    (2 + (GameEngine.player1.getHeroLVL() + GameEngine.player1.getHeroSpellPower()) / 5), this.spellDMG);
        } else if (this.spellName.equals("BLIND")) {
            this.spellCastingMessage = String.format("🕮 %s cast BLIND to %s. %n", GameEngine.playerName,
                    GameEngine.enemy.getMonsterName());
            if (GameEngine.enemy.getMonsterStunStatus() == true) {
                System.out.println("The monster is SUCCESSFULLY blinded.");
            } else {
                System.out.println("The monster magic resistance BLOCK the BLIND!");
            }
        } else if (this.spellName.equals("BLIZZARD")) {
            this.spellCastingMessage = String.format("🕮 %s cast BLIZZARD dealing %d damage and slow %s attack speed " +
                    "by 10%%. %n", GameEngine.playerName, this.spellDMG, GameEngine.enemy.getMonsterName());
        } else if (this.spellName.equals("SLOW")) {
            this.spellCastingMessage = String.format("🕮 %s cast SLOW to %s reduce his attack speed by %.2f. %n",
                    GameEngine.playerName, GameEngine.enemy.getMonsterName(), GameEngine.enemy.getMonsterAttackSpeed() * 0.2);
        } else if (this.spellName.equals("HASTE")) {
            this.spellCastingMessage = String.format("🕮 %s cast HASTE and increases its attack speed by %.2f. %n",
                    GameEngine.playerName, GameEngine.player1.getHeroAttackSpeed() * 0.2);
        } else if (this.spellName.equals("STUN STRIKE")) {
            this.spellCastingMessage = String.format("🕮 STUN STRIKE deal %d damage. %n", this.spellDMG);
        } else if (this.spellName.equals("LIFE DRAIN")) {
            this.spellCastingMessage = String.format("🕮 %s cast LIFE DRAIN draining %d HP from him and regain %d of " +
                    "his Life Points.%n", GameEngine.playerName, this.spellDMG, this.spellDMG);
        }

    }

    public void setSpellStats() {
        String whiteSpaces = "          ";

        if (this.spellName.equals("HEAL")) {
            this.spellManaCost = 2 + GameEngine.player1.getHeroLVL();
            this.setSpellDMG();
            this.spellCastingMessage = String.format("%s cast HEAL restoring %d of his Health Points.%n",
                    GameEngine.playerName, this.spellDMG);
            this.spellDescriptionRow1 = " ___________________ ";
            this.spellDescriptionRow2 = "|        HEAL       |";
            this.spellDescriptionRow3 = "|-------------------|";
            this.spellDescriptionRow4 = "|                   |";
            this.spellDescriptionRow5 = String.format("|  Restore %d Hero" +
                    whiteSpaces.substring(0, 4 - Integer.toString(this.spellDMG).length()) + "|", this.spellDMG);
            this.spellDescriptionRow6 = "|  Health Points.   |";
            this.spellDescriptionRow7 = "|                   |";
            this.spellDescriptionRow8 = "|___________________|";
            this.spellDescriptionRow9 = String.format("    MANA COST: %d" +
                    whiteSpaces.substring(0, 6 - Integer.toString(this.spellManaCost).length()), this.spellManaCost);

        } else if (this.spellName.equals("FIREBALL")) {
            this.spellManaCost = 3 + GameEngine.player1.getHeroLVL();
            this.setSpellDMG();
            this.spellCastingMessage = String.format("%s cast FIREBALL dealing %d damage to %s.%n",
                    GameEngine.playerName, this.spellDMG, GameEngine.enemy.getMonsterName());
            this.spellDescriptionRow1 = " ___________________ ";
            this.spellDescriptionRow2 = "|      FIREBALL     |";
            this.spellDescriptionRow3 = "|-------------------|";
            this.spellDescriptionRow4 = "|                   |";
            this.spellDescriptionRow5 = String.format("|   Deal %d damage" +
                    whiteSpaces.substring(0, 4 - Integer.toString(this.spellDMG).length()) + "|", this.spellDMG);
            this.spellDescriptionRow6 = "| to enemy creature.|";
            this.spellDescriptionRow7 = "|                   |";
            this.spellDescriptionRow8 = "|___________________|";
            this.spellDescriptionRow9 = String.format("    MANA COST: %d" +
                    whiteSpaces.substring(0, 6 - Integer.toString(this.spellManaCost).length()), this.spellManaCost);

        } else if (this.spellName.equals("STONE SKIN")) {
            this.spellManaCost = 2 + GameEngine.player1.getHeroLVL() / 2;
            this.setSpellDMG();
            this.spellCastingMessage = String.format("%s cast STONE SKIN increasing his Armor by %d.%n",
                    GameEngine.playerName, this.spellDMG);
            this.spellDescriptionRow1 = " ___________________ ";
            this.spellDescriptionRow2 = "|     STONE SKIN    |";
            this.spellDescriptionRow3 = "|-------------------|";
            this.spellDescriptionRow4 = "|                   |";
            this.spellDescriptionRow5 = "| Increase Armor by |";
            this.spellDescriptionRow6 = String.format("|" +
                    whiteSpaces.substring(0, 10 - Integer.toString(this.spellDMG).length()) + "%d" +
                    whiteSpaces.substring(0, 10 - Integer.toString(this.spellDMG).length()) + "|", this.spellDMG);
            this.spellDescriptionRow7 = "|                   |";
            this.spellDescriptionRow8 = "|___________________|";
            this.spellDescriptionRow9 = String.format("    MANA COST: %d" +
                    whiteSpaces.substring(0, 6 - Integer.toString(this.spellManaCost).length()), this.spellManaCost);

        } else if (this.spellName.equals("MANA BURN")) {
            spellManaCost = 3 + GameEngine.player1.getHeroLVL();
            this.setSpellDMG();
            this.spellCastingMessage = String.format("%s cast MANA BURN to %s decreasing his Mana by %d and dealing " +
                            "him %d damage.%n", GameEngine.playerName, GameEngine.enemy.getMonsterName(),
                    (2 + (GameEngine.player1.getHeroLVL() + GameEngine.player1.getHeroSpellPower()) / 5), this.spellDMG);
            this.spellDescriptionRow1 = " ___________________ ";
            this.spellDescriptionRow2 = "|     MANA BURN     |";
            this.spellDescriptionRow3 = "|-------------------|";
            this.spellDescriptionRow4 = "|                   |";
            this.spellDescriptionRow5 = "|Decrease enemy Mana|";
            this.spellDescriptionRow6 = String.format("| by %d and deal %d" +
                            whiteSpaces.substring(0, 4 - Integer.toString(this.spellDMG).length()) + "|",
                    (2 + (GameEngine.player1.getHeroLVL() + GameEngine.player1.getHeroSpellPower()) / 5), this.spellDMG);
            this.spellDescriptionRow7 = "|       damage.     |";
            this.spellDescriptionRow8 = "|___________________|";
            this.spellDescriptionRow9 = String.format("    MANA COST: %d" +
                    whiteSpaces.substring(0, 6 - Integer.toString(this.spellManaCost).length()), this.spellManaCost);

        } else if (this.spellName.equals("BLIND")) {
            this.spellManaCost = GameEngine.enemy.getMonsterLVL() + 2;
            this.setSpellDMG();
            this.spellCastingMessage = String.format("%s cast BLIND to %s. %n", GameEngine.playerName,
                    GameEngine.enemy.getMonsterName());
            this.spellDescriptionRow1 = " ___________________ ";
            this.spellDescriptionRow2 = "|        BLIND      |";
            this.spellDescriptionRow3 = "|-------------------|";
            this.spellDescriptionRow4 = "|                   |";
            this.spellDescriptionRow5 = "|  Blind the enemy  |";
            this.spellDescriptionRow6 = "| so that it cannot |";
            this.spellDescriptionRow7 = "|  act in his turn. |";
            this.spellDescriptionRow8 = "|___________________|";
            this.spellDescriptionRow9 = String.format("    MANA COST: %d" +
                    whiteSpaces.substring(0, 6 - Integer.toString(this.spellManaCost).length()), this.spellManaCost);

        } else if (this.spellName.equals("BLIZZARD")) {
            this.spellManaCost = 3 + GameEngine.player1.getHeroLVL();
            this.setSpellDMG();
            this.spellCastingMessage = String.format("%s cast BLIZZARD dealing %d damage and slow %s attack speed by " +
                    "10%%. %n", GameEngine.playerName, this.spellDMG, GameEngine.enemy.getMonsterName());
            this.spellDescriptionRow1 = " ___________________ ";
            this.spellDescriptionRow2 = "|      BLIZZARD     |";
            this.spellDescriptionRow3 = "|-------------------|";
            this.spellDescriptionRow4 = "|                   |";
            this.spellDescriptionRow5 = String.format("|  Deal %d DMG and" +
                    whiteSpaces.substring(0, 4 - Integer.toString(this.spellDMG).length()) + "|", this.spellDMG);
            this.spellDescriptionRow6 = "| slow enemy attack |";
            this.spellDescriptionRow7 = "|   speed by 10%.   |";
            this.spellDescriptionRow8 = "|___________________|";
            this.spellDescriptionRow9 = String.format("    MANA COST: %d" +
                    whiteSpaces.substring(0, 6 - Integer.toString(this.spellManaCost).length()), this.spellManaCost);
        } else if (this.spellName.equals("SLOW")) {
            this.spellManaCost = GameEngine.enemy.getMonsterLVL() + 2;
            this.setSpellDMG();
            this.spellCastingMessage = String.format("%s cast SLOW to %s reduce his attack speed by %.2f. %n",
                    GameEngine.playerName, GameEngine.enemy.getMonsterName(), GameEngine.enemy.getMonsterAttackSpeed() * 0.2);
            this.spellDescriptionRow1 = " ___________________ ";
            this.spellDescriptionRow2 = "|        SLOW       |";
            this.spellDescriptionRow3 = "|-------------------|";
            this.spellDescriptionRow4 = "|                   |";
            this.spellDescriptionRow5 = "|  Decrease enemy   |";
            this.spellDescriptionRow6 = "|  attack speed by  |";
            this.spellDescriptionRow7 = "|        20%        |";
            this.spellDescriptionRow8 = "|___________________|";
            this.spellDescriptionRow9 = String.format("    MANA COST: %d" +
                    whiteSpaces.substring(0, 6 - Integer.toString(this.spellManaCost).length()), this.spellManaCost);
        } else if (this.spellName.equals("HASTE")) {
            this.spellManaCost = GameEngine.enemy.getMonsterLVL() + 2;
            this.setSpellDMG();
            this.spellCastingMessage = String.format("%s cast HASTE and increases its attack speed by %.2f. %n",
                    GameEngine.playerName, GameEngine.player1.getHeroAttackSpeed() * 0.2);
            this.spellDescriptionRow1 = " ___________________ ";
            this.spellDescriptionRow2 = "|       HASTE       |";
            this.spellDescriptionRow3 = "|-------------------|";
            this.spellDescriptionRow4 = "|                   |";
            this.spellDescriptionRow5 = "|   Increases hero  |";
            this.spellDescriptionRow6 = "|  attack speed by  |";
            this.spellDescriptionRow7 = "|        20%        |";
            this.spellDescriptionRow8 = "|___________________|";
            this.spellDescriptionRow9 = String.format("    MANA COST: %d" +
                    whiteSpaces.substring(0, 6 - Integer.toString(this.spellManaCost).length()), this.spellManaCost);
        } else if (this.spellName.equals("STUN STRIKE")) {
            this.spellManaCost = GameEngine.enemy.getMonsterLVL() + 2;
            this.setSpellDMG();
            this.spellCastingMessage = String.format("STUN STRIKE deal %d damage. %n", this.spellDMG);
            this.spellDescriptionRow1 = " ___________________ ";
            this.spellDescriptionRow2 = "|    STUN STRIKE    |";
            this.spellDescriptionRow3 = "|-------------------|";
            this.spellDescriptionRow4 = "|                   |";
            this.spellDescriptionRow5 = String.format("|  Deal %d DMG and" +
                    whiteSpaces.substring(0, 4 - Integer.toString(this.spellDMG).length()) + "|", this.spellDMG);
            this.spellDescriptionRow6 = "|   stun the enemy  |";
            this.spellDescriptionRow7 = "| (80% base chance) |";
            this.spellDescriptionRow8 = "|___________________|";
            this.spellDescriptionRow9 = String.format("    MANA COST: %d" +
                    whiteSpaces.substring(0, 6 - Integer.toString(this.spellManaCost).length()), this.spellManaCost);
        } else if (this.spellName.equals("LIFE DRAIN")) {
            this.spellManaCost = GameEngine.enemy.getMonsterLVL() + 2;
            this.setSpellDMG();
            this.spellCastingMessage = String.format("%s cast LIFE DRAIN draining %d HP from him and regain %d of his " +
                    "Life Points.%n", GameEngine.playerName, this.spellDMG, this.spellDMG);
            this.spellDescriptionRow1 = " ___________________ ";
            this.spellDescriptionRow2 = "|     LIFE DRAIN    |";
            this.spellDescriptionRow3 = "|-------------------|";
            this.spellDescriptionRow4 = "|                   |";
            this.spellDescriptionRow5 = String.format("|  Drain %d Health " +
                    whiteSpaces.substring(0, 3 - Integer.toString(this.spellDMG).length()) + "|", this.spellDMG);
            this.spellDescriptionRow6 = "| Points from enemy |";
            this.spellDescriptionRow7 = "|to regaining own HP|";
            this.spellDescriptionRow8 = "|___________________|";
            this.spellDescriptionRow9 = String.format("    MANA COST: %d" +
                    whiteSpaces.substring(0, 6 - Integer.toString(this.spellManaCost).length()), this.spellManaCost);
        }
    }

}