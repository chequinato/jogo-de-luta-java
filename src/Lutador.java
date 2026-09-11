// Esta classe e o personagem do jogo.
// Cada lutador criado a partir dela tem a sua propria vida, forca, defesa,
// e sabe atacar, defender, mostrar a ficha e ate se desenhar na tela.
public class Lutador {

    // ===================== ATRIBUTOS =====================
    String nome;
    String pais;
    String estilo;
    String especial;      // nome do golpe especial

    int vida;
    int vidaMaxima;
    int furia;            // enche durante a luta e libera o golpe especial
    int forca;            // quanto dano o lutador causa
    int defesa;           // quanto dano o lutador consegue segurar
    int velocidade;       // chance de desviar dos golpes
    int roundsVencidos;

    // numeros guardados so para mostrar as estatisticas no fim da luta
    int danoCausado;
    int golpesAcertados;
    int maiorGolpe;

    // como o lutador esta agora
    boolean defendendo;             // esta de guarda levantada?
    boolean provocando;             // esta se exibindo e de guarda aberta?
    boolean olhandoParaDireita;     // fica no lado esquerdo da tela?
    String pose;                    // qual desenho mostrar

    Cores cor;            // objeto com os codigos de cor do terminal

    // ===================== CONSTRUTOR =====================
    // E aqui que o lutador "nasce". Quem cria escolhe o nome, o pais, o estilo,
    // o especial e os tres numeros. O resto ja comeca com valor padrao.
    public Lutador(String nome, String pais, String estilo, String especial,
                   int forca, int defesa, int velocidade) {
        this.nome = nome;
        this.pais = pais;
        this.estilo = estilo;
        this.especial = especial;
        this.forca = forca;
        this.defesa = defesa;
        this.velocidade = velocidade;

        this.vidaMaxima = 100;
        this.vida = 100;
        this.furia = 0;
        this.roundsVencidos = 0;
        this.danoCausado = 0;
        this.golpesAcertados = 0;
        this.maiorGolpe = 0;
        this.defendendo = false;
        this.provocando = false;
        this.olhandoParaDireita = true;
        this.pose = "parado";
        this.cor = new Cores();
    }

    // ===================== METODOS DE APOIO =====================

    // Sorteia um numero entre minimo e maximo. Serve para o dano nao ser
    // sempre igual e para decidir se um golpe errou.
    int sorteio(int minimo, int maximo) {
        return minimo + (int) (Math.random() * (maximo - minimo + 1));
    }

    // Cria outro Lutador igualzinho a este.
    // Isso e importante: se os dois jogadores escolhessem o mesmo personagem
    // sem a copia, os dois estariam mexendo no MESMO objeto e dividindo a
    // mesma barra de vida.
    Lutador criarCopia() {
        return new Lutador(this.nome, this.pais, this.estilo, this.especial,
                           this.forca, this.defesa, this.velocidade);
    }

    boolean estaVivo() {
        return this.vida > 0;
    }

    // Aumenta a furia, mas nunca deixa passar de 100.
    void ganharFuria(int quantidade) {
        this.furia = this.furia + quantidade;
        if (this.furia > 100) {
            this.furia = 100;
        }
    }

    // A guarda levantada e a provocacao valem so ate o lutador agir de novo.
    void limparPostura() {
        this.defendendo = false;
        this.provocando = false;
        if (this.estaVivo()) {
            this.pose = "parado";
        }
    }

    // Deixa o lutador pronto para comecar um novo round.
    void prepararParaORound() {
        this.vida = this.vidaMaxima;
        this.furia = 0;
        this.defendendo = false;
        this.provocando = false;
        this.pose = "parado";
    }

    void vencerRound() {
        this.roundsVencidos = this.roundsVencidos + 1;
    }

    // O lutador consegue desviar? Quanto maior a velocidade, maior a chance.
    // Quem esta de guarda levantada nao desvia, porque vai bloquear.
    boolean esquivou() {
        if (this.defendendo) {
            return false;
        }
        return sorteio(1, 100) <= this.velocidade * 2;
    }

    // Guarda o estrago que este lutador fez no adversario.
    void registrarAcerto(int dano) {
        this.danoCausado = this.danoCausado + dano;
        this.golpesAcertados = this.golpesAcertados + 1;
        if (dano > this.maiorGolpe) {
            this.maiorGolpe = dano;
        }
    }

