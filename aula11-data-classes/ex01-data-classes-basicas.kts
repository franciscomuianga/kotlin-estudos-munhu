println("═══════════════════════════════════════")
println("    MUNHU - DATA CLASSES BÁSICAS")
println("═══════════════════════════════════════")

// ========== CLASSE NORMAL vs DATA CLASS ==========

class UsuarioNormal(val nome: String, val idade: Int)

data class UsuarioData(val nome: String, val idade: Int)

println("\n=== CLASSE NORMAL vs DATA CLASS ===")

val normal1 = UsuarioNormal("Francisco", 17)
val normal2 = UsuarioNormal("Francisco", 17)

val data1 = UsuarioData("Francisco", 17)
val data2 = UsuarioData("Francisco", 17)

println("Classes Normais:")
println("normal1 == normal2: ${normal1 == normal2}")  // false (referências diferentes)
println("normal1: $normal1")  // toString feio

println("\nData Classes:")
println("data1 == data2: ${data1 == data2}")  // true (compara valores!)
println("data1: $data1")  // toString bonito!

// ========== MÉTODOS GERADOS AUTOMATICAMENTE ==========

data class Post(
    val id: String,
    val autor: String,
    val conteudo: String,
    val likes: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)

println("\n=== MÉTODOS GERADOS ===")

val post1 = Post("POST_1", "francisco_raul", "Estudando data classes!")
val post2 = Post("POST_1", "francisco_raul", "Estudando data classes!")
val post3 = Post("POST_2", "ana_silva", "Outro post")

// 1. toString() - representação legível
println("\n1. toString():")
println(post1)

// 2. equals() - compara valores
println("\n2. equals():")
println("post1 == post2: ${post1 == post2}")  // true
println("post1 == post3: ${post1 == post3}")  // false

// 3. hashCode() - para usar em mapas/sets
println("\n3. hashCode():")
println("post1.hashCode(): ${post1.hashCode()}")
println("post2.hashCode(): ${post2.hashCode()}")  // Mesmo hash!
println("post3.hashCode(): ${post3.hashCode()}")  // Hash diferente

// 4. copy() - copia com modificações
println("\n4. copy():")
val post1Editado = post1.copy(conteudo = "Conteúdo atualizado!")
println("Original: $post1")
println("Editado:  $post1Editado")

val post1ComMaisLikes = post1.copy(likes = 150)
println("Com likes: $post1ComMaisLikes")

// ========== DESTRUCTURING DECLARATIONS ==========

data class Usuario(
    val id: String,
    val username: String,
    val nome: String,
    val email: String
)

println("\n=== DESTRUCTURING ===")

val usuario = Usuario("U1", "francisco_raul", "Francisco Raul", "francisco@munhu.co.mz")

// Destructuring - extrai propriedades
val (id, username, nome, email) = usuario

println("id: $id")
println("username: $username")
println("nome: $nome")
println("email: $email")

// Pode ignorar valores com _
val (userId, _, nomeCompleto) = usuario
println("\nIgnorando username: $userId, $nomeCompleto")

// ========== COMPONENTN FUNCTIONS ==========

println("\n=== COMPONENT FUNCTIONS ===")

println("component1(): ${usuario.component1()}")  // id
println("component2(): ${usuario.component2()}")  // username
println("component3(): ${usuario.component3()}")  // nome
println("component4(): ${usuario.component4()}")  // email

// Útil em loops
val usuarios = listOf(
    Usuario("U1", "francisco", "Francisco", "f@mail.com"),
    Usuario("U2", "ana", "Ana", "a@mail.com"),
    Usuario("U3", "carlos", "Carlos", "c@mail.com")
)

println("\nDestructuring em loop:")
for ((id, username) in usuarios) {
    println("  @$username (ID: $id)")
}

// ========== DATA CLASS EM COLEÇÕES ==========

println("\n=== DATA CLASSES EM COLEÇÕES ===")

