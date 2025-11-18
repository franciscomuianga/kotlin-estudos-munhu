println("═══════════════════════════════════════")
println("    MUNHU - DATA CLASSES COMPLEXAS")
println("═══════════════════════════════════════")

// ========== DATA CLASSES ANINHADAS ==========

data class Endereco(
    val rua: String,
    val cidade: String,
    val provincia: String,
    val pais: String = "Moçambique"
)

data class Perfil(
    val username: String,
    val nome: String,
    val bio: String,
    val endereco: Endereco,
    val verificado: Boolean = false
)

println("\n=== DATA CLASSES ANINHADAS ===")

val endereco = Endereco(
    rua = "Av. Julius Nyerere",
    cidade = "Maputo",
    provincia = "Maputo"
)

val perfil = Perfil(
    username = "francisco_raul",
    nome = "Francisco Raul Muianga Junior",
    bio = "Desenvolvedor | THE FRA LABS",
    endereco = endereco
)

println(perfil)

// Copy aninhado - precisa copiar também o nested
val perfilNovaRua = perfil.copy(
    endereco = perfil.endereco.copy(rua = "Av. 24 de Julho")
)

println("\nNovo endereço: ${perfilNovaRua.endereco}")

// ========== DATA CLASS COM LISTAS ==========

data class Notificacao(
    val tipo: String,
    val mensagem: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class Usuario(
    val id: String,
    val username: String,
    val notificacoes: List<Notificacao> = emptyList()
)

println("\n=== DATA CLASS COM LISTAS ===")

val usuario = Usuario(
    id = "U1",
    username = "francisco",
    notificacoes = listOf(
        Notificacao("curtida", "@ana curtiu seu post"),
        Notificacao("seguidor", "@carlos te seguiu"),
        Notificacao("comentario", "@beatriz comentou")
    )
)

println("${usuario.username} tem ${usuario.notificacoes.size} notificações")

// Adicionar notificação (imutável)
val novaNotif = Notificacao("mencao", "@daniel te mencionou")
val usuarioAtualizado = usuario.copy(
    notificacoes = usuario.notificacoes + novaNotif
)

println("Agora tem: ${usuarioAtualizado.notificacoes.size} notificações")

// ========== DATA CLASS HIERÁRQUICA ==========

data class PostMetadata(
    val visualizacoes: Int = 0,
    val compartilhamentos: Int = 0,
    val salvamentos: Int = 0
)

data class Autor(
    val id: String,
    val username: String,
    val verificado: Boolean = false
)

data class PostCompleto(
    val id: String,
    val autor: Autor,
    val conteudo: String,
    val likes: Int = 0,
    val comentarios: List<String> = emptyList(),
    val metadata: PostMetadata = PostMetadata(),
    val timestamp: Long = System.currentTimeMillis()
)

println("\n=== HIERARQUIA COMPLEXA ===")

val autor = Autor("A1", "francisco_raul", true)
val metadata = PostMetadata(visualizacoes = 500, compartilhamentos = 20)

val post = PostCompleto(
    id = "P1",
    autor = autor,
    conteudo = "Data classes são incríveis!",
    likes = 150,
    comentarios = listOf("Verdade!", "Concordo!", "Top!"),
    metadata = metadata
)

println(post)

// Destructuring com aninhamento
val (postId, autorInfo, conteudo) = post
println("\nPost: $postId")
println("Autor: ${autorInfo.username} ${if (autorInfo.verificado) "✓" else ""}")
println("Conteúdo: $conteudo")

// ========== TRANSFORMAÇÕES ==========

println("\n=== TRANSFORMAÇÕES ===")

// Map sobre lista aninhada
val postsComComentarios = listOf(
    PostCompleto("P1", autor, "Post 1", comentarios = listOf("A", "B")),
    PostCompleto("P2", autor, "Post 2", comentarios = listOf("C")),
    PostCompleto("P3", autor, "Post 3", comentarios = listOf("D", "E", "F"))
)

val totalComentarios = postsComComentarios.sumOf { it.comentarios.size }
println("Total de comentários: $totalComentarios")

// Posts mais comentados
val maisComentados = postsComComentarios
    .sortedByDescending { it.comentarios.size }
    .take(2)

println("\nPosts mais comentados:")
maisComentados.forEach { post ->
    println("  ${post.id}: ${post.comentarios.size} comentários")
}

// ========== DATA CLASS PARA DTOs (Data Transfer Objects) ==========

data class LoginRequest(
    val email: String,
    val senha: String
)

data class LoginResponse(
    val sucesso: Boolean,
    val token: String? = null,
    val mensagem: String? = null,
    val usuario: Usuario? = null
)

data class CriarPostRequest(
    val autorId: String,
    val conteudo: String,
    val publico: Boolean = true,
    val tags: List<String> = emptyList()
)

data class CriarPostResponse(
    val sucesso: Boolean,
    val postId: String? = null,
    val erro: String? = null
)

println("\n=== DTOs (DATA TRANSFER OBJECTS) ===")

// Simulando requisição de login
val loginReq = LoginRequest("francisco@munhu.co.mz", "senha123")
println("Login Request: $loginReq")

// Resposta de sucesso
val loginResp = LoginResponse(
    sucesso = true,
    token = "TOKEN_ABC123",
    usuario = Usuario("U1", "francisco")
)
println("Login Response: $loginResp")

// Resposta de erro
val loginErro = LoginResponse(
    sucesso = false,
    mensagem = "Credenciais inválidas"
)
println("Login Erro: $loginErro")

// ========== VALIDAÇÕES COM DATA CLASSES ==========

data class Email(val valor: String) {
    init {
        require(valor.contains("@")) { "Email inválido: $valor" }
    }
}

data class Username(val valor: String) {
    init {
        require(valor.length >= 3) { "Username muito curto: $valor" }
        require(valor.all { it.isLetterOrDigit() || it == '_' }) {
            "Username com caracteres inválidos: $valor"
        }
    }
}

data class UsuarioValidado(
    val id: String,
    val username: Username,
    val email: Email
)

println("\n=== VALIDAÇÕES ===")

try {
    val emailValido = Email("francisco@munhu.co.mz")
    val usernameValido = Username("francisco_raul")
    val usuario = UsuarioValidado("U1", usernameValido, emailValido)
    println("✅ Usuário válido: $usuario")
} catch (e: IllegalArgumentException) {
    println("❌ Erro: ${e.message}")
}

println("\nTestando validações inválidas:")

try {
    val emailInvalido = Email("email-sem-arroba")
} catch (e: IllegalArgumentException) {
    println("❌ ${e.message}")
}

try {
    val usernameInvalido = Username("ab")  // Muito curto
} catch (e: IllegalArgumentException) {
    println("❌ ${e.message}")
}

// ========== PADRÃO: RESULT WRAPPER ==========

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val mensagem: String, val codigo: Int) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

