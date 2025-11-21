println("═══════════════════════════════════════")
println("    MUNHU - REDUCE, FOLD & SEQUENCES")
println("═══════════════════════════════════════")

// ========== REDUCE ==========

println("\n=== REDUCE (acumular) ===")

val numeros = listOf(1, 2, 3, 4, 5)

// Somar todos
val soma = numeros.reduce { acc, numero -> acc + numero }
println("Soma: $soma")

// Multiplicar todos
val produto = numeros.reduce { acc, numero -> acc * numero }
println("Produto: $produto")

// Exemplo prático: concatenar strings
val palavras = listOf("Munhu", "é", "incrível!")
val frase = palavras.reduce { acc, palavra -> "$acc $palavra" }
println("Frase: $frase")

// ========== FOLD ==========

println("\n=== FOLD (acumular com valor inicial) ===")

// Fold é como reduce, mas com valor inicial
val somaComFold = numeros.fold(0) { acc, numero -> acc + numero }
println("Soma com fold: $somaComFold")

// Valor inicial diferente
val somaComInicio = numeros.fold(100) { acc, numero -> acc + numero }
println("Soma começando de 100: $somaComInicio")

// Exemplo: construir objeto
data class Estatisticas(val total: Int, val soma: Int, val media: Double)

val stats = numeros.fold(Estatisticas(0, 0, 0.0)) { acc, numero ->
    val novoTotal = acc.total + 1
    val novaSoma = acc.soma + numero
    Estatisticas(novoTotal, novaSoma, novaSoma.toDouble() / novoTotal)
}
println("\nEstatísticas: $stats")

// ========== EXEMPLO PRÁTICO: ENGAGEMENT ==========

data class Post(val id: String, val likes: Int, val comentarios: Int, val compartilhamentos: Int)

val posts = listOf(
    Post("P1", 150, 20, 10),
    Post("P2", 89, 15, 5),
    Post("P3", 234, 45, 20),
    Post("P4", 45, 5, 2)
)

println("\n=== ENGAGEMENT TOTAL ===")

// Total de likes
val totalLikes = posts.fold(0) { acc, post -> acc + post.likes }
println("Total likes: $totalLikes")

// Engajamento total
val engajamentoTotal = posts.fold(0) { acc, post ->
    acc + post.likes + (post.comentarios * 2) + (post.compartilhamentos * 3)
}
println("Engajamento total: $engajamentoTotal")

// Estatísticas complexas
data class EngajamentoStats(
    val totalPosts: Int = 0,
    val totalLikes: Int = 0,
    val totalComentarios: Int = 0,
    val totalCompartilhamentos: Int = 0
) {
    val engajamento: Int
        get() = totalLikes + (totalComentarios * 2) + (totalCompartilhamentos * 3)
    
    val mediaLikes: Double
        get() = if (totalPosts > 0) totalLikes.toDouble() / totalPosts else 0.0
}

val statsCompletas = posts.fold(EngajamentoStats()) { acc, post ->
    EngajamentoStats(
        totalPosts = acc.totalPosts + 1,
        totalLikes = acc.totalLikes + post.likes,
        totalComentarios = acc.totalComentarios + post.comentarios,
        totalCompartilhamentos = acc.totalCompartilhamentos + post.compartilhamentos
    )
}

println("\n📊 Estatísticas Completas:")
println("   Posts: ${statsCompletas.totalPosts}")
println("   Likes: ${statsCompletas.totalLikes}")
println("   Comentários: ${statsCompletas.totalComentarios}")
println("   Compartilhamentos: ${statsCompletas.totalCompartilhamentos}")
println("   Engajamento total: ${statsCompletas.engajamento}")
println("   Média de likes: ${"%.1f".format(statsCompletas.mediaLikes)}")

// ========== SEQUENCES (LAZY EVALUATION) ==========

println("\n=== SEQUENCES (avaliação preguiçosa) ===")

