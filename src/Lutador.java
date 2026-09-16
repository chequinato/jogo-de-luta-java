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
    int roundsVencidos;
    boolean defendendo; // quando esta com a guarda levantada, sofre metade do dano

    // Construtor
    public Lutador(String nome, String pais, String estilo, String especial, int danoSoco, int danoChute, int danoEspecial) {
        this.nome = nome;
        this.pais = pais;
        this.estilo = estilo;
        this.especial = especial;
        this.danoSoco = danoSoco;
        this.danoChute = danoChute;
        this.danoEspecial = danoEspecial;
        this.vida = 100; // todo lutador comeca a luta com 100 de vida
        this.roundsVencidos = 0;
        this.defendendo = false;
    }

    // Metodos

    // Tira a vida do inimigo, do mesmo jeito que a aula fazia com inimigo.vida = inimigo.vida - 10
    void socar(Lutador inimigo) {
        int dano = this.danoSoco;

        // se o inimigo levantou a guarda, o golpe tira so a metade
        if (inimigo.defendendo) {
            dano = dano / 2;
        }

        inimigo.vida = inimigo.vida - dano;

        // a vida nao pode ficar negativa
        if (inimigo.vida < 0) {
            inimigo.vida = 0;
        }

        System.out.println("   " + this.nome + " deu um SOCO e tirou " + dano + " de vida de " + inimigo.nome + ".");
    }

    // O chute e igual ao soco, so que usa o dano do chute, que e maior
    void chutar(Lutador inimigo) {
        int dano = this.danoChute;

        if (inimigo.defendendo) {
            dano = dano / 2;
        }

        inimigo.vida = inimigo.vida - dano;

        if (inimigo.vida < 0) {
            inimigo.vida = 0;
        }

        System.out.println("   " + this.nome + " deu um CHUTE e tirou " + dano + " de vida de " + inimigo.nome + ".");
    }

    // O golpe especial e o mais forte e passa por cima da guarda
    void golpeEspecial(Lutador inimigo) {
        inimigo.vida = inimigo.vida - this.danoEspecial;

        if (inimigo.vida < 0) {
            inimigo.vida = 0;
        }

        System.out.println("   " + this.nome + " usou " + this.especial + "!");
        System.out.println("   O golpe passou pela guarda e tirou " + this.danoEspecial + " de vida de " + inimigo.nome + ".");
    }

    // Levanta a guarda. Quem ataca olha este atributo para saber se o dano cai pela metade
    void defender() {
        this.defendendo = true;
        System.out.println("   " + this.nome + " levantou a guarda e vai sofrer metade do dano.");
    }

    // Desenha a barra de vida com # e . usando um for de 20 voltas
    void mostrarBarra() {
        int blocos = this.vida / 5; // 100 de vida viram 20 blocos

        System.out.println("   " + this.nome + " - " + this.vida + " de vida");
        System.out.print("   [");

        for (int i = 0; i < 20; i++) {
            if (i < blocos) {
                System.out.print("#");
            } else {
                System.out.print(".");
            }
        }

        System.out.println("]");
    }

    // Mostra os dados do lutador, igual ao pokedex() do exercicio do Pokemon
    void mostrarFicha() {
        System.out.println("   Nome ......: " + this.nome);
        System.out.println("   Pais ......: " + this.pais);
        System.out.println("   Estilo ....: " + this.estilo);
        System.out.println("   Especial ..: " + this.especial);
        System.out.println("   Soco ......: " + this.danoSoco);
        System.out.println("   Chute .....: " + this.danoChute);
        System.out.println("   Dano esp ..: " + this.danoEspecial);
        System.out.println("   Vida ......: " + this.vida);
    }
}
