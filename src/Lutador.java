public class Lutador {

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

    boolean defendendo;
    boolean guardaAberta;

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

        this.vidaMaxima = 100;
        this.vida = 100;
        this.roundsVencidos = 0;
        this.defendendo = false;
        this.guardaAberta = false;
    }

    Lutador clonar() {
        return new Lutador(this.nome, this.pais, this.estilo, this.especial,
                this.danoSoco, this.danoChute, this.danoEspecial,
                this.defesa, this.velocidade);
    }

    void socar(Lutador inimigo) {
        int dano = inimigo.receberDano(this.danoSoco, false);
        System.out.println("  " + this.nome + " deu um soco e tirou " + dano + " de vida de " + inimigo.nome + ".");
    }

    void chutar(Lutador inimigo) {
        this.guardaAberta = true;
        int dano = inimigo.receberDano(this.danoChute, false);
        System.out.println("  " + this.nome + " chutou e tirou " + dano + " de vida de " + inimigo.nome + ".");
    }

    void defender() {
        this.defendendo = true;
        System.out.println("  " + this.nome + " levantou a guarda.");
    }

    void provocar() {
        this.guardaAberta = true;
        System.out.println("  " + this.nome + " provocou e abriu a guarda.");
    }

    void especial(Lutador inimigo) {
        if (this.vida <= 0) {
            return;
        }
        int dano = inimigo.receberDano(this.danoEspecial, true);
        System.out.println("  " + this.nome + " usou " + this.especial + " e tirou " + dano + " de vida de " + inimigo.nome + ".");
    }

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
            danoFinal = 1;
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

    void limparEstado() {
        this.defendendo = false;
        this.guardaAberta = false;
    }

    void prepararParaNovoRound() {
        this.vida = this.vidaMaxima;
        this.defendendo = false;
        this.guardaAberta = false;
    }

    void mostrarFicha() {
        System.out.println("  Nome: " + this.nome);
        System.out.println("  Pais: " + this.pais);
        System.out.println("  Estilo: " + this.estilo);
        System.out.println("  Especial: " + this.especial);
        System.out.println("  Soco: " + this.danoSoco);
        System.out.println("  Chute: " + this.danoChute);
        System.out.println("  Especial: " + this.danoEspecial);
        System.out.println("  Defesa: " + this.defesa);
        System.out.println("  Velocidade: " + this.velocidade);
        System.out.println("  Vida: " + this.vida + "/" + this.vidaMaxima);
    }

    void mostrarNaLista(int numero) {
        System.out.println("  [" + numero + "] " + this.nome + " - " + this.pais + " - " + this.especial);
    }
}
