package com.numeros.csv;

import com.numeros.entity.Numeros;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;

public class CsvProcessor {

    public ArrayList<Numeros> process(File file, int type) {
        BufferedReader br = null;
        String line = "";
      
        ArrayList<Numeros> listResult = new ArrayList();
        try {

            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                
                
                
                switch(type)
                        {
                    case 1:case 2:
                        this.tecAndStar(line, listResult);
                        break;
                    
                    case 3:
                        this.melate(line, listResult);
                        break;
                     
                        
                        }

                

            }

        } catch (FileNotFoundException e) {
//            JOptionPane.showMessageDialog(null, "Error al abrir el archivo", "Message", JOptionPane.ERROR);
            e.printStackTrace();
        } catch (IOException e) {
//            JOptionPane.showMessageDialog(null, "Error al leer el archivo", "Message", JOptionPane.ERROR);
            e.printStackTrace();
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Error fatal", "Message", JOptionPane.ERROR);
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return listResult;

    }
    
    private void tecAndStar(String line, ArrayList<Numeros> listResult){
          String cvsSplitBy = ",";
    String[] row = line.split(cvsSplitBy);
                if (row[0].trim().length() > 15) {
                    String temp = row[0].trim().substring(4);

                    String finalChain = temp.trim().substring(0, 6);
                    if (finalChain.trim().chars().allMatch(Character::isDigit)) {
                        System.out.println(completeNumber(finalChain.trim(),6));
                        Numeros numeroTemp = new Numeros();
                        numeroTemp.setNumero(completeNumber(finalChain.trim(),6));
                        listResult.add(numeroTemp);
                    } else {
                         System.out.println(finalChain+"==============NO VALIDO");
                    }

                } else {
                    String finalChain = row[1].trim().replace("R", "");
                    if (finalChain.trim().chars().allMatch(Character::isDigit)) {
                        System.out.println(completeNumber(finalChain.trim(),6));
                       Numeros numeroTemp = new Numeros();
                        numeroTemp.setNumero(completeNumber(finalChain.trim(),6));
                        listResult.add(numeroTemp);
                    } else {
                        System.out.println(finalChain+"==============NO VALIDO");
                    }

                }
    
    }
    
    
        
    private void melate(String line, ArrayList<Numeros> listResult) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
          String cvsSplitBy = ",";
          String number="";
             String[] row = line.split(cvsSplitBy);
                    boolean _valid=true;
                    for(int i =2; i<=8;i++){
                     String finalChain = row[i].trim();
                    if (finalChain.trim().chars().allMatch(Character::isDigit)) {
                       
                        number=number+completeNumber(finalChain.trim(),2);
                    } else {
                        System.out.println(finalChain+"==============NO VALIDO");
                        _valid=false;
                    }
                    
                    }
                    
                    if(_valid)
                    {
                     System.out.println(number);
                        Numeros numeroTemp = new Numeros();
                        numeroTemp.setNumero(number.trim());
                        Date date= null;
                        String dateString = row[10].trim();
                        try{
                        date = formatter.parse(dateString);
                        }catch(Exception e)
                        {
                         System.out.println(date+"==============NO VALIDO");
                        }
                        numeroTemp.setDate(date);
                        numeroTemp.setNumeroSorteo(Integer.parseInt(row[1].trim()));
                        listResult.add(numeroTemp);
                    }
                   
                   

                
    
    }
    public String completeNumber(String numb,int total){
    
      while( numb.length()<total)
      {
      numb=0+numb;
      }
      return numb;
    }

}
