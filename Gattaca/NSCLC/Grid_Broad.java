package GattacaExample;

import HAL.GridsAndAgents.AgentGrid2D;
import HAL.Gui.*;
import HAL.Rand;
import HAL.Tools.PhylogenyTracker.Genome;
import HAL.Util;

import static HAL.Util.*;

import java.io.File;

class Grid_Parameters2D {
    public double BIRTH_RATE = 0.1;
    public double DEATH_RATE = 0.05;
    public int sideLen = 100;
    public int n0 = 10;//sideLen*sideLen;
    public double IGNORE_CLONES_BELOW_FRACTION = 0.0; // (this is dependent on domain size)
    public boolean save_clonal_lineage = true;
    public int drawing_scaling_factor = 4;
    String[] AttributesList = new String[]{"Genome", "H", "S", "V"};//, "Color"};
}

public class Grid_Broad extends AgentGrid2D<Cell> {

    // tracking variables
    static final Grid_Parameters2D params = new Grid_Parameters2D();

    // neighborhoods
    int[]neighborhood=MooreHood(false);
    Rand rn;

    // initial gattaca parent genome
//    Gattaca1 common_ancestor_genome;
//    Gattaca2 common_ancestor_genome;
//    Gattaca4 common_ancestor_genome;
//    Gattaca7 common_ancestor_genome;
//    Gattaca26 common_ancestor_genome;

//    Gattaca1 common_ancestor1;
//    Gattaca2 common_ancestor2;

    public Gattaca1 common_ancestor1;
    public Gattaca2 common_ancestor2;


    Grid_Broad(int seed){
        super(params.sideLen,params.sideLen, Cell.class,false,false);

        rn = new Rand(seed);

        this.common_ancestor1 = new Gattaca1(null, "", 0.0, 0.0, 0.5, rn);
        Gattaca1 clone1 = new Gattaca1(common_ancestor1, "", 0.0, 0.0, 0.5, rn);

        this.common_ancestor2 = new Gattaca2(null, "", 0.7, 0.8, 0.5, rn);
        Gattaca2 clone2 = new Gattaca2(common_ancestor2, "", 0.7, 0.8, 0.5, rn);

//        common_ancestor1 = commonAncestor1;
//        common_ancestor2 = commonAncestor2;

        int cells = 0;
        while (cells < params.n0) {
            int cell_id = rn.Int(this.length);
            Cell c  = this.GetAgent(cell_id);
            if (c == null) {
                 if( (cells % 2 == 0)) {
//                        ? new Gattaca1(commonAncestor1, "", 0.0, 0.0, 0.5, rn)
//                        : new Gattaca2(commonAncestor2, "", 0.7, 0.8, 0.5, rn);
                     NewAgentSQ(cell_id).Init(new Gattaca1(clone1, "", 0.0, 0.0, 0.5, rn), null);
                 }
                     else {
                     NewAgentSQ(cell_id).Init(null, new Gattaca2(clone2, "", 0.7, 0.8, 0.5, rn));
                 }
//                NewAgentSQ(cell_id).Init(g);
                     cells++;

            }
        }
//        int count1 = 0, count2 = 0;
//        for (Cell c : this) {
//            if (c.genome instanceof Gattaca1) count1++;
//            else if (c.genome instanceof Gattaca2) count2++;
//        }
//        System.out.println("Initial: Gattaca1 = " + count1 + ", Gattaca2 = " + count2);

    }

    // Step function for PD model ("steps" all cells through birth/death/mutation)
    void OriginalStep(){
        for (Cell c:this) {
            c.Step();
        }
        CleanShuffle(rn);
    }


    /*
        Square()

        - run a simulation simulations:
        - Single region with a homogeneous initial condition (e.g. ~50% density)
     */

