import java.util.Scanner;

public class Jogo {

    // Atributos
    Scanner teclado;

    // Construtor
    public Jogo() {
        teclado = new Scanner(System.in);
    }

    // Metodos

    // Menu principal. O while segura o jogador aqui ate ele escolher sair
    void abrirMenu() {
        boolean continuar = true;

        while (continuar) {
            System.out.println();
            System.out.println("   SSSSS TTTTT RRRR  EEEEE EEEEE TTTTT");
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
            System.out.println("        j o g o   d e   l u t a   e m   j a v a");
            System.out.println();
            System.out.println("   =========== MENU PRINCIPAL ===========");
            System.out.println("   [1] Comecar a luta (2 jogadores)");
            System.out.println("   [2] Ver ficha de um lutador");
            System.out.println("   [3] Sair");
            System.out.println("   ======================================");
            System.out.print("   Escolha: ");

            int opcao = teclado.nextInt();

            if (opcao == 1) {
                Lutador jogador1 = escolherLutador("JOGADOR 1");
                Lutador jogador2 = escolherLutador("JOGADOR 2");

                iniciarBatalha(jogador1, jogador2);
            } else if (opcao == 2) {
                Lutador lutador = escolherLutador("VOCE");

                System.out.println();
                System.out.println("   =========== FICHA DO LUTADOR ===========");
                lutador.mostrarFicha();
                System.out.println("   ========================================");
                System.out.print("   Digite 1 para voltar ao menu: ");
                teclado.nextInt();
            } else if (opcao == 3) {
                continuar = false;
                System.out.println();
                System.out.println("   Ate a proxima! Obrigado por jogar.");
                System.out.println();
            } else {
                System.out.println("   Opcao invalida.");
            }
        }
    }

    // Mostra a lista, pede um numero de 1 a 6 e cria o lutador escolhido.
    // Os dois jogadores e a tela de ficha usam este mesmo metodo
    Lutador escolherLutador(String quemEscolhe) {
        System.out.println();
        System.out.println("   =============== ESCOLHA SEU LUTADOR ===============");
        System.out.println("   [1] Ryu       Japao    Karate       Hadouken");
        System.out.println("   [2] Ken       EUA      Karate       Shoryuken");
        System.out.println("   [3] Chun-Li   China    Kung Fu      Spinning Bird Kick");
        System.out.println("   [4] Blanka    Brasil   Selvagem     Electric Thunder");
        System.out.println("   [5] Zangief   Russia   Luta Livre   Spinning Piledriver");
        System.out.println("   [6] Dhalsim   India    Yoga         Yoga Flame");
        System.out.println("   ==================================================");
        System.out.print("   " + quemEscolhe + ", escolha de 1 a 6: ");

        int numero = teclado.nextInt();

        // enquanto o numero estiver fora da lista, pede de novo
        while (numero < 1 || numero > 6) {
            System.out.print("   Numero invalido. Escolha de 1 a 6: ");
            numero = teclado.nextInt();
        }

        // Cria um lutador novo a cada escolha, por isso os dois jogadores podem
        // pegar o mesmo personagem sem dividirem a mesma barra de vida
        //                            nome       pais      estilo        golpe especial        soco chute especial
        if (numero == 1) {
            return new Lutador("Ryu",     "Japao",  "Karate",     "Hadouken",            14, 22, 45);
        } else if (numero == 2) {
            return new Lutador("Ken",     "EUA",    "Karate",     "Shoryuken",           16, 24, 48);
        } else if (numero == 3) {
            return new Lutador("Chun-Li", "China",  "Kung Fu",    "Spinning Bird Kick",  13, 20, 42);
        } else if (numero == 4) {
            return new Lutador("Blanka",  "Brasil", "Selvagem",   "Electric Thunder",    17, 26, 50);
        } else if (numero == 5) {
            return new Lutador("Zangief", "Russia", "Luta Livre", "Spinning Piledriver", 19, 30, 55);
        }
        return new Lutador("Dhalsim", "India",  "Yoga",       "Yoga Flame",          14, 23, 46);
    }

