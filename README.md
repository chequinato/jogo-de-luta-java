# Jogo de Luta em Java — projeto de POO

Jogo de luta estilo Street Fighter que roda no terminal. **Dois jogadores no
mesmo teclado** escolhem um lutador cada e trocam golpes até a vida de um
deles chegar a zero.

O projeto usa só o que foi visto em aula: **atributos, construtor, métodos,
`this`, `if`, `while`, `Scanner` e um objeto recebendo outro objeto como
parâmetro**. Não tem nada além disso — sem herança, sem lista, sem sorteio,
sem tratamento de erro e sem biblioteca.

## Como rodar

Jeito mais fácil: dar dois cliques em **`jogar.bat`**. Ele compila tudo e já
abre o jogo.

Pelo terminal, dentro da pasta do projeto:

```bash
javac -d bin src/*.java
java -cp bin App
```

A tela usa só caracteres comuns do teclado (`-`, `=`, `[`, `]`), então fica
igual em qualquer terminal: `cmd`, PowerShell, VS Code ou IntelliJ.

## As duas classes do projeto

A divisão é a mesma dos exercícios de aula: uma classe guarda o objeto e os
métodos dele, e o `App` com o `main` executa tudo.

| Classe | O que ela é |
|---|---|
| `Lutador` | **O personagem**, igual ao `Pokemon` e ao `Personagem` da aula. Tem os atributos, o construtor e os métodos (`socar`, `chutar`, `golpeEspecial`, `defender`, `ficha`). |
| `App` | A classe com o `main`. Cria os lutadores com `new`, mostra o menu e controla a luta. |

Os quatro lutadores são criados no começo do `main`, do mesmo jeito que o
exercício do Pokémon criava o Snorlax, o Pikachu e o Blastoise:

```java
Lutador ryu = new Lutador("Ryu", "Japao", "Karate", "Hadouken", 14, 22, 45);
Lutador chunLi = new Lutador("Chun-Li", "China", "Kung Fu", "Spinning Bird Kick", 13, 20, 42);
Lutador blanka = new Lutador("Blanka", "Brasil", "Selvagem", "Electric Thunder", 17, 26, 50);
Lutador zangief = new Lutador("Zangief", "Russia", "Luta Livre", "Spinning Piledriver", 19, 30, 55);
```

Como são sempre esses mesmos quatro objetos, cada jogador escolhe um deles
pelo número e a variável `jogador1` (ou `jogador2`) passa a apontar para o
objeto escolhido.

## Os métodos do `Lutador`

| Método | O que faz |
|---|---|
| `socar` | Recebe o adversário como parâmetro e tira a vida dele com `inimigo.vida = inimigo.vida - dano`, igual ao `atacarOutroPersonagem` da aula. Dano menor. |
| `chutar` | Igual ao soco, mas usando o dano do chute, que é maior. |
| `golpeEspecial` | O golpe mais forte e o único que passa por cima da guarda. Só funciona quando o lutador está com **50 de vida ou menos**; acima disso ele perde a vez. |
| `defender` | Levanta a guarda: deixa o atributo `defendendo` como `true`. Quem atacar olha esse atributo e tira só metade do dano. |
| `ficha` | Mostra todos os dados do lutador, igual ao `pokedex()` do exercício do Pokémon. |

Todo o resto (menu, escolha dos lutadores, laço da luta) está escrito direto
no `main`, junto dos `System.out.println` das molduras — do mesmo jeito que o
`App` das aulas escrevia `System.out.println("=========== Dados iniciais ===========")`
no meio do código.

## Regras da luta

Os dois lutadores começam com **100 de vida** e a luta acaba quando a vida de
um deles chega a zero. O jogador 1 sempre joga primeiro no turno.

| Ação | Efeito |
|---|---|
| **Soco** | Dano menor. |
| **Chute** | Dano maior que o soco. |
| **Defender** | Não causa dano: levanta a guarda, e o próximo golpe recebido tira metade. |
| **Especial** | O golpe mais forte, passa por cima da guarda, mas só com 50 de vida ou menos. |

Outros detalhes:

- **Nada é sorteado.** O dano de cada golpe já vem definido no construtor do
  lutador, então o mesmo golpe sempre tira o mesmo tanto de vida.