    public static void Simulate(int totalTime, int modifier, boolean headless, int sims, int save_max, boolean OVERWRITE) {

        String masterfoldername = "./NSCLC/2d/";
        File dir = new File(masterfoldername);
        dir.mkdir();

        for (int seed = 0; seed < sims; seed++) {

            String foldername = masterfoldername;

            dir = new File(foldername);
            boolean success = dir.mkdir();

            if ((success) || OVERWRITE) {


                Grid_Broad model = new Grid_Broad(seed);
                Grid_Parameters2D p = model.params;
                int delete_thresh = 0;//(int) (p.sideLen * p.sideLen * p.IGNORE_CLONES_BELOW_FRACTION);

                // VISUALIZE
                int vis_dim = p.sideLen + 4;
                UIGrid Vis1 = new UIGrid(vis_dim, vis_dim, p.drawing_scaling_factor); // Gattaca1
                UIGrid Vis2 = new UIGrid(vis_dim, vis_dim, p.drawing_scaling_factor); // Gattaca2
                UIWindow win = (seed < save_max) ? CreateWindow(headless, Vis1, Vis2) : null;
                GifMaker smallGif = (seed < save_max) ? new GifMaker(foldername + "sim"+seed+".gif", 100, true) : null;



                for (int i = 0; i <= totalTime; i++) {

                    // step all models
                    model.OriginalStep();

                    // WRITE OUT VALUES
                    if (i % modifier == 0) {

                        System.out.println("Time: " + i + ", population: " + model.Pop());

//                        Draw(model, Vis, i);
                        Draw(model, Vis1, Vis2, i);

                        if (seed < save_max) {
                            smallGif.AddFrame(Vis1);
                            Draw(model, Vis1, Vis2, i);

                        }

                        if (p.save_clonal_lineage) {
//                            model.common_ancestor_genome.RecordClones(i);
                            model.common_ancestor1.RecordClones(i);
                            model.common_ancestor2.RecordClones(i);


                        }
                    }
                }
                if (p.save_clonal_lineage) {
                    if (seed < save_max) {
//                        Gattaca1 temp_clone = (Gattaca1) model.common_ancestor1;
//
//                        System.out.println(temp_clone.GetNumGenomes());
//                        temp_clone.OutputClonesToCSV(foldername + "gattaca1_output"+seed+".csv", p.AttributesList, (Gattaca1 g) -> {
//                            return GetAttributes(g);
//                        }, delete_thresh);
                        model.common_ancestor1.OutputClonesToCSV(
                                foldername + "gattaca1_output" + seed + ".csv",
                                p.AttributesList,
                                (Gattaca1 g) -> GetAttributes(g),
                                delete_thresh
                        );

                        model.common_ancestor2.OutputClonesToCSV(
                                foldername + "gattaca2_output" + seed + ".csv",
                                p.AttributesList,
                                (Gattaca2 g) -> {return GetAttributes(g);},
                                delete_thresh
                        );
                    }
                }

                // save clonal information in EvoFreq format:
//                if (p.save_clonal_lineage) {
////                    if (seed < save_max) {
////                        model.common_ancestor_genome.OutputClonesToCSV(foldername + "gattaca_output"+seed+".csv", p.AttributesList, (Gattaca1 g) -> {
////                            return GetAttributes(g);
////                        }, delete_thresh);
////                    }
//
//                    if (seed < save_max) {
//                        model.common_ancestor1.OutputClonesToCSV(foldername + "gattaca1_output" + seed + ".csv",
//                                p.AttributesList, (Gattaca1 g) -> GetAttributes(g), delete_thresh);
//
//                        model.common_ancestor2.OutputClonesToCSV(foldername + "gattaca2_output" + seed + ".csv",
//                                p.AttributesList, (Gattaca2 g) -> GetAttributes(g), delete_thresh);
//                    }
//                }

                // CLOSE GIFS, WINDOWS
                if (seed < save_max) {
                    smallGif.Close();
                    if (!headless) {
                        win.Close();
                    }
                }
            }
        }

        return;
    }


