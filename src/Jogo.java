import java.util.Scanner;

public class Jogo {

    // Atributos
    Lutador ryu;
    Lutador ken;
    Lutador chunLi;
    Lutador blanka;
    Lutador zangief;
    Lutador dhalsim;

    Scanner teclado;

    // Construtor
    public Jogo() {
        teclado = new Scanner(System.in);

        //                       nome       pais      estilo        golpe especial        soco chute esp def vel
        ryu     = new Lutador("Ryu",     "Japao",  "Karate",     "Hadouken",            14, 22, 45, 5, 6);
        ken     = new Lutador("Ken",     "EUA",    "Karate",     "Shoryuken",           16, 24, 48, 4, 7);
        chunLi  = new Lutador("Chun-Li", "China",  "Kung Fu",    "Spinning Bird Kick",  13, 20, 42, 4, 9);
        blanka  = new Lutador("Blanka",  "Brasil", "Selvagem",   "Electric Thunder",    17, 26, 50, 3, 6);
        zangief = new Lutador("Zangief", "Russia", "Luta Livre", "Spinning Piledriver", 19, 30, 55, 7, 2);
        dhalsim = new Lutador("Dhalsim", "India",  "Yoga",       "Yoga Flame",          14, 23, 46, 3, 8);
    }

    // Metodos

    void abrirMenu() {
        boolean continuar = true;

        while (continuar) {
            limparTela();
            mostrarAbertura();

            linhaDupla();
            System.out.println("               M E N U   P R I N C I P A L");
            linhaDupla();
            System.out.println();
            System.out.println("        [1]  Comecar a luta  (Jogador 1  vs  Jogador 2)");
            System.out.println("        [2]  Ver ficha de um lutador");
            System.out.println("        [3]  Sair");
            System.out.println();
            linhaDupla();
            System.out.print("        Escolha: ");

            int opcao = teclado.nextInt();

            if (opcao == 1) {
                jogar();
            } else if (opcao == 2) {
                verFicha();
            } else if (opcao == 3) {
                continuar = false;
                mostrarDespedida();
            } else {
                System.out.println("        Opcao invalida.");
                pausa(1200);
            }
        }
    }

    void jogar() {
        int numero1 = escolherLutador("JOGADOR 1");
        int numero2 = escolherLutador("JOGADOR 2");

        Lutador jogador1 = pegarLutador(numero1);
        Lutador jogador2 = pegarLutador(numero2);

        iniciarBatalha(jogador1, jogador2);
    }

    // A luta e melhor de 3: acaba quando alguem vence 2 rounds
    void iniciarBatalha(Lutador lutador1, Lutador lutador2) {
        mostrarVersus(lutador1, lutador2);

        int round = 1;

        while (lutador1.roundsVencidos < 2 && lutador2.roundsVencidos < 2) {
            // Os dois voltam com a vida cheia a cada round
            lutador1.prepararParaNovoRound();
            lutador2.prepararParaNovoRound();

            mostrarAnuncioDeRound(round);
            disputarRound(lutador1, lutador2);

            // Quem sobrou de pe ganhou o round
            if (lutador1.estaVivo()) {
                lutador1.roundsVencidos = lutador1.roundsVencidos + 1;
                mostrarFimDeRound(lutador1, lutador2);
            } else {
                lutador2.roundsVencidos = lutador2.roundsVencidos + 1;
                mostrarFimDeRound(lutador2, lutador1);
            }

            round = round + 1;
        }

        if (lutador1.roundsVencidos == 2) {
            mostrarVencedor(lutador1, lutador2);
        } else {
            mostrarVencedor(lutador2, lutador1);
        }

        voltarAoMenu();
    }

    // Um round vai ate a vida de um dos dois chegar a zero
    void disputarRound(Lutador lutador1, Lutador lutador2) {
        int turno = 1;

        while (lutador1.estaVivo() && lutador2.estaVivo()) {
            mostrarPlacar(lutador1, lutador2, turno);

            // Quem tem mais velocidade ataca primeiro
            if (lutador1.velocidade >= lutador2.velocidade) {
                executarTurno(lutador1, lutador2);

                if (lutador2.estaVivo() == false) {
                    break;
                }

                executarTurno(lutador2, lutador1);
            } else {
                executarTurno(lutador2, lutador1);

                if (lutador1.estaVivo() == false) {
                    break;
                }

                executarTurno(lutador1, lutador2);
            }

            turno = turno + 1;
        }
    }

