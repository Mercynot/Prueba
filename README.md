X 📱 Prueba - Programación Android

🚀 XX Resumen XX
Aplicación Android con un objetivo principal agregar intents (Explícitos e Implícitos), gestión de permisos en tiempo de ejecución (Cámara), uso de sensores (Flash/Linterna) y procesamiento en segundo plano mediante Threads.

🛠️ XX Versiones utilizadas: XX
* Android Studio (Panda)
* Lenguaje: Java
* SDK Mínimo: API 24 / Target: API 34

···························································

XX 🔗 Lista de Intents agregados XX

XX 🌐 Intents Implícitos XX
1. **Abrir Web:** `ACTION_VIEW` para abrir la página de Santo Tomás
2. **Enviar Correo:** `ACTION_SENDTO` con asunto y cuerpo prellenado (mailto).
3. **Compartir Texto:** `ACTION_SEND` para enviar un mensaje nativo a otras apps.
4. **Tomar Fotografía:** `MediaStore.ACTION_IMAGE_CAPTURE` usando `FileProvider` para guardar la imagen original.
5. **Ver en Mapa:** `ACTION_VIEW` con URI `geo:` para mostrar la sede.
6. **Llamar Telefónicamente:** `ACTION_DIAL` con esquema `tel:` para abrir el marcador telefónico.

XX 🎯 Intents Explícitos XX 
1. MainActivity a PerfilActivity: Envía datos (email) mediante `putExtra` y espera un resultado usando `ActivityResultLauncher` para actualizar el nombre de bienvenida.
2. MainActivity a CamaraActivity: Navegación directa hacia el módulo fotográfico.
3. PerfilActivity a AyudaActivity: Navegación hacia la guía de uso o FAQ.


···························································

## 📸 Capturas de Pantalla

📂 `app/build/outputs/apk/debug/app-debug.apk`<img width="512" height="888" alt="Main" src="https://github.com/user-attachments/assets/2a1fc520-52fe-42d3-a838-3a2aef96f331" />

<img width="487" height="868" alt="MIperfil" src="https://github.com/user-attachments/assets/84de47ba-cf2f-454a-ab3e-3e05172df05e" />
<img width="481" height="873" alt="Mian" src="https://github.com/user-attachments/assets/6c7238ab-78d4-497e-822c-8661765e0208" />

🧚‍♂️🗣: Al Hacer Click En Portal SantoTomas Nos Dirige A La Pagina Oficial
<img width="468" height="859" alt="Abrirportalsantotomas" src="https://github.com/user-attachments/assets/499bde2d-032d-4a33-b591-42a97771ce34" />

🧚‍♂️🗣: Me logue Para Mostrar Que Funciona :V

🧚‍♂️🗣: Al Hacer Click Tambien Mandar Corrreo Nos Dirige AL Gmail
<img width="610" height="870" alt="Gmail" src="https://github.com/user-attachments/assets/17ce35fa-4656-4bb8-8c1c-1ed1d5a7509e" />


🧚‍♂️🗣: En Comparti Texto Nos Aparece Para Poder Compartir En Redes,Correo,ETC...
<img width="472" height="836" alt="compartir texto" src="https://github.com/user-attachments/assets/d650aa1e-d0cd-49b4-9b5f-e4fc9b140edf" />




