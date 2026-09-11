import java.util.Scanner;

// Esta classe e o jogo em si: ela guarda os lutadores disponiveis, mostra o
// menu, deixa o jogador escolher o personagem e manda a Arena comecar a luta.
public class Jogo {

    // ===================== ATRIBUTOS =====================
    Lutador[] lutadores;    // todos os lutadores do jogo guardados juntos
    Scanner teclado;
    Cores cor;

    // ===================== CONSTRUTOR =====================
    // E aqui que os 6 lutadores sao criados, cada um com o dano dos seus
    // golpes ja definido. Para colocar mais um, e so aumentar o numero do
    // new Lutador[6] e acrescentar outra linha.
    public Jogo() {
        this.teclado = new Scanner(System.in);
        this.cor = new Cores();

        this.lutadores = new Lutador[6];
        //                             nome       pais      estilo        especial              soco chute esp def vel
        this.lutadores[0] = new Lutador("Ryu",     "Japao",  "Karate",     "HADOUKEN",             14,  22,  45,  5,  6);
        this.lutadores[1] = new Lutador("Ken",     "EUA",    "Karate",     "SHORYUKEN",            16,  24,  48,  4,  7);
        this.lutadores[2] = new Lutador("Chun-Li", "China",  "Kung Fu",    "SPINNING BIRD KICK",   13,  20,  42,  4,  9);
        this.lutadores[3] = new Lutador("Blanka",  "Brasil", "Selvagem",   "ELECTRIC THUNDER",     17,  26,  50,  3,  6);
        this.lutadores[4] = new Lutador("Zangief", "Russia", "Luta Livre", "SPINNING PILEDRIVER",  19,  30,  55,  7,  2);
        this.lutadores[5] = new Lutador("Dhalsim", "India",  "Yoga",       "YOGA FLAME",           14,  23,  46,  3,  8);
    }

    // ===================== O MENU =====================

    void abrirMenu() {
        boolean jogando = true;

        // o menu fica aparecendo ate o jogador escolher sair
        while (jogando) {
            limparTela();
            mostrarAbertura();

            System.out.println("  MENU PRINCIPAL");
            System.out.println("  --------------------------------------------------");
            System.out.println("   " + cor.amarelo + "[1]" + cor.reset + " Jogador contra o computador");
            System.out.println("   " + cor.amarelo + "[2]" + cor.reset + " Jogador contra jogador");
            System.out.println("   " + cor.amarelo + "[3]" + cor.reset + " Ver a ficha de um lutador");
            System.out.println("   " + cor.amarelo + "[4]" + cor.reset + " Sair do jogo");
            System.out.println();

            int opcao = teclado.nextInt();

            if (opcao == 1) {
                jogarContraOComputador();
            } else if (opcao == 2) {
                jogarContraOutroJogador();
            } else if (opcao == 3) {
                verFichaDeUmLutador();
            } else if (opcao == 4) {
                jogando = false;
                mostrarDespedida();
            } else {
                System.out.println("  Essa opcao nao esta no menu. Digite 1, 2, 3 ou 4.");
                pausa(1800);
            }
        }
    }

    // ===================== OS DOIS MODOS DE JOGO =====================

    void jogarContraOComputador() {
        int numeroDoJogador = escolherLutador("JOGADOR 1");

        // o computador sempre pega o proximo lutador da lista.
        // se o jogador escolheu o ultimo, o computador volta para o primeiro.
        int numeroDoComputador = numeroDoJogador + 1;
        if (numeroDoComputador > lutadores.length) {
            numeroDoComputador = 1;
        }

        Lutador jogador = lutadores[numeroDoJogador - 1].criarCopia();
        Lutador computador = lutadores[numeroDoComputador - 1].criarCopia();

        System.out.println();
        System.out.println("  O computador vai lutar com " + computador.nome + "!");
        pausa(1800);

        // o true avisa a Arena que o lutador 2 e o computador
        Arena arena = new Arena(jogador, computador, true, teclado);
        arena.lutar();
    }

    void jogarContraOutroJogador() {
        int numero1 = escolherLutador("JOGADOR 1");
        int numero2 = escolherLutador("JOGADOR 2");

        Lutador jogador1 = lutadores[numero1 - 1].criarCopia();
        Lutador jogador2 = lutadores[numero2 - 1].criarCopia();

        // se os dois escolherem o mesmo numero, marcamos quem e quem no placar
        if (numero1 == numero2) {
            jogador1.nome = jogador1.nome + " P1";
            jogador2.nome = jogador2.nome + " P2";
        }

        // o false avisa a Arena que os dois lados sao jogadores de verdade
        Arena arena = new Arena(jogador1, jogador2, false, teclado);
        arena.lutar();
    }

