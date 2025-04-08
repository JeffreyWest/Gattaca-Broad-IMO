package NSCLC;


import HAL.Tools.FileIO;
import cern.jet.random.Poisson;
import cern.jet.random.engine.DRand;
import cern.jet.random.engine.RandomEngine;
import HAL.Tools.PhylogenyTracker.Genome;
import HAL.Tools.MultinomialCalc;
import HAL.Rand;

import java.util.ArrayList;


public class Gattaca1 extends Genome<Gattaca1> {

	private static final int genomeComponents=31;
	private static final String[] geneNames=new String[]{"NRAS","RIT1","NTRK1","ALK","SOS1","CTNNB1","SETD2","PIK3CA","FAT1","ROS1","EGFR","MET","BRAF","FGFR1","NTRK2","RET","PTEN","FGFR2","ATM","KRAS","PTPRB","RB1","B2M","MAP2K1","NTRK3","TP53","NF1","STK11","KEAP1","SMARCA4","U2AF1"};
	private static double[] expectedMuts=new double[]{3.7387382805017096e-05,3.4912506818700106e-05,0.00022842417514253133,0.0021103584941391104,0.00027488127708095783,0.00019709264127708092,0.00026868584684150513,0.00043385193523375135,0.0006066339028506271,0.0006037030122690992,0.0010777124196123145,0.00038765124889395664,0.0007343108407753704,0.00015206218690992017,0.0012007719990877993,0.00023282534093500569,0.0002289421529760547,0.0003043641505131129,0.00041295400173318124,0.00018582962937286203,0.000500602529350057,0.0004959553882782212,1.975670367160775e-05,0.0002386591379703534,0.0024139356278221205,6.497747046750283e-05,0.0007179160556442416,0.00011961951124287342,4.3453400592930435e-05,0.0002552548794526796,4.104567972633979e-05};
	private static final String[] triNucs=new String[]{"aca","acc","acg","act","ata","atc","atg","att","cca","ccc","ccg","cct","cta","ctc","ctg","ctt","gca","gcc","gcg","gct","gta","gtc","gtg","gtt","tca","tcc","tcg","tct","tta","ttc","ttg","ttt"};
	private static final double[] triNucProbs=new double[]{0.013510696,0.01451909633,0.219015865,0.01355943624,0.0011686609,0.004446866,0.000928464945766,0.0015752678,0.006586501,0.0056694085000000005,0.19672569034395002,0.003105877974,0.0015278790000000002,0.009725645199999999,0.00582780932,0.0070900796,0.0160567997,0.036672849,0.20669618354000002,0.0202481182,0.00245820382,0.0066296428,0.00523782043,4.4878787e-05,0.0017792322,1.9695789e-20,0.18500655202,0.0038923785999999995,0.0022775054,0.00280675965,0.00242884048,0.0027809912192839395};
	private static final String[] chrom=new String[]{"1","1","1","2","2","3","3","3","4","6","7","7","7","8","9","10","10","10","11","12","12","13","15","15","15","17","17","19","19","19","21"};
	private static final String[] mutTypes=new String[]{"A[C>A]A","A[C>G]A","A[C>T]A","A[C>A]C","A[C>G]C","A[C>T]C","A[C>A]G","A[C>G]G","A[C>T]G","A[C>A]T","A[C>G]T","A[C>T]T","A[T>A]A","A[T>C]A","A[T>G]A","A[T>A]C","A[T>C]C","A[T>G]C","A[T>A]G","A[T>C]G","A[T>G]G","A[T>A]T","A[T>C]T","A[T>G]T","C[C>A]A","C[C>G]A","C[C>T]A","C[C>A]C","C[C>G]C","C[C>T]C","C[C>A]G","C[C>G]G","C[C>T]G","C[C>A]T","C[C>G]T","C[C>T]T","C[T>A]A","C[T>C]A","C[T>G]A","C[T>A]C","C[T>C]C","C[T>G]C","C[T>A]G","C[T>C]G","C[T>G]G","C[T>A]T","C[T>C]T","C[T>G]T","G[C>A]A","G[C>G]A","G[C>T]A","G[C>A]C","G[C>G]C","G[C>T]C","G[C>A]G","G[C>G]G","G[C>T]G","G[C>A]T","G[C>G]T","G[C>T]T","G[T>A]A","G[T>C]A","G[T>G]A","G[T>A]C","G[T>C]C","G[T>G]C","G[T>A]G","G[T>C]G","G[T>G]G","G[T>A]T","G[T>C]T","G[T>G]T","T[C>A]A","T[C>G]A","T[C>T]A","T[C>A]C","T[C>G]C","T[C>T]C","T[C>A]G","T[C>G]G","T[C>T]G","T[C>A]T","T[C>G]T","T[C>T]T","T[T>A]A","T[T>C]A","T[T>G]A","T[T>A]C","T[T>C]C","T[T>G]C","T[T>A]G","T[T>C]G","T[T>G]G","T[T>A]T","T[T>C]T","T[T>G]T"};
	private static final double[] mutTypeProb=new double[]{0.0,0.0,0.013510696,0.00036962633,0.001279962,0.012869508,0.0,0.000950125,0.21806574,0.00038453424,0.0,0.013174902,0.0,0.0011686609,0.0,0.0,0.003669439,0.000777427,0.00016695206,3.1415766e-08,0.00076148147,0.0,0.0015752678,0.0,0.0011861623,0.0001531107,0.005247228,0.0018347751,0.0007275869,0.0031070465,0.00053266255,2.779395e-08,0.196193,0.0,7.1584574e-05,0.0030342934,0.00028594056,0.00083105994,0.0004108785,0.0003650775,0.008081709,0.0012788587,0.0005819369,0.005138918,0.00010695442,0.0,0.0070900796,0.0,0.0022377297,0.0,0.01381907,0.0015586324,0.0018021306,0.033312086,0.00025502354,0.0,0.20644116,0.0031421282,0.000720228,0.016385762,0.0,0.002136762,0.00032144182,0.0,0.0054890704,0.0011405724,0.00033497743,0.004902843,0.0,0.0,4.4878787e-05,0.0,0.0017792322,2.2048599e-26,3.6470968e-22,0.0,6.36897e-40,1.9695789e-20,0.00091601687,0.00059374515,0.18349679,0.0009816716,2.2204376e-31,0.002910707,0.0,0.0022775054,0.0,0.00037452474,0.0021941448,0.00023809011,0.0,0.0019848791,0.00044396138,0.0,0.0027809935,0.0};

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
	public Gattaca1(Gattaca1 parent, String PrivateGenome, double h, double s, double v, Rand RN){
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
	public Gattaca1 _RunPossibleMutation(double time) {
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
			return(new Gattaca1(this, NewPrivateGenome, RN.Double(),RN.Double(),1,RN));
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