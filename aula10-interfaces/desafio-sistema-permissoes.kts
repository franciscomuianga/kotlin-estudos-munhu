println("╔════════════════════════════════════════╗")
println("║  MUNHU - SISTEMA DE PERMISSÕES V2.0    ║")
println("╚════════════════════════════════════════╝")

// ═══════════════════════════════════════════════════════
//  INTERFACES DE PERMISSÕES
// ═══════════════════════════════════════════════════════

interface PodePostar {
    fun postar(conteudo: String)
    fun limitePosts(): Int
}

interface PodeComentar {
    fun comentar(postId: String, texto: String)
}

interface PodeModerar {
    fun removerPost(postId: String, motivo: String)
    fun banirUsuario(usuarioId: String, dias: Int)
}

interface PodeVerificar {
    fun verificarUsuario(usuarioId: String)
}

interface PodeAcessarAnalytics {
    fun verAnalytics(tipo: String)
}

interface PodeGerenciarSistema {
    fun alterarConfiguracao(chave: String, valor: String)
    fun fazerBackup()
}

// ═══════════════════════════════════════════════════════
//  CLASSE BASE
// ═══════════════════════════════════════════════════════

abstract class Usuario(
    val id: String,
    var username: String,
    var nome: String
) {
    var seguidores = 0
    var seguindo = 0
    protected val posts = mutableListOf<String>()
    
    abstract val badge: String
    abstract fun exibirPermissoes()
    
    fun exibirPerfil() {
        println("\n┌─── PERFIL ────────────")
        println("│ $badge @$username")
        println("│ $nome")
        println("│ 👥 $seguidores · $seguindo")
        println("│ 📝 ${posts.size} posts")
        println("└───────────────────────")
    }
}

// ═══════════════════════════════════════════════════════
//  IMPLEMENTAÇÕES CONCRETAS
// ═══════════════════════════════════════════════════════

class UsuarioGratuito(
    id: String,
    username: String,
    nome: String
) : Usuario(id, username, nome), PodePostar, PodeComentar {
    
    override val badge = "🆓"
    private var postsHoje = 0
    private val LIMITE_DIARIO = 5
    
    override fun postar(conteudo: String) {
        if (postsHoje >= LIMITE_DIARIO) {
            println("❌ Limite diário atingido ($LIMITE_DIARIO posts)")
            println("💎 Upgrade para Premium para posts ilimitados!")
            return
        }
        
        posts.add(conteudo)
        postsHoje++
        println("📝 Post publicado! ($postsHoje/$LIMITE_DIARIO hoje)")
    }
    
    override fun limitePosts() = LIMITE_DIARIO
    
    override fun comentar(postId: String, texto: String) {
        if (texto.length > 100) {
            println("❌ Comentário muito longo! (máx 100 caracteres)")
            return
        }
        println("💬 Comentário em $postId: \"$texto\"")
    }
    
    override fun exibirPermissoes() {
        println("\n🆓 PERMISSÕES (GRATUITO):")
        println("   ✅ Postar (${limitePosts()} por dia)")
        println("   ✅ Comentar (máx 100 chars)")
        println("   ❌ Analytics")
        println("   ❌ Destacar posts")
    }
}

// ─────────────────────────────────────────────────────

class UsuarioPremium(
    id: String,
    username: String,
    nome: String
) : Usuario(id, username, nome), PodePostar, PodeComentar, PodeAcessarAnalytics {
    
    override val badge = "💎"
    private var postsDestacados = 0
    
    override fun postar(conteudo: String) {
        posts.add(conteudo)
        println("📝 Post publicado (ilimitado)")
    }
    
    override fun limitePosts() = Int.MAX_VALUE
    
    override fun comentar(postId: String, texto: String) {
        println("💬 Comentário Premium em $postId: \"$texto\"")
        println("   ✨ Comentário destacado!")
    }
    
    override fun verAnalytics(tipo: String) {
        println("📊 ANALYTICS PREMIUM:")
        println("   Tipo: $tipo")
        println("   Alcance: ${seguidores * 3}")
        println("   Engajamento: 8.5%")
        println("   Melhor horário: 19h-21h")
    }
    
    fun destacarPost(conteudo: String) {
        if (postsDestacados >= 3) {
            println("⚠️ Limite de posts destacados (3/dia)")
            return
        }
        
        postar(conteudo)
        postsDestacados++
        println("⭐ Post destacado! (${postsDestacados}/3)")
    }
    
    override fun exibirPermissoes() {
        println("\n💎 PERMISSÕES (PREMIUM):")
        println("   ✅ Posts ilimitados")
        println("   ✅ Comentários ilimitados")
        println("   ✅ Analytics completo")
        println("   ✅ Destacar posts (3/dia)")
        println("   ✅ Sem anúncios")
    }
}

