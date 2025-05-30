package main;
import Gestor.GestorTareas;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        GestorTareas gestor = new GestorTareas();
        boolean permanecer = true;

        final int opcionUno = 1;
        final int opcionDos = 2;
        final int opcionTres = 3;
        final int opcionCuatro = 4;
        final int opcionCinco = 5;
        final int opcionSeis = 6;

        System.out.println("\n--- Bienvenido a TaskMaker ---\n");
        gestor.cargarTareas();

        do {
            System.out.println("¿Qué desea hacer?");
            System.out.println(opcionUno + ". Añadir una tarea");
            System.out.println(opcionDos + ". Mostrar tareas");
            System.out.println(opcionTres + ". Marcar tarea como completada");
            System.out.println(opcionCuatro + ". Editar tarea");
            System.out.println(opcionCinco + ". Eliminar tarea");
            System.out.println(opcionSeis + ". Salir");


            int opcionUsuario;
            try {
                opcionUsuario = Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Opción no valida. Elige una opción del 1 al 6\n");
                continue;
            }
            if (opcionUsuario < 1 || opcionUsuario> 6){
                System.out.println("Opción no valida. Elige una opción del 1 al 6\n");
                continue;
            }

            switch (opcionUsuario){
                case opcionUno:
                    gestor.añadirTarea();
                    gestor.guardarTareas();
                    break;

                case opcionDos:
                    gestor.mostrarTareas();
                    break;

                case opcionTres:
                    gestor.marcarCompletada();
                    gestor.guardarTareas();
                    break;

                case opcionCuatro:
                    gestor.editarTarea();
                    gestor.guardarTareas();
                    break;

                case opcionCinco:
                    gestor.eliminarTarea();
                    gestor.guardarTareas();
                    break;

                case opcionSeis:
                    gestor.guardarTareas();
                    System.out.println("Ciao!");
                    permanecer = false;
            }

        }while (permanecer);

    }
}