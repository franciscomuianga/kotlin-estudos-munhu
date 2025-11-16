println("═══════════════════════════════════════")
println("    MUNHU - HERANÇA BÁSICA")
println("═══════════════════════════════════════")

// ========== CLASSE BASE (OPEN) ==========

open class Usuario(
    val id: String,
    var username: String,
    var nome: String
) {
    var bio: String = ""
    var seguidores: Int = 0
    var seguindo: Int = 0
    
    open fun exibirPerfil() {
        println("\n┌─── PERFIL ────────────")
        println("│ @$username")
        println("│ $nome")
        if (bio.isNotEmpty()) {
            println("│ \"$bio\"")
        }
        println("│ 👥 $seguidores seguidores")
        println("└───────────────────────")
    }
    
    open fun postar(conteudo: String) {
        println("📝 @$username postou: \"$conteudo\"")
    }
    
    fun seguir(outro: Usuario) {
        seguindo++
        outro.seguidores++
        println("✅ @$username agora segue @${outro.username}")
    }
}

println("\n=== CLASSE BASE ===")

val usuario1 = Usuario("USER_1", "francisco_raul", "Francisco Raul")
usuario1.bio = "Desenvolvedor 🇲🇿"
usuario1.exibirPerfil()

// ========== HERANÇA SIMPLES ==========

class UsuarioVerificado(
    id: String,
    username: String,
    nome: String
) : Usuario(id, username, nome) {
    
    var dataVerificacao: String = ""
    
    // Sobrescreve método da classe pai
    override fun exibirPerfil() {
        println("\n┌─── PERFIL VERIFICADO ✓ ────")
        println("│ @$username ✓")
        println("│ $nome")
        if (bio.isNotEmpty()) {
            println("│ \"$bio\"")
        }
        println("│ 👥 $seguidores seguidores")
        println("│ ✓ Verificado em: $dataVerificacao")
        println("└─────────────────────────────")
    }
    
    // Método novo (não existe na classe pai)
    fun solicitarBadgeOuro() {
        println("🏅 @$username solicitou badge ouro")
    }
}

println("\n=== HERANÇA - USUÁRIO VERIFICADO ===")

val verificado = UsuarioVerificado("USER_V1", "munhu_oficial", "Munhu Oficial")
verificado.bio = "Rede Social Moçambicana 🇲🇿"
verificado.seguidores = 50000
verificado.dataVerificacao = "12/11/2025"
verificado.exibirPerfil()
verificado.solicitarBadgeOuro()

// ========== MAIS UMA CLASSE FILHA ==========

class Moderador(
    id: String,
    username: String,
    nome: String
) : Usuario(id, username, nome) {
    
    var postsRemovidos: Int = 0
    var usuariosBanidos: Int = 0
    
    override fun exibirPerfil() {
        println("\n┌─── PERFIL MODERADOR 🛡️ ────")
        println("│ @$username 🛡️")
        println("│ $nome")
        println("│ 👥 $seguidores seguidores")
        println("│ 📊 Estatísticas:")
        println("│    Posts removidos: $postsRemovidos")
        println("│    Usuários banidos: $usuariosBanidos")
        println("└──────────────────────────────")
    }
    
    fun removerPost(postId: String) {
        postsRemovidos++
        println("🗑️ Moderador @$username removeu post $postId")
    }
    
    fun banirUsuario(usuarioId: String) {
        usuariosBanidos++
        println("🔨 Moderador @$username baniu usuário $usuarioId")
    }
}

println("\n=== HERANÇA - MODERADOR ===")

val mod = Moderador("USER_MOD1", "carlos_mod", "Carlos Moderador")
mod.exibirPerfil()
mod.removerPost("POST_123")
mod.banirUsuario("USER_SPAM")
mod.exibirPerfil()

// ========== POLIMORFISMO ==========

println("\n=== POLIMORFISMO (LISTA DE TIPOS DIFERENTES) ===")

val todosUsuarios: List<Usuario> = listOf(
    usuario1,
    verificado,
    mod
)

println("\nPercorrendo todos os usuários:")
for (usuario in todosUsuarios) {
    usuario.exibirPerfil()
}

// ========== SUPER KEYWORD ==========

class UsuarioPremium(
    id: String,
    username: String,
    nome: String
) : Usuario(id, username, nome) {
    
    var plano: String = "Premium"
    
    override fun exibirPerfil() {
        // Chama o método da classe pai primeiro
        super.exibirPerfil()
        println("│ 💎 Plano: $plano")
        println("└───────────────────────")
    }
    
    override fun postar(conteudo: String) {
        println("💎 [PREMIUM]")
        super.postar(conteudo)  // Chama método do pai
        println("   ✨ Post destacado no feed!")
    }
}

println("\n=== SUPER KEYWORD ===")

val premium = UsuarioPremium("USER_P1", "ana_premium", "Ana Silva")
premium.bio = "Designer Premium 🎨"
premium.seguidores = 5000
premium.exibirPerfil()
premium.postar("Novo design no Munhu!")

// ========== IS E AS (TYPE CHECKING) ==========

println("\n=== TYPE CHECKING (is/as) ===")

fun processarUsuario(usuario: Usuario) {
    println("\nProcessando: @${usuario.username}")
    
    // Verifica tipo
    when {
        usuario is Moderador -> {
            println("   É moderador! 🛡️")
            usuario.removerPost("POST_SPAM")  // Pode chamar métodos de Moderador
        }
        usuario is UsuarioVerificado -> {
            println("   É verificado! ✓")
            usuario.solicitarBadgeOuro()
        }
        usuario is UsuarioPremium -> {
            println("   É premium! 💎")
            println("   Plano: ${usuario.plano}")
        }
        else -> {
            println("   Usuário comum")
        }
    }
}

processarUsuario(usuario1)
processarUsuario(verificado)
processarUsuario(mod)
processarUsuario(premium)

// ========== CASTING SEGURO ==========

println("\n=== CASTING SEGURO (as?) ===")

fun tentarBanir(usuario: Usuario) {
    val moderador = usuario as? Moderador
    
    if (moderador != null) {
        moderador.banirUsuario("USER_BAD")
        println("✅ Banimento executado")
    } else {
        println("❌ Usuário não é moderador!")
    }
}

tentarBanir(mod)      // Funciona
tentarBanir(usuario1)  // Não funciona
