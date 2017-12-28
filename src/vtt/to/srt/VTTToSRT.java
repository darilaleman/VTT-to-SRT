/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vtt.to.srt;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Daril
 */
public class VTTToSRT {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        if(args.length==0){
            //Translate all subtitles in the current directory
            File currentDirectory = new File("");
            currentDirectory = new File(currentDirectory.getAbsolutePath());
            if(currentDirectory.isDirectory()){
                File[] array_vtt = getVTT(currentDirectory, false);
                Subtitle subt = new Subtitle();
                subt.translate(array_vtt);
                System.exit(0);
            }
        }
        
        if(args.length==1 && args[0].equals("-h")){
            usage();
            System.exit(0);       
        }
        
        if(args.length==1 && args[0].equals("-r")){
            //Translate all subtitles in the current directory recursively
            System.out.println("Looking for subtitles...");
            File currentDirectory = new File("");
            currentDirectory = new File(currentDirectory.getAbsolutePath());
            if(currentDirectory.isDirectory()){
                File[] array_vtt = getVTT(currentDirectory, true);
                Subtitle subt = new Subtitle();
                subt.translate(array_vtt);
                System.exit(0);
            }
        }
        
        if(args.length==2 && args[0].equals("-f")){
            File fileName = new File(args[1]);
            //Verifies its extension
            if(fileName.isFile() && (fileName.getAbsolutePath().endsWith(".vtt") || fileName.getAbsolutePath().endsWith(".VTT"))){
                //Translate the subtitle  
                Subtitle subt = new Subtitle();
                subt.translate(fileName);
                System.exit(0);
            }
            else{
                System.out.println("Not a valid file");
              
            }
            
        }
        
        if(args.length==2 && args[0].equals("-d")){
            System.out.println("Looking for subtitles...");
            File directoryPath = new File(args[1]);
            if(directoryPath.isDirectory()){
                //Translate all subtitles in the specified directory
                File[] array_vtt = getVTT(directoryPath, false);
                Subtitle subt = new Subtitle();
                subt.translate(array_vtt);
                System.exit(0);
            }
            
            else{
                System.out.println("Not a valid directory");
              
            }
            
        }
        
        if(args.length==3 && args[0].equals("-r") && args[1].equals("-d")){
            System.out.println("Looking for subtitles...");
            File directoryPath = new File(args[2]);
            if(directoryPath.isDirectory()){
                //Translate all subtitles recursively  
                File[] array_vtt = getVTT(directoryPath, true);
                Subtitle subt = new Subtitle();
                subt.translate(array_vtt);
                System.exit(0);
            }
            else{
                System.out.println("Not a valid directory");
              
            }
        }
        
        usage();
        System.exit(0);      
        
    }
    
    private static File [] getVTT(File directoryPath, boolean recursive) {
        String[] extensions = new String[]{"vtt", "VTT"};
        List<File> files = (List<File>) FileUtils.listFiles(directoryPath, extensions, recursive);
        File tmp [] = (File[]) files.toArray(new File[0]);
        return tmp;
    }
    
    private static void usage(){
        System.out.println("******************************************************************");
        System.out.println("*****        Simple VTT-to-SRT subtitles translator          *****");
        System.out.println("*****                        Version: 1.0                    *****");
        System.out.println("***** Author: Daril Aleman Morales    daril.aleman@gmail.com *****");
        System.out.println("******************************************************************");
        System.out.println();
        System.out.println("Usage:");
        System.out.println("java -jar VTT-to-SRT.jar [-h] [-r] [-f fileName] [-d directory]");
        System.out.println("-h          Shows this help");
        System.out.println("-r          If it's enabled, the program search all subtitles recursively");
        System.out.println("-f          Providing a filename, the program will translate only that file");
        System.out.println("-d          Providing a directory, the program will translate all subtitles in it");
        System.out.println();
        System.out.println("If no parametrers are provided, the program will translate all subtitles in the current directory");
        System.out.println("Note: Insert the paths inside double quotes to avoid erros");
    
    }

    
    
}
