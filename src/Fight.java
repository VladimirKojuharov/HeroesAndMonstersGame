import java.util.Random;
import java.util.Scanner;

public class Fight<privte> {
    private static int turnCounter = 0;
    private final static int AFTER_BATTLE_PRIZE_QUANTITY = 4;
    private static Item[] prizeItem = new Item[AFTER_BATTLE_PRIZE_QUANTITY + 1];

    private static String[] itemSets = {"Death's", "King's", "Rush's", "Great", "Devil's", "Justice's", "Dragon's",
            "Hell's", "Crusher's", "Bone's", "Ghost"};
    private static String[] armorTypes = {"Plate", "Armor", "Vestry", "Ring Mail", "Full Plate"};

    public static String chosenSpellForCasting;
    private static String whitеSpaces = "                                            ";
    private static int heroAttackDMG;
    private static int monsterAttackDMG;
    public static double heroSpeed;
    public static double enemySpeed;
    private static int currentWeaponDMG;
    private static Random rand = new Random();
    private static double criticalDmgCoeficient;
    private static int critStrike;
    private static int stunStrike;
    private static int evasionCheck;

    public static void Battle() throws InterruptedException {
        heroSpeed = GameEngine.player1.getHeroBaseAttackSpeed() + GameEngine.player1.getHeroWeaponAttackSpeed();
        enemySpeed = GameEngine.enemy.getMonsterAttackSpeed();
        Random rand = new Random();

        setActualSpellListForCurrentBattle();

        while (GameEngine.player1.getHeroCurrentHealth() >= 1 && GameEngine.enemy.getMonsterHealth() >= 1) {

            turnCounter++;
            combatTurnOverview();
            if (heroSpeed <= enemySpeed) {
                if (checkHeroStunSatus() == true) {
                } else {
                    openHeroSpellBookIfHasManaForMagic();
                    checkHeroForCurs();
                    CheckForHeroCriticalAttackForCurrentTurn();
                    calculateHeroAttackDMG();
                    duringBattleHeroHealthRegain();
                    monsterEvasionCheck();
                    setHeroManaForTheEndOfCurrentTurn();

                    if (GameEngine.enemy.getMonsterHealth() < 1) {
                        heroWinConsequence();
                        if (GameEngine.playerGameProgress < 40) {
                            getItemPrize();
                        }
                    } else {
                        heroSpeed += GameEngine.player1.getHeroAttackSpeed();
                    }
                }
            } else {
                if (checkMonsterStunSatus() == true) {
                } else {
                    Monster.activateMonsterSpecialSkill(GameEngine.enemy.getMonsterName());
                    checkForMonsterCriticalStrike();
                    calculateMonsterAttackDMG();
                    heroEvasionCheck();
                    monsterHealthRegain();
                    monsterManaRegain();

                    if (GameEngine.player1.getHeroCurrentHealth() < 1) {
                        monsterWinConsuquence();
                    } else {
                        enemySpeed += GameEngine.enemy.getMonsterAttackSpeed();
                    }
                }
            }
        }
        turnCounter = 0;
    }

    private static void setActualSpellListForCurrentBattle() {
        for (int i = 0; i < GameEngine.player1.getHeroSpellList().size(); i++) {
            GameEngine.player1.getHeroSpellList().get(i).setSpellStats();
            GameEngine.player1.getHeroSpellList().get(i).setSpellManaCost();
            GameEngine.player1.getHeroSpellList().get(i).setSpellDMG();
        }
    }

    private static boolean checkHeroStunSatus() throws InterruptedException {
        if (GameEngine.player1.getHeroStunStatus() == true) {
            GameEngine.player1.setHeroStunStatus(false);
            heroSpeed += GameEngine.player1.getHeroAttackSpeed();
            System.out.println("Hero is back from stun!");
            Thread.sleep(2000L);
            return true;
        } else {
            return false;
        }
    }

    private static boolean checkMonsterStunSatus() throws InterruptedException {
        if (GameEngine.enemy.getMonsterStunStatus() == true) {
            GameEngine.enemy.setMonsterStunStatus(false);
            enemySpeed += GameEngine.enemy.getMonsterAttackSpeed();
            System.out.printf(whitеSpaces.substring(0, 28) + "%s is back from the STUN!%n", GameEngine.enemy.getMonsterName().trim());
            Thread.sleep(2000L);
            return true;
        } else {
            return false;
        }
    }

    private static void openHeroSpellBookIfHasManaForMagic() throws InterruptedException {
        int cheepestHeroMagic = Integer.MAX_VALUE;
        for (int i = 0; i < GameEngine.player1.getHeroSpellList().size(); i++) {
            if (cheepestHeroMagic > GameEngine.player1.getHeroSpellList().get(i).getSpellManaCost()) {
                cheepestHeroMagic = GameEngine.player1.getHeroSpellList().get(i).getSpellManaCost();
            }
        }
        if (cheepestHeroMagic <= (int) GameEngine.player1.getHeroCurrentMana()) {
            printHeroSpellBook();
            heroCastSpell();
        }
    }

    private static void checkHeroForCurs() {
        if (Monster.CursingCast == false) {
            currentWeaponDMG = (GameEngine.player1.getHeroWeaponMinDMG() + rand.nextInt(0 +
                    GameEngine.player1.getHeroWeaponMaxDMG() - GameEngine.player1.getHeroWeaponMinDMG()));
        } else {
            currentWeaponDMG = GameEngine.player1.getHeroWeaponMinDMG();
        }
    }

    private static void CheckForHeroCriticalAttackForCurrentTurn() throws InterruptedException {
        critStrike = rand.nextInt(100) + 1;
        if (critStrike <= GameEngine.player1.getHeroCritChance()) {
            criticalDmgCoeficient = 2;
            System.out.printf("%s make CRITICAL strike %d DMG%n", GameEngine.playerName, GameEngine.player1.getHeroBaseDamage() +
                    (int) (criticalDmgCoeficient * currentWeaponDMG) - GameEngine.enemy.getMonsterArmor());
            Thread.sleep(2000L);
        } else {
            criticalDmgCoeficient = 1;
            System.out.printf("%s make %d DMG%n", GameEngine.playerName, GameEngine.player1.getHeroBaseDamage() +
                    currentWeaponDMG - GameEngine.enemy.getMonsterArmor());
            Thread.sleep(2000L);
        }
    }

    private static void checkForHeroStunStrike() throws InterruptedException {
        stunStrike = rand.nextInt(100) + 1;
        if (stunStrike <= GameEngine.player1.getHeroStunChance()) {
            GameEngine.enemy.setMonsterStunStatus(true);
            System.out.printf("%s STUND the %s%n", GameEngine.playerName, GameEngine.enemy.getMonsterName().trim());
            Thread.sleep(2000L);
        }
    }