    // ===================== ESCOLHER O LUTADOR =====================

    // Mostra a lista, pergunta o numero e devolve o numero escolhido.
    int escolherLutador(String quemEscolhe) {
        limparTela();
        mostrarAbertura();
        mostrarLista();

        System.out.println();
        System.out.println("  " + quemEscolhe + ", escolha seu lutador:");

        int numero = teclado.nextInt();

        // se digitar um numero que nao esta na lista, a gente avisa e pergunta de novo
        while (numero < 1 || numero > lutadores.length) {
            System.out.println("  Esse numero nao esta na lista. Escolha de 1 ate " + lutadores.length + ".");
            numero = teclado.nextInt();
        }

        System.out.println();
        System.out.println("  " + quemEscolhe + " vai lutar com " + lutadores[numero - 1].nome + ".");
        pausa(1200);

        return numero;
    }

    void verFichaDeUmLutador() {
        limparTela();
        mostrarAbertura();
        mostrarLista();

        System.out.println();
        System.out.println("  Escolha um lutador para ver a ficha completa:");

        int numero = teclado.nextInt();

        while (numero < 1 || numero > lutadores.length) {
            System.out.println("  Esse numero nao esta na lista. Escolha de 1 ate " + lutadores.length + ".");
            numero = teclado.nextInt();
        }

        System.out.println();
        System.out.println("  FICHA DE " + lutadores[numero - 1].nome.toUpperCase());
        System.out.println("  --------------------------------------------------");
        lutadores[numero - 1].ficha();
        System.out.println();
        System.out.println("  Digite 1 para voltar ao menu.");
        teclado.nextInt();
    }

    // Cada lutador sabe se mostrar na lista, entao aqui so percorremos o array.
    void mostrarLista() {
        System.out.println();
        System.out.println("  LUTADORES DISPONIVEIS");
        System.out.println("  --------------------------------------------------");
        System.out.println(cor.cinza + "   #   NOME       PAIS      SOCO  CHUTE  ESP   DEF   VEL   ESPECIAL" + cor.reset);

        for (int i = 0; i < lutadores.length; i++) {
            lutadores[i].mostrarNaLista(i + 1);
        }
    }

    // ===================== TELAS =====================

    void mostrarAbertura() {
        System.out.println();
        System.out.println(cor.amarelo + cor.negrito + "   ____   _____  ____   _____  _____  _____ " + cor.reset);
        System.out.println(cor.amarelo + cor.negrito + "  / ___| |_   _||  _ \\ | ____|| ____||_   _|" + cor.reset);
        System.out.println(cor.amarelo + cor.negrito + "  \\___ \\   | |  | |_) ||  _|  |  _|    | |  " + cor.reset);
        System.out.println(cor.amarelo + cor.negrito + "   ___) |  | |  |  _ < | |___ | |___   | |  " + cor.reset);
        System.out.println(cor.amarelo + cor.negrito + "  |____/   |_|  |_| \\_\\|_____||_____|  |_|  " + cor.reset);
        System.out.println(cor.vermelho + cor.negrito + "   _____  ___   ____  _   _  _____  _____  ____  " + cor.reset);
        System.out.println(cor.vermelho + cor.negrito + "  |  ___||_ _| / ___|| | | ||_   _|| ____||  _ \\ " + cor.reset);
        System.out.println(cor.vermelho + cor.negrito + "  | |_    | | | |  _ | |_| |  | |  |  _|  | |_) |" + cor.reset);
        System.out.println(cor.vermelho + cor.negrito + "  |  _|   | | | |_| ||  _  |  | |  | |___ |  _ < " + cor.reset);
        System.out.println(cor.vermelho + cor.negrito + "  |_|    |___| \\____||_| |_|  |_|  |_____||_| \\_\\" + cor.reset);
        System.out.println();
        System.out.println(cor.cinza + "  jogo de luta em java  -  programacao orientada a objetos" + cor.reset);
        System.out.println();
    }

    void mostrarDespedida() {
        limparTela();
        System.out.println();
        System.out.println("  ==================================================");
        System.out.println("   ATE A PROXIMA!");
        System.out.println("  ==================================================");
        System.out.println();
    }

    // ===================== FERRAMENTAS =====================

    void limparTela() {
        System.out.print(cor.escape + "[H" + cor.escape + "[J");
    }

    // O try/catch aqui e uma exigencia do Java para usar o Thread.sleep.
    void pausa(int milissegundos) {
        try {
            Thread.sleep(milissegundos);
        } catch (InterruptedException erro) {
            System.out.print("");
        }
    }
}
