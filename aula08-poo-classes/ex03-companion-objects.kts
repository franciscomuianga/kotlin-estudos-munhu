println("═══════════════════════════════════════")
println("    MUNHU - COMPANION & OBJECT")
println("═══════════════════════════════════════")

// ========== COMPANION OBJECT (métodos estáticos) ==========

class UsuarioMunhu(
    val id: String,
    val username: String,
    val nome: String
) {
    companion object {
        private var contadorIds = 1
        const val IDADE_MINIMA = 13
        const val MAX_USERNAME_LENGTH = 15
        
        fun gerarId(): String {
            return "USER_${contadorIds++}"
        }
        
        fun validarUsername(username: String): Boolean {
            return username.length in 3..MAX_USERNAME_LENGTH &&
                   username.all { it.isLetterOrDigit() || it == '_' } &&
                   username.first().isLetter()
        }
        
        fun validarIdade(idade: Int): Boolean {
            return idade >= IDADE_MINIMA
        }
        
        fun criar(username: String, nome: String, idade: Int): UsuarioMunhu? {
            if (!validarUsername(username)) {
                println("❌ Username inválido!")
                return null
            }
            
            if (!validarIdade(idade)) {
                println("❌ Idade mínima: $IDADE_MINIMA anos")
                return null
            }
            
            val id = gerarId()
            println("✅ Usuário criado: @$username (ID: $id)")
            return UsuarioMunhu(id, username, nome)
        }
    }
    
    fun exibir() {
        println("👤 @$username - $nome (ID: $id)")
    }
}

println("\n=== COMPANION OBJECT ===")

println("Constantes:")
println("Idade mínima: ${UsuarioMunhu.IDADE_MINIMA}")
println("Max username: ${UsuarioMunhu.MAX_USERNAME_LENGTH}")

println("\nValidações:")
println("'fr' é válido? ${UsuarioMunhu.validarUsername("fr")}")
println("'francisco_raul' é válido? ${UsuarioMunhu.validarUsername("francisco_raul")}")
println("Idade 12 é válida? ${UsuarioMunhu.validarIdade(12)}")
println("Idade 17 é válida? ${UsuarioMunhu.validarIdade(17)}")

println("\nCriando usuários:")
val user1 = UsuarioMunhu.criar("francisco_raul", "Francisco Raul", 17)
user1?.exibir()

val user2 = UsuarioMunhu.criar("ana_silva", "Ana Silva", 16)
user2?.exibir()

val user3 = UsuarioMunhu.criar("jr", "Junior", 12)  // Falha

val user4 = UsuarioMunhu.criar("carlos_dev", "Carlos", 25)
user4?.exibir()

println("\nIDs gerados automaticamente:")
println("Próximo ID seria: ${UsuarioMunhu.gerarId()}")

// ========== OBJECT DECLARATION (Singleton) ==========

object ConfiguracaoMunhu {
    const val NOME_APP = "Munhu"
    const val VERSAO = "1.0.0"
    const val MAX_POSTS_DIA = 50
    const val MAX_CARACTERES_POST = 280
    const val MAX_CARACTERES_BIO = 150
    
    var modoEscuro: Boolean = false
    var notificacoesAtivadas: Boolean = true
    var idiomaAtual: String = "pt-MZ"
    
    private val idiomas = mapOf(
        "pt-MZ" to "Português (Moçambique)",
        "en" to "English",
        "pt-BR" to "Português (Brasil)"
    )
    
    fun exibirInfo() {
        println("\n╔════════════════════════════════════════╗")
        println("║        CONFIGURAÇÕES MUNHU             ║")
        println("╠════════════════════════════════════════╣")
        println("║ App: $NOME_APP v$VERSAO")
        println("║ ")
        println("║ LIMITES:")
        println("║ • Posts/dia: $MAX_POSTS_DIA")
        println("║ • Caracteres/post: $MAX_CARACTERES_POST")
        println("║ • Caracteres/bio: $MAX_CARACTERES_BIO")
        println("║ ")
        println("║ PREFERÊNCIAS:")
        println("║ • Modo escuro: ${if (modoEscuro) "ON" else "OFF"}")
        println("║ • Notificações: ${if (notificacoesAtivadas) "ON" else "OFF"}")
        println("║ • Idioma: ${idiomas[idiomaAtual]}")
        println("╚════════════════════════════════════════╝")
    }
    
    fun alternarModoEscuro() {
        modoEscuro = !modoEscuro
        println("${if (modoEscuro) "🌙" else "☀️"} Modo escuro: ${if (modoEscuro) "ATIVADO" else "DESATIVADO"}")
    }
    
    fun alternarNotificacoes() {
        notificacoesAtivadas = !notificacoesAtivadas
        println("${if (notificacoesAtivadas) "🔔" else "🔕"} Notificações: ${if (notificacoesAtivadas) "ATIVADAS" else "DESATIVADAS"}")
    }
    
    fun mudarIdioma(codigo: String) {
        if (idiomas.containsKey(codigo)) {
            idiomaAtual = codigo
            println("🌍 Idioma alterado para: ${idiomas[codigo]}")
        } else {
            println("❌ Idioma não suportado")
        }
    }
}

println("\n=== OBJECT DECLARATION (SINGLETON) ===")

ConfiguracaoMunhu.exibirInfo()

println("\nAlterando configurações:")
ConfiguracaoMunhu.alternarModoEscuro()
ConfiguracaoMunhu.alternarNotificacoes()
ConfiguracaoMunhu.mudarIdioma("en")

println()
ConfiguracaoMunhu.exibirInfo()

// ========== DATABASE SINGLETON ==========

object DatabaseMunhu {
    private val usuarios = mutableMapOf<String, UsuarioMunhu>()
    private val posts = mutableListOf<String>()
    
    fun adicionarUsuario(usuario: UsuarioMunhu) {
        usuarios[usuario.id] = usuario
        println("💾 Usuário salvo no banco: @${usuario.username}")
    }
    
    fun buscarUsuario(id: String): UsuarioMunhu? {
        return usuarios[id]
    }
    
    fun adicionarPost(conteudo: String) {
        if (conteudo.length <= ConfiguracaoMunhu.MAX_CARACTERES_POST) {
            posts.add(conteudo)
            println("💾 Post salvo no banco")
        } else {
            println("❌ Post muito longo!")
        }
    }
    
    fun exibirEstatisticas() {
        println("\n📊 ESTATÍSTICAS DO BANCO:")
        println("   Usuários: ${usuarios.size}")
        println("   Posts: ${posts.size}")
    }
}

println("\n=== DATABASE SINGLETON ===")

if (user1 != null) DatabaseMunhu.adicionarUsuario(user1)
if (user2 != null) DatabaseMunhu.adicionarUsuario(user2)
if (user4 != null) DatabaseMunhu.adicionarUsuario(user4)

DatabaseMunhu.adicionarPost("Primeiro post!")
DatabaseMunhu.adicionarPost("Estudando POO em Kotlin")

DatabaseMunhu.exibirEstatisticas()

val usuarioBuscado = DatabaseMunhu.buscarUsuario(user1!!.id)
println("\n🔍 Busca: ${usuarioBuscado?.username}")