    // ===================== LEVAR UM GOLPE =====================

    // Tira vida do lutador e devolve quanto de dano ele levou de verdade.
    // O "passaPelaDefesa" so e true no golpe especial, que atravessa a guarda.
    int sofrerDano(int dano, boolean passaPelaDefesa) {
        int danoFinal = dano;

        // a defesa segura uma parte do golpe
        if (passaPelaDefesa == false) {
            danoFinal = danoFinal - this.defesa;
        }
        // quem esta de guarda levantada leva so metade
        if (this.defendendo) {
            danoFinal = danoFinal / 2;
        }
        // quem estava se exibindo leva 50% a mais
        if (this.provocando) {
            danoFinal = danoFinal + (danoFinal / 2);
        }
        // todo golpe tira pelo menos 1 de vida
        if (danoFinal < 1) {
            danoFinal = 1;
        }

        this.vida = this.vida - danoFinal;
        if (this.vida < 0) {
            this.vida = 0;
        }

        this.ganharFuria(8);   // apanhar tambem enche a furia

        // troca o desenho do lutador conforme o resultado
        if (this.estaVivo()) {
            this.pose = "dano";
        } else {
            this.pose = "ko";
        }

        return danoFinal;
    }

    // ===================== OS GOLPES =====================
    // Cada golpe recebe o adversario e mexe na vida dele, igual ao exercicio
    // do atacarOutroPersonagem que fizemos em aula.

    void soco(Lutador inimigo) {
        this.pose = "soco";

        if (inimigo.esquivou()) {
            this.ganharFuria(4);
            System.out.println("  " + this.nome + " soltou um SOCO, mas " + inimigo.nome + " desviou.");
            return;
        }

        int dano = 8 + this.forca + sorteio(0, 4);
        int aplicado = inimigo.sofrerDano(dano, false);
        this.registrarAcerto(aplicado);
        this.ganharFuria(12);

        System.out.println("  " + this.nome + " acertou um SOCO. "
            + inimigo.nome + " perdeu " + aplicado + " de vida.");

        if (inimigo.defendendo) {
            System.out.println("  " + inimigo.nome + " bloqueou parte do golpe.");
        }
    }

    void chute(Lutador inimigo) {
        this.pose = "chute";

        // o chute e mais forte, mas tem 25% de chance de errar
        if (sorteio(1, 100) <= 25) {
            this.ganharFuria(4);
            System.out.println("  " + this.nome + " girou o CHUTE e passou longe.");
            return;
        }

        if (inimigo.esquivou()) {
            this.ganharFuria(6);
            System.out.println("  " + this.nome + " chutou, mas " + inimigo.nome + " se abaixou e escapou.");
            return;
        }

        int dano = 14 + this.forca + sorteio(0, 6);
        int aplicado = inimigo.sofrerDano(dano, false);
        this.registrarAcerto(aplicado);
        this.ganharFuria(18);

        System.out.println("  " + this.nome + " acertou um CHUTE em cheio. "
            + inimigo.nome + " perdeu " + aplicado + " de vida.");

        if (inimigo.defendendo) {
            System.out.println("  " + inimigo.nome + " bloqueou parte do golpe.");
        }
    }

    void defender() {
        this.defendendo = true;
        this.pose = "defesa";
        this.ganharFuria(10);
        System.out.println("  " + this.nome + " levantou a guarda e espera o proximo golpe.");
    }

    void provocar() {
        // sorteia uma das frases da lista
        String[] frases = {
            "Vem pra cima!",
            "So isso que voce tem?",
            "Voce luta como um iniciante.",
            "Estou esperando faz tempo.",
            "Levanta essa guarda!"
        };
        String frase = frases[sorteio(0, 4)];

        this.provocando = true;
        this.pose = "provoca";
        this.ganharFuria(35);

        System.out.println("  " + this.nome + " provoca: \"" + frase + "\"");
        System.out.println("  A furia sobe bastante, mas a guarda fica aberta.");
    }

    void golpeEspecial(Lutador inimigo) {
        // trava de seguranca: sem furia cheia o golpe nao sai
        if (this.furia < 100) {
            System.out.println("  A furia de " + this.nome + " ainda nao esta cheia.");
            return;
        }

        this.furia = 0;
        this.pose = "especial";

        int dano = 26 + (this.forca * 2);
        int aplicado = inimigo.sofrerDano(dano, true);   // true = atravessa a defesa
        this.registrarAcerto(aplicado);

        System.out.println("  " + this.especial + " atravessou a guarda! "
            + inimigo.nome + " perdeu " + aplicado + " de vida.");
    }

