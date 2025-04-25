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
    public int n0 = 15;//sideLen*sideLen;
    public double IGNORE_CLONES_BELOW_FRACTION = 0.0; // (this is dependent on domain size)
    public boolean save_clonal_lineage = true;
    public int drawing_scaling_factor = 2;
    String[] AttributesList = new String[]{"Genome", "H", "S", "V"};//, "Color"};
}

public class Grid_Broad extends AgentGrid2D<Cell> {

    // tracking variables
    static final Grid_Parameters2D params = new Grid_Parameters2D();

    // neighborhoods
    int[]neighborhood=MooreHood(false);
    Rand rn;


    public Gattaca1 common_ancestor1;
    public Gattaca2 common_ancestor2;
    public Gattaca4 common_ancestor4;
    public Gattaca7 common_ancestor7;
    public Gattaca26 common_ancestor26;


    Grid_Broad(int seed){
        super(params.sideLen,params.sideLen, Cell.class,false,false);

        rn = new Rand(seed);

        this.common_ancestor1 = new Gattaca1(null, "", 0.0, 0.0, 0.5, rn);
        Gattaca1 clone1 = new Gattaca1(common_ancestor1, "", 0.0, 0.0, 0.5, rn);

        this.common_ancestor2 = new Gattaca2(null, "", 0.7, 0.8, 0.5, rn);
        Gattaca2 clone2 = new Gattaca2(common_ancestor2, "", 0.7, 0.8, 0.5, rn);

        this.common_ancestor4 = new Gattaca4(null, "", 0.568, 0.827, 0.706, rn);
        Gattaca4 clone4 = new Gattaca4(common_ancestor4, "", 0.568, 0.827, 0.706, rn);

        this.common_ancestor7 = new Gattaca7(null, "", 0.078, 0.941, 1.0, rn);
        Gattaca7 clone7 = new Gattaca7(common_ancestor7, "", 0.078, 0.941, 1.0, rn);

        this.common_ancestor26 = new Gattaca26(null, "", 0.333, 0.731, 0.627, rn);
        Gattaca26 clone26 = new Gattaca26(common_ancestor26, "", 0.333, 0.731, 0.627, rn);


        int cells = 0;
        while (cells < params.n0) {
            int cell_id = rn.Int(this.length);
            Cell c  = this.GetAgent(cell_id);
            if (c == null) {
                if (cells % 5 == 0) {
                    NewAgentSQ(cell_id).Init(
                            new Gattaca1(clone1, "", 0.0, 0.0, 0.5, rn),
                            null, null, null, null
                    );
                } else if (cells % 5 == 1) {
                    NewAgentSQ(cell_id).Init(
                            null,
                            new Gattaca2(clone2, "", 0.7, 0.8, 0.5, rn),
                            null, null, null
                    );
                } else if (cells % 5 == 2) {
                    NewAgentSQ(cell_id).Init(
                            null, null,
                            new Gattaca4(clone4, "", 0.568, 0.827, 0.706, rn),
                            null, null
                    );
                } else if (cells % 5 == 3) {
                    NewAgentSQ(cell_id).Init(
                            null, null, null,
                            new Gattaca7(clone7, "", 0.078, 0.941, 1.0, rn),
                            null
                    );
                } else if (cells % 5 == 4) {
                    NewAgentSQ(cell_id).Init(
                            null, null, null, null,
                            new Gattaca26(clone26, "", 0.333, 0.731, 0.627, rn)
                    );
                }
                     cells++;

            }
        }
    }

    // Step function for PD model ("steps" all cells through birth/death/mutation)
    void OriginalStep(){
        for (Cell c:this) {
            c.Step();
        }
        CleanShuffle(rn);
    }


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
                UIGrid Vis4 = new UIGrid(vis_dim, vis_dim, p.drawing_scaling_factor);
                UIGrid Vis7 = new UIGrid(vis_dim, vis_dim, p.drawing_scaling_factor);
                UIGrid Vis26 = new UIGrid(vis_dim, vis_dim, p.drawing_scaling_factor);

                UIWindow win = (seed < save_max) ? CreateWindow(headless, Vis1, Vis2, Vis4, Vis7, Vis26) : null;
                GifMaker smallGif = (seed < save_max) ? new GifMaker(foldername + "sim"+seed+".gif", 100, true) : null;



                for (int i = 0; i <= totalTime; i++) {

                    // step all models
                    model.OriginalStep();

                    // WRITE OUT VALUES
                    if (i % modifier == 0) {

                        System.out.println("Time: " + i + ", population: " + model.Pop());

//                        Draw(model, Vis, i);
                        Draw(model, Vis1, Vis2,Vis4,Vis7,Vis26, i);

                        if (seed < save_max) {
                            smallGif.AddFrame(Vis1);
                            Draw(model, Vis1, Vis2,Vis4,Vis7,Vis26, i);

                        }

                        if (p.save_clonal_lineage) {
//                            model.common_ancestor_genome.RecordClones(i);
                            model.common_ancestor1.RecordClones(i);
                            model.common_ancestor2.RecordClones(i);
                            model.common_ancestor4.RecordClones(i);
                            model.common_ancestor7.RecordClones(i);
                            model.common_ancestor26.RecordClones(i);


                        }
                    }
                }
                if (p.save_clonal_lineage) {
                    if (seed < save_max) {
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
                        model.common_ancestor4.OutputClonesToCSV(
                                foldername + "gattaca4_output" + seed + ".csv",
                                p.AttributesList,
                                (Gattaca4 g) -> {return GetAttributes(g);},
                                delete_thresh
                        );
                        model.common_ancestor7.OutputClonesToCSV(
                                foldername + "gattaca7_output" + seed + ".csv",
                                p.AttributesList,
                                (Gattaca7 g) -> {return GetAttributes(g);},
                                delete_thresh
                        );
                        model.common_ancestor26.OutputClonesToCSV(
                                foldername + "gattaca26_output" + seed + ".csv",
                                p.AttributesList,
                                (Gattaca26 g) -> {return GetAttributes(g);},
                                delete_thresh
                        );
                    }
                }


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


