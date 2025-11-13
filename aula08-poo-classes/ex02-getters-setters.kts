println("═══════════════════════════════════════")
println("    MUNHU - GETTERS E SETTERS")
println("═══════════════════════════════════════")

// ========== SETTER COM VALIDAÇÃO ==========

class Conta(
    username: String,
    senha: String
) {
    var username: String = username
        set(value) {
            if (value.length >= 3) {
                field = value.lowercase()
                println("✅ Username atualizado: @$field")
            } else {
                println("❌ Username muito curto!")
            }
        }
    
    var senha: String = senha
        set(value) {
            if (value.length >= 6) {
                field = value
                println("✅ Senha atualizada")
            } else {
                println("❌ Senha muito curta (mín 6 caracteres)!")
            }
        }
        get() = "*".repeat(field.length)  // Oculta senha
    
    fun exibir() {
        println("\n👤 @$username")
        println("🔒 Senha: $senha")
    }
}

println("\n=== SETTERS COM VALIDAÇÃO ===")

val conta = Conta("Francisco_Raul", "munhu123")
conta.exibir()

println("\nTentando mudar username:")
conta.username = "fr"  // Muito curto
conta.username = "francisco_raul_jr"  // OK

println("\nTentando mudar senha:")
conta.senha = "123"  // Muito curta
conta.senha = "novasenha456"  // OK

conta.exibir()

// ========== PROPRIEDADE BACKING FIELD ==========

class Postagem(conteudo: String) {
    var conteudo: String = conteudo
        set(value) {
            if (value.length <= 280) {
                field = value
                editado = true
                println("✅ Post atualizado")
            } else {
                println("❌ Post muito longo! (${value.length}/280)")
            }
        }
    
    var editado: Boolean = false
        private set  // Setter privado (só a classe pode modificar)
    
    val tamanho: Int
        get() = conteudo.length
    
    val status: String
        get() = if (editado) "[EDITADO]" else ""
    
    fun exibir() {
        println("\n📝 $status")
        println("   $conteudo")
        println("   ${tamanho}/280 caracteres")
    }
}

println("\n=== BACKING FIELD ===")

val post = Postagem("Meu primeiro post!")
post.exibir()

println("\nEditando post:")
post.conteudo = "Meu primeiro post no Munhu! 🇲🇿"
post.exibir()

println("\nTentando post muito longo:")
post.conteudo = "A".repeat(300)

// ========== LAZY INITIALIZATION ==========

class PerfilCompleto(val username: String) {
    val bio: String by lazy {
        println("   [Carregando bio do servidor...]")
        Thread.sleep(100)  // Simula delay
        "Desenvolvedor Moçambicano 🇲🇿"
    }
    
    val seguidores: Int by lazy {
        println("   [Carregando seguidores...]")
        Thread.sleep(100)
        1200
    }
    
    fun exibir() {
        println("\n👤 @$username")
        println("📝 $bio")  // Carrega só quando acessado
        println("👥 $seguidores seguidores")
    }
}

println("\n=== LAZY INITIALIZATION ===")

println("Criando perfil...")
val perfil = PerfilCompleto("francisco_raul")

println("\nPerfil criado! Agora vamos exibir...")
perfil.exibir()

println("\nExibindo novamente (não carrega de novo):")
perfil.exibir()
