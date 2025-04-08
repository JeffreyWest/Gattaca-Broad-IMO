package NSCLC;

import HAL.Tools.FileIO;
import cern.jet.random.Poisson;
import cern.jet.random.engine.DRand;
import cern.jet.random.engine.RandomEngine;
import HAL.Tools.PhylogenyTracker.Genome;
import HAL.Tools.MultinomialCalc;
import HAL.Rand;

import java.util.ArrayList;


public class Gattaca26 extends Genome<Gattaca26> {

	private static final int genomeComponents=31;
	private static final String[] geneNames=new String[]{"NRAS","RIT1","NTRK1","ALK","SOS1","CTNNB1","SETD2","PIK3CA","FAT1","ROS1","EGFR","MET","BRAF","FGFR1","NTRK2","RET","PTEN","FGFR2","ATM","KRAS","PTPRB","RB1","B2M","MAP2K1","NTRK3","TP53","NF1","STK11","KEAP1","SMARCA4","U2AF1"};
	private static double[] expectedMuts=new double[]{3.7387382805017096e-05,3.4912506818700106e-05,0.00022842417514253133,0.0021103584941391104,0.00027488127708095783,0.00019709264127708092,0.00026868584684150513,0.00043385193523375135,0.0006066339028506271,0.0006037030122690992,0.0010777124196123145,0.00038765124889395664,0.0007343108407753704,0.00015206218690992017,0.0012007719990877993,0.00023282534093500569,0.0002289421529760547,0.0003043641505131129,0.00041295400173318124,0.00018582962937286203,0.000500602529350057,0.0004959553882782212,1.975670367160775e-05,0.0002386591379703534,0.0024139356278221205,6.497747046750283e-05,0.0007179160556442416,0.00011961951124287342,4.3453400592930435e-05,0.0002552548794526796,4.104567972633979e-05};
	private static final String[] triNucs=new String[]{"aca","acc","acg","act","ata","atc","atg","att","cca","ccc","ccg","cct","cta","ctc","ctg","ctt","gca","gcc","gcg","gct","gta","gtc","gtg","gtt","tca","tcc","tcg","tct","tta","ttc","ttg","ttt"};
	private static final double[] triNucProbs=new double[]{0.017615054,0.0184511714,0.014144008699999999,0.020367173000000002,0.040896273350000005,0.029371335800000002,0.0615553456,0.04322921877,0.0157250739,0.024074216699999998,0.058455368,0.0230795212,0.0327131112,0.038498189399999994,0.09571350299999999,0.04090256015,0.0121143563,0.022395513699999998,0.033426185,0.03053587822,0.0278307456,0.0345109229,0.05623439499999999,0.049950134400000006,0.002550678300000002,0.01045193835,0.0065097674,0.005512887000161079,0.0255889412,0.033784167500000004,0.038358309,0.03545405595983888};
	private static final String[] chrom=new String[]{"1","1","1","2","2","3","3","3","4","6","7","7","7","8","9","10","10","10","11","12","12","13","15","15","15","17","17","19","19","19","21"};
	private static final String[] mutTypes=new String[]{"A[C>A]A","A[C>G]A","A[C>T]A","A[C>A]C","A[C>G]C","A[C>T]C","A[C>A]G","A[C>G]G","A[C>T]G","A[C>A]T","A[C>G]T","A[C>T]T","A[T>A]A","A[T>C]A","A[T>G]A","A[T>A]C","A[T>C]C","A[T>G]C","A[T>A]G","A[T>C]G","A[T>G]G","A[T>A]T","A[T>C]T","A[T>G]T","C[C>A]A","C[C>G]A","C[C>T]A","C[C>A]C","C[C>G]C","C[C>T]C","C[C>A]G","C[C>G]G","C[C>T]G","C[C>A]T","C[C>G]T","C[C>T]T","C[T>A]A","C[T>C]A","C[T>G]A","C[T>A]C","C[T>C]C","C[T>G]C","C[T>A]G","C[T>C]G","C[T>G]G","C[T>A]T","C[T>C]T","C[T>G]T","G[C>A]A","G[C>G]A","G[C>T]A","G[C>A]C","G[C>G]C","G[C>T]C","G[C>A]G","G[C>G]G","G[C>T]G","G[C>A]T","G[C>G]T","G[C>T]T","G[T>A]A","G[T>C]A","G[T>G]A","G[T>A]C","G[T>C]C","G[T>G]C","G[T>A]G","G[T>C]G","G[T>G]G","G[T>A]T","G[T>C]T","G[T>G]T","T[C>A]A","T[C>G]A","T[C>T]A","T[C>A]C","T[C>G]C","T[C>T]C","T[C>A]G","T[C>G]G","T[C>T]G","T[C>A]T","T[C>G]T","T[C>T]T","T[T>A]A","T[T>C]A","T[T>G]A","T[T>A]C","T[T>C]C","T[T>G]C","T[T>A]G","T[T>C]G","T[T>G]G","T[T>A]T","T[T>C]T","T[T>G]T"};
	private static final double[] mutTypeProb=new double[]{0.0,0.00476101,0.012854044,0.0,0.0042253244,0.014225847,0.003387117,0.00419416,0.0065627317,8.622283e-28,0.001076176,0.019290997,6.979135e-05,0.03845644,0.002370042,0.0014912038,0.023459341,0.004420791,0.004807104,0.049426693,0.0073215486,0.00017831947,0.038050227,0.0050006723,0.0010019579,0.004309831,0.010413285,0.0007550037,0.010165802,0.013153411,0.012689476,0.020214556,0.025551336,0.0009643906,0.0066140806,0.01550105,0.001839011,0.027954824,0.0029192762,0.0019171024,0.023243384,0.013337703,0.013082315,0.06575542,0.016875768,0.00026599455,0.03717299,0.0034635756,0.0014007265,0.0036481353,0.0070654945,8.553654e-28,0.0045787897,0.017816724,0.012571341,0.008575834,0.01227901,0.00022031862,0.0041661956,0.026149364,0.0023622366,0.022531018,0.002937491,0.0019983572,0.028112305,0.0044002607,0.00516033,0.04258719,0.008486875,0.0008660962,0.046580475,0.0025035632,2.1297523e-18,0.0025506783,0.0,0.00047468935,0.009977249,3.89444e-26,4.3034643e-26,0.0065097674,0.0,0.0,1.6107882e-13,0.005512887,0.0030246987,0.016683204,0.0058810385,0.0014939655,0.023222188,0.009068014,0.002144086,0.025486887,0.010727336,0.0014342165,0.030308627,0.003711147};

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
	public Gattaca26(Gattaca26 parent, String PrivateGenome, double h, double s, double v, Rand RN){
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
	public Gattaca26 _RunPossibleMutation(double time) {
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
			return(new Gattaca26(this, NewPrivateGenome, RN.Double(),RN.Double(),1,RN));
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