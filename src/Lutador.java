// Esta classe e o personagem do jogo.
// Cada lutador tem a sua propria vida, os seus golpes e sabe atacar,
// defender e mostrar os proprios dados na tela.
public class Lutador {

    // ===================== ATRIBUTOS =====================
    String nome;
    String pais;
    String estilo;
    String especial;      // nome do golpe especial

    int vida;
    int vidaMaxima;
    int furia;            // enche durante a luta e libera o golpe especial

    // o dano de cada golpe ja vem definido quando o lutador e criado.
    // e por isso que o Zangief bate mais forte que a Chun-Li.
    int danoDoSoco;
    int danoDoChute;
    int danoDoEspecial;

    int defesa;           // quanto dano o lutador consegue segurar
    int velocidade;       // quem tem mais velocidade comeca o round
    int roundsVencidos;

    // numeros guardados so para mostrar as estatisticas no fim da luta
    int danoCausado;
    int golpesAcertados;
    int maiorGolpe;

    // como o lutador esta agora
    boolean defendendo;      // esta de guarda levantada?
    boolean guardaAberta;    // deu um chute ou provocou e ficou exposto?

    Cores cor;            // objeto com os codigos de cor do terminal

    // ===================== CONSTRUTOR =====================
    // E aqui que o lutador "nasce". Quem cria escolhe o nome, o pais, o estilo,
    // o nome do especial, o dano dos tres golpes, a defesa e a velocidade.
    // A vida e a furia ja comecam com o valor padrao.
    public Lutador(String nome, String pais, String estilo, String especial,
                   int danoDoSoco, int danoDoChute, int danoDoEspecial,
                   int defesa, int velocidade) {
        this.nome = nome;
        this.pais = pais;
        this.estilo = estilo;
        this.especial = especial;
        this.danoDoSoco = danoDoSoco;
        this.danoDoChute = danoDoChute;
        this.danoDoEspecial = danoDoEspecial;
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
        this.guardaAberta = false;
        this.cor = new Cores();
    }

    // ===================== METODOS DE APOIO =====================

    // Cria outro Lutador igualzinho a este.
    // Isso e importante: se os dois jogadores escolhessem o mesmo personagem
    // sem a copia, os dois estariam mexendo no MESMO objeto e dividindo a
    // mesma barra de vida.
    Lutador criarCopia() {
        return new Lutador(this.nome, this.pais, this.estilo, this.especial,
                           this.danoDoSoco, this.danoDoChute, this.danoDoEspecial,
                           this.defesa, this.velocidade);
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

    // A guarda levantada e a guarda aberta valem so ate o lutador agir de novo.
    void limparPostura() {
        this.defendendo = false;
        this.guardaAberta = false;
    }

    // Deixa o lutador pronto para comecar um novo round.
    void prepararParaORound() {
        this.vida = this.vidaMaxima;
        this.furia = 0;
        this.defendendo = false;
        this.guardaAberta = false;
    }

    void vencerRound() {
        this.roundsVencidos = this.roundsVencidos + 1;
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
        // quem esta com a guarda aberta leva 50% a mais
        if (this.guardaAberta) {
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
        return danoFinal;
    }

    // ===================== OS GOLPES =====================
    // Cada golpe recebe o adversario e mexe na vida dele, igual ao exercicio
    // do atacarOutroPersonagem que fizemos em aula.
    // O dano nao e sorteado: e o numero que o lutador ja tem guardado.

    void soco(Lutador inimigo) {
        int aplicado = inimigo.sofrerDano(this.danoDoSoco, false);
        this.registrarAcerto(aplicado);
        this.ganharFuria(12);

        System.out.println("  " + this.nome + " acertou um SOCO. "
            + inimigo.nome + " perdeu " + aplicado + " de vida.");

        if (inimigo.defendendo) {
            System.out.println("  " + inimigo.nome + " bloqueou parte do golpe.");
        }
    }

    void chute(Lutador inimigo) {
        int aplicado = inimigo.sofrerDano(this.danoDoChute, false);
        this.registrarAcerto(aplicado);
        this.ganharFuria(18);

        // o chute e mais forte, mas o lutador se abre para girar o corpo
        this.guardaAberta = true;

        System.out.println("  " + this.nome + " acertou um CHUTE em cheio. "
            + inimigo.nome + " perdeu " + aplicado + " de vida.");
        System.out.println("  Para girar o chute, " + this.nome + " ficou com a guarda aberta.");

        if (inimigo.defendendo) {
            System.out.println("  " + inimigo.nome + " bloqueou parte do golpe.");
        }
    }

    void defender() {
        this.defendendo = true;
        this.ganharFuria(10);
        System.out.println("  " + this.nome + " levantou a guarda e espera o proximo golpe.");
    }

    void provocar() {
        this.guardaAberta = true;
        this.ganharFuria(35);

        System.out.println("  " + this.nome + " provoca: \"Vem pra cima!\"");
        System.out.println("  A furia sobe bastante, mas a guarda fica aberta.");
    }

    void golpeEspecial(Lutador inimigo) {
        // trava de seguranca: sem furia cheia o golpe nao sai
        if (this.furia < 100) {
            System.out.println("  A furia de " + this.nome + " ainda nao esta cheia.");
            return;
        }

        this.furia = 0;

        int aplicado = inimigo.sofrerDano(this.danoDoEspecial, true);   // true = atravessa a defesa
        this.registrarAcerto(aplicado);

        System.out.println("  " + this.especial + " atravessou a guarda! "
            + inimigo.nome + " perdeu " + aplicado + " de vida.");
    }

    // ===================== MOSTRAR NA TELA =====================

    // Mostra todos os dados do lutador, igual a pokedex do exercicio.
    void ficha() {
        System.out.println("   Nome ............ " + this.nome);
        System.out.println("   Pais ............ " + this.pais);
        System.out.println("   Estilo .......... " + this.estilo);
        System.out.println("   Especial ........ " + this.especial);
        System.out.println("   Dano do soco .... " + this.danoDoSoco);
        System.out.println("   Dano do chute ... " + this.danoDoChute);
        System.out.println("   Dano do especial  " + this.danoDoEspecial);
        System.out.println("   Defesa .......... " + this.defesa);
        System.out.println("   Velocidade ...... " + this.velocidade);
        System.out.println("   Vida ............ " + this.vida + "/" + this.vidaMaxima);
    }

    // Mostra o lutador em uma linha so, para aparecer na lista de escolha.
    void mostrarNaLista(int numero) {
        System.out.println("  " + cor.amarelo + "[" + numero + "]" + cor.reset + "  "
            + cor.negrito + completar(this.nome, 11) + cor.reset
            + completar(this.pais, 10)
            + completar("" + this.danoDoSoco, 6)
            + completar("" + this.danoDoChute, 7)
            + completar("" + this.danoDoEspecial, 6)
            + completar("" + this.defesa, 6)
            + completar("" + this.velocidade, 6)
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
