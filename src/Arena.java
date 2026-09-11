import java.util.Scanner;

// Esta classe e a luta. Ela guarda os dois lutadores, controla os rounds e
// os turnos, desenha o placar na tela e decide quem venceu.
public class Arena {

    // ===================== ATRIBUTOS =====================
    Lutador lutador1;
    Lutador lutador2;
    boolean lutador2EhComputador;

    int round;
    int turno;

    Scanner teclado;
    Cores cor;
    String linhaDupla;              // a linha de ==== do placar
    String linhaSimples;            // a linha de ---- das molduras

    // ===================== CONSTRUTOR =====================
    public Arena(Lutador lutador1, Lutador lutador2, boolean lutador2EhComputador, Scanner teclado) {
        this.lutador1 = lutador1;
        this.lutador2 = lutador2;
        this.lutador2EhComputador = lutador2EhComputador;
        this.teclado = teclado;

        this.round = 1;
        this.turno = 1;
        this.cor = new Cores();
        this.linhaDupla = repetir("=", 62);
        this.linhaSimples = repetir("-", 62);
    }

    // ===================== A LUTA INTEIRA =====================

    void lutar() {
        mostrarVersus();
        pausa(3500);

        // a luta continua enquanto ninguem chegou a 2 rounds vencidos
        while (lutador1.roundsVencidos < 2 && lutador2.roundsVencidos < 2) {
            disputarRound();
            this.round = this.round + 1;
        }

        mostrarVencedor();
    }

    // ===================== UM ROUND =====================

    void disputarRound() {
        lutador1.prepararParaORound();
        lutador2.prepararParaORound();
        mostrarAnuncioDeRound();

        this.turno = 1;

        // quem tem mais velocidade comeca o round
        Lutador primeiro = lutador1;
        Lutador segundo = lutador2;
        boolean primeiroEhComputador = false;
        boolean segundoEhComputador = lutador2EhComputador;

        if (lutador2.velocidade > lutador1.velocidade) {
            primeiro = lutador2;
            segundo = lutador1;
            primeiroEhComputador = lutador2EhComputador;
            segundoEhComputador = false;
        }

        // o round acaba quando a vida de um dos dois chega a zero
        while (lutador1.estaVivo() && lutador2.estaVivo()) {

            jogarTurno(primeiro, segundo, primeiroEhComputador);

            if (segundo.estaVivo() == false) {
                break;   // ele caiu, entao nem chega a jogar
            }

            jogarTurno(segundo, primeiro, segundoEhComputador);

            this.turno = this.turno + 1;
        }

        mostrarFimDeRound();
    }

    // ===================== UM TURNO =====================

    void jogarTurno(Lutador atacante, Lutador defensor, boolean ehComputador) {
        // a guarda levantada e a guarda aberta valem so ate o lutador agir de novo
        atacante.limparPostura();

        int acao;

        if (ehComputador) {
            acao = escolherAcaoDoComputador(atacante, defensor);
            System.out.println("  " + atacante.nome + " (computador) se movimenta...");
            pausa(1000);
        } else {
            mostrarPlacarLimpo();           // desenha o placar e limpa o resto da tela
            mostrarMenuDeAcoes(atacante);

            acao = teclado.nextInt();

            // se digitar um numero que nao esta no menu, a gente avisa e pergunta de novo
            while (acao < 1 || acao > 5) {
                System.out.println("  Essa opcao nao esta no menu. Digite de 1 ate 5.");
                acao = teclado.nextInt();
            }

            // o especial so sai com a furia cheia
            while (acao == 5 && atacante.furia < 100) {
                System.out.println("  A furia de " + atacante.nome + " esta em "
                    + atacante.furia + "/100. Escolha outra acao.");
                acao = teclado.nextInt();
            }

            mostrarPlacarLimpo();           // apaga o menu da tela
        }

        executarAcao(acao, atacante, defensor);
    }

    void executarAcao(int acao, Lutador atacante, Lutador defensor) {
        // guardamos a vida do adversario ANTES do golpe, para a barra
        // poder descer aos poucos na animacao
        int vidaAntes = defensor.vida;

        if (acao == 1) {
            atacante.soco(defensor);

        } else if (acao == 2) {
            atacante.chute(defensor);

        } else if (acao == 3) {
            atacante.defender();

        } else if (acao == 4) {
            atacante.provocar();

        } else {
            // o golpe especial tem uma entrada mais caprichada
            caixa(atacante.nome.toUpperCase() + " USOU " + atacante.especial, cor.roxo);
            pausa(900);
            atacante.golpeEspecial(defensor);
        }

        // se o adversario perdeu vida, a barra desce animada
        if (defensor.vida < vidaAntes) {
            animarDano(defensor, vidaAntes);
        } else {
            desenharPlacar();
        }

        pausa(1700);
    }

