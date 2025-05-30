package Gestor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestorTareas {
    private Scanner scanner = new Scanner(System.in);
    private List<Tarea> tareas = new ArrayList<>();

    public void añadirTarea() {
        boolean repetirAñadir;

        do {
            System.out.println("Introduce la tarea:");
            String descripcion = scanner.nextLine();
            tareas.add(new Tarea(descripcion));
            System.out.println("La tarea se ha añadido.\n");

            String opcion;
            do {
                System.out.println("¿Deseas añadir otra tarea? (S/N)");
                opcion = scanner.nextLine().trim().toUpperCase();
                if (!opcion.equals("S") && !opcion.equals("N")) {
                    System.out.println("No has introducido una opción válida (S o N).");
                }
            } while (!opcion.equals("S") && !opcion.equals("N"));

            repetirAñadir = opcion.equals("S");

        } while (repetirAñadir);
    }

    public void mostrarTareas() {
        if (tareas.isEmpty()) {
            System.out.println("No hay tareas guardadas\n");
            return;
        }

        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";

        System.out.println("\nTareas actuales:");
        for (int i = 0; i < tareas.size(); i++) {
            Tarea tarea = tareas.get(i);
            String estado = tarea.isCompletada() ? "Completada" : "Pendiente";
            String color = tarea.isCompletada() ? ANSI_GREEN : ANSI_RED;
            System.out.println(color + (i + 1) + ". " + tarea.getDescripcion() + " - " + estado + ANSI_RESET);
        }
        System.out.println();
    }

    public void marcarCompletada() {
        mostrarTareas();
        if (tareas.isEmpty()) return;

        System.out.println("Selecciona el número de la tarea que quieres marcar como completada:");
        int seleccion = leerNumero();

        if (seleccion > 0 && seleccion <= tareas.size()) {
            Tarea tarea = tareas.get(seleccion - 1);
            if (tarea.isCompletada()) {
                System.out.println("La tarea ya está marcada como completada.\n");
                return;
            }

            System.out.println("Descripción de la tarea: " + tarea.getDescripcion());
            System.out.println("¿Estás seguro que deseas marcarla como completada? (S/N)");
            String confirmar = scanner.nextLine().trim().toUpperCase();

            if (confirmar.equals("S")) {
                tarea.marcarCompletada();
                guardarTareas();
                System.out.println("Tarea marcada como completada.\n");
            } else {
                System.out.println("Acción cancelada.\n");
            }
        } else {
            System.out.println("Número de tarea inválido\n");
        }
    }

    public void editarTarea() {
        mostrarTareas();
        if (tareas.isEmpty()) return;

        System.out.println("Selecciona el número de la tarea que quieres editar:");
        int seleccion = leerNumero();

        if (seleccion > 0 && seleccion <= tareas.size()) {
            Tarea tarea = tareas.get(seleccion - 1);
            System.out.println("Descripción actual: \"" + tarea.getDescripcion() + "\"");

            System.out.println("Introduce la nueva descripción de la tarea:");
            String nuevaDescripcion = scanner.nextLine();

            System.out.println("¿Deseas guardar los cambios? (S/N)");
            String confirmar = scanner.nextLine().trim().toUpperCase();

            if (confirmar.equals("S")) {
                tarea.setDescripcion(nuevaDescripcion);
                guardarTareas();
                System.out.println("Tarea actualizada correctamente.\n");
            } else {
                System.out.println("Edición cancelada.\n");
            }
        } else {
            System.out.println("Número de tarea inválido\n");
        }
    }

    public void eliminarTarea() {
        mostrarTareas();
        if (tareas.isEmpty()) return;

        System.out.println("Selecciona el número de la tarea que quieres eliminar:");
        int seleccion = leerNumero();

        if (seleccion > 0 && seleccion <= tareas.size()) {
            Tarea tarea = tareas.get(seleccion - 1);
            System.out.println("Has seleccionado: \"" + tarea.getDescripcion() + "\"");

            System.out.println("¿Estás seguro de que deseas eliminar esta tarea? (S/N)");
            String confirmar = scanner.nextLine().trim().toUpperCase();

            if (confirmar.equals("S")) {
                tareas.remove(seleccion - 1);
                guardarTareas();
                System.out.println("Tarea eliminada correctamente.\n");
            } else {
                System.out.println("Eliminación cancelada.\n");
            }
        } else {
            System.out.println("Número de tarea inválido\n");
        }
    }

    public void guardarTareas() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("tareas.txt"))) {
            for (Tarea tarea : tareas) {
                writer.println(tarea.getDescripcion() + ";" + tarea.isCompletada());
            }
            System.out.println("Tareas guardadas correctamente.\n");
        } catch (IOException e) {
            System.out.println("Error al guardar las tareas.");
        }
    }

    public void cargarTareas() {
        try (Scanner fileScanner = new Scanner(new File("tareas.txt"))) {
            while (fileScanner.hasNextLine()) {
                String[] datos = fileScanner.nextLine().split(";");
                tareas.add(new Tarea(datos[0], Boolean.parseBoolean(datos[1])));
            }
            System.out.println("Tareas cargadas correctamente.\n");
        } catch (IOException e) {
            System.out.println("No se encontraron tareas guardadas.\n");
        }
    }

    // Utilidad para leer enteros y manejar errores
    private int leerNumero() {
        int numero = -1;
        try {
            numero = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada no válida. Debe ser un número.\n");
        }
        return numero;
    }
}
