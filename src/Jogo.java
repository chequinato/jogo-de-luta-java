import java.util.Scanner;

public class Jogo {

    Lutador ryu;
    Lutador ken;
    Lutador chunLi;
    Lutador blanka;
    Lutador zangief;
    Lutador dhalsim;

    Scanner teclado;

    public Jogo() {
        teclado = new Scanner(System.in);

        ryu = new Lutador("Ryu", "Japao", "Karate", "Hadouken", 14, 22, 45, 5, 6);
        ken = new Lutador("Ken", "EUA", "Karate", "Shoryuken", 16, 24, 48, 4, 7);
        chunLi = new Lutador("Chun-Li", "China", "Kung Fu", "Spinning Bird Kick", 13, 20, 42, 4, 9);
        blanka = new Lutador("Blanka", "Brasil", "Selvagem", "Electric Thunder", 17, 26, 50, 3, 6);
        zangief = new Lutador("Zangief", "Russia", "Luta Livre", "Spinning Piledriver", 19, 30, 55, 7, 2);
        dhalsim = new Lutador("Dhalsim", "India", "Yoga", "Yoga Flame", 14, 23, 46, 3, 8);
    }

    void abrirMenu() {
        boolean continuar = true;

        while (continuar) {
            limparTela();
            mostrarAbertura();
            System.out.println("  MENU PRINCIPAL");
            System.out.println("  ---------------------------");
            System.out.println("   [1] Jogador vs Computador");
            System.out.println("   [2] Jogador vs Jogador");
            System.out.println("   [3] Ver ficha");
            System.out.println("   [4] Sair");
            System.out.print("   Escolha: ");

            int opcao = teclado.nextInt();

            if (opcao == 1) {
                jogarContraComputador();
            } else if (opcao == 2) {
                jogarContraJogador();
            } else if (opcao == 3) {
                verFicha();
            } else if (opcao == 4) {
                continuar = false;
                mostrarDespedida();
            } else {
                System.out.println("  Opcao invalida.");
                pausa(1200);
            }
        }
    }

    void jogarContraComputador() {
        int numero = escolherLutador("JOGADOR 1");
        Lutador jogador = pegarLutador(numero);

        int numeroCpu = numero + 1;
        if (numeroCpu > 6) {
            numeroCpu = 1;
        }

        Lutador computador = pegarLutador(numeroCpu);
        System.out.println("  O computador vai lutar com " + computador.nome + ".");
        pausa(1200);

        iniciarBatalha(jogador, computador, true);
    }

    void jogarContraJogador() {
        int numero1 = escolherLutador("JOGADOR 1");
        int numero2 = escolherLutador("JOGADOR 2");

        Lutador jogador1 = pegarLutador(numero1);
        Lutador jogador2 = pegarLutador(numero2);

        iniciarBatalha(jogador1, jogador2, false);
    }

    void iniciarBatalha(Lutador lutador1, Lutador lutador2, boolean contraComputador) {
        System.out.println();
        System.out.println("  " + lutador1.nome + " vs " + lutador2.nome);
        System.out.println("  A luta vai comecar!");

        while (lutador1.estaVivo() && lutador2.estaVivo()) {
            if (lutador1.velocidade >= lutador2.velocidade) {
                executarTurno(lutador1, lutador2, true, contraComputador);

                if (lutador2.estaVivo() == false) {
                    break;
                }

                executarTurno(lutador2, lutador1, false, contraComputador);
            } else {
                executarTurno(lutador2, lutador1, false, contraComputador);

                if (lutador1.estaVivo() == false) {
                    break;
                }

                executarTurno(lutador1, lutador2, true, contraComputador);
            }
        }

        if (lutador1.estaVivo()) {
            System.out.println("  " + lutador1.nome + " venceu a luta!");
        } else {
            System.out.println("  " + lutador2.nome + " venceu a luta!");
        }

        System.out.println("  Digite 1 para voltar ao menu.");
        teclado.nextInt();
    }