// ─────────────────────────────────────────────────────

class UsuarioVerificado(
    id: String,
    username: String,
    nome: String
) : Usuario(id, username, nome), PodePostar, PodeComentar, PodeAcessarAnalytics {
    
    override val badge = "✓"
    var dataVerificacao = ""
    
    override fun postar(conteudo: String) {
        posts.add(conteudo)
        println("📝 Post publicado")
        println("   ✓ Badge verificado aumenta alcance em 50%")
    }
    
    override fun limitePosts() = Int.MAX_VALUE
    
    override fun comentar(postId: String, texto: String) {
        println("💬 ✓ Comentário verificado em $postId: \"$texto\"")
    }
    
    override fun verAnalytics(tipo: String) {
        println("📊 ANALYTICS VERIFICADO:")
        println("   Tipo: $tipo")
        println("   Impressões: ${seguidores * 5}")
        println("   Taxa de conversão: 12%")
        println("   Dados demográficos disponíveis")
    }
    
    override fun exibirPermissoes() {
        println("\n✓ PERMISSÕES (VERIFICADO):")
        println("   ✅ Posts ilimitados")
        println("   ✅ Comentários ilimitados")
        println("   ✅ Analytics avançado")
        println("   ✅ Badge verificado")
        println("   ✅ Prioridade no feed")
    }
}

// ─────────────────────────────────────────────────────

class Moderador(
    id: String,
    username: String,
    nome: String
) : Usuario(id, username, nome), PodePostar, PodeComentar, PodeModerar {
    
    override val badge = "🛡️"
    private val acoes = mutableMapOf(
        "posts_removidos" to 0,
        "usuarios_banidos" to 0
    )
    
    override fun postar(conteudo: String) {
        posts.add(conteudo)
        println("📝 Post de moderador publicado")
    }
    
    override fun limitePosts() = Int.MAX_VALUE
    
    override fun comentar(postId: String, texto: String) {
        println("💬 🛡️ Comentário oficial em $postId: \"$texto\"")
    }
    
    override fun removerPost(postId: String, motivo: String) {
        acoes["posts_removidos"] = acoes["posts_removidos"]!! + 1
        println("🗑️ Moderador removeu $postId")
        println("   Motivo: $motivo")
        println("   Total removidos: ${acoes["posts_removidos"]}")
    }
    
    override fun banirUsuario(usuarioId: String, dias: Int) {
        acoes["usuarios_banidos"] = acoes["usuarios_banidos"]!! + 1
        println("🔨 Moderador baniu $usuarioId")
        println("   Duração: $dias dias")
        println("   Total banidos: ${acoes["usuarios_banidos"]}")
    }
    
    fun relatorioMod() {
        println("\n📊 RELATÓRIO DE MODERAÇÃO:")
        acoes.forEach { (acao, qtd) ->
            println("   ${acao.replace("_", " ")}: $qtd")
        }
    }
    
    override fun exibirPermissoes() {
        println("\n🛡️ PERMISSÕES (MODERADOR):")
        println("   ✅ Posts ilimitados")
        println("   ✅ Comentários ilimitados")
        println("   ✅ Remover posts")
        println("   ✅ Banir usuários")
        println("   ✅ Ver denúncias")
    }
}

// ─────────────────────────────────────────────────────

