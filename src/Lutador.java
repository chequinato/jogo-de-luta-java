public class Lutador {

    // Atributos
    String nome;
    String pais;
    String estilo;
    String especial;

    int vida;
    int danoSoco;
    int danoChute;
    int danoEspecial;
    int defesa;
    int velocidade;
    int roundsVencidos;

    boolean defendendo;   // levantou a guarda, entao sofre metade do dano
    boolean guardaAberta; // se expos, entao sofre 50% a mais de dano

    // Construtor
    public Lutador(String nome, String pais, String estilo, String especial, int danoSoco, int danoChute, int danoEspecial, int defesa, int velocidade) {
        this.nome = nome;
        this.pais = pais;
        this.estilo = estilo;
        this.especial = especial;
        this.danoSoco = danoSoco;
        this.danoChute = danoChute;
        this.danoEspecial = danoEspecial;
        this.defesa = defesa;
        this.velocidade = velocidade;

        // Todo lutador comeca com 100 de vida e sem nenhum round vencido
        this.vida = 100;
        this.roundsVencidos = 0;
        this.defendendo = false;
        this.guardaAberta = false;
    }

    // Metodos

    void socar(Lutador inimigo) {
        int dano = inimigo.receberDano(this.danoSoco, false);
        System.out.println("   " + this.nome + " deu um SOCO e tirou " + dano + " de vida de " + inimigo.nome + ".");
    }

    void chutar(Lutador inimigo) {
        this.guardaAberta = true; // para girar o corpo ele se expoe
        int dano = inimigo.receberDano(this.danoChute, false);
        System.out.println("   " + this.nome + " deu um CHUTE e tirou " + dano + " de vida de " + inimigo.nome + ".");
        System.out.println("   So que " + this.nome + " abriu a guarda no chute.");
    }

    void defender() {
        this.defendendo = true;
        System.out.println("   " + this.nome + " levantou a guarda e vai sofrer metade do dano.");
    }

    void provocar() {
        this.guardaAberta = true;
        System.out.println("   " + this.nome + " provocou o adversario e abriu a guarda.");
    }

    void especial(Lutador inimigo) {
        int dano = inimigo.receberDano(this.danoEspecial, true);
        System.out.println("   " + this.nome + " usou " + this.especial + "!");
        System.out.println("   O golpe passou pela defesa e tirou " + dano + " de vida de " + inimigo.nome + ".");
    }

    // Desconta o dano da vida deste lutador e devolve quanto ele perdeu.
    // Os tres golpes de cima usam este metodo, por isso ele existe.
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

    // Desenha a barra de vida com # e . usando um for de 20 voltas
    void mostrarBarra() {
        int blocos = this.vida / 5; // 100 de vida viram 20 blocos
        String barra = "";

        for (int i = 0; i < 20; i++) {
            if (i < blocos) {
                barra = barra + "#";
            } else {
                barra = barra + ".";
            }
        }

        System.out.println("   " + this.nome + " - " + this.vida + " de vida");
        System.out.println("   [" + barra + "]");
    }

    void mostrarFicha() {
        System.out.println("   Nome ......: " + this.nome);
        System.out.println("   Pais ......: " + this.pais);
        System.out.println("   Estilo ....: " + this.estilo);
        System.out.println("   Especial ..: " + this.especial);
        System.out.println("   Soco ......: " + this.danoSoco);
        System.out.println("   Chute .....: " + this.danoChute);
        System.out.println("   Dano esp ..: " + this.danoEspecial + " (passa pela defesa)");
        System.out.println("   Defesa ....: " + this.defesa);
        System.out.println("   Velocidade : " + this.velocidade);
        System.out.println("   Vida ......: " + this.vida);
    }
}
