println("╔════════════════════════════════════════╗")
println("║         CADASTRO MUNHU V1.0            ║")
println("╚════════════════════════════════════════╝")

// ========== COLETANDO DADOS ==========

print("\n👤 Digite seu nome: ")
val nome = readln()

print("📧 Digite seu email: ")
val email = readln()

print("🎂 Digite sua idade: ")
val idadeTexto = readln()
val idade = idadeTexto.toInt()

print("🇲🇿 Cidade: ")
val cidade = readln()

print("📱 Username desejado: ")
val username = readln()

// ========== PROCESSANDO DADOS ==========

val emailValido = email.contains("@") && email.contains(".")
val idadeValida = idade >= 13

println("\n" + "=".repeat(40))
println("📊 RESUMO DO CADASTRO")
println("=".repeat(40))

println("Nome: $nome")
println("Email: $email")
println("Idade: $idade anos")
println("Cidade: $cidade")
println("Username: @$username")

println("\n" + "=".repeat(40))
println("✅ VALIDAÇÕES")
println("=".repeat(40))

println("Email válido? ${if (emailValido) "✅ SIM" else "❌ NÃO"}")
println("Idade permitida? ${if (idadeValida) "✅ SIM (13+)" else "❌ NÃO (menor que 13)"}")

if (emailValido && idadeValida) {
    println("\n🎉 CADASTRO APROVADO!")
    println("Bem-vindo ao Munhu, $nome! 🇲🇿")
} else {
    println("\n⚠️ CADASTRO NEGADO!")
    if (!emailValido) println("- Email inválido")
    if (!idadeValida) println("- Idade mínima: 13 anos")
}
