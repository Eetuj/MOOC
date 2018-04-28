
import java.util.Scanner;
import robotti.Ohjain;

public class Paaohjelma {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);

        //Ohjain.asetaLaatikkoSatunnaisesti();
        Ohjain.kaynnista();

        // toteuta ohjelma tänne
        
        // kertoo robotin x- ja y-koordinaatit
        int robottiX = Ohjain.robottiX();
        int robottiY = Ohjain.robottiY();

        // kertoo laatikon x- ja y-koordinaatit
        int laatikkoX = Ohjain.laatikkoX();
        int laatikkoY = Ohjain.laatikkoY();

        // kertoo rahtialueen x- ja y-koordinaatit
        int tavoiteX = Ohjain.tavoiteX();
        int tavoiteY = Ohjain.tavoiteY();
        
        String left = "vasen";
        String right = "oikea";
        String shut = "sammuta";
        String move = "liiku";
        String movex = "liikuMonta";
        String next = "viereen";
        String solve = "ratkaise";
        
        while (true){
            System.out.println("komento (sammuta, vasen, oikea, liiku, liikuMonta, viereen, ratkaise)");
            String command = lukija.nextLine();
            
            if (command.equals(left)){
                Ohjain.vasen();
            }else if (command.equals(right)){
                Ohjain.oikea();
            }else if (command.equals(shut)){
                break;
            }else if (command.equals(move)){
                Ohjain.liiku();
            }else if (command.equals(movex)){
                System.out.println("Montako askelta");
                int montakoAskelta = Integer.parseInt(lukija.nextLine());
                Ohjain.liikuMonta(montakoAskelta);
            }else if (command.equals(next)){
                Ohjain.vasen();
                Ohjain.liikuMonta(laatikkoY - robottiY);
                Ohjain.oikea();
                Ohjain.liikuMonta(laatikkoX - robottiX - 1);
            }else if (command.equals(solve)){
                Ohjain.vasen();
                Ohjain.liikuMonta(laatikkoY - robottiY);
                Ohjain.oikea();
                Ohjain.liikuMonta(laatikkoX - robottiX - 1);
                
                Ohjain.liikuMonta(tavoiteX - laatikkoX);
                Ohjain.vasen();
                Ohjain.liiku();
                Ohjain.oikea();
                Ohjain.liiku();
                Ohjain.oikea();
                Ohjain.liikuMonta(laatikkoY - tavoiteY);
            }     
        }
        Ohjain.sammuta();
    }
}