// Lista normal (EAGER - processa tudo imediatamente)
println("\nCom Lista (EAGER):")
val listaGrande = (1..1_000_000).toList()
val resultadoLista = listaGrande
    .map { 
        println("  map: $it")
        it * 2 
    }
    .filter {
        println("  filter: $it")
        it > 10
    }
    .take(3)

println("Resultado lista: $resultadoLista")

// Sequence (LAZY - processa só o necessário)
println("\nCom Sequence (LAZY):")
val sequencia = (1..1_000_000).asSequence()
val resultadoSequence = sequencia
    .map {
        println("  map: $it")
        it * 2
    }
    .filter {
        println("  filter: $it")
        it > 10
    }
    .take(3)
    .toList()  // Só aqui processa!

println("Resultado sequence: $resultadoSequence")

// ========== QUANDO USAR SEQUENCES ==========

println("\n=== QUANDO USAR SEQUENCES ===")

// Coleções grandes
val milhao = (1..1_000_000).asSequence()

// Operações em cadeia
val resultado = milhao
    .filter { it % 2 == 0 }
    .map { it * it }
    .filter { it > 100 }
    .take(10)
    .toList()

println("Primeiros 10 quadrados pares > 100: $resultado")

// ========== SEQUENCE DE POSTS (uso real) ==========

println("\n=== SEQUENCE NO FEED ===")

fun gerarPostsInfinitos(): Sequence<Post> = sequence {
    var id = 1
    while (true) {
        yield(Post(
            id = "P$id",
            likes = (10..500).random(),
            comentarios = (1..50).random(),
            compartilhamentos = (0..20).random()
        ))
        id++
    }
}

// Pegar apenas os 5 primeiros posts virais (500+ likes)
val postsVirais = gerarPostsInfinitos()
    .filter { it.likes >= 200 }
    .take(5)
    .toList()

println("Posts virais encontrados:")
postsVirais.forEach { post ->
    println("  ${post.id}: ${post.likes} likes")
}

// ========== GENERATESEQUENCE ==========

println("\n=== GENERATE SEQUENCE ===")

// Sequência infinita
val fibonacci = generateSequence(Pair(0, 1)) { (a, b) ->
    Pair(b, a + b)
}.map { it.first }

val primeiros10Fib = fibonacci.take(10).toList()
println("Primeiros 10 Fibonacci: $primeiros10Fib")

// IDs incrementais
val gerarIds = generateSequence(1) { it + 1 }
val primeiros5Ids = gerarIds.take(5).toList()
println("Primeiros 5 IDs: $primeiros5Ids")

// ========== PERFORMANCE COMPARISON ==========

println("\n=== COMPARAÇÃO DE PERFORMANCE ===")

val dados = (1..100_000).toList()

// Com lista (processa tudo)
val inicioLista = System.currentTimeMillis()
val resLista = dados
    .map { it * 2 }
    .filter { it > 50 }
    .map { it * it }
    .take(100)
val fimLista = System.currentTimeMillis()

// Com sequence (processa só necessário)
val inicioSeq = System.currentTimeMillis()
val resSeq = dados.asSequence()
    .map { it * 2 }
    .filter { it > 50 }
    .map { it * it }
    .take(100)
    .toList()
val fimSeq = System.currentTimeMillis()

println("Tempo com Lista: ${fimLista - inicioLista}ms")
println("Tempo com Sequence: ${fimSeq - inicioSeq}ms")
println("Sequence foi ${(fimLista - inicioLista).toDouble() / (fimSeq - inicioSeq)}x mais rápido")

// ========== DICAS DE PERFORMANCE ==========