    // ===================== MOSTRAR NA TELA =====================

    // Mostra todos os dados do lutador, igual a pokedex do exercicio.
    void ficha() {
        System.out.println("   Nome ......... " + this.nome);
        System.out.println("   Pais ......... " + this.pais);
        System.out.println("   Estilo ....... " + this.estilo);
        System.out.println("   Especial ..... " + this.especial);
        System.out.println("   Forca ........ " + this.forca);
        System.out.println("   Defesa ....... " + this.defesa);
        System.out.println("   Velocidade ... " + this.velocidade);
        System.out.println("   Vida ......... " + this.vida + "/" + this.vidaMaxima);
    }

    // Mostra o lutador em uma linha so, para aparecer na lista de escolha.
    void mostrarNaLista(int numero) {
        System.out.println("  " + cor.amarelo + "[" + numero + "]" + cor.reset + "  "
            + cor.negrito + completar(this.nome, 11) + cor.reset
            + completar(this.pais, 11)
            + completar(this.estilo, 14)
            + completar("" + this.forca, 5)
            + completar("" + this.defesa, 5)
            + completar("" + this.velocidade, 5)
            + cor.cinza + this.especial + cor.reset);
    }

    // Mostra a linha do lutador no placar: nome, barra de vida e barra de furia.
    void mostrarBarras() {
        System.out.println("  "
            + cor.negrito + completar(this.nome.toUpperCase(), 11) + cor.reset
            + "[" + barra(this.vida, this.vidaMaxima, 20, corDaVida(), "#", "-") + "]"
            + " " + completarNaFrente("" + this.vida, 3)
            + cor.cinza + "  FURIA " + cor.reset
            + "[" + barra(this.furia, 100, 10, corDaFuria(), "=", ".") + "]  "
            + marcaDosRounds());
    }

    // Mostra as estatisticas do lutador no fim da luta.
    void mostrarEstatisticas() {
        System.out.println("  " + cor.negrito + completar(this.nome.toUpperCase(), 13) + cor.reset
            + cor.cinza + "dano total " + cor.reset + completar("" + this.danoCausado, 6)
            + cor.cinza + "golpes certos " + cor.reset + completar("" + this.golpesAcertados, 5)
            + cor.cinza + "maior golpe " + cor.reset + this.maiorGolpe);
    }

    // Monta uma barra: primeiro a parte cheia, depois a parte vazia.
    // Com 20 blocos e 60 de vida sai assim: ############--------
    String barra(int valor, int maximo, int blocos, String corCheia, String cheio, String vazio) {
        int quantidadeCheia = (valor * blocos) / maximo;

        if (valor > 0 && quantidadeCheia == 0) {
            quantidadeCheia = 1;   // enquanto sobrar 1 de vida, aparece 1 bloco
        }
        if (quantidadeCheia > blocos) {
            quantidadeCheia = blocos;
        }

        return corCheia + repetir(cheio, quantidadeCheia) + cor.reset
             + cor.cinza + repetir(vazio, blocos - quantidadeCheia) + cor.reset;
    }

    // A barra de vida muda de cor conforme o lutador vai apanhando.
    String corDaVida() {
        if (this.vida > 60) {
            return cor.verde;
        } else if (this.vida > 30) {
            return cor.amarelo;
        } else {
            return cor.vermelho;
        }
    }

    String corDaFuria() {
        if (this.furia >= 100) {
            return cor.roxo;      // furia cheia, o especial esta liberado
        } else {
            return cor.azul;
        }
    }

    // Mostra os rounds ja vencidos: * e round ganho, - e round que falta.
    String marcaDosRounds() {
        return cor.amarelo + repetir("* ", this.roundsVencidos)
             + cor.cinza + repetir("- ", 2 - this.roundsVencidos) + cor.reset;
    }

    // ===================== O DESENHO DO LUTADOR =====================
    // Sao 4 linhas de 12 letras. O lutador da esquerda olha para a direita e
    // o da direita olha para a esquerda, entao cada pose esta escrita duas
    // vezes, uma virada para cada lado.
    // Para mudar um boneco, e so trocar os desenhos aqui embaixo.

