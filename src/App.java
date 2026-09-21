import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Criando os lutadores
        Lutador ryu = new Lutador( "Ryu", "Japao", "Karate", "Hadouken", 14, 22, 45);
        Lutador chunLi = new Lutador("Chun-Li", "China", "Kung Fu", "Spinning Bird Kick", 13, 20, 42);
        Lutador blanka = new Lutador("Blanka", "Brasil", "Selvagem", "Electric Thunder", 17, 26, 50);
        Lutador zangief = new Lutador( "Zangief", "Russia", "Luta Livre", "Spinning Piledriver", 19, 30, 55);

        int opcao = 0;
        while (opcao != 3) {

            System.out.println();
            System.out.println("================================");
            System.out.println("       STREET FIGHTER - JAVA POO");
            System.out.println("================================");
            System.out.println("[1] Lutar");
            System.out.println("[2] Ver ficha dos lutadores");
            System.out.println("[3] Sair");
            System.out.print("Escolha uma opcao: ");
            opcao = scanner.nextInt();

            // Lutar
            if (opcao == 1) {
                System.out.println();
                System.out.println("===== ESCOLHA OS LUTADORES =====");
                System.out.println("[1] Ryu - Japao - Karate");
                System.out.println("[2] Chun-Li - China - Kung Fu");
                System.out.println("[3] Blanka - Brasil - Selvagem");
                System.out.println("[4] Zangief - Russia - Luta Livre");

                System.out.print("Jogador 1: ");
                int escolha1 = scanner.nextInt();

                System.out.print("Jogador 2: ");
                int escolha2 = scanner.nextInt();

                Lutador jogador1;
                Lutador jogador2;

                // escolha do jogador 1
                if (escolha1 == 1) {
                    jogador1 = ryu;
                } else if (escolha1 == 2) {
                    jogador1 = chunLi;
                } else if (escolha1 == 3) {
                    jogador1 = blanka;
                } else {
                    jogador1 = zangief;
                }

                // escolha do jogador 2
                if (escolha2 == 1) {
                    jogador2 = ryu;
                } else if (escolha2 == 2) {
                    jogador2 = chunLi;
                } else if (escolha2 == 3) {
                    jogador2 = blanka;
                } else {
                    jogador2 = zangief;
                }

                // a luta começa com vida cheia e sem defesa
                jogador1.vida = 100;
                jogador2.vida = 100;
                jogador1.defendendo = false;
                jogador2.defendendo = false;

                System.out.println();
                System.out.println("===== " + jogador1.nome + " X " + jogador2.nome + " =====");

                // enquanto ambos os jogadores estiverem com vida
                while (jogador1.vida > 0 && jogador2.vida > 0) {

                    System.out.println();
                    System.out.println(jogador1.nome + ": " + jogador1.vida + " de vida");
                    System.out.println(jogador2.nome + ": " + jogador2.vida + " de vida");

                    // a guarda da rodada anterior é encerrada
                    jogador1.defendendo = false;
                    jogador2.defendendo = false;

                    // Jogador 1
                    System.out.println();
                    System.out.println("Vez de " + jogador1.nome);
                    System.out.println("[1] Soco");
                    System.out.println("[2] Chute");
                    System.out.println("[3] Defender");
                    System.out.println("[4] " + jogador1.especial);

                    System.out.print("Escolha: ");
                    int acao = scanner.nextInt();

                    if (acao == 1) {
                        jogador1.socar(jogador2);
                    } else if (acao == 2) {
                        jogador1.chutar(jogador2);
                    } else if (acao == 3) {
                        jogador1.defender();
                    } else if (acao == 4) {
                        jogador1.golpeEspecial(jogador2);
                    }

                    // Jogador 2 só joga se estiver vivo
                    if (jogador2.vida > 0) {
                        System.out.println();
                        System.out.println("Vez de " + jogador2.nome);
                        System.out.println("[1] Soco");
                        System.out.println("[2] Chute");
                        System.out.println("[3] Defender");
                        System.out.println("[4] " + jogador2.especial);

                        System.out.print("Escolha: ");
                        acao = scanner.nextInt();

                        if (acao == 1) {
                            jogador2.socar(jogador1);
                        } else if (acao == 2) {
                            jogador2.chutar(jogador1);
                        } else if (acao == 3) {
                            jogador2.defender();
                        } else if (acao == 4) {
                            jogador2.golpeEspecial(jogador1);
                        }
                    }
                }

                // Resultado final 
                System.out.println();
                System.out.println("=========== K.O.! ===========");

                if (jogador1.vida > 0) {
                    System.out.println(jogador1.nome + " venceu!");
                } else {
                    System.out.println(jogador2.nome + " venceu!");
                }
                System.out.println("==============================");

            } else if (opcao == 2) {
                // Ficha dos lutadores
                System.out.println();
                System.out.println("===== FICHA DOS LUTADORES =====");

                ryu.info();
                chunLi.info();
                blanka.info();
                zangief.info();

            } else if (opcao == 3) {
                System.out.println();
                System.out.println("Até a próxima! Obrigado por jogar.");

            } else {
                System.out.println("Opção inválida.");
            }
        }
        scanner.close();
    }
}