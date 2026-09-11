import java.util.Scanner;

// Esta classe e a luta. Ela guarda os dois lutadores, controla os rounds e
// os turnos, desenha a tela da luta e decide quem venceu.
public class Arena {

    // ===================== ATRIBUTOS =====================
    Lutador lutador1;               // fica do lado esquerdo da tela
    Lutador lutador2;               // fica do lado direito da tela
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

        // um lutador olha para o outro
        this.lutador1.olhandoParaDireita = true;
        this.lutador2.olhandoParaDireita = false;
    }

    // ===================== A LUTA INTEIRA =====================

    void lutar() {
        mostrarVersus();
        pausa(3000);

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

        // o round acaba quando a vida de um dos dois chega a zero
        while (lutador1.estaVivo() && lutador2.estaVivo()) {

            // primeiro age o lutador da esquerda
            jogarTurno(lutador1, lutador2, false);

            if (lutador2.estaVivo() == false) {
                break;   // o lutador 2 caiu, entao nem chega a jogar
            }

            // depois age o lutador da direita
            jogarTurno(lutador2, lutador1, lutador2EhComputador);

            this.turno = this.turno + 1;
        }

        mostrarFimDeRound();
    }

    // ===================== UM TURNO =====================

    void jogarTurno(Lutador atacante, Lutador defensor, boolean ehComputador) {
        // a guarda e a provocacao valem so ate o proprio lutador agir de novo
        atacante.limparPostura();

        int acao;

        if (ehComputador) {
            acao = escolherAcaoDoComputador(atacante, defensor);
            System.out.println("  " + atacante.nome + " (computador) se movimenta...");
            pausa(900);
        } else {
            mostrarCena();                  // desenha a luta e limpa o resto da tela
            mostrarMenuDeAcoes(atacante);
            acao = lerNumero(1, 5);

            // nao deixa usar o especial sem a furia cheia
            while (acao == 5 && atacante.furia < 100) {
                System.out.println("  A furia de " + atacante.nome + " esta em "
                    + atacante.furia + "/100. Escolha outra acao.");
                acao = lerNumero(1, 5);
            }

            mostrarCena();                  // apaga o menu da tela
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
            // o golpe especial tem uma entrada mais caprichada:
            // o lutador se concentra e depois o golpe atravessa a tela
            atacante.pose = "especial";
            desenharCena(-1);
            pausa(500);

            // se quem atacou foi o lutador da esquerda, o golpe voa para a direita
            animarEspecial(atacante.olhandoParaDireita);

            atacante.golpeEspecial(defensor);
        }

        // se o adversario perdeu vida, a barra desce animada;
        // se nao perdeu (defendeu, errou, provocou), so redesenhamos a cena
        if (defensor.vida < vidaAntes) {
            animarDano(defensor, vidaAntes);
        } else {
            desenharCena(-1);
        }

        pausa(1600);
    }

    // ===================== O COMPUTADOR ESCOLHENDO =====================

    int escolherAcaoDoComputador(Lutador computador, Lutador inimigo) {
        // com a furia cheia, o especial vem na hora
        if (computador.furia >= 100) {
            return 5;
        }
        // com pouca vida, ele costuma se proteger
        if (computador.vida < 30 && sorteio(1, 100) <= 45) {
            return 3;
        }
        // se o inimigo esta quase caindo, ele parte pra cima
        if (inimigo.vida <= 25) {
            return 2;
        }

        // no resto do tempo ele sorteia o que fazer
        int sorte = sorteio(1, 100);

        if (sorte <= 40) {
            return 1;    // soco
        } else if (sorte <= 72) {
            return 2;    // chute
        } else if (sorte <= 90) {
            return 3;    // defender
        } else {
            return 4;    // provocar
        }
    }

    int sorteio(int minimo, int maximo) {
        return minimo + (int) (Math.random() * (maximo - minimo + 1));
    }

    // ===================== DESENHAR A LUTA =====================

    // Desenha o placar e os dois bonecos. Sao sempre 14 linhas, nem uma a mais,
    // por isso da para redesenhar por cima sem baguncar o texto que esta embaixo.
    // "posicaoDoEspecial" e a coluna onde o golpe especial esta voando;
    // quando vale -1 e porque nao tem golpe nenhum voando.
    void desenharCena(int posicaoDoEspecial) {
        voltarAoTopo();

        System.out.println();
        System.out.println(cor.cinza + "  " + linhaDupla + cor.reset);
        System.out.println("  " + cor.negrito + "ROUND " + round + cor.reset
            + cor.cinza + completar("   turno " + turno + "   melhor de 3 rounds", 50) + cor.reset);
        System.out.println(cor.cinza + "  " + linhaDupla + cor.reset);
        lutador1.mostrarBarras();
        lutador2.mostrarBarras();
        System.out.println(cor.cinza + "  " + linhaDupla + cor.reset);
        System.out.println();

        // os dois bonecos, um de frente para o outro
        String[] esquerda = lutador1.desenho();
        String[] direita = lutador2.desenho();

        for (int i = 0; i < 4; i++) {
            String meio = repetir(" ", 38);

            // o golpe especial voa na altura dos bracos, que e a linha 1
            if (i == 1 && posicaoDoEspecial >= 0) {
                meio = repetir(" ", posicaoDoEspecial) + "(*)"
                     + repetir(" ", 38 - posicaoDoEspecial - 3);
            }

            System.out.println("  " + cor.azul + esquerda[i] + cor.reset
                             + meio + cor.vermelho + direita[i] + cor.reset);
        }

        System.out.println("  " + cor.cinza + repetir("_", 62) + cor.reset);
        System.out.println();
    }

    // Desenha a luta e apaga tudo o que estiver embaixo dela.
    void mostrarCena() {
        desenharCena(-1);
        apagarORestante();
    }

    // ===================== AS ANIMACOES =====================
    // As duas fazem a mesma coisa: desenham a cena varias vezes seguidas,
    // mudando um pouquinho a cada vez, com uma pausa no meio.

    // A barra de vida desce aos poucos ate chegar no valor novo.
    void animarDano(Lutador quemLevou, int vidaAntes) {
        int vidaDepois = quemLevou.vida;

        for (int i = 1; i <= 7; i++) {
            // vai descendo a vida de pouquinho em pouquinho
            quemLevou.vida = vidaAntes - (((vidaAntes - vidaDepois) * i) / 7);
            desenharCena(-1);
            pausa(60);
        }

        quemLevou.vida = vidaDepois;   // garante que o valor final fique certo
        desenharCena(-1);
    }

    // O golpe especial atravessa a tela de um lutador ate o outro.
    void animarEspecial(boolean vaiDaEsquerda) {
        for (int passo = 0; passo <= 35; passo = passo + 3) {
            int coluna = passo;

            if (vaiDaEsquerda == false) {
                coluna = 35 - passo;   // vindo da direita, anda ao contrario
            }

            desenharCena(coluna);
            pausa(45);
        }
    }

    // ===================== AS TELAS =====================

    void mostrarVersus() {
        limparTela();
        System.out.println();
        caixa(lutador1.nome.toUpperCase() + "  contra  " + lutador2.nome.toUpperCase(), cor.amarelo);
        System.out.println();

        String[] esquerda = lutador1.desenho();
        String[] direita = lutador2.desenho();
        for (int i = 0; i < 4; i++) {
            System.out.println("  " + cor.azul + esquerda[i] + cor.reset
                + repetir(" ", 38) + cor.vermelho + direita[i] + cor.reset);
        }
        System.out.println("  " + cor.cinza + repetir("_", 62) + cor.reset);

        System.out.println();
        mostrarResumo(lutador1, cor.azul);
        System.out.println();
        mostrarResumo(lutador2, cor.vermelho);
        System.out.println();
    }

    // Resumo curto de um lutador, usado na tela de apresentacao da luta.
    void mostrarResumo(Lutador lutador, String corDoLado) {
        System.out.println("  " + corDoLado + cor.negrito + completar(lutador.nome.toUpperCase(), 14) + cor.reset
            + cor.cinza + lutador.pais + " - " + lutador.estilo + " - " + lutador.especial + cor.reset);
        System.out.println("  " + cor.cinza + "forca " + lutador.forca
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
        System.out.println("   " + cor.amarelo + "[1]" + cor.reset + " Soco      " + cor.cinza + "rapido, sempre acerta" + cor.reset);
        System.out.println("   " + cor.amarelo + "[2]" + cor.reset + " Chute     " + cor.cinza + "mais forte, mas pode errar" + cor.reset);
        System.out.println("   " + cor.amarelo + "[3]" + cor.reset + " Defender  " + cor.cinza + "corta pela metade o dano do proximo golpe" + cor.reset);
        System.out.println("   " + cor.amarelo + "[4]" + cor.reset + " Provocar  " + cor.cinza + "enche muito a furia, mas abre a guarda" + cor.reset);

        // o especial so aparece liberado quando a furia chega em 100
        if (lutador.furia >= 100) {
            System.out.println("   " + cor.amarelo + "[5]" + cor.reset + " Especial  "
                + cor.verde + "PRONTO - " + lutador.especial + cor.reset);
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
        vencedor.pose = "vitoria";
        mostrarCena();

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

        vencedor.pose = "vitoria";
        perdedor.pose = "ko";

        limparTela();
        System.out.println();
        caixa("V E N C E D O R :  " + vencedor.nome.toUpperCase(), cor.amarelo);
        System.out.println();

        String[] esquerda = lutador1.desenho();
        String[] direita = lutador2.desenho();
        for (int i = 0; i < 4; i++) {
            System.out.println("  " + cor.azul + esquerda[i] + cor.reset
                + repetir(" ", 38) + cor.vermelho + direita[i] + cor.reset);
        }
        System.out.println("  " + cor.cinza + repetir("_", 62) + cor.reset);

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
        lerNumero(1, 1);
    }

    // Moldura usada nos momentos importantes: ROUND, LUTEM, K.O., VENCEDOR.
    void caixa(String texto, String corDaCaixa) {
        System.out.println(corDaCaixa + "  +" + linhaSimples + "+" + cor.reset);
        System.out.println(corDaCaixa + "  |" + cor.negrito + completar("  " + texto, 62) + cor.reset
            + corDaCaixa + "|" + cor.reset);
        System.out.println(corDaCaixa + "  +" + linhaSimples + "+" + cor.reset);
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
