println("═══════════════════════════════════════")
println("  MUNHU - INTERFACE VS ABSTRACT CLASS")
println("═══════════════════════════════════════")

// ========== QUANDO USAR INTERFACE ==========

// Interface: Define COMPORTAMENTO (o que fazer)
interface Visualizavel {
    fun exibir()
    fun ocultar()
}

interface Editavel {
    fun editar(novoConteudo: String)
}

interface Removivel {
    fun remover(): Boolean
}

// Múltiplas interfaces: flexibilidade total
class Post : Visualizavel, Editavel, Removivel {
    var conteudo = ""
    private var visivel = true
    private var removido = false
    
    override fun exibir() {
        if (!removido && visivel) {
            println("📝 \"$conteudo\"")
        } else if (removido) {
            println("🗑️ [POST REMOVIDO]")
        } else {
            println("👁️ [POST OCULTO]")
        }
    }
    
    override fun ocultar() {
        visivel = false
        println("👁️‍🗨️ Post ocultado")
    }
    
    override fun editar(novoConteudo: String) {
        if (!removido) {
            conteudo = "$novoConteudo [editado]"
            println("✏️ Post editado")
        }
    }
    
    override fun remover(): Boolean {
        removido = true
        println("🗑️ Post removido")
        return true
    }
}

class Comentario : Visualizavel, Removivel {
    var texto = ""
    private var removido = false
    
    override fun exibir() {
        if (!removido) {
            println("💬 \"$texto\"")
        } else {
            println("🗑️ [COMENTÁRIO REMOVIDO]")
        }
    }
    
    override fun ocultar() {
        println("⚠️ Comentários não podem ser ocultados, apenas removidos")
    }
    
    override fun remover(): Boolean {
        removido = true
        return true
    }
}

println("\n=== INTERFACES: FLEXIBILIDADE ===")

val post = Post()
post.conteudo = "Meu post original"
post.exibir()
post.editar("Conteúdo atualizado")
post.exibir()
post.ocultar()
post.exibir()

val coment = Comentario()
coment.texto = "Ótimo post!"
coment.exibir()
coment.remover()
coment.exibir()

// ========== QUANDO USAR CLASSE ABSTRATA ==========

// Abstract Class: Define ESTRUTURA COMUM (estado compartilhado)
abstract class Conteudo(
    val id: String,
    val autorId: String
) {
    var likes = 0
        protected set
    
    var visualizacoes = 0
        protected set
    
    val timestamp = System.currentTimeMillis()
    
    // Método abstrato
    abstract fun calcularEngajamento(): Int
    
    // Método concreto (compartilhado)
    fun curtir() {
        likes++
    }
    
    fun visualizar() {
        visualizacoes++
    }
    
    // Template method pattern
    fun exibirCompleto() {
        visualizar()
        exibirCabecalho()
        exibirCorpo()
        exibirRodape()
    }
    
    protected open fun exibirCabecalho() {
        println("\n┌────────────────────────")
        println("│ Autor: $autorId")
    }
    
    protected abstract fun exibirCorpo()
    
    protected open fun exibirRodape() {
        println("│ ❤️ $likes  👁️ $visualizacoes")
        println("└────────────────────────")
    }
}

class PostTexto(
    id: String,
    autorId: String,
    val texto: String
) : Conteudo(id, autorId) {
    
    override fun calcularEngajamento() = likes * 1
    
    override fun exibirCorpo() {
        println("│ 📝 \"$texto\"")
    }
}

class PostFoto(
    id: String,
    autorId: String,
    val urlFoto: String,
    val legenda: String
) : Conteudo(id, autorId) {
    
    override fun calcularEngajamento() = likes * 2
    
    override fun exibirCorpo() {
        println("│ 🖼️ [$urlFoto]")
        if (legenda.isNotEmpty()) {
            println("│ \"$legenda\"")
        }
    }
}

class PostVideo(
    id: String,
    autorId: String,
    val urlVideo: String,
    val duracao: Int
) : Conteudo(id, autorId) {
    
    override fun calcularEngajamento() = likes * 3 + visualizacoes
    
    override fun exibirCorpo() {
        println("│ 🎥 [$urlVideo]")
        println("│ ⏱️ ${duracao}s")
    }
    
    override fun exibirRodape() {
        println("│ ❤️ $likes  👁️ $visualizacoes  🔥 ${calcularEngajamento()}")
        println("└────────────────────────")
    }
}

println("\n=== CLASSE ABSTRATA: ESTRUTURA COMUM ===")

val post1 = PostTexto("P1", "francisco", "Estudando Kotlin")
post1.curtir()
post1.curtir()
post1.exibirCompleto()

val foto1 = PostFoto("F1", "ana", "foto.jpg", "Maputo 🌅")
foto1.curtir()
foto1.curtir()
foto1.curtir()
foto1.exibirCompleto()