    //    public static void Draw(Grid_Broad model, UIGrid visCells, int time) {
//
//        // color half by drivers and half by passengers
//        for (int x = 0; x < model.params.sideLen; x++) {
//            for (int y = 0; y < model.params.sideLen; y++) {
//
//                Cell c = model.GetAgent(x,y);
//
//                if (c== null) {
//                    visCells.SetPix(x + 2,y + 2, Util.WHITE);
//                } else {
//                    visCells.SetPix(x + 2,y + 2,HSBColor(c.genome.h,c.genome.v,c.genome.s));
//                }
//            }
//        }
//
////        visCells.SetString(Integer.toString((int) time), true, BLACK, WHITE, 2);
//
//    }
//    public static void Draw(Grid_Broad model, UIGrid visCells, int time) {
//
//        // color half by drivers and half by passengers
//        for (int x = 0; x < model.params.sideLen; x++) {
//            for (int y = 0; y < model.params.sideLen; y++) {
//
//                Cell c = model.GetAgent(x,y);
//
//                if (c== null) {
//                    visCells.SetPix(x + 2,y + 2, Util.WHITE);
//                } else {
//                    visCells.SetPix(x + 2,y + 2,HSBColor(c.genome.h,c.genome.v,c.genome.s));
//                }
//            }
//        }
//
////        visCells.SetString(Integer.toString((int) time), true, BLACK, WHITE, 2);
//
//    }
//    public static void Draw(Grid_Broad model, UIGrid visCells, int time) {
//        for (int x = 0; x < model.params.sideLen; x++) {
//            for (int y = 0; y < model.params.sideLen; y++) {
//                Cell c = model.GetAgent(x, y);
//                if (c == null) {
//                    visCells.SetPix(x + 2, y + 2, Util.WHITE);
//                } else {
//                    double h = 0, s = 0, v = 0;
//
//                    if (c.genome instanceof Gattaca1 g1) {
//                        h = g1.h; s = g1.s; v = g1.v;
//                    } else if (c.genome instanceof Gattaca2 g2) {
//                        h = g2.h; s = g2.s; v = g2.v;
//                    }
//
//                    visCells.SetPix(x + 2, y + 2, HSBColor(h, v, s));
//                }
//            }
//        }
//    }

    public static void Draw(Grid_Broad model, UIGrid visGattaca1, UIGrid visGattaca2, int time) {
        for (int x = 0; x < model.params.sideLen; x++) {
            for (int y = 0; y < model.params.sideLen; y++) {
                Cell c = model.GetAgent(x, y);

                // Draw background as white if no cell
                if (c == null) {
                    visGattaca1.SetPix(x + 2, y + 2, Util.WHITE);
                    visGattaca2.SetPix(x + 2, y + 2, Util.WHITE);
                } else if (c.genome1 != null) {
//                else if (c.genome instanceof Gattaca1 g1) {
                    // Gattaca1: draw in visGattaca1, blank in visGattaca2
                    int color = HSBColor(c.genome1.h, c.genome1.v, c.genome1.s);
                    visGattaca1.SetPix(x + 2, y + 2, color);
                    visGattaca2.SetPix(x + 2, y + 2, Util.WHITE);
                } else if (c.genome2 != null) {
//                else if (c.genome instanceof Gattaca2 g2) {
                    // Gattaca2: draw in visGattaca2, blank in visGattaca1
                    int color = HSBColor(c.genome2.h, c.genome2.v, c.genome2.s);
                    visGattaca2.SetPix(x + 2, y + 2, color);
                    visGattaca1.SetPix(x + 2, y + 2, Util.WHITE);
                }
            }
        }
    }


    /*

        GetAttributes()
            - used for phylogeny tracker, to output clone-specific attributes

     */

