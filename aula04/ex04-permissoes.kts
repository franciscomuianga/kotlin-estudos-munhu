println("═══════════════════════════════════════")
println("    MUNHU - SISTEMA DE PERMISSÕES")
println("═══════════════════════════════════════")

print("\n👤 Username: ")
val username = readln()

print("🎭 Cargo (usuario/moderador/admin): ")
val cargo = readln().lowercase()

print("🔐 Senha: ")
val senha = readln()

print("✅ Verificado? (sim/não): ")
val verificado = readln().lowercase() == "sim"

// ========== AUTENTICAÇÃO ==========

val senhaCorreta = senha == "munhu2025"  // Simplificado

println("\n🔐 AUTENTICANDO...")

if (!senhaCorreta) {
    println("❌ SENHA INCORRETA!")
    println("Acesso negado.")
} else {
    println("✅ Autenticado como: @$username")
    
    // ========== PERMISSÕES POR CARGO ==========
    
    println("\n🎫 PERMISSÕES:")
    
    when (cargo) {
        "usuario" -> {
            println("   📝 Criar posts")
            println("   💬 Comentar")
            println("   ❤️ Curtir")
            println("   👥 Seguir usuários")
            if (verificado) {
                println("   ✓ Badge verificado")
                println("   📊 Analytics básico")
            }
        }
        
        "moderador" -> {
            println("   📝 Criar posts")
            println("   💬 Comentar")
            println("   ❤️ Curtir")
            println("   👥 Seguir usuários")
            println("   🛡️ Moderar posts")
            println("   🚫 Banir usuários temporariamente")
            println("   📋 Ver denúncias")
            println("   ✓ Badge moderador")
        }
        
        "admin" -> {
            println("   🔓 ACESSO TOTAL:")
            println("   • Todas as permissões de usuário")
            println("   • Todas as permissões de moderador")
            println("   • 🔧 Configurações do sistema")
            println("   • 👑 Banir permanentemente")
            println("   • 📊 Analytics completo")
            println("   • 💾 Backup de dados")
            println("   • 👥 Gerenciar moderadores")
        }
        
        else -> {
            println("   ❌ Cargo não reconhecido")
        }
    }
    
    // ========== AÇÕES ESPECÍFICAS ==========
    
    println("\n🎬 TESTE DE AÇÕES:")
    
    // Tentar banir usuário
    print("\n🚫 Tentar banir @problemático? (sim/não): ")
    val tentarBanir = readln().lowercase() == "sim"
    
    if (tentarBanir) {
        val podeBanir = cargo == "moderador" || cargo == "admin"
        
        if (podeBanir) {
            val tipoBan = if (cargo == "admin") {
                "permanentemente"
            } else {
                "temporariamente (7 dias)"
            }
            println("✅ Permissão concedida!")
            println("🔨 @problemático foi banido $tipoBan")
        } else {
            println("❌ Permissão negada!")
            println("Apenas moderadores e admins podem banir.")
        }
    }
    
    // Tentar acessar configurações
    print("\n⚙️ Tentar acessar configurações? (sim/não): ")
    val tentarConfig = readln().lowercase() == "sim"
    
    if (tentarConfig) {
        if (cargo == "admin") {
            println("✅ Acesso concedido!")
            println("⚙️ Painel de administração carregado")
        } else {
            println("❌ Acesso negado!")
            println("Apenas administradores têm acesso.")
        }
    }
    
    // ========== LIMITES POR CARGO ==========
    
    println("\n📊 LIMITES DA CONTA:")
    
    val limitePostsDia = when (cargo) {
        "usuario" -> if (verificado) 50 else 20
        "moderador" -> 100
        "admin" -> Int.MAX_VALUE  // Ilimitado
        else -> 10
    }
    
    val limiteSeguidores = when (cargo) {
        "usuario" -> if (verificado) 10000 else 5000
        "moderador" -> 50000
        "admin" -> Int.MAX_VALUE
        else -> 1000
    }
    
    println("   Posts/dia: ${if (limitePostsDia == Int.MAX_VALUE) "Ilimitado" else limitePostsDia}")
    println("   Max seguidores: ${if (limiteSeguidores == Int.MAX_VALUE) "Ilimitado" else limiteSeguidores}")
}

println("\n═══════════════════════════════════════")
