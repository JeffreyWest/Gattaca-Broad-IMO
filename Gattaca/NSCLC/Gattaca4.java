/**
 * Created by Gattaca (Ryan O. Schenck).
 */

package GattacaExample;

import HAL.Tools.FileIO;
import cern.jet.random.Poisson;
import cern.jet.random.engine.DRand;
import cern.jet.random.engine.RandomEngine;
import HAL.Tools.PhylogenyTracker.Genome;
import HAL.Tools.MultinomialCalc;
import HAL.Rand;

import java.util.ArrayList;

//SBS4
//Associated with tobacco smoking. Its profile is similar to the mutational spectrum observed in experimental systems
//exposed to tobacco carcinogens such as benzo[a]pyrene. SBS4 is, therefore, likely due to direct DNA damage by tobacco smoke mutagens.


public class Gattaca4 extends Genome<Gattaca4> {

	private static final int genomeComponents=31;
	private static final String[] geneNames=new String[]{"NRAS","RIT1","NTRK1","ALK","SOS1","CTNNB1","SETD2","PIK3CA","FAT1","ROS1","EGFR","MET","BRAF","FGFR1","NTRK2","RET","PTEN","FGFR2","ATM","KRAS","PTPRB","RB1","B2M","MAP2K1","NTRK3","TP53","NF1","STK11","KEAP1","SMARCA4","U2AF1"};
	private static double[] expectedMuts=new double[]{3.7387382805017096e-05,3.4912506818700106e-05,0.00022842417514253133,0.0021103584941391104,0.00027488127708095783,0.00019709264127708092,0.00026868584684150513,0.00043385193523375135,0.0006066339028506271,0.0006037030122690992,0.0010777124196123145,0.00038765124889395664,0.0007343108407753704,0.00015206218690992017,0.0012007719990877993,0.00023282534093500569,0.0002289421529760547,0.0003043641505131129,0.00041295400173318124,0.00018582962937286203,0.000500602529350057,0.0004959553882782212,1.975670367160775e-05,0.0002386591379703534,0.0024139356278221205,6.497747046750283e-05,0.0007179160556442416,0.00011961951124287342,4.3453400592930435e-05,0.0002552548794526796,4.104567972633979e-05};
	private static final String[] triNucs=new String[]{"aca","acc","acg","act","ata","atc","atg","att","cca","ccc","ccg","cct","cta","ctc","ctg","ctt","gca","gcc","gcg","gct","gta","gtc","gtg","gtt","tca","tcc","tcg","tct","tta","ttc","ttg","ttt"};
	private static final double[] triNucProbs=new double[]{0.046167999,0.0404013897,0.0205062924,0.021240766101504528,0.0054854056,0.012138112999999999,0.00949142530000006,0.0076381546,0.062620252,0.061077839999999994,0.039245668,0.07063222599999999,0.0087089656,0.0227843847,0.0315368007,0.043473019,0.090763395,0.104344613,0.047447020000000006,0.053222108000000004,0.0097569342,0.012590651000000001,0.0140140214,0.0117080835,0.02188638,0.037905991,0.0141599121,0.0396188974,0.00405398235,0.0135823717,0.0100390529,0.01175788474849544};
	private static final String[] chrom=new String[]{"1","1","1","2","2","3","3","3","4","6","7","7","7","8","9","10","10","10","11","12","12","13","15","15","15","17","17","19","19","19","21"};
	private static final String[] mutTypes=new String[]{"A[C>A]A","A[C>G]A","A[C>T]A","A[C>A]C","A[C>G]C","A[C>T]C","A[C>A]G","A[C>G]G","A[C>T]G","A[C>A]T","A[C>G]T","A[C>T]T","A[T>A]A","A[T>C]A","A[T>G]A","A[T>A]C","A[T>C]C","A[T>G]C","A[T>A]G","A[T>C]G","A[T>G]G","A[T>A]T","A[T>C]T","A[T>G]T","C[C>A]A","C[C>G]A","C[C>T]A","C[C>A]C","C[C>G]C","C[C>T]C","C[C>A]G","C[C>G]G","C[C>T]G","C[C>A]T","C[C>G]T","C[C>T]T","C[T>A]A","C[T>C]A","C[T>G]A","C[T>A]C","C[T>C]C","C[T>G]C","C[T>A]G","C[T>C]G","C[T>G]G","C[T>A]T","C[T>C]T","C[T>G]T","G[C>A]A","G[C>G]A","G[C>T]A","G[C>A]C","G[C>G]C","G[C>T]C","G[C>A]G","G[C>G]G","G[C>T]G","G[C>A]T","G[C>G]T","G[C>T]T","G[T>A]A","G[T>C]A","G[T>G]A","G[T>A]C","G[T>C]C","G[T>G]C","G[T>A]G","G[T>C]G","G[T>G]G","G[T>A]T","G[T>C]T","G[T>G]T","T[C>A]A","T[C>G]A","T[C>T]A","T[C>A]C","T[C>G]C","T[C>T]C","T[C>A]G","T[C>G]G","T[C>T]G","T[C>A]T","T[C>G]T","T[C>T]T","T[T>A]A","T[T>C]A","T[T>G]A","T[T>A]C","T[T>C]C","T[T>G]C","T[T>A]G","T[T>C]G","T[T>G]G","T[T>A]T","T[T>C]T","T[T>G]T"};
	private static final double[] mutTypeProb=new double[]{0.025933107,0.007860168,0.012374724,0.029158859,0.006925627,0.0043169037,0.012419015,0.0025455304,0.005541747,0.0140049895,0.0072357766,1.5045252e-12,0.0045926385,1.8860798e-32,0.0008927671,0.008278594,0.0026356813,0.0012238377,0.006571526,5.966756e-17,0.0029198993,0.0043166946,3.8818898e-20,0.00332146,0.03720809,0.005918282,0.01949388,0.034199122,0.007833768,0.01904495,0.022289194,0.004591338,0.012365136,0.034734216,0.011710617,0.024187393,0.006218309,0.002035561,0.0004550956,0.012437465,0.009201717,0.0011452027,0.020329656,0.0035138251,0.0076933196,0.019303175,0.010322349,0.013847495,0.040230356,0.010686991,0.039846048,0.050849836,0.030942867,0.02255191,0.019809421,0.005095568,0.022542031,0.025991095,0.014184885,0.013046128,0.0047922283,0.0045373407,0.0004273652,0.008088914,0.002254594,0.002247143,0.0076101474,0.0,0.006403874,0.0075049065,0.0,0.004203177,0.02188638,1.9223522e-21,0.0,0.030599276,0.007306715,7.4294163e-31,0.011763784,0.0023961281,0.0,0.03232264,0.006128845,0.0011674124,0.0037037218,0.0,0.00035026055,0.0069406196,0.004210752,0.0024310001,0.0069531472,0.0005166889,0.0025692168,0.0033243834,1.0341989e-25,0.008433473};