    // ===================== O COMPUTADOR ESCOLHENDO =====================
    // Nao tem sorteio: o computador segue sempre as mesmas regras.

    int escolherAcaoDoComputador(Lutador computador, Lutador inimigo) {
        // com a furia cheia, o especial vem na hora
        if (computador.furia >= 100) {
            return 5;
        }
        // com pouca vida, ele se protege
        if (computador.vida < 30) {
            return 3;
        }
        // se o inimigo esta quase caindo, ele parte pra cima
        if (inimigo.vida <= 25) {
            return 2;
        }
        // no resto do tempo ele vai alternando soco e chute
        if (turno % 2 == 0) {
            return 2;
        } else {
            return 1;
        }
    }

    // ===================== DESENHAR A LUTA =====================

    // Desenha o placar dos dois lutadores. Sao sempre 7 linhas, nem uma a mais,
    // por isso da para redesenhar por cima sem baguncar o texto que esta embaixo.
    void desenharPlacar() {
        voltarAoTopo();

        System.out.println();
        System.out.println(cor.cinza + "  " + linhaDupla + cor.reset);
        System.out.println("  " + cor.negrito + "ROUND " + round + cor.reset
            + cor.cinza + completar("   turno " + turno + "   melhor de 3 rounds", 50) + cor.reset);
        System.out.println(cor.cinza + "  " + linhaDupla + cor.reset);
        lutador1.mostrarBarras();
        lutador2.mostrarBarras();
        System.out.println(cor.cinza + "  " + linhaDupla + cor.reset);
    }

    // Desenha o placar e apaga tudo o que estiver embaixo dele.
    void mostrarPlacarLimpo() {
        desenharPlacar();
        apagarORestante();
        System.out.println();
    }

    // A barra de vida desce aos poucos ate chegar no valor novo.
    // O truque e desenhar o placar 7 vezes seguidas, cada uma com um valor
    // entre a vida antiga e a vida nova, com uma pausa entre elas.
    void animarDano(Lutador quemLevou, int vidaAntes) {
        int vidaDepois = quemLevou.vida;

        for (int i = 1; i <= 7; i++) {
            quemLevou.vida = vidaAntes - (((vidaAntes - vidaDepois) * i) / 7);
            desenharPlacar();
            pausa(60);
        }

        quemLevou.vida = vidaDepois;   // garante que o valor final fique certo
        desenharPlacar();
    }

    // ===================== AS TELAS =====================

    void mostrarVersus() {
        limparTela();
        System.out.println();
        caixa(lutador1.nome.toUpperCase() + "  contra  " + lutador2.nome.toUpperCase(), cor.amarelo);
        System.out.println();
        mostrarResumo(lutador1, cor.azul);
        System.out.println();
        mostrarResumo(lutador2, cor.vermelho);
        System.out.println();

        // quem for mais rapido comeca o round
        if (lutador1.velocidade > lutador2.velocidade) {
            System.out.println("  " + lutador1.nome + " e mais rapido e comeca atacando.");
        } else {
            System.out.println("  " + lutador2.nome + " e mais rapido e comeca atacando.");
        }
    }

    // Resumo curto de um lutador, usado na tela de apresentacao da luta.
    void mostrarResumo(Lutador lutador, String corDoLado) {
        System.out.println("  " + corDoLado + cor.negrito + completar(lutador.nome.toUpperCase(), 14) + cor.reset
            + cor.cinza + lutador.pais + " - " + lutador.estilo + " - " + lutador.especial + cor.reset);
        System.out.println("  " + cor.cinza + "soco " + lutador.danoDoSoco
            + "   chute " + lutador.danoDoChute
            + "   especial " + lutador.danoDoEspecial
            + "   defesa " + lutador.defesa
            + "   velocidade " + lutador.velocidade + cor.reset);
    }

    void mostrarAnuncioDeRound() {
        limparTela();
        System.out.println();
        System.out.println();
        caixa("R O U N D   " + round, cor.branco);
        pausa(800);
        System.out.println();
        caixa("L U T E M !", cor.vermelho);
        pausa(900);
    }

