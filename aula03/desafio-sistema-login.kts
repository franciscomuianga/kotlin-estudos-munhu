println("╔════════════════════════════════════════╗")
println("║          🇲🇿 MUNHU LOGIN 🇲🇿          ║")
println("╚════════════════════════════════════════╝")

// ========== BASE DE DADOS SIMULADA ==========
val usuarioCadastrado = "francisco"
val senhaCadastrada = "munhu2025"
val nomeCadastrado = "Francisco - THE FRA LABS"

// ========== TENTATIVA DE LOGIN ==========
println("\n🔐 ENTRE COM SUAS CREDENCIAIS\n")

print("👤 Username: ")
val usernameInput = readln().lowercase().trim()

print("🔒 Senha: ")
val senhaInput = readln()

println("\n⏳ Verificando...")

// Simular delay (opcional)
// Thread.sleep(1000)

println()

// ========== VALIDAÇÃO ==========

val usernameCorreto = usernameInput == usuarioCadastrado
val senhaCorreta = senhaInput == senhaCadastrada

if (usernameCorreto && senhaCorreta) {
    // LOGIN SUCESSO
    println("✅ LOGIN REALIZADO COM SUCESSO!")
    println()
    println("═".repeat(40))
    println("   BEM-VINDO DE VOLTA!")
    println("═".repeat(40))
    println()
    println("👤 $nomeCadastrado")
    println("🏠 Feed · 🔍 Explorar · 🔔 Notificações")
    println()
    println("📊 SUAS ESTATÍSTICAS:")
    println("   • Posts: 45")
    println("   • Seguidores: 1,200")
    println("   • Seguindo: 340")
    println()
    println("🔥 3 novas notificações!")
    println("═".repeat(40))
    
} else {
    // LOGIN FALHOU
    println("❌ FALHA NO LOGIN!")
    println()
    println("═".repeat(40))
    
    if (!usernameCorreto && !senhaCorreta) {
        println("   ⚠️ Username e senha incorretos")
    } else if (!usernameCorreto) {
        println("   ⚠️ Username não encontrado")
        println("   💡 Sugestão: Verifique a ortografia")
    } else if (!senhaCorreta) {
        println("   ⚠️ Senha incorreta")
        println("   💡 Esqueceu a senha? Clique em 'Recuperar'")
    }
    
    println()
    println("   🔄 Tente novamente")
    println("   📝 Não tem conta? Cadastre-se!")
    println("═".repeat(40))
}

println()
println("Desenvolvido por THE FRA LABS 🇲🇿")
