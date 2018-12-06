/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package image_editor_java;





import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javafx.application.Application.*;
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light.Distant;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Reflection;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import java.lang.*;
import java.util.ArrayList;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class ImageEditorUsingFX  extends Application
{
        private double getHistAverage(Mat hsvImg, Mat hueValues)
    {
            // init
            double average = 0.0;
            Mat hist_hue = new Mat();
            MatOfInt histSize = new MatOfInt(180);
            List<Mat> hue = new ArrayList<>();
            hue.add(hueValues);

            // compute the histogram
            Imgproc.calcHist(hue, new MatOfInt(0), new Mat(), hist_hue, histSize, new MatOfFloat(0, 179));

            // get the average for each bin
            for (int h = 0; h < 180; h++)
            {
                    average += (hist_hue.get(h, 0)[0] * h);
            }

            return average = average / hsvImg.size().height / hsvImg.size().width;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage)
    {
      
       //Image image = new Image(Main.class.getResourceAsStream(""));
        ImageView iv1 = new ImageView();
        Image image = null;
        iv1.setImage(image);
        
        
        //Parent root = null;
        VBox root;
        root = new VBox(5);
        Scene scene = new Scene(root, 800, 500,Color.BLANCHEDALMOND);
        
        primaryStage.setTitle("Image Editor Using JavaFX");
    
        //create the BorderPane
        BorderPane pane1 = new BorderPane();
        pane1.setPadding(new Insets(10));
       // root.setCenter(iv1);
         
        
        Reflection r = new Reflection();
        r.setFraction(1);
       
        BoxBlur bb = new BoxBlur();
        bb.setWidth(5);
        bb.setHeight(5);
        bb.setIterations(3);
       
        Distant light = new Distant();
        light.setAzimuth(-135.0f);
        Lighting l = new Lighting();
        l.setLight(light);
        l.setSurfaceScale(5.0f);
       
        DropShadow ds = new DropShadow();
        ds.setOffsetY(30.0);
        ds.setOffsetX(3.0);
        ds.setColor(Color.BLACK);
       
        InnerShadow is = new InnerShadow();
        is.setOffsetX(20.0);
        is.setOffsetY(2.0);
        is.setColor(Color.RED);

   /**************************************************************/
 
    final String[] viewOptions = new String[] {
        "Title", 
        "Binomial name", 
        "Picture", 
        "Description"
    };

    final ImageView pic = new ImageView();
    final Label name = new Label();
    final Label binName = new Label();
    final Label description = new Label();
 Image ima = null;

       
    /*ChangeListener<Number> listener = new ChangeListener<Number>() {
    @Override
    public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
        Bounds boundsInScene = iv1.localToScene(iv1.getBoundsInLocal());
        double xInScene = boundsInScene.getMinX();
        double yInScene = boundsInScene.getMinY();
        // do something with values...
    }   
    });*/
    //iv1.translateXProperty().addListener(listener);
    //iv1.translateYProperty().addListener(listener);
        MenuBar menuBar = new MenuBar();
 
        // --- Menu File
        Menu menuFile = new Menu("File");
            MenuItem add = new MenuItem("Open");
            add.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                //Open -- 
              
                  
                FileChooser fileChooser = new FileChooser();
                // FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                      
                // fileChooser.getExtensionFilters().add(extFilter);
                            
                File file = fileChooser.showOpenDialog(primaryStage);
                    
                Image ima = new Image(file.toURI().toString());
                iv1.setImage(ima);
                 // iv1 = new imageView(ima);        
                     iv1.setFitWidth(500);
                     iv1.setFitHeight(400);
            }

            });
            
            MenuItem save = new MenuItem("Save");
            save.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                //Save the image file
                  
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Image");
                 
                File file = fileChooser.showSaveDialog(primaryStage);
                
                //'if (file != null) {
                    try {
                        ImageIO.write(SwingFXUtils.fromFXImage(iv1.snapshot(null, null), null),"png",file);
                        //ImageIO.write(SwingFXUtils.fromFXImage(iv1.getImage(), null), "png", file);
                    } catch (IOException ex) {
                            //Logger.getLogger
                            // JavaFXImageFileChooser.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
            
                
            }
            });
            
            MenuItem exit = new MenuItem("Exit");
            exit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            System.exit(0);
            }
            });
        menuFile.getItems().addAll(add, save, exit);
 
        // --- Menu Edit
        
        Menu menuEdit = new Menu("Edit");
            MenuItem bloom = new MenuItem("bloom");
            bloom.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                Mat frame = null;
                Mat thresholdImg = new Mat();
                Mat hsvImg = new Mat();
                List<Mat> hsvPlanes = new ArrayList<>();
                double threshValue;
                threshValue = getHistAverage(hsvImg, hsvPlanes.get(0));

            
                    Imgproc.threshold(hsvPlanes.get(0), thresholdImg, threshValue, 179.0, Imgproc.THRESH_BINARY_INV);
            
                    // create the new image
                    Mat foreground = new Mat(frame.size(), CvType.CV_8UC3, new Scalar(255, 255, 255));
                    frame.copyTo(foreground, thresholdImg);

                    //return foreground;
            }
            });
        menuEdit.getItems().addAll(bloom);    
        
        
        // --- Menu View
        Menu menuView = new Menu("View");
        
        // --- Menu Filter
        Menu menuFilter = new Menu("Filter");
            MenuItem blur = new MenuItem("Blur");
            blur.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                iv1.setEffect(bb);
            }
            });
            MenuItem button2 = new MenuItem("Light Effect");
            button2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                iv1.setEffect(l);
            }
            });
            MenuItem button3 = new MenuItem("Drop Shadow");
            button3.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                iv1.setEffect(ds);
            }
            });
            MenuItem button4 = new MenuItem("Inner Shadow");
            button4.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                iv1.setEffect(is);
            }
            });
            MenuItem button5 = new MenuItem("Reflection");
            button5.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                iv1.setEffect(r);
            }
            });
            MenuItem button6 = new MenuItem("Skew");
            button6.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                Group g = new Group();
                PerspectiveTransform pt = new PerspectiveTransform();
                pt.setUlx(10.0);
                pt.setUly(10.0);
                pt.setUrx(310.0);
                pt.setUry(40.0);
                pt.setLrx(310.0);
                pt.setLry(60.0);
                pt.setLlx(10.0);
                pt.setLly(90.0);
                
                iv1.setEffect(pt);
                iv1.setFitWidth(400);
                     iv1.setFitHeight(400);
              }
                
            
            });
            MenuItem button7 = new MenuItem("Rotation");
            button7.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                
                iv1.setRotate(90);
                iv1.setFitWidth(400);
                     iv1.setFitHeight(400);
                //iv1.setimage();    

            }
            });
            
            
            
        menuFilter.getItems().addAll(blur,button2,button3,button4,button5, button6,button7);
 
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView, menuFilter);
        // = new Scene(new VBox(), 400, 350);
        scene.setFill(Color.OLDLACE);
 
        ((VBox) scene.getRoot()).getChildren().addAll(menuBar);
 

   /**************************************************************/
   
   
       /* Button btnChooseImage = new Button();
        btnChooseImage.setText("Choose Image");
   
        btnChooseImage.setOnAction(new EventHandler<ActionEvent>()
                     {
                     @Override
                     public void handle(ActionEvent event)
                     {
                            FileChooser fileChooser = new FileChooser();
                         // FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                      
                         // fileChooser.getExtensionFilters().add(extFilter);
                            
                          File file = fileChooser.showOpenDialog(primaryStage);
                    
                         Image ima = new Image(file.toURI().toString());
                         iv1.setImage(ima);
                          
                     }
                     }
        );
   
       
        Button btnLightEffect = new Button();
        btnLightEffect.setText("Light Effect");
   
        btnLightEffect.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                iv1.setEffect(l);
                          
            }
        });
   
       
        Button btnOuterShadow = new Button();
        btnOuterShadow.setText("Drop Shadow");
   
        btnOuterShadow.setOnAction(new EventHandler<ActionEvent>()
                     {
                     @Override
                     public void handle(ActionEvent event)
                     {
                          
                           iv1.setEffect(ds);
                          
                     }
                     }
        );
       
        Button btnInnerShadow = new Button();
        btnInnerShadow.setText("Inner Shadow");
   
        btnInnerShadow.setOnAction(new EventHandler<ActionEvent>()
                     {
                     @Override
                     public void handle(ActionEvent event)
                     {
                          
                           iv1.setEffect(is);
                     }
                     }
        );
       
        Button btnBlur = new Button();
        btnBlur.setText("Blur");
   
        btnBlur.setOnAction(new EventHandler<ActionEvent>()
                     {
                     @Override
                     public void handle(ActionEvent event)
                     {
                           iv1.setEffect(bb);
                     }
                     }
        );
       
        Button btnReflection = new Button();
        btnReflection.setText("Reflection");
   
        btnReflection.setOnAction(new EventHandler<ActionEvent>()
                     {
                     @Override
                     public void handle(ActionEvent event)
                     {
                           iv1.setEffect(r);
                     }
                     }
        );
      
       
        
     */
        HBox hbEffects=new HBox(5);
        HBox hbEffectsSlider=new HBox(5);
        HBox hbImage=new HBox();
       
        HBox hbEffects1=new HBox(5);
        HBox hbEffectsSlider1=new HBox(5);
        HBox hbImage1=new HBox();

        
        /*hbEffects.getChildren().add(btnChooseImage);
        hbEffects.getChildren().add(btnLightEffect);
        hbEffects.getChildren().add(btnOuterShadow);
        hbEffects.getChildren().add(btnInnerShadow);
        hbEffects.getChildren().add(btnBlur);
        hbEffects.getChildren().add(btnReflection);*/
        hbImage.getChildren().add(iv1);
       
        final Label opacityCaption = new Label("Opacity Level:");
       
        final Slider opacityLevel = new Slider(0, 1, 1);
        opacityLevel.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    iv1.setOpacity(new_val.doubleValue());
                 
                
            }
        });
       
        final Label sepiaCaption = new Label("Sepia Tone:");
        final Slider sepiaTone = new Slider(0, 1, 1);
        final SepiaTone sepiaEffect = new SepiaTone();
       
        sepiaTone.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    sepiaEffect.setLevel(new_val.doubleValue());
                
                    iv1.setEffect(sepiaEffect);
            }
        });
       
        final Label scalingCaption = new Label("Zoom :");
        final Slider scaling = new Slider (0.5, 1, 1);
       
        scaling.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
              iv1.setScaleX(new_val.doubleValue());
              iv1.setScaleY(new_val.doubleValue());
                  
            }
        });
       
         final Label slider1 = new Label("Slider1:");
       
        final Slider Slider1 = new Slider(0, 1, 1);
        Slider1.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                
    
                
            }
        });
       
        hbEffectsSlider.getChildren().add(opacityCaption);
        hbEffectsSlider.getChildren().add(opacityLevel);
        hbEffectsSlider.getChildren().add(sepiaCaption);
        hbEffectsSlider.getChildren().add(sepiaTone);
        hbEffectsSlider.getChildren().add(scalingCaption);
        hbEffectsSlider.getChildren().add(scaling);
        hbEffectsSlider1.getChildren().add(slider1);
        hbEffectsSlider1.getChildren().add(Slider1);
        root.getChildren().add(hbEffects);
        root.getChildren().add(hbEffectsSlider);
        root.getChildren().add(hbEffectsSlider1);
        root.getChildren().add(hbImage);
       
       // primaryStage.setScene();
        //primaryStage.show();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}