class Administrador(
    id: String,
    username: String,
    nome: String
) : Usuario(id, username, nome), 
    PodePostar, 
    PodeComentar, 
    PodeModerar, 
    PodeVerificar, 
    PodeAcessarAnalytics,
    PodeGerenciarSistema {
    
    override val badge = "👑"
    
    override fun postar(conteudo: String) {
        posts.add(conteudo)
        println("📝 Anúncio oficial: \"$conteudo\"")
    }
    
    override fun limitePosts() = Int.MAX_VALUE
    
    override fun comentar(postId: String, texto: String) {
        println("💬 👑 Comentário oficial em $postId: \"$texto\"")
    }
    
    override fun removerPost(postId: String, motivo: String) {
        println("🗑️ Admin removeu $postId: $motivo")
    }
    
    override fun banirUsuario(usuarioId: String, dias: Int) {
        if (dias == 0) {
            println("🔨 Admin BANIU PERMANENTEMENTE $usuarioId")
        } else {
            println("🔨 Admin baniu $usuarioId por $dias dias")
        }
    }
    
    override fun verificarUsuario(usuarioId: String) {
        println("✓ Admin verificou usuário $usuarioId")
        println("   Badge verificado concedido")
    }
    
    override fun verAnalytics(tipo: String) {
        println("📊 ANALYTICS COMPLETO DO SISTEMA:")
        println("   Usuários ativos: 150,000")
        println("   Posts hoje: 45,000")
        println("   Taxa de crescimento: +15%")
        println("   Receita mensal: 25,000 MT")
    }
    
    override fun alterarConfiguracao(chave: String, valor: String) {
        println("⚙️ Admin alterou: $chave = $valor")
    }
    
    override fun fazerBackup() {
        println("💾 Admin iniciou backup...")
        Thread.sleep(100)
        println("✅ Backup completo!")
    }
    
    override fun exibirPermissoes() {
        println("\n👑 PERMISSÕES (ADMIN):")
        println("   ✅ TODAS AS PERMISSÕES")
        println("   ✅ Verificar usuários")
        println("   ✅ Analytics completo")
        println("   ✅ Gerenciar sistema")
        println("   ✅ Acesso total")
    }
}

// ═══════════════════════════════════════════════════════
//  SISTEMA DE UPGRADE
// ═══════════════════════════════════════════════════════

object SistemaUpgrade {
    fun gratuito_para_premium(usuario: UsuarioGratuito): UsuarioPremium {
        println("\n⬆️ UPGRADE: Gratuito → Premium")
        println("   Usuário: @${usuario.username}")
        println("   💳 Pagamento: 299 MT/mês")
        
        val premium = UsuarioPremium(usuario.id, usuario.username, usuario.nome)
        premium.seguidores = usuario.seguidores
        premium.seguindo = usuario.seguindo
        
        println("✅ Upgrade concluído!")
        println("   Novos benefícios desbloqueados!")
        
        return premium
    }
    
    fun qualquer_para_verificado(usuario: Usuario, admin: Administrador): UsuarioVerificado {
        println("\n✓ VERIFICAÇÃO")
        admin.verificarUsuario(usuario.id)
        
        val verificado = UsuarioVerificado(usuario.id, usuario.username, usuario.nome)
        verificado.seguidores = usuario.seguidores
        verificado.seguindo = usuario.seguindo
        verificado.dataVerificacao = "15/11/2025"
        
        return verificado
    }
}

// ═══════════════════════════════════════════════════════
//  SIMULAÇÃO COMPLETA
// ═══════════════════════════════════════════════════════

println("\n🚀 Iniciando Munhu v3.0 com sistema de permissões...\n")

// Criar usuários
val francisco = UsuarioGratuito("U1", "francisco_raul", "Francisco Raul")
francisco.seguidores = 450

val ana = UsuarioPremium("U2", "ana_silva", "Ana Silva")
ana.seguidores = 5000

val carlos = Moderador("U3", "carlos_mod", "Carlos Moderador")
carlos.seguidores = 1200

val admin = Administrador("U_ADMIN", "munhu_admin", "Munhu Team")

