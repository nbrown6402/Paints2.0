package com.example.paints;


import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.*;

public class HelloController {


    @FXML
    private ToggleGroup Type;
    @FXML
    private ScrollPane sp;

    @FXML
    private VBox vbox;
    @FXML
    private RadioMenuItem pencil;


    @FXML
    private TextField brushSize;

    @FXML
    private Canvas canv;


    @FXML
    private ColorPicker colorPicker;

    @FXML
    private RadioMenuItem eraser;
    @FXML
    private GraphicsContext graph;
    @FXML
    private RadioMenuItem move;

    public String direct;


    //**********************    Open   **************************************\\
    @FXML
    void OpeningImage(ActionEvent event) throws FileNotFoundException {
        graph = canv.getGraphicsContext2D();
        graph.clearRect(0, 0, canv.getWidth(), canv.getHeight());
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\nicol\\Pictures\\images"));
        File pic = fileChooser.showOpenDialog(null);
        direct = pic.toString();
        if (pic != null)
            try {
                InputStream im = new FileInputStream(pic);
                Image open = new Image(im);
                double oh = open.getHeight();
                double ow = open.getWidth();
                canv.setHeight(oh);
                canv.setWidth(ow);
                graph.drawImage(open, 0, 0);
            } catch (IOException e) {
                System.out.println("NO IMAGE FOR YOU!");
            }
    }

    //*********************    Save As   ************************************\\
    @FXML
    void SavingNewImage(ActionEvent event) {
        Stage stage = new Stage();
        FileChooser savefile = new FileChooser();
        savefile.setTitle("Save File");

        File file = savefile.showSaveDialog(stage);
        if (file != null) {
            try {
                WritableImage writableImage = new WritableImage((int) canv.getWidth(), (int) canv.getHeight());
                canv.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
                System.out.println("Error!");
            }
        }
    }


    //**********************    Save   **************************************\\
    @FXML
    void save(ActionEvent event) {

        File file = new File(direct);

        if (file != null) {
            try {
                WritableImage writableImage = new WritableImage((int) canv.getWidth(), (int) canv.getHeight());
                canv.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
                System.out.println("Error!");
            }
        }

    }

    //***************************    Pencil and Eraser   **************************************\\
    @FXML
    void Draw(ActionEvent event) {
    }

    public void initialize() {
        GraphicsContext graph = canv.getGraphicsContext2D();
        brushSize.setText("10");

        canv.setOnMouseDragged(e -> {
            double size = Double.parseDouble(brushSize.getText());
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;

            if (eraser.isSelected()) {
                graph.clearRect(x, y, size, size);
            } else if (pencil.isSelected()) {
                graph.setFill(colorPicker.getValue());
                graph.fillOval(x, y, size, size);
                graph.stroke();
            }
        });


    }

    //*******************************    About menu   **************************************\\
    @FXML
    void About(ActionEvent event) {

        File file = new File("C:\\Users\\nicol\\Desktop\\Cs250\\Paints\\src\\main\\assets\\About.txt");
        Desktop desktop = Desktop.getDesktop();
        if (file.exists())
            try {
                desktop.open(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    //*********************************    Help menu   **************************************\\
    @FXML
    void HELP(ActionEvent event) {
        File file = new File("C:\\Users\\nicol\\Desktop\\Cs250\\Paints\\src\\main\\assets\\HELP.txt");
        Desktop desktop = Desktop.getDesktop();
        if (file.exists())
            try {
                desktop.open(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    //********************************    Zoom with mouse   **************************************\\
    @FXML
    void zoom(ScrollEvent event) {
        double fac = 1.05;
        double deltaY = event.getDeltaY();
        if (deltaY < 0) {
            fac = 0.95;
        }
        canv.setScaleX(canv.getScaleX() * fac);
        canv.setScaleY(canv.getScaleY() * fac);
        event.consume();
    }

    @FXML
    void scale(ActionEvent event) {
        double spw = vbox.computeAreaInScreen();
        //double sph = sp.getHeight();
        //canv.setScaleX(sp);
        //canv.setScaleY(canv.getScaleY() / spw);

       // System.out.println(spw);
    }
}



