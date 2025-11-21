println("═══════════════════════════════════════")
println("    MUNHU - COLEÇÕES BÁSICAS")
println("═══════════════════════════════════════")

// ========== LIST (IMUTÁVEL) ==========

val usuarios = listOf("francisco", "ana", "carlos", "beatriz")

println("\n=== LIST IMUTÁVEL ===")
println("Usuários: $usuarios")
println("Tamanho: ${usuarios.size}")
println("Primeiro: ${usuarios.first()}")
println("Último: ${usuarios.last()}")
println("Índice 2: ${usuarios[2]}")

// usuarios.add("novo")  // ❌ Erro! Lista imutável

// Verificações
println("\nContém 'ana'? ${usuarios.contains("ana")}")
println("'daniel' está na lista? ${"daniel" in usuarios}")
println("Está vazia? ${usuarios.isEmpty()}")

// ========== MUTABLE LIST ==========

val posts = mutableListOf("Post 1", "Post 2")

println("\n=== MUTABLE LIST ===")
println("Posts: $posts")

posts.add("Post 3")
posts.add("Post 4")
println("Após adicionar: $posts")

posts.remove("Post 2")
println("Após remover: $posts")

posts[0] = "Post editado"
println("Após editar: $posts")

posts.clear()
println("Após limpar: $posts")

// ========== SET (SEM DUPLICADOS) ==========

val tags = setOf("kotlin", "android", "kotlin", "programming")

println("\n=== SET (sem duplicados) ===")
println("Tags: $tags")  // kotlin aparece só 1 vez!
println("Tamanho: ${tags.size}")

// Mutable Set
val seguidores = mutableSetOf("ana", "carlos")
seguidores.add("beatriz")
seguidores.add("ana")  // Não adiciona (já existe)
println("\nSeguidores: $seguidores")

// ========== MAP (CHAVE-VALOR) ==========

val likes = mapOf(
    "POST_1" to 150,
    "POST_2" to 89,
    "POST_3" to 234
)

println("\n=== MAP (chave-valor) ===")
println("Likes: $likes")
println("Likes do POST_1: ${likes["POST_1"]}")
println("Likes do POST_4: ${likes["POST_4"]}")  // null

// Mutable Map
val comentarios = mutableMapOf(
    "POST_1" to 10,
    "POST_2" to 5
)

comentarios["POST_3"] = 15
comentarios["POST_1"] = 20  // Atualiza
println("\nComentários: $comentarios")

// Iterar sobre Map
println("\nIterando:")
for ((postId, qtd) in comentarios) {
    println("  $postId: $qtd comentários")
}

// ========== CONVERSÕES ==========

println("\n=== CONVERSÕES ===")

val lista = listOf(1, 2, 2, 3, 3, 3)
val conjunto = lista.toSet()  // Remove duplicados
println("Lista: $lista")
println("Set: $conjunto")

val map = mapOf("a" to 1, "b" to 2)
val listaDePares = map.toList()
println("\nMap: $map")
println("Lista de pares: $listaDePares")

// ========== OPERAÇÕES BÁSICAS ==========

val numeros = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

println("\n=== OPERAÇÕES BÁSICAS ===")

// Slice (fatia)
println("Primeiros 3: ${numeros.take(3)}")
println("Últimos 3: ${numeros.takeLast(3)}")
println("Pular 3 primeiros: ${numeros.drop(3)}")

// Índices
println("\nÍndice do 5: ${numeros.indexOf(5)}")
println("Contém 7? ${7 in numeros}")

// SubList
println("\nDo índice 2 ao 5: ${numeros.subList(2, 5)}")

// Concatenação
val mais = numeros + listOf(11, 12)
println("\nConcatenação: $mais")

// ========== NULLABLES ==========

val listaMista: List<String?> = listOf("a", null, "b", null, "c")

println("\n=== LIDANDO COM NULLS ===")
println("Lista com nulls: $listaMista")

val semNulls = listaMista.filterNotNull()
println("Sem nulls: $semNulls")

val primeiro = listaMista.firstOrNull()
println("\nPrimeiro (pode ser null): $primeiro")

val inexistente = listaMista.find { it == "z" }
println("Buscar 'z': $inexistente")  // null

// ========== RANGES E PROGRESSÕES ==========

println("\n=== RANGES ===")

val range1 = 1..10
println("1..10: ${range1.toList()}")

val range2 = 1 until 10  // Não inclui 10
println("1 until 10: ${range2.toList()}")

val range3 = 10 downTo 1
println("10 downTo 1: ${range3.toList()}")

val range4 = 1..20 step 2
println("1..20 step 2: ${range4.toList()}")

val letras = 'a'..'e'
println("\n'a'..'e': ${letras.toList()}")

// ========== EXEMPLO PRÁTICO: FEED ==========

data class Post(val id: String, val autor: String, val likes: Int)

val feed = listOf(
    Post("P1", "francisco", 150),
    Post("P2", "ana", 89),
    Post("P3", "carlos", 234),
    Post("P4", "beatriz", 45),
    Post("P5", "francisco", 180)
)

println("\n=== FEED DE POSTS ===")
println("Total de posts: ${feed.size}")
println("Primeiro post: ${feed.first()}")
println("Último post: ${feed.last()}")

// Acessar com segurança
val postSeguro = feed.getOrNull(10)
println("Post índice 10: $postSeguro")  // null

// Posts de um autor específico
val postsFrancisco = feed.filter { it.autor == "francisco" }
println("\nPosts de francisco: ${postsFrancisco.size}")

// Total de likes
val totalLikes = feed.sumOf { it.likes }
println("Total de likes no feed: $totalLikes")

// ========== COLEÇÕES VAZIAS ==========

println("\n=== COLEÇÕES VAZIAS ===")

val listaVazia1 = emptyList<String>()
val listaVazia2 = listOf<String>()
val setVazio = emptySet<Int>()
val mapVazio = emptyMap<String, Int>()

println("Lista vazia: $listaVazia1")
println("Set vazio: $setVazio")
println("Map vazio: $mapVazio")

// Verificação
if (listaVazia1.isEmpty()) {
    println("Lista está vazia!")
}

// ========== ARRAY (não muito usado) ==========

val array = arrayOf(1, 2, 3, 4, 5)
println("\n=== ARRAY ===")
println("Array: ${array.contentToString()}")
println("Para lista: ${array.toList()}")

// Arrays são mutáveis mas têm tamanho fixo
array[0] = 10
println("Modificado: ${array.contentToString()}")
