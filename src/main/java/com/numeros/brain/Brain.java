/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.numeros.brain;

import com.numeros.bll.CoreBll;
import com.numeros.entity.Numeros;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.groupingBy;
import javax.swing.JTextField;

/**
 *
 * @author jluis
 */
public class Brain {
    
    private CoreBll core;
    
    public Brain(){
    
    core= new CoreBll();
    }
    
    
    
    public String concurrencias(int sorteoId){
    
        String result="";
        
        HashMap<Integer,HashMap<Integer,Integer>> controlNumbers = new HashMap<>();
         HashMap<Integer,HashMap<Integer,Integer>> controlNumbersTemp = new HashMap<>();
         String [] maybe = new String[54];
        controlNumbers.put(0, new HashMap<Integer,Integer>());
        controlNumbers.put(1, new HashMap<Integer,Integer>());
        controlNumbers.put(2, new HashMap<Integer,Integer>());
        controlNumbers.put(3, new HashMap<Integer,Integer>());
        controlNumbers.put(4, new HashMap<Integer,Integer>());
        controlNumbers.put(5, new HashMap<Integer,Integer>());
        controlNumbersTemp.put(0, new HashMap<Integer,Integer>());
        controlNumbersTemp.put(1, new HashMap<Integer,Integer>());
        controlNumbersTemp.put(2, new HashMap<Integer,Integer>());
        controlNumbersTemp.put(3, new HashMap<Integer,Integer>());
        controlNumbersTemp.put(4, new HashMap<Integer,Integer>());
        controlNumbersTemp.put(5, new HashMap<Integer,Integer>());
        core.getAllBySorteo(sorteoId).stream().forEach(item->{
        int i=0;
        for(char num :item.getNumero().toCharArray())
        {
            int numTemp = Integer.parseInt(""+num);
            if(controlNumbers.get(i).containsKey(numTemp))
            {
                Integer aux = controlNumbers.get(i).get(numTemp);
                controlNumbers.get(i).replace(numTemp, ++aux);
            }
            else{
             controlNumbers.get(i).put(numTemp,1);
            }
            i++;
        }
        });
    
         for(int j=0;j<=5;j++)
        {
          
            Entry<Integer,Integer> entTemp = null;
             for(Iterator<Entry<Integer, Integer>> it = controlNumbers.get(j).entrySet().stream()
                .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue())).iterator(); it.hasNext(); ) {
                 Entry<Integer, Integer> ent = it.next();
            
                if(entTemp!=null)
                {
                    if(entTemp.getValue()<=ent.getValue())
                    {
                        
                        entTemp=ent;
                        controlNumbersTemp.get(j).put(entTemp.getKey(), entTemp.getValue());
                    }
                   
                    
                }
                else
                {
                    entTemp=ent;
                    controlNumbersTemp.get(j).put(entTemp.getKey(), entTemp.getValue());
                }
            }  
            
        }
         
         ArrayList<String> elems=new ArrayList();
         elems.add("");
         for(Entry<Integer,HashMap<Integer,Integer>> entry :controlNumbersTemp.entrySet() )
         {
             ArrayList<String> elemsTemp=new ArrayList();
             for(Entry<Integer,Integer> entRow : entry.getValue().entrySet())
             {
                  
                  for(String item : elems)
                  {
                      elemsTemp.add(item+entRow.getKey());
                  
                  }
                 
             }
             elems=elemsTemp;
         }
        
         
         
         
      for(String data : elems)
      {
          result=result.isEmpty()?  result+""+data:data+",\r\n"+result;
      }
        return result;
    }

    public String numerosRepetidos(int sorteoId) {
     Map<String,List<Numeros>> map;
     Map<String,Integer> mapTemp= new HashMap<>();
     String result="";
        map = core.getAllBySorteo(sorteoId).stream().collect(Collectors.groupingBy(x -> x.getNumero()));
        
        
        for(Entry<String,List<Numeros> > entry : map.entrySet())
        {
            if(entry.getValue().size()>1)
                mapTemp.put(entry.getKey(), entry.getValue().size());
        
        }
        
        for(Entry<String,Integer > entry : mapTemp.entrySet())
        {
        result =    result.concat(entry.getKey()+"  repeticiones: "+entry.getValue()+"   \n\r");
        
        }
        return result;
    }

    public String findNumero(String numero, int sorteoTipo) {
       if(sorteoTipo==3)
       {
       
       return this.findNumeroMelate(numero, sorteoTipo);
       }
       else
       {
         String result="";
      List<Numeros> listTemp =   core.getAllbyNumero(numero,sorteoTipo);
        
        
       
        return result+numero+" total:"+listTemp.size();
       }
   
    }

    
    public String findNumeroMelate(String numero, int sorteoTipo) {
       
     String result="";
      List<Numeros> listTemp =   core.getAllbyNumero(numero,sorteoTipo);
        
        
       result = result+numero+" total:"+listTemp.size();
       
       for(Numeros item : listTemp)
       {
           result = result+"\n\r"+item.getNumero();
       
       }
       
        return result;
    }
    
    public String calculateNumero(String numero, int sorteoId) {
         String result="";
         HashMap<Integer,HashMap<Integer,Integer>> controlNumbers = new HashMap<>();
         String [] maybe = new String[54];
         
      
         
      
        controlNumbers.put(0, new HashMap<Integer,Integer>());
        controlNumbers.put(1, new HashMap<Integer,Integer>());
        controlNumbers.put(2, new HashMap<Integer,Integer>());
        controlNumbers.put(3, new HashMap<Integer,Integer>());
        controlNumbers.put(4, new HashMap<Integer,Integer>());
        controlNumbers.put(5, new HashMap<Integer,Integer>());

        int j=0;
          for(char num : numero.toCharArray())
        {
        controlNumbers.get(j).put(Integer.parseInt(""+num),0 );
        j++;
        }
         
        core.getAllBySorteo(sorteoId).stream().forEach(item->{
        int i=0;
        for(char num :item.getNumero().toCharArray())
        {
            int numTemp = Integer.parseInt(""+num);
            if(controlNumbers.get(i).containsKey(numTemp))
            {
                Integer aux = controlNumbers.get(i).get(numTemp);
                controlNumbers.get(i).replace(numTemp, ++aux);
            }
           
            i++;
        }
        
       
        
        });
        
        
        for(Entry<Integer,HashMap<Integer,Integer>> entry : controlNumbers.entrySet())
        {
            
            result=result+"Posicion:" +(entry.getKey()+1)+"    veces: "+entry.getValue()+" \r\n";
        }
                
                
        return result;
    }
    
        public String calculateNumeroMelate(String numero, int sorteoId, Date inicio, Date fin) {
         String result="";
         HashMap<Integer,HashMap<Integer,Integer>> controlNumbers = new HashMap<>();
         String [] maybe = new String[54];
         
      
         
      
        controlNumbers.put(0, new HashMap<Integer,Integer>());
        controlNumbers.put(1, new HashMap<Integer,Integer>());
        controlNumbers.put(2, new HashMap<Integer,Integer>());
        controlNumbers.put(3, new HashMap<Integer,Integer>());
        controlNumbers.put(4, new HashMap<Integer,Integer>());
        controlNumbers.put(5, new HashMap<Integer,Integer>());
//        controlNumbers.put(6, new HashMap<Integer,Integer>()); //adicional
        int j=0;
          for(String num :  this.bytwo(numero) )
        {
        controlNumbers.get(j).put(Integer.parseInt(""+num),0 );
        j++;
        if(j==6) //adicional
            break;
        }
         
        core.getAllBySorteo(sorteoId,inicio,fin).stream().forEach(item->{
        int i=0;
        for(String num :this.bytwo(item.getNumero()))
        {
            int numTemp = Integer.parseInt(""+num);
            if(controlNumbers.get(i).containsKey(numTemp))
            {
                Integer aux = controlNumbers.get(i).get(numTemp);
                controlNumbers.get(i).replace(numTemp, ++aux);
            }
           
            i++;
            if(i==6) //adicional
            break;
        }
        
       
        
        });
        
        
        for(Entry<Integer,HashMap<Integer,Integer>> entry : controlNumbers.entrySet())
        {
            
            result=result+"Posicion:" +(entry.getKey()+1)+"    veces: "+entry.getValue()+" \r\n";
        }
                
                
        return result;
    }
    
    
    public String concurrenciasMelate(int sorteoId, Date inicio, Date fin){
    
        String result="";
        
        HashMap<Integer,HashMap<Integer,Integer>> controlNumbers = new HashMap<>();
         HashMap<Integer,HashMap<Integer,Integer>> controlNumbersTemp = new HashMap<>();
         String [] maybe = new String[54];
        controlNumbers.put(0, new HashMap<Integer,Integer>());
        controlNumbers.put(1, new HashMap<Integer,Integer>());
        controlNumbers.put(2, new HashMap<Integer,Integer>());
        controlNumbers.put(3, new HashMap<Integer,Integer>());
        controlNumbers.put(4, new HashMap<Integer,Integer>());
        controlNumbers.put(5, new HashMap<Integer,Integer>());
//        controlNumbers.put(6, new HashMap<Integer,Integer>()); //adicional
        controlNumbersTemp.put(0, new HashMap<Integer,Integer>());
        controlNumbersTemp.put(1, new HashMap<Integer,Integer>());
        controlNumbersTemp.put(2, new HashMap<Integer,Integer>());
        controlNumbersTemp.put(3, new HashMap<Integer,Integer>());
        controlNumbersTemp.put(4, new HashMap<Integer,Integer>());
        controlNumbersTemp.put(5, new HashMap<Integer,Integer>());
        
        
        
//        controlNumbersTemp.put(6, new HashMap<Integer,Integer>()); //adicional


           return this.concurrenciasListaTipoMelate(core.getAllBySorteo(sorteoId,inicio,fin));

//        core.getAllBySorteo(sorteoId,inicio,fin).stream().forEach(item->{
//        int i=0;
//          String[] numbers = this.bytwo(item.getNumero());
//        for(String num :numbers)
//        {
//            int numTemp = Integer.parseInt(""+num);
//          
//            if(controlNumbers.get(i).containsKey(numTemp))
//            {
//                Integer aux = controlNumbers.get(i).get(numTemp);
//                controlNumbers.get(i).replace(numTemp, ++aux);
//            }
//            else{
//             controlNumbers.get(i).put(numTemp,1);
//            }
//            i++;
//            if(i==6) //adicional
//                break;
//        }
//        });
//    
//         for(int j=0;j<=5;j++)
//        {
//          
//            Entry<Integer,Integer> entTemp = null;
//             for(Iterator<Entry<Integer, Integer>> it = controlNumbers.get(j).entrySet().stream()
//                .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue())).iterator(); it.hasNext(); ) {
//                 Entry<Integer, Integer> ent = it.next();
//            
//                if(entTemp!=null)
//                {
//                    if(entTemp.getValue()<=ent.getValue())
//                    {
//                        
//                        entTemp=ent;
//                        controlNumbersTemp.get(j).put(entTemp.getKey(), entTemp.getValue());
//                    }
//                   
//                    
//                }
//                else
//                {
//                    entTemp=ent;
//                    controlNumbersTemp.get(j).put(entTemp.getKey(), entTemp.getValue());
//                }
//            }  
//            
//        }
//         
//         ArrayList<String> elems=new ArrayList();
//         elems.add("");
//         for(Entry<Integer,HashMap<Integer,Integer>> entry :controlNumbersTemp.entrySet() )
//         {
//             ArrayList<String> elemsTemp=new ArrayList();
//             for(Entry<Integer,Integer> entRow : entry.getValue().entrySet())
//             {
//                  
//                  for(String item : elems)
//                  {
//                      elemsTemp.add(item+entRow.getKey()+",");
//                  
//                  }
//                 
//             }
//             if(!elemsTemp.isEmpty())
//             elems=elemsTemp;
//         }
//        
//         
//         
//         
//      for(String data : elems)
//      {
//          result=result.isEmpty()?  result+""+data:data+",\r\n"+result;
//      }
//        return result;
    }
    
    
    
    public String amigosMelate(int sorteoId, Date inicio, Date fin){
    
                String result="";
        
                List <Numeros> originList = core.getAllBySorteo(sorteoId,inicio,fin);
                
                estadistica(originList);//TODO test

        Integer [] tempNumber = new Integer[1];
        //Filter 2
        Integer pos1=2;
         Map<Integer,List<Numeros>> map =originList.stream().filter(x-> x.getposNumero1()==2)
                .collect(groupingBy( y -> y.getposNumero2()));
                
        //filter 2-> mayor
        Integer pos2=0;
        List<Numeros> listTemp = this.mayorOfHashMap(map);
        pos2= listTemp.get(0).getposNumero2();
        tempNumber[0]=pos2;
        map = originList.stream().filter(x-> x.getposNumero2() == tempNumber[0] ).collect(groupingBy( y -> y.getposNumero3() ));
        
        //filter 3-> mayor
        Integer pos3=0;
         listTemp = this.mayorOfHashMap(map);
        pos3= listTemp.get(0).getposNumero3();
          tempNumber[0]=pos3;
        map = originList.stream().filter(x-> x.getposNumero3() == tempNumber[0] ).collect(groupingBy( y -> y.getposNumero4()));
        
        //filter 4-> mayor
        Integer pos4=0;
         listTemp = this.mayorOfHashMap(map);
        pos4= listTemp.get(0).getposNumero4();
          tempNumber[0]=pos4;
        map = originList.stream().filter(x-> x.getposNumero4() == tempNumber[0] ).collect(groupingBy( y -> y.getposNumero5()));
        
        //filter 5-> mayor
        Integer pos5=0;
         listTemp = this.mayorOfHashMap(map);
        pos5= listTemp.get(0).getposNumero5();
        tempNumber[0]=pos5;
        map = originList.stream().filter(x-> x.getposNumero5() == tempNumber[0] ).collect(groupingBy( y -> y.getposNumero6()));
        
         //filter 6-> mayor
        Integer pos6=0;
        listTemp= this.mayorOfHashMap(map);
         pos6= listTemp.get(0).getposNumero6();
         
         result = this.completeNumber(""+pos1, 2)+","+this.completeNumber(""+pos2, 2)+","+this.completeNumber(""+pos3, 2)+","+this.completeNumber(""+pos4, 2)+","+this.completeNumber(""+pos5, 2)+","+this.completeNumber(""+pos6, 2);
         if(listTemp.size()>1){
            result= result+" \n " + this.concurrenciasListaTipoMelate(originList);
         }
    return result;
    }
    
      private String completeNumber(String numb,int total){
    
      while( numb.length()<total)
      {
      numb=0+numb;
      }
      return numb;
    }
    private String concurrenciasListaTipoMelate(List<Numeros> listResult){
        String result="";
        
        HashMap<Integer,HashMap<Integer,Integer>> controlNumbers = new HashMap<>();
         HashMap<Integer,HashMap<Integer,Integer>> controlNumbersTemp = new HashMap<>();
         String [] maybe = new String[54];
        controlNumbers.put(0, new HashMap<Integer,Integer>());
        controlNumbers.put(1, new HashMap<Integer,Integer>());
        controlNumbers.put(2, new HashMap<Integer,Integer>());
        controlNumbers.put(3, new HashMap<Integer,Integer>());
        controlNumbers.put(4, new HashMap<Integer,Integer>());
        controlNumbers.put(5, new HashMap<Integer,Integer>());
//        controlNumbers.put(6, new HashMap<Integer,Integer>()); //adicional
        controlNumbersTemp.put(0, new HashMap<Integer,Integer>());
        controlNumbersTemp.put(1, new HashMap<Integer,Integer>());
        controlNumbersTemp.put(2, new HashMap<Integer,Integer>());
        controlNumbersTemp.put(3, new HashMap<Integer,Integer>());
        controlNumbersTemp.put(4, new HashMap<Integer,Integer>());
        controlNumbersTemp.put(5, new HashMap<Integer,Integer>());
//        controlNumbersTemp.put(6, new HashMap<Integer,Integer>()); //adicional

        listResult.stream().forEach(item->{
        int i=0;
          String[] numbers = this.bytwo(item.getNumero());
        for(String num :numbers)
        {
            int numTemp = Integer.parseInt(""+num);
          
            if(controlNumbers.get(i).containsKey(numTemp))
            {
                Integer aux = controlNumbers.get(i).get(numTemp);
                controlNumbers.get(i).replace(numTemp, ++aux);
            }
            else{
             controlNumbers.get(i).put(numTemp,1);
            }
            i++;
            if(i==6) //adicional
                break;
        }
        });
    
         for(int j=0;j<=5;j++)
        {
          
            Entry<Integer,Integer> entTemp = null;
             for(Iterator<Entry<Integer, Integer>> it = controlNumbers.get(j).entrySet().stream()
                .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue())).iterator(); it.hasNext(); ) {
                 Entry<Integer, Integer> ent = it.next();
            
                if(entTemp!=null)
                {
                    if(entTemp.getValue()<=ent.getValue())
                    {
                        
                        entTemp=ent;
                        controlNumbersTemp.get(j).put(entTemp.getKey(), entTemp.getValue());
                    }
                   
                    
                }
                else
                {
                    entTemp=ent;
                    controlNumbersTemp.get(j).put(entTemp.getKey(), entTemp.getValue());
                }
            }  
            
        }
         
         ArrayList<String> elems=new ArrayList();
         elems.add("");
         for(Entry<Integer,HashMap<Integer,Integer>> entry :controlNumbersTemp.entrySet() )
         {
             ArrayList<String> elemsTemp=new ArrayList();
             for(Entry<Integer,Integer> entRow : entry.getValue().entrySet())
             {
                  
                  for(String item : elems)
                  {
                      elemsTemp.add(item+entRow.getKey()+",");
                  
                  }
                 
             }
             if(!elemsTemp.isEmpty())
             elems=elemsTemp;
         }
        
         
         
         
      for(String data : elems)
      {
          result=result.isEmpty()?  result+""+data:data+",\r\n"+result;
      }
        return result;
    }
    
    private String estadistica(List<Numeros> listResult){
        double [] array = new double[listResult.size()];
        Integer [] pip = new Integer[1];
        pip[0]=0;
        double desvTipica;
        double media;
        double mediana;
        double varianza;
        double correlacion;
        double numero_observaciones;
        String moda;
        double result;
       listResult.stream().forEach(x -> {
                
               array[pip[0]]= x.getposNumero1().doubleValue();
               pip[0]++;
               
               });
        Estadistica estadistica = new Estadistica(array);
        
        desvTipica = estadistica.getDesvTipica();
        media = estadistica.getMedia();
        mediana = estadistica.getMediana();
        moda = estadistica.getModa();
        varianza = estadistica.getVarianza();
        correlacion=estadistica.getCorrelacion();
        estadistica.regresion.lineal();
        numero_observaciones= correlacion*desvTipica;
       // double mediaY =estadistica.getMediaY() ;
        result = media - (numero_observaciones* array.length+1 );
        pip[0]=0;
        listResult.stream().forEach(x -> {
                
               array[pip[0]]= x.getposNumero2().doubleValue();
               pip[0]++;
               
               });
        estadistica = new Estadistica(array);
        
        desvTipica = estadistica.getDesvTipica();
        media = estadistica.getMedia();
        mediana = estadistica.getMediana();
        moda = estadistica.getModa();
        varianza = estadistica.getVarianza();
        correlacion=estadistica.getCorrelacion();
        pip[0]=0;
         listResult.stream().forEach(x -> {
                
               array[pip[0]]= x.getposNumero3().doubleValue();
               pip[0]++;
               
               });
        estadistica = new Estadistica(array);
        
        desvTipica = estadistica.getDesvTipica();
        media = estadistica.getMedia();
        mediana = estadistica.getMediana();
        moda = estadistica.getModa();
        varianza = estadistica.getVarianza();
         correlacion=estadistica.getCorrelacion();
        pip[0]=0;
        
         listResult.stream().forEach(x -> {
                
               array[pip[0]]= x.getposNumero4().doubleValue();
               pip[0]++;
               
               });
        estadistica = new Estadistica(array);
        
        desvTipica = estadistica.getDesvTipica();
        media = estadistica.getMedia();
        mediana = estadistica.getMediana();
        moda = estadistica.getModa();
        varianza = estadistica.getVarianza();
         correlacion=estadistica.getCorrelacion();
        pip[0]=0;
         listResult.stream().forEach(x -> {
                
               array[pip[0]]= x.getposNumero5().doubleValue();
               pip[0]++;
               
               });
        estadistica = new Estadistica(array);
        
        desvTipica = estadistica.getDesvTipica();
        media = estadistica.getMedia();
        mediana = estadistica.getMediana();
        moda = estadistica.getModa();
        varianza = estadistica.getVarianza();
         correlacion=estadistica.getCorrelacion();
        pip[0]=0;
        
         listResult.stream().forEach(x -> {
                
               array[pip[0]]= x.getposNumero6().doubleValue();
               pip[0]++;
               
               });
        estadistica = new Estadistica(array);
        
        desvTipica = estadistica.getDesvTipica();
        media = estadistica.getMedia();
        mediana = estadistica.getMediana();
        moda = estadistica.getModa();
        varianza = estadistica.getVarianza();
         correlacion=estadistica.getCorrelacion();
    return null;
    }
    
    
    private List<Numeros> mayorOfHashMap(Map<Integer,List<Numeros>> map){
    
        Integer keyTemp=0;
        
        for(Integer key : map.keySet())
        {
            if(keyTemp == 0)
            {
                keyTemp= key;
            }
            else{
                if(map.get(key).size() > map.get(keyTemp).size())
                {
                    keyTemp=key;
                }
            }
        }
            
        return map.get(keyTemp);
    }
    
    
    private String[] bytwo(String number)
    {
         String[] result = new String[6];
         int j=0;
         int i=0;
         String temp="";
         for(char elem: number.toCharArray())
         {
             if(i!=0&& i%2==0)
             {
                 result[j]=temp;
                 j++;
                 temp="";
             }
             temp = temp+elem;
             i++;
             if(i==number.length()&&result[5]==null)
             {
              result[j]=temp;
             }
         
         }
         
//        result[6]=temp; //adicional
        return result;
    }
    
}