println("═".repeat(45))
println("PERFIS CRIADOS")
println("═".repeat(45))

listOf(francisco, ana, carlos, admin).forEach { 
    it.exibirPerfil()
    it.exibirPermissoes()
}

// Testar permissões
println("\n" + "═".repeat(45))
println("TESTANDO PERMISSÕES")
println("═".repeat(45))

println("\n--- FRANCISCO (GRATUITO) ---")
repeat(6) {
    francisco.postar("Post ${it + 1}")
}
francisco.comentar("POST_1", "Legal!")

println("\n--- ANA (PREMIUM) ---")
repeat(3) {
    ana.postar("Post premium ${it + 1}")
}
ana.destacarPost("Post especial")
ana.verAnalytics("semanal")

println("\n--- CARLOS (MODERADOR) ---")
carlos.postar("Lembrete: Respeitem as regras!")
carlos.removerPost("POST_SPAM", "Spam comercial")
carlos.banirUsuario("USER_BAD", 7)
carlos.relatorioMod()

println("\n--- ADMIN ---")
admin.postar("Nova atualização disponível!")
admin.verAnalytics("mensal")
admin.alterarConfiguracao("max_posts_dia", "50")
admin.fazerBackup()

// Upgrade de usuário
println("\n" + "═".repeat(45))
println("SISTEMA DE UPGRADE")
println("═".repeat(45))

val franciscoPremium = SistemaUpgrade.gratuito_para_premium(francisco)
franciscoPremium.exibirPermissoes()

println("\nAgora Francisco pode postar sem limite:")
repeat(10) {
    franciscoPremium.postar("Post ilimitado ${it + 1}")
}

franciscoPremium.verAnalytics("diario")

// Verificação
println("\n" + "═".repeat(45))
println("VERIFICAÇÃO DE USUÁRIO")
println("═".repeat(45))

val anaVerificada = SistemaUpgrade.qualquer_para_verificado(ana, admin)
anaVerificada.exibirPermissoes()
anaVerificada.postar("Primeiro post verificado! ✓")

// Polimorfismo - processar todos
println("\n" + "═".repeat(45))
println("PROCESSAMENTO POLIMÓRFICO")
println("═".repeat(45))

val todosUsuarios: List<Usuario> = listOf(
    francisco, ana, carlos, admin
)

println("\nUsuários que podem postar:")
todosUsuarios.filterIsInstance<PodePostar>().forEach { usuario ->
    println("  • ${(usuario as Usuario).username} (limite: ${usuario.limitePosts()})")
}

println("\nUsuários que podem moderar:")
todosUsuarios.filterIsInstance<PodeModerar>().forEach { usuario ->
    println("  • ${(usuario as Usuario).username}")
}

println("\nUsuários com analytics:")
todosUsuarios.filterIsInstance<PodeAcessarAnalytics>().forEach { usuario ->
    println("  • ${(usuario as Usuario).username}")
}

// Estatísticas finais
println("\n╔════════════════════════════════════════╗")
println("║       ESTATÍSTICAS DO SISTEMA          ║")
println("╠════════════════════════════════════════╣")
println("║ Total usuários: ${todosUsuarios.size}")
println("║")
println("║ POR TIPO:")
println("║ • Gratuitos: ${todosUsuarios.filterIsInstance<UsuarioGratuito>().size}")
println("║ • Premium: ${todosUsuarios.filterIsInstance<UsuarioPremium>().size}")
println("║ • Verificados: ${todosUsuarios.filterIsInstance<UsuarioVerificado>().size}")
println("║ • Moderadores: ${todosUsuarios.filterIsInstance<Moderador>().size}")
println("║ • Admins: ${todosUsuarios.filterIsInstance<Administrador>().size}")
println("║")
println("║ Total seguidores: ${todosUsuarios.sumOf { it.seguidores }}")
println("║ Total posts: ${todosUsuarios.sumOf { it.posts.size }}")
println("╚════════════════════════════════════════╝")

println("\n✅ SISTEMA DE PERMISSÕES FUNCIONANDO PERFEITAMENTE!")