    public static void Draw(Grid_Broad model, UIGrid visGattaca1, UIGrid visGattaca2, UIGrid visGattaca4, UIGrid visGattaca7, UIGrid visGattaca26, int time) {
        for (int x = 0; x < model.params.sideLen; x++) {
            for (int y = 0; y < model.params.sideLen; y++) {
                Cell c = model.GetAgent(x, y);

                // Draw background as white if no cell
                if (c == null) {
                    visGattaca1.SetPix(x + 2, y + 2, Util.WHITE);
                    visGattaca2.SetPix(x + 2, y + 2, Util.WHITE);
                    visGattaca4.SetPix(x + 2, y + 2, Util.WHITE);
                    visGattaca7.SetPix(x + 2, y + 2, Util.WHITE);
                    visGattaca26.SetPix(x + 2, y + 2, Util.WHITE);
                } else if (c.genome1 != null) {
                    int color = HSBColor(c.genome1.h, c.genome1.v, c.genome1.s);
                    visGattaca1.SetPix(x + 2, y + 2, color);
                    visGattaca2.SetPix(x + 2, y + 2, Util.WHITE);
                    visGattaca4.SetPix(x + 2, y + 2, Util.WHITE);
                    visGattaca7.SetPix(x + 2, y + 2, Util.WHITE);
                    visGattaca26.SetPix(x + 2, y + 2, Util.WHITE);
                } else if (c.genome2 != null) {
                    int color = HSBColor(c.genome2.h, c.genome2.v, c.genome2.s);
                    visGattaca2.SetPix(x + 2, y + 2, color);
                    visGattaca1.SetPix(x + 2, y + 2, Util.WHITE);
                    visGattaca4.SetPix(x + 2, y + 2, Util.WHITE);
                    visGattaca7.SetPix(x + 2, y + 2, Util.WHITE);
                    visGattaca26.SetPix(x + 2, y + 2, Util.WHITE);
                }else if (c.genome4 != null) {
                    int color = HSBColor(c.genome4.h, c.genome4.v, c.genome4.s);
                    visGattaca4.SetPix(x + 2, y + 2, color);
                    visGattaca1.SetPix(x + 2, y + 2, Util.WHITE);
                    visGattaca2.SetPix(x + 2, y + 2, Util.WHITE);
                    visGattaca7.SetPix(x + 2, y + 2, Util.WHITE);
                    visGattaca26.SetPix(x + 2, y + 2, Util.WHITE);
                }else if (c.genome7 != null) {
                    int color = HSBColor(c.genome7.h, c.genome7.v, c.genome7.s);
                    visGattaca7.SetPix(x + 2, y + 2, color);
                    visGattaca1.SetPix(x + 2, y + 2, Util.WHITE);
                    visGattaca2.SetPix(x + 2, y + 2, Util.WHITE);
                    visGattaca4.SetPix(x + 2, y + 2, Util.WHITE);
                    visGattaca26.SetPix(x + 2, y + 2, Util.WHITE);
                }else if (c.genome26 != null) {
                    int color = HSBColor(c.genome26.h, c.genome26.v, c.genome26.s);
                    visGattaca26.SetPix(x + 2, y + 2, color);
                    visGattaca1.SetPix(x + 2, y + 2, Util.WHITE);
                    visGattaca2.SetPix(x + 2, y + 2, Util.WHITE);
                    visGattaca7.SetPix(x + 2, y + 2, Util.WHITE);
                    visGattaca4.SetPix(x + 2, y + 2, Util.WHITE);
                }
            }
        }
    }

    public static String GetAttributes(Gattaca1 root) {
       return root.PrivateGenome + "," + Double.toString(root.h) + "," + Double.toString(root.s)+ "," + Double.toString(root.v);

    }
    public static String GetAttributes(Gattaca2 root) {
        return root.PrivateGenome + "," + Double.toString(root.h) + "," + Double.toString(root.s)+ "," + Double.toString(root.v);
    }
    public static String GetAttributes(Gattaca4 root) {
        return root.PrivateGenome + "," + Double.toString(root.h) + "," + Double.toString(root.s)+ "," + Double.toString(root.v);
    }
    public static String GetAttributes(Gattaca7 root) {
        return root.PrivateGenome + "," + Double.toString(root.h) + "," + Double.toString(root.s)+ "," + Double.toString(root.v);
    }
    public static String GetAttributes(Gattaca26 root) {
        return root.PrivateGenome + "," + Double.toString(root.h) + "," + Double.toString(root.s)+ "," + Double.toString(root.v);
    }
    public static String Hex(int color) {
        return String.format("#%02x%02x%02x", GetRed256(color), GetGreen256(color), GetBlue256(color));
    }

public static UIWindow CreateWindow(boolean headless, UIGrid vis1, UIGrid vis2, UIGrid vis4, UIGrid vis7, UIGrid vis26) {
    UIWindow win = (headless) ? null : new UIWindow("Gattaca1 & Gattaca2", false, null, true);

        if (!headless) {
            win.AddCol(0, vis1); // Column 0: Gattaca1 visualization
            win.AddCol(1, vis2); // Column 1: Gattaca2 visualization
            win.AddCol(2, vis4);
            win.AddCol(3, vis7);
            win.AddCol(4, vis26);
            win.RunGui();
        }
        return win;
    }

}