    public static void printHeroSpellBook() {
        Scanner scanner = new Scanner(System.in);
        chosenSpellForCasting = " ";
        while (!chosenSpellForCasting.equals("1") && !chosenSpellForCasting.equals("2") &&
                !chosenSpellForCasting.equals("3") && !chosenSpellForCasting.equals("4") &&
                !chosenSpellForCasting.equals("5") && !chosenSpellForCasting.equals("6") &&
                !chosenSpellForCasting.equals("7") && !chosenSpellForCasting.equals("8") &&
                !chosenSpellForCasting.equals("9") && !chosenSpellForCasting.equals("10") &&
                !chosenSpellForCasting.equals("11") && !chosenSpellForCasting.equals("12") &&
                !chosenSpellForCasting.equals("0")) {

            for (int i = 0; i < GameEngine.player1.getHeroSpellList().size(); i++) {
                if (i < 6) {
                    System.out.printf(GameEngine.player1.getHeroSpellList().get(i).getSpellDescriptionRow1() + whitеSpaces.substring(0, 8));
                }
            }
            System.out.println();
            for (int i = 0; i < GameEngine.player1.getHeroSpellList().size(); i++) {
                if (i < 6) {
                    System.out.printf(GameEngine.player1.getHeroSpellList().get(i).getSpellDescriptionRow2() + whitеSpaces.substring(0, 8));
                }
            }
            System.out.println();
            for (int i = 0; i < GameEngine.player1.getHeroSpellList().size(); i++) {
                if (i < 6) {
                    System.out.printf(GameEngine.player1.getHeroSpellList().get(i).getSpellDescriptionRow3() + whitеSpaces.substring(0, 8));
                }
            }
            System.out.println();
            for (int i = 0; i < GameEngine.player1.getHeroSpellList().size(); i++) {
                if (i < 6) {
                    System.out.printf(GameEngine.player1.getHeroSpellList().get(i).getSpellDescriptionRow4() + whitеSpaces.substring(0, 8));
                }
            }
            System.out.println();
            for (int i = 0; i < GameEngine.player1.getHeroSpellList().size(); i++) {
                if (i < 6) {
                    System.out.printf(GameEngine.player1.getHeroSpellList().get(i).getSpellDescriptionRow5() + whitеSpaces.substring(0, 8));
                }
            }
            System.out.println();
            for (int i = 0; i < GameEngine.player1.getHeroSpellList().size(); i++) {
                if (i < 6) {
                    System.out.printf(GameEngine.player1.getHeroSpellList().get(i).getSpellDescriptionRow6() + whitеSpaces.substring(0, 8));
                }
            }
            System.out.println();
            for (int i = 0; i < GameEngine.player1.getHeroSpellList().size(); i++) {
                if (i < 6) {
                    System.out.print(GameEngine.player1.getHeroSpellList().get(i).getSpellDescriptionRow7() + whitеSpaces.substring(0, 8));
                }
            }
            System.out.println();
            for (int i = 0; i < GameEngine.player1.getHeroSpellList().size(); i++) {
                if (i < 6) {
                    System.out.printf(GameEngine.player1.getHeroSpellList().get(i).getSpellDescriptionRow8() + whitеSpaces.substring(0, 8));
                }
            }
            System.out.println();
            for (int i = 0; i < GameEngine.player1.getHeroSpellList().size(); i++) {
                if (i < 6) {
                    System.out.printf("    MANA COST: %d" +
                            whitеSpaces.substring(0, 6 - Integer.toString(GameEngine.player1.getHeroSpellList().get(i).getSpellManaCost()).length()) +
                            whitеSpaces.substring(0, 8), GameEngine.player1.getHeroSpellList().get(i).getSpellManaCost());
                }
            }
            System.out.println();
            System.out.println();

            if (GameEngine.player1.getHeroSpellList().size() > 6) {
                for (int i = 6; i < GameEngine.player1.getHeroSpellList().size(); i++) {
                    System.out.printf(GameEngine.player1.getHeroSpellList().get(i).getSpellDescriptionRow1() + whitеSpaces.substring(0, 8));
                }
                System.out.println();
                for (int i = 6; i < GameEngine.player1.getHeroSpellList().size(); i++) {
                    System.out.printf(GameEngine.player1.getHeroSpellList().get(i).getSpellDescriptionRow2() + whitеSpaces.substring(0, 8));
                }
                System.out.println();
                for (int i = 6; i < GameEngine.player1.getHeroSpellList().size(); i++) {
                    System.out.printf(GameEngine.player1.getHeroSpellList().get(i).getSpellDescriptionRow3() + whitеSpaces.substring(0, 8));
                }
                System.out.println();
                for (int i = 6; i < GameEngine.player1.getHeroSpellList().size(); i++) {
                    System.out.printf(GameEngine.player1.getHeroSpellList().get(i).getSpellDescriptionRow4() + whitеSpaces.substring(0, 8));
                }
                System.out.println();
                for (int i = 6; i < GameEngine.player1.getHeroSpellList().size(); i++) {
                    System.out.printf(GameEngine.player1.getHeroSpellList().get(i).getSpellDescriptionRow5() + whitеSpaces.substring(0, 8));
                }
                System.out.println();
                for (int i = 6; i < GameEngine.player1.getHeroSpellList().size(); i++) {
                    System.out.printf(GameEngine.player1.getHeroSpellList().get(i).getSpellDescriptionRow6() + whitеSpaces.substring(0, 8));
                }
                System.out.println();
                for (int i = 6; i < GameEngine.player1.getHeroSpellList().size(); i++) {
                    System.out.print(GameEngine.player1.getHeroSpellList().get(i).getSpellDescriptionRow7() + whitеSpaces.substring(0, 8));
                }
                System.out.println();
                for (int i = 6; i < GameEngine.player1.getHeroSpellList().size(); i++) {
                    System.out.printf(GameEngine.player1.getHeroSpellList().get(i).getSpellDescriptionRow8() + whitеSpaces.substring(0, 8));
                }
                System.out.println();
                for (int i = 6; i < GameEngine.player1.getHeroSpellList().size(); i++) {
                    System.out.printf("    MANA COST: %d" +
                            whitеSpaces.substring(0, 6 - Integer.toString(GameEngine.player1.getHeroSpellList().get(i).getSpellManaCost()).length()) +
                            whitеSpaces.substring(0, 8), GameEngine.player1.getHeroSpellList().get(i).getSpellManaCost());
                }
                System.out.println();
            }

            for (int i = 0; i < GameEngine.player1.getHeroSpellList().size(); i++) {
                if (i < 6) {
                    System.out.printf("(" + (i + 1) + ") " + GameEngine.player1.getHeroSpellList().get(i).getSpellName() + "    ");
                }
            }
            System.out.println();
            if (GameEngine.player1.getHeroSpellList().size() >= 6) {
                for (int i = 6; i < GameEngine.player1.getHeroSpellList().size(); i++) {
                    System.out.printf("(" + (i + 1) + ") " + GameEngine.player1.getHeroSpellList().get(i).getSpellName() + "    ");
                }
            }
            System.out.println();
            System.out.printf("(0) Close Spell Book                HERO MANA: %.0f%n", GameEngine.player1.getHeroCurrentMana());
            chosenSpellForCasting = scanner.nextLine();
        }
    }

