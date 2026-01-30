package Arboles;

import java.util.List;

import us.lsi.tiposrecursivos.BEmpty;
import us.lsi.tiposrecursivos.BLeaf;
import us.lsi.tiposrecursivos.BTree;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.TEmpty;
import us.lsi.tiposrecursivos.TLeaf;
import us.lsi.tiposrecursivos.TNary;

public class Ejercicio1 {
	//This method decides whether a Binary Tree is Equally weighted. 
	//I am going to use an auxiliar function and a record in which i am going to save an accumulator and a weight.

	
	public record Tupla(Boolean ac,Integer peso) {};
	//In this auxiliar record i save the weight and a boolean attribute carried from the children of a leaf, so when the recursive call ends i can decide
	public static Boolean esEquiponderado(BinaryTree<Integer> arbol) {
		Tupla res=aux(arbol);
		return res.ac();
	}
	
	
	private static Tupla aux(BinaryTree<Integer> arbol) {
		return switch(arbol) {
		case BEmpty() -> new Tupla(true,0);
		
		case BLeaf(var lb)->new Tupla(true,lb);
		
		case BTree(var lb,var hi,var hd)->{
			//recursive calls, for both sons
			Tupla Ti=aux(hi);
			Tupla Td=aux(hd);
			//I am declaring both attributes of the record associated to this "leaf" of the tree, declaring that it is Equally weighted as True 
			Boolean Eqp=true;
			Integer peso=lb+Ti.peso()+Td.peso();
			//In this part of the code i take into account the attributes carried from the recursive calls, so if one of the children(or both) is not
			//Equally weighted, this will be carried to the top leaf
			if(Ti.ac()==false||Td.ac()==false) {
				Eqp=false;
			}
			
			if(Ti.peso()!=Td.peso()) {
				Eqp=false;
			}
			yield new Tupla(Eqp,peso);
		}
		};
		
	}
	//now i'm going to do the implementation with a tree(supposing that the condition is that the summing of the labels of the children has to be zero
	//maybe logically the problem doesn't makes sense with a non Binary Tree, but if i use the condition explained i can show another implementation
	public static Boolean esEquiponderado2(Tree<Integer> arbol) {
		return aux2(arbol).ac();
	}
	private static Tupla aux2(Tree<Integer> arbol) {
		return switch(arbol) {
		case TEmpty()->new Tupla(true,0);
		case TLeaf(var lb)-> new Tupla(true,lb);
		case TNary(var lb,var ch)-> {
			Boolean Eqp=true;
			Integer peso=lb;
			
			Integer ac=0;
	/*The main difference is that i have to run the whole children list, and if just one of them doesn't have the attribute of "Tupla"
	 * in true, it will get carried
	 * Also i declare an accumulator so i can check at the end of the run if the condition is satisfied or not.
	 */
			for(Tree<Integer> hijo: ch) {
				//recursive call
				Tupla t=aux2(hijo);
				//carrying the accumulator
				ac+=t.peso();
				
				
				Eqp=Eqp&&t.ac();
				peso+=t.peso();
			}
			if(ac!=0) {
				Eqp=false;
			}
			yield new Tupla(Eqp,peso);
		}
		};
	}
	
}

