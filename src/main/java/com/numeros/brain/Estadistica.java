/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.numeros.brain;

import javax.swing.JOptionPane;

public class Estadistica {
    public Regresion regresion;
    private double[] valores;
    private int longitud;

    public Estadistica(double[] values) {
        try {
            if (values.length > 0) {
                valores = values.clone();
                longitud = values.length;
                double[] y = new double[longitud];
                for (int i = 0; i < longitud; i++) {
                    y[i] = i + 1;
                }
                regresion = new Regresion(valores, y);
            }
        } catch (java.lang.NullPointerException exc) {
            JOptionPane.showMessageDialog(null, "Los valores no estan correctos");
        }
    }

    public void setValues(double[] values) {
        try {
            if (values.length > 0) {
                valores = values.clone();
                longitud = values.length;
            }
        } catch (java.lang.NullPointerException exc) {

        }
    }

    public double[] getvalues() {
        return valores.clone();
    }

    public double getMedia() {
        try {
            double suma = 0;
            for (int i = 0; i < valores.length; i++) {
                suma += valores[i];
            }
            return (suma / valores.length);
        } catch (java.lang.NullPointerException exc) {
            return 0.0;
        }
    }

    public double getMediana() {
        try {
            if (longitud % 2 == 0) {
                double x1 = valores[longitud / 2];
                double x2 = valores[(longitud - 2) / 2];
                return ((x1 + x2) / 2);
            } else {
                double x = valores[(longitud - 1) / 2];
                return x;
            }
        } catch (java.lang.NullPointerException exc) {
            return 0.0;
        }
    }

    public double getVarianza() {
        try {
            double Xm = getMedia();
            double suma = 0;
            for (int i = 0; i < valores.length; i++) {
                double Xi = valores[i];
                suma += Math.pow((Xi - Xm), 2);
            }
            return (suma / valores.length);
        } catch (java.lang.NullPointerException exc) {
            return 0.0;
        }
    }

    public double getDesvTipica() {
        return (Math.sqrt(getVarianza()));
    }

    public String getModa() {

        try {
            // tomo la longitud del arreglo, me va a servir pa todos los arreglos
            int n = valores.length;
            // hago un clon de los valores y los guardo en datos
            double[] datos = valores.clone();
            // hago un vector pa contar la fracuencia de cada dato
            int[] frec = new int[n];

            // el arreglo datos y frec, me serviran como una tabla pa anotar
            // los valores en datos y su respectiva frecuencia en frec

            // verifico cada elemento de valores, si encuentro uno igual en datos aunmento su frecuencia y
            // qiebro el bucle pa seguir con el siguiente elemento, de modo q voy a tener una tabla de frecuencias
            // cada dato tendra su respectiva frecuencia en le arreglo frec, si es uno repetido tendra frec 0
            for (int i = 0; i < n; i++) {
                double val = valores[i];
                for (int j = 0; j < n; j++) {
                    if (val == datos[j]) {
                        frec[j]++;
                        break;
                    }
                }
            }

            // voy a buscar la moda, sera el qe tenga la frec maxima
            int maxFrec = 0;
            double moda = 0;
            // comparo cada valor del arreglo frec con el maxFrec, la moda sera el respectivo del arreglo datos
            for (int i = 0; i < n; i++) {
                if (frec[i] > maxFrec) {
                    maxFrec = frec[i];
                    moda = datos[i];
                }
            }

            // si hay dos datos con la misma frecuencia, no lo sabre hasta verificarlo en el bucle siguiente
            // si encuentro 2 veces la maxFrec en el arrglo frec el cont valdra 1
            int cont = 0;
            for (int i = 0; i < n; i++) {
                if (maxFrec == frec[i])
                    cont++;
            }

            // si se ha repetido la moda return 0, sino retrun la moda
            // if(cont>1)
            // return "No hay moda";
            // else
            return String.valueOf(moda);
        } catch (java.lang.NullPointerException exc) {
            return "";
        }

    }

    public double getSumX() {

        double suma = 0;
        for (int i = 0; i < valores.length; i++) {
            suma += valores[i];
        }
        return suma;
    }

    public double getSumY() {
        double suma = 0;
        for (int i = 1; i < valores.length; i++) {
            suma += i;
        }
        return suma;
    }

    public double getMediaY() {
        try {
            double suma = 0;
            for (int i = 1; i < valores.length; i++) {
                suma += i;
            }
            return (suma / valores.length);
        } catch (java.lang.NullPointerException exc) {
            return 0.0;
        }
    }

    public double getCorrelacion() {

        return regresion.correlacion();
    }

    public double desviacionMedia() {
        double suma = 0;
        double media = getMedia();
        for (int i = 0; i < valores.length; i++) {
            suma += Math.abs(valores[i] - media);
        }
        return suma / valores.length;
    }

    public double desviacionCuadratica() {
        double suma = 0;
        double media = getMedia();
        for (int i = 0; i < valores.length; i++) {
            suma += (valores[i] - media) * (valores[i] - media);
        }
        return Math.sqrt(suma / valores.length);
    }
}