    public static void heroCastSpell() throws InterruptedException {
        if (chosenSpellForCasting.equals("1")) {
            if (GameEngine.player1.getHeroCurrentMana() >= GameEngine.player1.getHeroSpellList().get(0).getSpellManaCost()) {
                GameEngine.player1.setHeroCurrentMana(GameEngine.player1.getHeroCurrentMana() -
                        GameEngine.player1.getHeroSpellList().get(0).getSpellManaCost());
                GameEngine.player1.getHeroSpellList().get(0).setSpellCastingMessage();
                GameEngine.player1.getHeroSpellList().get(0).getSpellCastingMessage();
                GameEngine.player1.getHeroSpellList().get(0).makeSpellEffect(GameEngine.player1.getHeroSpellList().get(0).getSpellName());
                GameEngine.player1.getHeroSpellList().remove(0);
            } else {
                System.out.printf("Not enough MANA!%n%n");
                printHeroSpellBook();
                heroCastSpell();
            }
        } else if (chosenSpellForCasting.equals("2")) {
            if (GameEngine.player1.getHeroSpellList().size() < 2) {
                System.out.println("There is no magic (2)!");
                printHeroSpellBook();
                heroCastSpell();
            } else if (GameEngine.player1.getHeroCurrentMana() >= GameEngine.player1.getHeroSpellList().get(1).getSpellManaCost()) {
                GameEngine.player1.setHeroCurrentMana(GameEngine.player1.getHeroCurrentMana() -
                        GameEngine.player1.getHeroSpellList().get(1).getSpellManaCost());
                GameEngine.player1.getHeroSpellList().get(1).setSpellCastingMessage();
                GameEngine.player1.getHeroSpellList().get(1).getSpellCastingMessage();
                GameEngine.player1.getHeroSpellList().get(1).makeSpellEffect(GameEngine.player1.getHeroSpellList().get(1).getSpellName());
                GameEngine.player1.getHeroSpellList().remove(1);
            } else {
                System.out.printf("Not enough MANA!%n%n");
                printHeroSpellBook();
                heroCastSpell();
            }
        } else if (chosenSpellForCasting.equals("3")) {
            if (GameEngine.player1.getHeroSpellList().size() < 3) {
                System.out.println("There is no magic (3)!");
                printHeroSpellBook();
                heroCastSpell();
            } else if (GameEngine.player1.getHeroCurrentMana() >= GameEngine.player1.getHeroSpellList().get(2).getSpellManaCost()) {
                GameEngine.player1.setHeroCurrentMana(GameEngine.player1.getHeroCurrentMana() -
                        GameEngine.player1.getHeroSpellList().get(2).getSpellManaCost());
                GameEngine.player1.getHeroSpellList().get(2).setSpellCastingMessage();
                GameEngine.player1.getHeroSpellList().get(2).getSpellCastingMessage();
                GameEngine.player1.getHeroSpellList().get(2).makeSpellEffect(GameEngine.player1.getHeroSpellList().get(2).getSpellName());
                GameEngine.player1.getHeroSpellList().remove(2);
            } else {
                System.out.printf("Not enough MANA!%n%n");
                printHeroSpellBook();
                heroCastSpell();
            }
        } else if (chosenSpellForCasting.equals("4")) {
            if (GameEngine.player1.getHeroSpellList().size() < 4) {
                System.out.println("There is no magic (4)!");
                printHeroSpellBook();
                heroCastSpell();
            } else if (GameEngine.player1.getHeroCurrentMana() >= GameEngine.player1.getHeroSpellList().get(3).getSpellManaCost()) {
                GameEngine.player1.setHeroCurrentMana(GameEngine.player1.getHeroCurrentMana() -
                        GameEngine.player1.getHeroSpellList().get(3).getSpellManaCost());
                GameEngine.player1.getHeroSpellList().get(3).setSpellCastingMessage();
                GameEngine.player1.getHeroSpellList().get(3).getSpellCastingMessage();
                GameEngine.player1.getHeroSpellList().get(3).makeSpellEffect(GameEngine.player1.getHeroSpellList().get(3).getSpellName());
                GameEngine.player1.getHeroSpellList().remove(3);
            } else {
                System.out.printf("Not enough MANA!%n%n");
                printHeroSpellBook();
                heroCastSpell();
            }
        } else if (chosenSpellForCasting.equals("5")) {
            if (GameEngine.player1.getHeroSpellList().size() < 5) {
                System.out.println("There is no magic (5)!");
                printHeroSpellBook();
                heroCastSpell();
            } else if (GameEngine.player1.getHeroCurrentMana() >= GameEngine.player1.getHeroSpellList().get(4).getSpellManaCost()) {
                GameEngine.player1.setHeroCurrentMana(GameEngine.player1.getHeroCurrentMana() -
                        GameEngine.player1.getHeroSpellList().get(4).getSpellManaCost());
                GameEngine.player1.getHeroSpellList().get(4).setSpellCastingMessage();
                GameEngine.player1.getHeroSpellList().get(4).getSpellCastingMessage();
                GameEngine.player1.getHeroSpellList().get(4).makeSpellEffect(GameEngine.player1.getHeroSpellList().get(4).getSpellName());
                GameEngine.player1.getHeroSpellList().remove(4);
            } else {
                System.out.printf("Not enough MANA!%n%n");
                printHeroSpellBook();
                heroCastSpell();
            }
        } else if (chosenSpellForCasting.equals("6")) {
            if (GameEngine.player1.getHeroSpellList().size() < 6) {
                System.out.println("There is no magic (6)!");
                printHeroSpellBook();
                heroCastSpell();
            } else if (GameEngine.player1.getHeroCurrentMana() >= GameEngine.player1.getHeroSpellList().get(5).getSpellManaCost()) {
                GameEngine.player1.setHeroCurrentMana(GameEngine.player1.getHeroCurrentMana() -
                        GameEngine.player1.getHeroSpellList().get(5).getSpellManaCost());
                GameEngine.player1.getHeroSpellList().get(5).setSpellCastingMessage();
                GameEngine.player1.getHeroSpellList().get(5).getSpellCastingMessage();
                GameEngine.player1.getHeroSpellList().get(5).makeSpellEffect(GameEngine.player1.getHeroSpellList().get(5).getSpellName());
                GameEngine.player1.getHeroSpellList().remove(5);
            } else {
                System.out.printf("Not enough MANA!%n%n");
                printHeroSpellBook();
                heroCastSpell();
            }
        } else if (chosenSpellForCasting.equals("7")) {
            if (GameEngine.player1.getHeroSpellList().size() < 7) {
                System.out.println("There is no magic (7)!");
                printHeroSpellBook();
                heroCastSpell();
            } else if (GameEngine.player1.getHeroCurrentMana() >= GameEngine.player1.getHeroSpellList().get(6).getSpellManaCost()) {
                GameEngine.player1.setHeroCurrentMana(GameEngine.player1.getHeroCurrentMana() -
                        GameEngine.player1.getHeroSpellList().get(0).getSpellManaCost());
                GameEngine.player1.getHeroSpellList().get(6).setSpellCastingMessage();
                GameEngine.player1.getHeroSpellList().get(6).getSpellCastingMessage();
                GameEngine.player1.getHeroSpellList().get(6).makeSpellEffect(GameEngine.player1.getHeroSpellList().get(6).getSpellName());
                GameEngine.player1.getHeroSpellList().remove(6);
            } else {
                System.out.printf("Not enough MANA!%n%n");
                printHeroSpellBook();
                heroCastSpell();
            }
        } else if (chosenSpellForCasting.equals("8")) {
            if (GameEngine.player1.getHeroSpellList().size() < 8) {
                System.out.println("There is no magic (8)!");
                printHeroSpellBook();
                heroCastSpell();
            } else if (GameEngine.player1.getHeroCurrentMana() >= GameEngine.player1.getHeroSpellList().get(7).getSpellManaCost()) {
                GameEngine.player1.setHeroCurrentMana(GameEngine.player1.getHeroCurrentMana() -
                        GameEngine.player1.getHeroSpellList().get(7).getSpellManaCost());
                GameEngine.player1.getHeroSpellList().get(7).setSpellCastingMessage();
                GameEngine.player1.getHeroSpellList().get(7).getSpellCastingMessage();
                GameEngine.player1.getHeroSpellList().get(7).makeSpellEffect(GameEngine.player1.getHeroSpellList().get(7).getSpellName());
                GameEngine.player1.getHeroSpellList().remove(7);
            } else {
                System.out.printf("Not enough MANA!%n%n");
                printHeroSpellBook();
                heroCastSpell();
            }
        } else if (chosenSpellForCasting.equals("9")) {
            if (GameEngine.player1.getHeroSpellList().size() < 9) {
                System.out.println("There is no magic (8)!");
                printHeroSpellBook();
                heroCastSpell();
            } else if (GameEngine.player1.getHeroCurrentMana() >= GameEngine.player1.getHeroSpellList().get(8).getSpellManaCost()) {
                GameEngine.player1.setHeroCurrentMana(GameEngine.player1.getHeroCurrentMana() -
                        GameEngine.player1.getHeroSpellList().get(8).getSpellManaCost());
                GameEngine.player1.getHeroSpellList().get(8).setSpellCastingMessage();
                GameEngine.player1.getHeroSpellList().get(8).getSpellCastingMessage();
                GameEngine.player1.getHeroSpellList().get(8).makeSpellEffect(GameEngine.player1.getHeroSpellList().get(8).getSpellName());
                GameEngine.player1.getHeroSpellList().remove(8);
            } else {
                System.out.printf("Not enough MANA!%n%n");
                printHeroSpellBook();
                heroCastSpell();
            }
        } else if (chosenSpellForCasting.equals("10")) {
            if (GameEngine.player1.getHeroSpellList().size() < 10) {
                System.out.println("There is no magic (10)!");
                printHeroSpellBook();
                heroCastSpell();
            } else if (GameEngine.player1.getHeroCurrentMana() >= GameEngine.player1.getHeroSpellList().get(9).getSpellManaCost()) {
                GameEngine.player1.setHeroCurrentMana(GameEngine.player1.getHeroCurrentMana() -
                        GameEngine.player1.getHeroSpellList().get(9).getSpellManaCost());
                GameEngine.player1.getHeroSpellList().get(9).setSpellCastingMessage();
                GameEngine.player1.getHeroSpellList().get(9).getSpellCastingMessage();
                GameEngine.player1.getHeroSpellList().get(9).makeSpellEffect(GameEngine.player1.getHeroSpellList().get(9).getSpellName());
                GameEngine.player1.getHeroSpellList().remove(9);
            } else {
                System.out.printf("Not enough MANA!%n%n");
                printHeroSpellBook();
                heroCastSpell();
            }
        } else if (chosenSpellForCasting.equals("11")) {
            if (GameEngine.player1.getHeroSpellList().size() < 11) {
                System.out.println("There is no magic (11)!");
                printHeroSpellBook();
                heroCastSpell();
            } else if (GameEngine.player1.getHeroCurrentMana() >= GameEngine.player1.getHeroSpellList().get(10).getSpellManaCost()) {
                GameEngine.player1.setHeroCurrentMana(GameEngine.player1.getHeroCurrentMana() -
                        GameEngine.player1.getHeroSpellList().get(10).getSpellManaCost());
                GameEngine.player1.getHeroSpellList().get(10).setSpellCastingMessage();
                GameEngine.player1.getHeroSpellList().get(10).getSpellCastingMessage();
                GameEngine.player1.getHeroSpellList().get(10).makeSpellEffect(GameEngine.player1.getHeroSpellList().get(10).getSpellName());
                GameEngine.player1.getHeroSpellList().remove(10);
            } else {
                System.out.printf("Not enough MANA!%n%n");
                printHeroSpellBook();
                heroCastSpell();
            }
        } else if (chosenSpellForCasting.equals("12")) {
            if (GameEngine.player1.getHeroSpellList().size() < 12) {
                System.out.println("There is no magic (12)!");
                printHeroSpellBook();
                heroCastSpell();
            } else if (GameEngine.player1.getHeroCurrentMana() >= GameEngine.player1.getHeroSpellList().get(11).getSpellManaCost()) {
                GameEngine.player1.setHeroCurrentMana(GameEngine.player1.getHeroCurrentMana() -
                        GameEngine.player1.getHeroSpellList().get(11).getSpellManaCost());
                GameEngine.player1.getHeroSpellList().get(11).setSpellCastingMessage();
                GameEngine.player1.getHeroSpellList().get(11).getSpellCastingMessage();
                GameEngine.player1.getHeroSpellList().get(11).makeSpellEffect(GameEngine.player1.getHeroSpellList().get(11).getSpellName());
                GameEngine.player1.getHeroSpellList().remove(11);
            } else {
                System.out.printf("Not enough MANA!%n%n");
                printHeroSpellBook();
                heroCastSpell();
            }
        }
        chosenSpellForCasting = " ";
        System.out.println();
        Thread.sleep(2000L);
        if (GameEngine.enemy.getMonsterHealth() < 0) {
            GameEngine.enemy.setMonsterHealth(0);
        }
        combatTurnOverview();
    }

