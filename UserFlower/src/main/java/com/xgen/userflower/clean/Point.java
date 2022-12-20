/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xgen.userflower.clean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author x19047xx
 */
public class Point {
    public int x;
    public int y;
    public int z;

    int v_;
        
    public Point(int x_, int y_, int z_){
        x = x_;
        y = y_;
        z = z_;
    }
    
    public List<Point> to(Point target){
        List<Point> loopArray = new ArrayList<>();
        
        int lower_Y = y;
        int lower_Z = z;
        int lower_X = x;

        int upper_Y = target.y;
        int upper_Z = target.z;
        int upper_X = target.x;
        

        
        if(lower_Y >= upper_Y){
            v_ = upper_Y;
            upper_Y = lower_Y;
            lower_Y = v_;
        }
        
        if(lower_Z >= upper_Z){
            v_ = upper_Z;
            upper_Z = lower_Z;
            lower_Z = v_;
        }
        
        if(lower_X >= upper_X){
            v_ = upper_X;
            upper_X = lower_X;
            lower_X = v_;
        }
        
        for(int y_ = upper_Y ; y_ >= lower_Y ; y_--){
            for(int z_ = lower_Z ; z_ <= upper_Z ; z_++){
                for(int x_ = lower_X; x_ <= upper_X ; x_++){
                    
                    loopArray.add(new Point(x_,y_,z_));
                    
                }
            }
        }
        
        return loopArray;
    }
    
}
