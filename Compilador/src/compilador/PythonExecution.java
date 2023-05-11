/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author mdavi
 */
class PythonExecution {
    public static void executePythonScript(String filePath) {
        // Tu código para ejecutar el script de Python
        try {
            // Construir el comando para ejecutar el script de Python en el símbolo del sistema
            ProcessBuilder processBuilder = new ProcessBuilder("python", filePath);

            // Redirigir la salida del proceso a la consola
            processBuilder.redirectErrorStream(true);

            // Iniciar el proceso
            Process process = processBuilder.start();

            // Leer la salida del proceso
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // Mostrar la salida en la consola
            }

            // Esperar a que el proceso termine
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Script de Python ejecutado exitosamente.");
            } else {
                System.out.println("Error al ejecutar el script de Python.");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
