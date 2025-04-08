package NSCLC;

import HAL.Gui.OpenGL3DWindow;
import HAL.Util;

public class NSCLCExample  {

    public static void main(String[] args) {

        boolean OVERWRITE = true; // whether or not to overwrite previous simulations
        boolean headless = false; // whether or not to visualize in real time
        System.setProperty("java.awt.headless", headless ? "true" : "false");

        int TOTAL_SIMS = 4;
        int  save_max_gifs = -1; // only save gifs of first n sims (to save space)
        int modifier = 50;      // when to save data
        int totalTime = 3000;   // total # time steps of simulation


        // does not appear anywhere in simulation: (0 popsize)


        Grid_Broad.Simulate(totalTime, modifier, headless, TOTAL_SIMS, save_max_gifs, OVERWRITE);


        return;
    }

}



// package NSCLC;

// import HAL.Gui.OpenGL3DWindow;
// import HAL.Util;

// public class NSCLCclass  {

//     public static void main(String[] args) {

//         boolean OVERWRITE = true; // whether or not to overwrite previous simulations
//         boolean headless = false; // whether or not to visualize in real time
//         System.setProperty("java.awt.headless", headless ? "true" : "false");

//         int TOTAL_SIMS = 1;
//         int  save_max_gifs = TOTAL_SIMS; // only save gifs of first n sims (to save space)
//         int modifier = 50;      // when to save data
//         int totalTime = 5000;   // total # time steps of simulation


//         Grid_Broad.Simulate(totalTime, modifier, headless, TOTAL_SIMS, save_max_gifs, OVERWRITE);

//         return;
//     }

// }

