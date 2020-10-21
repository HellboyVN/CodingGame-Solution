import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Solution {
    static HashSet<String> whiteOccupy = new HashSet<>();
    static HashSet<String> blackOccupy = new HashSet<>(); 
    static ArrayList<String> whiteKingOccupy = new ArrayList<>();
    static ArrayList<String> blackKingOccupy = new ArrayList<>();
    static BoardChess blackKing;
    static BoardChess whiteKing;
    public static void main(String args[]) {
        // char [][] board = new char[8][8];
        ArrayList<BoardChess> board  = new ArrayList<>();
        boolean whiteIsCheck = false;
        boolean blackIsCheck = false;
        Scanner in = new Scanner(System.in);

        for (int i = 0; i < 8; i++) {
            String boardRow = in.nextLine();
            char[] temp = boardRow.toCharArray();
            for (int j = 0; j < 8; j++) {
               if (temp[j] != '.') {
                    BoardChess tempBoard = new BoardChess(String.valueOf(temp[j]), j, i, java.lang.Character.isUpperCase(temp[j]));
                    if(temp[j] == 'k')
                        blackKing =  new BoardChess("k", j, i, false);
                    else if(temp[j] == 'K')
                        whiteKing =  new BoardChess("K", j, i, true);
                    board.add(tempBoard);
               }
            }
            System.err.println(boardRow);
        }
        for (int i = 0; i < board.size(); i++) {
            setOccupy(board.get(i), board);
        }
        if(whiteOccupy.contains(String.valueOf(blackKing.x)+ blackKing.y)) blackIsCheck = true;
        else if(blackOccupy.contains(String.valueOf(whiteKing.x)+ whiteKing.y)) whiteIsCheck = true;
        
        // if king is checkmated => remove pos of king occupied equal to the other picios
       
        if(blackIsCheck) {
            for(int i =0; i<board.size();i++) {
                if(blackKingOccupy.contains(String.valueOf(board.get(i).x)+board.get(i).y) && !board.get(i).isWhite) {
                    blackKingOccupy.remove(String.valueOf(board.get(i).x)+board.get(i).y);
                }
            }
            for (int i = 0; i < whiteKingOccupy.size(); i++) {
            // System.err.println(("White King can: " + whiteKingOccupy.get(i)));
                whiteOccupy.add(whiteKingOccupy.get(i));
            }
             String finalRes = "W";
            for (int i = 0; i < blackKingOccupy.size(); i++) {
                
                if(!whiteOccupy.contains(blackKingOccupy.get(i))) {
                    finalRes = "N";
                    System.err.println("White does not contain "+ blackKingOccupy.get(i));
                    break;
                }
            }
            System.out.println(finalRes);
            // if(finalRes.equals("")) System.out.println("W");
            // else  System.out.println("N");
                
        }
        else if(whiteIsCheck) {
            for(int i =0; i<board.size();i++) {
                if(whiteKingOccupy.contains(String.valueOf(board.get(i).x)+board.get(i).y) && board.get(i).isWhite) {
                    whiteKingOccupy.remove(String.valueOf(board.get(i).x)+board.get(i).y);
                }
            }
            for (int i = 0; i < blackKingOccupy.size(); i++) {
            // System.err.println("Black King can: " + blackKingOccupy.get(i));
                blackOccupy.add(blackKingOccupy.get(i));
            }
            String finalRes = "B";
            for (int i = 0; i < whiteKingOccupy.size(); i++) {
                if(!blackOccupy.contains(whiteKingOccupy.get(i))) {
                    finalRes = "N";
                    System.err.println("Black does not contain "+ whiteKingOccupy.get(i));
                    break;
                }
            }
            System.out.println(finalRes);
            // if(finalRes.equals("")) System.out.println("B");
            // else System.out.println("N");
            
        } else System.out.println("N");

        
        
        // for (int i = 0; i < blackOccupy.size(); i++) {
        //     System.err.println(("Black take: " + blackOccupy.toArray()[i]));
        // }
        // for (int i = 0; i < whiteOccupy.size(); i++) {
        //     System.err.println(("White take: " + whiteOccupy.toArray()[i]));
        // }

     
        // Rook: xe; knight: ma; Bishop: tuong; Queen: hau; Pawn: tot

        // Write an answer using System.out.println()
        // To debug: System.err.println("Debug messages...");

        
    }
    static boolean checkValid(int x) {
        if(x >=0 && x <= 7) return true;
        return false;
    }
    static void setOccupy(BoardChess b,ArrayList<BoardChess> arr) {
        switch (b.name){
            case "R": 
            case "r": rookMove(b,arr); break;
            case "N": 
            case "n": knightMove(b, arr); break;
            case "B": 
            case "b": bishopMove(b, arr); break;
            case "Q": 
            case "q": queenMove(b, arr); break;
            case "P": pawnMove(b.x, b.y,true, arr); break;
            case "p": pawnMove(b.x, b.y, false,arr); break;
            case "K": whiteKingMove(b.x, b.y, arr); break;
            case "k": blackKingMove(b.x, b.y, arr); break;
        }
    }
    static void rookMove(BoardChess b, ArrayList<BoardChess> arr) {
        int x = b.x;
        int y = b.y;
        boolean addmore1=false, addmore2=false, addmore3=false, addmore4 = false;
        if (b.isWhite) {
            for(int i = 0; i < x; i++){
                int j = i+1;
                if(checkValid(x-j) && !addmore1) {
                    if( (checkEmptyNew(arr, x-j, y) && (x-j)!=blackKing.x && y!=blackKing.y)){
                        whiteOccupy.add(String.valueOf(x-j)+y);
                        addmore1 = true;
                    } else {
                        whiteOccupy.add(String.valueOf(x-j)+y);
                    }
                }
            }
            for(int i = 0; i < 7-x; i++){
                int j = i+1;
                if(checkValid(x+j) && !addmore2) {
                    if((checkEmptyNew(arr, x+j, y)&& (x+j)!=blackKing.x && y!=blackKing.y)){
                        whiteOccupy.add(String.valueOf(x+j)+y);
                        addmore2 = true;
                    } else {
                        whiteOccupy.add(String.valueOf(x+j)+y);
                    }
                }
            }
            for(int i = 0; i < y; i++){
                int j = i+1;
                if(checkValid(y-j) && !addmore3) {
                    if((checkEmptyNew(arr, x, y-j)&& (x)!=blackKing.x && (y-j)!=blackKing.y)){
                        whiteOccupy.add(String.valueOf(x)+(y-j));
                        addmore3 = true;
                    } else {
                        whiteOccupy.add(String.valueOf(x)+(y-j));
                    }
                }
            }
            for(int i = 0; i < 7-y; i++){
                int j = i+1;
                if(checkValid(y+j) && !addmore4) {
                    if((checkEmptyNew(arr, x, y+j)&& (x)!=blackKing.x && (y+j)!=blackKing.y)){
                        whiteOccupy.add(String.valueOf(x)+(y+j));
                        addmore4 = true;
                    } else {
                        whiteOccupy.add(String.valueOf(x)+(y+j));
                    }
                }
            }
            
           
        } else {
            for(int i = 0; i < x; i++){
                int j = i+1;
                if(checkValid(x-j) && !addmore1) {
                    if((checkEmptyNew(arr, x-j, y)&& (x-j)!=whiteKing.x && y!=whiteKing.y)){
                        blackOccupy.add(String.valueOf(x-j)+y);
                        addmore1 = true;
                    } else {
                        blackOccupy.add(String.valueOf(x-j)+y);
                    }
                }
            }
            for(int i = 0; i < 7-x; i++){
                int j = i+1;
                if(checkValid(x+j) && !addmore2) {
                    if((checkEmptyNew(arr, x+j, y)&& (x+j)!=whiteKing.x && y!=whiteKing.y)){
                        blackOccupy.add(String.valueOf(x+j)+y);
                        addmore2 = true;
                    } else {
                        blackOccupy.add(String.valueOf(x+j)+y);
                    }
                }
            }
            for(int i = 0; i < y; i++){
                int j = i+1;
                if(checkValid(y-j) && !addmore3) {
                    if((checkEmptyNew(arr, x, y-j)&& (x)!=whiteKing.x && (y-j)!=whiteKing.y)){
                        blackOccupy.add(String.valueOf(x)+(y-j));
                        addmore3 = true;
                    } else {
                        blackOccupy.add(String.valueOf(x)+(y-j));
                    }
                }
            }
            for(int i = 0; i < 7-y; i++){
                int j = i+1;
                if(checkValid(y+j) && !addmore4) {
                    if((checkEmptyNew(arr, x, y+j)&& (x)!=whiteKing.x && (y+j)!=whiteKing.y)){
                        blackOccupy.add(String.valueOf(x)+(y+j));
                        addmore4 = true;
                    } else {
                        blackOccupy.add(String.valueOf(x)+(y+j));
                    }
                }
            }

        }
        

    }

    static boolean checkEmpty(ArrayList<BoardChess> arr,int x, int y,boolean _isWhite) {
        for(int i =0; i < arr.size(); i++) {
            if(arr.get(i).x == x && arr.get(i).y == y && arr.get(i).isWhite == _isWhite) {
                return true;
            }
        }
        return false;
    }
    static boolean checkEmptyNew(ArrayList<BoardChess> arr,int x, int y) {
        for(int i =0; i < arr.size(); i++) {
            if(arr.get(i).x == x && arr.get(i).y == y) {
                return true;
            }
        }
        return false;
    }
   
    static void knightMove(BoardChess b, ArrayList<BoardChess> arr) {
        int x = b.x;
        int y = b.y;
        if(b.isWhite) {
            if(x-2 >= 0) {
                if(y-1 >= 0) {
                    // BoardChess tempMoveWhite = new BoardChess(b.name, x-2, y-1, true);
                    whiteOccupy.add(String.valueOf(x-2)+(y-1));
                }
                if(y+1 <= 7) {
                    // BoardChess tempMoveWhite = new BoardChess(b.name, x-2, y+1, true);
                    whiteOccupy.add(String.valueOf(x-2)+(y+1));
                }
            }
            if(x+2 <= 7) {
                if(y-1 >= 0) {
                    // BoardChess tempMoveWhite = new BoardChess(b.name, x+2, y-1, true);
                    whiteOccupy.add(String.valueOf(x+2)+(y-1));
                }
                if(y+1 <= 7) {
                    // BoardChess tempMoveWhite = new BoardChess(b.name, x+2, y+1, true);
                    whiteOccupy.add(String.valueOf(x+2)+(y+1));
                }
            }
            if(y+2 <= 7) {
                if(x-1 >= 0) {
                    // BoardChess tempMoveWhite = new BoardChess(b.name, x-1, y+2, true);
                    whiteOccupy.add(String.valueOf(x-1)+(y+2));
                }
                if(x+1 <= 7) {
                    // BoardChess tempMoveWhite = new BoardChess(b.name, x+1, y+2, true);
                     whiteOccupy.add(String.valueOf(x+1)+(y+2));
                }
            }
            if(y-2 >= 0) {
                 if(x-1 >= 0) {
                    // BoardChess tempMoveWhite = new BoardChess(b.name, x-1, y-2, true);
                    whiteOccupy.add(String.valueOf(x-1)+(y-2));
                }
                if(x+1 <= 7) {
                    // BoardChess tempMoveWhite = new BoardChess(b.name, x+1, y-2, true);
                     whiteOccupy.add(String.valueOf(x+1)+(y-2));
                }
            }
        } else {
            if(x-2 >= 0) {
                if(y-1 >= 0) {
                    // BoardChess tempMoveWhite = new BoardChess(b.name, x-2, y-1, false);
                     blackOccupy.add(String.valueOf(x-2)+(y-1));
                }
                if(y+1 <= 7) {
                    // BoardChess tempMoveWhite = new BoardChess(b.name, x-2, y+1, false);
                   blackOccupy.add(String.valueOf(x-2)+(y+1));
                }
            }
            if(x+2 <= 7) {
                if(y-1 >= 0) {
                    // BoardChess tempMoveWhite = new BoardChess(b.name, x+2, y-1, false);
                     blackOccupy.add(String.valueOf(x+2)+(y-1));
                }
                if(y+1 <= 7) {
                    // BoardChess tempMoveWhite = new BoardChess(b.name, x+2, y+1, false);
                    blackOccupy.add(String.valueOf(x+2)+(y+1));
                }
            }
            if(y+2 <= 7) {
                if(x-1 >= 0) {
                    // BoardChess tempMoveWhite = new BoardChess(b.name, x-1, y+2, false);
                     blackOccupy.add(String.valueOf(x-1)+(y+2));
                }
                if(x+1 <= 7) {
                    // BoardChess tempMoveWhite = new BoardChess(b.name, x+1, y+2, false);
                    blackOccupy.add(String.valueOf(x+1)+(y+2));
                }
            }
            if(y-2 >= 0) {
                 if(x-1 >= 0) {
                    // BoardChess tempMoveWhite = new BoardChess(b.name, x-1, y-2, false);
                    blackOccupy.add(String.valueOf(x-1)+(y-2));
                }
                if(x+1 <= 7) {
                    // BoardChess tempMoveWhite = new BoardChess(b.name, x+1, y-2, false);
                    blackOccupy.add(String.valueOf(x+1)+(y-2));
                }
            }
        }
    }

    static void bishopMove(BoardChess b, ArrayList<BoardChess> arr) {
        int x = b.x;
        int y = b.y;
        boolean addmore1=false, addmore2=false, addmore3=false, addmore4 = false;
        if(b.isWhite) {
            for (int i = 0; i < x; i++) {
                int j = i +1;
                if(checkValid(x-j)) {
                    if(checkValid(y-j)&& !addmore1) {
                        if(checkEmptyNew(arr, x-j, y-j) && (x-j)!=blackKing.x && (y-j)!=blackKing.y){
                            whiteOccupy.add(String.valueOf(x-j)+(y-j));
                            addmore1 =true;
                        } else {
                            whiteOccupy.add(String.valueOf(x-j)+(y-j));
                        }
                    } 
                    if(checkValid(y+j)&& !addmore2) {
                       if(checkEmptyNew(arr, x-j, y+j) && (x-j)!=blackKing.x && (y+j)!=blackKing.y){
                            whiteOccupy.add(String.valueOf(x-j)+(y+j));
                            addmore2 =true;
                        } else {
                            whiteOccupy.add(String.valueOf(x-j)+(y+j));
                        }
                    }
                }
            }
            for (int i = 0; i < 7-x; i++) {
                int j = i +1;
                if(checkValid(x+j)) {
                    if(checkValid(y-j) && !addmore3) {
                        if(checkEmptyNew(arr, x+j, y-j) && (x+j)!=blackKing.x && (y-j)!=blackKing.y){
                            whiteOccupy.add(String.valueOf(x+j)+(y-j));
                            addmore3 =true;
                        } else {
                            whiteOccupy.add(String.valueOf(x+j)+(y-j));
                        }
                    }
                    if(checkValid(y+j) && !addmore4) {
                        if(checkEmptyNew(arr, x+j, y+j) && (x+j)!=blackKing.x && (y+j)!=blackKing.y){
                            whiteOccupy.add(String.valueOf(x+j)+(y+j));
                            addmore4 =true;
                        } else {
                            whiteOccupy.add(String.valueOf(x+j)+(y+j));
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < x; i++) {
                int j = i +1;
                if(checkValid(x-j)) {
                    if(checkValid(y-j)&& !addmore1) {
                        if(checkEmptyNew(arr, x-j, y-j) && (x-j)!=whiteKing.x && (y-j)!=whiteKing.y){
                            blackOccupy.add(String.valueOf(x-j)+(y-j));
                            addmore1 =true;
                        } else {
                            blackOccupy.add(String.valueOf(x-j)+(y-j));
                        }
                    } 
                    if(checkValid(y+j)&& !addmore2) {
                        if(checkEmptyNew(arr, x-j, y+j) && (x-j)!=whiteKing.x && (y+j)!=whiteKing.y){
                            blackOccupy.add(String.valueOf(x-j)+(y+j));
                            addmore2 =true;
                        } else {
                            blackOccupy.add(String.valueOf(x-j)+(y+j));
                        }
                    }
                }
            }
            for (int i = 0; i < 7-x; i++) {
                int j = i +1;
                if(checkValid(x+j)) {
                    if(checkValid(y-j) && !addmore3) {
                        if(checkEmptyNew(arr, x+j, y-j) && (x+j)!=whiteKing.x && (y-j)!=whiteKing.y){
                            blackOccupy.add(String.valueOf(x+j)+(y-j));
                            addmore3 =true;
                        } else {
                            blackOccupy.add(String.valueOf(x+j)+(y-j));
                        }
                    }
                    if(checkValid(y+j) && !addmore4) {
                        if(checkEmptyNew(arr, x+j, y+j) && (x+j)!=whiteKing.x && (y+j)!=whiteKing.y){
                            blackOccupy.add(String.valueOf(x+j)+(y+j));
                            addmore4 =true;
                        } else {
                            blackOccupy.add(String.valueOf(x+j)+(y+j));
                        }
                    }
                }
            }
        }
    }

    static void queenMove(BoardChess b, ArrayList<BoardChess> arr) {
        rookMove(b,arr);
        bishopMove(b, arr);
    }
    static void pawnMove(int x, int y, boolean isWhitePawn,ArrayList<BoardChess> arr) {
        if(isWhitePawn) {
            if(checkValid(y-1)) {
                if(checkValid(x-1)) 
                    whiteOccupy.add(String.valueOf(x-1)+(y-1));
                
                if(checkValid(x+1)) 
                    whiteOccupy.add(String.valueOf(x+1)+(y-1));
            }
        } else {
             if(checkValid(y+1)) {
                if(checkValid(x-1)) 
                    blackOccupy.add(String.valueOf(x-1)+(y+1));
                
                if(checkValid(x+1)) 
                    blackOccupy.add(String.valueOf(x+1)+(y+1));
                
            }
        }
    }

    static void whiteKingMove(int x, int y, ArrayList<BoardChess>arr) {
        String tempMove = String.valueOf(x) + String.valueOf(y);
        whiteKingOccupy.add(tempMove);
        if (checkValid(x-1) ) {
            tempMove = String.valueOf(x-1) + String.valueOf(y);
            whiteKingOccupy.add(tempMove);
        }
        if (checkValid(x+1) ) {
            tempMove = String.valueOf(x+1) + String.valueOf(y);
            whiteKingOccupy.add(tempMove);
        }
        if (checkValid(y-1)) {
            tempMove = String.valueOf(x) + String.valueOf(y-1);
            whiteKingOccupy.add(tempMove);
        }
        if (checkValid(y+1) ) {
            tempMove = String.valueOf(x) + String.valueOf(y+1);
            whiteKingOccupy.add(tempMove);
        }
        if (checkValid(x-1)) {
            if(checkValid(y-1)) {
                tempMove = String.valueOf(x-1) + String.valueOf(y-1);
                whiteKingOccupy.add(tempMove);
            }
            if(checkValid(y+1)) {
                tempMove = String.valueOf(x-1) + String.valueOf(y+1);
                whiteKingOccupy.add(tempMove);
            }
        }
        if (checkValid(x+1)) {
            if(checkValid(y-1)) {
                tempMove = String.valueOf(x+1) + String.valueOf(y-1);
                whiteKingOccupy.add(tempMove);
            }
            if(checkValid(y+1)) {
                tempMove = String.valueOf(x+1) + String.valueOf(y+1);
                whiteKingOccupy.add(tempMove);
            }
        }
    }
    static void blackKingMove(int x, int y, ArrayList<BoardChess> arr) {
        String tempMove = String.valueOf(x) + String.valueOf(y);
        blackKingOccupy.add(tempMove);
        if (checkValid(x-1)) {
            tempMove = String.valueOf(x-1) + String.valueOf(y);
            blackKingOccupy.add(tempMove);
        }
        if (checkValid(x+1)) {
            tempMove = String.valueOf(x+1) + String.valueOf(y);
            blackKingOccupy.add(tempMove);
        }
        if (checkValid(y-1)) {
            tempMove = String.valueOf(x) + String.valueOf(y-1);
            blackKingOccupy.add(tempMove);
        }
        if (checkValid(y+1)) {
            tempMove = String.valueOf(x) + String.valueOf(y+1);
            blackKingOccupy.add(tempMove);
        }
        if (checkValid(x-1)) {
            if(checkValid(y-1)) {
                tempMove = String.valueOf(x-1) + String.valueOf(y-1);
                blackKingOccupy.add(tempMove);
            }
            if(checkValid(y+1)) {
                tempMove = String.valueOf(x-1) + String.valueOf(y+1);
                blackKingOccupy.add(tempMove);
            }
        }
        if (checkValid(x+1)) {
            if(checkValid(y-1)) {
                tempMove = String.valueOf(x+1) + String.valueOf(y-1);
                blackKingOccupy.add(tempMove);
            }
            if(checkValid(y+1)) {
                tempMove = String.valueOf(x+1) + String.valueOf(y+1);
                blackKingOccupy.add(tempMove);
            }
        }
    }
    
}
class BoardChess{
    String name;
    int x;
    int y;
    boolean isWhite;
    public BoardChess(String _name, int _x, int _y, boolean _isWhite) {
        name = _name;
        x = _x;
        y = _y;
        isWhite = _isWhite;
    }
}