    // A luta e melhor de 3: acaba quando alguem vence 2 rounds
    void iniciarBatalha(Lutador lutador1, Lutador lutador2) {
        System.out.println();
        System.out.println("   ================================================");
        System.out.println("   " + lutador1.nome + " - " + lutador1.pais + " - " + lutador1.estilo);
        System.out.println("   Golpe especial: " + lutador1.especial);
        System.out.println();
        System.out.println("                      V S");
        System.out.println();
        System.out.println("   " + lutador2.nome + " - " + lutador2.pais + " - " + lutador2.estilo);
        System.out.println("   Golpe especial: " + lutador2.especial);
        System.out.println("   ================================================");
        System.out.println("   Melhor de 3 rounds. Quem vencer 2 leva a luta.");

        int round = 1;

        // este while conta os rounds
        while (lutador1.roundsVencidos < 2 && lutador2.roundsVencidos < 2) {
            // os dois voltam com a vida cheia e a guarda baixada a cada round
            lutador1.vida = 100;
            lutador1.defendendo = false;
            lutador2.vida = 100;
            lutador2.defendendo = false;

            System.out.println();
            System.out.println("   =========== ROUND " + round + " - LUTEM! ===========");

            int turno = 1;

            // e este while, que esta dentro do outro, conta os turnos do round
            while (lutador1.vida > 0 && lutador2.vida > 0) {
                System.out.println();
                System.out.println("   --------------- TURNO " + turno + " ---------------");
                lutador1.mostrarBarra();
                lutador2.mostrarBarra();
                System.out.println("   Placar: " + lutador1.nome + " " + lutador1.roundsVencidos + " x " + lutador2.roundsVencidos + " " + lutador2.nome);
                System.out.println("   ------------------------------------------");

                executarTurno(lutador1, lutador2);

                // o jogador 2 so joga se ainda estiver de pe
                if (lutador2.vida > 0) {
                    executarTurno(lutador2, lutador1);
                }

                turno = turno + 1;
            }

            // quem sobrou de pe ganhou o round
            System.out.println();
            System.out.println("   =============== K . O . ! ===============");

            if (lutador1.vida > 0) {
                lutador1.roundsVencidos = lutador1.roundsVencidos + 1;
                System.out.println("   " + lutador1.nome + " venceu o round " + round + "!");
            } else {
                lutador2.roundsVencidos = lutador2.roundsVencidos + 1;
                System.out.println("   " + lutador2.nome + " venceu o round " + round + "!");
            }

            System.out.println("   Placar: " + lutador1.nome + " " + lutador1.roundsVencidos + " x " + lutador2.roundsVencidos + " " + lutador2.nome);
            System.out.println("   =========================================");

            round = round + 1;
        }

        System.out.println();
        System.out.println("   =========== FIM DA LUTA ===========");

        if (lutador1.roundsVencidos == 2) {
            System.out.println("   " + lutador1.nome + " VENCEU A LUTA!");
            System.out.println("   " + lutador2.nome + " nao aguentou.");
        } else {
            System.out.println("   " + lutador2.nome + " VENCEU A LUTA!");
            System.out.println("   " + lutador1.nome + " nao aguentou.");
        }

        System.out.println("   Placar final: " + lutador1.roundsVencidos + " x " + lutador2.roundsVencidos);
        System.out.println("   ===================================");
        System.out.print("   Digite 1 para voltar ao menu: ");
        teclado.nextInt();
    }

    // Um turno so: o jogador escolhe uma acao e ela e executada
    void executarTurno(Lutador atacante, Lutador defensor) {
        // a guarda levantada vale so ate o lutador jogar de novo
        atacante.defendendo = false;

        System.out.println();
        System.out.println("   Vez de " + atacante.nome);
        System.out.println("   [1] Soco   [2] Chute   [3] Defender   [4] " + atacante.especial);
        System.out.print("   Escolha: ");

        int acao = teclado.nextInt();
        System.out.println();

        if (acao == 1) {
            atacante.socar(defensor);
        } else if (acao == 2) {
            atacante.chutar(defensor);
        } else if (acao == 3) {
            atacante.defender();
        } else if (acao == 4) {
            atacante.golpeEspecial(defensor);
        } else {
            System.out.println("   Opcao invalida, " + atacante.nome + " ficou parado.");
        }
    }
}