    // Function to retrieve the attributes of your choice.
//    public static String GetAttributes(Gattaca1 root) {
////        System.out.println(Hex(HSBColor(root.h,root.v,root.s)));
//        return root.PrivateGenome + "," + Double.toString(root.h) + "," + Double.toString(root.s)+ "," + Double.toString(root.v) + "," + Hex(HSBColor(root.h,root.v,root.s));
////        return root.PrivateGenome + "," + Double.toString(root.h) + "," + Double.toString(root.s)+ "," + Double.toString(root.v);
//
//    }
    public static String GetAttributes(Gattaca1 root) {
//        System.out.println(Hex(HSBColor(root.h,root.v,root.s)));
//        return root.PrivateGenome + "," + Double.toString(root.h) + "," + Double.toString(root.s)+ "," + Double.toString(root.v) + "," + Hex(HSBColor(root.h,root.v,root.s));
        return root.PrivateGenome + "," + Double.toString(root.h) + "," + Double.toString(root.s)+ "," + Double.toString(root.v);

    }
    public static String GetAttributes(Gattaca2 root) {
        return root.PrivateGenome + "," + Double.toString(root.h) + "," + Double.toString(root.s)+ "," + Double.toString(root.v);
    }
    public static String Hex(int color) {
        return String.format("#%02x%02x%02x", GetRed256(color), GetGreen256(color), GetBlue256(color));
    }


//    public static UIWindow CreateWindow(boolean headless, UIGrid Vis) {
//        UIWindow win = (headless) ? null : new UIWindow("Window", false, null, true);
public static UIWindow CreateWindow(boolean headless, UIGrid vis1, UIGrid vis2) {
    UIWindow win = (headless) ? null : new UIWindow("Gattaca1 & Gattaca2", false, null, true);

//        if (!headless) {
//            win.AddCol(0, Vis);
//            win.RunGui();
//        }
//        return win;
        if (!headless) {
            win.AddCol(0, vis1); // Column 0: Gattaca1 visualization
            win.AddCol(1, vis2); // Column 1: Gattaca2 visualization
            win.RunGui();
        }
        return win;
    }

}