	private static final String BaseIndexFile= System.getProperty("user.dir") + "/src/triNucsPos.csv";
	private static final long[][][] basePositions=ParseTriNucFile();
	private Poisson[] PoissonDists;
	private RandomEngine RNEngine;
	private Rand RN;
	private MultinomialCalc picker;

	String PrivateGenome;
	double h;
	double s;
	double v;
	int[] triNucMuts;
	static double[] theMutOptions=new double[3];
	static double[] mutOptionsProbs=new double[3];
	int[] mutTypeHit;
	int mutIdx;
	String mutChosen;
	String mutChrom;
	String mutGene;
	long mutPos;
	int mutations;
	public Gattaca4(Gattaca4 parent, String PrivateGenome, double h, double s, double v, Rand RN){
		super(parent, false);
		this.PrivateGenome = PrivateGenome;
		this.RNEngine=new DRand();
		this.RN=RN;
		this.picker=new MultinomialCalc(RN);
		this.BuildPoissons();
		this.h=h;
		this.s=s;
		this.v=v;
	}
	public Gattaca4 _RunPossibleMutation(double time) {
		StringBuilder MutsObtained = new StringBuilder();
		triNucMuts=new int[triNucProbs.length]; // Must reset to zeroes
		int mutsHappened=0;
		for (int j = 0; j < expectedMuts.length; j++) {
			mutations = PoissonDists[j].nextInt(); // How many mutations for this gene.
			if (mutations>0) {
				mutsHappened+=mutations;
				triNucMuts = new int[triNucProbs.length];
				GattacaMultinomial(triNucProbs, mutations, triNucMuts); // Step One acquire mutation trinucleotide based on 32 mutation types

				for (int tri = 0; tri < triNucMuts.length; tri++) {
					for (int hit = 0; hit < triNucMuts[tri]; hit++) { // Step Two acquire mutation type based on probability of context transition/transversion to get the 96 mutation types.
							theMutOptions[0] = mutTypeProb[(tri * 3) + 0];
							theMutOptions[1] = mutTypeProb[(tri * 3) + 1];
							theMutOptions[2] = mutTypeProb[(tri * 3) + 2];
							NormalizeMutTypeOptions();
							mutTypeHit = new int[3];
							GattacaMultinomial(mutOptionsProbs, 1, mutTypeHit);
							mutIdx = NonZeroIdx(mutTypeHit);
							mutChosen = mutTypes[(tri*3)+mutIdx]; // Which mutation occurs in bases.
							mutPos = basePositions[j][tri][RN.rn.Int(basePositions[j][tri].length)];
							mutChrom = chrom[j];
							mutGene = geneNames[j];
							String mutOut = time + "." + mutGene + "." + mutChosen + "." + mutChrom + "." + mutPos + ";";
							MutsObtained.append(mutOut);
						}
				}
			}

		}

		if(mutsHappened>0){
			String NewPrivateGenome=MutsObtained.toString();
			return(new Gattaca4(this, NewPrivateGenome, RN.Double(),RN.Double(),1,RN));
		} else {
			return(this);
		}
	}
private int NonZeroIdx(int[] arr){
		int idx=199;
		int sum=0;
		for (int i = 0; i < arr.length; i++) {
			if(arr[i]!=0){
				idx=i;
			}
			sum+=arr[i];
		}
		if(sum>1 | idx==199){
			System.out.println(arr[0] + "	" + arr[1] + "	" + arr[2]);
			System.out.println(idx);
			throw new IllegalStateException("Multiple mutation types chosen: "+sum);
		}
		return(idx);
	}