data class Comentario(val autor: String, val texto: String, val likes: Int = 0)

val comentarios = listOf(
    Comentario("ana", "Ótimo post!", 10),
    Comentario("carlos", "Concordo!", 5),
    Comentario("beatriz", "Legal!", 3),
    Comentario("ana", "Ótimo post!", 10)  // Duplicado
)

println("Total comentários: ${comentarios.size}")

// Set remove duplicados automaticamente (usa equals)
val comentariosUnicos = comentarios.toSet()
println("Comentários únicos: ${comentariosUnicos.size}")

// Agrupar por autor
val porAutor = comentarios.groupBy { it.autor }
println("\nComentários por autor:")
porAutor.forEach { (autor, comments) ->
    println("  @$autor: ${comments.size} comentário(s)")
}

// Ordenar por likes
val ordenadosPorLikes = comentarios.sortedByDescending { it.likes }
println("\nTop 3 comentários:")
ordenadosPorLikes.take(3).forEach { (autor, texto, likes) ->
    println("  @$autor ($likes ❤️): \"$texto\"")
}

// ========== DATA CLASS COMO CHAVE DE MAP ==========

data class PostId(val id: String)

println("\n=== DATA CLASS COMO CHAVE ===")

val cachePosts = mutableMapOf<PostId, Post>()

val postId1 = PostId("POST_1")
val post = Post("POST_1", "francisco", "Conteúdo")

cachePosts[postId1] = post

// Funciona mesmo com nova instância (usa equals!)
val postId1Copy = PostId("POST_1")
println("Post no cache: ${cachePosts[postId1Copy]}")

// ========== IMUTABILIDADE ==========

data class Perfil(
    val username: String,
    val nome: String,
    val bio: String = ""
)

println("\n=== IMUTABILIDADE (val) ===")

val perfil = Perfil("francisco", "Francisco Raul")
// perfil.nome = "Outro"  // ❌ Erro! val é imutável

// Para modificar, usa copy
val perfilAtualizado = perfil.copy(bio = "Desenvolvedor 🇲🇿")
println("Original: $perfil")
println("Atualizado: $perfilAtualizado")

// ========== DATA CLASS COM var (mutável) ==========

data class Estatisticas(
    var posts: Int = 0,
    var seguidores: Int = 0,
    var seguindo: Int = 0
)

println("\n=== MUTABILIDADE (var) ===")

val stats = Estatisticas(10, 500, 200)
println("Antes: $stats")

stats.posts++
stats.seguidores += 50
println("Depois: $stats")

// ⚠️ PROBLEMA: mutabilidade quebra equals/hashCode
val stats2 = Estatisticas(10, 500, 200)
val set = mutableSetOf(stats2)

stats2.posts = 20  // Modificou!
println("\nstats2 ainda no set? ${stats2 in set}")  // false! HashCode mudou!

println("\n💡 REGRA: Prefira 'val' em data classes para evitar problemas!")

// ========== VALORES PADRÃO ==========

data class ConfiguracaoPost(
    val conteudo: String,
    val publica: Boolean = true,
    val comentariosAtivos: Boolean = true,
    val localizacao: String? = null,
    val tags: List<String> = emptyList()
)

println("\n=== VALORES PADRÃO ===")

val post1Config = ConfiguracaoPost("Meu post")
println(post1Config)

val post2Config = ConfiguracaoPost(
    conteudo = "Post privado",
    publica = false,
    tags = listOf("kotlin", "programming")
)
println(post2Config)

// ========== COPY COM APENAS O QUE MUDA ==========

println("\n=== COPY INTELIGENTE ===")

val postOriginal = Post("P1", "francisco", "Conteúdo original", likes = 10)
println("Original: $postOriginal")

// Só muda o que precisa
val postEditado = postOriginal.copy(conteudo = "Conteúdo editado")
println("Editado: $postEditado")

val postViral = postOriginal.copy(likes = 1000)
println("Viral: $postViral")