    // Um turno so: o atacante escolhe uma acao e ela e executada
    void executarTurno(Lutador atacante, Lutador defensor) {
        atacante.limparEstado();

        mostrarMenuDeAtaque(atacante);
        int ataque = teclado.nextInt();

        linhaSimples();

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
            System.out.println("        Opcao invalida, " + atacante.nome + " ficou parado.");
        }

        linhaSimples();
        pausa(1500);
    }

    void mostrarMenuDeAtaque(Lutador lutador) {
        System.out.println();
        System.out.println("        >> Vez de " + lutador.nome + " <<");
        System.out.println("        [1] Soco      [2] Chute     [3] Defender");
        System.out.println("        [4] Provocar  [5] Especial (" + lutador.especial + ")");
        System.out.print("        Escolha: ");
    }

    int escolherLutador(String quemEscolhe) {
        limparTela();
        mostrarLista();
        System.out.print("        " + quemEscolhe + ", escolha de 1 a 6: ");
        int numero = teclado.nextInt();

        while (numero < 1 || numero > 6) {
            System.out.print("        Numero invalido. Escolha de 1 a 6: ");
            numero = teclado.nextInt();
        }

        return numero;
    }

    void verFicha() {
        limparTela();
        mostrarLista();
        System.out.print("        Escolha o lutador: ");
        int numero = teclado.nextInt();

        while (numero < 1 || numero > 6) {
            System.out.print("        Numero invalido. Escolha de 1 a 6: ");
            numero = teclado.nextInt();
        }

        Lutador lutador = pegarLutador(numero);

        limparTela();
        System.out.println();
        linhaDupla();
        System.out.println("               F I C H A   D O   L U T A D O R");
        linhaDupla();
        System.out.println();
        lutador.mostrarFicha();
        System.out.println();
        linhaDupla();

        voltarAoMenu();
    }

    // Devolve uma copia do lutador escolhido (para os dois jogadores
    // poderem pegar o mesmo personagem sem dividirem a mesma barra de vida)
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
        linhaDupla();
        System.out.println("               E S C O L H A   S E U   L U T A D O R");
        linhaDupla();
        System.out.println("        #   LUTADOR     PAIS     ESTILO       SOC CHU ESP DEF VEL");
        linhaSimples();
        ryu.mostrarNaLista(1);
        ken.mostrarNaLista(2);
        chunLi.mostrarNaLista(3);
        blanka.mostrarNaLista(4);
        zangief.mostrarNaLista(5);
        dhalsim.mostrarNaLista(6);
        linhaDupla();
        System.out.println();
    }

    void mostrarAbertura() {
        System.out.println();
        System.out.println("        SSSSS TTTTT RRRR  EEEEE EEEEE TTTTT");
        System.out.println("        S       T   R   R E     E       T");
        System.out.println("        SSSSS   T   RRRR  EEEE  EEEE    T");
        System.out.println("            S   T   R  R  E     E       T");
        System.out.println("        SSSSS   T   R   R EEEEE EEEEE   T");
        System.out.println();
        System.out.println("        FFFFF IIIII  GGGG H   H TTTTT EEEEE RRRR");
        System.out.println("        F       I   G     H   H   T   E     R   R");
        System.out.println("        FFFF    I   G  GG HHHHH   T   EEEE  RRRR");
        System.out.println("        F       I   G   G H   H   T   E     R  R");
        System.out.println("        F     IIIII  GGGG H   H   T   EEEEE R   R");
        System.out.println();
        System.out.println("             j o g o   d e   l u t a   e m   j a v a");
        System.out.println();
    }

    void mostrarVersus(Lutador lutador1, Lutador lutador2) {
        limparTela();
        System.out.println();
        linhaDupla();
        System.out.println();
        System.out.println("             " + completar(lutador1.nome, 15) + "  VS  " + lutador2.nome);
        System.out.println();
        linhaSimples();
        System.out.println("        PAIS       " + completar(lutador1.pais, 22) + "  " + lutador2.pais);
        System.out.println("        ESTILO     " + completar(lutador1.estilo, 22) + "  " + lutador2.estilo);
        System.out.println("        ESPECIAL   " + completar(lutador1.especial, 22) + "  " + lutador2.especial);
        linhaSimples();
        System.out.println();
        System.out.println("                    M E L H O R   D E   3   R O U N D S");
        System.out.println();
        linhaDupla();
        pausa(2500);
    }

    void mostrarAnuncioDeRound(int round) {
        System.out.println();
        linhaDupla();
        System.out.println("                        R O U N D   " + round);
        System.out.println("                          L U T E M !");
        linhaDupla();
        pausa(1500);
    }

    void mostrarPlacar(Lutador lutador1, Lutador lutador2, int turno) {
        System.out.println();
        linhaDupla();
        System.out.println("        TURNO " + turno + "     PLACAR:  "
                + lutador1.nome + " " + lutador1.roundsVencidos
                + "  x  " + lutador2.roundsVencidos + " " + lutador2.nome);
        linhaSimples();
        mostrarBarra(lutador1);
        mostrarBarra(lutador2);
        linhaDupla();
    }

    // Desenha a barra de vida com # e .
    void mostrarBarra(Lutador lutador) {
        int blocos = lutador.vida / 5; // 100 de vida viram 20 blocos
        String barra = "";

        for (int i = 0; i < 20; i++) {
            if (i < blocos) {
                barra = barra + "#";
            } else {
                barra = barra + ".";
            }
        }

        System.out.println("        " + completar(lutador.nome, 10) + "[" + barra + "]  " + lutador.vida + "/100");
    }

    void mostrarFimDeRound(Lutador vencedor, Lutador perdedor) {
        System.out.println();
        linhaDupla();
        System.out.println("        K . O . !   " + vencedor.nome + " venceu o round.");
        System.out.println("        PLACAR:  " + vencedor.nome + " " + vencedor.roundsVencidos
                + "  x  " + perdedor.roundsVencidos + " " + perdedor.nome);
        linhaDupla();
        pausa(2500);
    }

    void mostrarVencedor(Lutador vencedor, Lutador perdedor) {
        limparTela();
        System.out.println();
        linhaDupla();
        System.out.println("                    F I M   D A   L U T A");
        linhaDupla();
        System.out.println();
        System.out.println("             " + vencedor.nome + " VENCEU A LUTA!");
        System.out.println();
        System.out.println("             Placar final:  " + vencedor.roundsVencidos
                + "  x  " + perdedor.roundsVencidos);
        System.out.println("             " + perdedor.nome + " nao aguentou.");
        System.out.println();
        linhaDupla();
    }

    void mostrarDespedida() {
        limparTela();
        System.out.println();
        linhaDupla();
        System.out.println("        Ate a proxima! Obrigado por jogar.");
        linhaDupla();
        System.out.println();
    }

    // Coloca espacos no fim do texto ate ele ficar do tamanho pedido,
    // para as colunas ficarem alinhadas
    String completar(String texto, int tamanho) {
        String resultado = texto;

        while (resultado.length() < tamanho) {
            resultado = resultado + " ";
        }

        return resultado;
    }

    void linhaSimples() {
        System.out.println("        ----------------------------------------------------------");
    }

    void linhaDupla() {
        System.out.println("        ==========================================================");
    }

    void voltarAoMenu() {
        System.out.println();
        System.out.print("        Digite 1 para voltar ao menu: ");
        teclado.nextInt();
    }

    void limparTela() {
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }

    void pausa(int milissegundos) {
        // O try/catch aqui e obrigatorio, o Java nao deixa usar Thread.sleep sem ele
        try {
            Thread.sleep(milissegundos);
        } catch (InterruptedException e) {
            System.out.println("        Erro de pausa.");
        }
    }
}
