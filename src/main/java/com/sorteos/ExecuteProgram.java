package com.sorteos;

import com.numeros.bll.CoreBll;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import com.numeros.csv.CsvProcessor;
import com.numeros.entity.Numeros;
import com.numeros.entity.Sorteo;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

public class ExecuteProgram {

    // public static void main(String[] str)
    // {
    //
    //
    //
    // JFileChooser choose = new JFileChooser();
    //
    // choose.showOpenDialog(null);
    //
    // File file = choose.getSelectedFile();
    //
    // ArrayList<Integer> listInteger = csvProce.process(file);
    //
    //
    // }

    public void execute(File file, int sorteo, int claveSorteo, boolean persistir, boolean filterDuplicate) {

        CsvProcessor csvProce = new CsvProcessor();

        ArrayList<Numeros> listInteger = csvProce.process(file, claveSorteo);

        if (persistir && listInteger != null && listInteger.size() > 0) {
            CoreBll persit = new CoreBll();

            if (filterDuplicate) {
                Numeros last = persit.getLastNumero(claveSorteo);
                listInteger = listInteger.stream().filter(filter -> filter.getDate().after(last.getDate()))
                        .collect(Collectors.toCollection(ArrayList::new));
            }

            listInteger.stream().forEach(x -> {

                Numeros item = x;

                if (item.getNumeroSorteo() <= 0)// to put the number for TEc and estrella
                    item.setNumeroSorteo(sorteo);

                item.setSorteoId(new Sorteo(claveSorteo));
                persit.create(item);
            });

        }

        JOptionPane.showMessageDialog(null, "Proceso terminado", "Message", JOptionPane.INFORMATION_MESSAGE);

    }
}