    void executarTurno(Lutador atacante, Lutador defensor, boolean ehHumano, boolean contraComputador) {
        atacante.limparEstado();
        int ataque;

        if (ehHumano) {
            mostrarMenuDeAtaque(atacante);
            ataque = teclado.nextInt();
        } else {
            ataque = escolhaDoComputador(atacante, defensor, contraComputador);
            System.out.println("  O computador escolheu: " + ataque);
        }

        if (ataque == 1) {
            atacante.socar(defensor);
        } else if (ataque == 2) {
            atacante.chutar(defensor);
        } else if (ataque == 3) {
            atacante.defender();
        } else if (ataque == 4) {
            atacante.provocar();
        } else if (ataque == 5) {
            atacante.especial(defensor);
        } else {
            System.out.println("  Opcao invalida.");
        }

        System.out.println("  Vida de " + atacante.nome + ": " + atacante.vida);
        System.out.println("  Vida de " + defensor.nome + ": " + defensor.vida);
        System.out.println();
    }

    int escolhaDoComputador(Lutador atacante, Lutador defensor, boolean contraComputador) {
        if (atacante.vida < 30) {
            return 3;
        }
        if (defensor.vida <= 25) {
            return 2;
        }
        return 1;
    }

    void mostrarMenuDeAtaque(Lutador lutador) {
        System.out.println();
        System.out.println("  Turno de " + lutador.nome);
        System.out.println("  [1] Soco");
        System.out.println("  [2] Chute");
        System.out.println("  [3] Defender");
        System.out.println("  [4] Provocar");
        System.out.println("  [5] Especial");
        System.out.print("  Escolha: ");
    }

    int escolherLutador(String quemEscolhe) {
        mostrarLista();
        System.out.print("  " + quemEscolhe + ", escolha: ");
        int numero = teclado.nextInt();

        while (numero < 1 || numero > 6) {
            System.out.println("  Numero invalido. Escolha de 1 a 6.");
            numero = teclado.nextInt();
        }

        return numero;
    }

    void verFicha() {
        mostrarLista();
        System.out.print("  Escolha o lutador: ");
        int numero = teclado.nextInt();

        while (numero < 1 || numero > 6) {
            System.out.println("  Numero invalido. Escolha de 1 a 6.");
            numero = teclado.nextInt();
        }

        Lutador lutador = pegarLutador(numero);
        System.out.println();
        lutador.mostrarFicha();
        System.out.println();
        System.out.println("  Digite 1 para voltar ao menu.");
        teclado.nextInt();
    }

    Lutador pegarLutador(int numero) {
        if (numero == 1) {
            return ryu.clonar();
        } else if (numero == 2) {
            return ken.clonar();
        } else if (numero == 3) {
            return chunLi.clonar();
        } else if (numero == 4) {
            return blanka.clonar();
        } else if (numero == 5) {
            return zangief.clonar();
        }
        return dhalsim.clonar();
    }

    void mostrarLista() {
        System.out.println();
        System.out.println("  L U T A D O R E S");
        System.out.println("  ---------------------------");
        ryu.mostrarNaLista(1);
        ken.mostrarNaLista(2);
        chunLi.mostrarNaLista(3);
        blanka.mostrarNaLista(4);
        zangief.mostrarNaLista(5);
        dhalsim.mostrarNaLista(6);
        System.out.println();
    }

    void mostrarAbertura() {
        System.out.println();
        System.out.println("      SSSSS   TTTTT   RRRRR   EEEEE   EEEEE   TTTTT");
        System.out.println("      S         T      R   R   E       E           T");
        System.out.println("      SSSSS     T      RRRRR   EEEE    EEEE        T");
        System.out.println("          S     T      R  R    E       E           T");
        System.out.println("      SSSSS     T      R   R   EEEEE   EEEEE      T");
        System.out.println();
        System.out.println("      FFFFF   I      GGGGG   H   H   TTTTT   EEEEE   RRRRR");
        System.out.println("      F       I      G       H   H      T     E       R   R");
        System.out.println("      FFFF    I      G  GGG  HHHHH      T     EEEE    RRRRR");
        System.out.println("      F       I      G    G  H   H      T     E       R  R");
        System.out.println("      F       I      GGGGG  H   H      T     EEEEE   R   R");
        System.out.println();
        System.out.println("      JOGO DE LUTA EM JAVA");
        System.out.println("  ============================");
        System.out.println();
    }

    void mostrarDespedida() {
        System.out.println();
        System.out.println("  Ate a proxima!");
    }

    void limparTela() {
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }

    void pausa(int milissegundos) {
        try {
            Thread.sleep(milissegundos);
        } catch (InterruptedException e) {
            System.out.println("  Erro de pausa.");
        }
    }
}