    public static void combatTurnOverview() throws InterruptedException {
        System.out.printf("%n%n%n%n              TURN: " + turnCounter + "%n%.2f:                       %.2f:" + "%n",
                heroSpeed, enemySpeed);
        System.out.printf(GameEngine.getPlayerName() + whitеSpaces.substring(0, 28 - GameEngine.playerName.length()) +
                GameEngine.enemy.getMonsterName().trim() + "%n");
        System.out.printf("HP:" + whitеSpaces.substring(0, 9 - (Integer.toString((int) GameEngine.player1.getHeroCurrentHealth()).length() +
                        1 + Integer.toString((int) GameEngine.player1.getHeroMaxHealth()).length())) +
                        "%.0f/%d                HP:   %d/%d%n", Math.floor(GameEngine.player1.getHeroCurrentHealth()),
                GameEngine.player1.getHeroMaxHealth(), GameEngine.enemy.getMonsterHealth(), GameEngine.enemy.getMonsterBaseHealth());
        System.out.printf("HP.Reg: %.2f                " + "HP.Reg:  " + GameEngine.enemy.getMonsterHealthReg() +
                "%n", GameEngine.player1.getHeroHealthReg());
        System.out.printf("Armor:     " + GameEngine.player1.getHeroCurrentArmor() + "                " + "Armor:    " +
                GameEngine.enemy.getMonsterArmor() + "%n");
        System.out.println("Shield:    " + GameEngine.player1.getHeroShield());
        System.out.printf("Evsn%%:     %d                Evsn%%     %d%n", GameEngine.player1.getHeroEvasionChance(),
                GameEngine.enemy.getMonsterEvasionChance());
        System.out.printf("DMG:" + whitеSpaces.substring(0, 8 - (Integer.toString(GameEngine.player1.getHeroMinDMG()).length() +
                1 + Integer.toString(GameEngine.player1.getHeroMaxDMG()).length())) + GameEngine.player1.getHeroMinDMG() +
                "-" + GameEngine.player1.getHeroMaxDMG() + "                " + "DMG:     " +
                GameEngine.enemy.getMonsterDamage() + "%n");
        System.out.printf("AS:     %.2f                AS:    %.2f%n", GameEngine.player1.getHeroAttackSpeed(),
                GameEngine.enemy.getMonsterAttackSpeed());
        System.out.printf("Crit%%:     %d                Crit%%     %d%n", GameEngine.player1.getHeroCritChance(),
                GameEngine.enemy.getMonsterCritChance());
        System.out.printf("Stun%%:     %d                Stun%%     %d%n", GameEngine.player1.getHeroStunChance(),
                GameEngine.enemy.getMonsterStunChance());
        System.out.printf("S.Pwr:     %d                S.Pwr:    %d%n", GameEngine.player1.getHeroSpellPower(),
                GameEngine.enemy.getMonsterSpellPower());
        System.out.printf("M.Rst:     %d                M.Rst:    %d%n", GameEngine.player1.getHeroMagicResistance(),
                GameEngine.enemy.getMonsterMagicResistance());
        System.out.printf("Mana:      %.0f                Mana:     %.0f%n", Math.floor(GameEngine.player1.getHeroCurrentMana()),
                Math.floor(GameEngine.enemy.getMonsterMana()));
        System.out.printf("M.Reg:   %.1f                M.Reg:  %.1f%n%n", GameEngine.player1.getHeroManaReg(),
                GameEngine.enemy.getMonsterManaReg());
        Thread.sleep(1000);
    }

