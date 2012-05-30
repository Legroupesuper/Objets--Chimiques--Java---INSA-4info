/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChLoe.

    ChLoe is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChLoe is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChLoe.  If not, see <http://www.gnu.org/licenses/>
*/
package org.chemicalmozart.viewV2;

/*
 * JAnimatedIcon.java
 *
 * Created on 8 mars 2005, 23:19
 */

import  javax.swing.*;
import  java.awt.image.*;
import  java.io.*;
import  javax.imageio.*;
import  java.awt.*;
import  javax.imageio.stream.*;
import  java.util.*;


/**
 *
 * JAnimatedIcon : <br>
 *<br>
 * Permet de cr��r des JLabel anim�!!<br>
 * La classe utilise son propre format d'animation (fichiers en .ai) : pour creer une animation, utiliser saveAnimatedIcon() qui
 * a partir d'une grosse image decoupe les images de l'animations (images de l'animation toutes de la meme taille et plac�es 
 * sur l'horizontale dans la grosse image). Utiliser loadAnimatedIcon pour charger l'animation<br>
 * <br>
 * Sinon, ce composant s'utilise comme un JLabel (herite d'ailleurs de JLabel)
 * <br>
 * <br>
 * <b>MISE A JOUR </b>: 30 Mars 2006 : au lieu de creer l'animation a partir du format specifique a cette classe, on peut desormais charger
 * directement un gif anim� !!!<br>
 * Pour mettre une animation gif : utiliser le constructeur JAnimatedIcon(String gif,int milli) <br>
 * gfx est le chemin vers l'animation et milli est l'attente en millisecondes entre images<br>
 * Exemple : <br>
 * JAnimatedIcon anim=new JAnimatedIcon("bubble.gif",50);<br>
 * <br>
 *
 * @author Guillaume Bouchon (bouchon_guillaume@yahoo.fr)
 */
public class JAnimatedIcon extends  JLabel {
	private static final long serialVersionUID = 1L;

	/**
     * les images de l'anim
     */
    private ImageIcon   img[];          //les images de l'anim
    
    /**
     *  attente en millisecondes entre 2 images de l'anim
     */
    private int         anim_every=50;  
    
    /**
     * anim en pause ou pas
     */
    private boolean     pause=false; 
    
    /**
     * thread gerant l'anim
     */
    private Thread      refresh;
    
    /**
     * image courante
     */
    private int         c_img;
    
    private int         debut=0;
    private int         duration=-1;
    
    
     /** 
     * constructeur
     */
    public JAnimatedIcon()
    {
        super();
        createAnim();
    }
    
    /**
     * MISE A JOUR : nouveau constructeur<br>
     * Animation chargee a partir d'un gif anim� !!
     @param gif le chemin vers l'animation gif
     @param milli attente en milliseconde entre 2 images
     */
    public JAnimatedIcon(String gif,int milli)
    {
        setImages(readGifImages(gif)); 
        createAnim();
        setAnimRate(milli);
    }
    
     /** 
     * constructeur
      *<br>Copie de l'icone pass� en parametre
      @param a le JAnimatedIcon a copier
     */
    public JAnimatedIcon(JAnimatedIcon a)
    {
        a.copyIn(this);
        createAnim();
    }
    
    /** 
     * constructeur
      *<br>Copie de l'icone pass� en parametre avec transparence
     @param a le JAnimatedIcon a copier
     @param key la couleur transparente
     @param tolerance la tolerance sur la couleur transparente (comprise entre 0 et 1)
     */
    public JAnimatedIcon(JAnimatedIcon a,Color key,float tolerance)
    {
        a.copyIn(this);
        createAnim();
        setColorKeyTransparency(key,tolerance);
    }
    
    /** 
     * constructeur
      *<br>Copie de l'icone en redimensionnant 
     @param a le JAnimatedIcon a copier
     @param wi la largeur en pixels voulue
     @param hi la hauteur en pixels voulue
     */
    public JAnimatedIcon(JAnimatedIcon a,int wi,int hi)
    {
        this(a);
        reDimension(wi,hi);
        createAnim();
    }
    
