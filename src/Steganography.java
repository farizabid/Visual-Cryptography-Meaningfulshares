import java.awt.Color;
import java.awt.image.BufferedImage;
public class Steganography {
    public BufferedImage Embedd(BufferedImage cover, BufferedImage secret){
        String MsbRed1;
        String MsbGreen1;
        String MsbBlue1;
        String MsbRed2;
        String MsbGreen2;
        String MsbBlue2;
        String red,green,blue;
        
        BufferedImage stego = new BufferedImage(cover.getWidth(), cover.getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        for (int y = 0; y < cover.getWidth(); y++) {
            for (int x = 0; x < cover.getHeight(); x++) {
                        int img1 = cover.getRGB(x, y);
                        int r1 = ((img1>>16)&0xff);
                        int g1 = ((img1>>8)&0xff);
                        int b1 = ((img1&0xff));
                        String BitRed1 = (Integer.toBinaryString(r1));
                        String BitGreen1 = (Integer.toBinaryString(g1));
                        String BitBlue1 = (Integer.toBinaryString(b1));
                        
                        while(BitRed1.length()!=8){
                            BitRed1='0'+BitRed1;
                        }
                        while(BitGreen1.length()!=8){
                            BitGreen1='0'+BitGreen1;
                        }
                        while(BitBlue1.length()!=8){
                            BitBlue1='0'+BitBlue1;
                        }
                        
                        MsbRed1 = BitRed1.substring(0, 4);
                        MsbGreen1 = BitGreen1.substring(0, 4);
                        MsbBlue1 = BitBlue1.substring(0, 4);
                        
                        int img2 = secret.getRGB(x, y);
                        int r2 = ((img2>>16)&0xff);
                        int g2 = ((img2>>8)&0xff);
                        int b2 = ((img2&0xff));
                        String BitRed2 = (Integer.toBinaryString(r2));
                        String BitGreen2 = (Integer.toBinaryString(g2));
                        String BitBlue2 = (Integer.toBinaryString(b2));
                        
                        while(BitRed2.length()!=8){
                            BitRed2='0'+BitRed2;
                        }
                        while(BitGreen2.length()!=8){
                            BitGreen2='0'+BitGreen2;
                        }
                        while(BitBlue2.length()!=8){
                            BitBlue2='0'+BitBlue2;
                        }
                        
                        MsbRed2 = BitRed2.substring(0, 4);
                        MsbGreen2 = BitGreen2.substring(0, 4);
                        MsbBlue2 = BitBlue2.substring(0, 4);
                        
                        red=MsbRed1.concat(MsbRed2);
                        green=MsbGreen1.concat(MsbGreen2);
                        blue=MsbBlue1.concat(MsbBlue2);
                        
                        int a = 255;
                        int r = Integer.parseInt(red,2);
                        int g = Integer.parseInt(green,2);
                        int b = Integer.parseInt(blue,2);

                        Color c3 = new Color(r,g,b,a);
                        stego.setRGB(x, y, c3.getRGB());       
                }
            }
        return stego;
    }
    
    public BufferedImage Extract(BufferedImage stego){
        BufferedImage secret = new BufferedImage(stego.getWidth(), stego.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < stego.getWidth(); y++) {
            for (int x = 0; x < stego.getHeight(); x++) {
                int img = stego.getRGB(x, y);
                int r1 = ((img>>16)&0xff);
                int g1 = ((img>>8)&0xff);
                int b1 = ((img&0xff));
                String BitRed = (Integer.toBinaryString(r1));
                String BitGreen = (Integer.toBinaryString(g1));
                String BitBlue = (Integer.toBinaryString(b1));

                while(BitRed.length()!=8){
                    BitRed='0'+BitRed;
                }
                while(BitGreen.length()!=8){
                    BitGreen='0'+BitGreen;
                }
                while(BitBlue.length()!=8){
                    BitBlue='0'+BitBlue;
                }

                String Red = (BitRed.substring(4, 8)).concat("0000");
                String Green = (BitGreen.substring(4, 8)).concat("0000");
                String Blue = (BitBlue.substring(4, 8)).concat("0000");
                
                int a = 255;
                int r = Integer.parseInt(Red,2);
                int g = Integer.parseInt(Green,2);
                int b = Integer.parseInt(Blue,2);

                Color c3 = new Color(r,g,b,a);
                secret.setRGB(x, y, c3.getRGB());  
            }
        }
        return secret;
        
    }
}