fun buscarUsuario(id: String): Result<Usuario> {
    return if (id.startsWith("U")) {
        Result.Success(Usuario(id, "usuario_$id"))
    } else {
        Result.Error("Usuário não encontrado", 404)
    }
}

println("\n=== RESULT WRAPPER ===")

when (val resultado = buscarUsuario("U123")) {
    is Result.Success -> {
        println("✅ Usuário encontrado: ${resultado.data.username}")
    }
    is Result.Error -> {
        println("❌ Erro ${resultado.codigo}: ${resultado.mensagem}")
    }
    is Result.Loading -> {
        println("⏳ Carregando...")
    }
}

when (val resultado = buscarUsuario("INVALIDO")) {
    is Result.Success -> {
        println("✅ Usuário: ${resultado.data}")
    }
    is Result.Error -> {
        println("❌ Erro ${resultado.codigo}: ${resultado.mensagem}")
    }
    is Result.Loading -> {
        println("⏳ Carregando...")
    }
}

// ========== PADRÃO: STATE ==========

data class FeedState(
    val posts: List<PostCompleto> = emptyList(),
    val carregando: Boolean = false,
    val erro: String? = null,
    val paginaAtual: Int = 1,
    val temMais: Boolean = true
)

println("\n=== STATE PATTERN ===")

var feedState = FeedState(carregando = true)
println("Estado inicial: $feedState")

// Simula carregamento
Thread.sleep(50)
feedState = feedState.copy(
    posts = listOf(
        PostCompleto("P1", autor, "Post 1"),
        PostCompleto("P2", autor, "Post 2")
    ),
    carregando = false
)
println("Após carregar: $feedState")

// Carregar mais
feedState = feedState.copy(
    posts = feedState.posts + PostCompleto("P3", autor, "Post 3"),
    paginaAtual = 2
)
println("Página 2: $feedState")

// ========== COPY PROFUNDO (DEEP COPY) ==========

data class Config(val tema: String)
data class App(val nome: String, val config: Config)

println("\n=== COPY PROFUNDO ===")

val app1 = App("Munhu", Config("claro"))
val app2 = app1.copy()  // Shallow copy

println("app1: $app1")
println("app2: $app2")
println("São iguais? ${app1 == app2}")  // true
println("Mesma referência de config? ${app1.config === app2.config}")  // true!

// Para deep copy, precisa copiar nested também
val app3 = app1.copy(config = app1.config.copy())
println("app3 config é diferente? ${app1.config !== app3.config}")  // true