    /** 
     * constructeur
      *<br>Copie de l'icone en redimensionnant et avec une couleur transparente
     @param a le JAnimatedIcon a copier
     @param wi la largeur en pixels voulue
     @param hi la hauteur en pixels voulue
     @param key la couleur transparente
     @param tolerance la tolerance sur la couleur transparente (comprise entre 0 et 1)
     */
    public JAnimatedIcon(JAnimatedIcon a,int wi,int hi,Color key,float tolerance)
    {
        this(a);
        reDimension(wi,hi);
        createAnim();
        setColorKeyTransparency(key,tolerance);
    }
   
    /** 
     * constructeur
     @param text le texte
     @param images les images de l'animation
     */
    public JAnimatedIcon(String text,BufferedImage images[])
    {
        super(text);
        
        setImages(images);
        
        createAnim();
    }
    
    /** 
     * constructeur
     @param text le texte
     @param images les images de l'animation
     @param key la couleur transparente
     @param tolerance la tolerance sur la couleur transparente (comprise entre 0 et 1)
     */
    public JAnimatedIcon(String text,BufferedImage images[],Color key,float tolerance)
    {
        super(text);
        
        setImages(images);
        
        createAnim();
        setColorKeyTransparency(key,tolerance);
    }
    
     /** 
     * constructeur
     @param text le texte
     @param images les chemins des images de l'animation
     */
    public JAnimatedIcon(String text,String images[])
    {
        super(text);
        
        setImages(images);
        
        createAnim();
    }
    
     /** 
     * constructeur
     @param text le texte
     @param images les chemins des images de l'animation
     @param key la couleur transparente
     @param tolerance la tolerance sur la couleur transparente (comprise entre 0 et 1)
     */
    public JAnimatedIcon(String text,String images[],Color key,float tolerance)
    {
        super(text);
        
        setImages(images);
        
        createAnim();
        setColorKeyTransparency(key,tolerance);
    }
    
    /**
     * constructeur
     *<br>Une seule image
     @param text le texte
     @param image le chemin de l'image de l'animation
     @param key la couleur transparente
     @param tolerance la tolerance sur la couleur transparente (comprise entre 0 et 1)
     */
    public JAnimatedIcon(String text,String image,Color key,float tolerance)
    {
        super(text);
        
        setImages(image);
        
        createAnim();
        setColorKeyTransparency(key,tolerance);
    }  
    
    /**
     * constructeur
     *<br>Une seule image
     @param text le texte
     @param image le chemin de l'image de l'animation
     */
    public JAnimatedIcon(String text,String image)
    {
        super(text);
        
        setImages(image);
        
        createAnim();
    } 
    
     /**
     * constructeur
     *<br>Une seule image
     @param text le texte
     @param image l'image de l'animation
     */
    public JAnimatedIcon(String text,BufferedImage image)
    {
        super(text);
        
        setImages(image);
        
        createAnim();
    } 
    
    /**
     * constructeur
     *<br>Une seule image
     @param text le texte
     @param image l'image de l'animation
     @param key la couleur transparente
     @param tolerance la tolerance sur la couleur transparente (comprise entre 0 et 1)
     */
    public JAnimatedIcon(String text,BufferedImage image,Color key,float tolerance)
    {
        super(text);
        
        setImages(image);
        
        createAnim();
        setColorKeyTransparency(key,tolerance);
    } 
    
    /** 
     * constructeur
     @param text le texte
     @param images les images de l'animation
     @param horizontalAlignment l'alignement (voir JLabel)
     */
    public JAnimatedIcon(String text,BufferedImage images[],int horizontalAlignment)
    {
        super(text,horizontalAlignment);
        
        setImages(images);
        
        createAnim();
    }   
    
     /** 
     * constructeur
     @param text le texte
     @param images les chemins des images de l'animation
     @param horizontalAlignment l'alignement (voir JLabel)
     */
    public JAnimatedIcon(String text,String images[],int horizontalAlignment)
    {
        super(text,horizontalAlignment);
        
        setImages(images);
        
        createAnim();
    } 
    