    private static void duringBattleHeroHealthRegain() {
        GameEngine.player1.setHeroCurrentHealth((GameEngine.player1.getHeroCurrentHealth() +
                (GameEngine.player1.getHeroHealthReg()) * GameEngine.player1.getHeroAttackSpeed()));
        if (GameEngine.player1.getHeroCurrentHealth() > /* maxHeroHealth */ GameEngine.player1.getHeroMaxHealth()) {
            GameEngine.player1.setHeroCurrentHealth(GameEngine.player1.getHeroMaxHealth());
        }
    }

    private static int calculateHeroAttackDMG() {
        heroAttackDMG = GameEngine.player1.getHeroBaseDamage() + (int) (criticalDmgCoeficient * currentWeaponDMG);
        return heroAttackDMG;
    }

    private static void monsterEvasionCheck() throws InterruptedException {
        evasionCheck = rand.nextInt(100) + 1;
        if (evasionCheck > GameEngine.enemy.getMonsterEvasionChance()) {
            GameEngine.enemy.setMonsterHealth(GameEngine.enemy.getMonsterHealth() + GameEngine.enemy.getMonsterArmor() -
                    calculateHeroAttackDMG());
            checkForHeroStunStrike();
        } else {
            System.out.println(whitеSpaces.substring(0, 28) + "The Monster EVADE the Strike!");
            Thread.sleep(2000L);
        }
    }

    private static void heroEvasionCheck() throws InterruptedException {
        evasionCheck = rand.nextInt(100) + 1;
        if (evasionCheck > GameEngine.player1.getHeroEvasionChance()) {
            heroTakeDMG();
            monsterStunStrike();
        } else {
            System.out.println("Hero EVADE the attack!");
            Thread.sleep(2000L);
        }
    }

    private static void monsterStunStrike() throws InterruptedException {
        stunStrike = rand.nextInt(100) + 1;
        if (stunStrike <= GameEngine.enemy.getMonsterStunChance()) {
            GameEngine.player1.setHeroStunStatus(true);
            System.out.printf(whitеSpaces.substring(0, 28) + "%s STUN %s!%n", GameEngine.enemy.getMonsterName().trim(),
                    GameEngine.playerName);
            Thread.sleep(2000L);
        }
    }

    private static void heroTakeDMG() {
        GameEngine.player1.setHeroShield(GameEngine.player1.getHeroShield() - monsterAttackDMG);
        if (GameEngine.player1.getHeroShield() < 0) {
            GameEngine.player1.setHeroCurrentHealth(GameEngine.player1.getHeroCurrentHealth() + GameEngine.player1.getHeroShield());
            GameEngine.player1.setHeroShield(0);
        }
    }

