package NSCLC;


import HAL.Tools.FileIO;
import cern.jet.random.Poisson;
import cern.jet.random.engine.DRand;
import cern.jet.random.engine.RandomEngine;
import HAL.Tools.PhylogenyTracker.Genome;
import HAL.Tools.MultinomialCalc;
import HAL.Rand;

import java.util.ArrayList;


public class Gattaca2 extends Genome<Gattaca2> {

	private static final int genomeComponents=31;
	private static final String[] geneNames=new String[]{"NRAS","RIT1","NTRK1","ALK","SOS1","CTNNB1","SETD2","PIK3CA","FAT1","ROS1","EGFR","MET","BRAF","FGFR1","NTRK2","RET","PTEN","FGFR2","ATM","KRAS","PTPRB","RB1","B2M","MAP2K1","NTRK3","TP53","NF1","STK11","KEAP1","SMARCA4","U2AF1"};
	private static double[] expectedMuts=new double[]{3.7387382805017096e-05,3.4912506818700106e-05,0.00022842417514253133,0.0021103584941391104,0.00027488127708095783,0.00019709264127708092,0.00026868584684150513,0.00043385193523375135,0.0006066339028506271,0.0006037030122690992,0.0010777124196123145,0.00038765124889395664,0.0007343108407753704,0.00015206218690992017,0.0012007719990877993,0.00023282534093500569,0.0002289421529760547,0.0003043641505131129,0.00041295400173318124,0.00018582962937286203,0.000500602529350057,0.0004959553882782212,1.975670367160775e-05,0.0002386591379703534,0.0024139356278221205,6.497747046750283e-05,0.0007179160556442416,0.00011961951124287342,4.3453400592930435e-05,0.0002552548794526796,4.104567972633979e-05};
	private static final String[] triNucs=new String[]{"aca","acc","acg","act","ata","atc","atg","att","cca","ccc","ccg","cct","cta","ctc","ctg","ctt","gca","gcc","gcg","gct","gta","gtc","gtg","gtt","tca","tcc","tcg","tct","tta","ttc","ttg","ttt"};
	private static final double[] triNucProbs=new double[]{0.009106702500000001,0.0040585066,0.0008819236873550979,0.0041430786,0.0004546322,0.00024142471,0.0012568301,0.0021228595899999998,0.016492966499999998,0.0048261324,0.0025939299,0.0109542484,0.0006937726300042058,0.0011755906,0.0013707116199999998,0.0016482112,0.00961554282,0.0056793211,0.0084035299,0.0070698600999999995,0.00060081726,0.0002960399,0.0012624557999999998,0.0016732666999999999,0.388398428,0.133424129,0.093704013,0.284434822,0.0019175859,0.0008698903600000002,0.00019600804,0.00043276888264067283};
	private static final String[] chrom=new String[]{"1","1","1","2","2","3","3","3","4","6","7","7","7","8","9","10","10","10","11","12","12","13","15","15","15","17","17","19","19","19","21"};
	private static final String[] mutTypes=new String[]{"A[C>A]A","A[C>G]A","A[C>T]A","A[C>A]C","A[C>G]C","A[C>T]C","A[C>A]G","A[C>G]G","A[C>T]G","A[C>A]T","A[C>G]T","A[C>T]T","A[T>A]A","A[T>C]A","A[T>G]A","A[T>A]C","A[T>C]C","A[T>G]C","A[T>A]G","A[T>C]G","A[T>G]G","A[T>A]T","A[T>C]T","A[T>G]T","C[C>A]A","C[C>G]A","C[C>T]A","C[C>A]C","C[C>G]C","C[C>T]C","C[C>A]G","C[C>G]G","C[C>T]G","C[C>A]T","C[C>G]T","C[C>T]T","C[T>A]A","C[T>C]A","C[T>G]A","C[T>A]C","C[T>C]C","C[T>G]C","C[T>A]G","C[T>C]G","C[T>G]G","C[T>A]T","C[T>C]T","C[T>G]T","G[C>A]A","G[C>G]A","G[C>T]A","G[C>A]C","G[C>G]C","G[C>T]C","G[C>A]G","G[C>G]G","G[C>T]G","G[C>A]T","G[C>G]T","G[C>T]T","G[T>A]A","G[T>C]A","G[T>G]A","G[T>A]C","G[T>C]C","G[T>G]C","G[T>A]G","G[T>C]G","G[T>G]G","G[T>A]T","G[T>C]T","G[T>G]T","T[C>A]A","T[C>G]A","T[C>T]A","T[C>A]C","T[C>G]C","T[C>T]C","T[C>A]G","T[C>G]G","T[C>T]G","T[C>A]T","T[C>G]T","T[C>T]T","T[T>A]A","T[T>C]A","T[T>G]A","T[T>A]C","T[T>C]C","T[T>G]C","T[T>A]G","T[T>C]G","T[T>G]G","T[T>A]T","T[T>C]T","T[T>G]T"};
	private static final double[] mutTypeProb=new double[]{0.0012780181,0.0038861374,0.003942547,0.0015798756,0.002478631,1.7826105e-29,0.0,0.00088192365,3.7355098e-11,0.0010709735,0.0015549378,0.0015171673,0.0,0.0,0.0004546322,0.00024142471,1.6257042e-30,0.0,0.0012568301,4e-45,0.0,0.00086524367,0.0010385483,0.00021906762,0.001990988,0.004260958,0.0102410205,0.0018431951,0.0029829373,0.0,7.2900497e-31,0.0007073726,0.0018865573,0.0013891207,0.0057955487,0.003769579,0.0005568363,4.2057502e-15,0.00013693633,0.0011755906,0.0,0.0,0.00020728436,0.00018189926,0.000981528,9.000008e-21,0.0,0.0016482112,0.00039798682,2.4546549e-30,0.009217556,0.0008569911,0.0015070438,0.0033152862,0.0014148403,0.0014711276,0.005517562,0.0006714486,0.0033172097,0.0030812018,0.0,0.00060081726,0.0,0.0,8.217932e-05,0.00021386058,0.0009580162,2.9107584e-29,0.0003044396,0.0,0.0014190986,0.0002541681,0.023698308,0.1370439,0.22765622,0.017194403,0.047028486,0.06920124,0.009064702,0.021271301,0.06336801,0.015792992,0.15704094,0.11160089,0.0,0.001260591,0.0006569949,5.700177e-20,0.00064945547,0.00022043489,0.0,0.00019600804,0.0,0.00043282038,1.5991039e-12,0.0};

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
	public Gattaca2(Gattaca2 parent, String PrivateGenome, double h, double s, double v, Rand RN){
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
	public Gattaca2 _RunPossibleMutation(double time) {
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
			return(new Gattaca2(this, NewPrivateGenome, RN.Double(),RN.Double(),1,RN));
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