     /** 
     * constructeur
     @param text le texte
     @param images les chemins des images de l'animation
     @param horizontalAlignment l'alignement (voir JLabel)
     @param key la couleur transparente
     @param tolerance la tolerance sur la couleur transparente (comprise entre 0 et 1)
     */
    public JAnimatedIcon(String text,String images[],int horizontalAlignment,Color key,float tolerance)
    {
        super(text,horizontalAlignment);
        
        setImages(images);
        
        createAnim();
        setColorKeyTransparency(key,tolerance);
    }
    
    /** 
     * constructeur
     @param text le texte
     @param image l'image de l'animation
     @param iw la largeur en pixels voulue
     @param ih la hauteur en pixels voulue
     */
    public JAnimatedIcon(String text,BufferedImage images,int iw,int ih)
    {
        super(text);
        
        setImages(images,iw,ih);
        
        createAnim();
    } 
    
    /** 
     * constructeur
     @param text le texte
     @param image chemin de l'image de l'animation
     @param iw la largeur en pixels voulue
     @param ih la hauteur en pixels voulue
     */
    public JAnimatedIcon(String text,String images,int iw,int ih)
    {
        super(text);
        
        setImages(images,iw,ih);
        
        createAnim();
    } 
    
    /**
     * definie en milliseconde l'attente entre 2 images
     @param milli duree d'attente en millisecondes entre 2 images de l'animation
     */ 
    public  void    setAnimRate(int milli)
    {
       anim_every=milli; 
    }
    
    /**
     * defini la duree de l'animation en millisecondes
     @param milli <=0 pas de duree
     */
    public  void    setDuration(int milli)
    {
        duration=milli;
        restart();
    }
    
    /**
     * pause ou sort de pause
     @param si pause=true, met en pause sinon en sort
     */
    public  synchronized void    setPause(boolean pause)
    {
        if  (refresh==null) return;
        
       
       this.pause=pause;
        
       synchronized(refresh)
       {
        refresh.notify();
       }
    }
    
    /**
     * renvoi true si l'anim est en pause
     @return true si l'animation est en pause
     */
    public  boolean isPaused()
    {
        return  pause;
    }
    
     /**
     * definie l'image de l'anim
     @param image l'image
     */
    public  void    setImages(String image)
    {
        BufferedImage   img=loadImage(image);
        
        if  (img==null) return;
        setImages(img);
    }
    
    /**
     * definie l'image de l'anim
     *<br>Anim avec 1 seule image
     @param image l'image
     */
    public  void    setImages(BufferedImage image)
    {
        
        BufferedImage im[]=new BufferedImage[1];
        im[0]=image;
        if  (image==null) return;
        setImages(im);
    }
    
    /**
     * redemarre l'animation
     */
    public  synchronized void    restart()
    {
        debut=(int) (System.currentTimeMillis());
        c_img=0;
        setPause(false);
        
    }
    
    /**
     * definie les images de l'anim
     @param images les images
     */
    public  void    setImages(BufferedImage images[])
    {
        setPause(true);
        
        if  (images==null)  { img=null; return;}
        
        c_img=0;
        
        img=new ImageIcon[images.length];
        
        for(int i=0;i<images.length;i++)    //creation des icones
        {
             
           img[i]=new  ImageIcon(images[i]);
        }
        
        setPause(false);
        
        c_img=-1;
         nextFrame();
         repaint();
    }
    
    /**
     * definie les images de l'anim
     @param images les chemins des images
     */
    public  void    setImages(String images[])
    {
        BufferedImage   im[]=new    BufferedImage[images.length];
        
        c_img=0;
        
        for(int i=0;i<images.length;i++)
            im[i]=loadImage(images[i]);     //charge l'image
        
        setImages(im);  //defini les images
    }
    
    /**
     * decoupe les images de l'anim a partir d'une grande image (decoupe horizontale)
     @param big le chemin vers la "grosse" image contenant toutes les images
     @param iw largeur des images de l'animation
     @param ih hauteur des images de l'animation
     */ 
    public  void    setImages(String big,int iw,int ih)
    {
        setImages(loadImage(big),iw,ih);
    }
    
