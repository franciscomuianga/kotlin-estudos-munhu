println("═══════════════════════════════════════")
println("    MUNHU - OPERAÇÕES FUNCIONAIS")
println("═══════════════════════════════════════")

data class Post(
    val id: String,
    val autor: String,
    val conteudo: String,
    val likes: Int,
    val comentarios: Int,
    val publico: Boolean = true
)

val posts = listOf(
    Post("P1", "francisco", "Kotlin é incrível!", 150, 20),
    Post("P2", "ana", "Bom dia! ☀️", 89, 10),
    Post("P3", "carlos", "Tutorial completo", 234, 45),
    Post("P4", "beatriz", "Primeiro post", 45, 5),
    Post("P5", "francisco", "Estudando coleções", 180, 30),
    Post("P6", "ana", "Post privado", 10, 2, false),
    Post("P7", "daniel", "Moçambique 🇲🇿", 500, 80)
)

println("\n=== DADOS INICIAIS ===")
println("Total de posts: ${posts.size}")

// ========== FILTER ==========

println("\n=== FILTER (filtrar) ===")

// Posts públicos
val postsPublicos = posts.filter { it.publico }
println("Posts públicos: ${postsPublicos.size}")

// Posts com mais de 100 likes
val postsPopulares = posts.filter { it.likes > 100 }
println("Posts com 100+ likes: ${postsPopulares.size}")

// Posts de um autor
val postsFrancisco = posts.filter { it.autor == "francisco" }
println("Posts de francisco: ${postsFrancisco.size}")

// Múltiplas condições
val postsVirais = posts.filter { it.likes > 200 && it.comentarios > 40 }
println("Posts virais: ${postsVirais.size}")

// filterNot (inverso)
val postsNaoVirais = posts.filterNot { it.likes > 200 }
println("Posts não virais: ${postsNaoVirais.size}")

// ========== MAP ==========

println("\n=== MAP (transformar) ===")

// Extrair só os autores
val autores = posts.map { it.autor }
println("Autores: $autores")

// Autores únicos
val autoresUnicos = posts.map { it.autor }.distinct()
println("Autores únicos: $autoresUnicos")

// Criar strings formatadas
val resumos = posts.map { "@${it.autor}: ${it.conteudo.take(20)}..." }
println("\nResumos:")
resumos.forEach { println("  $it") }

// Transformar em outro tipo
val likesDosPosts = posts.map { it.likes }
println("\nLikes: $likesDosPosts")

// Map com índice
val comIndice = posts.mapIndexed { index, post ->
    "#${index + 1}: ${post.conteudo}"
}
println("\nCom índice:")
comIndice.take(3).forEach { println("  $it") }

// ========== FLATMAP ==========

println("\n=== FLATMAP (achatar) ===")

data class Usuario(val nome: String, val tags: List<String>)

val usuarios = listOf(
    Usuario("francisco", listOf("kotlin", "android", "dev")),
    Usuario("ana", listOf("design", "ui", "kotlin")),
    Usuario("carlos", listOf("backend", "kotlin", "java"))
)

// Todas as tags (com duplicatas)
val todasTags = usuarios.flatMap { it.tags }
println("Todas tags: $todasTags")

// Tags únicas
val tagsUnicas = usuarios.flatMap { it.tags }.distinct()
println("Tags únicas: $tagsUnicas")

// Exemplo prático: todos os comentários de todos os posts
data class PostComComentarios(val id: String, val comentarios: List<String>)

val postsComentados = listOf(
    PostComComentarios("P1", listOf("Legal!", "Top!", "Ótimo!")),
    PostComComentarios("P2", listOf("Bom dia!")),
    PostComComentarios("P3", listOf("Obrigado", "Ajudou muito"))
)

val todosComentarios = postsComentados.flatMap { it.comentarios }
println("\nTodos comentários: $todosComentarios")

// ========== FOREACH ==========

println("\n=== FOREACH (iterar) ===")

posts.take(3).forEach { post ->
    println("📝 @${post.autor}: ${post.conteudo}")
    println("   ❤️ ${post.likes}  💬 ${post.comentarios}")
}

// ForEachIndexed
println("\nCom índice:")
posts.take(3).forEachIndexed { index, post ->
    println("${index + 1}. ${post.conteudo} (${post.likes} likes)")
}

// ========== ANY, ALL, NONE ==========

println("\n=== ANY, ALL, NONE ===")

// Algum post tem mais de 200 likes?
val temViral = posts.any { it.likes > 200 }
println("Tem post viral? $temViral")

// Todos os posts são públicos?
val todosPublicos = posts.all { it.publico }
println("Todos públicos? $todosPublicos")

// Nenhum post tem 0 likes?
val nenhumSemLikes = posts.none { it.likes == 0 }
println("Nenhum sem likes? $nenhumSemLikes")

// ========== FIND, FIRST, LAST ==========

println("\n=== FIND, FIRST, LAST ===")

// Encontrar primeiro post de um autor
val primeiroFrancisco = posts.find { it.autor == "francisco" }
println("Primeiro de francisco: ${primeiroFrancisco?.id}")

// Ou null se não encontrar
val postInexistente = posts.find { it.likes > 1000 }
println("Post com 1000+ likes: $postInexistente")