- A guarda levantada vale só até aquele lutador jogar de novo: antes da vez
  dele o jogo faz `jogador1.defendendo = false`.
- Os dois jogadores não podem escolher o mesmo lutador. Como o jogo usa sempre
  os mesmos quatro objetos, escolher o mesmo número duas vezes faria um objeto
  lutar contra ele mesmo, dividindo a mesma barra de vida.
- A vida nunca fica negativa: quando passa de zero, ela é acertada para zero.
- No fim da luta os dois voltam para 100 de vida, para o próximo que escolher
  aquele lutador começar do zero.
- Se você digitar um número de ação que não existe, o lutador perde a vez.

## Os lutadores

| # | Nome | País | Estilo | Soco | Chute | Especial | Golpe |
|---|---|---|---|---|---|---|---|
| 1 | Ryu | Japão | Karate | 14 | 22 | 45 | Hadouken |
| 2 | Chun-Li | China | Kung Fu | 13 | 20 | 42 | Spinning Bird Kick |
| 3 | Blanka | Brasil | Selvagem | 17 | 26 | 50 | Electric Thunder |
| 4 | Zangief | Rússia | Luta Livre | 19 | 30 | 55 | Spinning Piledriver |

O Zangief bate mais forte, mas quem apanha primeiro chega antes nos 50 de
vida e libera o golpe especial — é o que dá a chance de virada.

## Como é feito o visual do terminal

Não tem biblioteca nenhuma, é tudo `System.out.println`:

- O nome **STREET FIGHTER** é desenhado com as próprias letras, uma linha de
  `println` por vez, no começo do `main` — antes do `while` do menu, para
  aparecer uma vez só quando o jogo abre.
- As **molduras** são `System.out.println` com `=` e `-`, escritos no lugar
  onde a mensagem aparece.
- A **vida dos dois lutadores** aparece no começo de cada turno, no formato
  `Zangief: 55 de vida`. Não existe método para isso: é a linha escrita no
  lugar onde ela aparece, igual às molduras.

## Conceitos de POO usados (para explicar na apresentação)

- **Classe e objeto**: `Lutador` é a classe (a "forma"); `new Lutador("Ryu", ...)`
  cria um objeto, um lutador de verdade com os dados dele.
- **Atributos**: as características de cada objeto (`nome`, `vida`, `danoSoco`...),
  todos de tipo simples (`String`, `int`, `boolean`).
- **Construtor**: `public Lutador(...)` define como um lutador nasce. Todo
  lutador já começa com 100 de vida e com a guarda baixada, isso está escrito
  no construtor. É lá também que entra o dano dos golpes de cada personagem.
- **Métodos**: as ações do objeto (`socar`, `chutar`, `defender`, `ficha`).
- **Um objeto agindo sobre outro**: `ryu.socar(zangief)` — o método recebe
  outro `Lutador` como parâmetro e mexe na vida dele, igualzinho ao exercício
  do `atacarOutroPersonagem`.
- **Mexer no atributo de outro objeto**: dentro do `socar` o jogo faz
  `inimigo.vida = inimigo.vida - dano;`, do mesmo jeito que a aula fazia
  `inimigo.vida = inimigo.vida - 10;`.
- **`this`**: usado no construtor para diferenciar o atributo do objeto do
  parâmetro que chegou com o mesmo nome.
- **Duas variáveis apontando para o mesmo objeto**: `jogador1 = ryu` não cria
  um Ryu novo — as duas variáveis passam a ser o mesmo lutador. É por isso que
  os dois jogadores não podem escolher o mesmo número.
- **`Scanner`**: a leitura do teclado com `scanner.nextInt()`, igual ao
  primeiro exercício da aula.

## Ideias para aumentar o projeto depois

- Mais lutadores (criar outro objeto no começo do `main` e acrescentar um
  `else if` na escolha).
- Voltar a luta para melhor de 3 rounds, com um atributo `int roundsVencidos`.
- Limitar o golpe especial a um uso por luta, com um atributo `int` contando
  quantas vezes já foi usado.
- Um atributo `defesa` descontado do dano dos golpes normais.
