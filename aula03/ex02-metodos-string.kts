val texto = "Munhu - Rede Social Moçambicana"

println("=== TEXTO ORIGINAL ===")
println(texto)

// ========== PROPRIEDADES ==========

println("\n=== PROPRIEDADES ===")
println("Comprimento: ${texto.length} caracteres")
println("Primeiro caractere: ${texto[0]}")
println("Último caractere: ${texto[texto.length - 1]}")
println("Está vazio? ${texto.isEmpty()}")
println("Não está vazio? ${texto.isNotEmpty()}")

// ========== TRANSFORMAÇÕES ==========

println("\n=== TRANSFORMAÇÕES ===")
println("MAIÚSCULAS: ${texto.uppercase()}")
println("minúsculas: ${texto.lowercase()}")
println("Capitalizado: ${texto.capitalize()}")

// ========== VERIFICAÇÕES ==========

println("\n=== VERIFICAÇÕES ===")
println("Começa com 'Munhu'? ${texto.startsWith("Munhu")}")
println("Termina com 'cana'? ${texto.endsWith("cana")}")
println("Contém 'Social'? ${texto.contains("Social")}")
println("Contém 'Facebook'? ${texto.contains("Facebook")}")

// ========== BUSCA ==========

val palavra = "Social"
val indice = texto.indexOf(palavra)
println("\n=== BUSCA ===")
println("'$palavra' está na posição: $indice")

// ========== SUBSTITUIÇÃO ==========

println("\n=== SUBSTITUIÇÃO ===")
val novoTexto = texto.replace("Moçambicana", "de Moçambique")
println("Original: $texto")
println("Modificado: $novoTexto")

// ========== SUBSTRING ==========

println("\n=== SUBSTRING ===")
val nomeProjeto = texto.substring(0, 5)  // "Munhu"
println("Primeiros 5 caracteres: $nomeProjeto")

val tipo = texto.substring(8, 19)  // "Rede Social"
println("Do 8º ao 19º: $tipo")

// ========== SPLIT (DIVIDIR) ==========

val usuario = "francisco@munhu.co.mz"
val partes = usuario.split("@")
println("\n=== SPLIT ===")
println("Email: $usuario")
println("Username: ${partes[0]}")
println("Domínio: ${partes[1]}")

// ========== TRIM (REMOVER ESPAÇOS) ==========

val comEspacos = "   Munhu   "
println("\n=== TRIM ===")
println("Com espaços: '${comEspacos}'")
println("Sem espaços: '${comEspacos.trim()}'")

// ========== REPEAT ==========

println("\n=== REPEAT ===")
println("=" * 40)  // Não funciona!
println("=".repeat(40))  // Funciona!
