// Esta classe guarda so os codigos de cor do terminal.
//
// O terminal pinta o texto quando recebe o caractere numero 27 (que tem o
// nome de "escape") seguido de um codigo. Como esses codigos sao feios de
// ler, guardamos cada um em um atributo com nome facil.
// Assim, no resto do jogo, a gente escreve so "cor.vermelho".
public class Cores {

    // Atributos
    String escape;
    String reset;      // volta o texto para a cor normal
    String negrito;
    String vermelho;
    String amarelo;
    String verde;
    String azul;
    String roxo;
    String cinza;
    String branco;

    // Construtor
    public Cores() {
        this.escape   = "" + (char) 27;
        this.reset    = this.escape + "[0m";
        this.negrito  = this.escape + "[1m";
        this.vermelho = this.escape + "[91m";
        this.amarelo  = this.escape + "[93m";
        this.verde    = this.escape + "[92m";
        this.azul     = this.escape + "[96m";
        this.roxo     = this.escape + "[95m";
        this.cinza    = this.escape + "[90m";
        this.branco   = this.escape + "[97m";
    }
}