val video1 = PostVideo("V1", "carlos", "video.mp4", 180)
video1.curtir()
video1.curtir()
video1.curtir()
video1.curtir()
repeat(10) { video1.visualizar() }
video1.exibirCompleto()

// ========== COMBINANDO AMBOS ==========

interface Moderavel {
    fun denunciar(motivo: String)
    fun aprovar()
}

abstract class ConteudoModeravel(
    id: String,
    autorId: String
) : Conteudo(id, autorId), Moderavel {
    
    var flags = 0
        protected set
    
    var aprovado = false
        protected set
    
    override fun denunciar(motivo: String) {
        flags++
        println("🚩 Denúncia: $motivo (total: $flags)")
        
        if (flags >= 3) {
            println("⚠️ Conteúdo bloqueado para revisão")
        }
    }
    
    override fun aprovar() {
        aprovado = true
        flags = 0
        println("✅ Conteúdo aprovado")
    }
}

class PostModeravel(
    id: String,
    autorId: String,
    val texto: String
) : ConteudoModeravel(id, autorId) {
    
    override fun calcularEngajamento() = likes
    
    override fun exibirCorpo() {
        if (flags >= 3) {
            println("│ ⚠️ [CONTEÚDO EM REVISÃO]")
        } else {
            println("│ 📝 \"$texto\"")
        }
    }
    
    override fun exibirRodape() {
        super.exibirRodape()
        if (flags > 0) {
            println("│ 🚩 $flags flag(s)")
        }
    }
}

println("\n=== COMBINANDO INTERFACE + ABSTRACT ===")

val post2 = PostModeravel("P2", "usuario", "Conteúdo questionável")
post2.exibirCompleto()
post2.denunciar("Spam")
post2.denunciar("Inapropriado")
post2.exibirCompleto()
post2.denunciar("Desinformação")
post2.exibirCompleto()
post2.aprovar()
post2.exibirCompleto()

// ========== COMPARAÇÃO RESUMIDA ==========

println("\n╔════════════════════════════════════════╗")
println("║     INTERFACE VS ABSTRACT CLASS        ║")
println("╠════════════════════════════════════════╣")
println("║")
println("║ INTERFACE:")
println("║ • Define COMPORTAMENTO")
println("║ • Múltipla implementação (✅)")
println("║ • Sem estado (só em Kotlin)")
println("║ • Mais flexível")
println("║ • Use quando: \"pode fazer X\"")
println("║")
println("║ ABSTRACT CLASS:")
println("║ • Define ESTRUTURA e ESTADO")
println("║ • Herança única (❌)")
println("║ • Com estado compartilhado")
println("║ • Menos flexível")
println("║ • Use quando: \"é um tipo de X\"")
println("║")
println("║ COMBINE AMBOS:")
println("║ • Estado comum → Abstract")
println("║ • Comportamentos → Interface")
println("║ • Máxima flexibilidade! 🚀")
println("║")
println("╚════════════════════════════════════════╝")

// ========== EXEMPLO PRÁTICO ==========

println("\n=== EXEMPLO PRÁTICO: SISTEMA DE PLUGINS ===")

interface Plugin {
    val nome: String
    val versao: String
    fun inicializar()
    fun desativar()
}

interface PluginConfiguravel : Plugin {
    fun configurar(opcoes: Map<String, String>)
}

abstract class PluginBase(
    override val nome: String,
    override val versao: String
) : Plugin {
    
    protected var ativo = false
    
    override fun inicializar() {
        if (!ativo) {
            ativo = true
            println("🔌 Plugin '$nome' v$versao inicializado")
        }
    }
    
    override fun desativar() {
        if (ativo) {
            ativo = false
            println("🔌 Plugin '$nome' desativado")
        }
    }
}

class PluginAnalytics : PluginBase("Analytics", "1.0.0"), PluginConfiguravel {
    private var rastreamentoAtivo = false
    
    override fun configurar(opcoes: Map<String, String>) {
        val modo = opcoes["modo"] ?: "basico"
        println("⚙️ Analytics configurado: modo=$modo")
        rastreamentoAtivo = modo == "completo"
    }
    
    fun rastrearEvento(evento: String) {
        if (ativo && rastreamentoAtivo) {
            println("📊 Evento rastreado: $evento")
        }
    }
}

class PluginNotificacoes : PluginBase("Notificações Push", "2.0.0") {
    fun enviarNotificacao(mensagem: String) {
        if (ativo) {
            println("🔔 Notificação: $mensagem")
        } else {
            println("❌ Plugin desativado")
        }
    }
}

val analytics = PluginAnalytics()
analytics.inicializar()
analytics.configurar(mapOf("modo" to "completo"))
analytics.rastrearEvento("usuario_login")

val notif = PluginNotificacoes()
notif.inicializar()
notif.enviarNotificacao("Novo seguidor!")
notif.desativar()
notif.enviarNotificacao("Teste")
