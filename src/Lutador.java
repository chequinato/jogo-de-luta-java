public class Lutador {

    // Atributos
    String nome;
    String pais;
    String estilo;
    String especial;

    int vida;
    int vidaMaxima;
    int danoSoco;
    int danoChute;
    int danoEspecial;
    int defesa;
    int velocidade;
    int roundsVencidos;

    boolean defendendo;   // levantou a guarda, entao sofre metade do dano
    boolean guardaAberta; // se expos, entao sofre 50% a mais de dano

    // Construtor
    public Lutador(String nome, String pais, String estilo, String especial,
                   int danoSoco, int danoChute, int danoEspecial,
                   int defesa, int velocidade) {
        this.nome = nome;
        this.pais = pais;
        this.estilo = estilo;
        this.especial = especial;
        this.danoSoco = danoSoco;
        this.danoChute = danoChute;
        this.danoEspecial = danoEspecial;
        this.defesa = defesa;
        this.velocidade = velocidade;

        // Todo lutador comeca com 100 de vida
        this.vidaMaxima = 100;
        this.vida = 100;
        this.roundsVencidos = 0;
        this.defendendo = false;
        this.guardaAberta = false;
    }

    // Metodos

    // Cria um lutador novo com os mesmos dados deste aqui.
    // Serve para dois jogadores poderem escolher o mesmo personagem
    // sem dividirem a mesma barra de vida.
    Lutador clonar() {
        return new Lutador(this.nome, this.pais, this.estilo, this.especial,
                this.danoSoco, this.danoChute, this.danoEspecial,
                this.defesa, this.velocidade);
    }

    void socar(Lutador inimigo) {
        int dano = inimigo.receberDano(this.danoSoco, false);
        System.out.println("        " + this.nome + " deu um SOCO e tirou " + dano + " de vida de " + inimigo.nome + ".");
    }

    void chutar(Lutador inimigo) {
        this.guardaAberta = true; // para girar o corpo ele se expoe
        int dano = inimigo.receberDano(this.danoChute, false);
        System.out.println("        " + this.nome + " deu um CHUTE e tirou " + dano + " de vida de " + inimigo.nome + ".");
        System.out.println("        So que " + this.nome + " abriu a guarda no chute.");
    }

    void defender() {
        this.defendendo = true;
        System.out.println("        " + this.nome + " levantou a guarda e vai sofrer metade do dano.");
    }

    void provocar() {
        this.guardaAberta = true;
        System.out.println("        " + this.nome + " provocou o adversario e abriu a guarda.");
    }

    void especial(Lutador inimigo) {
        int dano = inimigo.receberDano(this.danoEspecial, true);
        System.out.println("        " + this.nome + " usou " + this.especial + "!");
        System.out.println("        O golpe passou pela defesa e tirou " + dano + " de vida de " + inimigo.nome + ".");
    }

    // Desconta o dano da vida deste lutador e devolve quanto ele perdeu
    int receberDano(int dano, boolean passaPelaDefesa) {
        int danoFinal = dano;

        if (passaPelaDefesa == false) {
            danoFinal = danoFinal - this.defesa;
        }

        if (this.defendendo) {
            danoFinal = danoFinal / 2;
        }

        if (this.guardaAberta) {
            danoFinal = danoFinal + (danoFinal / 2);
        }

        if (danoFinal < 1) {
            danoFinal = 1; // todo golpe tira pelo menos 1
        }

        this.vida = this.vida - danoFinal;

        if (this.vida < 0) {
            this.vida = 0;
        }

        return danoFinal;
    }

    boolean estaVivo() {
        return this.vida > 0;
    }

    // Deixa o lutador pronto para comecar outro round: vida cheia e guarda limpa.
    // Os rounds que ele ja venceu continuam contados.
    void prepararParaNovoRound() {
        this.vida = this.vidaMaxima;
        this.defendendo = false;
        this.guardaAberta = false;
    }

    // A guarda levantada e a guarda aberta valem so ate o lutador agir de novo
    void limparEstado() {
        this.defendendo = false;
        this.guardaAberta = false;
    }

    // Mostra o lutador em uma linha so, na tela de escolha
    void mostrarNaLista(int numero) {
        String linha = "        " + numero + "   ";
        linha = linha + completar(this.nome, 11);
        linha = linha + completar(this.pais, 9);
        linha = linha + completar(this.estilo, 13);
        linha = linha + completar("" + this.danoSoco, 4);
        linha = linha + completar("" + this.danoChute, 4);
        linha = linha + completar("" + this.danoEspecial, 4);
        linha = linha + completar("" + this.defesa, 4);
        linha = linha + this.velocidade;
        System.out.println(linha);
    }

    // Coloca espacos no fim do texto ate ele ficar do tamanho pedido,
    // para as colunas da tabela ficarem alinhadas
    String completar(String texto, int tamanho) {
        String resultado = texto;

        while (resultado.length() < tamanho) {
            resultado = resultado + " ";
        }

        return resultado;
    }

    void mostrarFicha() {
        System.out.println("        Nome .......: " + this.nome);
        System.out.println("        Pais .......: " + this.pais);
        System.out.println("        Estilo .....: " + this.estilo);
        System.out.println("        Golpe ......: " + this.especial);
        System.out.println("        Soco .......: " + this.danoSoco);
        System.out.println("        Chute ......: " + this.danoChute);
        System.out.println("        Especial ...: " + this.danoEspecial + "  (passa pela defesa)");
        System.out.println("        Defesa .....: " + this.defesa);
        System.out.println("        Velocidade .: " + this.velocidade);
        System.out.println("        Vida .......: " + this.vida + "/" + this.vidaMaxima);
    }
}
