package presentation;

import java.awt.*;

class ChartPoint extends Point{
    int pointX;
    int pointY;

    boolean is_centroid = false;

    int reg_and = 0;

    Color color;


    ChartPoint(int x, int y){
        this.x=x;
        this.y=y;
        pointX = (200-x)*-1;
        pointY = (200-y);
    }

    ChartPoint(){}


    
    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    

    

  

    public int []  getPoints(){
        int result [] = {pointX, pointY};
        return result;
    }

    public void setPointX(int pointX) {
        this.x= pointX + 200;
        this.pointX = pointX;
    }

    public void setPointY(int pointY) {
        this.y= 200 - pointY;
        this.pointY = pointY;
    }

    public void setReg_and(int label){
        this.reg_and= label;
    }

    public int getReg_and(){
        return this.reg_and;
    }

    @Override
    public boolean equals(Object obj) {
        
        if (!(obj instanceof ChartPoint)) {
            return false;
        }
        
        ChartPoint other = (ChartPoint) obj;
        return this.pointX == other.pointX && this.pointY == other.pointY;
    }

    
 }
