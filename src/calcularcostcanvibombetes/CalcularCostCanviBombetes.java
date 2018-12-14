package calcularcostcanvibombetes;

import java.util.Random;

/**
 *
 * @author mblan
 */
public class CalcularCostCanviBombetes {

    static Random rand = new Random();

    static Halogena[] bombetes_halogenes = new Halogena[14];
    static LED[] bombetes_LED = new LED[14];
    
    static int nombre_de_bombetes_comptades = 0;

    static double energia_total_utilitzada_halogenes;
    static double energia_total_utilitzada_LED;
    static double estalvi_energia;
    static final double PREU_ENERGIA = 0.084655;
    static final double PREU_LED = 6.95;
    static double estalvi_energia_per_any;
    static double inversio;
    static double temps_recuperar_inversio;

    public static void main(String[] args) {

        Generar();
        Calcular_Temps_a_Recuperar_Inversio();
        
    }

    public static void Generar() {

        int temps_aleatori_que_les_bombetes_estan_enceses = 0;

        for (int i = 0; i < bombetes_halogenes.length; i++) {
            
            Halogena halogena = new calcularcostcanvibombetes.Halogena();
            LED led = new calcularcostcanvibombetes.LED();

            if (nombre_de_bombetes_comptades >= 3 || nombre_de_bombetes_comptades == 0) {
                
                temps_aleatori_que_les_bombetes_estan_enceses = rand.nextInt(300) + 5;
                
            } 
            
            halogena.temps_encesa = temps_aleatori_que_les_bombetes_estan_enceses;
            led.temps_encesa = temps_aleatori_que_les_bombetes_estan_enceses;

            bombetes_LED[i] = led;
            bombetes_halogenes[i] = halogena;

            System.out.println("La bombeta halogena guardada a l'array estarà encesa durant " + halogena.temps_encesa + " minuts");
            //System.out.println("La bombeta LED guardada a l'array estarà encesa durant " + led.temps_encesa + " minuts");
            
            nombre_de_bombetes_comptades++;

        }

    }

    public static void Calcular_Temps_a_Recuperar_Inversio() {

        //Passar temps que les bombetes estan enceses a hores
        for (int i = 0; i < bombetes_halogenes.length || i < bombetes_LED.length; i++){
            
            bombetes_halogenes[i].temps_encesa /= 60;
            bombetes_LED[i].temps_encesa /= 60;
            
        }
        
        //Saber energia que consumeix cada bombeta
        for (int i = 0; i < bombetes_halogenes.length || i < bombetes_LED.length; i++) {

            bombetes_halogenes[i].energia_que_consumeix = bombetes_halogenes[i].temps_encesa * bombetes_halogenes[i].vats;
            bombetes_LED[i].energia_que_consumeix = bombetes_LED[i].temps_encesa * bombetes_LED[i].vats;

        }

        //Sumar energia de totes les bombetes del mateix tipus
        for (int i = 0; i < bombetes_halogenes.length || i < bombetes_LED.length; i++) {

            energia_total_utilitzada_halogenes += bombetes_halogenes[i].energia_que_consumeix;
            energia_total_utilitzada_LED += bombetes_LED[i].energia_que_consumeix;

        }

        //Passar Wh/dia -> Wh/mes
        energia_total_utilitzada_halogenes *= 30;
        energia_total_utilitzada_LED *= 30;

        //Passar Wh/mes -> kWh/mes
        energia_total_utilitzada_halogenes /= 1000;
        energia_total_utilitzada_LED /= 1000;

        //Passar kWh/mes -> kWh/any
        energia_total_utilitzada_halogenes *= 12;
        energia_total_utilitzada_LED *= 12;

        estalvi_energia = energia_total_utilitzada_halogenes - energia_total_utilitzada_LED;

        estalvi_energia_per_any = estalvi_energia * PREU_ENERGIA;

        //Saber inversió
        inversio = bombetes_LED.length * PREU_LED;
        
        temps_recuperar_inversio = inversio / estalvi_energia_per_any;
        
        double en_mesos = temps_recuperar_inversio * 12;
        double en_dies = en_mesos * 30;
        
        System.out.println("\nTardarem " + temps_recuperar_inversio + " anys a recuperar l'inversió");
        System.out.println("\nTardarem " + en_mesos + " mesos a recuperar l'inversió");
        System.out.println("\nTardarem " + en_dies + " dies a recuperar l'inversió de " + inversio + " euros.");

    }

}

class Halogena {

    final int vats = 28;
    double temps_encesa;
    double energia_que_consumeix;

}

class LED {

    int vats = 8;
    double temps_encesa;
    double energia_que_consumeix;

}
