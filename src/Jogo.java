import java.util.Scanner;

public class Jogo {

    // Atributos
    Scanner teclado;

    // Construtor
    public Jogo() {
        teclado = new Scanner(System.in);
    }

    // Metodos

    // O "throws Exception" e por causa do Thread.sleep, que da as pausas do jogo
    void abrirMenu() throws Exception {
        boolean continuar = true;

        while (continuar) {
            // Empurra a tela anterior para cima
            for (int i = 0; i < 30; i++) {
                System.out.println();
            }

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
                int numero1 = escolherLutador("JOGADOR 1");
                int numero2 = escolherLutador("JOGADOR 2");

                Lutador jogador1 = pegarLutador(numero1);
                Lutador jogador2 = pegarLutador(numero2);

                iniciarBatalha(jogador1, jogador2);
            } else if (opcao == 2) {
                int numero = escolherLutador("VOCE");
                Lutador lutador = pegarLutador(numero);

                System.out.println();
                System.out.println("   =========== FICHA DO LUTADOR ===========");
                lutador.mostrarFicha();
                System.out.println("   ========================================");
                System.out.println();
                System.out.print("   Digite 1 para voltar ao menu: ");
                teclado.nextInt();
            } else if (opcao == 3) {
                continuar = false;
                System.out.println();
                System.out.println("   Ate a proxima! Obrigado por jogar.");
                System.out.println();
            } else {
                System.out.println("   Opcao invalida.");
                Thread.sleep(1200);
            }
        }
    }

    // Mostra a lista e pede um numero de 1 a 6.
    // Os dois jogadores e a tela de ficha usam este mesmo metodo.
    int escolherLutador(String quemEscolhe) {
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }

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

        while (numero < 1 || numero > 6) {
            System.out.print("   Numero invalido. Escolha de 1 a 6: ");
            numero = teclado.nextInt();
        }

        return numero;
    }

    // Cria o objeto do lutador que foi escolhido.
    // Como cria um lutador novo a cada chamada, os dois jogadores podem
    // escolher o mesmo personagem sem dividirem a mesma barra de vida.
    Lutador pegarLutador(int numero) {
        //                            nome       pais      estilo        golpe especial        soco chute esp def vel
        if (numero == 1) {
            return new Lutador("Ryu",     "Japao",  "Karate",     "Hadouken",            14, 22, 45, 5, 6);
        } else if (numero == 2) {
            return new Lutador("Ken",     "EUA",    "Karate",     "Shoryuken",           16, 24, 48, 4, 7);
        } else if (numero == 3) {
            return new Lutador("Chun-Li", "China",  "Kung Fu",    "Spinning Bird Kick",  13, 20, 42, 4, 9);
        } else if (numero == 4) {
            return new Lutador("Blanka",  "Brasil", "Selvagem",   "Electric Thunder",    17, 26, 50, 3, 6);
        } else if (numero == 5) {
            return new Lutador("Zangief", "Russia", "Luta Livre", "Spinning Piledriver", 19, 30, 55, 7, 2);
        }
        return new Lutador("Dhalsim", "India",  "Yoga",       "Yoga Flame",          14, 23, 46, 3, 8);
    }

    // A luta e melhor de 3: acaba quando alguem vence 2 rounds
    void iniciarBatalha(Lutador lutador1, Lutador lutador2) throws Exception {
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }

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
        Thread.sleep(2500);

        int round = 1;

        while (lutador1.roundsVencidos < 2 && lutador2.roundsVencidos < 2) {
            // Os dois voltam com a vida cheia e a guarda limpa a cada round
            lutador1.vida = 100;
            lutador1.defendendo = false;
            lutador1.guardaAberta = false;

            lutador2.vida = 100;
            lutador2.defendendo = false;
            lutador2.guardaAberta = false;

            System.out.println();
            System.out.println("   =========== ROUND " + round + " - LUTEM! ===========");
            Thread.sleep(1500);

            disputarRound(lutador1, lutador2);

            // Quem sobrou de pe ganhou o round
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
            Thread.sleep(2500);

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
        System.out.println();
        System.out.print("   Digite 1 para voltar ao menu: ");
        teclado.nextInt();
    }

    // Um round vai ate a vida de um dos dois chegar a zero
    void disputarRound(Lutador lutador1, Lutador lutador2) throws Exception {
        int turno = 1;

        while (lutador1.vida > 0 && lutador2.vida > 0) {
            System.out.println();
            System.out.println("   --------------- TURNO " + turno + " ---------------");
            lutador1.mostrarBarra();
            lutador2.mostrarBarra();
            System.out.println("   Rounds vencidos: " + lutador1.nome + " " + lutador1.roundsVencidos + " x " + lutador2.roundsVencidos + " " + lutador2.nome);
            System.out.println("   ------------------------------------------");

            // Quem tem mais velocidade ataca primeiro
            if (lutador1.velocidade >= lutador2.velocidade) {
                executarTurno(lutador1, lutador2);

                if (lutador2.vida > 0) {
                    executarTurno(lutador2, lutador1);
                }
            } else {
                executarTurno(lutador2, lutador1);

                if (lutador1.vida > 0) {
                    executarTurno(lutador1, lutador2);
                }
            }

            turno = turno + 1;
        }
    }

    // Um turno so: o jogador escolhe uma acao e ela e executada
    void executarTurno(Lutador atacante, Lutador defensor) throws Exception {
        // A guarda levantada e a guarda aberta valem so ate o lutador agir de novo
        atacante.defendendo = false;
        atacante.guardaAberta = false;

        System.out.println();
        System.out.println("   Vez de " + atacante.nome);
        System.out.println("   [1] Soco      [2] Chute     [3] Defender");
        System.out.println("   [4] Provocar  [5] " + atacante.especial);
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
            atacante.provocar();
        } else if (acao == 5) {
            atacante.especial(defensor);
        } else {
            System.out.println("   Opcao invalida, " + atacante.nome + " ficou parado.");
        }

        Thread.sleep(1500);
    }
}
