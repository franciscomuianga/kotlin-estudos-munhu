println("═══════════════════════════════════════")
println("    MUNHU - VERIFICADOR DE IDADE")
println("═══════════════════════════════════════")

print("\n🎂 Digite sua idade: ")
val idade = readln().toInt()

// ========== IF SIMPLES ==========

println("\n=== IF SIMPLES ===")

if (idade >= 18) {
    println("✅ Você é maior de idade!")
}

if (idade < 18) {
    println("⚠️ Você é menor de idade!")
}

// ========== IF/ELSE ==========

println("\n=== IF/ELSE ===")

if (idade >= 18) {
    println("🎉 Acesso total ao Munhu!")
} else {
    println("⚠️ Precisa de autorização dos pais")
}

// ========== IF/ELSE IF/ELSE ==========

println("\n=== CLASSIFICAÇÃO ETÁRIA ===")

if (idade < 13) {
    println("❌ Idade mínima: 13 anos")
    println("   Volte em ${13 - idade} ano(s)")
} else if (idade < 18) {
    println("⚠️ Menor de idade")
    println("   Requer autorização parental")
} else if (idade < 60) {
    println("✅ Adulto")
    println("   Acesso completo")
} else {
    println("👴 Sênior")
    println("   Acesso completo + modo simplificado")
}

// ========== CONDIÇÕES COMPOSTAS ==========

println("\n=== VERIFICAÇÃO DE PERMISSÕES ===")

val temAutorizacao = true

if (idade >= 13 && idade < 18 && temAutorizacao) {
    println("✅ Menor autorizado pode usar!")
} else if (idade >= 13 && idade < 18 && !temAutorizacao) {
    println("❌ Precisa de autorização dos pais")
} else if (idade >= 18) {
    println("✅ Acesso liberado automaticamente")
} else {
    println("❌ Idade insuficiente")
}

// ========== IF COMO EXPRESSÃO (retorna valor) ==========

println("\n=== IF COMO EXPRESSÃO ===")

val status = if (idade >= 18) "Adulto" else "Menor"
println("Status: $status")

val permissao = if (idade >= 18) {
    "completa"
} else if (idade >= 13) {
    "limitada"
} else {
    "negada"
}

println("Permissão: $permissao")

// ========== VALIDAÇÃO DE CONTEÚDO ==========

println("\n=== CONTEÚDO PERMITIDO ===")

val podeverConteudoAdulto = if (idade >= 18) "SIM" else "NÃO"
val podePostar = if (idade >= 13) "SIM" else "NÃO"
val podeComentar = if (idade >= 13) "SIM" else "NÃO"

println("Ver conteúdo adulto: $podeverConteudoAdulto")
println("Postar: $podePostar")
println("Comentar: $podeComentar")
