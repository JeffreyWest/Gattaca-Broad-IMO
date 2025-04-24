package GattacaExample;

import HAL.GridsAndAgents.AgentSQ2Dunstackable;
class NSCLCCell extends AgentSQ2Dunstackable<ExampleNSCLC> {

    // each cell carries its own GattacaExample.Gattaca genome:
    Gattaca2 genome;

    NSCLCCell Init(Gattaca2 self_genome){
        genome = self_genome;
        if (G.params.save_clonal_lineage) {
            this.genome.IncPop();
        }
        return this;
    }

    NSCLCCell Divide(){
        int nDivOptions = G.MapEmptyHood(G.neighborhood,Xsq(),Ysq());
        if(nDivOptions==0){ return null; }
        int nextAgentID = G.neighborhood[G.rn.Int(nDivOptions)];

        Gattaca2 g = genome._RunPossibleMutation(G.GetTick());
        NSCLCCell c = G.NewAgentSQ(nextAgentID).Init(g);

        return c;
    }

    // constant death rate
    void Death() {
        if (G.params.save_clonal_lineage) { this.genome.DecPop(); }
        Dispose();
    }

    void Step(){
        if(G.rn.Double()<(G.params.DEATH_RATE + G.params.BIRTH_RATE )){
            // death OR birth event occurs w/in this cell:
            double birth_event_probability = G.params.BIRTH_RATE/(G.params.DEATH_RATE + G.params.BIRTH_RATE);

            if(G.rn.Double()<(birth_event_probability )) {
                Divide();
            } else {
                Death();
                return;
            }
        }
    }
}