    /**
     * definie les images
     @icon les images
     */
    public  void    setImages(ImageIcon icon[])
    {
        if  (icon==null)    return;
        
        BufferedImage   img[]=new   BufferedImage[icon.length];
        
        for(int i=0;i<icon.length;i++)
        {
            if  (icon[i]!=null)
                img[i]=(BufferedImage) icon[i].getImage();
        }
        
        setImages(img);
    }
    
    /**
     * decoupe les images de l'anim a partir d'une grande image (decoupe horizontale)
     @param big la "grosse" image contenant toutes les images
     @param iw largeur des images de l'animation
     @param ih hauteur des images de l'animation
     */ 
    public  void    setImages(BufferedImage big,int iw,int ih)
    {
        int ni=big.getWidth()/iw;   //nombre d'images 
        
        c_img=0;
        
        if  (big.getHeight()<ih)    return;     //invalide
        
        BufferedImage   im[]=new    BufferedImage[ni];
        
        //decoupage...
        int x=0;
        for(int i=0;i<ni;i++)
        {
            im[i]=big.getSubimage(x,0,iw,ih);   //decoupage de l'image i
            x+=iw;  //on avance
        }
        
        setImages(im);  //defini les images
        
    }
    
    
    
    
    /**
     * charge une image
     @param pic le chemin vers l'image
     @return l'image charg�e ou null si erreur
     */
    private BufferedImage   loadImage(String pic)
    {
        try{
            BufferedImage   img=ImageIO.read(new File(pic));
        return img;
        }catch(Exception e)
        {
            return  null;
        }
    }
    
    /**
     * cr�� le thread g�rant les anims
     */
    private void    createAnim()
    {
        if  (refresh!=null)     return; //1 seule fois
            
        refresh=new Thread() 
        {   public void run() 
            { 
                while(true) //boucle principale
                {
                    try{
                        synchronized(this)
                        {
                            while(pause)    //en pause
                                wait();
                        
                            wait(anim_every);
                        }
                        
                        //change d'image
                        nextFrame();
                        
                    }catch(InterruptedException e)
                    {}
                    
                    
                }
            } 
        };
        
        refresh.start();    //on demarre
            
        
    }
    
    /**
     * avance dans l'anim
     */
    private void    nextFrame()
    {
        if  (img==null || img.length==0)    return;
        
        if  (img.length<2 && c_img>=0)  return; //animation avec moins de 2 images
        
        if  (duration>0)
        {
            if  (debut>0)
            {
                if  ( (((int) System.currentTimeMillis())-debut) >= duration)   //duree depass�e
                {
                    setPause(true);
                    c_img=-1;
                    debut=-1;
                }
            }
        }    
        
        if  (c_img<0)   c_img=-1;
        
        c_img=(c_img+1)%img.length; //prochaine image
            
        setIcon(img[c_img]);
        repaint();
    }
    
    /**
     * appell� a la destruction de la classe
     *<br>
     *EXPERIMENTAL
     */
    @SuppressWarnings("deprecation")
	protected   void    finalize() throws Throwable
    {
        if  (refresh!=null)
        {
            synchronized(refresh)
            {
                //arret du thread
                refresh.stop();
                refresh=null;
            }
        }
        super.finalize();
    }
    
     /**
     * sauvegarde d'une image en format appropri� pour un animated icon (JAnimatedIcon)
     *<br>les images de l'animation doivent etre align�es horizontalement et doivent faire la meme taille
     @param wi largeur d'une animation
     @param hi hauteur d'une animation
     @param milli attente en milliseconde entre chaque image
     */
    public  static  boolean saveAnimatedIcon(BufferedImage img,String dest,int wi,int hi,int milli)
    {
        byte    header[]={'A','n','i','m','a','t','e','d','*','I','c','o','n'}; //l'entete
                
        try{
            FileOutputStream   out=new FileOutputStream(dest);
            
            out.write(header);  //entete
            
            out.write((wi&0xFF00)>>8);  //poids fort
            out.write(wi&0x00FF);   //poids faible
            
            out.write((hi&0xFF00)>>8);  //poids fort
            out.write(hi&0x00FF);   //poids faible
            
            out.write((milli&0xFF00)>>8);  //poids fort
            out.write(milli&0x00FF);   //poids faible
            
            //l'image
            ImageIO.write(img,"png",out);
            
            out.close();
        }catch(Exception e)
        {
            return  false;  //erreur
        }
        
        return   true;
    }
    
