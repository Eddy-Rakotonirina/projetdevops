package com.monapp;

public class Triangle {

    private double a, b, c;

    public Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    // Vérifie si les côtés forment un triangle valide
    public boolean isValid() {
        return (a + b > c) && (a + c > b) && (b + c > a);
    }

    // Calcule le périmètre
    public double perimeter() {
        return a + b + c;
    }

    // Calcule l'aire (formule de Héron)
    public double area() {
        double s = perimeter() / 2;
        return Math.sqrt(s * (s-a) * (s-b) * (s-c));
    }

    public static void main(String[] args) {
        Triangle t = new Triangle(3, 4, 5);
        System.out.println("Valide : " + t.isValid());
        System.out.println("Périmètre : " + t.perimeter());
        System.out.println("Aire : " + t.area());
    }
}