    void mostrarMenuDeAcoes(Lutador lutador) {
        System.out.println("  " + cor.negrito + lutador.nome.toUpperCase() + cor.reset + ", escolha o que fazer:");
        System.out.println("   " + cor.amarelo + "[1]" + cor.reset + " Soco      " + cor.cinza + "tira " + lutador.danoDoSoco + " e mantem a guarda" + cor.reset);
        System.out.println("   " + cor.amarelo + "[2]" + cor.reset + " Chute     " + cor.cinza + "tira " + lutador.danoDoChute + ", mas abre a guarda" + cor.reset);
        System.out.println("   " + cor.amarelo + "[3]" + cor.reset + " Defender  " + cor.cinza + "corta pela metade o dano do proximo golpe" + cor.reset);
        System.out.println("   " + cor.amarelo + "[4]" + cor.reset + " Provocar  " + cor.cinza + "enche muito a furia, mas abre a guarda" + cor.reset);

        // o especial so aparece liberado quando a furia chega em 100
        if (lutador.furia >= 100) {
            System.out.println("   " + cor.amarelo + "[5]" + cor.reset + " Especial  "
                + cor.verde + "PRONTO - " + lutador.especial + " tira " + lutador.danoDoEspecial + cor.reset);
        } else {
            System.out.println("   " + cor.amarelo + "[5]" + cor.reset + " Especial  "
                + cor.cinza + "precisa de 100 de furia (voce tem " + lutador.furia + ")" + cor.reset);
        }
    }

    void mostrarFimDeRound() {
        // quem continuou de pe ganhou o round
        Lutador vencedor = lutador1;
        Lutador perdedor = lutador2;

        if (lutador1.estaVivo() == false) {
            vencedor = lutador2;
            perdedor = lutador1;
        }

        vencedor.vencerRound();
        mostrarPlacarLimpo();

        caixa("K . O .", cor.vermelho);
        System.out.println();
        System.out.println("  " + cor.negrito + vencedor.nome.toUpperCase() + cor.reset
            + " derrubou " + perdedor.nome.toUpperCase() + " e venceu o round.");
        System.out.println("  Placar da luta: " + lutador1.nome + " " + lutador1.roundsVencidos
            + "  x  " + lutador2.roundsVencidos + " " + lutador2.nome);
        pausa(3500);
    }

    void mostrarVencedor() {
        // quem ganhou mais rounds leva a luta
        Lutador vencedor = lutador1;
        Lutador perdedor = lutador2;

        if (lutador2.roundsVencidos > lutador1.roundsVencidos) {
            vencedor = lutador2;
            perdedor = lutador1;
        }

        limparTela();
        System.out.println();
        caixa("V E N C E D O R :  " + vencedor.nome.toUpperCase(), cor.amarelo);
        System.out.println();
        System.out.println("  " + cor.negrito + vencedor.nome.toUpperCase() + cor.reset
            + " venceu a luta por " + vencedor.roundsVencidos + " a " + perdedor.roundsVencidos + ".");
        System.out.println();
        System.out.println("  " + cor.negrito + "ESTATISTICAS DA LUTA" + cor.reset);
        System.out.println(cor.cinza + "  " + linhaSimples + cor.reset);
        lutador1.mostrarEstatisticas();
        lutador2.mostrarEstatisticas();
        System.out.println(cor.cinza + "  " + linhaSimples + cor.reset);

        System.out.println();
        System.out.println("  Digite 1 para voltar ao menu.");
        teclado.nextInt();
    }

    // Moldura usada nos momentos importantes: ROUND, LUTEM, K.O., VENCEDOR.
    void caixa(String texto, String corDaCaixa) {
        System.out.println(corDaCaixa + "  +" + linhaSimples + "+" + cor.reset);
        System.out.println(corDaCaixa + "  |" + cor.negrito + completar("  " + texto, 62) + cor.reset
            + corDaCaixa + "|" + cor.reset);
        System.out.println(corDaCaixa + "  +" + linhaSimples + "+" + cor.reset);
    }

    // ===================== FERRAMENTAS =====================

    // Manda o cursor para o canto de cima da tela, para desenhar por cima
    // do que ja estava la. E isso que faz a animacao nao ficar piscando.
    void voltarAoTopo() {
        System.out.print(cor.escape + "[H");
    }

    // Apaga tudo o que estiver abaixo do cursor.
    void apagarORestante() {
        System.out.print(cor.escape + "[J");
    }

    void limparTela() {
        voltarAoTopo();
        apagarORestante();
    }

    // Para o jogo por alguns milesimos de segundo.
    // O try/catch aqui e uma exigencia do Java para usar o Thread.sleep.
    void pausa(int milissegundos) {
        try {
            Thread.sleep(milissegundos);
        } catch (InterruptedException erro) {
            System.out.print("");
        }
    }

    // Repete um pedaco de texto varias vezes. repetir("=", 3) devolve "===".
    String repetir(String pedaco, int vezes) {
        String resultado = "";
        for (int i = 0; i < vezes; i++) {
            resultado = resultado + pedaco;
        }
        return resultado;
    }

    // Poe espacos depois do texto ate ele ficar do tamanho pedido.
    String completar(String texto, int tamanho) {
        while (texto.length() < tamanho) {
            texto = texto + " ";
        }
        return texto;
    }
}