     /**
     * charge une image en format appropri� pour un animated icon (JAnimatedIcon) (sauvegard� avec saveAnimatedIcon)
     @param src le fichier animated icon cr�� avec saveAnimatedIcon
     @param key la couleur transparente
     @param tolerance la tolerance sur la couleur transparente (comprise entre 0 et 1)
     @return le JAnimatedIcon charg� ou null si erreur
     */
    public  static  JAnimatedIcon loadAnimatedIcon(String src,Color key,float tolerance)
    {
        JAnimatedIcon ai=loadAnimatedIcon(src);
        if  (ai==null)  return  null;
        
        ai.setColorKeyTransparency(key,tolerance);
        
        return  ai;
    }
    
     /**
     * charge une image en format appropri� pour un animated icon (JAnimatedIcon) (sauvegard� avec saveAnimatedIcon)
     @param src le fichier animated icon cr�� avec saveAnimatedIcon
     @return le JAnimatedIcon charg� ou null si erreur
     */
    public  static  JAnimatedIcon loadAnimatedIcon(String src)
    {
        byte    header[]={'A','n','i','m','a','t','e','d','*','I','c','o','n'}; //l'entete
                
        try{
            FileInputStream   in=new FileInputStream(src);
            
            //verification de l'entete
            for(int i=0;i<header.length;i++)
            {
                if  (in.read()!=header[i])  return  null;   //mauvais entete
            }    
            
            int wi=0,hi=0,milli=0;
            
            wi=(in.read()<<8)|(in.read());
            hi=(in.read()<<8)|(in.read());
            milli=(in.read()<<8)|(in.read());

            //l'image
            BufferedImage img=ImageIO.read(in);
            
            in.close();
            
            if  (img==null) return  null;
            
            JAnimatedIcon ai=new JAnimatedIcon("",img,wi,hi);
            ai.setAnimRate(milli);
            
            return  ai;
        }catch(Exception e)
        {
            return  null;  //erreur
        }
        
    }
    
    /**
     * copie l'icone dans un autre
     @param in o� copier l'icone
     */
    public  void    copyIn(JAnimatedIcon in)
    {
        if  (in==null)  return;
        
        in.setImages(img);
        in.setAnimRate(anim_every);
        
        in.setPause(false);
    }
    
    /**
     * redimensionne les images
     @param wi largeur des images
     @param hi hauteur des images
     */
    @SuppressWarnings("deprecation")
	public  void    reDimension(int wi,int hi)
    {
        if  (img==null) return;
        
        setPause(true);
        
        for(int i=0;i<img.length;i++)
        {
            Image   io=img[i].getImage();
            BufferedImage   in=new BufferedImage(wi,hi,BufferedImage.TYPE_INT_ARGB); //io.getType());
            Graphics g=in.createGraphics();
            g.drawImage(io,0,0,wi,hi,null);
            img[i].setImage(in);
        }
        
        resize(wi,hi);
        
        setPause(false);
    }
    
    /**
     * toString
     @return la chaine representant cette classe
     */
    public  String toString()
    {
        return  "JAnimatedIcon - number of frames : "+img.length+" - frame rate : "+anim_every;
    }
    
    /**
     * defini une couleur transparente pour l'animation
     @param key la couleur transparente
     @param tolerance compris entre 0.0f et 1.0f
     */
    public  void    setColorKeyTransparency(Color key,float tolerance)
    {
        if  (img==null) return;
        
        setPause(true);
        
        for(int i=0;i<img.length;i++)
        {
            Image   io=img[i].getImage();
            BufferedImage   in=new BufferedImage(io.getWidth(null),io.getHeight(null),BufferedImage.TYPE_INT_ARGB); 
            Graphics g=in.createGraphics();
            g.drawImage(io,0,0,in.getWidth(),in.getHeight(),null);
            
            setColorKey(in,key,tolerance);
            
            img[i].setImage(in);
        }
        
        setPause(false);
    }
    