	private void BuildPoissons(){
		PoissonDists = new Poisson[expectedMuts.length];
		for (int i = 0; i < expectedMuts.length; i++) {
			Poisson poisson_dist = new Poisson(expectedMuts[i], RNEngine);
			PoissonDists[i] = poisson_dist;
		}
	}

	private static void NormalizeMutTypeOptions(){
		double sum = sumArray(theMutOptions);
		for (int i = 0; i < theMutOptions.length; i++) {
			mutOptionsProbs[i]=theMutOptions[i]/sum;
		}
		double check=1;
		for (int i = 0; i < mutOptionsProbs.length; i++) {
			check-=mutOptionsProbs[i];
		}

		mutOptionsProbs=FixSumToOne(check, mutOptionsProbs);
	}

	private static double sumArray(double[] vals){
		double val=0;
		for (int i = 0; i < vals.length; i++) {
			val+=vals[i];
		}
		return(val);
	}

	private static double[] FixSumToOne(double check, double[] arr){
		if(check!=0){
			for (int i = 0; i < arr.length; i++) {
				if(check<0){
					arr[i]-=check/arr.length;
				} else if(check>0) {
					arr[i]+=check/arr.length;
				}
			}
		}
		return(arr);
	}

	public void GattacaMultinomial(double[] probabilities, int n, int[] ret) {
		double probRemaining = 1;
		for (int i = 0; i < probabilities.length; i++) {
			double prob=probabilities[i];
			if(n==0){
				ret[i]=0;
				return;
			}
			if(probRemaining-prob==0){
				ret[i]=n;
				return;
			}
			int popSelected=picker.Binomial(n,prob/ probRemaining);
			probRemaining -=prob;
			n-=popSelected;
			ret[i]=popSelected;
		}
	}

	// Parses Base Mutation Information
	private static long[][][] ParseTriNucFile(){
		FileIO reader = new FileIO(BaseIndexFile, "r");
		ArrayList<long[]> data = new ArrayList<> (reader.ReadLongs(","));
		long[][][] BaseIndexes = new long[data.size()/32][32][];
		for (int i = 0; i < data.size(); i+=32) {
			BaseIndexes[i/32][0] = data.get(i+0);
			BaseIndexes[i/32][1] = data.get(i+1);
			BaseIndexes[i/32][2] = data.get(i+2);
			BaseIndexes[i/32][3] = data.get(i+3);
			BaseIndexes[i/32][4] = data.get(i+4);
			BaseIndexes[i/32][5] = data.get(i+5);
			BaseIndexes[i/32][6] = data.get(i+6);
			BaseIndexes[i/32][7] = data.get(i+7);
			BaseIndexes[i/32][8] = data.get(i+8);
			BaseIndexes[i/32][9] = data.get(i+9);
			BaseIndexes[i/32][10] = data.get(i+10);
			BaseIndexes[i/32][11] = data.get(i+11);
			BaseIndexes[i/32][12] = data.get(i+12);
			BaseIndexes[i/32][13] = data.get(i+13);
			BaseIndexes[i/32][14] = data.get(i+14);
			BaseIndexes[i/32][15] = data.get(i+15);
			BaseIndexes[i/32][16] = data.get(i+16);
			BaseIndexes[i/32][17] = data.get(i+17);
			BaseIndexes[i/32][18] = data.get(i+18);
			BaseIndexes[i/32][19] = data.get(i+19);
			BaseIndexes[i/32][20] = data.get(i+20);
			BaseIndexes[i/32][21] = data.get(i+21);
			BaseIndexes[i/32][22] = data.get(i+22);
			BaseIndexes[i/32][23] = data.get(i+23);
			BaseIndexes[i/32][24] = data.get(i+24);
			BaseIndexes[i/32][25] = data.get(i+25);
			BaseIndexes[i/32][26] = data.get(i+26);
			BaseIndexes[i/32][27] = data.get(i+27);
			BaseIndexes[i/32][28] = data.get(i+28);
			BaseIndexes[i/32][29] = data.get(i+29);
			BaseIndexes[i/32][30] = data.get(i+30);
			BaseIndexes[i/32][31] = data.get(i+31);
		}
		return BaseIndexes;
	}
}