    private static void monsterHealthRegain() {
        GameEngine.enemy.setMonsterHealth(GameEngine.enemy.getMonsterHealth() + GameEngine.enemy.getMonsterHealthReg());
        if (GameEngine.enemy.getMonsterHealth() > GameEngine.enemy.getMonsterBaseHealth()) {
            GameEngine.enemy.setMonsterHealth(GameEngine.enemy.getMonsterBaseHealth());
        }
    }

    private static void monsterManaRegain() {
        GameEngine.enemy.setMonsterMana(GameEngine.enemy.getMonsterMana() + GameEngine.enemy.getMonsterManaReg());
    }

    private static void monsterWinConsuquence() throws InterruptedException {
        GameEngine.player1.setHeroCurrentHealth(0);
        enemySpeed += GameEngine.enemy.getMonsterAttackSpeed();
        System.out.printf("%n%n%n%n       %s break the hero!" + "%n", GameEngine.enemy.getMonsterName());
        Thread.sleep(1500L);
        combatTurnOverview();
        System.out.printf(GameEngine.enemy.getMonsterName().trim() + " win the battle!!!%n%n");
        System.out.printf("                 ### PROGRESS %d/%d ###%n%n", GameEngine.playerGameProgress, GameEngine.ALL_GAME_LEVELS);
        Thread.sleep(2000L);
    }

    private static void setHeroManaForTheEndOfCurrentTurn() {
        GameEngine.player1.setHeroCurrentMana((GameEngine.player1.getHeroCurrentMana() +
                (GameEngine.player1.getHeroManaReg()) * GameEngine.player1.getHeroAttackSpeed()));
    }

    private static void afterBattleHeroStatsRestore() {
        GameEngine.player1.setHeroMaxHealth();
        GameEngine.player1.setHeroHealthReg();
        GameEngine.player1.setCurrentHeroArmor();
        GameEngine.player1.setHeroShield(GameEngine.player1.getHeroBaseShield() + GameEngine.player1.armor.getItemShield());
        GameEngine.player1.setHeroEvasionChance();
        GameEngine.player1.setHeroMinDMG();
        GameEngine.player1.setHeroMaxDMG(GameEngine.player1.getHeroBaseDamage() + GameEngine.player1.weapon.getWeaponMaxDMG());
        GameEngine.player1.setHeroCritChance();
        GameEngine.player1.setHeroAttackSpeed(GameEngine.player1.getHeroBaseAttackSpeed() +
                GameEngine.player1.getHeroWeaponAttackSpeed() + GameEngine.player1.getHeroBootsAttackSpeed());
        GameEngine.player1.setHeroStunChance();
        GameEngine.player1.setHeroSpellPower();
        GameEngine.player1.setHeroMagicResistance();
        GameEngine.player1.setHeroManaReg();
        GameEngine.player1.setHeroCurrentMana(GameEngine.player1.getHeroBaseMana());
    }

    private static void afterBattleHeroSpellBookRestore() {
        GameEngine.player1.heroSpellList.clear();
        for (int i = 0; i < GameEngine.player1.heroSpellListCopy.size(); i++) {
            GameEngine.player1.heroSpellList.add(GameEngine.player1.heroSpellListCopy.get(i));
        }
    }

    private static void afterBattleMonsterSkillRestore() {
        Monster.CursingCast = false;
        Monster.skeletonArmorIncreasment = false;
        Monster.hydraLifeTracker = 800;
        Monster.lichKingPoisonActivation = false;
        Monster.lichKingTotalPoisonDMG = 0;
    }

    private static void generateItemPrize() {
        Random rand = new Random();
        String[] itemModels = {"WEAPON", "ARMOR", "HELM", "BOOTS"};

        for (int i = 1; i < AFTER_BATTLE_PRIZE_QUANTITY + 1; i++) {
            int generateItemRandomModel = rand.nextInt(itemModels.length);
            String itemModel = itemModels[generateItemRandomModel];

            if (itemModel.equals("WEAPON")) {
                prizeItem[i] = new Weapon();
                prizeItem[i].setItemModel("WEAPON");
            } else if (itemModel.equals("ARMOR")) {
                prizeItem[i] = new Armor();
                prizeItem[i].setItemModel("ARMOR");
            } else if (itemModel.equals("HELM")) {
                prizeItem[i] = new Helm();
                prizeItem[i].setItemModel("HELM");
            } else if (itemModel.equals("BOOTS")) {
                prizeItem[i] = new Boots();
                prizeItem[i].setItemModel("BOOTS");
            }
        }
    }

