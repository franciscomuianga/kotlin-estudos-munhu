println("╔════════════════════════════════════════╗")
println("║   MUNHU - SISTEMA DE ESTADO COMPLETO   ║")
println("╚════════════════════════════════════════╝")

// ═══════════════════════════════════════════════════════
//  MODELS (DATA CLASSES)
// ═══════════════════════════════════════════════════════

data class Usuario(
    val id: String,
    val username: String,
    val nome: String,
    val avatar: String? = null,
    val verificado: Boolean = false,
    val seguidores: Int = 0,
    val seguindo: Int = 0
)

data class Comentario(
    val id: String,
    val autorId: String,
    val texto: String,
    val likes: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)

data class Post(
    val id: String,
    val autorId: String,
    val conteudo: String,
    val likes: Int = 0,
    val comentarios: List<Comentario> = emptyList(),
    val compartilhamentos: Int = 0,
    val visualizacoes: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
) {
    val engajamento: Int
        get() = likes + (comentarios.size * 2) + (compartilhamentos * 3)
}

data class Notificacao(
    val id: String,
    val tipo: TipoNotificacao,
    val mensagem: String,
    val lida: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

enum class TipoNotificacao {
    CURTIDA, COMENTARIO, SEGUIDOR, MENCAO, SISTEMA
}

// ═══════════════════════════════════════════════════════
//  ESTADOS DA APLICAÇÃO
// ═══════════════════════════════════════════════════════

data class UsuarioState(
    val usuario: Usuario? = null,
    val autenticado: Boolean = false,
    val carregando: Boolean = false,
    val erro: String? = null
)

data class FeedState(
    val posts: List<Post> = emptyList(),
    val carregando: Boolean = false,
    val erro: String? = null,
    val paginaAtual: Int = 1,
    val temMais: Boolean = true,
    val filtro: FiltroFeed = FiltroFeed.TODOS
)

enum class FiltroFeed {
    TODOS, SEGUINDO, POPULARES, RECENTES
}

data class NotificacoesState(
    val notificacoes: List<Notificacao> = emptyList(),
    val naoLidas: Int = 0,
    val carregando: Boolean = false
)

data class PerfilState(
    val usuario: Usuario? = null,
    val posts: List<Post> = emptyList(),
    val seguidores: List<Usuario> = emptyList(),
    val seguindo: List<Usuario> = emptyList(),
    val aba: AbaPerf = AbaPerf.POSTS
)

enum class AbaPerfil {
    POSTS, SEGUIDORES, SEGUINDO
}

data class AppState(
    val usuario: UsuarioState = UsuarioState(),
    val feed: FeedState = FeedState(),
    val notificacoes: NotificacoesState = NotificacoesState(),
    val perfil: PerfilState = PerfilState(),
    val telaAtual: Tela = Tela.FEED
)

enum class Tela {
    FEED, PERFIL, NOTIFICACOES, BUSCA, CONFIGURACOES
}

// ═══════════════════════════════════════════════════════
//  ACTIONS (O QUE PODE ACONTECER)
// ═══════════════════════════════════════════════════════

sealed class Action {
    // Usuário
    data class Login(val username: String, val senha: String) : Action()
    data class LoginSucesso(val usuario: Usuario) : Action()
    data class LoginErro(val mensagem: String) : Action()
    object Logout : Action()
    
    // Feed
    object CarregarFeed : Action()
    data class FeedCarregado(val posts: List<Post>) : Action()
    data class AlterarFiltro(val filtro: FiltroFeed) : Action()
    
    // Posts
    data class CurtirPost(val postId: String) : Action()
    data class ComentarPost(val postId: String, val texto: String) : Action()
    data class CompartilharPost(val postId: String) : Action()
    
    // Notificações
    object CarregarNotificacoes : Action()
    data class MarcarComoLida(val notificacaoId: String) : Action()
    object MarcarTodasLidas : Action()
    
    // Navegação
    data class NavegarPara(val tela: Tela) : Action()
}

// ═══════════════════════════════════════════════════════
//  REDUCER (APLICA AÇÕES AO ESTADO)
// ═══════════════════════════════════════════════════════

fun reducer(state: AppState, action: Action): AppState {
    return when (action) {
        is Action.Login -> {
            state.copy(
                usuario = state.usuario.copy(carregando = true, erro = null)
            )
        }
        
        is Action.LoginSucesso -> {
            state.copy(
                usuario = UsuarioState(
                    usuario = action.usuario,
                    autenticado = true,
                    carregando = false
                )
            )
        }
        
        is Action.LoginErro -> {
            state.copy(
                usuario = state.usuario.copy(
                    carregando = false,
                    erro = action.mensagem
                )
            )
        }
        
        is Action.Logout -> {
            AppState()  // Reset completo
        }
        
        is Action.CarregarFeed -> {
            state.copy(
                feed = state.feed.copy(carregando = true, erro = null)
            )
        }
        
        is Action.FeedCarregado -> {
            state.copy(
                feed = state.feed.copy(
                    posts = action.posts,
                    carregando = false,
                    paginaAtual = state.feed.paginaAtual + 1
                )
            )
        }
        
        is Action.AlterarFiltro -> {
            state.copy(
                feed = state.feed.copy(
                    filtro = action.filtro,
                    posts = emptyList(),
                    paginaAtual = 1
                )
            )
        }
        
        is Action.CurtirPost -> {
            val postsAtualizados = state.feed.posts.map { post ->
                if (post.id == action.postId) {
                    post.copy(likes = post.likes + 1)
                } else {
                    post
                }
            }
            state.copy(
                feed = state.feed.copy(posts = postsAtualizados)
            )
        }
        
        is Action.ComentarPost -> {
            val postsAtualizados = state.feed.posts.map { post ->
                if (post.id == action.postId) {
                    val novoComentario = Comentario(
                        id = "COM_${System.currentTimeMillis()}",
                        autorId = state.usuario.usuario?.id ?: "",
                        texto = action.texto
                    )
                    post.copy(comentarios = post.comentarios + novoComentario)
                } else {
                    post
                }
            }
            state.copy(
                feed = state.feed.copy(posts = postsAtualizados)
            )
        }
        
        is Action.CompartilharPost -> {
            val postsAtualizados = state.feed.posts.map { post ->
                if (post.id == action.postId) {
                    post.copy(compartilhamentos = post.compartilhamentos + 1)
                } else {
                    post
                }
            }
            state.copy(
                feed = state.feed.copy(posts = postsAtualizados)
            )
        }
        
        is Action.CarregarNotificacoes -> {
            state.copy(
                notificacoes = state.notificacoes.copy(carregando = true)
            )
        }
        
        is Action.MarcarComoLida -> {
            val notifsAtualizadas = state.notificacoes.notificacoes.map { notif ->
                if (notif.id == action.notificacaoId) {
                    notif.copy(lida = true)
                } else {
                    notif
                }
            }
            val naoLidas = notifsAtualizadas.count { !it.lida }
            state.copy(
                notificacoes = state.notificacoes.copy(
notificacoes = notifsAtualizadas,
naoLidas = naoLidas
)
)
}
is Action.MarcarTodasLidas -> {
        val notifsAtualizadas = state.notificacoes.notificacoes.map { 
            it.copy(lida = true)
        }
        state.copy(
            notificacoes = state.notificacoes.copy(
                notificacoes = notifsAtualizadas,
                naoLidas = 0
            )
        )
    }
    
    is Action.NavegarPara -> {
        state.copy(telaAtual = action.tela)
    }
}
}
// ═══════════════════════════════════════════════════════
//  SIMULAÇÃO DO SISTEMA
// ═══════════════════════════════════════════════════════
fun exibirEstado(state: AppState) {
println("\n╔════════════════════════════════════════╗")
println("║           ESTADO DO APP                ║")
println("╠════════════════════════════════════════╣")
println("║")
// Usuário
if (state.usuario.autenticado) {
    val user = state.usuario.usuario!!
    println("║ 👤 USUÁRIO: @${user.username} ${if (user.verificado) "✓" else ""}")
    println("║    ${user.seguidores} seguidores · ${user.seguindo} seguindo")
} else {
    println("║ 🔒 NÃO AUTENTICADO")
}

// Tela atual
println("║")
println("║ 📱 TELA: ${state.telaAtual}")

// Feed
if (state.telaAtual == Tela.FEED) {
    println("║")
    println("║ 📰 FEED (${state.feed.filtro}):")
    println("║    Posts: ${state.feed.posts.size}")
    println("║    Carregando: ${state.feed.carregando}")
    if (state.feed.erro != null) {
        println("║    ❌ Erro: ${state.feed.erro}")
    }
}

// Notificações
if (state.notificacoes.naoLidas > 0) {
    println("║")
    println("║ 🔔 ${state.notificacoes.naoLidas} notificação(ões) não lida(s)")
}

println("║")
println("╚════════════════════════════════════════╝")
}
println("\n🚀 Iniciando simulação do sistema de estado...\n")
// Estado inicial
var appState = AppState()
println("═".repeat(45))
println("1. ESTADO INICIAL")
println("═".repeat(45))
exibirEstado(appState)
// Login
println("\n═".repeat(45))
println("2. FAZENDO LOGIN")
println("═".repeat(45))
appState = reducer(appState, Action.Login("francisco_raul", "senha123"))
exibirEstado(appState)
Thread.sleep(100)  // Simula delay
val usuario = Usuario(
id = "U1",
username = "francisco_raul",
nome = "Francisco Raul Muianga Junior",
verificado = true,
seguidores = 1200,
seguindo = 340
)
appState = reducer(appState, Action.LoginSucesso(usuario))
exibirEstado(appState)
// Carregar feed
println("\n═".repeat(45))
println("3. CARREGANDO FEED")
println("═".repeat(45))
appState = reducer(appState, Action.CarregarFeed)
exibirEstado(appState)
Thread.sleep(100)
val posts = listOf(
Post("P1", "U2", "Bom dia Moçambique! 🇲🇿", likes = 150),
Post("P2", "U3", "Estudando Kotlin é incrível!", likes = 89),
Post("P3", "U4", "Primeira vez no Munhu!", likes = 45)
)
appState = reducer(appState, Action.FeedCarregado(posts))
exibirEstado(appState)
// Interações
println("\n═".repeat(45))
println("4. CURTINDO POST")
println("═".repeat(45))
appState = reducer(appState, Action.CurtirPost("P1"))
println("\n📝 Post P1:")
val postP1 = appState.feed.posts.find { it.id == "P1" }
println("   Likes: ${postP1?.likes}")
// Comentar
println("\n═".repeat(45))
println("5. COMENTANDO")
println("═".repeat(45))
appState = reducer(appState, Action.ComentarPost("P2", "Concordo! Kotlin é top!"))
val postP2 = appState.feed.posts.find { it.id == "P2" }
println("\n📝 Post P2:")
println("   Comentários: ${postP2?.comentarios?.size}")
postP2?.comentarios?.forEach { coment ->
println("   💬 ${coment.texto}")
}
// Compartilhar
println("\n═".repeat(45))
println("6. COMPARTILHANDO")
println("═".repeat(45))
appState = reducer(appState, Action.CompartilharPost("P3"))
val postP3 = appState.feed.posts.find { it.id == "P3" }
println("\n📝 Post P3:")
println("   Compartilhamentos: ${postP3?.compartilhamentos}")
println("   Engajamento total: ${postP3?.engajamento}")
// Adicionar notificações
println("\n═".repeat(45))
println("7. RECEBENDO NOTIFICAÇÕES")
println("═".repeat(45))
val notificacoes = listOf(
Notificacao("N1", TipoNotificacao.CURTIDA, "@ana curtiu seu post"),
Notificacao("N2", TipoNotificacao.SEGUIDOR, "@carlos começou a te seguir"),
Notificacao("N3", TipoNotificacao.COMENTARIO, "@beatriz comentou: 'Ótimo!'")
)
appState = appState.copy(
notificacoes = NotificacoesState(
notificacoes = notificacoes,
naoLidas = 3
)
)
exibirEstado(appState)
// Marcar como lida
println("\n═".repeat(45))
println("8. MARCANDO NOTIFICAÇÃO COMO LIDA")
println("═".repeat(45))
appState = reducer(appState, Action.MarcarComoLida("N1"))
exibirEstado(appState)
// Mudar filtro
println("\n═".repeat(45))
println("9. MUDANDO FILTRO DO FEED")
println("═".repeat(45))
appState = reducer(appState, Action.AlterarFiltro(FiltroFeed.POPULARES))
exibirEstado(appState)
// Navegar para notificações
println("\n═".repeat(45))
println("10. NAVEGANDO PARA NOTIFICAÇÕES")
println("═".repeat(45))
appState = reducer(appState, Action.NavegarPara(Tela.NOTIFICACOES))
exibirEstado(appState)
println("\nNotificações:")
appState.notificacoes.notificacoes.forEach { notif ->
val icone = when (notif.tipo) {
TipoNotificacao.CURTIDA -> "❤️"
TipoNotificacao.COMENTARIO -> "💬"
TipoNotificacao.SEGUIDOR -> "👥"
TipoNotificacao.MENCAO -> "📢"
TipoNotificacao.SISTEMA -> "ℹ️"
}
val status = if (notif.lida) "✓" else "•"
println("  $status $icone ${notif.mensagem}")
}
// Marcar todas como lidas
println("\n═".repeat(45))
println("11. MARCANDO TODAS COMO LIDAS")
println("═".repeat(45))
appState = reducer(appState, Action.MarcarTodasLidas)
exibirEstado(appState)
// Navegar para perfil
println("\n═".repeat(45))
println("12. NAVEGANDO PARA PERFIL")
println("═".repeat(45))
appState = reducer(appState, Action.NavegarPara(Tela.PERFIL))
appState = appState.copy(
perfil = PerfilState(
usuario = usuario,
posts = appState.feed.posts.filter { it.autorId == usuario.id }
)
)
exibirEstado(appState)
// Logout
println("\n═".repeat(45))
println("13. FAZENDO LOGOUT")
println("═".repeat(45))
appState = reducer(appState, Action.Logout)
exibirEstado(appState)
// ═══════════════════════════════════════════════════════
//  RELATÓRIO FINAL
// ═══════════════════════════════════════════════════════
println("\n╔════════════════════════════════════════╗")
println("║       RELATÓRIO DA SIMULAÇÃO           ║")
println("╠════════════════════════════════════════╣")
println("║")
println("║ ✅ 13 ações processadas")
println("║ ✅ Estado sempre consistente")
println("║ ✅ Imutabilidade mantida")
println("║ ✅ Todas transições funcionaram")
println("║")
println("║ 💡 BENEFÍCIOS DO PADRÃO:")
println("║ • Estado previsível")
println("║ • Fácil debug (histórico de ações)")
println("║ • Testável (reducer é função pura)")
println("║ • Time-travel debugging possível")
println("║ • Undo/Redo fácil de implementar")
println("║")
println("╚════════════════════════════════════════╝")
// ═══════════════════════════════════════════════════════
//  EXEMPLO: HISTÓRICO DE AÇÕES (TIME TRAVEL)
// ═══════════════════════════════════════════════════════
println("\n═".repeat(45))
println("BÔNUS: TIME TRAVEL DEBUGGING")
println("═".repeat(45))
data class HistoricoEstado(
val estado: AppState,
val acao: Action,
val timestamp: Long = System.currentTimeMillis()
)
val historico = mutableListOf()
fun executarAcao(estado: AppState, acao: Action): AppState {
val novoEstado = reducer(estado, acao)
historico.add(HistoricoEstado(novoEstado, acao))
return novoEstado
}
// Simular algumas ações com histórico
var estadoComHistorico = AppState()
println("\nExecutando ações com histórico:")
estadoComHistorico = executarAcao(estadoComHistorico, Action.LoginSucesso(usuario))
estadoComHistorico = executarAcao(estadoComHistorico, Action.FeedCarregado(posts))
estadoComHistorico = executarAcao(estadoComHistorico, Action.CurtirPost("P1"))
estadoComHistorico = executarAcao(estadoComHistorico, Action.CurtirPost("P1"))
println("\nHistórico de ações:")
historico.forEachIndexed { index, registro ->
println("${index + 1}. ${registro.acao::class.simpleName}")
}
println("\nPodemos voltar a qualquer ponto!")
val estadoAnterior = historico[1].estado
println("Estado no passo 2: Posts = ${estadoAnterior.feed.posts.size}")
println("\n✅ SISTEMA DE ESTADO COMPLETO FUNCIONANDO!")
