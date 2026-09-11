import java.util.Scanner;

// Esta classe e o jogo em si: ela guarda os lutadores disponiveis, mostra o
// menu, deixa o jogador escolher o personagem e manda a Arena comecar a luta.
public class Jogo {

    // ===================== ATRIBUTOS =====================
    // os seis lutadores do jogo, cada um na sua variavel, do mesmo jeito
    // que a gente criou o snorlax, o pikachu e o blastoise na aula
    Lutador ryu;
    Lutador ken;
    Lutador chunLi;
    Lutador blanka;
    Lutador zangief;
    Lutador dhalsim;

    int quantidadeDeLutadores;

    Scanner teclado;

    // codigos de cor do terminal. o terminal pinta o texto quando recebe o
    // caractere numero 27 (que tem o nome de "escape") seguido de um codigo.
    String escape;
    String reset;      // volta o texto para a cor normal
    String negrito;
    String vermelho;
    String amarelo;
    String cinza;

    // ===================== CONSTRUTOR =====================
    // E aqui que os 6 lutadores sao criados, cada um com o dano dos seus
    // golpes ja definido.
    public Jogo() {
        this.teclado = new Scanner(System.in);
        this.quantidadeDeLutadores = 6;

        //                    nome       pais      estilo        especial              soco chute esp def vel
        this.ryu     = new Lutador("Ryu",     "Japao",  "Karate",     "HADOUKEN",             14,  22,  45,  5,  6);
        this.ken     = new Lutador("Ken",     "EUA",    "Karate",     "SHORYUKEN",            16,  24,  48,  4,  7);
        this.chunLi  = new Lutador("Chun-Li", "China",  "Kung Fu",    "SPINNING BIRD KICK",   13,  20,  42,  4,  9);
        this.blanka  = new Lutador("Blanka",  "Brasil", "Selvagem",   "ELECTRIC THUNDER",     17,  26,  50,  3,  6);
        this.zangief = new Lutador("Zangief", "Russia", "Luta Livre", "SPINNING PILEDRIVER",  19,  30,  55,  7,  2);
        this.dhalsim = new Lutador("Dhalsim", "India",  "Yoga",       "YOGA FLAME",           14,  23,  46,  3,  8);

        this.escape   = "" + (char) 27;
        this.reset    = this.escape + "[0m";
        this.negrito  = this.escape + "[1m";
        this.vermelho = this.escape + "[91m";
        this.amarelo  = this.escape + "[93m";
        this.cinza    = this.escape + "[90m";
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
            System.out.println("   " + amarelo + "[1]" + reset + " Jogador contra o computador");
            System.out.println("   " + amarelo + "[2]" + reset + " Jogador contra jogador");
            System.out.println("   " + amarelo + "[3]" + reset + " Ver a ficha de um lutador");
            System.out.println("   " + amarelo + "[4]" + reset + " Sair do jogo");
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
        if (numeroDoComputador > quantidadeDeLutadores) {
            numeroDoComputador = 1;
        }

        Lutador jogador = pegarLutador(numeroDoJogador);
        Lutador computador = pegarLutador(numeroDoComputador);

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

        Lutador jogador1 = pegarLutador(numero1);
        Lutador jogador2 = pegarLutador(numero2);

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

    // Devolve uma copia do lutador que tem aquele numero na lista.
    // E copia porque, sem ela, dois jogadores escolhendo o mesmo personagem
    // estariam mexendo no MESMO objeto e dividindo a mesma barra de vida.
    Lutador pegarLutador(int numero) {
        if (numero == 1) {
            return ryu.criarCopia();
        } else if (numero == 2) {
            return ken.criarCopia();
        } else if (numero == 3) {
            return chunLi.criarCopia();
        } else if (numero == 4) {
            return blanka.criarCopia();
        } else if (numero == 5) {
            return zangief.criarCopia();
        } else {
            return dhalsim.criarCopia();
        }
    }

    // Mostra a lista, pergunta o numero e devolve o numero escolhido.
    int escolherLutador(String quemEscolhe) {
        limparTela();
        mostrarAbertura();
        mostrarLista();

        System.out.println();
        System.out.println("  " + quemEscolhe + ", escolha seu lutador:");

        int numero = teclado.nextInt();

        // se digitar um numero que nao esta na lista, a gente avisa e pergunta de novo
        while (numero < 1 || numero > quantidadeDeLutadores) {
            System.out.println("  Esse numero nao esta na lista. Escolha de 1 ate " + quantidadeDeLutadores + ".");
            numero = teclado.nextInt();
        }

        Lutador escolhido = pegarLutador(numero);
        System.out.println();
        System.out.println("  " + quemEscolhe + " vai lutar com " + escolhido.nome + ".");
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

        while (numero < 1 || numero > quantidadeDeLutadores) {
            System.out.println("  Esse numero nao esta na lista. Escolha de 1 ate " + quantidadeDeLutadores + ".");
            numero = teclado.nextInt();
        }

        Lutador escolhido = pegarLutador(numero);

        System.out.println();
        System.out.println("  FICHA DE " + escolhido.nome.toUpperCase());
        System.out.println("  --------------------------------------------------");
        escolhido.ficha();
        System.out.println();
        System.out.println("  Digite 1 para voltar ao menu.");
        teclado.nextInt();
    }

    // Cada lutador sabe se mostrar na lista, entao aqui e so mandar
    // cada um deles se mostrar, um embaixo do outro.
    void mostrarLista() {
        System.out.println();
        System.out.println("  LUTADORES DISPONIVEIS");
        System.out.println("  --------------------------------------------------");
        System.out.println(cinza + "   #   NOME       PAIS      SOCO  CHUTE  ESP   DEF   VEL   ESPECIAL" + reset);

        ryu.mostrarNaLista(1);
        ken.mostrarNaLista(2);
        chunLi.mostrarNaLista(3);
        blanka.mostrarNaLista(4);
        zangief.mostrarNaLista(5);
        dhalsim.mostrarNaLista(6);
    }

    // ===================== TELAS =====================

    void mostrarAbertura() {
        System.out.println();
        System.out.println(amarelo + negrito + "   ____   _____  ____   _____  _____  _____ " + reset);
        System.out.println(amarelo + negrito + "  / ___| |_   _||  _ \\ | ____|| ____||_   _|" + reset);
        System.out.println(amarelo + negrito + "  \\___ \\   | |  | |_) ||  _|  |  _|    | |  " + reset);
        System.out.println(amarelo + negrito + "   ___) |  | |  |  _ < | |___ | |___   | |  " + reset);
        System.out.println(amarelo + negrito + "  |____/   |_|  |_| \\_\\|_____||_____|  |_|  " + reset);
        System.out.println(vermelho + negrito + "   _____  ___   ____  _   _  _____  _____  ____  " + reset);
        System.out.println(vermelho + negrito + "  |  ___||_ _| / ___|| | | ||_   _|| ____||  _ \\ " + reset);
        System.out.println(vermelho + negrito + "  | |_    | | | |  _ | |_| |  | |  |  _|  | |_) |" + reset);
        System.out.println(vermelho + negrito + "  |  _|   | | | |_| ||  _  |  | |  | |___ |  _ < " + reset);
        System.out.println(vermelho + negrito + "  |_|    |___| \\____||_| |_|  |_|  |_____||_| \\_\\" + reset);
        System.out.println();
        System.out.println(cinza + "  jogo de luta em java  -  programacao orientada a objetos" + reset);
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
        System.out.print(escape + "[H" + escape + "[J");
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
