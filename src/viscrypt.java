import java.awt.Color;
import java.awt.image.BufferedImage;

public class viscrypt {
        BufferedImage shares1 = null;
        BufferedImage shares2 = null;
    
//  MEMBUAT BLOCK CCT
    public BufferedImage CCT(BufferedImage img, int x, int y, int a, int b, int c, int d, boolean ganjil){
        if(ganjil){
            img.setRGB(x*4, y*2, a);
            img.setRGB(x*4+1, y*2, b);
            img.setRGB(x*4, y*2+1, c);
            img.setRGB(x*4+1, y*2+1, d);
        }
        else{
            img.setRGB(x*4+2, y*2, a);
            img.setRGB(x*4+3, y*2, b);
            img.setRGB(x*4+2, y*2+1, c);
            img.setRGB(x*4+3, y*2+1, d);
        }
        return img;    
    }
    
//  MEMBUAT BLOCK SCT
    public BufferedImage SCT(BufferedImage img, int x, int y, int a, int b, int c, int d, boolean ganjil){
        if(ganjil){
            img.setRGB(x*4+2, y*2, a);
            img.setRGB(x*4+3, y*2, b);
            img.setRGB(x*4+2, y*2+1, c);
            img.setRGB(x*4+3, y*2+1, d);
        }
        else{
            img.setRGB(x*4, y*2, a);
            img.setRGB(x*4+1, y*2, b);
            img.setRGB(x*4, y*2+1, c);
            img.setRGB(x*4+1, y*2+1, d);
        }
        return img;    
    }
    
//  METHOD CEK APAKAH GAMBAR RGB ATAU TIDAK  
    public Boolean cekRGB(BufferedImage image){
        
        boolean rgb = false;
        
        for (int x = 0; x < image.getWidth(); x++){
            for (int y = 0; y < image.getHeight(); y++) {
                //scan pixel
                int pixel = image.getRGB(x, y);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                //check jika R!=G!=B
                if (red != green || green != blue ) {
                    rgb = true;
                    break;
                }
            }
            }
        return rgb;
    }
    
//  METHOD CEK UKURAN IMAGE HARUS SAMA
    public Boolean cekSize(BufferedImage cover1, BufferedImage cover2, BufferedImage secret){
        
        boolean size=false;
        
        if(cover1.getWidth()==cover2.getWidth() && cover1.getWidth()==secret.getWidth() && cover1.getHeight()==cover2.getHeight() && cover1.getHeight()==secret.getHeight()){
            size = true;
        }
        else{
            size=false;
        }
        return size;
    }
    // method pembuat shares
    public void generateShares(BufferedImage cover1,BufferedImage cover2,BufferedImage secret){
            //          *WARNA
        Color blk = new Color(0,0,0);
        Color blu = new Color(0,0,255);
        Color grn = new Color(0,255,0);
        Color cyn = new Color(  0, 255, 255);
        Color rd = new Color(255,   0,   0);
        Color prpl = new Color(255,   0, 255);
        Color yllw = new Color(255, 255,   0);
        Color wht = new Color(255, 255, 255);                  
        int black=blk.getRGB();
        int green=grn.getRGB();
        int blue=blu.getRGB();
        int cyan=cyn.getRGB();
        int red=rd.getRGB();
        int purple=prpl.getRGB();
        int yellow=yllw.getRGB();
        int white=wht.getRGB();
        
        
        Dither dither = new Dither();
        boolean ganjil;
            
        BufferedImage errdif1 = dither.floydSteinbergDithering(cover1);     
        BufferedImage errdif2 = dither.floydSteinbergDithering(cover2);
        BufferedImage secreterdif = dither.floydSteinbergDithering(secret);
        
        shares1 = new BufferedImage(errdif1.getWidth()*4, errdif1.getHeight()*2, BufferedImage.TYPE_INT_ARGB);
        shares2 = new BufferedImage(errdif1.getWidth()*4, errdif1.getHeight()*2, BufferedImage.TYPE_INT_ARGB);

        
        for(int x=0; x<errdif1.getWidth();x++){
            for(int y=0; y<errdif1.getHeight();y++){
                if(y%2==1){
                    ganjil=true;
                }else{
                    ganjil=false;
                }
//                    *COVER CODING TABLE*
//
//                  UNTUK BLOCK 1
                    if(errdif1.getRGB(x, y)==white){
                        CCT(shares1,x,y,white,black,black,white,ganjil);
                    }
                    else if(errdif1.getRGB(x, y)==cyan){
                        CCT(shares1,x,y,cyan,black,black,cyan,ganjil);
                    }
                    else if(errdif1.getRGB(x, y)==yellow){
                        CCT(shares1,x,y,yellow,black,black,yellow,ganjil);
                    }
                    else if(errdif1.getRGB(x, y)==purple){
                        CCT(shares1,x,y,purple,black,black,purple,ganjil);
                    }
                    else if(errdif1.getRGB(x, y)==blue){
                        CCT(shares1,x,y,blue,black,black,blue,ganjil);
                    }
                    else if(errdif1.getRGB(x, y)==red){
                        CCT(shares1,x,y,red,black,black,red,ganjil);
                    }
                    else if(errdif1.getRGB(x, y)==green){
                        CCT(shares1,x,y,green,black,black,green,ganjil);
                    }
                    else if(errdif1.getRGB(x, y)==black){
                        CCT(shares1,x,y,black,black,black,black,ganjil);
                    }
                    
//                  UNTUK BLOCK 2
                    if(errdif2.getRGB(x,y)==white){
                        CCT(shares2,x,y,black,white,white,black,ganjil);
                    }
                    if(errdif2.getRGB(x,y)==cyan){
                        CCT(shares2,x,y,black,cyan,cyan,black,ganjil);
                    }
                    if(errdif2.getRGB(x,y)==purple){
                        CCT(shares2,x,y,black,purple,purple,black,ganjil);
                    }
                    if(errdif2.getRGB(x,y)==yellow){
                        CCT(shares2,x,y,black,yellow,yellow,black,ganjil);
                    }
                    if(errdif2.getRGB(x,y)==blue){
                        CCT(shares2,x,y,black,blue,blue,black,ganjil);
                    }
                    if(errdif2.getRGB(x,y)==red){
                        CCT(shares2,x,y,black,red,red,black,ganjil);
                    }
                    if(errdif2.getRGB(x,y)==green){
                        CCT(shares2,x,y,black,green,green,black,ganjil);
                    }
                    if(errdif2.getRGB(x,y)==black){
                        CCT(shares2,x,y,black,black,black,black,ganjil);
                    }
                    
//                  SECRET CODING TABLE
//                  BLOCK3
                    SCT(shares1,x,y,yellow,cyan,white,purple,ganjil);
                    
//                  BLOCK 4
                    if(secreterdif.getRGB(x,y)==white){
                        SCT(shares2,x,y,yellow,cyan,white,purple,ganjil);
                    }
                    if(secreterdif.getRGB(x,y)==cyan){
                        SCT(shares2,x,y,yellow,white,cyan,purple,ganjil);
                    }
                    if(secreterdif.getRGB(x,y)==purple){
                        SCT(shares2,x,y,yellow,cyan,purple,white,ganjil);
                    }
                    if(secreterdif.getRGB(x,y)==yellow){
                        SCT(shares2,x,y,white,cyan,yellow,purple,ganjil);
                    }
                    if(secreterdif.getRGB(x,y)==blue){
                        SCT(shares2,x,y,yellow,purple,white,cyan,ganjil);
                    }
                    if(secreterdif.getRGB(x,y)==red){
                        SCT(shares2,x,y,purple,cyan,white,yellow,ganjil);
                    }
                    if(secreterdif.getRGB(x,y)==green){
                        SCT(shares2,x,y,cyan,yellow,white,purple,ganjil);
                    }
                    if(secreterdif.getRGB(x,y)==black){
                        SCT(shares2,x,y,purple,white,cyan,yellow,ganjil);
                    }
            }
        }
        this.shares1=shares1;
        this.shares2=shares2;
    }
    //mengambil shares1
    public BufferedImage getShares1(){
        return shares1;
    }
    //mengambil shares2
    public BufferedImage getShares2(){
        return shares2;
    }
    
    //method untuk merekonstruksi ulang shares menjadi secret
    public BufferedImage Decrypt(BufferedImage s1, BufferedImage s2){
                    
//          DECRYPT
            BufferedImage decrypt = new BufferedImage(s1.getWidth(), s1.getHeight(), BufferedImage.TYPE_INT_ARGB);
            for(int x=0; x<s1.getWidth();x++){
                for(int y=0; y<s1.getHeight();y++){
                    int shares1 = s1.getRGB(x, y);
                    int shares2 = s2.getRGB(x, y);
                    int a = ((shares1>>24)&0xff) & ((shares2>>24)&0xff);
                    int r = ((shares1>>16)&0xff) & ((shares2>>16)&0xff);
                    int g = ((shares1>>8)&0xff) & ((shares2>>8)&0xff);
                    int b = ((shares1&0xff) & (shares2&0xff));
                                        
                    Color c3 = new Color(r,g,b,a);
                    
                    decrypt.setRGB(x, y, c3.getRGB());
                }
            }
        return decrypt;
    }
}