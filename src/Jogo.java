import java.util.Scanner;

// Esta classe e o jogo em si: ela guarda os lutadores disponiveis, mostra o
// menu, deixa o jogador escolher o personagem e manda a Arena comecar a luta.
public class Jogo {

    // ===================== ATRIBUTOS =====================
    Lutador[] lutadores;    // todos os lutadores do jogo guardados juntos
    Scanner teclado;
    Cores cor;

    // ===================== CONSTRUTOR =====================
    // E aqui que os 6 lutadores sao criados. Para colocar mais um, e so
    // aumentar o numero do new Lutador[6] e acrescentar outra linha.
    public Jogo() {
        this.teclado = new Scanner(System.in);
        this.cor = new Cores();

        this.lutadores = new Lutador[6];
        //                             nome       pais      estilo        especial               for def vel
        this.lutadores[0] = new Lutador("Ryu",     "Japao",  "Karate",     "HADOUKEN",             11,  5,  6);
        this.lutadores[1] = new Lutador("Ken",     "EUA",    "Karate",     "SHORYUKEN",            13,  4,  7);
        this.lutadores[2] = new Lutador("Chun-Li", "China",  "Kung Fu",    "SPINNING BIRD KICK",    9,  4,  9);
        this.lutadores[3] = new Lutador("Blanka",  "Brasil", "Selvagem",   "ELECTRIC THUNDER",     14,  3,  6);
        this.lutadores[4] = new Lutador("Zangief", "Russia", "Luta Livre", "SPINNING PILEDRIVER",  16,  7,  2);
        this.lutadores[5] = new Lutador("Dhalsim", "India",  "Yoga",       "YOGA FLAME",           10,  3,  8);
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

            int opcao = lerNumero(1, 4);

            if (opcao == 1) {
                jogarContraOComputador();
            } else if (opcao == 2) {
                jogarContraOutroJogador();
            } else if (opcao == 3) {
                verFichaDeUmLutador();
            } else {
                jogando = false;
                mostrarDespedida();
            }
        }
    }

    // ===================== OS DOIS MODOS DE JOGO =====================

    void jogarContraOComputador() {
        Lutador jogador = escolherLutador("JOGADOR 1");

        // o computador sorteia um lutador qualquer
        int numeroSorteado = 1 + (int) (Math.random() * lutadores.length);
        Lutador computador = lutadores[numeroSorteado - 1].criarCopia();

        // se der o mesmo personagem, marcamos quem e quem no placar
        if (jogador.nome.equals(computador.nome)) {
            jogador.nome = jogador.nome + " P1";
            computador.nome = computador.nome + " CPU";
        }

        System.out.println();
        System.out.println("  O computador escolheu " + computador.nome + "!");
        pausa(1500);

        // o true avisa a Arena que o lutador 2 e o computador
        Arena arena = new Arena(jogador, computador, true, teclado);
        arena.lutar();
    }

    void jogarContraOutroJogador() {
        Lutador jogador1 = escolherLutador("JOGADOR 1");
        Lutador jogador2 = escolherLutador("JOGADOR 2");

        // se os dois escolherem o mesmo personagem, marcamos quem e quem
        if (jogador1.nome.equals(jogador2.nome)) {
            jogador1.nome = jogador1.nome + " P1";
            jogador2.nome = jogador2.nome + " P2";
        }

        // o false avisa a Arena que os dois lados sao jogadores de verdade
        Arena arena = new Arena(jogador1, jogador2, false, teclado);
        arena.lutar();
    }

    // ===================== ESCOLHER O LUTADOR =====================

    // Mostra a lista, pergunta o numero e devolve uma COPIA do escolhido.
    Lutador escolherLutador(String quemEscolhe) {
        limparTela();
        mostrarAbertura();
        mostrarLista();

        System.out.println();
        System.out.println("  " + quemEscolhe + ", escolha seu lutador:");

        int numero = lerNumero(1, lutadores.length);
        Lutador escolhido = lutadores[numero - 1].criarCopia();

        System.out.println();
        System.out.println("  " + quemEscolhe + " vai lutar com " + escolhido.nome + ".");
        pausa(1200);

        return escolhido;
    }

    void verFichaDeUmLutador() {
        limparTela();
        mostrarAbertura();
        mostrarLista();

        System.out.println();
        System.out.println("  Escolha um lutador para ver a ficha completa:");

        int numero = lerNumero(1, lutadores.length);

        System.out.println();
        System.out.println("  FICHA DE " + lutadores[numero - 1].nome.toUpperCase());
        System.out.println("  --------------------------------------------------");
        lutadores[numero - 1].ficha();
        System.out.println();
        System.out.println("  Digite 1 para voltar ao menu.");
        lerNumero(1, 1);
    }

    // Cada lutador sabe se mostrar na lista, entao aqui so percorremos o array.
    void mostrarLista() {
        System.out.println();
        System.out.println("  LUTADORES DISPONIVEIS");
        System.out.println("  --------------------------------------------------");
        System.out.println(cor.cinza + "   #   NOME       PAIS       ESTILO        FOR  DEF  VEL  ESPECIAL" + cor.reset);

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

    // Le um numero do teclado e so aceita se estiver entre minimo e maximo.
    int lerNumero(int minimo, int maximo) {
        int numero = 0;
        boolean numeroCerto = false;

        while (numeroCerto == false) {
            System.out.print("  > ");

            if (teclado.hasNextInt()) {
                numero = teclado.nextInt();
                if (numero >= minimo && numero <= maximo) {
                    numeroCerto = true;
                }
            } else {
                teclado.next();   // joga fora o que foi digitado de errado
            }

            if (numeroCerto == false) {
                System.out.println("  Digite um numero de " + minimo + " ate " + maximo + ".");
            }
        }
        return numero;
    }

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