    private static void printRandomItemPrizes() {
        System.out.println("Prize I:              Prize II:             Prize III:            Prize IV: " +
                "                      HERO WEAPON:          HERO ARMOR:          HERO HELM:          HERO BOOTS:");
        System.out.printf("%s" + whitеSpaces.substring(prizeItem[1].getItemName().length(), 22) + "%s" +
                        whitеSpaces.substring(prizeItem[2].getItemName().length(), 22) + "%s" +
                        whitеSpaces.substring(prizeItem[3].getItemName().length(), 22) + "%s" +
                        whitеSpaces.substring(prizeItem[4].getItemName().length(), 22) + "          %s" +
                        whitеSpaces.substring(GameEngine.player1.getHeroWeaponName().length(), 22) +
                        "%s" + whitеSpaces.substring(GameEngine.player1.getHeroArmorName().length(), 21) +
                        "%s" + whitеSpaces.substring(GameEngine.player1.getHeroHelmName().length(), 20) + "%s%n",
                prizeItem[1].getItemName(), prizeItem[2].getItemName(), prizeItem[3].getItemName(), prizeItem[4].getItemName(),
                GameEngine.player1.getHeroWeaponName(), GameEngine.player1.getHeroArmorName(),
                GameEngine.player1.getHeroHelmName(), GameEngine.player1.getHeroBootsName());
        System.out.printf("LVL: %d                LVL: %d                LVL: %d                LVL: %d" +
                        "                          LVL: %d" +
                        whitеSpaces.substring(Integer.toString(GameEngine.player1.getHeroLVL()).length(), 17) + "LVL: %d" +
                        "               LVL: %d" + whitеSpaces.substring(Integer.toString(GameEngine.player1.getHeroLVL()).length(), 15) +
                        "LVL: %d%n", prizeItem[1].getItemLVL(), prizeItem[2].getItemLVL(), prizeItem[3].getItemLVL(), prizeItem[4].getItemLVL(),
                GameEngine.player1.getHeroWeaponLVL(), GameEngine.player1.getHeroArmorLVL(),
                GameEngine.player1.getHeroHelmLVL(), GameEngine.player1.getHeroBootsLVL());

        System.out.printf(prizeItem[1].itemStat1 + whitеSpaces.substring(0, 10) + prizeItem[2].itemStat1 + whitеSpaces.substring(0, 10) +
                        prizeItem[3].itemStat1 + whitеSpaces.substring(0, 10) + prizeItem[4].itemStat1 + whitеSpaces.substring(0, 20) +
                        "DMG:" + whitеSpaces.substring(0, 8 - (Integer.toString(GameEngine.player1.getHeroWeaponMinDMG()).length() +
                        1 + Integer.toString(GameEngine.player1.getHeroWeaponMaxDMG()).length())) + "%d-%d" +
                        whitеSpaces.substring(0, 10) + "Armor:" +
                        whitеSpaces.substring(0, 7 - Integer.toString(GameEngine.player1.armor.getItemArmor()).length()) +
                        "%s" + whitеSpaces.substring(0, 8) + "Health:" +
                        whitеSpaces.substring(0, 6 - Integer.toString(GameEngine.player1.helm.getItemHP()).length()) +
                        "%s" + whitеSpaces.substring(0, 7) + "HP.Reg:  %.2f%n", GameEngine.player1.getHeroWeaponMinDMG(),
                GameEngine.player1.getHeroWeaponMaxDMG(), GameEngine.player1.armor.getItemArmor(),
                GameEngine.player1.helm.getItemHP(), GameEngine.player1.boots.getItemHPReg());
        System.out.printf(prizeItem[1].itemStat2 + whitеSpaces.substring(0, 10) + prizeItem[2].itemStat2 +
                        whitеSpaces.substring(0, 10) + prizeItem[3].itemStat2 + whitеSpaces.substring(0, 10) + prizeItem[4].itemStat2 +
                        whitеSpaces.substring(0, 20) + "AS:" + whitеSpaces.substring(0, 5) + "%.2f" +
                        whitеSpaces.substring(0, 10) + "Health:" +
                        whitеSpaces.substring(0, 8 - Double.toString(GameEngine.player1.armor.getItemHP()).length()) +
                        "%d" + whitеSpaces.substring(0, 8) + "Shield:" +
                        whitеSpaces.substring(0, 6 - Integer.toString(GameEngine.player1.helm.getItemShield()).length()) +
                        "%d" + whitеSpaces.substring(0, 7) + "Evsn:" +
                        whitеSpaces.substring(0, 8 - Integer.toString(GameEngine.player1.boots.getItemEvasionChance()).length()) +
                        "%d%n", GameEngine.player1.weapon.getItemAttackSpeed(), GameEngine.player1.armor.getItemHP(),
                GameEngine.player1.helm.getItemShield(), GameEngine.player1.boots.getItemEvasionChance());
        System.out.printf(prizeItem[1].itemStat3 + whitеSpaces.substring(0, 10) + prizeItem[2].itemStat3 + whitеSpaces.substring(0, 10) +
                        prizeItem[3].itemStat3 + whitеSpaces.substring(0, 10) + prizeItem[4].itemStat3 + whitеSpaces.substring(0, 42) +
                        "Shield:" + whitеSpaces.substring(0, 6 - Integer.toString(GameEngine.player1.armor.getItemShield()).length()) +
                        GameEngine.player1.armor.getItemShield() + whitеSpaces.substring(0, 8) + "S.Pwr:" +
                        whitеSpaces.substring(0, 7 - Integer.toString(GameEngine.player1.helm.getItemSpellPower()).length()) +
                        GameEngine.player1.helm.getItemSpellPower() + whitеSpaces.substring(0, 7) + "M.Reg: " + "  %.2f%n",
                GameEngine.player1.boots.getItemManaReg());
        System.out.printf(prizeItem[1].itemStat4 + whitеSpaces.substring(0, 10) + prizeItem[2].itemStat4 + whitеSpaces.substring(0, 10) +
                        prizeItem[3].itemStat4 + whitеSpaces.substring(0, 10) + prizeItem[4].itemStat4 + whitеSpaces.substring(0, 20) +
                        "Crit%%:" +
                        whitеSpaces.substring(0, 6 - Integer.toString(GameEngine.player1.weapon.getItemCriticalChance()).length()) +
                        "%d" + whitеSpaces.substring(0, 10) + "HP.Reg:  %.2f" + whitеSpaces.substring(0, 8) + "Resist:" +
                        whitеSpaces.substring(0, 6 - Integer.toString(GameEngine.player1.helm.getItemMagicResistance()).length()) +
                        GameEngine.player1.helm.getItemMagicResistance() + whitеSpaces.substring(0, 7) + "AS:   " +
                        whitеSpaces.substring(0, 8 - Double.toString(GameEngine.player1.boots.getItemAttackSpeed()).length()) +
                        "%.2f%n", GameEngine.player1.weapon.getItemCriticalChance(), GameEngine.player1.armor.getItemHPReg(),
                GameEngine.player1.boots.getItemAttackSpeed());
        System.out.printf(prizeItem[1].itemStat5 + whitеSpaces.substring(0, 10) + prizeItem[2].itemStat5 + whitеSpaces.substring(0, 10) +
                prizeItem[3].itemStat5 + whitеSpaces.substring(0, 10) + prizeItem[4].itemStat5 + whitеSpaces.substring(0, 20) +
                "Stun%%:" + whitеSpaces.substring(0, 6 - Integer.toString(GameEngine.player1.weapon.getItemStunChance()).length()) +
                "%d" + whitеSpaces.substring(0, 10) + "Evsn%%:" +
                whitеSpaces.substring(0, 7 - Integer.toString(GameEngine.player1.armor.getItemEvasionChance()).length()) +
                "%d%n", GameEngine.player1.weapon.getItemStunChance(), GameEngine.player1.armor.getItemEvasionChance());
        System.out.printf(prizeItem[1].itemStat6 + whitеSpaces.substring(0, 10) + prizeItem[2].itemStat6 + whitеSpaces.substring(0, 10) +
                prizeItem[3].itemStat6 + whitеSpaces.substring(0, 10) + prizeItem[4].itemStat6 + whitеSpaces.substring(0, 20) +
                "S.Pwr:" + whitеSpaces.substring(0, 6 - Integer.toString(GameEngine.player1.weapon.getWeaponSpellPower()).length()) +
                "%d" + whitеSpaces.substring(0, 10) +
                "S.Pwr:" + whitеSpaces.substring(0, 7 - Integer.toString(GameEngine.player1.armor.getItemSpellPower()).length()) +
                "%d%n", GameEngine.player1.weapon.getWeaponSpellPower(), GameEngine.player1.armor.getItemSpellPower());
        System.out.printf(prizeItem[1].itemStat7 + whitеSpaces.substring(0, 10) + prizeItem[2].itemStat7 + whitеSpaces.substring(0, 10) +
                prizeItem[3].itemStat7 + whitеSpaces.substring(0, 10) + prizeItem[4].itemStat7 + whitеSpaces.substring(0, 20) +
                whitеSpaces.substring(0, 22) + "Resist:" +
                whitеSpaces.substring(0, 6 - Integer.toString(GameEngine.player1.armor.getItemMagicResistance()).length()) +
                "%d%n", GameEngine.player1.armor.getItemMagicResistance());
        System.out.printf(prizeItem[1].itemStat8 + whitеSpaces.substring(0, 10) + prizeItem[2].itemStat8 + whitеSpaces.substring(0, 10) +
                        prizeItem[3].itemStat8 + whitеSpaces.substring(0, 10) + prizeItem[4].itemStat8 + whitеSpaces.substring(0, 20) +
                        whitеSpaces.substring(0, 22) + "M.Reg:" + whitеSpaces.substring(0, 4) + "%s%n",
                String.valueOf(GameEngine.player1.armor.getItemManaReg()).substring(0, 3));

        System.out.printf("PICK YOUR PRIZE:%n");
        System.out.printf("(1) %s  (2) %s  (3) %s  (4) %s   (0) No One%n", prizeItem[1].getItemName(), prizeItem[2].getItemName(),
                prizeItem[3].getItemName(), prizeItem[4].getItemName());
    }