println("\n╔════════════════════════════════════════╗")
println("║       DICAS DE PERFORMANCE             ║")
println("╠════════════════════════════════════════╣")
println("║")
println("║ USE LIST quando:                       ║")
println("║ • Coleção pequena (< 1000 itens)      ║")
println("║ • Poucas operações em cadeia          ║")
println("║ • Precisa processar tudo              ║")
println("║")
println("║ USE SEQUENCE quando:                   ║")
println("║ • Coleção grande (1000+ itens)        ║")
println("║ • Muitas operações em cadeia          ║")
println("║ • Só precisa de parte do resultado    ║")
println("║ • Performance é crítica                ║")
println("║")
println("╚════════════════════════════════════════╝")
// ========== EXEMPLO COMPLETO: FEED OTIMIZADO ==========
println("\n=== FEED OTIMIZADO COM SEQUENCE ===")
data class Usuario(val id: String, val nome: String, val bloqueado: Boolean = false)
data class PostCompleto(
val id: String,
val autorId: String,
val conteudo: String,
val likes: Int,
val comentarios: Int,
val spam: Boolean = false
)
val usuarios = listOf(
Usuario("U1", "francisco"),
Usuario("U2", "ana"),
Usuario("U3", "spam_user", bloqueado = true),
Usuario("U4", "carlos")
)
val todosOsPosts = (1..10000).map { i ->
val autor = usuarios.random()
PostCompleto(
id = "P$i",
autorId = autor.id,
conteudo = "Post $i",
likes = (0..500).random(),
comentarios = (0..50).random(),
spam = i % 100 == 0  // 1% spam
)
}
println("Total de posts no banco: ${todosOsPosts.size}")
// Carregar feed (primeiros 20 posts válidos)
val feed = todosOsPosts.asSequence()
.filterNot { it.spam }  // Remove spam
.filter { post ->  // Remove posts de usuários bloqueados
val autor = usuarios.find { it.id == post.autorId }
autor?.bloqueado == false
}
.filter { it.likes >= 10 }  // Mínimo de qualidade
.sortedByDescending { it.likes + (it.comentarios * 2) }  // Engajamento
.take(20)  // Só os top 20
.toList()
println("Posts no feed: ${feed.size}")
println("\nTop 5 posts:")
feed.take(5).forEach { post ->
val autor = usuarios.find { it.id == post.autorId }
println("  {post.id} by @{autor?.nome}: ${post.likes} ❤️ ${post.comentarios} 💬")
}
// ========== CHUNKED SEQUENCE ==========
println("\n=== PAGINAÇÃO COM SEQUENCE ===")
fun carregarPagina(numeroPagina: Int, tamanhoPagina: Int = 10): List {
return todosOsPosts.asSequence()
.filterNot { it.spam }
.drop(numeroPagina * tamanhoPagina)
.take(tamanhoPagina)
.toList()
}
println("Página 1:")
carregarPagina(0).take(3).forEach { println("  ${it.id}") }
println("\nPágina 2:")
carregarPagina(1).take(3).forEach { println("  ${it.id}") }
// ========== BONUS: OPERAÇÕES AVANÇADAS ==========
println("\n=== OPERAÇÕES AVANÇADAS ===")
val postsSample = posts
// MaxBy / MinBy
val maisLikes = postsSample.maxByOrNull { it.likes }
val menosLikes = postsSample.minByOrNull { it.likes }
println("Mais likes: {maisLikes?.id} ({maisLikes?.likes})")
println("Menos likes: {menosLikes?.id} ({menosLikes?.likes})")
// SumOf
val totalLikesAll = postsSample.sumOf { it.likes }
val totalEngajamento = postsSample.sumOf {
it.likes + (it.comentarios * 2) + (it.compartilhamentos * 3)
}
println("\nTotal likes: $totalLikesAll")
println("Total engajamento: $totalEngajamento")
// Average
val mediaLikes = postsSample.map { it.likes }.average()
println("Média de likes: ${"%.1f".format(mediaLikes)}")
// Count (com condição)
val postsCom100Plus = postsSample.count { it.likes >= 100 }
println("Posts com 100+ likes: $postsCom100Plus")