    /**definie une couleur transparente sur l'image
     * les pixels de cette couleur ne seront pas affich�s lors de drawImage 
     * UNIQUEMENT POUR UNE IMAGE DE TYPE TYPE_INT_ARGB !!!
     * tolerance : 0.0f<=tol<=1.0f
     @param img l'image a traiter
     @param key la couleur transparente
     @param tolerance la tolerance (comprise entre 0 et 1)
     */
    private  static  void    setColorKey(BufferedImage img,Color key,float tolerance)
    {
        if  (img==null) return;
        if  (img.getType()!=BufferedImage.TYPE_INT_ARGB)    return;
            
        int w=img.getWidth(),h=img.getHeight();
        Raster  raster=img.getData();
        int im[]=new int[4];   //raster.getPixels(0,0,w,h,(int[]) null);
        WritableRaster araster=img.getAlphaRaster();
        if  (araster==null) return; 
        
        double transparent[]=new double[1];
        transparent[0]=0.0;
        
        int rk=key.getRed(),gk=key.getGreen(),bk=key.getBlue();
        
        int rkmin=(int) ((1.0f-tolerance)*rk);
        int rkmax=(int) ((1.0f+tolerance)*rk);
        int gkmin=(int) ((1.0f-tolerance)*gk);
        int gkmax=(int) ((1.0f+tolerance)*gk);
        int bkmin=(int) ((1.0f-tolerance)*bk);
        int bkmax=(int) ((1.0f+tolerance)*bk);
        
        //System.out.println("rk=["+rkmin+","+rkmax+"] gk=["+gkmin+","+gkmax+"] bk=["+bkmin+","+bkmax+"]");       
        int r,g,b;
        for(int y=0;y<h;y++)
        {
            for(int x=0;x<w;x++)
            {
                im=raster.getPixel(x,y,im);
                r=im[0]; 
                g=im[1]; 
                b=im[2]; 
                
                //System.out.println("r="+r+" g="+g+" b="+b+" im="+im[0]+" rk="+rk+" gk="+gk+" bk="+bk);

                if  (r>=rkmin && r<=rkmax && g>=gkmin && g<=gkmax && b>=bkmin && b<=bkmax)
                {
                    araster.setPixel(x,y,transparent);
                }
            }
        }
    }
    
      /**
     * lit les images d'un gif anim�
     @param f le gif anim�
     @return le tableau d'images ou null si erreur
     */
    public  static  BufferedImage[]    readGifImages(String f)
    {
        BufferedInputStream in=null;
        //stream image
        ImageInputStream stream = null;
        try {
            in=new  BufferedInputStream(new FileInputStream(new File(f)));
            stream = ImageIO.createImageInputStream(in);
        } catch (IOException e) {
            return  null;
        }

        //reader d'image'
        ImageReader r=getReader("GIF");
        if  (r==null)   return  null;

        //tableau dynamique des images
        ArrayList<BufferedImage>   img=new   ArrayList<BufferedImage>();
        try{
            r.setInput(stream,true,true);
            ImageReadParam param = r.getDefaultReadParam();

            //lecture des images : tant qu'il y a pas d'exception
            try{
                int i=0;
                while(true)
                {
                    img.add(r.read(i,param));
                    i++;
                }
            }catch(Exception e2) {}
            
            r.dispose();
            in.close();
        }catch(Exception e)
        {
            return  null;
        }
        
        return  img.toArray(new BufferedImage[img.size()]);
    }
    
    /**
     * donne le reader correspondant au format
     @param format le format d�sir� (voir ImageIO.write)
     @return le reader correspondant ou null si erreur
     */
    private static  ImageReader getReader(String format)
    {
        Iterator readers = ImageIO.getImageReadersBySuffix(format);
        if (readers.hasNext()) 
        {
            ImageReader reader = (ImageReader) readers.next();
            return  reader;
        }
        return  null;
    }
}