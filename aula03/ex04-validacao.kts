println("═══════════════════════════════════════")
println("    MUNHU - VALIDAÇÃO DE USERNAME")
println("═══════════════════════════════════════")

print("\n📱 Escolha seu username: @")
val username = readln()

println("\n🔍 VALIDANDO...\n")

// ========== REGRAS DE VALIDAÇÃO ==========

val tamanhoMinimo = username.length >= 3
val tamanhoMaximo = username.length <= 15
val semEspacos = !username.contains(" ")
val somenteCaracteresValidos = username.all { 
    it.isLetterOrDigit() || it == '_' || it == '.'
}
val comecaComLetra = username[0].isLetter()

// ========== RESULTADOS ==========

println("📏 Tamanho (3-15 caracteres): ${if (tamanhoMinimo && tamanhoMaximo) "✅" else "❌"} (${username.length})")
println("🚫 Sem espaços: ${if (semEspacos) "✅" else "❌"}")
println("🔤 Apenas letras, números, _ ou .: ${if (somenteCaracteresValidos) "✅" else "❌"}")
println("📝 Começa com letra: ${if (comecaComLetra) "✅" else "❌"}")

// ========== DECISÃO FINAL ==========

val usernameValido = tamanhoMinimo && tamanhoMaximo && semEspacos && 
                     somenteCaracteresValidos && comecaComLetra

println("\n" + "═".repeat(40))

if (usernameValido) {
    println("🎉 USERNAME DISPONÍVEL!")
    println("Seu perfil: munhu.co.mz/@$username")
} else {
    println("❌ USERNAME INVÁLIDO!")
    println("\n💡 DICAS:")
    if (!tamanhoMinimo) println("   • Use pelo menos 3 caracteres")
    if (!tamanhoMaximo) println("   • Máximo de 15 caracteres")
    if (!semEspacos) println("   • Não use espaços")
    if (!somenteCaracteresValidos) println("   • Use apenas: a-z, 0-9, _ ou .")
    if (!comecaComLetra) println("   • Comece com uma letra")
}

println("═".repeat(40))