// First (lança exceção se não achar)
val primeiroPublico = posts.first { it.publico }
println("Primeiro público: ${primeiroPublico.id}")

// FirstOrNull (seguro)
val primeiroComMilLikes = posts.firstOrNull { it.likes > 1000 }
println("Primeiro com 1000+: $primeiroComMilLikes")

// Last
val ultimoPost = posts.last()
println("Último post: ${ultimoPost.id}")

// ========== TAKE, DROP ==========

println("\n=== TAKE, DROP ===")

// Primeiros 3
val primeiros3 = posts.take(3)
println("Primeiros 3: ${primeiros3.map { it.id }}")

// Últimos 2
val ultimos2 = posts.takeLast(2)
println("Últimos 2: ${ultimos2.map { it.id }}")

// Pular primeiros 2
val semPrimeiros2 = posts.drop(2)
println("Sem primeiros 2: ${semPrimeiros2.map { it.id }}")

// TakeWhile (enquanto condição for verdadeira)
val ateMenos100 = posts.takeWhile { it.likes < 100 }
println("Até menos de 100 likes: ${ateMenos100.map { it.id }}")

// ========== PARTITION ==========

println("\n=== PARTITION (dividir em 2) ===")

val (populares, normais) = posts.partition { it.likes > 150 }
println("Populares (150+): ${populares.size}")
println("Normais (<150): ${normais.size}")

// ========== GROUPBY ==========

println("\n=== GROUPBY (agrupar) ===")

// Agrupar por autor
val porAutor = posts.groupBy { it.autor }
println("\nPosts por autor:")
porAutor.forEach { (autor, postsdoAutor) ->
    println("  @$autor: ${postsdoAutor.size} post(s)")
}

// Agrupar por faixa de likes
val porFaixaLikes = posts.groupBy { post ->
    when {
        post.likes < 100 -> "Baixo"
        post.likes < 200 -> "Médio"
        else -> "Alto"
    }
}
println("\nPor faixa de likes:")
porFaixaLikes.forEach { (faixa, postsdaFaixa) ->
    println("  $faixa: ${postsdaFaixa.size}")
}

// ========== SORTEDBY ==========

println("\n=== SORTED (ordenar) ===")

// Ordenar por likes (crescente)
val porLikes = posts.sortedBy { it.likes }
println("\nMenos curtidos:")
porLikes.take(3).forEach { println("  ${it.id}: ${it.likes} likes") }

// Ordenar por likes (decrescente)
val porLikesDesc = posts.sortedByDescending { it.likes }
println("\nMais curtidos:")
porLikesDesc.take(3).forEach { println("  ${it.id}: ${it.likes} likes") }

// Múltiplos critérios
val ordenadoComplexo = posts.sortedWith(
    compareByDescending<Post> { it.likes }
        .thenBy { it.autor }
)
println("\nOrdenado (likes desc, depois autor):")
ordenadoComplexo.take(3).forEach { 
    println("  ${it.likes} - @${it.autor}")
}

// ========== DISTINCT ==========

println("\n=== DISTINCT (únicos) ===")

val autoresDuplicados = posts.map { it.autor }
val autoresSemDuplicatas = autoresDuplicados.distinct()
println("Autores (com duplicatas): ${autoresDuplicados.size}")
println("Autores (únicos): ${autoresSemDuplicatas.size}")

// DistinctBy (único por critério)
val umPostPorAutor = posts.distinctBy { it.autor }
println("Um post por autor: ${umPostPorAutor.size}")

// ========== ASSOCIATEBY, ASSOCIATEWITH ==========

println("\n=== ASSOCIATE (criar maps) ===")

// ID → Post
val mapPorId = posts.associateBy { it.id }
println("\nAcessar por ID:")
println("Post P3: ${mapPorId["P3"]?.conteudo}")

// Autor → Total de likes
val likesPorAutor = posts.groupBy { it.autor }
    .mapValues { (_, posts) -> posts.sumOf { it.likes } }
println("\nLikes por autor:")
likesPorAutor.forEach { (autor, total) ->
    println("  @$autor: $total likes")
}

// ========== CHUNKED ==========

println("\n=== CHUNKED (dividir em pedaços) ===")

val numeros = (1..10).toList()
val emGruposDe3 = numeros.chunked(3)
println("Em grupos de 3: $emGruposDe3")

// Útil para paginação
val postsPaginados = posts.chunked(3)
println("\nPáginas (3 posts cada):")
postsPaginados.forEachIndexed { index, pagina ->
    println("  Página ${index + 1}: ${pagina.size} posts")
}

// ========== WINDOWED ==========

println("\n=== WINDOWED (janelas deslizantes) ===")

val janelas = numeros.windowed(3)
println("Janelas de 3: $janelas")

// Útil para análises de tendências
val trending = posts.windowed(3).map { window ->
    window.sumOf { it.likes }
}
println("Likes em janelas de 3: $trending")

// ========== ZIP ==========

println("\n=== ZIP (combinar 2 listas) ===")

val ids = listOf("P1", "P2", "P3")
val likes = listOf(100, 200, 300)

val combinado = ids.zip(likes)
println("Combinado: $combinado")

val comTransformacao = ids.zip(likes) { id, likes ->
    "$id tem $likes likes"
}
println("Com transformação: $comTransformacao")
