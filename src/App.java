
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Lutador ryu = new Lutador("Ryu", "Japao", "Karate", "Hadouken", 14, 22, 45);
        Lutador chunLi = new Lutador("Chun-Li", "China", "Kung Fu", "Spinning Bird Kick", 13, 20, 42);
        Lutador blanka = new Lutador("Blanka", "Brasil", "Selvagem", "Electric Thunder", 17, 26, 50);
        Lutador zangief = new Lutador("Zangief", "Russia", "Luta Livre", "Spinning Piledriver", 19, 30, 55);

        System.out.println();
        System.out.println("   SSSSS TTTTT RRRR  EE EEE EEEEE TTTTT");
        System.out.println("   S       T   R   R E     E       T");
        System.out.println("   SSSSS   T   RRRR  EEEE  EEEE    T");
        System.out.println("       S   T   R  R  E     E       T");
        System.out.println("   SSSSS   T   R   R EEEEE EEEEE   T");
        System.out.println();
        System.out.println("   FFFFF IIIII  GGGG H   H TTTTT EEEEE RRRR");
        System.out.println("   F       I   G     H   H   T   E     R   R");
        System.out.println("   FFFF    I   G  GG HHHHH   T   EEEE  RRRR");
        System.out.println("   F       I   G   G H   H   T   E     R  R");
        System.out.println("   F     IIIII  GGGG H   H   T   EEEEE R   R");
        System.out.println();

        System.out.println("Jogo de luta - Java POO");

        int opcao = 0;

        while (opcao != 3){
            System.out.println();
            System.out.println("=========== MENU ===========");
            System.out.println("[1] Lutar");
            System.out.println("[2] Ver a ficha dos lutadores");
            System.out.println("[3] Sair");
            System.out.println("============================");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();

            if (opcao == 1){
                System.out.println();
                System.out.println("===== ESCOLHAM OS LUTADORES =====");
                System.out.println("[1] Ryu       Japao    Karate");
                System.out.println("[2] Chun-Li   China    Kung Fu");
                System.out.println("[3] Blanka    Brasil   Selvagem");
                System.out.println("[4] Zangief   Russia   Luta Livre");
                System.out.println("=================================");

                System.out.print("Jogador 1 escolhe de 1 a 4: ");
                int escolha1 = scanner.nextInt();
                System.out.print("Jogador 2 escolhe de 1 a 4: ");
                int escolha2 = scanner.nextInt();

                // os dois jogadores não podem escolher o mesmo lutador
                if (escolha1 == escolha2){
                    System.out.print("Esse lutador já foi escolhido. Jogador 2 escolhe outro: ");
                    escolha2 = scanner.nextInt();
                }

                Lutador jogador1;

                if (escolha1 == 1) {
                    jogador1 = ryu;
                } else if (escolha1 == 2) {
                    jogador1 = chunLi;
                } else if (escolha1 == 3) {
                    jogador1 = blanka;
                } else {
                    jogador1 = zangief;
                }

                Lutador jogador2;

                if (escolha2 == 1) {
                    jogador2 = ryu;
                } else if (escolha2 == 2) {
                    jogador2 = chunLi;
                } else if (escolha2 == 3) {
                    jogador2 = blanka;
                } else {
                    jogador2 = zangief;
                }

                // os dois comecam a luta com a vida cheia e a guarda baixada
                jogador1.vida = 100;
                jogador1.defendendo = false;
                jogador2.vida = 100;
                jogador2.defendendo = false;

                System.out.println();
                System.out.println("===== " + jogador1.nome + " X " + jogador2.nome + " =====");
                System.out.println();

                jogador1.info();
                System.out.println("================================");
                jogador2.info();

                // A luta acaba quando a vida de um dos dois chega a zero
                while (jogador1.vida > 0 && jogador2.vida > 0){
                    System.out.println("---------------------------------");
                    System.out.println("   " + jogador1.nome + ": " + jogador1.vida + " de vida");
                    System.out.println("   " + jogador2.nome + ": " + jogador2.vida + " de vida");
                    System.out.println("---------------------------------");

                    // Vez do jogador 1
                    jogador1.defendendo = false; // a guarda da vez passada cai agora

                    System.out.println("Vez de " + jogador1.nome);
                    System.out.println("[1] Soco [2] Chute [3] Defender [4] " + jogador1.especial);
                    System.out.print("Escolha: ");
                    int acao1 = scanner.nextInt();

                    if (acao1 == 1) {
                        jogador1.socar(jogador2);
                    } else if (acao1 == 2) {
                        jogador1.chutar(jogador2);
                    } else if (acao1 == 3) {
                        jogador1.defender();
                    } else if (acao1 == 4) {
                        jogador1.golpeEspecial(jogador2);
                    } else {
                        System.out.println("Opção inválida. " + jogador1.nome + " perdeu a vez");
                    }

                    // Vez do jogador 2, se caso estiver vivo
                    if (jogador2.vida > 0){
                        jogador2.defendendo = false;
                        System.out.println();
                        System.out.println("Vez de " + jogador2.nome);
                        System.out.println("[1] Soco [2] Chute [3] Defender [4] " + jogador2.especial);
                        System.out.print("Escolha: ");

                        int acao2 = scanner.nextInt();

                        if (acao2 == 1) {
                            jogador2.socar(jogador1);
                        } else if (acao2 == 2) {
                            jogador2.chutar(jogador1);
                        } else if (acao2 == 3) {
                            jogador2.defender();
                        } else if (acao2 == 4) {
                            jogador2.golpeEspecial(jogador1);
                        } else {
                            System.out.println("Opção inválida. " + jogador2.nome + " perdeu a vez");
                        }
                    }
                }

                System.out.println();

                // quem sobrou venceu a luta
                System.out.println("=========== K . O . ! ===========");

                if (jogador1.vida > 0) {
                    System.out.println(" " + jogador1.nome + " venceu a luta com " + jogador1.vida + " de vida");
                } else {
                    System.out.println(" " + jogador2.nome + " venceu a luta com " + jogador2.vida + " de vida");
                }

                System.out.println("=================================");

                jogador1.vida = 100;
                jogador2.vida = 100;

            } else if (opcao == 2) {
                System.out.println();
                System.out.println("===== FICHA DOS LUTADORES =====");
                System.out.println();

                ryu.info();
                chunLi.info();
                blanka.info();
                zangief.info();

            } else if (opcao == 3) {
                System.out.println();
                System.out.println("Até a proxima! Obrigado por jogar.");
            } else {
                System.out.println("Opção inválida.");
            }
        }
        scanner.close();
    }
}