    String[] desenho() {
        if (this.olhandoParaDireita) {
            return desenhoOlhandoParaDireita();
        } else {
            return desenhoOlhandoParaEsquerda();
        }
    }

    String[] desenhoOlhandoParaDireita() {

        if (this.pose.equals("soco")) {
            return new String[] {
                "     o      ",
                "    /|==>   ",
                "     |      ",
                "    / \\     " };
        }
        if (this.pose.equals("chute")) {
            return new String[] {
                "     o      ",
                "    /|\\     ",
                "     |==>   ",
                "    /       " };
        }
        if (this.pose.equals("defesa")) {
            return new String[] {
                "     o      ",
                "    /|]     ",
                "     |]     ",
                "    / \\     " };
        }
        if (this.pose.equals("provoca")) {
            return new String[] {
                "     o/     ",
                "    /|      ",
                "     |      ",
                "    / \\     " };
        }
        if (this.pose.equals("especial")) {
            return new String[] {
                "     o      ",
                "    /|==(*) ",
                "     |      ",
                "    / \\     " };
        }
        if (this.pose.equals("dano")) {
            return new String[] {
                "    \\o      ",
                "     |\\     ",
                "     |      ",
                "    / \\     " };
        }
        if (this.pose.equals("ko")) {
            return new String[] {
                "            ",
                "            ",
                "  X_______  ",
                "     / \\    " };
        }
        if (this.pose.equals("vitoria")) {
            return new String[] {
                "    \\o/     ",
                "     |      ",
                "     |      ",
                "    / \\     " };
        }

        // se nao for nenhuma das poses de cima, ele fica parado
        return new String[] {
            "     o      ",
            "    /|\\     ",
            "     |      ",
            "    / \\     " };
    }

    String[] desenhoOlhandoParaEsquerda() {

        if (this.pose.equals("soco")) {
            return new String[] {
                "      o     ",
                "   <==|\\    ",
                "      |     ",
                "     / \\    " };
        }
        if (this.pose.equals("chute")) {
            return new String[] {
                "      o     ",
                "     /|\\    ",
                "   <==|     ",
                "       \\    " };
        }
        if (this.pose.equals("defesa")) {
            return new String[] {
                "      o     ",
                "     [|\\    ",
                "     [|     ",
                "     / \\    " };
        }
        if (this.pose.equals("provoca")) {
            return new String[] {
                "     \\o     ",
                "      |\\    ",
                "      |     ",
                "     / \\    " };
        }
        if (this.pose.equals("especial")) {
            return new String[] {
                "      o     ",
                " (*)==|\\    ",
                "      |     ",
                "     / \\    " };
        }
        if (this.pose.equals("dano")) {
            return new String[] {
                "      o/    ",
                "     /|     ",
                "      |     ",
                "     / \\    " };
        }
        if (this.pose.equals("ko")) {
            return new String[] {
                "            ",
                "            ",
                "  _______X  ",
                "    / \\     " };
        }
        if (this.pose.equals("vitoria")) {
            return new String[] {
                "     \\o/    ",
                "      |     ",
                "      |     ",
                "     / \\    " };
        }

        // se nao for nenhuma das poses de cima, ele fica parado
        return new String[] {
            "      o     ",
            "     /|\\    ",
            "      |     ",
            "     / \\    " };
    }

    // ===================== FERRAMENTAS DE TEXTO =====================

    // Repete um pedaco de texto varias vezes. repetir("=", 3) devolve "===".
    String repetir(String pedaco, int vezes) {
        String resultado = "";
        for (int i = 0; i < vezes; i++) {
            resultado = resultado + pedaco;
        }
        return resultado;
    }

    // Poe espacos DEPOIS do texto ate ele ficar do tamanho pedido.
    // E isso que deixa as colunas alinhadas uma embaixo da outra.
    String completar(String texto, int tamanho) {
        while (texto.length() < tamanho) {
            texto = texto + " ";
        }
        return texto;
    }

    // Poe espacos ANTES do texto, para os numeros ficarem alinhados
    // pela direita, como em "  5", " 40" e "100".
    String completarNaFrente(String texto, int tamanho) {
        while (texto.length() < tamanho) {
            texto = " " + texto;
        }
        return texto;
    }
}
