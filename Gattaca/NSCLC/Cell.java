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
    Genome genome;


    Cell Init(Genome self_genome){
        genome = self_genome;
        if (G.params.save_clonal_lineage) {
            genome.IncPop();
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
Cell Divide(){
    int nDivOptions = G.MapEmptyHood(G.neighborhood,Xsq(),Ysq());
    if(nDivOptions==0){ return null; }
    int nextAgentID = G.neighborhood[G.rn.Int(nDivOptions)];

    Genome g;

    if (genome instanceof Gattaca1) {
        g = ((Gattaca1) genome)._RunPossibleMutation(G.GetTick());
    } else if (genome instanceof Gattaca2) {
        g = ((Gattaca2) genome)._RunPossibleMutation(G.GetTick());
    } else {
        throw new RuntimeException("Unknown genome type in Cell.Divide()");
    }

    Cell c = G.NewAgentSQ(nextAgentID).Init(g);

    return c;
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
    if (G.params.save_clonal_lineage) { genome.DecPop(); }
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

