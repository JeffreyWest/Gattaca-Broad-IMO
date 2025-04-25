package GattacaExample;

import HAL.GridsAndAgents.AgentSQ2Dunstackable;
import HAL.Tools.PhylogenyTracker.Genome;

//class Cell extends AgentSQ2Dunstackable<Grid_Broad> {
//
//    // each cell carries its own GattacaExample.Gattaca genome:
////    Gattaca1 genome;
////    Genome genome;
//    Gattaca1 genome;
//
//
//
//
//    Cell Init(Gattaca1 self_genome){
//        genome = self_genome;
//        if (G.params.save_clonal_lineage) {
//            this.genome.IncPop();
//        }
//        return this;
//    }
//
//    Cell Divide(){
//        int nDivOptions = G.MapEmptyHood(G.neighborhood,Xsq(),Ysq());
//        if(nDivOptions==0){ return null; }
//        int nextAgentID = G.neighborhood[G.rn.Int(nDivOptions)];
//
//        Gattaca1 g = genome._RunPossibleMutation(G.GetTick());
////        Gattaca1 g = null;
//        Cell c = G.NewAgentSQ(nextAgentID).Init(g);
//
//        return c;
//    }
//
//    // constant death rate
//    void Death() {
//        if (G.params.save_clonal_lineage) { this.genome.DecPop(); }
//        Dispose();
//    }
//
//    void Step(){
//        if(G.rn.Double()<(G.params.DEATH_RATE + G.params.BIRTH_RATE )){
//            // death OR birth event occurs w/in this cell:
//            double birth_event_probability = G.params.BIRTH_RATE/(G.params.DEATH_RATE + G.params.BIRTH_RATE);
//
//            if(G.rn.Double()<(birth_event_probability )) {
//                Divide();
//            } else {
//                Death();
//                return;
//            }
//        }
//    }
//}
//

class Cell extends AgentSQ2Dunstackable<Grid_Broad> {

    // each cell carries its own GattacaExample.Gattaca genome:
//    Gattaca1 genome;
//    Genome genome;
//    Genome genome;

    Gattaca1 genome1 = null;
    Gattaca2 genome2 = null;
    Gattaca4 genome4 = null;
    Gattaca7 genome7 = null;
    Gattaca26 genome26 = null;
//    Cell Init(Genome self_genome){
//        genome = self_genome;
//        if (G.params.save_clonal_lineage) {
//            genome.IncPop();
//        }
//        return this;
//    }
Cell Init(Gattaca1 g1, Gattaca2 g2, Gattaca4 g4, Gattaca7 g7, Gattaca26 g26){
    genome1 = g1;
    genome2 = g2;
    genome4 = g4;
    genome7 = g7;
    genome26 = g26;
    if (G.params.save_clonal_lineage) {
        if(g1!=null) {
            g1.IncPop();
        }
        if(g2!=null) {
            g2.IncPop();
        }
        if(g4!=null) {
            g4.IncPop();
        }
        if(g7!=null) {
            g7.IncPop();
        }
        if(g26!=null) {
            g26.IncPop();
        }
    }
    return this;
}

//    Cell Init(Gattaca1 self_genome){
//        genome = self_genome;
//        if (G.params.save_clonal_lineage) {
//            this.genome.IncPop();
//        }
//        return this;
//    }
Cell Divide() {
    int nDivOptions = G.MapEmptyHood(G.neighborhood, Xsq(), Ysq());
    if (nDivOptions == 0) {
        return null;
    }
    int nextAgentID = G.neighborhood[G.rn.Int(nDivOptions)];
    if (genome1 != null) {
        Gattaca1 g = genome1._RunPossibleMutation(G.GetTick());
        Cell c = G.NewAgentSQ(nextAgentID).Init(g, null, null, null, null);
        return c;
    }
    if (genome2 != null) {
        Gattaca2 g = genome2._RunPossibleMutation(G.GetTick());
        Cell c = G.NewAgentSQ(nextAgentID).Init(null, g, null, null, null);
        return c;
    }
    if (genome4 != null) {
        Gattaca4 g = genome4._RunPossibleMutation(G.GetTick());
        Cell c = G.NewAgentSQ(nextAgentID).Init(null, null, g, null, null);
        return c;
    }
    if (genome7 != null) {
        Gattaca7 g =  genome7._RunPossibleMutation(G.GetTick());
        Cell c = G.NewAgentSQ(nextAgentID).Init(null, null, null, g, null);
        return c;
    }
    if (genome26 != null) {
        Gattaca26 g = genome26._RunPossibleMutation(G.GetTick());
        Cell c = G.NewAgentSQ(nextAgentID).Init(null, null, null, null, g);
        return c;
    }
    return null;
}

//    Cell Divide(){
//        int nDivOptions = G.MapEmptyHood(G.neighborhood,Xsq(),Ysq());
//        if(nDivOptions==0){ return null; }
//        int nextAgentID = G.neighborhood[G.rn.Int(nDivOptions)];
//
//        Gattaca1 g = genome._RunPossibleMutation(G.GetTick());
////        Gattaca1 g = null;
//        Cell c = G.NewAgentSQ(nextAgentID).Init(g);
//
//        return c;
//    }
void Death() {
    if (G.params.save_clonal_lineage) {
        if(genome1!=null) {
            genome1.DecPop();
        }
        if(genome2!=null) {
            genome2.DecPop();
        }
        if(genome4!=null) {
            genome4.DecPop();
        }
        if(genome7!=null) {
            genome7.DecPop();
        }
        if(genome26!=null) {
            genome26.DecPop();
        }


//        genome.DecPop();
    }
    Dispose();
}

    void Step(){
        if(G.rn.Double()<(G.params.DEATH_RATE + G.params.BIRTH_RATE )){
            double birth_event_probability = G.params.BIRTH_RATE/(G.params.DEATH_RATE + G.params.BIRTH_RATE);

            if(G.rn.Double()<(birth_event_probability )) {
                Divide();
            } else {
                Death();
                return;
            }
        }
    }
    // constant death rate
//    void Death() {
//        if (G.params.save_clonal_lineage) { this.genome.DecPop(); }
//        Dispose();
//    }
//
//    void Step(){
//        if(G.rn.Double()<(G.params.DEATH_RATE + G.params.BIRTH_RATE )){
//            // death OR birth event occurs w/in this cell:
//            double birth_event_probability = G.params.BIRTH_RATE/(G.params.DEATH_RATE + G.params.BIRTH_RATE);
//
//            if(G.rn.Double()<(birth_event_probability )) {
//                Divide();
//            } else {
//                Death();
//                return;
//            }
//        }
//    }
}