//public class Grid_Broad extends AgentGrid2D<Cell> {
//
//    // tracking variables
//    static final Grid_Parameters2D params = new Grid_Parameters2D();
//
//    // neighborhoods
//    int[]neighborhood=MooreHood(false);
//    Rand rn;
//
//    // initial gattaca parent genome
//    Gattaca1 common_ancestor_genome;
////    Gattaca2 common_ancestor_genome;
////    Gattaca4 common_ancestor_genome;
////    Gattaca7 common_ancestor_genome;
////    Gattaca26 common_ancestor_genome;
//
//    Grid_Broad(int seed){
//        super(params.sideLen,params.sideLen, Cell.class,false,false);
//
//        rn = new Rand(seed);
//
//        common_ancestor_genome = new Gattaca1(null, "", 0.0, 0.0, 0.5, rn);
//        Gattaca1 clone1=new Gattaca1(common_ancestor_genome, "",0.0, 0.0, 0.5, rn);
//
//        int cells = 0;
//        while (cells < params.n0) {
//            // random location
//            int cell_id = rn.Int(this.length);
//            Cell c  = this.GetAgent(cell_id);
//            if (c==null) {
//                NewAgentSQ(cell_id).Init(clone1);
//                cells++;
//            }
//
//        }
//    }
//
//    // Step function for PD model ("steps" all cells through birth/death/mutation)
//    void OriginalStep(){
//        for (Cell c:this) {
//            c.Step();
//        }
//        CleanShuffle(rn);
//    }
//
//
//    /*
//        Square()
//
//        - run a simulation simulations:
//        - Single region with a homogeneous initial condition (e.g. ~50% density)
//     */
//
//    public static void Simulate(int totalTime, int modifier, boolean headless, int sims, int save_max, boolean OVERWRITE) {
//
//        String masterfoldername = "./NSCLC/2d/";
//        File dir = new File(masterfoldername);
//        dir.mkdir();
//
//        for (int seed = 0; seed < sims; seed++) {
//
//            String foldername = masterfoldername;
//
//            dir = new File(foldername);
//            boolean success = dir.mkdir();
//
//            if ((success) || OVERWRITE) {
//
//
//                Grid_Broad model = new Grid_Broad(seed);
//                Grid_Parameters2D p = model.params;
//                int delete_thresh = (int) (p.sideLen * p.sideLen * p.IGNORE_CLONES_BELOW_FRACTION);
//
//                // VISUALIZE
//                int vis_dim = p.sideLen + 4;
//                UIGrid Vis = new UIGrid(vis_dim, vis_dim, p.drawing_scaling_factor);
//                UIWindow win = (seed < save_max) ? CreateWindow(headless, Vis) : null;
//                GifMaker smallGif = (seed < save_max) ? new GifMaker(foldername + "sim"+seed+".gif", 100, true) : null;
//
//                for (int i = 0; i <= totalTime; i++) {
//
//                    // step all models
//                    model.OriginalStep();
//
//                    // WRITE OUT VALUES
//                    if (i % modifier == 0) {
//
//                        System.out.println("Time: " + i + ", population: " + model.Pop());
//
//                        Draw(model, Vis, i);
//
//                        if (seed < save_max) {
//                            smallGif.AddFrame(Vis);
//                        }
//
//                        if (p.save_clonal_lineage) {
//                            model.common_ancestor_genome.RecordClones(i);
//                        }
//                    }
//                }
//
//                // save clonal information in EvoFreq format:
//                if (p.save_clonal_lineage) {
//                    if (seed < save_max) {
//                        model.common_ancestor_genome.OutputClonesToCSV(foldername + "gattaca_output"+seed+".csv", p.AttributesList, (Gattaca1 g) -> {
//                            return GetAttributes(g);
//                        }, delete_thresh);
//                    }
//                }
//
//                // CLOSE GIFS, WINDOWS
//                if (seed < save_max) {
//                    smallGif.Close();
//                    if (!headless) {
//                        win.Close();
//                    }
//                }
//            }
//        }
//
//        return;
//    }
//
//
////    public static void Draw(Grid_Broad model, UIGrid visCells, int time) {
////
////        // color half by drivers and half by passengers
////        for (int x = 0; x < model.params.sideLen; x++) {
////            for (int y = 0; y < model.params.sideLen; y++) {
////
////                Cell c = model.GetAgent(x,y);
////
////                if (c== null) {
////                    visCells.SetPix(x + 2,y + 2, Util.WHITE);
////                } else {
////                    visCells.SetPix(x + 2,y + 2,HSBColor(c.genome.h,c.genome.v,c.genome.s));
////                }
////            }
////        }
////
//////        visCells.SetString(Integer.toString((int) time), true, BLACK, WHITE, 2);
////
////    }
//public static void Draw(Grid_Broad model, UIGrid visCells, int time) {
//
//    // color half by drivers and half by passengers
//    for (int x = 0; x < model.params.sideLen; x++) {
//        for (int y = 0; y < model.params.sideLen; y++) {
//
//            Cell c = model.GetAgent(x,y);
//
//            if (c== null) {
//                visCells.SetPix(x + 2,y + 2, Util.WHITE);
//            } else {
//                visCells.SetPix(x + 2,y + 2,HSBColor(c.genome.h,c.genome.v,c.genome.s));
//            }
//        }
//    }
//
////        visCells.SetString(Integer.toString((int) time), true, BLACK, WHITE, 2);
//
//}
//
//    /*
//
//        GetAttributes()
//            - used for phylogeny tracker, to output clone-specific attributes
//
//     */
//
//    // Function to retrieve the attributes of your choice.
////    public static String GetAttributes(Gattaca1 root) {
//////        System.out.println(Hex(HSBColor(root.h,root.v,root.s)));
////        return root.PrivateGenome + "," + Double.toString(root.h) + "," + Double.toString(root.s)+ "," + Double.toString(root.v) + "," + Hex(HSBColor(root.h,root.v,root.s));
//////        return root.PrivateGenome + "," + Double.toString(root.h) + "," + Double.toString(root.s)+ "," + Double.toString(root.v);
////
////    }
//    public static String GetAttributes(Gattaca1 root) {
////        System.out.println(Hex(HSBColor(root.h,root.v,root.s)));
////        return root.PrivateGenome + "," + Double.toString(root.h) + "," + Double.toString(root.s)+ "," + Double.toString(root.v) + "," + Hex(HSBColor(root.h,root.v,root.s));
//        return root.PrivateGenome + "," + Double.toString(root.h) + "," + Double.toString(root.s)+ "," + Double.toString(root.v);
//
//    }
//
//    public static String Hex(int color) {
//        return String.format("#%02x%02x%02x", GetRed256(color), GetGreen256(color), GetBlue256(color));
//    }
//
//
//    public static UIWindow CreateWindow(boolean headless, UIGrid Vis) {
//        UIWindow win = (headless) ? null : new UIWindow("Window", false, null, true);
//
//        if (!headless) {
//            win.AddCol(0, Vis);
//            win.RunGui();
//        }
//        return win;
//    }
//
//}
//
