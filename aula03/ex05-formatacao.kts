println("═══════════════════════════════════════")
println("    MUNHU - CRIADOR DE POSTS")
println("═══════════════════════════════════════")

print("\n✍️ Escreva seu post: ")
val post = readln()

// ========== ESTATÍSTICAS ==========

val numCaracteres = post.length
val numPalavras = post.split(" ").size
val numLinhas = post.split("\n").size

println("\n📊 ESTATÍSTICAS DO POST:")
println("   Caracteres: $numCaracteres / 280")
println("   Palavras: $numPalavras")
println("   Linhas: $numLinhas")

// ========== VALIDAÇÃO ==========

val dentroDolimite = numCaracteres <= 280
val naoVazio = post.isNotBlank()

if (naoVazio && dentroDolimite) {
    println("\n✅ POST VÁLIDO!")
    
    // ========== PREVIEW ==========
    println("\n" + "─".repeat(40))
    println("📱 PREVIEW:")
    println("─".repeat(40))
    println("👤 @francisco · agora")
    println()
    println(post)
    println()
    println("💬 0   🔄 0   ❤️ 0")
    println("─".repeat(40))
    
    // ========== PROCESSAMENTO ==========
    println("\n🔍 ANÁLISE:")
    
    // Detectar hashtags
    val palavras = post.split(" ")
    val hashtags = palavras.filter { it.startsWith("#") }
    if (hashtags.isNotEmpty()) {
        println("   Hashtags encontradas: ${hashtags.joinToString(", ")}")
    }
    
    // Detectar menções
    val mencoes = palavras.filter { it.startsWith("@") }
    if (mencoes.isNotEmpty()) {
        println("   Menções: ${mencoes.joinToString(", ")}")
    }
    
    // Tom do post
    val palavrasPositivas = listOf("feliz", "ótimo", "incrível", "legal", "bom")
    val temPalavraPositiva = palavrasPositivas.any { post.lowercase().contains(it) }
    
    if (temPalavraPositiva) {
        println("   Tom: 😊 Positivo")
    }
    
} else {
    println("\n❌ POST INVÁLIDO!")
    if (!naoVazio) println("   • Post não pode estar vazio")
    if (!dentroDolimite) println("   • Limite de 280 caracteres excedido")
}