    private static void heroWinConsequence() throws InterruptedException {
        GameEngine.enemy.setMonsterHealth(0);
        heroSpeed += GameEngine.player1.getHeroBaseAttackSpeed() + GameEngine.player1.getHeroWeaponAttackSpeed() +
        GameEngine.player1.getHeroBootsAttackSpeed();
        System.out.printf("%n%n%n%n        The %s is fallen! " + "%n", GameEngine.enemy.getMonsterName());
        Thread.sleep(1500L);
        combatTurnOverview();
        System.out.printf(GameEngine.playerName + " win the battle!%n%n%n");
        Thread.sleep(1500L);
        afterBattleHeroStatsRestore();
        afterBattleHeroSpellBookRestore();
        afterBattleMonsterSkillRestore();
        GameEngine.player1.setHeroCurrentMana(GameEngine.player1.getHeroBaseMana());
        GameEngine.player1.setHeroXP();
        if (GameEngine.player1.getHeroXP() >= (GameEngine.player1.getHeroLVL() * 5)) {
            GameEngine.player1.lvlUP();
        }
    }

    private static void getItemPrize() throws InterruptedException {
        generateItemPrize();

        Scanner scanner = new Scanner(System.in);
        String prizePick = " ";

        while (!prizePick.equals("1") && !prizePick.equals("2") && !prizePick.equals("3") && !prizePick.equals("4") &&
                !prizePick.equals("0") && !prizePick.toUpperCase().equals("E") && !prizePick.toUpperCase().equals("END")) {
            printRandomItemPrizes();
            prizePick = scanner.nextLine();
        }
        if (prizePick.equals("0")) {
            return;
        } else if (prizePick.toUpperCase().equals("E")) {
            GameEngine.setGameStatus("END");
        } else if (prizePick.toUpperCase().equals("END")) {
            GameEngine.setGameStatus("END");
        } else {
            int pickedPrizeItemPosition = Integer.parseInt(prizePick);

            if (prizeItem[pickedPrizeItemPosition].getItemModel().equals("WEAPON")) {
                if (prizeItem[pickedPrizeItemPosition] instanceof Weapon) {
                    Weapon prizeItem = (Weapon) Fight.prizeItem[pickedPrizeItemPosition];
                    GameEngine.player1.changeHeroWeapon(prizeItem);
                }
            } else if (prizeItem[pickedPrizeItemPosition].getItemModel().equals("ARMOR")) {
                if (prizeItem[pickedPrizeItemPosition].getItemModel().equals("ARMOR")) {
                    if (prizeItem[pickedPrizeItemPosition] instanceof Armor) {
                        Armor prizeItem = (Armor) Fight.prizeItem[pickedPrizeItemPosition];
                        GameEngine.player1.changeHeroArmor(GameEngine.player1.armor, prizeItem);
                    }
                }
            } else if (prizeItem[pickedPrizeItemPosition].getItemModel().equals("HELM")) {
                if (prizeItem[pickedPrizeItemPosition].getItemModel().equals("HELM")) {
                    if (prizeItem[pickedPrizeItemPosition] instanceof Helm) {
                        Helm prizeItem = (Helm) Fight.prizeItem[pickedPrizeItemPosition];
                        GameEngine.player1.changeHeroHelm(GameEngine.player1.helm, prizeItem);
                    }
                }
            } else if (prizeItem[pickedPrizeItemPosition].getItemModel().equals("BOOTS")) {
                if (prizeItem[pickedPrizeItemPosition].getItemModel().equals("BOOTS")) {
                    if (prizeItem[pickedPrizeItemPosition] instanceof Boots) {
                        Boots prizeItem = (Boots) Fight.prizeItem[pickedPrizeItemPosition];
                        GameEngine.player1.changeHeroBoots(GameEngine.player1.boots, prizeItem);
                    }
                }
            }

            System.out.printf("%n%n%n");

            GameEngine.player1.showEquippedHeroStats();
            Thread.sleep(2000);
        }
    }

    private static double checkForMonsterCriticalStrike() {
        critStrike = rand.nextInt(100) + 1;
        if (critStrike <= GameEngine.enemy.getMonsterCritChance()) {
            criticalDmgCoeficient = 1.5;
        } else {
            criticalDmgCoeficient = 1;
        }
        return criticalDmgCoeficient;
    }

    private static void calculateMonsterAttackDMG() throws InterruptedException {
        monsterAttackDMG = (int) (GameEngine.enemy.getMonsterDamage() * criticalDmgCoeficient) - GameEngine.player1.getHeroCurrentArmor();
        if (monsterAttackDMG < 1) {
            monsterAttackDMG = 1;
        }
        if (criticalDmgCoeficient == 1.5) {
            System.out.printf(whitеSpaces.substring(0, 28) + "%s make %d DMG CRITICAL strike%n",
                    GameEngine.enemy.getMonsterName().trim(), monsterAttackDMG);
        } else {
            System.out.printf(whitеSpaces.substring(0, 28) + "%s make %d DMG%n", GameEngine.enemy.getMonsterName().trim(),
                    monsterAttackDMG);
        }
        Thread.sleep(1500L);
    }
}