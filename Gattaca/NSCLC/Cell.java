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
//    Cell Init(Genome self_genome){
//        genome = self_genome;
//        if (G.params.save_clonal_lineage) {
//            genome.IncPop();
//        }
//        return this;
//    }
Cell Init(Gattaca1 g1, Gattaca2 g2){
    genome1 = g1;
    genome2 = g2;
    if (G.params.save_clonal_lineage) {
        if(g1!=null) {
            g1.IncPop();
        }
        if(g2!=null) {
            g2.IncPop();
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
        genome1._RunPossibleMutation(G.GetTick());
        Cell c = G.NewAgentSQ(nextAgentID).Init(genome1, null);
        return c;
    }
    if (genome2 != null) {
        genome2._RunPossibleMutation(G.GetTick());
        Cell c = G.NewAgentSQ(nextAgentID).Init(null, genome2);
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

