package com.bjak.dxfreduce;

import com.bjak.dxfreduce.entity.*;
import com.bjak.dxfreduce.util.FileUtil;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class TestCreateDxf {

    public static void main(String[] args) {
        Random random = new Random();
        try (DxfDocWriter dxfDocWriter = new DxfDocWriter()) {
            for (int i = 0; i < 50; i++) {
                Color randomColor = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
                LineWidth lineWidth = LineWidth.values()[random.nextInt(LineWidth.values().length)];
                // test add Circle
                Vector3 center = new Vector3(200 + i * 100, random.nextInt(200), 100);
                DxfCircle dxfCircle = new DxfCircle(center, 50);
                dxfCircle.setLineWidth(lineWidth);
                dxfCircle.setColor(randomColor);
                dxfCircle.setSolid(i % 3 == 0);
                dxfCircle.setSolidColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                dxfDocWriter.addEntity(dxfCircle);

                // test add Circle solid
                DxfCircle dxfCircle2 = new DxfCircle(new Vector3(200 + i * 100, -250, 100), 50);
                dxfCircle2.setLineWidth(lineWidth);
                dxfCircle2.setColor(randomColor);
                dxfCircle2.setSolid(i % 3 == 0);
                dxfDocWriter.addEntity(dxfCircle2);

                // test add line
                DxfLine dxfLine = new DxfLine(new Vector3(2600, 1000, 100), center);
                dxfLine.setColor(randomColor);
                dxfLine.setLineWidth(lineWidth);
                dxfDocWriter.addEntity(dxfLine);

                // test add lwPolyLine
                DxfLwPolyLine dxfLwPolyLine = new DxfLwPolyLine();
                dxfLwPolyLine.addPoint(new Vector2(i * 100, 1200));
                dxfLwPolyLine.addPoint(new Vector2(50 + i * 100, 1200));
                dxfLwPolyLine.addPoint(new Vector2(60 + i * 100, 1200 - 50));
                dxfLwPolyLine.addPoint(new Vector2(10 + i * 100, 1200 - 50));
                dxfLwPolyLine.setClose(i % 5 != 0);
                dxfLwPolyLine.setLineWidth(lineWidth);
                dxfLwPolyLine.setColor(randomColor);
                dxfDocWriter.addEntity(dxfLwPolyLine);

                // test add text
                DxfText dxfText = new DxfText();
                dxfText.setText((char) (65 + random.nextInt(26)) + "哈哈");
                dxfText.setAngle(random.nextInt(360));
                dxfText.setHigh(10 + random.nextInt(10));
                dxfText.setInclination(random.nextInt(45));
                dxfText.setWidth(1 + random.nextInt(2));
                dxfText.setColor(randomColor);
                dxfText.setStartPoint(new Vector3(i * 100, 1400, 100));
                dxfDocWriter.addEntity(dxfText);

                // test add arc
                DxfArc dxfArc = new DxfArc();
                dxfArc.setCenter(new Vector3(200 + i * 100, 1600, 100));
                dxfArc.setRadius(100);
                dxfArc.setStartAngle(random.nextInt(90));
                dxfArc.setEndAngle(180 + random.nextInt(180));
                dxfArc.setColor(randomColor);
                dxfArc.setLineWidth(lineWidth);
                dxfArc.setSolid(i % 5 == 0);
                dxfDocWriter.addEntity(dxfArc);
            }
            String generateNewDxf = "E:\\testDxf\\test_empty_generate.dxf";
            String entitiesFilePath = "E:\\testDxf\\test_empty_generate_entities.dxf";
            FileUtil.deleteFile(new File(generateNewDxf));
            FileUtil.deleteFile(new File(entitiesFilePath));
            dxfDocWriter.save(generateNewDxf, true);
            dxfDocWriter.saveEntities(entitiesFilePath, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
